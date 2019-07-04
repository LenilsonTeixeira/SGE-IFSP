package com.ifsp.edu.hto.sge.documentos.service;

import com.ifsp.edu.hto.sge.documentos.entity.Documento;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface DocumentoService {

    Optional<Documento> findById(Integer id);

    void delete(Integer id);

    Optional<Documento> update(Documento documento, Integer id);

    void save(Documento documento);

    Page<Documento> findAll(int page, int count);

    Page<Documento> findByNomeContainingIgnoreCase(String nome, int page, int count);
}
