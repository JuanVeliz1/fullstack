package com.edutech.cl.edutech.cl.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.model.Curso;

import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.CursoRepository;
import com.edutech.cl.edutech.cl.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CursoController.class) // Solo carga el controlador CursoController y los componentes necesarios para probarlo
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permite simular llamadas HTTP como si fuera Postman

    @MockBean // Simula el bean CursoService que el controlador necesita
    private CursoService cursoService;

    @MockBean // También se mockea el repositorio (aunque no se usa directamente en los tests)
    private CursoRepository cursoRepository;

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private Curso curso;
    private Alumno alumno;
    private Profesor profesor;

    @BeforeEach // Se ejecuta antes de cada prueba para preparar los objetos de prueba
    void setUp(){

        List<Alumno> alumnos = new ArrayList<>();
        
        profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012"); // Profesor ficticio

        curso = new Curso(); // Curso de prueba
        curso.setId(1);
        curso.setNombreCurso("1B");
        curso.setProfesor(profesor);
        curso.setAlumnos(alumnos);

        alumno = new Alumno(1, "123456789-0", "Hector", "Contreras", "12/12/2012", curso); // Alumno ficticio asociado al curso

        alumnos.add(alumno); // Se agrega el alumno a la lista del curso
    }

    @Test // Test del endpoint GET /api/v1/Cursos
    public void testFindAll() throws Exception{

        when(cursoService.findAll()).thenReturn(List.of(curso)); // Simula que el servicio devuelve una lista con un curso

        mockMvc.perform(get("/api/v1/Cursos")) // Llama al endpoint como si fuera un cliente
                .andExpect(status().isOk()) // 🧪 Verifica que la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica que el primer curso tiene ID 1
                .andExpect(jsonPath("$[0].nombreCurso").value("1B")) // Verifica el nombre del curso
                .andExpect(jsonPath("$[0].profesor.id").value(1)); // Verifica el ID del profesor
    }

    @Test // Test del endpoint GET /api/v1/Cursos/1
    public void testFindById() throws Exception{

        when(cursoService.findById(1)).thenReturn(curso); // Simula que se devuelve un curso específico

        mockMvc.perform(get("/api/v1/Cursos/1")) // Llama al endpoint con ID 1
                .andExpect(status().isOk()) // 🧪 Espera código 200
                .andExpect(jsonPath("$.id").value(1)) // 🧪 Verifica ID
                .andExpect(jsonPath("$.nombreCurso").value("1B")) // Verifica nombre del curso
                .andExpect(jsonPath("$.profesor.id").value(1)); // Verifica el profesor
    }

    @Test // Test del endpoint POST /api/v1/Cursos
    public void testSave() throws Exception{

        when(cursoService.save(any(Curso.class))).thenReturn(curso); // Simula que se guarda y retorna el mismo curso

        mockMvc.perform(post("/api/v1/Cursos") // Simula llamada POST
                        .contentType(MediaType.APPLICATION_JSON) // Indica que se envía JSON
                        .content(objectMapper.writeValueAsString(curso))) // Convierte el objeto curso a JSON
                .andExpect(status().isOk()) // 🧪 Espera 200 OK (puede ser 201 si se cambia en el controller)
                .andExpect(jsonPath("$.id").value(1)) // Verifica ID
                .andExpect(jsonPath("$.nombreCurso").value("1B")) // Verifica nombre del curso
                .andExpect(jsonPath("$.profesor.id").value(1)); // Verifica ID del profesor
    }

    @Test // Test del endpoint GET /api/v1/Cursos/{nombreCurso}/alumnos
    public void testObtenerAlumnosPorNombreCurso() throws Exception {

        when(cursoService.obtenerAlumnosPorNombreCurso("1B"))
            .thenReturn(List.of(alumno)); // Simula que devuelve la lista de alumnos por nombre de curso

        mockMvc.perform(get("/api/v1/Cursos/1B/alumnos")) // Llama al endpoint correspondiente
            .andExpect(status().isOk()) // Verifica 200 OK
            .andExpect(jsonPath("$[0].id").value(1)) // Verifica ID del alumno
            .andExpect(jsonPath("$[0].nombre").value("Hector")) // Verifica nombre
            .andExpect(jsonPath("$[0].apellido").value("Contreras")) // Verifica apellido
            .andExpect(jsonPath("$[0].curso.nombreCurso").value("1B")); // Verifica nombre del curso dentro del alumno
    }
}

