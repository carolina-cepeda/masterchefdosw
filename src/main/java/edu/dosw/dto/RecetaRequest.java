package edu.dosw.dto;

import edu.dosw.model.Ingrediente;
import java.util.List;

public class RecetaRequest {
    private String titulo;
    private List<Ingrediente> listaIngredientes;
    private String pasosPreparacion;
    private String nombreChef;
    private String tipoAutor;
    private Integer temporada;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<Ingrediente> getListaIngredientes() { return listaIngredientes; }
    public void setListaIngredientes(List<Ingrediente> listaIngredientes) { this.listaIngredientes = listaIngredientes; }

    public String getPasosPreparacion() { return pasosPreparacion; }
    public void setPasosPreparacion(String pasosPreparacion) { this.pasosPreparacion = pasosPreparacion; }

    public String getNombreChef() { return nombreChef; }
    public void setNombreChef(String nombreChef) { this.nombreChef = nombreChef; }

    public String getTipoAutor() { return tipoAutor; }
    public void setTipoAutor(String tipoAutor) { this.tipoAutor = tipoAutor; }

    public Integer getTemporada() { return temporada; }
    public void setTemporada(Integer temporada) { this.temporada = temporada; }
}
