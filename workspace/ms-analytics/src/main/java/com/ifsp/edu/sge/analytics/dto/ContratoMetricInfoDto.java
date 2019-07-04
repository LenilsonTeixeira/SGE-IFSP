package com.ifsp.edu.sge.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContratoMetricInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long totalContratos;
    private Long totalAlunos;
    private Long contratosAtivos;
    private Long contratosFinaliados;
    private BigDecimal mediaSalarial;
    private Long efetivados;
    private Long naoEfetivados;
    private Long totalEmpresas;
    private Long totalCursos;
    private List<EmpresaMetricInfoDto> empresas;
    private List<CursoMetricInfoDto> cursos;

    public List<EmpresaMetricInfoDto> getEmpresas(){
        if(Objects.isNull(empresas)){
            List<EmpresaMetricInfoDto> empresas = new ArrayList<>();
            return empresas;
        }
        return empresas;
    }

    public List<CursoMetricInfoDto> getCursos(){
        if(Objects.isNull(cursos)){
            List<CursoMetricInfoDto> cursos = new ArrayList<>();
            return cursos;
        }
        return cursos;
    }
}
