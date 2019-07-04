package com.ifsp.edu.hto.sge.relatorios.repository;

import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends MongoRepository<Relatorio, String> {
    Relatorio findByContrato_NumeroContrato(String numeroContrato);
}
