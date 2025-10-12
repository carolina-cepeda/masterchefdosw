package edu.dosw.model;

import java.util.List;

public class RecetaConcursante extends Receta {
    private int temporada;

    public RecetaConcursante(String titulo, List<Ingrediente> ingredientes, String pasos, String nombreChef, int temporada) {
        super(titulo, ingredientes, pasos, nombreChef, "Concursante");
        this.temporada = temporada;
    }

    public int getTemporada() { return temporada; }
    public void setTemporada(int temporada) { this.temporada = temporada; }
}
