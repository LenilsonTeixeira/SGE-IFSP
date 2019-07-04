package com.ifsp.edu.hto.sge.contratos.event.mapper;

import com.ifsp.edu.hto.sge.contratos.dto.AlunoEventDto;
import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import com.ifsp.edu.hto.sge.contratos.event.model.ContratoEvent;
import org.springframework.stereotype.Component;

@Component
public class ContratoPublisherEventMapper {

    public ContratoEvent map(Contrato contrato){
        AlunoEventDto alunoEventDto = AlunoEventDto.builder()
                .nome(contrato.getAluno().getNome())
                .email(contrato.getAluno().getEmail())
                .prontuario(contrato.getAluno().getProntuario())
                .telefone(contrato.getAluno().getTelefone())
                .build();
        return ContratoEvent.builder()
                .numeroContrato(contrato.getNumeroContrato())
                .alunoEventDto(alunoEventDto)
                .situacaoContrato(contrato.getSituacaoContrato())
                .dataFinal(contrato.getDataFinal())
                .dataInicial(contrato.getDataInicial())
                .build();
    }

}
