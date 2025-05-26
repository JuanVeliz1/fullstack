package com.edutech.cl.edutech.cl.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="clase")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Clase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombreClase;

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)  //Solo se ingresa el id en postman
    private Profesor profesor;

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //Este parametro se ignora una vez que ingresemos el JSON en postaman 
    private List<Alumno> alumnos; //Para poder visualizar los alumnos al hacer consulta personalizada en CONTROLLER

}
