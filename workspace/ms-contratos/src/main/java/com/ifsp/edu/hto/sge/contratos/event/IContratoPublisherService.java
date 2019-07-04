package com.ifsp.edu.hto.sge.contratos.event;

import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import com.ifsp.edu.hto.sge.contratos.event.enums.ContratoPublishActionEnum;

@FunctionalInterface
public interface IContratoPublisherService {
    void accept(Contrato contrato, ContratoPublishActionEnum action);
}
