package edu.dosw.repository;

import edu.dosw.model.Receta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioReceta extends MongoRepository<Receta, String> {
    void guardar(Receta receta);
    Optional<Receta> findById(String id);
    List<Receta> findAll();
    List<Receta> findByTipo(String tipoAutor);
    List<Receta> findByIngrediente(String ingrediente);
    List <Receta> findByTemporada(int temporada);
    void eliminar(String titulo);
}
