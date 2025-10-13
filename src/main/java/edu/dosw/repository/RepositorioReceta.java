package edu.dosw.repository;

import edu.dosw.model.Receta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioReceta extends MongoRepository<Receta, String> {

    List<Receta> findByTipo(String tipoAutor);

    List<Receta> findByIngredientesContaining(String ingrediente);

    List<Receta> findByTemporada(int temporada);

    void deleteByTitulo(String titulo);
}