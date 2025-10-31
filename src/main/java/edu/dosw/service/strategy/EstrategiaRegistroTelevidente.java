package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.RecetaTelevidente;
import org.springframework.stereotype.Component;

@Component
public class EstrategiaRegistroTelevidente implements EstrategiaRegistroReceta {
  @Override
  public RecetaTelevidente registrarReceta(RecetaRequest req) {
    return new RecetaTelevidente(
        req.getTitulo(),
        req.getListaIngredientes(),
        req.getPasosPreparacion(),
        req.getNombreChef());
  }
}
