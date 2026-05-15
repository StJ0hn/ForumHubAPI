# ForumHub API

API RESTful desenvolvida para gerenciamento de tópicos em um fórum de discussões, atuando como desafio backend do programa Oracle Next Education (ONE) via Alura.

## Objetivo
O projeto consiste na construção do núcleo de um fórum digital, com foco na consolidação do ecossistema Spring para aplicações corporativas. O objetivo técnico central foi implementar uma arquitetura stateless segura, dominando o ciclo de vida da requisição HTTP, desde a persistência relacional até a interceptação e validação de tokens de acesso.

## Stack Tecnológico
* Java 17
* Spring Boot / Spring Data JPA
* Spring Security / JWT (JSON Web Token)
* MySQL
* Flyway (Database Migrations)
* Maven

## Arquitetura e Funcionalidades Principais
A API expõe recursos seguindo os princípios de maturidade REST, garantindo integridade e segurança no tráfego de dados.
* Autenticação Stateless: Controle de acesso seguro utilizando Spring Security e interceptação de requisições via filtros customizados para validação de tokens JWT.
* Versionamento de Banco de Dados: Gerenciamento rigoroso de migrações DDL e DML através do Flyway, garantindo rastreabilidade e integridade do esquema relacional no MySQL.
* Gerenciamento de Tópicos (CRUD): Endpoints para criação, listagem paginada, detalhamento, atualização e deleção de tópicos.
* Boas Práticas de Design: Implementação de exclusão lógica de registros (soft delete) e validação estrita de dados de entrada na camada de controle (Bean Validation).

## Endpoints Principais
* POST `/login` - Autentica as credenciais e devolve o token JWT.
* POST `/topicos` - Persiste um novo tópico (Requer Autenticação).
* GET `/topicos` - Retorna a listagem de tópicos ativos com paginação.
* GET `/topicos/{id}` - Detalha o estado de um tópico específico.
* PUT `/topicos/{id}` - Atualiza os metadados do tópico (Requer Autenticação).
* DELETE `/topicos/{id}` - Realiza a exclusão lógica do registro (Requer Autenticação).

## Como Executar Localmente

1. Clone o repositório:
git clone https://github.com/StJ0hn/ForumHubChallengeAlura.git

2. Provisione o banco de dados:
Crie um schema no MySQL nomeado `forumhub_db`.

3. Configure o ambiente:
No diretório `src/main/resources/`, localize ou crie o arquivo `application.properties` e injete as credenciais da sua instância local:
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

4. Compile e execute a aplicação:
./mvnw spring-boot:run
(A API inicializará e escutará conexões na porta 8080).

## Desafios Técnicos e Aprendizados
A estruturação da camada de segurança representou o principal desafio arquitetural. A configuração do Spring Security sem as abstrações depreciadas (como o WebSecurityConfigurerAdapter) forçou um entendimento profundo da cadeia de filtros (SecurityFilterChain) e da gestão de contexto de segurança em aplicações stateless. Além disso, a adoção do Flyway permitiu o abandono das abstrações automáticas do Hibernate (`ddl-auto`), transferindo a responsabilidade do estado do banco de dados para scripts SQL controlados e versionados manualmente, refletindo o rigor esperado em ambientes de produção.
