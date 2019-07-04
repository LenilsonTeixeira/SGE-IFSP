package com.ifsp.edu.hto.sge.relatorios.service;

import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IRelatorioService {
    Optional<Relatorio> findById(String id);

    void delete(String id);

    Optional<Relatorio> update(Relatorio relatorio, String id);

    void save(Relatorio relatorio);

    Page<Relatorio> findAll(int page, int count);

    List<Relatorio> getAllReports();

    Relatorio findByContrato_NumeroContrato(String numeroContrato);
}
