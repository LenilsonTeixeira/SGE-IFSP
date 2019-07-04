package com.ifsp.edu.hto.sge.docentes.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Setter
@Getter
public class DocenteDto {

    private Long id;

    private String nome;

    private String email;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }

}
