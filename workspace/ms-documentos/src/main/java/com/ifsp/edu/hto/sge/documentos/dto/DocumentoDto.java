package com.ifsp.edu.hto.sge.documentos.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Setter
@Getter
public class DocumentoDto {

    private Integer id;

    private String sigla;

    private String nome;

    private Integer copias;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }

}
