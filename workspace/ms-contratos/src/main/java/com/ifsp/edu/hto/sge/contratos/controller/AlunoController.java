package com.ifsp.edu.hto.sge.contratos.controller;

import com.ifsp.edu.hto.sge.contratos.dto.AlunoDto;
import com.ifsp.edu.hto.sge.contratos.entity.Aluno;
import com.ifsp.edu.hto.sge.contratos.service.AlunoService;
import com.ifsp.edu.hto.sge.contratos.response.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@CrossOrigin
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<AlunoDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<AlunoDto>> response = new Response<Page<AlunoDto>>();
        Page<Aluno> pages = alunoService.findAll(page, count);
        Page<AlunoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<AlunoDto>>> findByNameContainingIgnoreCase(@RequestParam("nome") String nome, @RequestParam("page") int page, @RequestParam("count") int count){
        Response<Page<AlunoDto>> response = new Response<Page<AlunoDto>>();
        Page<Aluno> pages = alunoService.findByNomeContainingIgnoreCase(nome, page, count);
        Page<AlunoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/prontuario")
    public ResponseEntity<Response<Aluno>> fillStudent(@RequestParam("prontuario") String prontuario){
        Response<Aluno> response = new Response<>();
        Optional<Aluno> aluno = Optional.ofNullable(this.alunoService.findByProntuarioContainingIgnoreCase(prontuario));
        if (!aluno.isPresent()) {
            response.getErrors().add("Registro não encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(aluno.get());
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<AlunoDto>> save(@Valid @RequestBody AlunoDto alunoDto, BindingResult result) throws ParseException {
        Response<AlunoDto> response = new Response<AlunoDto>();
        Aluno aluno = modelMapper.map(alunoDto, Aluno.class);
        alunoService.save(aluno);
        alunoDto.setId(aluno.getId());
        response.setData(alunoDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Optional<Aluno> alunoOptional = alunoService.findById(id);
        Response<String> response = new Response<String>();
        if (!alunoOptional.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        try{
            alunoService.delete(id);
        }catch(Exception e){
            response.getErrors().add("Não é possivel excluir o aluno, pois o aluno está associado a um contrato");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<AlunoDto>> update(@Valid @RequestBody AlunoDto alunoDto, @PathVariable("id") Long id){
        Response<AlunoDto> response = new Response<AlunoDto>();
        Optional<Aluno> studentOpt = alunoService.findById(id);
        if (!studentOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + alunoDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Aluno aluno = modelMapper.map(alunoDto, Aluno.class);
        alunoService.update(aluno, id);
        response.setData(alunoDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<Aluno>> findById(@PathVariable("id") Long id){
        Response<Aluno> response = new Response<>();
        Optional<Aluno> aluno = this.alunoService.findById(id);
        if (!aluno.isPresent()) {
            response.getErrors().add("Registro não encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(aluno.get());
        return ResponseEntity.ok(response);
    }

    public Page<AlunoDto> toPageObjectDto(Page<Aluno> objects) {
        Page<AlunoDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private AlunoDto convertToObjectDto(Aluno aluno) {
        AlunoDto alunoDto = modelMapper.map(aluno, AlunoDto.class);
        return alunoDto;
    }

}
