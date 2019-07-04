package com.ifsp.edu.hto.sge.contratos.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "curso")
@Setter
@Getter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 3)
    @NotBlank
    @Column(name = "sigla")
    private String sigla;

    @Size(max = 255)
    @NotBlank
    @Column(name = "nome")
    private String nome;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }
}
