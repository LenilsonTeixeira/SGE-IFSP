package com.ifsp.edu.hto.sge.contratos.service.impl;

import com.ifsp.edu.hto.sge.contratos.entity.Aluno;
import com.ifsp.edu.hto.sge.contratos.repository.AlunoRepository;
import com.ifsp.edu.hto.sge.contratos.service.AlunoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AlunoServiceImpl implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public Optional<Aluno> findById(Long id) {
        log.info("Buscando aluno com ID: {}",id);
        return this.alunoRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deletando aluno com ID: {}",id);
        this.alunoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Aluno> update(Aluno aluno, Long id) {
        log.info("Atualizando aluno com ID: {}",id);
        Optional<Aluno> studentDb = this.findById(id);
        if(studentDb.isPresent()){
            return Optional.ofNullable(this.alunoRepository.save(aluno));
        }
        return Optional.empty();
    }

    @Override
    public List<Aluno> getAllStudents() {
        return this.alunoRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Aluno aluno) {
        log.info("Salvando aluno: {} ", aluno.getNome());
        this.alunoRepository.save(aluno);
    }

    @Override
    public Page<Aluno> findAll(int page, int count) {
        return this.alunoRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public Page<Aluno> findByNomeContainingIgnoreCase(String nome, int page, int count) {
        return this.alunoRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, count));
    }

    @Override
    public Aluno findByProntuarioContainingIgnoreCase(String prontuario) {
        return this.alunoRepository.findByProntuarioContainingIgnoreCase(prontuario);
    }
}
