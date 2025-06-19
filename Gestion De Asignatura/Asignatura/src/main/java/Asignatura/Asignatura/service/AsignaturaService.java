package Asignatura.Asignatura.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.repository.AsignaturaRepository;

import java.util.List;

@Service
public class AsignaturaService {
    @Autowired
    private  AsignaturaRepository repository;

    public AsignaturaService(AsignaturaRepository repository) {
        this.repository = repository;
    }

    public List<Asignatura> obtenerTodas() {
        return repository.findAll();
    }

    public Asignatura save(Asignatura asignatura) {
        return repository.save(asignatura);
    }
}