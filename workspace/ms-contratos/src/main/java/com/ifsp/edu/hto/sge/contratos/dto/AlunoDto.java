package com.ifsp.edu.hto.sge.contratos.dto;

import com.ifsp.edu.hto.sge.contratos.entity.Curso;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Setter
@Getter
public class AlunoDto {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String prontuario;

    private Curso curso;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }

}
