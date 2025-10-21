package edu.dosw.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recetas")
@Data
@AllArgsConstructor
public class Receta {
  private String id;
  private String titulo;
  private List<Ingrediente> listaIngredientes;
  private String pasosPreparacion;
  private String nombreChef;
  private String tipoAutor;
  private Integer temporada;

  public Receta(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      String tipoAutor) {
    this(titulo, listaIngredientes, pasosPreparacion, nombreChef, tipoAutor, null);
  }

  public Receta(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      String tipoAutor,
      Integer temporada) {
    this.id = UUID.randomUUID().toString();
    this.titulo = titulo;
    this.listaIngredientes = listaIngredientes;
    this.pasosPreparacion = pasosPreparacion;
    this.nombreChef = nombreChef;
    this.tipoAutor = tipoAutor;
    this.temporada = temporada;
  }
}
