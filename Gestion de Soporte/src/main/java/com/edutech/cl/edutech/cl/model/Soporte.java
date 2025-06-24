package com.edutech.cl.edutech.cl.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="soporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Soporte extends RepresentationModel<Soporte> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 1500)
    private String mensajeSoporte;

}
