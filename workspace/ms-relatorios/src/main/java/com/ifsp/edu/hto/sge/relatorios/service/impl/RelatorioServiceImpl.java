package com.ifsp.edu.hto.sge.relatorios.service.impl;

import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import com.ifsp.edu.hto.sge.relatorios.repository.RelatorioRepository;
import com.ifsp.edu.hto.sge.relatorios.service.IRelatorioService;
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
public class RelatorioServiceImpl implements IRelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Override
    public Optional<Relatorio> findById(String id) {
        return this.relatorioRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deletando relatório com ID: {}",id);
        this.relatorioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Relatorio> update(Relatorio relatorio, String id) {
        log.info("Atualizando relatório com ID: {}",id);
        Optional<Relatorio> companyOpt = this.findById(id);
        if(companyOpt.isPresent()){
            return Optional.ofNullable(this.relatorioRepository.save(relatorio));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void save(Relatorio relatorio) {
        log.info("Salvando relatório: {} ", relatorio.toString());
        this.relatorioRepository.save(relatorio);
    }

    @Override
    public Page<Relatorio> findAll(int page, int count) {
        return this.relatorioRepository.findAll(PageRequest.of(page, count));
    }

    @Override
    public List<Relatorio> getAllReports() {
        return this.relatorioRepository.findAll();
    }

    @Override
    public Relatorio findByContrato_NumeroContrato(String numeroContrato) {
        return this.relatorioRepository.findByContrato_NumeroContrato(numeroContrato);
    }
}
