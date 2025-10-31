package edu.dosw.service;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.model.TipoAutor;
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

/**
 * Servicio encargado de gestionar las operaciones relacionadas con las recetas, incluyendo
 * registro, búsqueda, actualización y eliminación.
 */
@Service
public class ServicioReceta {

  private final RepositorioReceta recetaRepository;
  private final Map<TipoAutor, EstrategiaRegistroReceta> estrategias;

  /**
   * Constructor del servicio de recetas.
   *
   * @param recetaRepository Repositorio de recetas
   * @param estrategiaChef Estrategia para recetas de chefs
   * @param estrategiaConcursante Estrategia para recetas de concursantes
   * @param estrategiaTelevidente Estrategia para recetas de televidentes
   */
  @Autowired
  public ServicioReceta(
      RepositorioReceta recetaRepository,
      EstrategiaRegistroChef estrategiaChef,
      EstrategiaRegistroConcursante estrategiaConcursante,
      EstrategiaRegistroTelevidente estrategiaTelevidente) {

    this.recetaRepository = recetaRepository;
    this.estrategias = new HashMap<>();
    this.estrategias.put(TipoAutor.CHEF, estrategiaChef);
    this.estrategias.put(TipoAutor.CONCURSANTE, estrategiaConcursante);
    this.estrategias.put(TipoAutor.TELEVIDENTE, estrategiaTelevidente);
  }

  /**
   * Registra una nueva receta sin que el usuario deba especificar el ID.
   *
   * @param req Datos de la receta
   * @param tipo Tipo de autor (CHEF, CONCURSANTE, TELEVIDENTE)
   * @return Receta registrada
   */
  public Receta registrarReceta(RecetaRequest req, TipoAutor tipo) {
    EstrategiaRegistroReceta estrategia = estrategias.get(tipo);
    if (estrategia == null) {
      throw new RuntimeException("Tipo de receta no válido: " + tipo);
    }

    Receta receta = estrategia.registrarReceta(req);
    return recetaRepository.save(receta);
  }

  /** Obtiene todas las recetas registradas. */
  public List<Receta> obtenerTodas() {
    return recetaRepository.findAll();
  }

  /** Busca una receta por su ID. */
  public Receta obtenerPorId(String id) {
    return recetaRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + id));
  }

  /** Filtra recetas por tipo de autor. */
  public List<Receta> obtenerPorTipo(TipoAutor tipo) {
    return recetaRepository.findByTipoAutor(tipo);
  }

  /** Filtra recetas de concursantes por temporada. */
  public List<Receta> obtenerPorTemporada(int temp) {
    return recetaRepository.findByTemporada(temp);
  }

  /** Busca recetas que contengan un ingrediente específico. */
  public List<Receta> buscarPorIngrediente(String nombre) {
    return recetaRepository.findByIngredienteNombre(nombre);
  }

  /** Busca recetas por título exacto. */
  public List<Receta> buscarPorTitulo(String titulo) {
    return recetaRepository.findByTituloIgnoreCase(titulo);
  }

  /** Elimina una receta por su ID. */
  public void eliminar(String id) {
    if (!recetaRepository.existsById(id)) {
      throw new RuntimeException("Receta no encontrada con id: " + id);
    }
    recetaRepository.deleteById(id);
  }

  /** Elimina una receta por su título. */
  public void eliminarPorTitulo(String titulo) {
    List<Receta> recetas = recetaRepository.findByTituloIgnoreCase(titulo);
    if (recetas.isEmpty()) {
      throw new RuntimeException("No se encontró ninguna receta con el título: " + titulo);
    }
    recetas.forEach(recetaRepository::delete);
  }

  /**
   * Actualiza los datos de una receta existente identificada por su título. Si hay varias recetas
   * con el mismo título, lanza una excepción para que se use el ID.
   */
  public Receta actualizar(RecetaRequest req) {
    List<Receta> recetas = recetaRepository.findByTituloIgnoreCase(req.getTitulo());

    if (recetas.isEmpty()) {
      throw new RuntimeException(
          "No se encontró ninguna receta registrada con el título: " + req.getTitulo());
    }

    if (recetas.size() > 1) {
      throw new IllegalStateException(
          "Se encontraron varias recetas con el título '"
              + req.getTitulo()
              + "'. Por favor use la actualización por ID (PUT /recetas/{id}).");
    }

    Receta existente = recetas.get(0);

    existente.setListaIngredientes(req.getListaIngredientes());
    existente.setPasosPreparacion(req.getPasosPreparacion());
    existente.setNombreChef(req.getNombreChef());
    existente.setTemporada(req.getTemporada());

    return recetaRepository.save(existente);
  }

  /** Actualiza una receta buscando por su ID. */
  public Receta actualizarPorId(String id, RecetaRequest req) {
    Receta existente =
        recetaRepository
            .findById(id)
            .orElseThrow(
                () -> new RuntimeException("No se encontró ninguna receta con el ID: " + id));

    existente.setTitulo(req.getTitulo());
    existente.setListaIngredientes(req.getListaIngredientes());
    existente.setPasosPreparacion(req.getPasosPreparacion());
    existente.setNombreChef(req.getNombreChef());
    existente.setTemporada(req.getTemporada());

    return recetaRepository.save(existente);
  }
}
