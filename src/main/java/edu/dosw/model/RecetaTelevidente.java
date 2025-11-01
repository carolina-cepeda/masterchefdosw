package edu.dosw.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una receta enviada por un televidente del programa. Hereda de {@link Receta} y define
 * el tipo de autor como {@link TipoAutor#TELEVIDENTE}.
 */
@Document(collection = "recetas")
public class RecetaTelevidente extends Receta {

  /**
   * Crea una nueva receta enviada por un televidente.
   *
   * @param titulo título de la receta
   * @param listaIngredientes lista de ingredientes
   * @param pasosPreparacion pasos de preparación
   * @param nombreChef nombre del televidente autor
   */
  public RecetaTelevidente(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef) {
    super(titulo, listaIngredientes, pasosPreparacion, nombreChef, TipoAutor.TELEVIDENTE);
  }

  /** Constructor vacío requerido por frameworks y deserialización. */
  public RecetaTelevidente() {
    super();
  }
}
