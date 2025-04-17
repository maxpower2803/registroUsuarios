# Registro

Proyecto de ejemplo para el registro de usuarios utilizando Spring Boot, Hibernate, H2 y autenticación con JWT.

## Requisitos previos

- JDK 17 o superior
- Maven 3.8+
- IDE (recomendado: Eclipse o IntelliJ)

## Configuración

Este proyecto utiliza una base de datos en memoria (H2) y parámetros de configuración definidos en `application.properties`.

Ubicación del archivo:
```
src/main/resources/application.properties
```

Parámetros relevantes:

```properties
# JWT
jwt.secret=mysecretkey
jwt.expiration=3600000

# Base de datos H2
spring.datasource.url=jdbc:h2:mem:registrodb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Ejecución del proyecto

### Desde línea de comandos

```bash
mvn spring-boot:run
```

## Endpoints de ejemplo

- `POST`  - (http://localhost:8080/api/registro) – Registro de nuevo usuario`

## Ejemplo con JSON

### Registro de usuario

**POST** `/api/registro`

```json
{
  "name": "Max Rodríguez",
  "email": "max@example.com",
  "password": "MiClaveSegura12",
  "phones": [
    {
      "number": "123456789",
      "cityCode": "2",
      "countryCode": "56"
    }
  ]
}
```
**Respuesta exitosa:**

```json
{
    "id": "d9bd72cd-2f63-40df-9676-81a8fac5c4af",
    "created": "2025-04-16T21:12:30.1210632",
    "modified": "2025-04-16T21:12:30.1210632",
    "lastLogin": "2025-04-16T21:12:30.1210632",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJqdGkiOiI5MGNiODVjMC0wZTRmLTRhOGUtOGFlNi0wYjVmYTY3MjcwMTkiLCJpYXQiOjE3NDQ4NTIzNTAsImV4cCI6MTc0NDg1NTk1MH0.DKAdl8QdhwcR3WY4_TpycElKcvpkbM999EV5FsmHRP5jf4lYkr16_QZBUVDQO49BQ77oD3sf22IOJOd1YlQJxA",
    "active": true
}
```
## Uso con Postman

1. Agregar el endpoint .
2. Verificar que las peticiones sean en formato `application/json`.
3. ejecutar la peticion POST

## Consola H2

Disponible en:  
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- JDBC URL: `jdbc:h2:mem:registrodb`
- Usuario: `sa`
- Contraseña: *(dejar vacío)*

Swagger UI disponible en:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Diagrama
![image](https://github.com/user-attachments/assets/72b9fd1c-30de-42bc-80b7-fc7361e1c34b)
