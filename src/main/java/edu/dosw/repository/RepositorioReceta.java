package edu.dosw.repository;

import edu.dosw.model.Receta;
import edu.dosw.model.TipoAutor;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de las recetas en la base de datos MongoDB. Extiende {@link
 * MongoRepository} para proporcionar operaciones CRUD y consultas personalizadas basadas en el tipo
 * de autor, ingredientes y temporada.
 */
@Repository
public interface RepositorioReceta extends MongoRepository<Receta, String> {

  /**
   * Obtiene todas las recetas de un tipo de autor específico.
   *
   * @param tipoAutor tipo de autor (CHEF, CONCURSANTE o TELEVIDENTE)
   * @return lista de recetas asociadas a ese tipo de autor
   */
  List<Receta> findByTipoAutor(TipoAutor tipoAutor);

  /**
   * Busca recetas que contengan un ingrediente cuyo nombre coincida (parcialmente, sin mayúsculas).
   *
   * @param nombreIngrediente nombre parcial o completo del ingrediente
   * @return lista de recetas que contienen ese ingrediente
   */
  @Query("{ 'listaIngredientes.nombre': { $regex: ?0, $options: 'i' } }")
  List<Receta> findByIngredienteNombre(String nombreIngrediente);

  /**
   * Obtiene las recetas creadas por concursantes en una temporada específica.
   *
   * @param temporada número de temporada
   * @return lista de recetas de concursantes en esa temporada
   */
  @Query("{ 'tipoAutor': 'CONCURSANTE', 'temporada': ?0 }")
  List<Receta> findByTemporada(int temporada);

  /**
   * Busca recetas cuyo título coincida sin distinguir mayúsculas o minúsculas.
   *
   * @param titulo título de la receta
   * @return lista de recetas con ese título
   */
  List<Receta> findByTituloIgnoreCase(String titulo);
}
