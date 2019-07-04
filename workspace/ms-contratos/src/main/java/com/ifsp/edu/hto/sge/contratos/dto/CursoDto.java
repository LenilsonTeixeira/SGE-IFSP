package com.ifsp.edu.hto.sge.contratos.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Setter
@Getter
public class CursoDto {

    private Long id;

    private String sigla;

    private String nome;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }

}
