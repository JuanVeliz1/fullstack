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
import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.service.AlumnoService;

@RestController
@RequestMapping("/api/v1/Alumnos")
public class AlumnoController {
    
    @Autowired
    private AlumnoService alumnoService;
    
    @GetMapping
    public ResponseEntity<List<Alumno>> listar(){

        List<Alumno> alumnos = alumnoService.findAll();
        
        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
        
    }

    @PostMapping
    public Alumno agregarAlumno(@RequestBody Alumno alumno){
        return alumnoService.save(alumno);
    }

    @GetMapping("{id}")
    public Alumno buscaAlumno(@PathVariable Integer id)
    {
        return alumnoService.findById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Integer id) {
        alumnoService.delete(id); 
        return ResponseEntity.noContent().build(); 

    
}    
}