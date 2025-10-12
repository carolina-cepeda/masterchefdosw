package edu.dosw;

import edu.dosw.controller.ControladorReceta;
import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.service.ServicioReceta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorRecetaTest {

    @Mock
    private ServicioReceta servicioReceta;

    @InjectMocks
    private ControladorReceta controladorReceta;

    private Receta receta;
    private RecetaRequest req;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        receta = new Receta();
        receta.setTitulo("Ajiaco");
        receta.setTipo("televidente");

        req = new RecetaRequest();
        req.setTitulo("Ajiaco");
    }

    @Test
    void registrarRecetaTelevidente_debeRetornarOkYReceta() {
        when(servicioReceta.registrarReceta(req)).thenReturn(receta);

        ResponseEntity<Receta> response = controladorReceta.registrarRecetaTelevidente(req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ajiaco", response.getBody().getTitulo());
        verify(servicioReceta, times(1)).registrarReceta(req);
    }

    @Test
    void registrarRecetaParticipante_debeRetornarOkYReceta() {
        when(servicioReceta.registrarReceta(req)).thenReturn(receta);

        ResponseEntity<Receta> response = controladorReceta.registrarRecetaParticipante(req);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(servicioReceta).registrarReceta(req);
    }

    @Test
    void registrarRecetaChef_debeRetornarOkYReceta() {
        when(servicioReceta.registrarReceta(req)).thenReturn(receta);

        ResponseEntity<Receta> response = controladorReceta.registrarRecetaChef(req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ajiaco", response.getBody().getTitulo());
        verify(servicioReceta).registrarReceta(req);
    }

    @Test
    void getTodas_debeRetornarListaDeRecetas() {
        when(servicioReceta.obtenerTodas()).thenReturn(List.of(receta));

        ResponseEntity<List<Receta>> response = controladorReceta.getTodas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(servicioReceta).obtenerTodas();
    }

    @Test
    void getPorConsecutivo_debeRetornarReceta() {
        when(servicioReceta.obtenerPorConsecutivo("1")).thenReturn(receta);

        ResponseEntity<Receta> response = controladorReceta.getPorConsecutivo("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ajiaco", response.getBody().getTitulo());
        verify(servicioReceta).obtenerPorConsecutivo("1");
    }

    @Test
    void getPorTipo_debeRetornarListaDeRecetas() {
        when(servicioReceta.obtenerPorTipo("televidente")).thenReturn(List.of(receta));

        ResponseEntity<List<Receta>> response = controladorReceta.getPorTipo("televidente");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(servicioReceta).obtenerPorTipo("televidente");
    }

    @Test
    void getPorTemporada_debeRetornarListaDeRecetas() {
        when(servicioReceta.obtenerPorTemporada(2)).thenReturn(List.of(receta));

        ResponseEntity<List<Receta>> response = controladorReceta.getPorTemporada(2);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(servicioReceta).obtenerPorTemporada(2);
    }

    @Test
    void buscarPorIngrediente_debeRetornarListaDeRecetas() {
        when(servicioReceta.buscarPorIngrediente("papa")).thenReturn(List.of(receta));

        ResponseEntity<List<Receta>> response = controladorReceta.buscarPorIngrediente("papa");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ajiaco", response.getBody().get(0).getTitulo());
        verify(servicioReceta).buscarPorIngrediente("papa");
    }
}
