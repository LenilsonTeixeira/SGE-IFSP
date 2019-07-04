package com.ifsp.edu.hto.sge.relatorios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Relatorio {
    @Id
    private String id;
    private Contrato contrato;
    private List<RelatorioMensal> relatoriosMensais;

    public List<RelatorioMensal> getRelatoriosMensais(){
        if(Objects.isNull(relatoriosMensais)){
            List<RelatorioMensal> relatorios = new ArrayList<>();
            return relatorios;
        }
        return relatoriosMensais;
    }

}
