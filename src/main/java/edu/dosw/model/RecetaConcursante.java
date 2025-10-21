package edu.dosw.model;

import java.util.List;

public class RecetaConcursante extends Receta {
  private int temporada;

  public RecetaConcursante(
      String titulo,
      List<Ingrediente> ingredientes,
      String pasos,
      String nombreChef,
      int temporada) {
    super(titulo, ingredientes, pasos, nombreChef, "Concursante");
    this.temporada = temporada;
  }

  @Override
  public Integer getTemporada() {
    return temporada;
  }

  public void setTemporada(int temporada) {
    this.temporada = temporada;
  }

  @Override
  public boolean equals(Object o) {
    return this == o;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
