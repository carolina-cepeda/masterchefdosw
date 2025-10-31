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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las recetas de chefs, concursantes y televidentes. Proporciona
 * endpoints para registrar, consultar, buscar, actualizar y eliminar recetas. Especialmente para
 * nuestros fans del programa masterChef
 */
@RestController
@RequestMapping("/recetas")
public class ControladorReceta {

  private final ServicioReceta servicio;

  public ControladorReceta(ServicioReceta servicio) {
    this.servicio = servicio;
  }

  @Operation(
      summary = "Registrar receta de televidente",
      description =
          "Permite que un televidente registre una receta nueva. El sistema genera el ID automáticamente.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Receta registrada correctamente"),
    @ApiResponse(
        responseCode = "400",
        description = "Faltan datos obligatorios o el formato es incorrecto")
  })
  @PostMapping("/televidente")
  public ResponseEntity<Receta> registrarRecetaTelevidente(@RequestBody RecetaRequest req) {
    Receta nueva = servicio.registrarReceta(req, TipoAutor.TELEVIDENTE);
    return ResponseEntity.ok(nueva);
  }

  @Operation(
      summary = "Registrar receta de concursante",
      description = "Permite que un concursante registre una receta asociada a una temporada.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Receta registrada correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos incompletos o temporada inválida")
  })
  @PostMapping("/concursante")
  public ResponseEntity<Receta> registrarRecetaConcursante(@RequestBody RecetaRequest req) {
    Receta nueva = servicio.registrarReceta(req, TipoAutor.CONCURSANTE);
    return ResponseEntity.ok(nueva);
  }

  @Operation(
      summary = "Registrar receta de chef",
      description = "Permite a un chef profesional registrar una nueva receta.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Receta registrada correctamente"),
    @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
  })
  @PostMapping("/chef")
  public ResponseEntity<Receta> registrarRecetaChef(@RequestBody RecetaRequest req) {
    Receta nueva = servicio.registrarReceta(req, TipoAutor.CHEF);
    return ResponseEntity.ok(nueva);
  }

  @Operation(
      summary = "Obtener todas las recetas",
      description = "Muestra una lista completa de todas las recetas disponibles.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de recetas obtenida correctamente")
  })
  @GetMapping
  public ResponseEntity<List<Receta>> getTodas() {
    return ResponseEntity.ok(servicio.obtenerTodas());
  }

  @Operation(
      summary = "Buscar receta por ID",
      description = "Permite obtener una receta específica ingresando su identificador único.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Receta encontrada correctamente"),
    @ApiResponse(responseCode = "404", description = "No existe una receta con el ID proporcionado")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Receta> getPorId(
      @Parameter(description = "Identificador único de la receta") @PathVariable String id) {
    return ResponseEntity.ok(servicio.obtenerPorId(id));
  }

  @Operation(
      summary = "Filtrar recetas por tipo de autor",
      description =
          "Muestra las recetas según si fueron creadas por un CHEF, CONCURSANTE o TELEVIDENTE.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado filtrado correctamente"),
    @ApiResponse(responseCode = "400", description = "Tipo de autor no válido")
  })
  @GetMapping("/tipo/{tipo}")
  public ResponseEntity<List<Receta>> getPorTipo(@PathVariable TipoAutor tipo) {
    return ResponseEntity.ok(servicio.obtenerPorTipo(tipo));
  }

  @Operation(
      summary = "Obtener recetas por temporada",
      description = "Filtra las recetas realizadas por concursantes de una temporada específica.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
    @ApiResponse(
        responseCode = "404",
        description = "No existen recetas registradas en esa temporada")
  })
  @GetMapping("/temporada/{temp}")
  public ResponseEntity<List<Receta>> getPorTemporada(@PathVariable int temp) {
    return ResponseEntity.ok(servicio.obtenerPorTemporada(temp));
  }

  @Operation(
      summary = "Buscar recetas por ingrediente",
      description = "Devuelve las recetas que contienen un ingrediente específico.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Recetas encontradas correctamente"),
    @ApiResponse(
        responseCode = "404",
        description = "No se encontraron recetas con ese ingrediente")
  })
  @GetMapping("/buscar")
  public ResponseEntity<List<Receta>> buscarPorIngrediente(@RequestParam String nombre) {
    return ResponseEntity.ok(servicio.buscarPorIngrediente(nombre));
  }

  @Operation(
      summary = "Buscar recetas por título",
      description =
          "Busca recetas ingresando el título exacto o parcial, sin importar mayúsculas/minúsculas.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Recetas encontradas correctamente"),
    @ApiResponse(responseCode = "404", description = "No se encontró ninguna receta con ese título")
  })
  @GetMapping("/titulo")
  public ResponseEntity<List<Receta>> buscarPorTitulo(
      @Parameter(description = "Título de la receta a buscar") @RequestParam String titulo) {
    return ResponseEntity.ok(servicio.buscarPorTitulo(titulo));
  }

  @Operation(
      summary = "Eliminar receta por ID",
      description = "Permite eliminar una receta específica mediante su identificador.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Receta eliminada correctamente"),
    @ApiResponse(responseCode = "404", description = "No se encontró la receta con el ID indicado")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(
      @Parameter(description = "Identificador único de la receta a eliminar") @PathVariable
          String id) {
    servicio.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Eliminar receta por título",
      description =
          "Elimina todas las recetas que tengan el título indicado (sin importar mayúsculas/minúsculas).")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Receta(s) eliminada(s) correctamente"),
    @ApiResponse(responseCode = "404", description = "No se encontró ninguna receta con ese título")
  })
  @DeleteMapping("/titulo")
  public ResponseEntity<Void> eliminarPorTitulo(
      @Parameter(description = "Título exacto de la receta a eliminar") @RequestParam
          String titulo) {
    servicio.eliminarPorTitulo(titulo);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/titulo/{titulo}")
  @Operation(summary = "Actualiza una receta existente usando su título")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "La receta fue actualizada correctamente"),
    @ApiResponse(responseCode = "404", description = "No se encontró una receta con ese título"),
    @ApiResponse(
        responseCode = "409",
        description = "Existen varias recetas con el mismo título. Use el ID para actualizar."),
    @ApiResponse(
        responseCode = "400",
        description = "Error en los datos enviados. Revise los campos requeridos.")
  })
  public ResponseEntity<?> actualizarPorTitulo(
      @Parameter(description = "Título exacto de la receta que se desea actualizar") @PathVariable
          String titulo,
      @RequestBody RecetaRequest req) {

    try {
      req.setTitulo(titulo);
      Receta actualizada = servicio.actualizar(req);
      return ResponseEntity.ok(actualizada);
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(e.getMessage() + " Ejemplo: PUT /recetas/{id}");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualiza una receta existente usando su identificador único (ID)")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "La receta fue actualizada correctamente"),
    @ApiResponse(responseCode = "404", description = "No se encontró una receta con ese ID"),
    @ApiResponse(
        responseCode = "400",
        description = "Error en los datos enviados. Revise los campos requeridos.")
  })
  public ResponseEntity<?> actualizarPorId(
      @Parameter(description = "Identificador único de la receta") @PathVariable String id,
      @RequestBody RecetaRequest req) {

    try {
      Receta actualizada = servicio.actualizarPorId(id, req);
      return ResponseEntity.ok(actualizada);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
