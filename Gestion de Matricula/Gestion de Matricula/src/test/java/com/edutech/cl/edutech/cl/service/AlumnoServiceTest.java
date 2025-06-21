package com.edutech.cl.edutech.cl.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.AlumnoRepository;

@SpringBootTest
public class AlumnoServiceTest {
    //Inyecta el servicio de Alumno para ser probado
    @Autowired
    private AlumnoService alumnoService;
    
    //Creacion de mock del repositorio Alumno para simular el comportamiento.
    @MockBean
    private AlumnoRepository alumnoRepository;

    //Llamamos a la clase Alumno para el BeforeEach
    private Alumno alumno;
    

    @BeforeEach
    void setUp(){
        //Creamos un Arraylist Alumnos para utilizarlo en el objeto alumno
        ArrayList<Alumno> alumnos = new ArrayList<>();

        //Creamos un objeto profesor para utilizarlo en el objeto Curso
        Profesor profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012");
        
        //Creamos un objeto curso para utilizarlo en el objeto alumno
        Curso curso = new Curso(1,"8° Básico",profesor,alumnos);

        //Creamos objeto alumno para utilizarlo en los test
        alumno = new Alumno();
        alumno.setId(1);
        alumno.setRun("123456789-0");
        alumno.setNombre("Hector");
        alumno.setApellido("Contreras");
        alumno.setFechaNacimiento("12/12/2012");
        alumno.setCurso(curso);
        
    }

    @Test
    public void testFindAll(){
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Alumno.
        when(alumnoRepository.findAll()).thenReturn(List.of(alumno));

        // Llama al método findAll() del servicio.
        List<Alumno> alumnos = alumnoService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente un Alumno.
        assertNotNull(alumnos);
        assertEquals(1, alumnos.size());
    }

    @Test
    public void testFindById(){
        int id = 1;
        when(alumnoRepository.findById((long)id)).thenReturn(Optional.of(alumno));

        // Llama al método findByCodigo() del servicio.
        Alumno encontrado = alumnoService.findById(id);

        // Verifica que el Alumno devuelto no sea nulo y que su id coincida con el id esperado.
        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }

    @Test
    public void testSave(){
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el alumno proporcionado.
        when(alumnoRepository.save(alumno)).thenReturn(alumno);

        // Llama al método save() del servicio.
        Alumno guardado = alumnoService.save(alumno);

        // Verifica que el Alumno devuelto no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(guardado);
        assertEquals("Hector", guardado.getNombre());
    }

    @Test
    public void testDeleteById(){
        int id = 1;

        // Define el comportamiento del mock.
        doNothing().when(alumnoRepository).deleteById((long)id);

        // Llama al método deleteByCodigo() del servicio.
        alumnoService.deleteById(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el id proporcionado.
        verify(alumnoRepository, times(1)).deleteById((long)id);
    }

}