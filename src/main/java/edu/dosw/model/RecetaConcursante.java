package edu.dosw.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recetas")
public class RecetaConcursante extends Receta {

  public RecetaConcursante(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      int temporada) {
    super(
        titulo, listaIngredientes, pasosPreparacion, nombreChef, TipoAutor.CONCURSANTE, temporada);
  }

  public RecetaConcursante() {
    super();
  }
}
