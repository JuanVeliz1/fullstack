package Asignatura.Asignatura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.repository.AsignaturaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaService {
    @Autowired
    private AsignaturaRepository repository;

    // inyección por constructor para Autowired
    public AsignaturaService(AsignaturaRepository repository) {
        this.repository = repository;
    }

    public List<Asignatura> obtenerTodas() {
        return repository.findAll();
    }

    // método para buscar por ID
    public Optional<Asignatura> findById(Integer id) {
        return repository.findById(id);
    }

    public Asignatura save(Asignatura asignatura) {
        return repository.save(asignatura);
    }
}