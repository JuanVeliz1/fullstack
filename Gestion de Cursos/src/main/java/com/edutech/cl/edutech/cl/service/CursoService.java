package com.edutech.cl.edutech.cl.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.model.Curso;
import com.edutech.cl.edutech.cl.repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository claseRepository;

    public List<Curso> findAll(){
        return claseRepository.findAll();
    }

    public Curso findById(long id){
        return claseRepository.findById(id).get();
    }

    public Curso save(Curso alumno){
        return claseRepository.save(alumno);
    }

    public void delete(long id){
        claseRepository.deleteById(id);
    }

    public List<Alumno> obtenerAlumnosPorNombreCurso(String nombreClase) {
    Optional<Curso> claseOpt = claseRepository.findByNombreCurso(nombreClase);
    return claseOpt.map(Curso::getAlumnos).orElse(null);
}
}
