package com.edutech.cl.edutech.cl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.service.ProfesorService;

@RestController
@RequestMapping("/api/v1/Profesores")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @GetMapping
    public ResponseEntity<List<Profesor>> listar(){

        List<Profesor> profesores = profesorService.findAll();
        
        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesores);
        
    }

    @PostMapping
    public Profesor agregarProfesor(@RequestBody Profesor profesor){
        return profesorService.save(profesor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Integer id) {
        profesorService.delete(id); 
        return ResponseEntity.noContent().build(); 
    
}
}
