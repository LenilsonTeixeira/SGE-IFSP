package com.ifsp.edu.hto.sge.documentos.controller;

import com.ifsp.edu.hto.sge.documentos.dto.DocumentoDto;
import com.ifsp.edu.hto.sge.documentos.entity.Documento;
import com.ifsp.edu.hto.sge.documentos.response.Response;
import com.ifsp.edu.hto.sge.documentos.service.DocumentoService;
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
@RequestMapping("/documentos")
@CrossOrigin
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<DocumentoDto>>> findAll(@PathVariable int page, @PathVariable int count){
        Response<Page<DocumentoDto>> response = new Response<>();
        Page<Documento> pages = documentoService.findAll(page, count);
        Page<DocumentoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<DocumentoDto>>> findByNameContainingIgnoreCase(@RequestParam("nome") String nome, @RequestParam("page") int page, @RequestParam("count") int count){
        Response<Page<DocumentoDto>> response = new Response<Page<DocumentoDto>>();
        Page<Documento> pages = documentoService.findByNomeContainingIgnoreCase(nome, page, count);
        Page<DocumentoDto> pageResponse = toPageObjectDto(pages);
        response.setData(pageResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<DocumentoDto>> save(@Valid @RequestBody DocumentoDto documentoDto, BindingResult result) throws ParseException {
        Response<DocumentoDto> response = new Response<DocumentoDto>();
        Documento documento = modelMapper.map(documentoDto, Documento.class);
        documentoService.save(documento);
        documentoDto.setId(documento.getId());
        response.setData(documentoDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Integer id) {
        Optional<Documento> documentoOptional = documentoService.findById(id);
        if (!documentoOptional.isPresent()) {
            Response<String> response = new Response<>();
            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        documentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<DocumentoDto>> update(@Valid @RequestBody DocumentoDto documentoDto, @PathVariable("id") Integer id){
        Response<DocumentoDto> response = new Response<DocumentoDto>();
        Optional<Documento> documentOpt = documentoService.findById(id);
        if (!documentOpt.isPresent()) {
            response.getErrors().add("Registro não encontrado id: " + documentoDto.getId());
            return ResponseEntity.badRequest().body(response);
        }
        Documento documento = modelMapper.map(documentoDto, Documento.class);
        documentoService.update(documento, id);
        response.setData(documentoDto);
        return ResponseEntity.ok(response);
    }

    public Page<DocumentoDto> toPageObjectDto(Page<Documento> objects) {
        Page<DocumentoDto> dtos  = objects.map(this::convertToObjectDto);
        return dtos;
    }

    private DocumentoDto convertToObjectDto(Documento documento) {
        DocumentoDto documentoDto = modelMapper.map(documento, DocumentoDto.class);
        return documentoDto;
    }

}
