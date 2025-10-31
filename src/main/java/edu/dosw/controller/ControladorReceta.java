package edu.dosw.controller;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.model.TipoAutor;
import edu.dosw.service.ServicioReceta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recetas")
public class ControladorReceta {

  private final ServicioReceta servicio;

  public ControladorReceta(ServicioReceta servicio) {
    this.servicio = servicio;
  }

  @PostMapping("/televidente")
  public ResponseEntity<Receta> registrarRecetaTelevidente(@RequestBody RecetaRequest req) {
    Receta nueva = servicio.registrarReceta(req, TipoAutor.TELEVIDENTE);
    return ResponseEntity.ok(nueva);
  }

  @PostMapping("/concursante")
  public ResponseEntity<Receta> registrarRecetaConcursante(@RequestBody RecetaRequest req) {
    Receta nueva = servicio.registrarReceta(req, TipoAutor.CONCURSANTE);
    return ResponseEntity.ok(nueva);
  }

  @PostMapping("/chef")
  public ResponseEntity<Receta> registrarRecetaChef(@RequestBody RecetaRequest req) {
    Receta nueva = servicio.registrarReceta(req, TipoAutor.CHEF);
    return ResponseEntity.ok(nueva);
  }

  @Operation(
      summary = "Obtener todas las recetas",
      description = "Retorna la lista completa de recetas")
  @GetMapping
  public ResponseEntity<List<Receta>> getTodas() {
    return ResponseEntity.ok(servicio.obtenerTodas());
  }

  @Operation(
      summary = "Obtener receta por ID",
      description = "Busca una receta específica por su identificador único")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Receta encontrada"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
      })
  @GetMapping("/{id}")
  public ResponseEntity<Receta> getPorId(
      @Parameter(description = "ID único de la receta") @PathVariable String id) {
    return ResponseEntity.ok(servicio.obtenerPorId(id));
  }

  @Operation(summary = "Obtener recetas por tipo", description = "Filtra recetas por tipo de autor")
  @GetMapping("/tipo/{tipo}")
  public ResponseEntity<List<Receta>> getPorTipo(
      @Parameter(description = "Tipo de autor: CHEF, CONCURSANTE o TELEVIDENTE") @PathVariable
          TipoAutor tipo) { // ← Spring convierte automáticamente
    return ResponseEntity.ok(servicio.obtenerPorTipo(tipo));
  }

  @Operation(
      summary = "Obtener recetas por temporada",
      description = "Filtra recetas de concursantes por temporada")
  @GetMapping("/temporada/{temp}")
  public ResponseEntity<List<Receta>> getPorTemporada(
      @Parameter(description = "Número de temporada") @PathVariable int temp) {
    return ResponseEntity.ok(servicio.obtenerPorTemporada(temp));
  }

  @Operation(
      summary = "Buscar recetas por ingrediente",
      description = "Busca recetas que contengan un ingrediente específico")
  @GetMapping("/buscar")
  public ResponseEntity<List<Receta>> buscarPorIngrediente(
      @Parameter(description = "Nombre del ingrediente a buscar") @RequestParam String nombre) {
    return ResponseEntity.ok(servicio.buscarPorIngrediente(nombre));
  }

  @Operation(summary = "Eliminar receta", description = "Elimina una receta por su ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Receta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(
      @Parameter(description = "ID único de la receta a eliminar") @PathVariable String id) {
    servicio.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Actualizar receta",
      description = "Actualiza los datos de una receta existente")
  @PutMapping("/{id}")
  public ResponseEntity<Receta> actualizar(
      @Parameter(description = "ID único de la receta a actualizar") @PathVariable String id,
      @Parameter(description = "Nuevos datos de la receta") @RequestBody RecetaRequest req) {
    req.setId(id);
    Receta actualizada = servicio.actualizar(req);
    return ResponseEntity.ok(actualizada);
  }
}
