package com.ifsp.edu.sge.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CursoMetricInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private Long total;
}
