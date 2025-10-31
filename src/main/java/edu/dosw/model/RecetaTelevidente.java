package edu.dosw.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recetas")
public class RecetaTelevidente extends Receta {
  public RecetaTelevidente(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef) {
    super(titulo, listaIngredientes, pasosPreparacion, nombreChef, TipoAutor.TELEVIDENTE);
  }

  public RecetaTelevidente() {
    super();
  }
}
