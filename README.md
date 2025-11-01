# MASTER CHEF CELEBRITY


## Descripción general
Este proyecto es una aplicación desarrollada basada en master chef celebrity en **Java** con **Spring Boot** que permite registrar, consultar, actualizar y eliminar recetas de distintos tipos de usuarios:
- **Televidentes**
- **Participantes**
- **Chefs**

---

## Tecnologías utilizadas
- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Maven**
- **IntelliJ IDEA**
- **Azure**

---

## Estructura del proyecto
![img.png](docs/img/estructura.png)
____
## Diseño
___
## **Diagrama de casos de uso**
A continuación se muestran los casos de uso para los actores identificados, se muestra de forma general
que los actores pueden hacer las mismas acciones como lo son eliminar, consultar y registrar o actualizar una receta.

![img.png](docs/UML/diagramaCasosUso.png)


## Diagrama de clases

El siguiente diagrama representa las clases del sistema, en el cual se usa el patrón Strategy
para el registro de recetas según el tipo de usuario y manejando una arquitectura Controller -> Service -> repository.

Las clases principales son:
* **ControladorReceta**: gestiona las solicitudes externas desde una API y devuelve las respuestas mediante ResponseEntity.

* **ServicioReceta**:Contiene la lógica de negocio del sistema., teniendo conexión a un RepositorioReceta (acceso a datos) y a una EstrategiaRegistroReceta,
implementando los metodos CRUD.

* **RepositorioReceta (interfaz):** permite buscar recetas por título, ingrediente o tipo, obtener las más populares, y actualizar la información.


* **EstrategiaRegistroReceta (interfaz):** Define el método registrarReceta, que las subclases concretas implementan de acuerdo al tipo de usuario. Las
estrategias específicas son:EstrategiaRegistroChef, EstrategiaRegistroConcursante y EstrategiaRegistroTelevidente. Se maneja el uso del patrón Strategy que
permite crear cada receta de forma sencilla sin que el usuario tenga que buscar específicamente un método para ello.


* **Receta**: clase base con atributos comunes: título, listaIngredientes,tipoAutor, pasosPreparacion y nombreChef.En el caso de tipoAutor solo se tienen
* 3 opciones, como mencionadas anteriormente, por ello se hace uso de un ENUM para evitar el uso de diferentes tipos de autores a los establecidos.
![img.png](docs/UML/diagramaClases.png)


## Diagrama bases de datos NOSQL

A continuación se muestra el diagrama de la base de datos NOSQL, el documento recetas es el contenedor prinicpal
en donde se incluyen todos los campos, aplicando una estructura embebida se guardan los ingredientes como una lista de objetos, los cuales
tienen sus propios campos (nombre, descripción). Se tienen campos opcionales como lo es la temporada, ya que solo se
guarda si la receta es de un concursante.
![img.png](docs/UML/diagramaBD.png)

## Diagramas de secuencia

###  **Registrar recetas**
Con base en la estrategia
específica de cada actor se construye una Entidad (RecetaTelevidente, RecetaConcursante, RecetaJurado), se guarda vía 
repositorio en la base de datos y se devuelve la información.

* **Receta Televidente**

![img.png](img.png)

* **Receta Concursante**
![img_1.png](docs/UML/img_1.png)

* **Receta Jurado**

![img_2.png](docs/UML/img_2.png)

### **Obtener Recetas**
Se tienen varios métodos de consulta de recetas:

* **obtener todas las recetas**
Se hace uso de una función findAll() en la colección de recetas y se devuelve la lista de estas.
![img.png](docs/UML/obtenerTodasRecetas.png)

* **Obtener receta por su ID**

Se hace la búsqueda del id dentro de la colección de recetas, en caso de encontrarla se devuelve la información
de esta receta.
![img_1.png](docs/UML/obtenerRecetaPorID.png)

* **Obtener receta por su título**
De forma similar a la consulta por identificador, se busca en la colección de recetas y se devuelve la lista de ellas .
![img_3.png](docs/UML/img_3.png)
* **Obtener receta por tipo (concursante, televidente, jurado)**
Se hace un filtro por el tipo de Receta en la base de datos.Esto se maneja con un ENUM por lo que a la hora de aplicación se
muestra como una lista de opciones, por ejemplo, si se buscan las recetas de los concursantes.

![img_2.png](docs/UML/obtenerRecetaconcursante.png)


* **Obtener Receta por Temporada**
Al darse una temporada, se buscan todos los registros guardados que coincidan en el campo de la temporada y son devueltos.
![img_2.png](docs/UML/obtenerRecetaTemporada.png)

* **Obtener Receta por ingrediente**
Se realiza una consulta sobre la lista de ingredientes de cada una de las recetas presentes en la base de datos.
![img.png](docs/UML/obtenerRecetaIngrediente.png)


### Eliminar una receta 
* **Eliminar receta por ID**
Se elimina una receta con su identificador (id) en caso de existir.
![img.png](docs/UML/eliminarReceta.png)


* **Eliminar receta por titulo**
* Se elimina una receta con base en su título, pero sí existe más de una receta con el mismo título, se le recomienda
el uso de la funcionalidad de eliminar receta por ID.

![img.png](docs/UML/eliminarRecetaPorTitulo.png)

### Actualizar una receta

* una receta puede actualizarse con su ID
Si la receta existe, se hace la actualización de los campos especificados por el actor usando su id (identificador)
![img_2.png](img_2.png)


## Instrucciones de instalación y ejecución

### Ejemplos de solicitudes y respuestas

### Swagger UI
![img_12.png](img_12.png)
SwaggerUI es una herramienta que nos permite simplificar el desarrollo de la API REST.Esto se debe a que
nos permite visualizar e interactuar con esta.

A continuación se muestran algunos ejemplos de uso de los ENDPOINTS, con su solicitud(request) y
respuesta.

* **Registro de recetas**
Se registran por cada tipo como se observa a continuación:

* receta de un televidente
![img_3.png](img_3.png)
![img_5.png](img_5.png)

  
* **Consulta de recetas**

* consulta por titulo
![img_4.png](img_4.png)
* consulta por identificador
* ![img_6.png](img_6.png)

* todas las recetas
![img_7.png](img_7.png)
* por tipo
![img_8.png](img_8.png)
![img_9.png](img_9.png)

* por temporada: como se observa a continuación sólo se muestran recetas de concursantes, en esa
temporada específica.
![img_10.png](img_10.png)
![img_11.png](img_11.png)

* por ingrediente:
![img_13.png](img_13.png)

* **Actualización de recetas**

* por titulo
![img_16.png](img_16.png)
![img_17.png](img_17.png)
* por id
![img_18.png](img_18.png)
![img_19.png](img_19.png)
* **Eliminación de recetas**

* por titulo
![img_15.png](img_15.png)
* por ID
![img_14.png](img_14.png)

### Uso de Docker para conectar BD mongo

Se hace el uso del siguiente comando para crear un contenedor de la base de datos.
docker run -d --name masterchef-mongo -p 27017:27017 -v mongo_data:/data/db -e MONGO_INITDB_DATABASE=masterchef mongo:7.0
