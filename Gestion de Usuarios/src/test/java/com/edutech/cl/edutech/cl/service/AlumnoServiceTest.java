package com.edutech.cl.edutech.cl.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.model.Curso;
import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.AlumnoRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AlumnoServiceTest {

    // Inyecta el servicio de Alumno para ser probado.
    @Autowired
    private AlumnoService alumnoService;

    // Crea un mock del repositorio de Alumno para simular su comportamiento.
    @MockBean
    private AlumnoRepository alumnoRepository;
    
    private Alumno alumno;

    @BeforeEach
        void setUp() {
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
    public void testFindAll() {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con una Alumno.
        when(alumnoRepository.findAll()).thenReturn(List.of(alumno));

        // Llama al método findAll() del servicio.
        List<Alumno> alumnos = alumnoService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente una Alumno.
        assertNotNull(alumnos);
        assertEquals(1, alumnos.size());
    }

    @Test
    public void testFindByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una Alumno opcional.
        when(alumnoRepository.findById((long)id)).thenReturn(Optional.of(alumno));

        // Llama al método findByCodigo() del servicio.
        Alumno found = alumnoService.findById(id);

        // Verifica que la Alumno devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {


        // Define el comportamiento del mock: cuando se llame a save(), devuelve la Alumno proporcionada.
        when(alumnoRepository.save(alumno)).thenReturn(alumno);

        // Llama al método save() del servicio.
        Alumno saved = alumnoService.save(alumno);

        // Verifica que la Alumno guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Hector", saved.getNombre());
    }

    @Test
    public void testDeleteByCodigo() {
        long id = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(alumnoRepository).deleteById(id);

        // Llama al método deleteByCodigo() del servicio.
        alumnoService.delete(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(alumnoRepository, times(1)).deleteById(id);
    }
}
