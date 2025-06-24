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
import com.edutech.cl.edutech.cl.repository.CursoRepository;

@SpringBootTest
public class CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    @MockBean
    private CursoRepository cursoRepository;

    private Curso curso;
    private Alumno alumno;
    private Profesor profesor;

    @BeforeEach
        void setUp(){
        
        ArrayList<Alumno> alumnos = new ArrayList<>();
        

        profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012"); 

        alumno = new Alumno(1,"123456789-0","Hector","Contreras","12/12/2012",curso);    

        curso = new Curso();
        curso.setId(1);   
        curso.setNombreCurso("1B");
        curso.setProfesor(profesor);
        curso.setAlumnos(alumnos);

    }

    @Test
    public void testFindAll(){

        when(cursoRepository.findAll()).thenReturn(List.of(curso));

        List<Curso> cursos = cursoService.findAll();

        assertNotNull(cursos);
        assertEquals(1,cursos.size());

    }

    @Test
    public void testFindByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una Carrera opcional.
        when(cursoRepository.findById((long)id)).thenReturn(Optional.of(curso));

        // Llama al método findByCodigo() del servicio.
        Curso encontrado = cursoService.findById(id);

        // Verifica que la Carrera devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }


    @Test
    public void testSave() {

        // Define el comportamiento del mock: cuando se llame a save(), devuelve la Carrera proporcionada.
        when(cursoRepository.save(curso)).thenReturn(curso);

        // Llama al método save() del servicio.
        Curso guardado = cursoService.save(curso);

        // Verifica que la Carrera guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(guardado);
        assertEquals(1, guardado.getId());
    }

    @Test
    public void testDeleteByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(cursoRepository).deleteById((long)id);

        // Llama al método deleteByCodigo() del servicio.
        cursoService.delete(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(cursoRepository, times(1)).deleteById((long)id);
    }



    



    
}
