package com.ifsp.edu.hto.sge.atividades.service.impl;

import com.ifsp.edu.hto.sge.atividades.entity.Atividade;
import com.ifsp.edu.hto.sge.atividades.entity.AtividadeContabilizada;
import com.ifsp.edu.hto.sge.atividades.repository.AtividadeRepository;
import com.ifsp.edu.hto.sge.atividades.service.IAtividadeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AtividadeServiceImpl implements IAtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Override
    public Optional<Atividade> findById(String id) {
        return this.atividadeRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.info("Deletando atividade com ID: {}",id);
        this.atividadeRepository.deleteById(id);
    }

    @Override
    public Optional<Atividade> update(Atividade atividade, String id) {
        log.info("Atualizando atividade com ID: {}",id);
        Optional<Atividade> atividadeDb = this.findById(id);
        if(atividadeDb.isPresent()){
            return Optional.ofNullable(this.atividadeRepository.save(atividade));
        }
        return Optional.empty();
    }

    @Override
    public void save(Atividade atividade) {
        log.info("Salvando atividade: {} ", atividade.toString());
        this.atividadeRepository.save(atividade);
    }

    @Override
    public Page<Atividade> findAll(int page, int count) {
        return this.atividadeRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public Page<Atividade> findByNomeContainingIgnoreCase(String nome, int page, int count) {
        return this.atividadeRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, count));
    }

    public List<AtividadeContabilizada> aggregate() {
        return this.atividadeRepository.aggregate();
    }
}