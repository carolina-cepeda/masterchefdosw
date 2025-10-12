package edu.dosw.repository;

import edu.dosw.model.Receta;
import java.util.List;
@Repository
public interface RepositorioReceta {
    void guardar(Receta receta);
    List<Receta> findAll();
    List<Receta> findByTipoAutor(String tipoAutor);
    List<Receta> findByIngrediente(String ingrediente);
    void eliminar(String titulo);
}
