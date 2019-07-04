package com.ifsp.edu.hto.sge.contratos.event.model;

import com.ifsp.edu.hto.sge.contratos.dto.AlunoEventDto;
import com.ifsp.edu.hto.sge.contratos.enums.SituacaoContrato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoEvent implements Serializable {
    private String numeroContrato;
    private AlunoEventDto alunoEventDto;
    private Date dataInicial;
    private Date dataFinal;
    private Date dataRenovacao;
    private Date dataTermino;
    private SituacaoContrato situacaoContrato;
}
