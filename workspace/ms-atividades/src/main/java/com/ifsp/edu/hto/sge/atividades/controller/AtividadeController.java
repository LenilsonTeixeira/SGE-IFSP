package com.ifsp.edu.hto.sge.atividades.controller;

import com.ifsp.edu.hto.sge.atividades.dto.AtividadeDto;
import com.ifsp.edu.hto.sge.atividades.entity.Atividade;
import com.ifsp.edu.hto.sge.atividades.entity.AtividadeContabilizada;
import com.ifsp.edu.hto.sge.atividades.response.Response;
import com.ifsp.edu.hto.sge.atividades.service.IAtividadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atividades")
@CrossOrigin
public class AtividadeController {

    @Autowired
    private IAtividadeService activityService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<AtividadeDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<AtividadeDto>> response = new Response<>();
        Page<Atividade> pages = activityService.findAll(page, count);
        Page<AtividadeDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<AtividadeDto>>> findByNameContainingIgnoreCase(@RequestParam("nome") String nome, @RequestParam("page") int page, @RequestParam("count") int count){
        Response<Page<AtividadeDto>> response = new Response<Page<AtividadeDto>>();
        Page<Atividade> pages = activityService.findByNomeContainingIgnoreCase(nome, page, count);
        Page<AtividadeDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/agrupadas")
    //    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<List<AtividadeContabilizada>>> getActivitiesSummary(){
        Response<List<AtividadeContabilizada>> response = new Response<List<AtividadeContabilizada>>();
        response.setData(activityService.aggregate());
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<AtividadeDto>> save(@Valid @RequestBody AtividadeDto atividadeDto, BindingResult result) throws ParseException {
        Response<AtividadeDto> response = new Response<AtividadeDto>();
        Atividade atividade = modelMapper.map(atividadeDto, Atividade.class);
        atividade.setNome(atividade.getNome().toUpperCase());
        activityService.save(atividade);
        atividadeDto.setId(atividade.getId());
        response.setData(atividadeDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
        Response<String> response = new Response<String>();
        Optional<Atividade> activityOpt = activityService.findById(id);
        if (!activityOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        activityService.delete(id);
        return ResponseEntity.ok(new Response<String>());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<AtividadeDto>> update(@Valid @RequestBody AtividadeDto atividadeDto, @PathVariable("id") String id){
        Response<AtividadeDto> response = new Response<AtividadeDto>();
        Optional<Atividade> activityOpt = activityService.findById(id);
        if (!activityOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + atividadeDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Atividade atividade = modelMapper.map(atividadeDto, Atividade.class);
        activityService.update(atividade, id);
        response.setData(atividadeDto);
        return ResponseEntity.ok(response);
    }


    public Page<AtividadeDto> toPageObjectDto(Page<Atividade> objects) {
        Page<AtividadeDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private AtividadeDto convertToObjectDto(Atividade atividade) {
        AtividadeDto atividadeDto = modelMapper.map(atividade, AtividadeDto.class);
        return atividadeDto;
    }

}