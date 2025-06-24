package com.edutech.cl.edutech.cl.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.repository.ProfesorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProfesorServiceTest {

    // Inyecta el servicio de Profesor para ser probado.
    @Autowired
    private ProfesorService profesorService;

    // Crea un mock del repositorio de Profesor para simular su comportamiento.
    @MockBean
    private ProfesorRepository profesorRepository;
    
    private Profesor profesor;

    @BeforeEach
        void setUp() {


            //Creamos objeto profesor para utilizarlo en los test
            profesor = new Profesor();
            profesor.setId(1);
            profesor.setRun("123456789-0");
            profesor.setNombre("Hector");
            profesor.setApellido("Contreras");
            profesor.setFechaNacimiento("12/12/2012");
        }
    @Test
    public void testFindAll() {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con una Profesor.
        when(profesorRepository.findAll()).thenReturn(List.of(profesor));

        // Llama al método findAll() del servicio.
        List<Profesor> profesores = profesorService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente una Profesor.
        assertNotNull(profesores);
        assertEquals(1, profesores.size());
    }

    @Test
    public void testFindByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una Profesor opcional.
        when(profesorRepository.findById((long)id)).thenReturn(Optional.of(profesor));

        // Llama al método findByCodigo() del servicio.
        Profesor found = profesorService.findById(id);

        // Verifica que la Profesor devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {

        // Define el comportamiento del mock: cuando se llame a save(), devuelve la Profesor proporcionada.
        when(profesorRepository.save(profesor)).thenReturn(profesor);

        // Llama al método save() del servicio.
        Profesor saved = profesorService.save(profesor);

        // Verifica que la Profesor guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Hector", saved.getNombre());
    }

    @Test
    public void testDeleteByCodigo() {
        long id = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(profesorRepository).deleteById(id);

        // Llama al método deleteByCodigo() del servicio.
        profesorService.delete(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(profesorRepository, times(1)).deleteById(id);
    }	
}