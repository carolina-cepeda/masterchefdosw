package edu.dosw.model;

import java.util.List;

public class RecetaTelevidente extends Receta {
    public RecetaTelevidente(String titulo, List<Ingrediente> ingredientes, String pasos, String nombreChef) {
        super(titulo, ingredientes, pasos, nombreChef, "Televidente");
    }
}
