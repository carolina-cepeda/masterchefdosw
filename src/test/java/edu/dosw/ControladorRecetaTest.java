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
    }

    @Test
    void getTodas_debeRetornarListaDeRecetas() {
        when(servicioReceta.obtenerTodas()).thenReturn(List.of(receta));

        ResponseEntity<List<Receta>> response = controladorReceta.getTodas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void eliminar_debeRetornarNoContent() {
        doNothing().when(servicioReceta).eliminar("1");

        ResponseEntity<Void> response = controladorReceta.eliminar("1");

        assertEquals(204, response.getStatusCodeValue());
        verify(servicioReceta).eliminar("1");
    }
}
