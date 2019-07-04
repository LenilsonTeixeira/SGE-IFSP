package com.ifsp.edu.hto.sge.contratos.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Setter
@Getter
public class EmpresaDto {

    private Long id;

    private Integer codigo;

    private String cnpj;

    private String nome;

    private String email;

    private String telefone;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }

}
