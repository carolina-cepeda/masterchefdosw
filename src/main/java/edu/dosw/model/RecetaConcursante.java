package edu.dosw.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una receta creada por un concursante de un programa de cocina. Hereda de {@link
 * Receta} y agrega la información de temporada.
 */
@Document(collection = "recetas")
public class RecetaConcursante extends Receta {

  /**
   * Crea una nueva receta perteneciente a un concursante.
   *
   * @param titulo título de la receta
   * @param listaIngredientes lista de ingredientes
   * @param pasosPreparacion pasos de preparación
   * @param nombreChef nombre del concursante
   * @param temporada número de temporada en la que participa
   */
  public RecetaConcursante(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      int temporada) {
    super(
        titulo, listaIngredientes, pasosPreparacion, nombreChef, TipoAutor.CONCURSANTE, temporada);
  }

  /** Constructor vacío necesario para la deserialización automática. */
  public RecetaConcursante() {
    super();
  }
}
