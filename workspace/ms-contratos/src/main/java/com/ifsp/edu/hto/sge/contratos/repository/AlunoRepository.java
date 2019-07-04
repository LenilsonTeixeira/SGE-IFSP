package com.ifsp.edu.hto.sge.contratos.repository;

import com.ifsp.edu.hto.sge.contratos.entity.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Page<Aluno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Aluno findByProntuarioContainingIgnoreCase(String prontuario);

}
