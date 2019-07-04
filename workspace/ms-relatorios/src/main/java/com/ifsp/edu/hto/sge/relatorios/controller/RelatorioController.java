package com.ifsp.edu.hto.sge.relatorios.controller;

import com.ifsp.edu.hto.sge.relatorios.dto.RelatorioDto;
import com.ifsp.edu.hto.sge.relatorios.model.Relatorio;
import com.ifsp.edu.hto.sge.relatorios.response.Response;
import com.ifsp.edu.hto.sge.relatorios.service.IRelatorioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin
public class RelatorioController {

    @Autowired
    private IRelatorioService relatorioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<RelatorioDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<RelatorioDto>> response = new Response<>();
        Page<Relatorio> pages = relatorioService.findAll(page, count);
        Page<RelatorioDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
        Optional<Relatorio> relatorioOptional = relatorioService.findById(id);
        if (!relatorioOptional.isPresent()) {
            Response<String> response = new Response<String>();
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        relatorioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<RelatorioDto>> update(@Valid @RequestBody RelatorioDto relatorioDto, @PathVariable("id") String id){
        Response<RelatorioDto> response = new Response<>();
        Optional<Relatorio> relatorioOptional = relatorioService.findById(id);
        if (!relatorioOptional.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + relatorioDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Relatorio relatorio = modelMapper.map(relatorioDto, Relatorio.class);
        relatorioService.update(relatorio, id);
        response.setData(relatorioDto);
        return ResponseEntity.ok(response);
    }

    public Page<RelatorioDto> toPageObjectDto(Page<Relatorio> objects) {
        Page<RelatorioDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private RelatorioDto convertToObjectDto(Relatorio relatorio) {
        RelatorioDto relatorioDto = modelMapper.map(relatorio, RelatorioDto.class);
        return relatorioDto;
    }
}
