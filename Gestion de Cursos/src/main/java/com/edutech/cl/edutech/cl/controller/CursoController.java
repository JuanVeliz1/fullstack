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

@RestController // Define que esta clase es un controlador REST de Spring
@RequestMapping("/api/v1/Cursos") // Ruta base para todos los endpoints de esta clase
public class CursoController {

    @Autowired // Inyección automática del servicio CursoService
    private CursoService cursoService;

    @Operation(summary = "Obtiene el listado de Cursos") // Swagger: descripción breve del endpoint
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa", // Swagger: documentación del código 200 OK
        content = @Content(mediaType = "application/json", // Swagger: el tipo de contenido de respuesta es JSON
        schema = @Schema(implementation = Curso.class))) // Swagger: el objeto devuelto es del tipo Curso
    @GetMapping // Define que este método responde a peticiones HTTP GET
    public ResponseEntity<List<Curso>> listar(){

        List<Curso> cursos = cursoService.findAll(); // Llama al servicio para obtener todos los cursos
        //  JUnit: probar que devuelve cursos correctamente
        //  Mockito: mockear cursoService.findAll()

        cursos.forEach(c -> //  HATEOAS: a cada curso le agrega un link con su propio ID
            c.add(linkTo(methodOn(CursoController.class).buscaCurso(c.getId())).withSelfRel())
        );

        CollectionModel<Curso> modelo = CollectionModel.of(cursos); //  HATEOAS: encapsula la colección con links
        modelo.add(linkTo(methodOn(CursoController.class).listar()).withSelfRel()); //  HATEOAS: agrega link a la lista completa

        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content si no hay cursos
        }
        return ResponseEntity.ok(cursos); // Retorna 200 OK con la lista de cursos
    }

    @Operation(summary = "Crea un nuevo curso") // Swagger: documentación del POST
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Curso creado exitosamente", // Código 201: creado
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"), // Documenta posibles errores
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping // Método para crear un nuevo curso usando POST
    public Curso agregarCurso(@RequestBody Curso curso){

        Curso nuevo = cursoService.save(curso); // Guarda el nuevo curso
        //  JUnit: probar que se crea correctamente un curso
        //  Mockito: mockear cursoService.save()

        nuevo.add(linkTo(methodOn(CursoController.class).buscaCurso(nuevo.getId())).withSelfRel()); //  HATEOAS: link al nuevo recurso creado
        return nuevo; // Devuelve el curso creado
    }

    @Operation(summary = "Obtiene una Curso por su ID") // Swagger: documentación del GET por ID
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Curso encontrado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404",description = "Curso no encontrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}") // Define que este método responde a GET /{id}
    public Curso buscaCurso(@PathVariable Integer id)
    {
        Curso curso = cursoService.findById(id); // Busca el curso por ID
        //  JUnit: probar que devuelve el curso correcto o lanza excepción si no existe
        //  Mockito: mockear cursoService.findById(id)

        curso.add(linkTo(methodOn(CursoController.class).buscaCurso(id)).withSelfRel()); //  HATEOAS: link al curso consultado

        return curso; // Devuelve el curso encontrado
    }

    @Operation(summary = "Elimina curso por id") // Swagger: documentación del DELETE
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Curso eliminado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404",description = "Curso no encontrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @DeleteMapping("{id}") // Método para eliminar curso por ID
    public ResponseEntity<Void> eliminarClase(@PathVariable Integer id) {


        cursoService.delete(id);  // Llama al servicio para eliminar el curso
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    @Operation(summary = "Obtiene Alumnos por nombre curso") // Swagger: documentación para obtener alumnos
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Alumnos por nombre de curso encontrados",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404",description = "Alumnos ni curso encontrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("/{nombreCurso}/alumnos") // Endpoint para obtener alumnos por nombre del curso
    public ResponseEntity<List<Alumno>> obtenerAlumnosPorNombreCurso(@PathVariable String nombreCurso) {

        List<Alumno> alumnos = cursoService.obtenerAlumnosPorNombreCurso(nombreCurso); // Llama al servicio
        //  JUnit: probar que devuelve lista de alumnos o not found
        //  Mockito: mockear cursoService.obtenerAlumnosPorNombreCurso()

        if (alumnos != null) {
            alumnos.forEach(a -> 
                a.add(linkTo(methodOn(CursoController.class).buscaCurso(a.getId())).withSelfRel()) //  HATEOAS: link al curso del alumno (aunque se usa ID del alumno aquí, revisar lógica)
            );

            CollectionModel<Alumno> modelo = CollectionModel.of(alumnos); //  HATEOAS: colección de alumnos
            modelo.add(linkTo(methodOn(CursoController.class).listar()).withSelfRel());

            return ResponseEntity.ok(alumnos); // Retorna 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encontraron alumnos
        }
    }
}
