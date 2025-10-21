package edu.dosw.service;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.repository.RepositorioReceta;
import edu.dosw.service.strategy.EstrategiaRegistroChef;
import edu.dosw.service.strategy.EstrategiaRegistroConcursante;
import edu.dosw.service.strategy.EstrategiaRegistroReceta;
import edu.dosw.service.strategy.EstrategiaRegistroTelevidente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioReceta {

  private final RepositorioReceta recetaRepository;
  private final Map<String, EstrategiaRegistroReceta> estrategias;

  @Autowired
  public ServicioReceta(
      RepositorioReceta recetaRepository,
      EstrategiaRegistroChef estrategiaChef,
      EstrategiaRegistroConcursante estrategiaConcursante,
      EstrategiaRegistroTelevidente estrategiaTelevidente) {
    this.recetaRepository = recetaRepository;

    // Inicializar estrategias
    this.estrategias = new HashMap<>();
    this.estrategias.put("chef", estrategiaChef);
    this.estrategias.put("participante", estrategiaConcursante);
    this.estrategias.put("televidente", estrategiaTelevidente);
  }

  public Receta registrarReceta(RecetaRequest req, String tipo) {
    EstrategiaRegistroReceta estrategia = estrategias.get(tipo);
    if (estrategia == null) {
      throw new RuntimeException("Tipo de receta no válido: " + tipo);
    }

    Receta receta = estrategia.registrarReceta(req);
    return recetaRepository.save(receta);
  }

  public List<Receta> obtenerTodas() {
    return recetaRepository.findAll();
  }

  public Receta obtenerPorId(String id) {
    return recetaRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + id));
  }

  public List<Receta> obtenerPorTipo(String tipo) {
    return recetaRepository.findByTipoAutor(tipo);
  }

  public List<Receta> obtenerPorTemporada(int temp) {
    return recetaRepository.findByTemporada(temp);
  }

  public List<Receta> buscarPorIngrediente(String nombre) {
    return recetaRepository.findByIngredienteNombre(nombre);
  }

  public void eliminar(String id) {
    if (!recetaRepository.existsById(id)) {
      throw new RuntimeException("Receta no encontrada con id: " + id);
    }
    recetaRepository.deleteById(id);
  }

  public Receta actualizar(RecetaRequest req) {
    Receta existente =
        recetaRepository
            .findById(req.getId())
            .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + req.getId()));

    existente.setTitulo(req.getTitulo());
    existente.setListaIngredientes(req.getListaIngredientes());
    existente.setPasosPreparacion(req.getPasosPreparacion());

    return recetaRepository.save(existente);
  }
}
