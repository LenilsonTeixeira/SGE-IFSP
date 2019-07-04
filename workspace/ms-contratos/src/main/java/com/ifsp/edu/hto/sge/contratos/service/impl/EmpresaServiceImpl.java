package com.ifsp.edu.hto.sge.contratos.service.impl;

import com.ifsp.edu.hto.sge.contratos.entity.Empresa;
import com.ifsp.edu.hto.sge.contratos.repository.EmpresaRepository;
import com.ifsp.edu.hto.sge.contratos.service.EmpresaService;
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
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> findById(Long id) {
        log.info("Buscando empresa com ID: {}",id);
        return this.empresaRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deletando empresa com ID: {}",id);
        this.empresaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Empresa> update(Empresa empresa, Long id) {
        log.info("Atualizando empresa com ID: {}",id);
        Optional<Empresa> empresaOptional = this.findById(id);
        if(empresaOptional.isPresent()){
            return Optional.ofNullable(this.empresaRepository.save(empresa));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void save(Empresa empresa) {
        log.info("Salvando empresa: {} ", empresa.getNome());
        this.empresaRepository.save(empresa);
    }

    @Override
    public Page<Empresa> findAll(int page, int count) {
        return this.empresaRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public List<Empresa> getAllCompanies(){
        return this.empresaRepository.findAll();
    }

    @Override
    public Empresa findByCodigo(Integer codigo) {
        return empresaRepository.findByCodigo(codigo);
    }

    @Override
    public Page<Empresa> findByNomeContainingIgnoreCase(String nome, int page, int count) {
        log.info("Buscando listagem de páginas por nome: {}", nome);
        return this.empresaRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, count));
    }
}
