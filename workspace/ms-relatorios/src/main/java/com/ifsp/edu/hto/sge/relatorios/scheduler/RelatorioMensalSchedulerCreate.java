package com.ifsp.edu.hto.sge.relatorios.scheduler;

import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoContrato;
import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoRelatorio;
import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import com.ifsp.edu.hto.sge.relatorios.model.RelatorioMensal;
import com.ifsp.edu.hto.sge.relatorios.service.IRelatorioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
@EnableScheduling
public class RelatorioMensalSchedulerCreate {

    @Autowired
    private IRelatorioService relatorioService;

    @Scheduled(cron = "0 08 4 8 * *")
    public void createReportMonth(){
        log.info("Executando agendador de tarefas para criação de relátórios mensais");

        List<Relatorio> relatorios = relatorioService.getAllReports().stream()
                .filter(r-> SituacaoContrato.ATIVO.equals(r.getContrato().getSituacaoContrato()))
                .collect(Collectors.toList());

        relatorios.forEach(relatorio -> {
            relatorio.getRelatoriosMensais().add(buildReportMonth(relatorio));
            relatorioService.update(relatorio, relatorio.getId());
        });

    }

    private RelatorioMensal buildReportMonth(Relatorio relatorio){
        log.info("{} - Criando relatório mensal: {}", LocalDateTime.now(), relatorio.toString());
        LocalDate dataAtual = LocalDate.now();
        RelatorioMensal relatorioMensal = new RelatorioMensal();
        relatorioMensal.setSituacaoRelatorio(SituacaoRelatorio.PENDENTE);
        LocalDate localDate = relatorio.getContrato().getDataInicial().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        relatorioMensal.setAnoRelatorio(dataAtual.getYear());
        relatorioMensal.setMesRelatorio(dataAtual.getMonth().name());
        Date dataVencimento = Date.from(dataAtual.plusDays(localDate.getDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        relatorioMensal.setDataVencimento(dataVencimento);
        return relatorioMensal;
    }
}
