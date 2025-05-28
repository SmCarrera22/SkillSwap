# SkillSwap - Microservicios (Usuarios, Evento, Pedido)

Este proyecto consiste en el desarrollo de tres microservicios RESTful utilizando Spring Boot, cada uno con su propia base de datos y responsabilidad. El objetivo es aplicar arquitectura de microservicios para gestionar usuarios, inventario de eventos, y pedidos (simulando un carrito de compras).

## Estructura del Repositorio

```
SkillSwap/
├── usuario/
├── evento/
├── pedido/
├── script_creacion_tablas.sql
├── informe.pdf
├── README.md
```

## Requisitos

* Java 21
* Gradle 8+
* PostgreSQL (base de datos remota usando [Neon Console](https://neon.tech/))
* Postman (para pruebas)

## Configuración del entorno

Para ejecutar los microservicios correctamente, necesitas configurar variables de entorno usando un archivo `.env` en la raíz de cada microservicio.

### Variables necesarias en `.env`

```
DB_URL=jdbc:postgresql://ep-falling-field-a41gjakk-pooler.us-east-1.aws.neon.tech/SkillSwap?sslmode=require
DB_USERNAME=db_datareader
DB_PASSWORD=npg_DCV9wl1eQkKj
```

> ⬆️ Puedes copiar este `.env` a cada carpeta de microservicio (`usuario/`, `evento/`, `pedido/`).

## Configuración del Puerto

Cada microservicio debe usar un puerto diferente. Esto se configura en `application.properties`:

### `application.properties`

```
spring.application.name=Usuario
server.port=8081
```

```
spring.application.name=Evento
server.port=8082
```

```
spring.application.name=Pedido
server.port=8083
```

## Ejecución del proyecto

En tres terminales distintas (VS Code o IntelliJ), navega a cada carpeta y ejecuta:

```
./gradlew bootRun
```

Ejecuta primero:

1. **Usuario** (8081)
2. **Inventario/Evento** (8082)
3. **Carrito/Pedido** (8083)

## Endpoints principales

* **Usuarios**: `http://localhost:8081/api/v1/usuarios`
* **Eventos**: `http://localhost:8082/api/v1/eventos`
* **Pedidos**: `http://localhost:8083/api/pedidos`

## Comunicación entre servicios

El microservicio **Pedido** hace uso de `RestTemplate` para consultar si el `usuarioId` y `eventoId` existen antes de crear el pedido. Esto asegura integridad entre servicios.

## Base de datos

Puedes encontrar el script para crear las tablas en el archivo:

```
script_creacion_tablas.sql
```

## Pruebas

* Usa la colección Postman incluida para probar todos los endpoints.
* Se incluye un archivo Excel con al menos 5 ejemplos por microservicio, incluyendo método, URL, JSON de entrada y salida.

---

© Proyecto SkillSwap - Duoc UC | Evaluación Parcial 1 - 2025