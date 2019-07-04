package com.ifsp.edu.hto.sge.documentos.repository;

import com.ifsp.edu.hto.sge.documentos.entity.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento,Integer> {
    Page<Documento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
