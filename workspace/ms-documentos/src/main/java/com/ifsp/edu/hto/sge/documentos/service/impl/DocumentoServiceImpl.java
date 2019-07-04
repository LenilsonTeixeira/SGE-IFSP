package com.ifsp.edu.hto.sge.documentos.service.impl;

import com.ifsp.edu.hto.sge.documentos.entity.Documento;
import com.ifsp.edu.hto.sge.documentos.repository.DocumentoRepository;
import com.ifsp.edu.hto.sge.documentos.service.DocumentoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class DocumentoServiceImpl implements DocumentoService{

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public Optional<Documento> findById(Integer id) {
        log.info("Buscando documento com ID: {}",id);
        return this.documentoRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deletando documento com ID: {}",id);
        this.documentoRepository.deleteById(id);
    }

    @Override
    public Optional<Documento> update(Documento documento, Integer id) {
        log.info("Atualizando documento com ID: {}",id);
        Optional<Documento> documentoOptional = this.findById(id);
        if(documentoOptional.isPresent()){
            return Optional.ofNullable(this.documentoRepository.save(documento));
        }
        return Optional.empty();
    }

    @Override
    public void save(Documento documento) {
        log.info("Salvando documento: {} ", documento.getNome());
        this.documentoRepository.save(documento);
    }

    @Override
    public Page<Documento> findAll(int page, int count) {
        log.info("Buscando listagem de páginas");
        return this.documentoRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public Page<Documento> findByNomeContainingIgnoreCase(String nome, int page, int count) {
        log.info("Buscando listagem de páginas contendo o nome: {}",nome);
        return this.documentoRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, count));
    }
}
