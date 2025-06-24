package com.edutech.cl.edutech.cl.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="matricula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Matricula extends RepresentationModel<Matricula>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fechaMatricula;

    @Column(nullable = false)
    private double monto;

    @ManyToOne(cascade = CascadeType.ALL)
    private Alumno alumno;
    
}
