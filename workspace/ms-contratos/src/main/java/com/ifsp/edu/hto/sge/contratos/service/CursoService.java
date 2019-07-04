package com.ifsp.edu.hto.sge.contratos.service;

import com.ifsp.edu.hto.sge.contratos.entity.Curso;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    Optional<Curso> findById(Long id);

    void delete(Long id);

    Optional<Curso> update(Curso curso, Long id);

    void save(Curso curso);

    Page<Curso> findAll(int page, int count);

    List<Curso> getAllCourses();

    Page<Curso> findByNomeContainingIgnoreCase(String nome, int page, int count);
}
