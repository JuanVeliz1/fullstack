package com.edutech.cl.edutech.cl.model;

import jakarta.persistence.Column;


@Entity
@Table(name="alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = true,length = 13)
    private String run;

    @Column(nullable = true)
    private String nombre;

    @Column(nullable = true)
    private String apellido;

    @Column(nullable = true)
    private String fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase;

}
