package com.edutech.cl.edutech.cl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.repository.AlumnoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> findAll(){
        return alumnoRepository.findAll();
    }

    public Alumno findById(long id){
        return alumnoRepository.findById(id).get();
    }

    public Alumno save(Alumno alumno){
        return alumnoRepository.save(alumno);
    }

    public void delete(long id){
        alumnoRepository.deleteById(id);
    }

    public void deleteById(long id){
        alumnoRepository.deleteById(id);
    }

    
    
}
