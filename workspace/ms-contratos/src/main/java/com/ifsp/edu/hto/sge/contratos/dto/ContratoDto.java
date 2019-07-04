package com.ifsp.edu.hto.sge.contratos.dto;

import com.ifsp.edu.hto.sge.contratos.entity.Empresa;
import com.ifsp.edu.hto.sge.contratos.entity.Aluno;
import com.ifsp.edu.hto.sge.contratos.enums.SituacaoContrato;
import com.ifsp.edu.hto.sge.contratos.enums.StatusTermino;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
public class ContratoDto {
    private Long id;
    private String numeroContrato;
    private Aluno aluno;
    private Empresa empresa;
    private Integer horasSemanais;
    private BigDecimal salario;
    private Date dataInicial;
    private Date dataFinal;
    private SituacaoContrato situacaoContrato;
    private StatusTermino statusTermino;


}



