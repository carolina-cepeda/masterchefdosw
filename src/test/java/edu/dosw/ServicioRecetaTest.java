package edu.dosw;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.repository.RepositorioReceta;
import edu.dosw.service.ServicioReceta;
import edu.dosw.service.strategy.EstrategiaRegistroReceta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioRecetaTest {

    private RepositorioReceta recetaRepository;
    private EstrategiaRegistroReceta estrategia;
    private ServicioReceta servicioReceta;
    private Receta receta;
    private RecetaRequest req;

    @BeforeEach
    void setUp() {
        recetaRepository = mock(RepositorioReceta.class);
        estrategia = mock(EstrategiaRegistroReceta.class);
        servicioReceta = new ServicioReceta(recetaRepository);
        servicioReceta.setEstrategia(estrategia);

        receta = new Receta();
        receta.setId("1");
        receta.setTitulo("Ajiaco");
        receta.setTipo("televidente");

        req = new RecetaRequest();
        req.setTitulo("Ajiaco");
    }

    @Test
    void registrarReceta_debeUsarEstrategiaYGuardarReceta() {
        when(estrategia.registrarReceta(req)).thenReturn(receta);

        Receta resultado = servicioReceta.registrarReceta(req);

        assertNotNull(resultado);
        assertEquals("Ajiaco", resultado.getTitulo());
        verify(estrategia, times(1)).registrarReceta(req);
        verify(recetaRepository, times(1)).guardar(receta);
    }

    @Test
    void obtenerTodas_debeRetornarListaDeRecetas() {
        when(recetaRepository.findAll()).thenReturn(List.of(receta));

        List<Receta> resultado = servicioReceta.obtenerTodas();

        assertEquals(1, resultado.size());
        verify(recetaRepository).findAll();
    }

    @Test
    void obtenerPorConsecutivo_debeRetornarReceta() {
        when(recetaRepository.findById("1")).thenReturn(receta);

        Receta resultado = servicioReceta.obtenerPorConsecutivo("1");

        assertNotNull(resultado);
        assertEquals("televidente", resultado.getTipo());
        verify(recetaRepository).findById("1");
    }

    @Test
    void buscarPorIngrediente_debeRetornarListaDeRecetas() {
        when(recetaRepository.findByIngrediente("papa")).thenReturn(List.of(receta));

        List<Receta> resultado = servicioReceta.buscarPorIngrediente("papa");

        assertFalse(resultado.isEmpty());
        assertEquals("Ajiaco", resultado.get(0).getTitulo());
        verify(recetaRepository).findByIngrediente("papa");
    }

    @Test
    void eliminar_debeInvocarRepositorio() {
        doNothing().when(recetaRepository).eliminar("1");

        servicioReceta.eliminar("1");

        verify(recetaRepository, times(1)).eliminar("1");
    }


    @Test
    void actualizar_debeActualizarCamposYGuardarReceta() {

        Receta existente = new Receta();
        existente.setTitulo("Ajiaco");
        existente.setPasosPreparacion("Original");
        existente.setNombreChef("Chef A");

        req.setTitulo("Ajiaco");
        req.setPasosPreparacion("Nuevo paso");
        req.setNombreChef("Chef B");

        when(recetaRepository.findById("Ajiaco")).thenReturn(existente);

        Receta resultado = servicioReceta.actualizar(req);

        assertNotNull(resultado);
        assertEquals("Nuevo paso", resultado.getPasosPreparacion());
        assertEquals("Chef B", resultado.getNombreChef());
        verify(recetaRepository, times(1)).findById("Ajiaco");
        verify(recetaRepository, times(1)).guardar(existente);
    }



    @Test
    void obtenerPorTipo_debeRetornarRecetasDelTipo() {
        when(recetaRepository.findByTipo("televidente")).thenReturn(List.of(receta));

        List<Receta> resultado = servicioReceta.obtenerPorTipo("televidente");

        assertFalse(resultado.isEmpty());
        assertEquals("Ajiaco", resultado.get(0).getTitulo());
        verify(recetaRepository).findByTipo("televidente");
    }

    @Test
    void obtenerPorTemporada_debeRetornarRecetasDeEsaTemporada() {
        when(recetaRepository.findByTemporada(1)).thenReturn(List.of(receta));

        List<Receta> resultado = servicioReceta.obtenerPorTemporada(1);

        assertFalse(resultado.isEmpty());
        assertEquals("Ajiaco", resultado.get(0).getTitulo());
        verify(recetaRepository).findByTemporada(1);
    }


}
