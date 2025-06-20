package Asignatura.Asignatura.service;

import Asignatura.Asignatura.model.Asignatura;
import Asignatura.Asignatura.model.Profesor;
import Asignatura.Asignatura.repository.AsignaturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AsignaturaServiceTest { // Clase del test de servicio

    @Mock // Crea un mock del repositorio
    private AsignaturaRepository repository;

    @InjectMocks // Inyecta los mocks en el servicio que queremos probar
    private AsignaturaService asignaturaService;

    private Profesor dummyProfesor;
    private Asignatura asignatura1;
    private Asignatura asignatura2;

    @BeforeEach // Se ejecuta antes de cada test
    void setUp() {
        // Inicializa los objetos de prueba
        dummyProfesor = new Profesor(1, "12.345.678-9", "Juan", "Perez", "01-01-1980");
        asignatura1 = new Asignatura(1, "Matemáticas I", dummyProfesor);
        asignatura2 = new Asignatura(2, "Programación Java", dummyProfesor);
    }

    @Test
    void testObtenerTodas() {
        // PREPARACIÓN (GIVEN): Define el comportamiento del repositorio mock
        when(repository.findAll()).thenReturn(Arrays.asList(asignatura1, asignatura2));

        // ACCIÓN (WHEN): Llama al método del servicio que estamos probando
        List<Asignatura> result = asignaturaService.obtenerTodas();

        // VERIFICACIÓN (THEN): Afirma los resultados y verifica las interacciones con el mock
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Matemáticas I", result.get(0).getNombreAsignatura());
        assertEquals("Programación Java", result.get(1).getNombreAsignatura());
        verify(repository, times(1)).findAll(); // Verifica que findAll() fue llamado exactamente una vez
    }

    @Test
    void testSaveAsignatura() {
        // PREPARACIÓN (GIVEN): Crea una asignatura para guardar y simula la que sería devuelta por el repositorio
        Profesor newProfesor = new Profesor(2, "98.765.432-1", "Maria", "Lopez", "10-10-1985");
        Asignatura asignaturaToSave = new Asignatura(null, "Base de Datos", newProfesor); // ID null para una nueva entidad
        Asignatura savedAsignatura = new Asignatura(3, "Base de Datos", newProfesor); // Simula la entidad guardada con un ID

        when(repository.save(any(Asignatura.class))).thenReturn(savedAsignatura); // Cualquier asignatura que se guarde, devuelve la simulada

        // ACCIÓN (WHEN): Llama al método save del servicio
        Asignatura result = asignaturaService.save(asignaturaToSave);

        // VERIFICACIÓN (THEN): Afirma los resultados y verifica las interacciones con el mock
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("Base de Datos", result.getNombreAsignatura());
        assertEquals("Maria", result.getProfesor().getNombre());
        verify(repository, times(1)).save(asignaturaToSave); // Verifica que save() fue llamado con el objeto correcto
    }
}