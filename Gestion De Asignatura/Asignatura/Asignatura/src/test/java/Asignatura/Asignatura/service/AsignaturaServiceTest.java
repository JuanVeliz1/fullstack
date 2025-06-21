package Asignatura.Asignatura.service;

import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.model.Profesor;
import Asignatura.Asignatura.repository.AsignaturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Ya no necesitamos @ExtendWith(MockitoExtension.class)
import org.springframework.beans.factory.annotation.Autowired; // Necesario para inyectar beans de Spring
import org.springframework.boot.test.context.SpringBootTest; // Para cargar el contexto de Spring Boot
import org.springframework.boot.test.mock.mockito.MockBean; // Para mockear beans en el contexto de Spring

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // ¡Este es el cambio fundamental! Carga el contexto de Spring Boot.
public class AsignaturaServiceTest {

    @MockBean // Ahora usamos @MockBean para mockear el repositorio dentro del contexto de Spring
    private AsignaturaRepository repository;

    @Autowired // Ahora inyectamos el servicio, que Spring Boot habrá creado y le habrá inyectado nuestro @MockBean
    private AsignaturaService asignaturaService;

    private Profesor dummyProfesor;
    private Asignatura asignatura1;
    private Asignatura asignatura2;

    @BeforeEach
    void setUp() {
        dummyProfesor = new Profesor(1, "12.345.678-9", "Juan", "Perez", "01-01-1980");
        asignatura1 = new Asignatura(1, "Matemáticas I", dummyProfesor);
        asignatura2 = new Asignatura(2, "Programación FullStack", dummyProfesor);
    }

    @Test
    void testObtenerTodas() {
        // PREPARACIÓN (GIVEN): Define el comportamiento del repositorio mockeado por Spring
        when(repository.findAll()).thenReturn(Arrays.asList(asignatura1, asignatura2));

        // ACCIÓN (WHEN): Llama al método del servicio que estamos probando
        List<Asignatura> result = asignaturaService.obtenerTodas();

        // VERIFICACIÓN (THEN): Afirma los resultados y verifica las interacciones con el mock
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Matemáticas I", result.get(0).getNombreAsignatura());
        assertEquals("Programación FullStack", result.get(1).getNombreAsignatura());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testSaveAsignatura() {
        Profesor newProfesor = new Profesor(2, "19.765.432-1", "Maria", "Lopez", "10-10-1985");
        Asignatura asignaturaToSave = new Asignatura(null, "Base de Datos", newProfesor);
        Asignatura savedAsignatura = new Asignatura(3, "Base de Datos", newProfesor);

        when(repository.save(any(Asignatura.class))).thenReturn(savedAsignatura);

        Asignatura result = asignaturaService.save(asignaturaToSave);

        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("Base de Datos", result.getNombreAsignatura());
        assertEquals("Maria", result.getProfesor().getNombre());
        verify(repository, times(1)).save(asignaturaToSave);
    }

    @Test
    void testObtenerTodas_throwsException() {
    // PREPARACIÓN: Haz que el mock lance una excepción
    when(repository.findAll()).thenThrow(new RuntimeException("Error simulado de base de datos"));

    // ACCIÓN y VERIFICACIÓN: Espera que el servicio propague la excepción
    assertThrows(RuntimeException.class, () -> asignaturaService.obtenerTodas());

    verify(repository, times(1)).findAll();
}

} 
//Http://localhost:8080/swagger-ui/index.html  /v3/api-docs