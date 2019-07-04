package com.ifsp.edu.hto.sge.contratos.controller;

import com.ifsp.edu.hto.sge.contratos.dto.ContratoDto;
import com.ifsp.edu.hto.sge.contratos.dto.ContratoMetricInfoDto;
import com.ifsp.edu.hto.sge.contratos.entity.Contrato;
import com.ifsp.edu.hto.sge.contratos.response.Response;
import com.ifsp.edu.hto.sge.contratos.service.ContratoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/contratos")
@CrossOrigin
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<ContratoDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<ContratoDto>> response = new Response<>();
        Page<Contrato> pages = contratoService.findAll(page, count);
        Page<ContratoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<ContratoDto>> save(@Valid @RequestBody ContratoDto contratoDto, BindingResult result) throws ParseException {
        Response<ContratoDto> response = new Response<ContratoDto>();
        Contrato contrato = modelMapper.map(contratoDto, Contrato.class);
        contratoService.save(contrato);
        contratoDto.setId(contrato.getId());
        contratoDto.setNumeroContrato(contrato.getNumeroContrato());
        response.setData(contratoDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Optional<Contrato> contractOpt = contratoService.findById(id);
        if (!contractOpt.isPresent()) {
            Response<String> response = new Response<String>();
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        contratoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ContratoDto>> update(@Valid @RequestBody ContratoDto contratoDto, @PathVariable("id") Long id){
        Response<ContratoDto> response = new Response<>();
        Optional<Contrato> contractOpt = contratoService.findById(id);
        if (!contractOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + contratoDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Contrato contrato = modelMapper.map(contratoDto, Contrato.class);
        contratoService.update(contrato, id);
        response.setData(contratoDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/metricas")
    public ResponseEntity<ContratoMetricInfoDto> getMetrics(){
         Optional<ContratoMetricInfoDto> contractMetricDto = contratoService.prepareMetrics();
         return ResponseEntity.ok(contractMetricDto.get());
    }

    public Page<ContratoDto> toPageObjectDto(Page<Contrato> objects) {
        Page<ContratoDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private ContratoDto convertToObjectDto(Contrato contrato) {
        ContratoDto contratoDto = modelMapper.map(contrato, ContratoDto.class);
        return contratoDto;
    }
}
