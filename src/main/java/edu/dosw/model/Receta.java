package edu.dosw.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una receta culinaria almacenada en la base de datos MongoDB. Cada receta contiene un
 * identificador único, un conjunto de ingredientes, pasos de preparación, información sobre el
 * autor y, opcionalmente, la temporada en que fue creada si pertenece a un concursante.
 *
 * <p>Esta clase es la base para las subclases {@link RecetaChef}, {@link RecetaConcursante} y
 * {@link RecetaTelevidente}.
 */
@Data
@AllArgsConstructor
@Document(collection = "recetas")
public class Receta {
  /** Identificador único generado automáticamente. */
  @Id private String id;

  /** Título o nombre de la receta. */
  private String titulo;

  /** Lista de ingredientes que componen la receta. */
  private List<Ingrediente> listaIngredientes;

  /** Descripción detallada de los pasos para preparar la receta. */
  private String pasosPreparacion;

  /** Nombre del chef o autor que registró la receta. */
  private String nombreChef;

  /** Tipo de autor (CHEF, CONCURSANTE o TELEVIDENTE). */
  private TipoAutor tipoAutor;

  /** Temporada en la que participó el concursante (solo aplica a CONCURSANTE). */
  private Integer temporada;

  /**
   * Crea una receta sin temporada, comúnmente utilizada para CHEF o TELEVIDENTE.
   *
   * @param titulo título de la receta
   * @param listaIngredientes ingredientes utilizados
   * @param pasosPreparacion pasos de preparación
   * @param nombreChef nombre del autor
   * @param tipoAutor tipo de autor de la receta
   */
  public Receta(
      String titulo,
      List<Ingrediente> listaIngredientes,
      String pasosPreparacion,
      String nombreChef,
      TipoAutor tipoAutor) {
    this(titulo, listaIngredientes, pasosPreparacion, nombreChef, tipoAutor, null);
  }

  /**
   * Crea una receta con temporada, útil para recetas de concursantes.
   *
   * @param titulo título de la receta
   * @param listaIngredientes lista de ingredientes
   * @param pasosPreparacion pasos de preparación
   * @param nombreChef nombre del autor
   * @param tipoAutor tipo de autor
   * @param temporada número de temporada (opcional)
   */
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

  /** Constructor vacío que genera automáticamente un ID único. */
  public Receta() {
    this.id = UUID.randomUUID().toString();
  }
}
