package com.edutech.cl.edutech.cl.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.model.Clase;
import com.edutech.cl.edutech.cl.repository.ClaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    public List<Clase> findAll(){
        return claseRepository.findAll();
    }

    public Clase findById(long id){
        return claseRepository.findById(id).get();
    }

    public Clase save(Clase alumno){
        return claseRepository.save(alumno);
    }

    public void delete(long id){
        claseRepository.deleteById(id);
    }

    public List<Alumno> obtenerAlumnosPorNombreClase(String nombreClase) {
    Optional<Clase> claseOpt = claseRepository.findByNombreClase(nombreClase);
    return claseOpt.map(Clase::getAlumnos).orElse(null);
}
}
