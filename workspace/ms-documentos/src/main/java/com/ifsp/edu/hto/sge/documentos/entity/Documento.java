package com.ifsp.edu.hto.sge.documentos.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity(name = "Documento")
@Setter
@Getter
@Table(name = "documento")
public class Documento implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 3)
    @NotBlank
    @Column(name = "sigla")
    private String sigla;

    @Size(max = 255)
    @NotBlank
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "copias")
    private Integer copias;

    @Override
    public String toString() { return new ReflectionToStringBuilder(this).toString(); }
}
