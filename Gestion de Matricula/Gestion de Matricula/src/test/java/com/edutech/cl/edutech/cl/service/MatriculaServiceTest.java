package com.edutech.cl.edutech.cl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.model.Curso;
import com.edutech.cl.edutech.cl.model.Matricula;
import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.MatriculaRepository;

@SpringBootTest
public class MatriculaServiceTest {
    
    @Autowired
    private MatriculaService matriculaService;

    @MockBean
    private MatriculaRepository matriculaRepository;

    private Matricula matricula;

    private Alumno alumno;

    @BeforeEach
        void setUp(){
        //Creamos un Arraylist Alumnos para crear objeto Curso
        ArrayList<Alumno> alumnos = new ArrayList<>();

        //Creamos un objeto profesor para crear objeto Curso
        Profesor profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012");
            
        //Creamos un objeto curso para crear objeto alumno
        Curso curso = new Curso(1,"8° Básico",profesor,alumnos);

        //Creamos objeto alumno para utilizarlo en matricula
        alumno = new Alumno(1,"123456789-0","Hector","Contreras","12/12/2012",curso);


        matricula = new Matricula();
        matricula.setId(1);
        matricula.setFechaMatricula("12/12/2012");
        matricula.setAlumno(alumno);
        matricula.setMonto(250000);
    }

    @Test
    public void testFindAll(){
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con una Matricula.
        when(matriculaRepository.findAll()).thenReturn(List.of(matricula));

        // Llama al método findAll() del servicio.
        List<Matricula> matriculas = matriculaService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente una Matricula.
        assertNotNull(matriculas);
        assertEquals(1, matriculas.size());
    }

    @Test
    public void testFindByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una Carrera opcional.
        when(matriculaRepository.findById((long)id)).thenReturn(Optional.of(matricula));

        // Llama al método findByCodigo() del servicio.
        Matricula encontrado = matriculaService.findById(id);

        // Verifica que la Carrera devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }

    @Test
    public void testSave() {

        // Define el comportamiento del mock: cuando se llame a save(), devuelve la Carrera proporcionada.
        when(matriculaRepository.save(matricula)).thenReturn(matricula);

        // Llama al método save() del servicio.
        Matricula guardado = matriculaService.save(matricula);

        // Verifica que la Carrera guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(guardado);
        assertEquals(1, guardado.getId());
    }

    @Test
    public void testDeleteByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(matriculaRepository).deleteById((long)id);

        // Llama al método deleteByCodigo() del servicio.
        matriculaService.deleteById(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(matriculaRepository, times(1)).deleteById((long)id);
    }


}
