package edu.dosw.repository;

import edu.dosw.model.Receta;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositorioReceta {
    void guardar(Receta receta);
    Receta findById(String id);
    List<Receta> findAll();
    List<Receta> findByTipoAutor(String tipoAutor);
    List<Receta> findByIngrediente(String ingrediente);
    List <Receta> findByTemporada(int temporada);
    void eliminar(String titulo);
}
