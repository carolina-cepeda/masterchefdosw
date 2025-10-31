package edu.dosw.dto;

import edu.dosw.model.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * DTO utilizado para recibir los datos de una receta desde las solicitudes del usuario. No requiere
 * ID, ya que este se genera automáticamente mediante UUID.
 */
@Data
@Schema(description = "Información necesaria para registrar o actualizar una receta")
public class RecetaRequest {

  @Schema(
      description = "Título o nombre de la receta",
      example = "Paella Valenciana",
      required = true)
  private String titulo;

  @Schema(description = "Lista de ingredientes utilizados en la receta", required = true)
  private List<Ingrediente> listaIngredientes;

  @Schema(
      description = "Pasos detallados para preparar la receta",
      example = "1. Sofreír los mariscos. 2. Añadir el arroz...",
      required = true)
  private String pasosPreparacion;

  @Schema(
      description = "Nombre del autor o chef que crea la receta",
      example = "Juan Pérez",
      required = true)
  private String nombreChef;

  @Schema(
      description = "Número de temporada (solo aplica si el autor es un concursante)",
      example = "5")
  private Integer temporada;
}
