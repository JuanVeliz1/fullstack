package com.edutech.cl.edutech.cl.controller;

import com.edutech.cl.edutech.cl.model.Matricula;
import com.edutech.cl.edutech.cl.repository.AlumnoRepository;
import com.edutech.cl.edutech.cl.repository.MatriculaRepository;
import com.edutech.cl.edutech.cl.service.MatriculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.hateoas.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


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



    @Operation(summary = "Obtiene el listado de matriculas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class)))
    @GetMapping
    public ResponseEntity<List<Matricula>> listar(){

        List<Matricula> matriculas = matriculaService.findAll();

        //HATE OAS
        matriculas.forEach(m -> //For Each agrega en la lista matriculas con un bucle for los links
        m.add(linkTo(methodOn(MatriculaController.class).buscarMatricula(m.getId())).withSelfRel()));

        CollectionModel<Matricula> modelo = CollectionModel.of(matriculas);
        modelo.add(linkTo(methodOn(MatriculaController.class).listar()).withSelfRel());
        
        
        return ResponseEntity.ok(matriculas);
        
    }
    

    @Operation(summary = "Crea una nueva Matricula")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Matricula creada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping
    public Matricula agregarMatricula(@RequestBody Matricula matricula){
        
        //HATE OAS
        Matricula nueva = matriculaService.save(matricula);
        nueva.add(linkTo(methodOn(MatriculaController.class).buscarMatricula(nueva.getId())).withSelfRel()); // línea HATEOAS
        return nueva;

        //Retornamos el objeto Matricula como variable "nueva", enriquecido con links
    }


    @Operation(summary = "Obtiene una Matricula por su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Matricula encontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class))),
        @ApiResponse(responseCode = "404",description = "Matricula no encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public Matricula buscarMatricula(@PathVariable Integer id)
    {   
        Matricula matricula = matriculaService.findById(id);
        matricula.add(linkTo(methodOn(MatriculaController.class).buscarMatricula(id)).withSelfRel()); //HATE OAS

        //Agregamos link en la respuesta

        return matricula;
    }


    @Operation(summary = "Crea una nueva Matricula simulando un pago y agregando al Alumno en su Base de datos")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Matricula creada exitosamente, el pago fue verificado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida, el pago no fue verificado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping("/Simulacion")
    public Map<String, String> procesarPago(@RequestBody Matricula matricula) {
        Map<String, String> respuesta = new HashMap<>();

        if (matricula.getMonto() > 0 && matricula.getAlumno() != null) {
            alumnoRepository.save(matricula.getAlumno());
            matriculaRepository.save(matricula);

            respuesta.put("estado", "exitoso");
            respuesta.put("mensaje", "Pago procesado, entregando detalles:" + matricula.getAlumno());


            String enlace = linkTo(methodOn(MatriculaController.class).buscarMatricula(matricula.getId())).withSelfRel().getHref();
            respuesta.put("link: ", enlace);

            //Agregamos link en la respuesta
            //HATE OAS


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
