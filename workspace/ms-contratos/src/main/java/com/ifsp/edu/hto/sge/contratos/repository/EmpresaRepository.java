package com.ifsp.edu.hto.sge.contratos.repository;

import com.ifsp.edu.hto.sge.contratos.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Page<Empresa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Empresa findByCodigo(Integer codigo);
}
