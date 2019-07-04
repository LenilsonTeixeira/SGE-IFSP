package com.ifsp.edu.hto.sge.docentes.repository;

import com.ifsp.edu.hto.sge.docentes.entity.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Page<Docente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
