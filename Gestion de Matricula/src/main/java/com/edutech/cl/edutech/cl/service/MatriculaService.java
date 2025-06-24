package com.edutech.cl.edutech.cl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.cl.edutech.cl.model.Matricula;
import com.edutech.cl.edutech.cl.repository.MatriculaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public List<Matricula> findAll(){
        return matriculaRepository.findAll();
    }

    public Matricula findById(long id){
        return matriculaRepository.findById(id).get();
    }

    public Matricula save(Matricula profesor){
        return matriculaRepository.save(profesor);
    }

    public void delete(long id){
        matriculaRepository.deleteById(id);
    }

    public void deleteById(long id){
        matriculaRepository.deleteById(id);
    }
    
}
