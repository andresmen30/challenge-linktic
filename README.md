# LinkTic Project

LinkTic project - Api Rest

La implementación del proyecto se llevo acabo con Java 11, junto con el framework Sprint boot, también se utiliza la estructura de Base de datos H2,
todo bajo la arquitectura hexagonal.

## Tecnologias

### Java 11

### Spring Boot

### Spring data

### JUnit

### Mockito

### Tomcat

### Base de datos H2

### Intellij

### OpenApi - Swagger

### Consumo Servicio /product

```
http://localhost:7071/product
```

### Documentación Swagger

```
localhost:7071/challenge-documentation
```

### Documentación Swagger json

```
localhost:7071/challenge-api-docs
```

## Endpoints

### GET /product

obtiene todos los productos

### GET /product/{id}

obtiene un producto por id

### POST /product

Inserta un nuevo registro

### PUT /product/{id}

Actualiza un registro

### DELETE /product/{id}

Elimina un registro

## Compilación del proyecto

##### Clonar del repositorio

```
git clone https://github.com/andresmen30/challenge-linktic.git
```

##### Compilar

Ubíquese en la ruta /challenge-linktic

```
mvn clean install
```

##### Desplegar

Ubíquese en la ruta /challenge-linktic/target y ejecute:

```
java -jar challenge-linktic-0.0.1-SNAPSHOT.jar
```















