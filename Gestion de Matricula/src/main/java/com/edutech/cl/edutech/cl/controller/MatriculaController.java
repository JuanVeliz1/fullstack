package com.edutech.cl.edutech.cl.controller;

import com.edutech.cl.edutech.cl.model.Matricula;
import com.edutech.cl.edutech.cl.repository.AlumnoRepository;
import com.edutech.cl.edutech.cl.repository.CursoRepository;
import com.edutech.cl.edutech.cl.repository.MatriculaRepository;
import com.edutech.cl.edutech.cl.service.MatriculaService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/Matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private CursoRepository cursoRepository;



    @GetMapping
    public ResponseEntity<List<Matricula>> listar(){

        List<Matricula> matriculas = matriculaService.findAll();
        
        if (matriculas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(matriculas);
        
    }

    @PostMapping
    public Matricula agregarMatricula(@RequestBody Matricula matricula){
        return matriculaService.save(matricula);
    }

    @GetMapping("{id}")
    public Matricula buscarMatricula(@PathVariable Integer id)
    {
        return matriculaService.findById(id);
    }

    @PostMapping("/Simulacion")
    public Map<String, String> procesarPago(@RequestBody Matricula matricula) {
        Map<String, String> respuesta = new HashMap<>();

        if (matricula.getMonto() > 0 && matricula.getAlumno() != null) {
            alumnoRepository.save(matricula.getAlumno());
            cursoRepository.save(matricula.getCurso())
            matriculaRepository.save(matricula);

            respuesta.put("estado", "exitoso");
            respuesta.put("mensaje", "Pago procesado, entregando detalles:" + matricula.getAlumno());
        } else {
            respuesta.put("estado", "rechazado");
            respuesta.put("mensaje", "Monto inválido o alumno no proporcionado");
        }

        return respuesta;
    }

    //PERSONALIZADO (Realiza simulacion de pago, almacena en base de datos)
}

//                { 
//                "estudiante": "Juan Pérez",
//                "curso": "3ro B",
//                "monto": 300000
//                }
