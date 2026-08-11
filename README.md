# ForumHub API
---
> RESTful API developed for topics management in a discussion forum, serving as a Back-End challenge of Oracle Next Education (ONE) program via Alura.
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

> Originally built as an Oracle Next Education (ONE) / Alura backend challenge, this project is currently undergoing a professional refactoring process.

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
