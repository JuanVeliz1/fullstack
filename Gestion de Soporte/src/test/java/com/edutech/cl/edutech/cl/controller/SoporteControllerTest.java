package com.edutech.cl.edutech.cl.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.edutech.cl.edutech.cl.model.Soporte;

import com.edutech.cl.edutech.cl.service.SoporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(SoporteController.class) // Indica que se está probando el controlador de Estudiante
public class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockBean
    private SoporteService soporteService; // Crea un mock del servicio de Estudiante

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private Soporte soporte;

    @BeforeEach
        void setUp() {
            //Creamos un Arraylist Soportes para utilizarlo en el objeto soporte



            //Creamos objeto soporte para utilizarlo en los test
            
            soporte = new Soporte();
            soporte.setId(1);
            soporte.setMensajeSoporte("Ok");
 
        }

    @Test
    public void testGetAllSoportes() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Estudiante
        when(soporteService.findAll()).thenReturn(List.of(soporte));

        // Realiza una petición GET a /api/estudiantes y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/v1/Soporte"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica que el primer elemento tenga id 1
                .andExpect(jsonPath("$[0].mensajeSoporte").value("Ok")); // Verifica que el primer elemento tenga el nombre "Juan Pérez"
    }

    @Test
    public void testGetSoporteById() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findById() con 1, devuelve el objeto Estudiante
        when(soporteService.findById(1)).thenReturn(soporte);

        // Realiza una petición GET a /api/estudiantes/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/v1/Soporte/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.mensajeSoporte").value("Ok")); // Verifica que el run del objeto devuelto sea "12345678-9"
}

    @Test
    public void testCreateSoporte() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(soporteService.save(any(Soporte.class))).thenReturn(soporte);

        // Realiza una petición POST a /api/estudiantes con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/api/v1/Soporte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(soporte))) // Convierte el objeto Estudiante a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.mensajeSoporte").value("Ok"));// Verifica que el run del objeto devuelto sea "12345678-9"
}


   
}

