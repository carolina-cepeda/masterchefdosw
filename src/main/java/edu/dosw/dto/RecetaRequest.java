package edu.dosw.dto;

import edu.dosw.model.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "DTO para solicitud de receta")
public class RecetaRequest {

  @Schema(
      description = "ID único de la receta (solo para actualizaciones)",
      example = "550e8400-e29b-41d4-a716-446655440000")
  private String id;

  @Schema(description = "Título de la receta", example = "Paella Valenciana", required = true)
  private String titulo;

  @Schema(description = "Lista de ingredientes", required = true)
  private List<Ingrediente> listaIngredientes;

  @Schema(
      description = "Pasos de preparación",
      example = "1. Sofreír... 2. Añadir...",
      required = true)
  private String pasosPreparacion;

  @Schema(description = "Nombre del chef/autor", example = "Juan Pérez", required = true)
  private String nombreChef;

  @Schema(description = "Temporada (solo para concursantes)", example = "5")
  private Integer temporada;
}
