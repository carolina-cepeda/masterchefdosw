package edu.dosw.service.strategy;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.RecetaConcursante;

public class EstrategiaRegistroConcursante implements EstrategiaRegistroReceta {
  @Override
  public RecetaConcursante registrarReceta(RecetaRequest req) {
    return new RecetaConcursante(
        req.getTitulo(),
        req.getListaIngredientes(),
        req.getPasosPreparacion(),
        req.getNombreChef(),
        req.getTemporada());
  }
}
