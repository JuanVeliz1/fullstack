package com.edutech.cl.edutech.cl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.ProfesorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> findAll(){
        return profesorRepository.findAll();
    }

    public Profesor findById(long id){
        return profesorRepository.findById(id).get();
    }

    public Profesor save(Profesor profesor){
        return profesorRepository.save(profesor);
    }

    public void delete(long id){
        profesorRepository.deleteById(id);
    }

    
    
}

