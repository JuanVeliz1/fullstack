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
import com.edutech.cl.edutech.cl.model.Clase;
import com.edutech.cl.edutech.cl.service.ClaseService;

@RestController
@RequestMapping("/api/v1/Clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public ResponseEntity<List<Clase>> listar(){

        List<Clase> clases = claseService.findAll();
        
        if (clases.isEmpty()) {
            return ResponseEntity.noContent().build();//return new ResponseEntity<>(HttpsStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(clases);//return new ResponseEntity<>(pacientes,HttpsStatus.OK);
        
    }

    @PostMapping
    public Clase agregarClase(@RequestBody Clase clase){
        return claseService.save(clase);
    }

    @GetMapping("{id}")
    public Clase buscaClase(@PathVariable Integer id)
    {
        return claseService.findById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Integer id) {
        claseService.delete(id); 
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{nombreClase}/alumnos")
    public ResponseEntity<List<Alumno>> obtenerAlumnosPorNombreClase(@PathVariable String nombreClase) {
        List<Alumno> alumnos = claseService.obtenerAlumnosPorNombreClase(nombreClase);
        if (alumnos != null) {
        return ResponseEntity.ok(alumnos);
        } else {
        return ResponseEntity.notFound().build();
        }

    }

    //PERSONALIZADO
    

}
