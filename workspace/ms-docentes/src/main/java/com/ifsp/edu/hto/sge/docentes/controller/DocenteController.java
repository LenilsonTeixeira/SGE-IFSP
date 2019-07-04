package com.ifsp.edu.hto.sge.docentes.controller;

import com.ifsp.edu.hto.sge.docentes.dto.DocenteDto;
import com.ifsp.edu.hto.sge.docentes.entity.Docente;
import com.ifsp.edu.hto.sge.docentes.response.Response;
import com.ifsp.edu.hto.sge.docentes.service.DocenteService;
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
@RequestMapping("/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<DocenteDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<DocenteDto>> response = new Response<Page<DocenteDto>>();
        Page<Docente> pages = docenteService.findAll(page, count);
        Page<DocenteDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<DocenteDto>>> findByNameContainingIgnoreCase(@RequestParam("nome") String nome, @RequestParam("page") int page, @RequestParam("count") int count){
        Response<Page<DocenteDto>> response = new Response<Page<DocenteDto>>();
        Page<Docente> pages = docenteService.findByNomeContainingIgnoreCase(nome, page, count);
        Page<DocenteDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<DocenteDto>> save(@Valid @RequestBody DocenteDto docenteDto, BindingResult result) throws ParseException {
        Response<DocenteDto> response = new Response<DocenteDto>();
        Docente docente = modelMapper.map(docenteDto, Docente.class);
        docenteService.save(docente);
        docenteDto.setId(docente.getId());
        response.setData(docenteDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Optional<Docente> teacherOpt = docenteService.findById(id);
        if (!teacherOpt.isPresent()) {
            Response<String> response = new Response<String>();
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        docenteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<DocenteDto>> update(@Valid @RequestBody DocenteDto docenteDto, @PathVariable("id") Long id){
        Response<DocenteDto> response = new Response<DocenteDto>();
        Optional<Docente> teacherOpt = docenteService.findById(id);
        if (!teacherOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + docenteDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Docente docente = modelMapper.map(docenteDto, Docente.class);
        docenteService.update(docente, id);
        response.setData(docenteDto);
        return ResponseEntity.ok(response);
    }

    public Page<DocenteDto> toPageObjectDto(Page<Docente> objects) {
        Page<DocenteDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private DocenteDto convertToObjectDto(Docente docente) {
        DocenteDto docenteDto = modelMapper.map(docente, DocenteDto.class);
        return docenteDto;
    }

}
