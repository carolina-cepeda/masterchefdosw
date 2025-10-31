package edu.dosw.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recetas")
public class RecetaChef extends Receta {

  public RecetaChef(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef) {
    super(titulo, listaIngredientes, pasosPreparacion, nombreChef, TipoAutor.CHEF);
  }

  public RecetaChef() {
    super();
  }
}
