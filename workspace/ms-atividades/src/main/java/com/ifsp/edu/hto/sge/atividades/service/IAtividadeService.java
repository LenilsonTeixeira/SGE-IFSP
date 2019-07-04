package com.ifsp.edu.hto.sge.atividades.service;

import com.ifsp.edu.hto.sge.atividades.entity.Atividade;
import com.ifsp.edu.hto.sge.atividades.entity.AtividadeContabilizada;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IAtividadeService {

    Optional<Atividade> findById(String id);

    void delete(String id);

    Optional<Atividade> update(Atividade atividade, String id);

    void save(Atividade atividade);

    Page<Atividade> findAll(int page, int count);

    Page<Atividade> findByNomeContainingIgnoreCase(String nome, int page, int count);

    List<AtividadeContabilizada> aggregate();
}