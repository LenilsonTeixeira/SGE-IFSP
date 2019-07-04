package com.ifsp.edu.sge.analytics.service;

import com.ifsp.edu.sge.analytics.dto.ContratoMetricInfoDto;
import com.ifsp.edu.sge.analytics.model.ContratoMetricInfo;
import com.ifsp.edu.sge.analytics.repository.ContratoRepository;
import com.ifsp.edu.sge.analytics.utils.BuilderUri;
import com.ifsp.edu.sge.analytics.webclient.SendRequestEndpoint;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class KafkaReceiver {
    private static final String KAFKA_TOPIC_CONTRACTOR_METRICS = "ms-contratos-metrics";
    private static final String KAFKA_GROUP_ID = "kafka-cluster";
    private static final String CONTRACT_METRIC_ID  = "contrato";


    @Autowired
    private ContratoRepository contratoRepository;


    @KafkaListener(topics = KAFKA_TOPIC_CONTRACTOR_METRICS, groupId = KAFKA_GROUP_ID)
    public void receiveData(String data){
        log.info("Recebendo mensagem do kafka");

        if(Optional.ofNullable(data).isPresent()){

            log.info("Realizando requisição no endpoint do microserviço de contratos");

            SendRequestEndpoint sendRequestEndpoint = new SendRequestEndpoint();

            Optional<ContratoMetricInfoDto> contractMetricInfo = sendRequestEndpoint.callMetricsApiContractor(new BuilderUri().buildUri("http", "ms-contratos", "8080", "ms-contratos/v1/contratos/metricas"));

            if(contractMetricInfo.isPresent()){
                contratoRepository.save(new ContratoMetricInfo(CONTRACT_METRIC_ID, contractMetricInfo.get()));
            }
        }
    }

}
