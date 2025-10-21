package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;

public interface EstrategiaRegistroReceta {
  Receta registrarReceta(RecetaRequest req);
}
