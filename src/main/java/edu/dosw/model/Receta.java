package edu.dosw.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "recetas")
public class Receta {
  @Id private String id;
  private String titulo;
  private List<Ingrediente> listaIngredientes;
  private String pasosPreparacion;
  private String nombreChef;
  private TipoAutor tipoAutor;
  private Integer temporada;

  public Receta(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      TipoAutor tipoAutor) {
    this(titulo, listaIngredientes, pasosPreparacion, nombreChef, tipoAutor, null);
  }

  public Receta(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      TipoAutor tipoAutor,
      Integer temporada) {
    this.id = UUID.randomUUID().toString();
    this.titulo = titulo;
    this.listaIngredientes = listaIngredientes;
    this.pasosPreparacion = pasosPreparacion;
    this.nombreChef = nombreChef;
    this.tipoAutor = tipoAutor;
    this.temporada = temporada;
  }

  public Receta() {
    this.id = UUID.randomUUID().toString();
  }
}
