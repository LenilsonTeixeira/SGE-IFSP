package com.ifsp.edu.hto.sge.docentes.service.impl;

import com.ifsp.edu.hto.sge.docentes.entity.Docente;
import com.ifsp.edu.hto.sge.docentes.repository.DocenteRepository;
import com.ifsp.edu.hto.sge.docentes.service.DocenteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public Optional<Docente> findById(Long id) {
        log.info("Buscando docente com ID: {}",id);
        return this.docenteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.info("Deletando docente com ID: {}",id);
        this.docenteRepository.deleteById(id);
    }

    @Override
    public Optional<Docente> update(Docente docente, Long id) {
        log.info("Atualizando docente com ID: {}",id);
        Optional<Docente> docenteOptional = this.findById(id);
        if(docenteOptional.isPresent()){
            return Optional.ofNullable(this.docenteRepository.save(docente));
        }
        return Optional.empty();
    }

    @Override
    public void save(Docente docente) {
        log.info("Salvando docente: ID:{} Nome: {}", docente.getId(), docente.getNome());
        this.docenteRepository.save(docente);
    }

    @Override
    public Page<Docente> findAll(int page, int count) {
        log.info("Buscando listagem de páginas");
        return this.docenteRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public Page<Docente> findByNomeContainingIgnoreCase(String nome, int page, int count) {
        log.info("Buscando listagem de páginas por nome: {}", nome);
        return this.docenteRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, count));
    }
}
