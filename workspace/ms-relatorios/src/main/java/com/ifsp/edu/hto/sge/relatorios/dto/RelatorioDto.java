package com.ifsp.edu.hto.sge.relatorios.dto;

import com.ifsp.edu.hto.sge.relatorios.model.Contrato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioDto {
    private String id;
    private Contrato contrato;
    private List<RelatorioMensalDto> relatoriosMensais;


    public List<RelatorioMensalDto> getRelatoriosMensais(){
        if(Objects.isNull(relatoriosMensais)){
            List<RelatorioMensalDto> relatorios = new ArrayList<>();
            return relatorios;
        }
        return relatoriosMensais;
    }
}
