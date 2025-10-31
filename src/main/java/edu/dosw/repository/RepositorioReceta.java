package edu.dosw.repository;

import edu.dosw.model.Receta;
import edu.dosw.model.TipoAutor;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioReceta extends MongoRepository<Receta, String> {

  List<Receta> findByTipoAutor(TipoAutor tipoAutor);

  @Query("{ 'listaIngredientes.nombre': { $regex: ?0, $options: 'i' } }")
  List<Receta> findByIngredienteNombre(String nombreIngrediente);

  @Query("{ 'tipoAutor': 'CONCURSANTE', 'temporada': ?0 }")
  List<Receta> findByTemporada(int temporada);

  List<Receta> findByTituloIgnoreCase(String titulo);
}
