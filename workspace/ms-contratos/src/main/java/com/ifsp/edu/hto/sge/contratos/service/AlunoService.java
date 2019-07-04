package com.ifsp.edu.hto.sge.contratos.service;

import com.ifsp.edu.hto.sge.contratos.entity.Aluno;
import com.ifsp.edu.hto.sge.contratos.entity.Curso;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AlunoService {

    Optional<Aluno> findById(Long id);

    void delete(Long id);

    Optional<Aluno> update(Aluno aluno, Long id);

    void save(Aluno aluno);

    Page<Aluno> findAll(int page, int count);

    Page<Aluno> findByNomeContainingIgnoreCase(String nome, int page, int count);

    Aluno findByProntuarioContainingIgnoreCase(String prontuario);

    List<Aluno> getAllStudents();
}
