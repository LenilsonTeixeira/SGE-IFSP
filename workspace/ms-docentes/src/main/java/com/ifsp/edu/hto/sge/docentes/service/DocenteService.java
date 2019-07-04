package com.ifsp.edu.hto.sge.docentes.service;

import com.ifsp.edu.hto.sge.docentes.entity.Docente;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface DocenteService {

    Optional<Docente> findById(Long id);

    void delete(Long id);

    Optional<Docente> update(Docente docente, Long id);

    void save(Docente docente);

    Page<Docente> findAll(int page, int count);

    Page<Docente> findByNomeContainingIgnoreCase(String name, int page, int count);
}
