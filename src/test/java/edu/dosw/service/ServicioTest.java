package edu.dosw.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Ingrediente;
import edu.dosw.model.Receta;
import edu.dosw.repository.RepositorioReceta;
import edu.dosw.service.strategy.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class ServicioRecetaTest {

  @Mock private RepositorioReceta recetaRepository;

  @Mock private EstrategiaRegistroChef estrategiaChef;
  @Mock private EstrategiaRegistroConcursante estrategiaConcursante;
  @Mock private EstrategiaRegistroTelevidente estrategiaTelevidente;

  @InjectMocks private ServicioReceta servicio;

  private RecetaRequest request;
  private Receta receta;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    request = new RecetaRequest();
    request.setTitulo("Paella");
    request.setPasosPreparacion("Cocinar arroz");
    request.setNombreChef("Juan Pérez");
    request.setTemporada(5);
    request.setListaIngredientes(List.of(new Ingrediente("Arroz", "1 taza")));

    receta = new Receta();
    receta.setId("abc123");
    receta.setTitulo("Paella");
  }

  @Test
  void registrarReceta_tipoInvalido_lanzaExcepcion() {
    assertThrows(RuntimeException.class, () -> servicio.registrarReceta(request, null));
  }

  @Test
  void obtenerPorId_existente() {
    when(recetaRepository.findById("abc123")).thenReturn(Optional.of(receta));

    Receta encontrada = servicio.obtenerPorId("abc123");
    assertEquals("Paella", encontrada.getTitulo());
  }

  @Test
  void obtenerPorId_noExiste_lanzaExcepcion() {
    when(recetaRepository.findById("xyz")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> servicio.obtenerPorId("xyz"));
  }

  @Test
  void actualizarPorId_exitoso() {
    when(recetaRepository.findById("abc123")).thenReturn(Optional.of(receta));
    when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

    Receta actualizada = servicio.actualizarPorId("abc123", request);

    assertEquals("Paella", actualizada.getTitulo());
    verify(recetaRepository).save(any(Receta.class));
  }

  @Test
  void actualizarPorId_noExiste_lanzaExcepcion() {
    when(recetaRepository.findById("xyz")).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> servicio.actualizarPorId("xyz", request));
  }

  @Test
  void eliminar_existente() {
    when(recetaRepository.existsById("abc123")).thenReturn(true);
    servicio.eliminar("abc123");
    verify(recetaRepository).deleteById("abc123");
  }

  @Test
  void eliminar_noExiste_lanzaExcepcion() {
    when(recetaRepository.existsById("xyz")).thenReturn(false);
    assertThrows(RuntimeException.class, () -> servicio.eliminar("xyz"));
  }

  @Test
  void eliminarPorTitulo_encontradas() {
    List<Receta> recetas = List.of(receta);
    when(recetaRepository.findByTituloIgnoreCase("Paella")).thenReturn(recetas);

    servicio.eliminarPorTitulo("Paella");

    verify(recetaRepository).delete(receta);
  }

  @Test
  void eliminarPorTitulo_noEncontradas() {
    when(recetaRepository.findByTituloIgnoreCase("Paella")).thenReturn(List.of());
    assertThrows(RuntimeException.class, () -> servicio.eliminarPorTitulo("Paella"));
  }

  @Test
  void actualizar_unicaReceta_exitoso() {
    when(recetaRepository.findByTituloIgnoreCase("Paella")).thenReturn(List.of(receta));
    when(recetaRepository.save(receta)).thenReturn(receta);

    Receta actualizada = servicio.actualizar(request);
    assertEquals("Paella", actualizada.getTitulo());
    verify(recetaRepository).save(receta);
  }

  @Test
  void actualizar_variasRecetas_lanzaIllegalStateException() {
    when(recetaRepository.findByTituloIgnoreCase("Paella"))
        .thenReturn(List.of(receta, new Receta()));
    assertThrows(IllegalStateException.class, () -> servicio.actualizar(request));
  }

  @Test
  void actualizar_noExiste_lanzaRuntimeException() {
    when(recetaRepository.findByTituloIgnoreCase("Paella")).thenReturn(List.of());
    assertThrows(RuntimeException.class, () -> servicio.actualizar(request));
  }
}
