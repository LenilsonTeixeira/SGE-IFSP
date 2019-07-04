package com.ifsp.edu.hto.sge.relatorios.scheduler;

import com.ifsp.edu.hto.sge.relatorios.dto.RelatorioMensalEmailDto;
import com.ifsp.edu.hto.sge.relatorios.enums.Mes;
import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoContrato;
import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoRelatorio;
import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import com.ifsp.edu.hto.sge.relatorios.model.RelatorioMensal;
import com.ifsp.edu.hto.sge.relatorios.service.IRelatorioService;
import com.ifsp.edu.hto.sge.relatorios.utils.JsonUtils;
import com.ifsp.edu.hto.sge.relatorios.utils.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Component
@EnableScheduling
public class RelatorioMensalAtrasadosScheduler {
    @Autowired
    private IRelatorioService relatorioService;

    private static final String KAFKA_TOPIC = "ms-relatorios-atrasados-data";

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Scheduled(cron = "*/30 * * * * *")
    public void identifyOverdueReport(){
        log.info("Executando agendador de tarefas para Identificação de relatórios em atrado de entrega");

        List<Relatorio> relatorios = relatorioService.getAllReports().stream()
                .filter(r-> SituacaoContrato.ATIVO.equals(r.getContrato().getSituacaoContrato()))
                .collect(Collectors.toList());

        LocalDate dataAtual = LocalDate.now();

        relatorios.forEach(relatorio -> {
            LocalDate dataVencimento = relatorio.getRelatoriosMensais().get(relatorio.getRelatoriosMensais().size() - 1).getDataVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Optional<LocalDate> dataEntrega = Optional.ofNullable(Objects.nonNull(relatorio.getRelatoriosMensais().get(relatorio.getRelatoriosMensais().size() - 1).getDataEntrega()) ? relatorio.getRelatoriosMensais().get(relatorio.getRelatoriosMensais().size() - 1).getDataEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null);
            if(!dataEntrega.isPresent() && dataAtual.isAfter(dataVencimento)){
                relatorio.getRelatoriosMensais().get(relatorio.getRelatoriosMensais().size() - 1).setSituacaoRelatorio(SituacaoRelatorio.ATRASADO);
                RelatorioMensal relatorioMensalAtradado = relatorio.getRelatoriosMensais().get(relatorio.getRelatoriosMensais().size() - 1);
                log.info("Identificado relatório em atrado: {}",relatorio);
                relatorioService.update(relatorio, relatorio.getId());
                RelatorioMensalEmailDto relatorioMensalEmailDto = RelatorioMensalEmailDto.builder()
                        .aluno(relatorio.getContrato().getAluno().getNome())
                        .email(relatorio.getContrato().getAluno().getEmail())
                        .prontuario(relatorio.getContrato().getAluno().getProntuario())
                        .mesRelatorio(Mes.valueOf(relatorioMensalAtradado.getMesRelatorio()).getNome())
                        .anoRelatorio(relatorioMensalAtradado.getAnoRelatorio())
                        .dataVencimento(relatorioMensalAtradado.getDataVencimento())
                        .build();
                final String relatorioJson = JsonUtils.toJson(relatorioMensalEmailDto);
                log.info("Contruindo mensagem para publicar no kafka");
                Message message = new Message();
                message.setPayload(relatorioJson);
                try {
                    log.info("Publicando mensagem no kafka");
                    this.kafkaTemplate.send(KAFKA_TOPIC, message);
                } catch (Exception e) {
                    log.error("Falha ao publicar mensagem", e);
                }
            }

        });

    }
}
