package com.ifsp.edu.hto.sge.contratos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoEventDto {
    private String nome;
    private String email;
    private String telefone;
    private String prontuario;
}
