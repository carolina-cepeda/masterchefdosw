package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import org.springframework.stereotype.Component;

@Component
public interface EstrategiaRegistroReceta {
  Receta registrarReceta(RecetaRequest req);
}
