package Asignatura.Asignatura.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.model.Profesor;
import Asignatura.Asignatura.repository.AsignaturaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Importar ExtendWith
import org.mockito.InjectMocks; // Importar InjectMocks
import org.mockito.Mock; // Importar Mock
import org.mockito.junit.jupiter.MockitoExtension; // Importar MockitoExtension
import java.util.List;
import java.util.Optional;

// Esta anotación habilita la integración de Mockito con JUnit 5.
// No necesitamos @SpringBootTest aquí porque no cargamos el contexto completo de la aplicación.
@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
public class AsignaturaServiceTest {

    /// @InjectMocks se utiliza en la instancia de la clase que queremos probar.
    /// Mockito inyectará automáticamente los mocks.
    @InjectMocks
    private AsignaturaService asignaturaService;

    // @Mock se utiliza para crear objetos mockeados de las dependencias.
    // En este caso, simulamos el comportamiento de AsignaturaRepository.
    @Mock
    private AsignaturaRepository asignaturaRepository;

    @Test
    void testObtenerTodas() {
        // PREPARACIÓN (GIVEN): Define el comportamiento del mock del repositorio.
        // Creamos un objeto Profesor simulado con solo un ID, ya que solo es relevante para la relación.
        Profesor dummyProfesor = new Profesor(1, null, null, null, null);
        List<Asignatura> mockAsignaturas = List.of(new Asignatura(1, "Matemáticas", dummyProfesor));
        when(asignaturaRepository.findAll()).thenReturn(mockAsignaturas);

        // ACCIÓN (WHEN): Llama al método del servicio que estamos probando.
        List<Asignatura> resultado = asignaturaService.obtenerTodas();

        // VERIFICACIÓN (THEN): Asegúrate de que el resultado sea el esperado y que el mock fue llamado.
        assertNotNull(resultado, "La lista de asignaturas no debería ser nula.");
        assertEquals(1, resultado.size(), "Debería haber 1 asignatura en la lista.");
        assertEquals("Matemáticas", resultado.get(0).getNombreAsignatura(), "El nombre de la asignatura debería coincidir.");
        assertNotNull(resultado.get(0).getProfesor(), "El profesor de la asignatura no debería ser nulo.");
        assertEquals(1, resultado.get(0).getProfesor().getId(), "El ID del profesor debería coincidir.");

        // Verifica que el método findAll() del repositorio fue llamado exactamente una vez.
        verify(asignaturaRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        // PREPARACIÓN (GIVEN): Define la asignatura a guardar y el comportamiento del mock.
        Profesor dummyProfesor = new Profesor(2, null, null, null, null); // Otro ID de profesor para el test de save
        Asignatura asignaturaToSave = new Asignatura(null, "Programación Avanzada", dummyProfesor);
        Asignatura savedAsignatura = new Asignatura(100, "Programación Avanzada", dummyProfesor); // Simula el ID generado después de guardar

        // Cuando se llame a save con *cualquier* objeto Asignatura, devuelve nuestro objeto simulado guardado.
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(savedAsignatura);

        // ACCIÓN (WHEN): Llama al método save() del servicio.
        Asignatura resultado = asignaturaService.save(asignaturaToSave);

        // VERIFICACIÓN (THEN): Asegúrate de que el resultado sea el esperado.
        assertNotNull(resultado, "La asignatura guardada no debería ser nula.");
        assertEquals(100, resultado.getId(), "El ID de la asignatura guardada debería ser 100.");
        assertEquals("Programación Avanzada", resultado.getNombreAsignatura(), "El nombre de la asignatura guardada debería coincidir.");
        assertNotNull(resultado.getProfesor(), "El profesor de la asignatura guardada no debería ser nulo.");
        assertEquals(2, resultado.getProfesor().getId(), "El ID del profesor de la asignatura guardada debería coincidir.");

        // Verifica que el método save() del repositorio fue llamado exactamente una vez con el objeto correcto.
        verify(asignaturaRepository, times(1)).save(asignaturaToSave);
    }
}