# Vert.x Post Service (Spring)

[![Java CI with Maven](https://github.com/hendisantika/vertx-post-service-spring/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/vertx-post-service-spring/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange.svg)
[![Vert.x](https://img.shields.io/badge/Vert.x-5.2.2-purple.svg)](https://vertx.io)
![Spring Framework](https://img.shields.io/badge/Spring%20Framework-7.0.10-6db33f.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-336791.svg)

A reactive REST API for blog posts built with **Eclipse Vert.x**, using the **Spring Framework** IoC container
(no Spring Boot) for dependency injection and the **Vert.x reactive PostgreSQL client** for data access.

## Tech Stack

| Component         | Version  |
|-------------------|----------|
| JDK               | 25       |
| Eclipse Vert.x    | 5.2.2    |
| Spring Framework  | 7.0.10   |
| Jackson           | 2.22.4   |
| Lombok            | 1.18.48  |
| JUnit Jupiter     | 6.1.3    |
| PostgreSQL        | 18       |
| Maven (wrapper)   | 3.10.0   |

## How It Works

- `DemoApplication` bootstraps a Spring `AnnotationConfigApplicationContext`, which provides the `Vertx`
  instance and the PostgreSQL `Pool` as beans.
- `SpringAwareVerticleFactory` lets Vert.x create verticles as Spring beans, so `MainVerticle` gets
  `PostsHandler` / `PostRepository` injected.
- `MainVerticle` starts an HTTP server on port **8888**.
- `DataInitializer` clears the `posts` table and inserts sample data on startup.

## Prerequisites

- JDK 25
- Docker (for PostgreSQL), or a local PostgreSQL instance

## Database

The application connects to `localhost:5432`, database `blogdb`, user `user`, password `password`
(see `DemoApplication#pgPool`). The schema lives in [`src/main/resources/db/schema.sql`](src/main/resources/db/schema.sql).

Start PostgreSQL with Docker Compose (the schema is applied automatically on first start):

```bash
docker compose up -d
```

Using your own PostgreSQL instead:

```bash
psql -h localhost -U user -d blogdb -f src/main/resources/db/schema.sql
```

## Build, Test & Run

```bash
# run tests (requires PostgreSQL running)
./mvnw clean test

# package (produces target/vertx-post-service-spring-1.0.0-SNAPSHOT-fat.jar)
./mvnw clean package

# run from Maven
./mvnw clean compile exec:java

# or run the Spring bootstrap class from your IDE
#   id.my.hendisantika.vertx_post_service_spring.DemoApplication
```

## API Endpoints

Base URL: `http://localhost:8888`

| Method   | Path          | Description        | Response                         |
|----------|---------------|--------------------|----------------------------------|
| `GET`    | `/posts`      | List all posts     | `200` JSON array                 |
| `GET`    | `/posts/{id}` | Get a post by UUID | `200` JSON, `404` if not found   |
| `POST`   | `/posts`      | Create a post      | `201` + `Location` header        |
| `PUT`    | `/posts/{id}` | Update a post      | `204`                            |
| `DELETE` | `/posts/{id}` | Delete a post      | `204`                            |
| `GET`    | `/hello`      | Health/hello check | `200` text                       |

Example:

```bash
curl -X POST http://localhost:8888/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"Hello Vert.x","content":"My first post"}'

curl http://localhost:8888/posts
```

## CI/CD

[GitHub Actions](.github/workflows/maven.yml) builds and tests every push and pull request to `main` on
**Temurin JDK 25**, with a PostgreSQL 18 service container initialized from `schema.sql`. On pushes to `main`
the Maven dependency graph is submitted to GitHub for better Dependabot alerts.

## Dependency Updates

[Dependabot](.github/dependabot.yml) is configured for:

- **Maven** – daily, grouped by `vertx`, `spring`, `jackson`, `testing` and `maven-plugins`
  (Jackson major upgrades are ignored to stay on 2.x).
- **GitHub Actions** – weekly, all actions grouped into one PR.
- **Docker Compose** – weekly, for the PostgreSQL image.

## Help

- [Vert.x Documentation](https://vertx.io/docs/)
- [Vert.x Stack Overflow](https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15)
- [Vert.x Discord](https://discord.gg/6ry7aqPWXy)

## Author

**Hendi Santika** — [s.id/hendisantika](https://s.id/hendisantika) · Telegram [@hendisantika34](https://t.me/hendisantika34)
