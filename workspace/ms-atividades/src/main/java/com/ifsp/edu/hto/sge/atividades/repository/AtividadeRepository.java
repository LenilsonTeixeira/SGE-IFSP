package com.ifsp.edu.hto.sge.atividades.repository;

import com.ifsp.edu.hto.sge.atividades.entity.Atividade;
import com.ifsp.edu.hto.sge.atividades.entity.AtividadeContabilizada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends MongoRepository<Atividade,String>, AtividadeRepositoryCustom {
    Page<Atividade> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    List<AtividadeContabilizada> aggregate();
}
