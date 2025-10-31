package edu.dosw.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Ingrediente;
import edu.dosw.model.Receta;
import edu.dosw.model.TipoAutor;
import edu.dosw.service.ServicioReceta;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ControladorReceta.class)
class ControladorRecetaTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ServicioReceta servicio;

  @Autowired private ObjectMapper mapper;

  private Receta receta;
  private RecetaRequest request;

  @BeforeEach
  void setup() {
    receta = new Receta();
    receta.setId("abc123");
    receta.setTitulo("Paella");

    request = new RecetaRequest();
    request.setTitulo("Paella");
    request.setListaIngredientes(List.of(new Ingrediente("Arroz", "1 taza")));
    request.setPasosPreparacion("Cocinar arroz");
    request.setNombreChef("Juan Pérez");
  }

  @Test
  void registrarRecetaTelevidente_devuelveOk() throws Exception {
    when(servicio.registrarReceta(any(), eq(TipoAutor.TELEVIDENTE))).thenReturn(receta);

    mockMvc
        .perform(
            post("/recetas/televidente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Paella"));
  }

  @Test
  void getPorId_devuelveReceta() throws Exception {
    when(servicio.obtenerPorId("abc123")).thenReturn(receta);

    mockMvc
        .perform(get("/recetas/abc123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Paella"));
  }

  @Test
  void getPorTipo_devuelveLista() throws Exception {
    when(servicio.obtenerPorTipo(TipoAutor.CHEF)).thenReturn(List.of(receta));

    mockMvc
        .perform(get("/recetas/tipo/CHEF"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].titulo").value("Paella"));
  }

  @Test
  void eliminarPorId_devuelveNoContent() throws Exception {
    doNothing().when(servicio).eliminar("abc123");

    mockMvc.perform(delete("/recetas/abc123")).andExpect(status().isNoContent());
  }

  @Test
  void actualizarPorTitulo_conflicto() throws Exception {
    doThrow(new IllegalStateException("Existen varias recetas"))
        .when(servicio)
        .actualizar(any(RecetaRequest.class));

    mockMvc
        .perform(
            put("/recetas/titulo/Paella")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
        .andExpect(status().isConflict());
  }

  @Test
  void actualizarPorId_exitoso() throws Exception {
    when(servicio.actualizarPorId(eq("abc123"), any(RecetaRequest.class))).thenReturn(receta);

    mockMvc
        .perform(
            put("/recetas/abc123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Paella"));
  }
    @Test
    void registrarRecetaConcursante_devuelveOk() throws Exception {
        when(servicio.registrarReceta(any(), eq(TipoAutor.CONCURSANTE))).thenReturn(receta);

        mockMvc.perform(post("/recetas/concursante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Paella"));
    }

    @Test
    void registrarRecetaChef_devuelveOk() throws Exception {
        when(servicio.registrarReceta(any(), eq(TipoAutor.CHEF))).thenReturn(receta);

        mockMvc.perform(post("/recetas/chef")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Paella"));
    }

    @Test
    void actualizarPorTitulo_exitoso() throws Exception {
        when(servicio.actualizar(any(RecetaRequest.class))).thenReturn(receta);

        mockMvc.perform(put("/recetas/titulo/Paella")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Paella"));
    }

    @Test
    void actualizarPorTitulo_notFound() throws Exception {
        doThrow(new RuntimeException("No encontrada")).when(servicio).actualizar(any(RecetaRequest.class));

        mockMvc.perform(put("/recetas/titulo/Paella")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarPorId_notFound() throws Exception {
        doThrow(new RuntimeException("No se encontró")).when(servicio).actualizarPorId(eq("abc123"), any(RecetaRequest.class));

        mockMvc.perform(put("/recetas/abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPorTitulo_devuelveNoContent() throws Exception {
        doNothing().when(servicio).eliminarPorTitulo("Paella");

        mockMvc.perform(delete("/recetas/titulo")
                        .param("titulo", "Paella"))
                .andExpect(status().isNoContent());

        verify(servicio).eliminarPorTitulo("Paella");
    }

}
