package com.ifsp.edu.hto.sge.contratos.service.impl;

import com.ifsp.edu.hto.sge.contratos.dto.ContratoMetricInfoDto;
import com.ifsp.edu.hto.sge.contratos.dto.CursoMetricInfoDto;
import com.ifsp.edu.hto.sge.contratos.dto.EmpresaMetricInfoDto;
import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import com.ifsp.edu.hto.sge.contratos.enums.SituacaoContrato;
import com.ifsp.edu.hto.sge.contratos.enums.StatusTermino;
import com.ifsp.edu.hto.sge.contratos.event.enums.ContratoPublishActionEnum;
import com.ifsp.edu.hto.sge.contratos.event.service.ContratoPublisherService;
import com.ifsp.edu.hto.sge.contratos.exception.ContratoException;
import com.ifsp.edu.hto.sge.contratos.repository.ContratoRepository;
import com.ifsp.edu.hto.sge.contratos.service.AlunoService;
import com.ifsp.edu.hto.sge.contratos.service.ContratoService;
import com.ifsp.edu.hto.sge.contratos.service.CursoService;
import com.ifsp.edu.hto.sge.contratos.service.EmpresaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ContratoPublisherService contratoPublisherService;

    @Override
    public Optional<Contrato> findById(Long id) {
        log.info("Buscando contrato ID: {}",id);
        return this.contratoRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deletando contrato ID: {}",id);
        Optional<Contrato> contrato = this.findById(id);
        if(contrato.isPresent()){
            contratoPublisherService.accept(contrato.get(), ContratoPublishActionEnum.DELETE);
        }
        this.contratoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Contrato> update(Contrato contrato, Long id) {
        log.info("Atualizando contrato ID: {}",id);
        Optional<Contrato> contratoOptional = this.findById(id);
        if(contratoOptional.isPresent()){
            contratoPublisherService.accept(contrato, ContratoPublishActionEnum.UPDATE);
            return Optional.ofNullable(this.contratoRepository.save(contrato));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void save(Contrato contrato) {

        validateUniqueContractBeforeSave(contrato);
        log.info("Salvando contrato: {} ", contrato.toString());
        YearMonth yearMonthActual = YearMonth.now();
        YearMonth yearMonthFinal = YearMonth.of(YearMonth.now().getYear(), Month.DECEMBER);
        Integer diffMonth = yearMonthFinal.getMonthValue() - yearMonthActual.getMonthValue();
        contrato.setNumeroContrato(generateContractNumber());
        this.contratoRepository.save(contrato);
        contratoPublisherService.accept(contrato, ContratoPublishActionEnum.CREATE);
    }

    @Override
    public Page<Contrato> findAll(int page, int count) {
        return this.contratoRepository.findAll(PageRequest.of(page, count));
    }

    public List<Contrato> getAllContracts(){
        return this.contratoRepository.findAll();
    }

    public Optional<ContratoMetricInfoDto> prepareMetrics(){
        log.info("Extraindo metricas do contrato");

        Long totalContratos = this.countContracts();
        Long totalAlunos = this.countStudents();
        Long totalEmpresas = this.countCompanies();
        Long totalCursos = this.countCourses();
        Long efetivados = this.countEngaged();
        Long naoEfetivados = this.countNonEngaged();
        BigDecimal mediaSalarial = this.getSalaryAverage();
        Long contratosAtivos = this.countActiveContracts();
        Long contratosFinaliados = this.countFinalizedContracts();
        List<CursoMetricInfoDto> cursos = this.getCoursesByContract();
        List<EmpresaMetricInfoDto> empresas = this.getCompaniesByContract();

        ContratoMetricInfoDto contratoMetricInfoDto = ContratoMetricInfoDto.builder()
                .contratosAtivos(contratosAtivos)
                .totalAlunos(totalAlunos)
                .totalContratos(totalContratos)
                .totalCursos(totalCursos)
                .totalEmpresas(totalEmpresas)
                .mediaSalarial(mediaSalarial)
                .naoEfetivados(naoEfetivados)
                .efetivados(efetivados)
                .cursos(cursos)
                .empresas(empresas)
                .contratosFinaliados(contratosFinaliados)
                .build();

        return Optional.of(contratoMetricInfoDto);
    }

    private Long countStudents(){
        return this.alunoService.getAllStudents().stream().count();
    }

    private Long countEngaged(){
        return this.getAllContracts().stream()
                .filter(c -> SituacaoContrato.FINALIZADO.equals(c.getSituacaoContrato()))
                .filter(c -> Objects.nonNull(c.getStatusTermino()))
                .filter(c -> StatusTermino.EFETIVADO.equals(c.getStatusTermino())).count();
    }

    private Long countNonEngaged(){
        return this.getAllContracts().stream()
                .filter(c -> SituacaoContrato.FINALIZADO.equals(c.getSituacaoContrato()))
                .filter(c -> Objects.nonNull(c.getStatusTermino()))
                .filter(c -> StatusTermino.NAO_EFETIVADO.equals(c.getStatusTermino())).count();
    }

    private Long countCourses(){
        return this.cursoService.getAllCourses().stream().count();
    }

    private BigDecimal getSalaryAverage(){
        BigDecimal salarioTotal = this.getAllContracts().stream()
                .filter(c -> SituacaoContrato.ATIVO.equals(c.getSituacaoContrato()))
                .map(Contrato::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return salarioTotal.divide(BigDecimal.valueOf(this.countActiveContracts()));

    }

    private Long countContracts(){
        return this.getAllContracts().stream().count();
    }

    private Long countCompanies(){
        return this.empresaService.getAllCompanies().stream().count();
    }

    private Long countFinalizedContracts(){
        return this.getAllContracts().stream().filter(c -> SituacaoContrato.FINALIZADO.equals(c.getSituacaoContrato())).count();
    }

    private Long countActiveContracts(){
        return this.getAllContracts().stream().filter(c -> SituacaoContrato.ATIVO.equals(c.getSituacaoContrato())).count();
    }

    private List<CursoMetricInfoDto> getCoursesByContract(){
        Map<String, Long> map = this.getAllContracts().stream().map(contract -> contract.getAluno().getCurso().getNome()).collect(Collectors.toList()).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<CursoMetricInfoDto> cursos = new ArrayList<>();
        map.forEach((nome, total) -> {
            cursos.add(new CursoMetricInfoDto(nome,total));
        });

        return cursos;
    }

    private List<EmpresaMetricInfoDto> getCompaniesByContract(){
        Map<String, Long> map = this.getAllContracts().stream().map(contract -> contract.getEmpresa().getNome()).collect(Collectors.toList()).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<EmpresaMetricInfoDto> empresas = new ArrayList<>();
        map.forEach((nome, total) -> {
            empresas.add(new EmpresaMetricInfoDto(nome,total));
            });

        return empresas;

    }

    private String generateContractNumber(){
        return UUID.randomUUID().toString();
    }

    private void validateUniqueContractBeforeSave(Contrato contrato){
        log.info("Validando se o estudante já possui contratos ativos");
        long total = this.getAllContracts().stream()
                .filter(c -> SituacaoContrato.ATIVO.equals(c.getSituacaoContrato()))
                .filter(c -> contrato.getAluno().getId().equals(c.getAluno().getId()))
                .count();

        if(total > 0){
            throw new ContratoException("O contrato não pode ser salvo porque o estudante: "+ contrato.getAluno().getNome()+ " possui um contrato ativo");
        }

    }

}
