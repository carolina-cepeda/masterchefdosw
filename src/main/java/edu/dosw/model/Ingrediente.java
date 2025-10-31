package edu.dosw.model;

import lombok.Data;

/**
 * Representa un ingrediente utilizado dentro de una receta. Contiene información básica como el
 * nombre y una descripción del ingrediente.
 */
@Data
public class Ingrediente {
  /** Nombre del ingrediente. */
  private String nombre;

  /** Descripción o detalles adicionales del ingrediente. */
  private String descripcion;

  /** Constructor vacío necesario para la deserialización y frameworks como Spring. */
  public Ingrediente() {}

  /**
   * Crea un nuevo ingrediente con el nombre y descripción especificados.
   *
   * @param nombre nombre del ingrediente
   * @param descripcion descripción del ingrediente
   */
  public Ingrediente(String nombre, String descripcion) {
    this.nombre = nombre;
    this.descripcion = descripcion;
  }
}
