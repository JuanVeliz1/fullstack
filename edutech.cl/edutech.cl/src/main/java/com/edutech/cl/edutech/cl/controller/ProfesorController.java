package com.edutech.cl.edutech.cl.controller;

import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    @Autowired
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // Obtener todos los profesores
    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodos() {
        List<Profesor> profesores = profesorService.findAll();

        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesores);
    }

    // Agregar un nuevo profesor
    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody Profesor profesor) {
        Profesor nuevoProfesor = profesorService.save(profesor);
        return ResponseEntity.ok(nuevoProfesor);
    }

    // Eliminar profesor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Integer id) {
        profesorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

