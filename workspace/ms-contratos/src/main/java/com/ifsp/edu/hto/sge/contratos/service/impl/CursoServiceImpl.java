package com.ifsp.edu.hto.sge.contratos.service.impl;

import com.ifsp.edu.hto.sge.contratos.entity.Curso;
import com.ifsp.edu.hto.sge.contratos.repository.CursoRepository;
import com.ifsp.edu.hto.sge.contratos.service.CursoService;
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
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Optional<Curso> findById(Long id) {
        log.info("Buscando curso com ID: {}",id);
        return this.cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deletando curso com ID: {}",id);
        this.cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Curso> update(Curso curso, Long id) {
        log.info("Atualizando curso com ID: {}",id);
        Optional<Curso> cursoOptional = this.findById(id);
        if(cursoOptional.isPresent()){
            return Optional.ofNullable(this.cursoRepository.save(curso));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void save(Curso curso) {
        log.info("Salvando curso: {} ", curso.getNome());
        this.cursoRepository.save(curso);
    }

    @Override
    public Page<Curso> findAll(int page, int count) {
        return this.cursoRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public List<Curso> getAllCourses() {
        return this.cursoRepository.findAll();
    }

    @Override
    public Page<Curso> findByNomeContainingIgnoreCase(String nome, int page, int count) {
        log.info("Buscando listagem de páginas por nome: {}", nome);
        return this.cursoRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, count));
    }
}
