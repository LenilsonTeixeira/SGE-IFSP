package com.ifsp.edu.hto.sge.relatorios.model;

import com.ifsp.edu.hto.sge.relatorios.enums.SituacaoRelatorio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioMensal {
    private String mesRelatorio;
    private Integer anoRelatorio;
    private SituacaoRelatorio situacaoRelatorio;
    private Date dataVencimento;
    private Date dataEntrega;
}
