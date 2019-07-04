package com.ifsp.edu.hto.sge.relatorios.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Mes {
    JANUARY("Janeiro"),
    FEBRUARY("Fevereiro"),
    MARCH("Março"),
    APRIL("Abril"),
    MAY("Maio"),
    JUNE("Junho"),
    JULY("Julho"),
    AUGUST("Agosto"),
    SEPTEMBER("Setembro"),
    OCTOBER("Outubro"),
    NOVEMBER("Novembro"),
    DECEMBER("Dezembro");

    private String nome;
}
