package edu.dosw;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Ingrediente;
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
        verify(recetaRepository, times(1)).save(receta);
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
        Receta receta = new Receta();
        receta.setId("1");
        receta.setTipo("televidente");

        when(recetaRepository.findById("1")).thenReturn(Optional.of(receta));

        Receta resultado = servicioReceta.obtenerPorConsecutivo("1");

        assertNotNull(resultado);
        assertEquals("televidente", resultado.getTipo());
        verify(recetaRepository).findById("1");
    }



    @Test
    void buscarPorIngrediente_debeRetornarListaDeRecetas() {
        when(recetaRepository.findByIngredientesContaining("papa")).thenReturn(List.of(receta));

        List<Receta> resultado = servicioReceta.buscarPorIngrediente("papa");

        assertFalse(resultado.isEmpty());
        assertEquals("Ajiaco", resultado.get(0).getTitulo());
        verify(recetaRepository).findByIngredientesContaining("papa");
    }

    @Test
    void eliminar_debeInvocarRepositorio() {
        doNothing().when(recetaRepository).deleteByTitulo("Ajiaco");

        servicioReceta.eliminar("Ajiaco");

        verify(recetaRepository, times(1)).deleteByTitulo("Ajiaco");
    }


    @Test
    void actualizar_debeActualizarCamposYGuardarReceta() {
        // Arrange
        Receta existente = new Receta();
        existente.setId("1");
        existente.setTitulo("Antigua");
        existente.setListaIngredientes(List.of(
                new Ingrediente("Huevos", "Frescos"),
                new Ingrediente("Leche", "Entera")
        ));
        existente.setPasosPreparacion("Batir todo");

        RecetaRequest req = new RecetaRequest();
        req.setId("1");
        req.setTitulo("Nueva receta");
        req.setListaIngredientes(List.of(
                new Ingrediente("Harina", "De trigo"),
                new Ingrediente("Azúcar", "Refinada")
        ));
        req.setPasosPreparacion("Mezclar bien");

        when(recetaRepository.findById("1")).thenReturn(Optional.of(existente));
        when(recetaRepository.save(any(Receta.class))).thenAnswer(inv -> inv.getArgument(0));

        Receta resultado = servicioReceta.actualizar(req);

        assertNotNull(resultado);
        assertEquals("Nueva receta", resultado.getTitulo());
        assertEquals(2, resultado.getListaIngredientes().size());
        assertEquals("Harina", resultado.getListaIngredientes().get(0).getNombre());
        verify(recetaRepository).findById("1");
        verify(recetaRepository).save(any(Receta.class));
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

    @Test
    void actualizarDebeLanzarExcepcionCuandoNoExisteReceta() {
        RecetaRequest req = new RecetaRequest();
        req.setTitulo("Torta de chocolate");
        when(recetaRepository.findById("99")).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> servicioReceta.actualizar(req));
        assertEquals("Receta no encontrada", ex.getMessage());

        verify(recetaRepository, never()).save(any());
    }


}
