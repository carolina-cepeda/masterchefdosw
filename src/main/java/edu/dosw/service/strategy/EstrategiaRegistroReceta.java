package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import org.springframework.stereotype.Component;

/** Interfaz para el registro de una receta */
@Component
public interface EstrategiaRegistroReceta {
  Receta registrarReceta(RecetaRequest req);
}
