package com.ifsp.edu.hto.sge.atividades.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Atividade {

  @Id
  @Indexed
  private String id;

  private String nome;

  @Override
  public String toString() { return new ReflectionToStringBuilder(this).toString(); }

}
