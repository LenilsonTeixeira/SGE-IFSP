package com.ifsp.edu.hto.sge.contratos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoUsuario {

    ADMINISTRADOR("Administrador"),
    PADRAO("Padrão");

    private String descricao;
}
