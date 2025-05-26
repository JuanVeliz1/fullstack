package com.edutech.cl.edutech.cl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.cl.edutech.cl.model.Soporte;
import com.edutech.cl.edutech.cl.service.SoporteService;

@RestController
@RequestMapping("/api/v1/Soporte")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;
    
    @GetMapping
    public ResponseEntity<List<Soporte>> listar(){

        List<Soporte> soportes = soporteService.findAll();
        
        if (soportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(soportes);
        
    }

    @PostMapping
    public Soporte agregarAlumno(@RequestBody Soporte soporte){
        return soporteService.save(soporte);
    }

    @GetMapping("{id}")
    public Soporte buscaAlumno(@PathVariable Integer id)
    {
        return soporteService.findById(id);
    }

    
}
