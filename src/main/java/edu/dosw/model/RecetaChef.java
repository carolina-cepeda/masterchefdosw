package edu.dosw.model;

import java.util.List;

public class RecetaChef extends Receta {
    public RecetaChef(String titulo, List<Ingrediente> ingredientes, String pasos, String nombreChef) {
        super(titulo, ingredientes, pasos, nombreChef, "Chef");
    }
}
