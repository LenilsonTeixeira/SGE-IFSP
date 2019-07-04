package com.ifsp.edu.hto.sge.contratos.event.service;


import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import com.ifsp.edu.hto.sge.contratos.event.IContratoPublisherService;
import com.ifsp.edu.hto.sge.contratos.event.enums.ContratoPublishActionEnum;
import com.ifsp.edu.hto.sge.contratos.event.mapper.ContratoPublisherEventMapper;
import com.ifsp.edu.hto.sge.contratos.event.model.ContratoEvent;
import com.ifsp.edu.hto.sge.contratos.event.model.ContratoEventPublisher;
import com.ifsp.edu.hto.sge.contratos.event.utils.JsonUtils;
import com.ifsp.edu.hto.sge.contratos.event.utils.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class ContratoPublisherService implements IContratoPublisherService {

    private static final String KAFKA_TOPIC = "ms-contratos-data";
    private static final String KAFKA_TOPIC_METRICS = "ms-contratos-metrics";
    private static final String ACTION_HEADER = "action";

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private ContratoPublisherEventMapper contratoPublisherEventMapper;

    private Message buildMessage(final ContratoEventPublisher contratoEventPublisher) {

        final String contractEventString = JsonUtils.toJson(contratoEventPublisher);

        log.info("Contruindo mensagem para publicar no kafka: {}", contractEventString);

        Message message = new Message();
        message.setHeaders(buildHeaders(contratoEventPublisher));
        message.setPayload(contractEventString);
        return message;
    }

    private static Map<String, String> buildHeaders(final ContratoEventPublisher eventPublisher) {
        final Map<String, String> headers = new HashMap<>();
        headers.put(ACTION_HEADER, eventPublisher.getAction().name());
        return headers;
    }

    private ContratoEvent contractEventBuild(Contrato contrato, ContratoPublishActionEnum action) {
        return action.DELETE.equals(action) ? ContratoEvent.builder().numeroContrato(contrato.getNumeroContrato()).build() : contratoPublisherEventMapper.map(contrato);
    }

    public void accept(Contrato contrato, ContratoPublishActionEnum action) {
        try {
            log.info("Publicando mensagem no kafka");
            final ContratoEventPublisher contratoEventPublisher = ContratoEventPublisher.with(contractEventBuild(contrato, action), action);
            this.kafkaTemplate.send(KAFKA_TOPIC, buildMessage(contratoEventPublisher));
            this.kafkaTemplate.send(KAFKA_TOPIC_METRICS, new Message(null, "OK"));
        } catch (Exception e) {
            log.error("Falha ao publicar mensagem", e);
        }
    }
}
