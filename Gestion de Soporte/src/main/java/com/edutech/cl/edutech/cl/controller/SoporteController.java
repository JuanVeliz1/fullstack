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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



import com.edutech.cl.edutech.cl.model.Soporte;
import com.edutech.cl.edutech.cl.service.SoporteService;


@RestController
@RequestMapping("/api/v1/Soporte")
public class SoporteController {
    
    @Autowired
    private SoporteService soporteService;

    @Operation(summary = "Obtiene el listado de soportes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class)))
    @GetMapping
    public ResponseEntity<List<Soporte>> listar(){

        List<Soporte> soportes = soporteService.findAll();

        //HATE OAS
        soportes.forEach(m -> //For Each agrega en la lista soportes con un bucle for los links
        m.add(linkTo(methodOn(SoporteController.class).buscarSoporte(m.getId())).withSelfRel()));

        CollectionModel<Soporte> modelo = CollectionModel.of(soportes);
        modelo.add(linkTo(methodOn(SoporteController.class).listar()).withSelfRel());
        
        
        return ResponseEntity.ok(soportes);
        
    }
    

    @Operation(summary = "Crea una nueva Soporte")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Soportecreada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping
    public Soporte agregarSoporte(@RequestBody Soporte soporte){
        
        //HATE OAS
        Soporte nueva = soporteService.save(soporte);
        nueva.add(linkTo(methodOn(SoporteController.class).buscarSoporte(nueva.getId())).withSelfRel()); // línea HATEOAS
        return nueva;

        //Retornamos el objeto Soportecomo variable "nueva", enriquecido con links
    }

    @Operation(summary = "Obtiene una Soportepor su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Soporteencontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "404",description = "Soporteno encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public Soporte buscarSoporte(@PathVariable Integer id)
    {   
        Soporte soporte= soporteService.findById(id);
        soporte.add(linkTo(methodOn(SoporteController.class).buscarSoporte(id)).withSelfRel()); //HATE OAS

        //Agregamos link en la respuesta

        return soporte;
    }
    }

