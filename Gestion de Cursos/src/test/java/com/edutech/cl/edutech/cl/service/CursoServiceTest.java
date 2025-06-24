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

@SpringBootTest // Indica que se cargue el contexto completo de Spring para pruebas
public class CursoServiceTest {

    @Autowired // Se inyecta la clase CursoService
    private CursoService cursoService;

    @MockBean // Crea un mock del CursoRepository y lo reemplaza dentro del contexto de Spring
    private CursoRepository cursoRepository;

    private Curso curso;
    private Alumno alumno;
    private Profesor profesor;

    @BeforeEach //  Se ejecuta antes de cada @Test para inicializar los objetos
    void setUp(){

        ArrayList<Alumno> alumnos = new ArrayList<>();

        profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012"); // Crea un profesor de prueba

        alumno = new Alumno(1, "123456789-0", "Hector", "Contreras", "12/12/2012", curso); // Alumno ficticio asociado a curso

        curso = new Curso(); // Inicializa el objeto Curso de prueba
        curso.setId(1);
        curso.setNombreCurso("1B");
        curso.setProfesor(profesor);
        curso.setAlumnos(alumnos); // Aún vacío
    }

    @Test // Prueba del método findAll() del servicio
    public void testFindAll(){

        when(cursoRepository.findAll()).thenReturn(List.of(curso)); // Simula que el repositorio devuelve una lista con un curso

        List<Curso> cursos = cursoService.findAll(); // Llama al método del servicio

        assertNotNull(cursos); // Verifica que la lista no sea null
        assertEquals(1, cursos.size()); // Verifica que hay exactamente un curso
    }

    @Test // Prueba del método findById() del servicio
    public void testFindByCodigo() {
        int id = 1;

        when(cursoRepository.findById((long) id)).thenReturn(Optional.of(curso)); // Simula que el repositorio encuentra un curso

        Curso encontrado = cursoService.findById(id); // Llama al método real del servicio

        assertNotNull(encontrado); // Verifica que el curso no es null
        assertEquals(id, encontrado.getId()); // Verifica que el ID sea correcto
    }

    @Test // Prueba del método save() del servicio
    public void testSave() {

        when(cursoRepository.save(curso)).thenReturn(curso); // Simula que el curso se guarda y devuelve correctamente

        Curso guardado = cursoService.save(curso); // Guarda el curso a través del servicio

        assertNotNull(guardado); // Verifica que el resultado no sea null
        assertEquals(1, guardado.getId()); // Verifica que el ID del curso guardado sea 1
    }

    @Test // Prueba del método delete() del servicio
    public void testDeleteByCodigo() {
        int id = 1;

        doNothing().when(cursoRepository).deleteById((long) id); // Simula que el método delete no hace nada (no lanza error)

        cursoService.delete(id); // Llama al método de eliminar del servicio

        verify(cursoRepository, times(1)).deleteById((long) id); // Verifica que se llamó exactamente 1 vez al método del mock
    }
}

