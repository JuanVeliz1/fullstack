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
import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.service.ProfesorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/Profesores")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;

    @Operation(summary = "Obtiene el listado de profesors")
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Profesor.class)))
    @GetMapping
    public ResponseEntity<List<Profesor>> listar(){

        List<Profesor> profesores = profesorService.findAll();

        //HATE OAS
        profesores.forEach(m -> //For Each agrega en la lista profesors con un bucle for los links
        m.add(linkTo(methodOn(ProfesorController.class).buscarProfesor(m.getId())).withSelfRel()));

        CollectionModel<Profesor> modelo = CollectionModel.of(profesores);
        modelo.add(linkTo(methodOn(ProfesorController.class).listar()).withSelfRel());
        
        
        return ResponseEntity.ok(profesores);
        
    }
    

    @Operation(summary = "Crea una nueva Profesor")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Profesor creada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Profesor.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping
    public Profesor agregarProfesor(@RequestBody Profesor profesor){
        
        //HATE OAS
        Profesor nueva = profesorService.save(profesor);
        nueva.add(linkTo(methodOn(ProfesorController.class).buscarProfesor(nueva.getId())).withSelfRel()); // línea HATEOAS
        return nueva;

        //Retornamos el objeto Profesorcomo variable "nueva", enriquecido con links
    }

    @Operation(summary = "Obtiene una Profesor por su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Profesorencontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Profesor.class))),
        @ApiResponse(responseCode = "404",description = "Profesorno encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public Profesor buscarProfesor(@PathVariable Integer id)
    {   
        Profesor profesor= profesorService.findById(id);
        profesor.add(linkTo(methodOn(ProfesorController.class).buscarProfesor(id)).withSelfRel()); //HATE OAS

        //Agregamos link en la respuesta

        return profesor;
    }
    }
   
    
    
     

