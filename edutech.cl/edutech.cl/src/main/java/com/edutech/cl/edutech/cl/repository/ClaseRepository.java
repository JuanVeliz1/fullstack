package com.edutech.cl.edutech.cl.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.cl.edutech.cl.model.Clase;

public interface ClaseRepository extends JpaRepository<Clase,Long>{
    Optional<Clase> findByNombreClase(String nombreClase);
}
