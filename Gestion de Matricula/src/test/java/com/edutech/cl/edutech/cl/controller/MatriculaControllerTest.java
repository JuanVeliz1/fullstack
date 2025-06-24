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
import com.edutech.cl.edutech.cl.model.Matricula;
import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.AlumnoRepository;
import com.edutech.cl.edutech.cl.repository.MatriculaRepository;
import com.edutech.cl.edutech.cl.service.MatriculaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MatriculaController.class) //Indicamos que probaremos el controlador de matricula
public class MatriculaControllerTest {
    
    @Autowired
    private MockMvc mockMvc; //Proporcionamos una manera de simular peticiones HTTP en las pruebas (Como postman)

    @MockBean
    private MatriculaService matriculaService;

    @MockBean
    private AlumnoRepository alumnoRepository;

    @MockBean
    private MatriculaRepository matriculaRepository;



    @Autowired
    private ObjectMapper objectMapper; //Lo utilizaremos para transformar objetos Java a JSON

    private Matricula matricula;

    private Alumno alumno;

    

    @BeforeEach
    void setUp(){
        //Creamos un Arraylist Alumnos para crear objeto Curso
        ArrayList<Alumno> alumnos = new ArrayList<>();

        //Creamos un objeto profesor para crear objeto Curso
        Profesor profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012");
            
        //Creamos un objeto curso para crear objeto alumno y matricula
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
    public void testFindAll() throws Exception{
        

        when(matriculaService.findAll()).thenReturn(List.of(matricula));

        mockMvc.perform(get("/api/v1/Matriculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fechaMatricula").value("12/12/2012"))
                .andExpect(jsonPath("$[0].alumno.id").value(1))
                .andExpect(jsonPath("$[0].alumno.nombre").value("Hector"))
                .andExpect(jsonPath("$[0].alumno.apellido").value("Contreras"))
                .andExpect(jsonPath("$[0].alumno.curso.nombreCurso").value("8° Básico"))
                .andExpect(jsonPath("$[0].monto").value(250000));
    }

    @Test
    public void testFindById() throws Exception{

        when(matriculaService.findById(1)).thenReturn(matricula);

        mockMvc.perform(get("/api/v1/Matriculas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fechaMatricula").value("12/12/2012"))
                .andExpect(jsonPath("$.alumno.id").value(1))
                .andExpect(jsonPath("$.alumno.nombre").value("Hector"))
                .andExpect(jsonPath("$.alumno.apellido").value("Contreras"))
                .andExpect(jsonPath("$.alumno.curso.nombreCurso").value("8° Básico"))
                .andExpect(jsonPath("$.monto").value(250000));

    }

    @Test
    public void testSave() throws Exception{

        when(matriculaService.save(any(Matricula.class))).thenReturn(matricula);

        mockMvc.perform(post("/api/v1/Matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fechaMatricula").value("12/12/2012"))
                .andExpect(jsonPath("$.alumno.id").value(1))
                .andExpect(jsonPath("$.alumno.nombre").value("Hector"))
                .andExpect(jsonPath("$.alumno.apellido").value("Contreras"))
                .andExpect(jsonPath("$.alumno.curso.nombreCurso").value("8° Básico"))
                .andExpect(jsonPath("$.monto").value(250000));
    }

    @Test
    public void testProcesarPago() throws Exception{

        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        
        mockMvc.perform(post("/api/v1/Matriculas/Simulacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("exitoso"))
                .andExpect(jsonPath("$.mensaje").exists());

    }

    

    

}
