package com.ifsp.edu.hto.sge.contratos.service;

import com.ifsp.edu.hto.sge.contratos.dto.ContratoDto;
import com.ifsp.edu.hto.sge.contratos.dto.ContratoMetricInfoDto;
import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ContratoService {

    Optional<Contrato> findById(Long id);

    void delete(Long id);

    Optional<Contrato> update(Contrato contrato, Long id);

    void save(Contrato contrato);

    Page<Contrato> findAll(int page, int count);

    Optional<ContratoMetricInfoDto> prepareMetrics();
}
