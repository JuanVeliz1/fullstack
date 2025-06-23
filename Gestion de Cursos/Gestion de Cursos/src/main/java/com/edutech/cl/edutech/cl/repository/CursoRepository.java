package com.edutech.cl.edutech.cl.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.cl.edutech.cl.model.Curso;

public interface CursoRepository extends JpaRepository<Curso,Long>{
    Optional<Curso> findByNombreCurso(String nombreCurso);
}
