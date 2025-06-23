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
   public void testFindAll() throws Exception{

   when(cursoService.findAll()).thenReturn(List.of(curso));
   
   mockMvc.perform(get("/api/v1/Cursos"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id").value(1))
          .andExpect(jsonPath("$[0].nombreCurso").value("1B"))
          .andExpect(jsonPath("$[0].profesor.id").value(1))
          .andExpect(jsonPath("$[0].alumno.id").value(1))
          .andExpect(jsonPath("$[0].alumno.nombre").value("Hector"))
          .andExpect(jsonPath("$[0].alumno.apellido").value("Contreras"));
          
    }

    @Test
    public void testFindById() throws Exception{

        when(cursoService.findById(1)).thenReturn(curso);

        mockMvc.perform(get("/api/v1/Cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreCurso").value("1B"))
                .andExpect(jsonPath("$[0].profesor.id").value(1))
                .andExpect(jsonPath("$[0].alumno.id").value(1))
                .andExpect(jsonPath("$[0].alumno.nombre").value("Hector"))
                .andExpect(jsonPath("$[0].alumno.apellido").value("Contreras"));

    }

    @Test
    public void testSave() throws Exception{

        when(cursoService.save(any(Curso.class))).thenReturn(curso);

        mockMvc.perform(post("/api/v1/Cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreCurso").value("1B"))
                .andExpect(jsonPath("$[0].profesor.id").value(1))
                .andExpect(jsonPath("$[0].alumno.id").value(1))
                .andExpect(jsonPath("$[0].alumno.nombre").value("Hector"))
                .andExpect(jsonPath("$[0].alumno.apellido").value("Contreras"));
    }

    @Test
    public void testDeleteReserva() throws Exception {
        doNothing().when(cursoService).delete(1);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk());

        verify(cursoService, times(1)).delete(1);
    }


}
