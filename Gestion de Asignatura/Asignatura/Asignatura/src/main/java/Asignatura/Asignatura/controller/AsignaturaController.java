package Asignatura.Asignatura.controller;

import org.springframework.web.bind.annotation.*;
import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.service.AsignaturaService;

import java.util.List;
import java.util.stream.Collectors; // Necesario para .stream().collect(Collectors.toList())

// Importaciones para HATEOAS
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; // Importa estáticamente para usar linkTo y methodOn

// Importaciones para Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter; // Para anotar parámetros

@RestController
@RequestMapping("/api/v1/asignaturas")
@Tag(name = "Asignaturas", description = "API para la gestión de asignaturas en el sistema académico.") // Anotación a nivel de clase para Swagger
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las asignaturas", description = "Devuelve una lista de todas las asignaturas existentes, con enlaces HATEOAS para cada una y la colección.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CollectionModel.class))) // Indica que devuelve una colección
    public CollectionModel<EntityModel<Asignatura>> getAll() {
        List<Asignatura> asignaturas = asignaturaService.obtenerTodas();

        // Convertir cada Asignatura a EntityModel y añadir enlaces
        List<EntityModel<Asignatura>> asignaturaModels = asignaturas.stream()
                .map(asignatura -> EntityModel.of(asignatura, // Convierte la asignatura en un EntityModel
                        linkTo(methodOn(AsignaturaController.class).getById(asignatura.getId())).withSelfRel(), // Enlace a sí misma
                        linkTo(methodOn(AsignaturaController.class).getAll()).withRel("asignaturas"))) // Enlace a la colección
                .collect(Collectors.toList());

        // Devolver una CollectionModel con los EntityModels y un enlace a la propia colección
        return CollectionModel.of(asignaturaModels,
                linkTo(methodOn(AsignaturaController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una asignatura por ID", description = "Devuelve una asignatura específica según su ID, con enlaces HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Asignatura encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Asignatura.class)))
    @ApiResponse(responseCode = "404", description = "Asignatura no encontrada")
    public EntityModel<Asignatura> getById(
            @Parameter(description = "ID de la asignatura a buscar", required = true, example = "1")
            @PathVariable Integer id) {
        Asignatura asignatura = asignaturaService.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id)); // Manejo básico de no encontrado

        // Devolver un EntityModel con la asignatura y sus enlaces
        return EntityModel.of(asignatura,
                linkTo(methodOn(AsignaturaController.class).getById(id)).withSelfRel(), // Enlace a sí misma
                linkTo(methodOn(AsignaturaController.class).getAll()).withRel("asignaturas")); // Enlace a la colección
    }

    @PostMapping
    @Operation(summary = "Guardar una nueva asignatura", description = "Crea una nueva asignatura y devuelve la asignatura creada con enlaces HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Asignatura creada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Asignatura.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    public EntityModel<Asignatura> save(
            @Parameter(description = "Objeto Asignatura a crear. El ID será ignorado y generado automáticamente.", required = true)
            @RequestBody Asignatura asignatura) {
        Asignatura savedAsignatura = asignaturaService.save(asignatura);

        // Devolver un EntityModel con la asignatura guardada y sus enlaces
        return EntityModel.of(savedAsignatura,
                linkTo(methodOn(AsignaturaController.class).getById(savedAsignatura.getId())).withSelfRel(), // Enlace a la asignatura recién creada
                linkTo(methodOn(AsignaturaController.class).getAll()).withRel("asignaturas")); // Enlace a la colección
    }
}