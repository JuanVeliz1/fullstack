package com.edutech.cl.edutech.cl.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.edutech.cl.edutech.cl.model.Soporte;

import com.edutech.cl.edutech.cl.repository.SoporteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SoporteServiceTest {

    // Inyecta el servicio de Soporte para ser probado.
    @Autowired
    private SoporteService soporteService;

    // Crea un mock del repositorio de Soporte para simular su comportamiento.
    @MockBean
    private SoporteRepository soporteRepository;
    
    private Soporte soporte;

    @BeforeEach
        void setUp() {

            //Creamos objeto soporte para utilizarlo en los test
            
            soporte = new Soporte();
            soporte.setId(1);
            soporte.setMensajeSoporte("Ok");
        }
    @Test
    public void testFindAll() {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con una Soporte.
        when(soporteRepository.findAll()).thenReturn(List.of(soporte));

        // Llama al método findAll() del servicio.
        List<Soporte> soportes = soporteService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente una Soporte.
        assertNotNull(soportes);
        assertEquals(1, soportes.size());
    }

    @Test
    public void testFindByCodigo() {
        int id = 1;

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una Soporte opcional.
        when(soporteRepository.findById((long)id)).thenReturn(Optional.of(soporte));

        // Llama al método findByCodigo() del servicio.
        Soporte found = soporteService.findById(id);

        // Verifica que la Soporte devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {

        // Define el comportamiento del mock: cuando se llame a save(), devuelve la Soporte proporcionada.
        when(soporteRepository.save(soporte)).thenReturn(soporte);

        // Llama al método save() del servicio.
        Soporte saved = soporteService.save(soporte);

        // Verifica que la Soporte guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Hector", saved.getId());
    }

}



