package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.RecetaTelevidente;

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
