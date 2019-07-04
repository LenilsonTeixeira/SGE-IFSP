package com.ifsp.edu.hto.sge.notificador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.edu.hto.sge.notificador.model.RelatorioMensal;
import com.ifsp.edu.hto.sge.notificador.utils.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@Service
public class KafkaReceiver {

    private static final String KAFKA_TOPIC_CONTRACTOR_DATA = "ms-relatorios-atrasados-data";
    private static final String KAFKA_GROUP_ID = "kafka-cluster";

    @Autowired
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @KafkaListener(topics = KAFKA_TOPIC_CONTRACTOR_DATA, groupId = KAFKA_GROUP_ID)
    public void receiveData(String data) throws IOException {
        log.info("Mensagem: {} - Recebida do Kafka", data);

        if(Optional.ofNullable(data).isPresent()){
            Message message = objectMapper.readValue(data, Message.class);
            RelatorioMensal relatorio = extractPaylod(message.getPayload());
            emailService.sendEmail(relatorio);
        }
    }

    private RelatorioMensal extractPaylod(String payload) throws IOException{
        return objectMapper.readValue(payload, RelatorioMensal.class);
    }
}
