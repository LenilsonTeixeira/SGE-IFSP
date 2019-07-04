package com.ifsp.edu.hto.sge.relatorios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioMensalEmailDto {
    private String aluno;
    private String prontuario;
    private String email;
    private String mesRelatorio;
    private Integer anoRelatorio;
    private Date dataVencimento;
}
