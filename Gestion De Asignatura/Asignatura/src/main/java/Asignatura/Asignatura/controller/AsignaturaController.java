package Asignatura.Asignatura.controller;


import org.springframework.web.bind.annotation.*;

import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.service.AsignaturaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asignaturas")
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @GetMapping
    public List<Asignatura> getAll() {
        return asignaturaService.obtenerTodas();
    }

    @PostMapping
    public Asignatura save(@RequestBody Asignatura asignatura) {
        return asignaturaService.save(asignatura);
    }
}