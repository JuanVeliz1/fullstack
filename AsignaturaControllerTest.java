package Asignatura.Asignatura.controller;

import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.model.Profesor;
import Asignatura.Asignatura.service.AsignaturaService;
import com.fasterxml.jackson.databind.ObjectMapper; // Importar ObjectMapper
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*; // Importar matchers de Hamcrest

@WebMvcTest(AsignaturaController.class) // Enfocado en probar AsignaturaController
public class AsignaturaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Utilizado para simular peticiones HTTP

    @MockBean // Crea un mock de AsignaturaService y lo añade al contexto de Spring
    private AsignaturaService asignaturaService;

    @Autowired
    private ObjectMapper objectMapper; // Utilizado para convertir objetos a JSON

    private Profesor dummyProfesor;
    private Asignatura asignatura1;
    private Asignatura asignatura2;

    @BeforeEach
    void setUp() {
        // Inicializa profesores y asignaturas de prueba para ser usados en los tests
        dummyProfesor = new Profesor(1, "12.345.678-9", "Juan", "Perez", "01-01-1980");
        asignatura1 = new Asignatura(1, "Matemáticas I", dummyProfesor);
        asignatura2 = new Asignatura(2, "Programación Java", dummyProfesor);
    }

    @Test
    void testGetAllAsignaturasConContenido() throws Exception {
        // PREPARACIÓN (GIVEN): Define el comportamiento del servicio mock
        List<Asignatura> mockAsignaturas = Arrays.asList(asignatura1, asignatura2);
        when(asignaturaService.obtenerTodas()).thenReturn(mockAsignaturas);

        // ACCIÓN (WHEN) & VERIFICACIÓN (THEN): Realiza la petición GET y verifica la respuesta
        mockMvc.perform(get("/api/v1/asignaturas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un estado HTTP 200 OK
                .andExpect(jsonPath("$", hasSize(2))) // Espera un array JSON con 2 elementos
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombreAsignatura", is("Matemáticas I")))
                .andExpect(jsonPath("$[0].profesor.id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombreAsignatura", is("Programación Java")))
                .andExpect(jsonPath("$[1].profesor.id", is(1)));
    }

    @Test
    void testGetAllAsignaturasSinContenido() throws Exception {
        // PREPARACIÓN (GIVEN): Define el comportamiento del servicio mock para una lista vacía
        when(asignaturaService.obtenerTodas()).thenReturn(List.of());

        // ACCIÓN (WHEN) & VERIFICACIÓN (THEN): Realiza la petición GET y verifica la respuesta
        mockMvc.perform(get("/api/v1/asignaturas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un estado HTTP 200 OK con un array vacío
                .andExpect(jsonPath("$", hasSize(0))); // Espera un array JSON vacío
    }

    @Test
    void testSaveAsignatura() throws Exception {
        // PREPARACIÓN (GIVEN): Crea una asignatura para guardar y una simulada después de ser guardada
        Profesor newProfesor = new Profesor(2, "98.765.432-1", "Maria", "Lopez", "10-10-1985");
        Asignatura asignaturaToSave = new Asignatura(null, "Base de Datos", newProfesor); // ID null para una nueva entidad
        Asignatura savedAsignatura = new Asignatura(3, "Base de Datos", newProfesor); // Simula la entidad guardada con un ID

        // Define el comportamiento del servicio mock: cuando se llame a save() con cualquier Asignatura,
        // devuelve la entidad simulada ya guardada.
        when(asignaturaService.save(any(Asignatura.class))).thenReturn(savedAsignatura);

        // ACCIÓN (WHEN) & VERIFICACIÓN (THEN): Realiza la petición POST y verifica la respuesta
        mockMvc.perform(post("/api/v1/asignaturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(asignaturaToSave))) // Convierte el objeto a una cadena JSON
                .andExpect(status().isOk()) // Espera un estado HTTP 200 OK
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nombreAsignatura", is("Base de Datos")))
                .andExpect(jsonPath("$.profesor.id", is(2)));
    }
}
