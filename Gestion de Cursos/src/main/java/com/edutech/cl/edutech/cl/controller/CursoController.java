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
import com.edutech.cl.edutech.cl.model.Curso;
import com.edutech.cl.edutech.cl.service.CursoService;

@RestController
@RequestMapping("/api/v1/Clases")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listar(){

        List<Curso> clases = cursoService.findAll();
        
        if (clases.isEmpty()) {
            return ResponseEntity.noContent().build();//return new ResponseEntity<>(HttpsStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(clases);//return new ResponseEntity<>(pacientes,HttpsStatus.OK);
        
    }

    @PostMapping
    public Curso agregarClase(@RequestBody Curso clase){
        return cursoService.save(clase);
    }

    @GetMapping("{id}")
    public Curso buscaClase(@PathVariable Integer id)
    {
        return cursoService.findById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Integer id) {
        cursoService.delete(id); 
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{nombreCurso}/alumnos")
    public ResponseEntity<List<Alumno>> obtenerAlumnosPorNombreCurso(@PathVariable String nombreCurso) {
        List<Alumno> alumnos = cursoService.obtenerAlumnosPorNombreCurso(nombreCurso);
        if (alumnos != null) {
        return ResponseEntity.ok(alumnos);
        } else {
        return ResponseEntity.notFound().build();
        }

    }

    //PERSONALIZADO
    

}
