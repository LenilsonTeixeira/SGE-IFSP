package com.ifsp.edu.sge.analytics.controller;

import com.ifsp.edu.sge.analytics.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metricas")
@CrossOrigin
public class ContratoMetricController {

    @Autowired
    private ContratoRepository contratoRepository;

@GetMapping(value = "/{id}")
    public ResponseEntity<Object> getMetrics(@PathVariable("id") String id){
        return ResponseEntity.ok(this.contratoRepository.find(id));
    }
}
