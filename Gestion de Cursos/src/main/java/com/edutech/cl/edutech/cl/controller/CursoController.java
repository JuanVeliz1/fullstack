package com.edutech.cl.edutech.cl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/Cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;


    @Operation(summary = "Obtiene el listado de matriculas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class)))

    @GetMapping
    public ResponseEntity<List<Curso>> listar(){

        List<Curso> cursos = cursoService.findAll();

        //HATE OAS
        cursos.forEach(m -> //For Each agrega en la lista matriculas con un bucle for los links
        m.add(linkTo(methodOn(CursoController.class).buscaCurso(m.getId())).withSelfRel()));

        CollectionModel<Curso> modelo = CollectionModel.of(cursos);
        modelo.add(linkTo(methodOn(CursoController.class).listar()).withSelfRel());
        
         
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();//return new ResponseEntity<>(HttpsStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(cursos);//return new ResponseEntity<>(pacientes,HttpsStatus.OK);
        
    }

    @Operation(summary = "Crea un nuevo curso")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Curso creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })

    @PostMapping
    public Curso agregarCurso(@RequestBody Curso curso){

        Curso nuevo = cursoService.save(curso);
        nuevo.add(linkTo(methodOn(CursoController.class).buscaCurso(nuevo.getId())).withSelfRel()); // línea HATEOAS
        return nuevo;

    }

    @Operation(summary = "Obtiene una Curso por su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Curso encontrado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404",description = "Curso no encontrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public Curso buscaCurso(@PathVariable Integer id)
    {
        Curso curso = cursoService.findById(id);
        curso.add(linkTo(methodOn(CursoController.class).buscaCurso(id)).withSelfRel()); //HATE OAS

        //Agregamos link en la respuesta

        return curso;
    }
    
    
    @Operation(summary = "Elimina curso por id")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Curso eliminado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404",description = "Curso no encontrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Integer id) {

        Curso curso = cursoService.findById(id);
        curso.add(linkTo(methodOn(CursoController.class).buscaCurso(id)).withSelfRel()); //HATE OAS

        cursoService.delete(id); 
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Obtiene Alumnos por nombre curso")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Alumnos por nombre de curso encontrados",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404",description = "Alumnos ni curso encontrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("/{nombreCurso}/alumnos")
    public ResponseEntity<List<Alumno>> obtenerAlumnosPorNombreCurso(@PathVariable String nombreCurso) {



        List<Alumno> alumnos = cursoService.obtenerAlumnosPorNombreCurso(nombreCurso);

        if (alumnos != null) {
        alumnos.forEach(a -> //For Each agrega en la lista matriculas con un bucle for los links
        a.add(linkTo(methodOn(CursoController.class).buscaCurso(a.getId())).withSelfRel()));

        CollectionModel<Alumno> modelo = CollectionModel.of(alumnos);
        modelo.add(linkTo(methodOn(CursoController.class).listar()).withSelfRel());    
        return ResponseEntity.ok(alumnos);
        
        } else {
        return ResponseEntity.notFound().build();
        }

    }

    //PERSONALIZADO
    

}
