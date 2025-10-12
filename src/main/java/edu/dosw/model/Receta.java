package edu.dosw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "recetas")
@Data
@AllArgsConstructor
public class Receta {
    private String id;
    private String titulo;
    private List<Ingrediente> listaIngredientes;
    private String pasosPreparacion;
    private String nombreChef;
    private String tipoAutor;

    public Receta(String titulo, List<Ingrediente> listaIngredientes, String pasosPreparacion, String nombreChef, String tipoAutor) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.listaIngredientes = listaIngredientes;
        this.pasosPreparacion = pasosPreparacion;
        this.nombreChef = nombreChef;
        this.tipoAutor = tipoAutor;
    }

    public Receta() {}

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
}
