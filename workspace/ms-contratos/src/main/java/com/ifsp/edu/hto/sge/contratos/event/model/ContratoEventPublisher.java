package com.ifsp.edu.hto.sge.contratos.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifsp.edu.hto.sge.contratos.event.enums.ContratoPublishActionEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContratoEventPublisher {

    private final ContratoEvent payload;
    private final ContratoPublishActionEnum action;

    public static ContratoEventPublisher with(final ContratoEvent payload, final ContratoPublishActionEnum action) {
        return new ContratoEventPublisher(payload, action);
    }

}
