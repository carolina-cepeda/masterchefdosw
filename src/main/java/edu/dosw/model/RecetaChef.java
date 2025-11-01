package edu.dosw.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una receta creada por un chef profesional. Hereda todos los atributos de {@link
 * Receta} y define automáticamente el tipo de autor como {@link TipoAutor#CHEF}.
 */
@Document(collection = "recetas")
public class RecetaChef extends Receta {

  /**
   * Crea una nueva receta de chef.
   *
   * @param titulo título de la receta
   * @param listaIngredientes lista de ingredientes
   * @param pasosPreparacion pasos de preparación
   * @param nombreChef nombre del chef autor
   */
  public RecetaChef(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef) {
    super(titulo, listaIngredientes, pasosPreparacion, nombreChef, TipoAutor.CHEF);
  }

  /** Constructor vacío necesario para frameworks como Spring y deserialización. */
  public RecetaChef() {
    super();
  }
}
