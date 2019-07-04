package com.ifsp.edu.hto.sge.contratos.controller;

import com.ifsp.edu.hto.sge.contratos.entity.Empresa;
import com.ifsp.edu.hto.sge.contratos.response.Response;
import com.ifsp.edu.hto.sge.contratos.dto.EmpresaDto;
import com.ifsp.edu.hto.sge.contratos.service.EmpresaService;
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
@RequestMapping("/empresas")
@CrossOrigin
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<EmpresaDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<EmpresaDto>> response = new Response<Page<EmpresaDto>>();
        Page<Empresa> pages = empresaService.findAll(page, count);
        Page<EmpresaDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<EmpresaDto>>> findByNameContainingIgnoreCase(@RequestParam("nome") String nome, @RequestParam("page") int page, @RequestParam("count") int count){
        Response<Page<EmpresaDto>> response = new Response<Page<EmpresaDto>>();
        Page<Empresa> pages = empresaService.findByNomeContainingIgnoreCase(nome, page, count);
        Page<EmpresaDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<EmpresaDto>> save(@Valid @RequestBody EmpresaDto empresaDto, BindingResult result) throws ParseException {
        Response<EmpresaDto> response = new Response<EmpresaDto>();
        Empresa empresa = modelMapper.map(empresaDto, Empresa.class);
        empresaService.save(empresa);
        empresaDto.setId(empresa.getId());
        response.setData(empresaDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Optional<Empresa> companyOpt = empresaService.findById(id);
        if (!companyOpt.isPresent()) {
            Response<String> response = new Response<String>();
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EmpresaDto>> update(@Valid @RequestBody EmpresaDto empresaDto, @PathVariable("id") Long id){
        Response<EmpresaDto> response = new Response<EmpresaDto>();
        Optional<Empresa> companyOpt = empresaService.findById(id);
        if (!companyOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + empresaDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Empresa empresa = modelMapper.map(empresaDto, Empresa.class);
        empresaService.update(empresa, id);
        response.setData(empresaDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/codigo")
    public ResponseEntity<Response<Empresa>> fillStudent(@RequestParam("codigo") Integer codigo){
        Response<Empresa> response = new Response<>();
        Optional<Empresa> empresa = Optional.ofNullable(this.empresaService.findByCodigo(codigo));
        if (!empresa.isPresent()) {
            response.getErrors().add("Registro não encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(empresa.get());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<Empresa>> findById(@PathVariable("id") Long id){
        Response<Empresa> response = new Response<>();
        Optional<Empresa> empresa = this.empresaService.findById(id);
        if (!empresa.isPresent()) {
            response.getErrors().add("Registro não encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(empresa.get());
        return ResponseEntity.ok(response);
    }

    public Page<EmpresaDto> toPageObjectDto(Page<Empresa> objects) {
        Page<EmpresaDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private EmpresaDto convertToObjectDto(Empresa empresa) {
        EmpresaDto empresaDto = modelMapper.map(empresa, EmpresaDto.class);
        return empresaDto;
    }

}
