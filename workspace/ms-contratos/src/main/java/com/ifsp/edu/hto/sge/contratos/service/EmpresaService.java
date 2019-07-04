package com.ifsp.edu.hto.sge.contratos.service;

import com.ifsp.edu.hto.sge.contratos.entity.Empresa;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {

    Optional<Empresa> findById(Long id);

    void delete(Long id);

    Optional<Empresa> update(Empresa empresa, Long id);

    void save(Empresa empresa);

    Page<Empresa> findAll(int page, int count);

    Page<Empresa> findByNomeContainingIgnoreCase(String nome, int page, int count);

    List<Empresa> getAllCompanies();

    Empresa findByCodigo(Integer codigo);
}
