package edu.dosw.service;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.repository.RepositorioReceta;
import edu.dosw.service.strategy.EstrategiaRegistroReceta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioReceta {

  private final RepositorioReceta recetaRepository;
  private EstrategiaRegistroReceta estrategia;

  // CORREGIDO: Remover @Autowired del campo y usar solo en constructor
  @Autowired
  public ServicioReceta(RepositorioReceta recetaRepository) {
    this.recetaRepository = recetaRepository;
  }

  public void setEstrategia(EstrategiaRegistroReceta estrategia) {
    this.estrategia = estrategia;
  }

  public Receta registrarReceta(RecetaRequest req) {
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
