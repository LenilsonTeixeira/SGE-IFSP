package com.ifsp.edu.hto.sge.relatorios.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoContrato;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Contrato {
    private String numeroContrato;
    private Aluno aluno;
    private Date dataInicial;
    private Date dataFinal;
    private Date dataRenovacao;
    private Date dataTermino;
    private SituacaoContrato situacaoContrato;
}
