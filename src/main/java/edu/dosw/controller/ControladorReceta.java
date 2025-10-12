package edu.dosw.controller;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.model.RecetaConcursante;
import edu.dosw.service.ServicioReceta;
import edu.dosw.service.strategy.EstrategiaRegistroChef;
import edu.dosw.service.strategy.EstrategiaRegistroConcursante;
import edu.dosw.service.strategy.EstrategiaRegistroTelevidente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")
public class ControladorReceta {

    private final ServicioReceta servicio;

    public ControladorReceta(ServicioReceta servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/televidente")
    public ResponseEntity<Receta> registrarRecetaTelevidente(@RequestBody RecetaRequest req) {
        servicio.setEstrategia(new EstrategiaRegistroTelevidente());
        Receta nueva = servicio.registrarReceta(req);
        return ResponseEntity.ok(nueva);
    }

    @PostMapping("/participante")
    public ResponseEntity<Receta> registrarRecetaParticipante(@RequestBody RecetaRequest req) {
        servicio.setEstrategia(new EstrategiaRegistroConcursante());
        Receta nueva = servicio.registrarReceta(req);
        return ResponseEntity.ok(nueva);
    }

    @PostMapping("/chef")
    public ResponseEntity<Receta> registrarRecetaChef(@RequestBody RecetaRequest req) {
        servicio.setEstrategia(new EstrategiaRegistroChef());
        Receta nueva = servicio.registrarReceta(req);
        return ResponseEntity.ok(nueva);
    }



    @GetMapping
    public ResponseEntity<List<Receta>> getTodas() {
        return ResponseEntity.ok(servicio.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> getPorConsecutivo(@PathVariable String id) {
        return ResponseEntity.ok(servicio.obtenerPorConsecutivo(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Receta>> getPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(servicio.obtenerPorTipo(tipo));
    }

    @GetMapping("/temporada/{temp}")
    public ResponseEntity<List<Receta>> getPorTemporada(@PathVariable int temp) {
        return ResponseEntity.ok(servicio.obtenerPorTemporada(temp));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Receta>> buscarPorIngrediente(@RequestParam String nombre) {
        return ResponseEntity.ok(servicio.buscarPorIngrediente(nombre));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Receta> actualizar(@RequestBody RecetaRequest req) {
        Receta actualizada = servicio.actualizar(req);
        return ResponseEntity.ok(actualizada);
    }
}
