package com.edutech.cl.edutech.cl.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="matricula")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fechaMatricula;

    @Column(nullable = false)
    private double monto;

    @ManyToOne(cascade = CascadeType.ALL)
    private Alumno alumno;

    @ManyToOne(cascade = CascadeType.ALL)
    private Curso curso;
    
}
