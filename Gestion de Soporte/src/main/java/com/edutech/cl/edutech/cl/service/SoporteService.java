package com.edutech.cl.edutech.cl.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.cl.edutech.cl.model.Soporte;
import com.edutech.cl.edutech.cl.repository.SoporteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    public List<Soporte> findAll(){
        return soporteRepository.findAll();
    }

    public Soporte findById(long id){
        return soporteRepository.findById(id).get();
    }

    public Soporte save(Soporte profesor){
        return soporteRepository.save(profesor);
    }

    public void delete(long id){
        soporteRepository.deleteById(id);
    }

    
    
}
