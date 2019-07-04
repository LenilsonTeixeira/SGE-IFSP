package com.ifsp.edu.hto.sge.relatorios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoRelatorio;
import com.ifsp.edu.hto.sge.relatorios.enums.TipoAcao;
import com.ifsp.edu.hto.sge.relatorios.model.Aluno;
import com.ifsp.edu.hto.sge.relatorios.model.Contrato;
import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import com.ifsp.edu.hto.sge.relatorios.model.RelatorioMensal;
import com.ifsp.edu.hto.sge.relatorios.utils.Message;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class KafkaReceiver {
    private static final String KAFKA_GROUP_ID = "kafka-cluster";
    private static final String KAFKA_TOPIC_CONTRACTOR_DATA = "ms-contratos-data";

    @Autowired
    private IRelatorioService relatorioService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = KAFKA_TOPIC_CONTRACTOR_DATA, groupId = KAFKA_GROUP_ID)
    public void receiveData(String data) throws IOException {
        log.info("Mensagem: {} - Recebida do Kafka", data);

        if(Optional.ofNullable(data).isPresent()){

            Message message = objectMapper.readValue(data, Message.class);
            TipoAcao tipoAcao = TipoAcao.valueOf(message.getHeaders().get("action"));
            Relatorio relatorio = extractPaylod(message.getPayload());
            switch (tipoAcao){
                case CREATE:{
                    RelatorioMensal relatorioMensal = buildReportMonth(relatorio);
                    relatorio.setRelatoriosMensais(Collections.singletonList(relatorioMensal));
                    relatorioService.save(relatorio);
                    break;
                }
                case UPDATE:{
                    Relatorio relatorioDB = relatorioService.findByContrato_NumeroContrato(relatorio.getContrato().getNumeroContrato());
                    relatorio.setRelatoriosMensais(relatorioDB.getRelatoriosMensais());
                    relatorio.setId(relatorioDB.getId());
                    relatorioService.update(relatorio, relatorioDB.getId());
                    break;
                }
                case DELETE:{
                    Relatorio relatorioDB = relatorioService.findByContrato_NumeroContrato(relatorio.getContrato().getNumeroContrato());
                    relatorioService.delete(relatorioDB.getId());
                    break;
                }
            }
        }
    }

    private Relatorio extractPaylod(String payload) throws IOException{
        Relatorio relatorio = new Relatorio();
        JSONObject payloadJson = new JSONObject(payload);
        String json = payloadJson.get("payload").toString();
        JSONObject alunoJson = new JSONObject(json);
        String jsonAluno = alunoJson.get("alunoEventDto").toString();
        Aluno aluno = objectMapper.readValue(jsonAluno, Aluno.class);
        Contrato contrato = objectMapper.readValue(json, Contrato.class);
        relatorio.setContrato(contrato);
        relatorio.getContrato().setAluno(aluno);
        return relatorio;
    }

    private RelatorioMensal buildReportMonth(Relatorio relatorio){
        RelatorioMensal relatorioMensal = new RelatorioMensal();
        relatorioMensal.setSituacaoRelatorio(SituacaoRelatorio.PENDENTE);
        LocalDate localDate = relatorio.getContrato().getDataInicial().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        relatorioMensal.setAnoRelatorio(localDate.getYear());
        relatorioMensal.setMesRelatorio(localDate.getMonth().name());
        Date dataVencimento = Date.from(localDate.plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        relatorioMensal.setDataVencimento(dataVencimento);
        return relatorioMensal;
    }

}
