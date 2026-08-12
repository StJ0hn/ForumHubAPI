# ForumHub API
---
> RESTful API developed for topics management in a discussion forum, serving as a Back-End challenge of Oracle Next Education (ONE) program via Alura.

> 🚧 This project is under active refactoring, see the Features section for current progress.

> Originally built as an Oracle Next Education (ONE) / Alura backend challenge, this project is currently undergoing a professional refactoring process.
---
## Technologies

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![JWT (Auth0)](https://img.shields.io/badge/JWT-Auth0_java--jwt-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC0000?style=for-the-badge&logo=lombok&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

---

## Overview

- **Problem to be solved:** create an application to centralize discussion around topics opened by users.
- **Solution:** develop a RESTful API with AuthN and AuthZ services.
- **Conclusion:** implementing a RESTful API to manage a forum with AuthN and AuthZ is not just about writing code, it required a sequence of deliberate decisions, such as architecture, problem domain modeling, and technology choices. Working under a tight deadline was one of the biggest challenges of the original assignment.

---
## Features

- [x] JWT-based login (authentication only — see note below)
- [x] Topic creation, listing and update
- [ ] Topic deletion (soft-delete field exists on the entity but is not yet wired to any endpoint)
- [ ] Paginated topic listing (currently returns the full list)
- [ ] Route-level authorization (all endpoints are currently public — `permitAll()`)
- [ ] Automated test suite (characterization tests, then behavior-driven tests post-refactor)
- [ ] Architectural refactoring guided by Martin Fowler's *Refactoring*
- [ ] React frontend consuming the API
- [ ] CI/CD pipeline (GitHub Actions) and cloud deployment

---
## Requirements

- [x] Java 17+
- [x] Maven 3.9+ (or use the included `mvnw` wrapper)
- [x] MySQL (local instance)
- [ ] Node.js — required from Sprint 3 (React frontend)
- [ ] Docker — required from Sprint 4 (containerization/deploy)

---
## Installation

1. Clone the repository:
```bash
   git clone https://github.com/StJ0hn/ForumHubAPI.git
   cd ForumHubAPI
```

2. Provision a MySQL database:
```sql
   CREATE DATABASE forumhub_db;
```

3. Set the required environment variables (do not commit credentials):
```bash
   export DB_USERNAME=your_username
   export DB_PASSWORD=your_password
   export JWT_SECRET=your_jwt_secret
```

4. Build and run:
```bash
   ./mvnw spring-boot:run
```

---

## Usage

After the application starts (`http://localhost:8080`), authenticate to obtain a JWT token:

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "yourpassword"}'
```

Create a topic:

```bash
curl -X POST http://localhost:8080/topics \
  -H "Content-Type: application/json" \
  -d '{"title": "Topic title", "message": "Topic message", "authorId": 1, "courseId": 1}'
```

List topics:

```bash
curl http://localhost:8080/topics
```

> ⚠️ Authorization is not yet enforced on protected routes (see Features), all endpoints are currently reachable without a valid token. Field and route names shown above reflect the target English naming (Sprint 2); the current codebase still uses Portuguese identifiers.

---
## Architecture

### Current (pre-refactor)

The current codebase applies the Service layer inconsistently, some endpoints skip it entirely, calling the Repository directly.

```text
                    HTTP Request
                          │
                          ▼
                     Controller
                    │           │
                    ▼           ▼
              Repository     Service (only used by detail())
                    │           │
                    ▼           ▼
                     MySQL Database
```

### Target (post-refactor)

```text
                    HTTP Request
                          │
                          ▼
                     Controller
                          │
                          ▼
                       Service
                          │
                          ▼
                     Repository
                          │
                          ▼
                     MySQL Database
```

### Design Decisions

#### Inconsistent layering (identified issue)

`TopicoController.listar()` and `cadastrar()` call `TopicoRepository` directly, bypassing `TopicoService` — which already contains the correct soft-delete filtering logic (`findAllByStatusTrue()`). Only `detalhar()` goes through the Service. This inconsistency is one of the first refactoring targets planned for Sprint 2.

#### Stateless authentication

JWT-based authentication with a stateless `SecurityFilterChain` (no server-side session). Route-level authorization is not yet enforced, see Features.

#### Database schema versioning

Flyway manages schema migrations via versioned SQL scripts, replacing Hibernate's automatic `ddl-auto` schema generation.

---
## Project Structure

```text
src/main/java/com/forumhub/forumhub/
├── controller/       # REST controllers (HTTP layer)
├── dto/              # Data Transfer Objects
├── infra/security/   # JWT token service and Spring Security configuration
├── model/            # JPA entities
├── principal/        # Legacy CLI entry point (to be removed)
├── repository/       # Spring Data repositories
├── service/          # Business logic (currently underused — see Architecture)
└── ForumhubApplication.java

src/main/resources/
└── db/migration/     # Flyway versioned SQL migrations
```

---
## References

- Spring Boot Documentation
- Spring Security Documentation
- Spring Data JPA Documentation
- Flyway Documentation
- Martin Fowler, *Refactoring: Improving the Design of Existing Code*
- Oracle Next Education (ONE)

---
## License
MIT License
