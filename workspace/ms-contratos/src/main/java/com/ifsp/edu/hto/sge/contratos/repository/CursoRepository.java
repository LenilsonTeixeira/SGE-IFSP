package com.ifsp.edu.hto.sge.contratos.repository;

import com.ifsp.edu.hto.sge.contratos.entity.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {
    Page<Curso> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
