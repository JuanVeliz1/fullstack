package com.edutech.cl.edutech.cl.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v1/Alumnos")
public class AlumnoController {
    
    @Autowired
    private AlumnoService alumnoService;

    @Operation(summary = "Obtiene el listado de alumnos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Alumno.class)))
    @GetMapping
    public ResponseEntity<List<Alumno>> listar(){

        List<Alumno> alumnos = alumnoService.findAll();

        //HATE OAS
        alumnos.forEach(m -> //For Each agrega en la lista alumnos con un bucle for los links
        m.add(linkTo(methodOn(AlumnoController.class).buscarAlumno(m.getId())).withSelfRel()));

        CollectionModel<Alumno> modelo = CollectionModel.of(alumnos);
        modelo.add(linkTo(methodOn(AlumnoController.class).listar()).withSelfRel());
        
        
        return ResponseEntity.ok(alumnos);
        
    }
    

    @Operation(summary = "Crea una nueva Alumno")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Alumnocreada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Alumno.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping
    public Alumno agregarAlumno(@RequestBody Alumno alumno){
        
        //HATE OAS
        Alumno nueva = alumnoService.save(alumno);
        nueva.add(linkTo(methodOn(AlumnoController.class).buscarAlumno(nueva.getId())).withSelfRel()); // línea HATEOAS
        return nueva;

        //Retornamos el objeto Alumnocomo variable "nueva", enriquecido con links
    }

    @Operation(summary = "Obtiene una Alumnopor su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Alumnoencontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Alumno.class))),
        @ApiResponse(responseCode = "404",description = "Alumnono encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public Alumno buscarAlumno(@PathVariable Integer id)
    {   
        Alumno alumno= alumnoService.findById(id);
        alumno.add(linkTo(methodOn(AlumnoController.class).buscarAlumno(id)).withSelfRel()); //HATE OAS

        //Agregamos link en la respuesta

        return alumno;
    }
    }
   
    
    
     