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


@WebMvcTest(CursoController.class)
public class CursoControllerTest {
    @Autowired
    private MockMvc mockMvc; //Proporcionamos una manera de simular peticiones HTTP en las pruebas (Como postman)

    @MockBean
    private CursoService cursoService;

    @MockBean
    private CursoRepository cursoRepository;

    



    @Autowired
    private ObjectMapper objectMapper; //Lo utilizaremos para transformar objetos Java a JSON

    private Curso curso;

    private Alumno alumno;

    private Profesor profesor;

    

    @BeforeEach
    void setUp(){

      List<Alumno> alumnos = new ArrayList<>();
        

      profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012");

      curso = new Curso();
      curso.setId(1);   
      curso.setNombreCurso("1B");
      curso.setProfesor(profesor);
      curso.setAlumnos(alumnos);

      alumno = new Alumno(1,"123456789-0","Hector","Contreras","12/12/2012",curso);  
      
      alumnos.add(alumno);
      

   }

   @Test
   public void testFindAll() throws Exception{

   when(cursoService.findAll()).thenReturn(List.of(curso));
   
   mockMvc.perform(get("/api/v1/Cursos"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id").value(1))
          .andExpect(jsonPath("$[0].nombreCurso").value("1B"))
          .andExpect(jsonPath("$[0].profesor.id").value(1));
          
          
    }

    @Test
    public void testFindById() throws Exception{

        when(cursoService.findById(1)).thenReturn(curso);

        mockMvc.perform(get("/api/v1/Cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCurso").value("1B"))
                .andExpect(jsonPath("$.profesor.id").value(1));


    }

    @Test
    public void testSave() throws Exception{

        when(cursoService.save(any(Curso.class))).thenReturn(curso);

        mockMvc.perform(post("/api/v1/Cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCurso").value("1B"))
                .andExpect(jsonPath("$.profesor.id").value(1));
    }

    @Test
    public void testObtenerAlumnosPorNombreCurso() throws Exception {
    
    when(cursoService.obtenerAlumnosPorNombreCurso("1B"))
        .thenReturn(List.of(alumno));

    
    mockMvc.perform(get("/api/v1/Cursos/1B/alumnos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].nombre").value("Hector"))
        .andExpect(jsonPath("$[0].apellido").value("Contreras"))
        .andExpect(jsonPath("$[0].curso.nombreCurso").value("1B"));
}
    


}
