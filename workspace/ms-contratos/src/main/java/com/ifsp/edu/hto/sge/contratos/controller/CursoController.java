package com.ifsp.edu.hto.sge.contratos.controller;

import com.ifsp.edu.hto.sge.contratos.dto.CursoDto;
import com.ifsp.edu.hto.sge.contratos.entity.Curso;
import com.ifsp.edu.hto.sge.contratos.service.CursoService;
import com.ifsp.edu.hto.sge.contratos.response.Response;
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
@RequestMapping("/cursos")
@CrossOrigin
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<CursoDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<CursoDto>> response = new Response<Page<CursoDto>>();
        Page<Curso> pages = cursoService.findAll(page, count);
        Page<CursoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<List<Curso>>> getAll(){
        Response<List<Curso>> response = new Response<>();
        List<Curso> cours = cursoService.getAllCourses();
        response.setData(cours);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<CursoDto>>> findByNameContainingIgnoreCase(@RequestParam("nome") String nome, @RequestParam("page") int page, @RequestParam("count") int count){
        Response<Page<CursoDto>> response = new Response<>();
        Page<Curso> pages = cursoService.findByNomeContainingIgnoreCase(nome, page, count);
        Page<CursoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<CursoDto>> save(@Valid @RequestBody CursoDto cursoDto, BindingResult result) throws ParseException {
        Response<CursoDto> response = new Response<>();
        Curso curso = modelMapper.map(cursoDto, Curso.class);
        cursoService.save(curso);
        cursoDto.setId(curso.getId());
        response.setData(cursoDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Optional<Curso> courseOpt = cursoService.findById(id);
        if (!courseOpt.isPresent()) {
            Response<String> response = new Response<String>();
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<CursoDto>> update(@Valid @RequestBody CursoDto cursoDto, @PathVariable("id") Long id){
        Response<CursoDto> response = new Response<CursoDto>();
        Optional<Curso> courseOpt = cursoService.findById(id);
        if (!courseOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + cursoDto.getId());
            return ResponseEntity.badRequest().body(response);
        }

        Curso curso = modelMapper.map(cursoDto, Curso.class);
        cursoService.update(curso, id);
        response.setData(cursoDto);
        return ResponseEntity.ok(response);
    }

    public Page<CursoDto> toPageObjectDto(Page<Curso> objects) {
        Page<CursoDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private CursoDto convertToObjectDto(Curso curso) {
        CursoDto cursoDto = modelMapper.map(curso, CursoDto.class);
        return cursoDto;
    }
}
