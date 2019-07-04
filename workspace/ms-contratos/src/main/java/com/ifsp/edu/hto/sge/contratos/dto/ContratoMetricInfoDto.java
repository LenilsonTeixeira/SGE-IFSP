package com.ifsp.edu.hto.sge.contratos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoMetricInfoDto {
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
