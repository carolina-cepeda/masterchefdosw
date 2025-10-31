package edu.dosw.model;

import lombok.Data;

@Data
public class Ingrediente {
  private String nombre;
  private String descripcion;

  public Ingrediente() {}

  public Ingrediente(String nombre, String descripcion) {
    this.nombre = nombre;
    this.descripcion = descripcion;
  }
}
