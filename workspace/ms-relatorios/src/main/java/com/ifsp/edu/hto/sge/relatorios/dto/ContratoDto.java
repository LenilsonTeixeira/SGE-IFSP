package com.ifsp.edu.hto.sge.relatorios.dto;

import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoContrato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoDto {
    private String numeroContrato;
    private String prontuarioAluno;
    private Date dataInicial;
    private Date dataFinal;
    private Date dataRenovacao;
    private Date dataTermino;
    private SituacaoContrato situacaoContrato;
}
