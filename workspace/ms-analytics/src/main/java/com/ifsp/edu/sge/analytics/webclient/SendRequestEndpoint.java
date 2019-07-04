package com.ifsp.edu.sge.analytics.webclient;

import com.ifsp.edu.sge.analytics.dto.ContratoMetricInfoDto;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.util.Optional;

@Log4j2
public class SendRequestEndpoint {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public Optional<ContratoMetricInfoDto> callMetricsApiContractor(UriComponents uri){
        ResponseEntity<ContratoMetricInfoDto> responseEntity = REST_TEMPLATE.getForEntity(uri.toString(), ContratoMetricInfoDto.class);

        log.info("Requisição realizada com sucesso! Payload: {}",responseEntity.getBody().toString() );

        return Optional.of(responseEntity.getBody());
    }
}
