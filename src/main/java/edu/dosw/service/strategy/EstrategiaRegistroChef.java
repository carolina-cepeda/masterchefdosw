package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.RecetaChef;
import org.springframework.stereotype.Component;

@Component
public class EstrategiaRegistroChef implements EstrategiaRegistroReceta {
  @Override
  public RecetaChef registrarReceta(RecetaRequest req) {
    return new RecetaChef(
        req.getTitulo(),
        req.getListaIngredientes(),
        req.getPasosPreparacion(),
        req.getNombreChef());
  }
}
