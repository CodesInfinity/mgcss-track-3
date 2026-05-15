# MGCSS Track 3 - Sistema de Gestión de Solicitudes de Soporte Técnico

[![CI Pipeline](https://github.com/CodesInfinity/mgcss-track-3/actions/workflows/ci.yml/badge.svg)](https://github.com/CodesInfinity/mgcss-track-3/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=CodesInfinity_mgcss-track-3&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=CodesInfinity_mgcss-track-3)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=CodesInfinity_mgcss-track-3&metric=coverage)](https://sonarcloud.io/summary/new_code?id=CodesInfinity_mgcss-track-3)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=CodesInfinity_mgcss-track-3&metric=bugs)](https://sonarcloud.io/summary/new_code?id=CodesInfinity_mgcss-track-3)

Backend robusto desarrollado en Java y Spring Boot enfocado en la automatización y gestión del ciclo de vida de incidencias y soporte técnico corporativo. El sistema administra el flujo completo desde el registro inicial de una solicitud por parte de un cliente, la asignación eficiente de técnicos cualificados, las transiciones de estados de negocio y el mantenimiento de una auditoría estricta mediante un histórico inmutable de cambios de estado.

---

# 🏗️ Arquitectura del Sistema

El proyecto está diseñado de forma estricta bajo los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)**, garantizando un desacoplamiento total entre las reglas lógicas del negocio y las tecnologías de infraestructura.

## Capas principales

### Dominio (`com.mgcss.domain`)
El núcleo central de la aplicación. Contiene los modelos puros de negocio:

- `Solicitud`
- `Tecnico`
- `Cliente`

Y tipos enumerados:

- `Estado`
- `TipoCliente`

Todos completamente libres de anotaciones de persistencia o frameworks. Consolidan el comportamiento y las restricciones operativas.

### Puertos (`com.mgcss.infrastructure`)
Interfaces abstractas que actúan como contratos de salida, definiendo las operaciones de persistencia que el dominio requiere.

Ejemplo:
```java
SolicitudRepository
```

### Adaptadores de Salida (`com.mgcss.infrastructure.persistence`)
Implementaciones de infraestructura que conectan los puertos con la base de datos relacional usando:

- Spring Data JPA
- Hibernate

Traducen los objetos puros de dominio a entidades ORM (`SolicitudEntity`) aislando completamente el núcleo del sistema.

### Adaptadores de Entrada (`com.mgcss.api.rest`)
Controladores REST que exponen los casos de uso del backend hacia clientes externos empleando DTOs estructurados de petición y respuesta.

---

# 🛠️ Stack Tecnológico

| Categoría | Tecnología |
|---|---|
| Core | Java 17 & Spring Boot 4.0.3 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de Datos | PostgreSQL / H2 |
| DevOps | Docker, Docker Compose, GitHub Actions |
| Calidad | SonarCloud, JaCoCo |
| Documentación | Springdoc OpenAPI (Swagger UI) |

---

# 📦 Requisitos Previos

Asegúrate de tener instalado:

- JDK 17
- Maven Wrapper (`./mvnw`)
- Docker
- Docker Compose

---

# 🚀 Despliegue con Docker Compose

El proyecto incluye soporte automatizado para levantar el backend y la base de datos relacional dentro de una red aislada de contenedores.

## 1. Compilar y empaquetar el proyecto

```bash
./mvnw clean package -DskipTests
```

## 2. Construir y levantar los contenedores

```bash
docker compose up --build -d
```

## 3. Verificar el estado de los servicios

```bash
docker compose ps
```

## 4. Detener y limpiar el entorno

```bash
docker compose down -v
```

Este comando elimina también los volúmenes persistentes de PostgreSQL.

---

# 📖 Documentación de la API (Swagger UI)

Una vez iniciado el entorno, la API estará disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 🔌 Endpoints Principales

## Registrar una nueva solicitud

```http
POST /api/solicitudes
```

Estado inicial automático:
```text
ABIERTA
```

---

## Obtener detalles de una solicitud

```http
GET /api/solicitudes/{id}
```

---

## Asignar técnico a una incidencia

```http
PUT /api/solicitudes/{id}/tecnicos
```

Transición automática:
```text
EN_PROCESO
```

---

## Actualizar estado de una solicitud

```http
PATCH /api/solicitudes/estado
```

---

## Reabrir incidencia cerrada

```http
PATCH /api/solicitudes/{id}/reabrir
```

---

## Consultar histórico de estados

```http
GET /api/solicitudes/{id}/historico
```

---

## Listar todas las solicitudes

```http
GET /api/solicitudes/listarSolicitudes
```

---

# 🧪 Ejecución de Pruebas

El proyecto incorpora:

- Tests unitarios
- Tests de integración
- Mockito
- Bases de datos H2 en memoria
- Verificación de persistencia JPA

## Ejecutar todas las pruebas

```bash
./mvnw clean verify
```

---

# 📊 Reporte de Cobertura

Después de ejecutar los tests, JaCoCo generará automáticamente el reporte de cobertura en:

```text
target/site/jacoco/index.html
```

---

# 🔄 Integración Continua (CI Pipeline)

El workflow configurado en:

```text
.github/workflows/ci.yml
```

ejecuta automáticamente:

1. Checkout del repositorio
2. Configuración de JDK 17 (Temurin)
3. Compilación Maven
4. Ejecución de pruebas automatizadas
5. Verificación de calidad
6. Análisis estático con SonarCloud

---

# ✨ Características de Diseño Implementadas

## Estrategia de Identidad Nativa

La persistencia de IDs se delega completamente a PostgreSQL mediante:

```java
GenerationType.IDENTITY
```

Ventajas:

- Prevención de colisiones concurrentes
- IDs secuenciales consistentes
- Compatibilidad natural con PostgreSQL
- Simplicidad operacional

---

## Auditoría e Inmutabilidad

Todos los cambios de estado quedan registrados mediante:

```java
EstadoChange
```

Persistidos en:

```text
solicitud_historico
```

usando una relación:

```java
@OneToMany
```

Esto garantiza:

- Trazabilidad completa
- Historial inmutable
- Auditoría empresarial
- Seguimiento cronológico de incidencias

---

# 📁 Estructura General del Proyecto

```text
src
├── main
│   ├── java
│   │   └── com.mgcss
│   │       ├── api
│   │       ├── domain
│   │       ├── infrastructure
│   │       └── application
│   └── resources
└── test
    ├── java
    └── resources
```

---

# 🐳 Servicios Docker

El entorno local está compuesto por:

| Servicio | Puerto |
|---|---|
| Backend Spring Boot | 8080 |
| PostgreSQL | 5432 |

---

# 📌 Objetivos del Proyecto

- Aplicar Arquitectura Hexagonal real
- Separar dominio e infraestructura
- Implementar trazabilidad de negocio
- Mantener alta cobertura de pruebas
- Automatizar CI/CD
- Facilitar escalabilidad y mantenimiento

---

# 👨‍💻 Autor
Sergio Núñez Sierra y Agustín Rodríguez Aguilar.

Proyecto desarrollado como backend corporativo orientado a buenas prácticas de arquitectura, mantenibilidad y calidad de software empresarial.