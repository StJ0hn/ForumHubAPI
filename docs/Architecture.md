# Architecture

<!-- Este documento descreve o estado ALVO (blueprint para a Sprint 2).
     O comparativo visual Current vs. Target já está no README do repositório —
     não duplicar o diagrama completo aqui. -->

## Architectural Overview
[Descreva brevemente a arquitetura escolhida.]

## Architectural Style

**Style:** [Layered / Hexagonal / Clean / Modular Monolith / etc.]

**Rationale:**
[Por que essa abordagem foi escolhida?]

## Components

### [Component]
**Responsibility:**
[Responsabilidade principal.]

### [Component]
**Responsibility:**
[Responsabilidade principal.]

### [Component]
**Responsibility:**
[Responsabilidade principal.]

## Data Flow

```text
[Component]
    ↓
[Component]
    ↓
[Component]
    ↓
[Database / External Service]
```

## Known Deviations

<!-- Achados de auditoria — desvios já identificados entre o código atual e o alvo descrito acima.
     Preencher a coluna "Resolution" conforme forem endereçados na Sprint 2. -->

### Orphaned Service layer
`TopicoService` has two methods — `listarTodos()` (correct soft-delete filtering via
`findAllByStatusTrue()`) and `detalharTopico(Long id)` — but no endpoint calls either one.
`TopicoController` calls `TopicoRepository` directly in all endpoints, ignoring the
Service layer entirely.

**Resolution:** [to be defined in Sprint 2]

### Listing ignores soft delete
`GET /topicos` uses `TopicoRepository.findAll()` and returns topics with `status = false`
(soft-deleted) alongside active ones. Proven by characterization test
(`TopicoControllerTest.listarDeveRetornarTopicosComStatusFalsos`): a topic seeded with
`status = false` appears in the listing. The correct filter already exists in
`TopicoService.listarTodos()` (`findAllByStatusTrue()`), which is never called
(see "Orphaned Service layer").

**Resolution:** [to be defined in Sprint 2]

### Legacy CLI entry point
There is a legacy command-line interface attempt (`principal/`) that serves no functional purpose in a REST API. 
It is a remnant of an early phase of the project.

**Resolution: remove `principal/` and  `Principal` class

### Detail endpoint returns internal error for non-existent ID
`GET /topicos/{id}` with a non-existent ID returns HTTP 500 instead of 404.
`getReferenceById()` returns a lazy proxy that throws `EntityNotFoundException`
when the DTO reads its fields, and no error handler exists to translate it
into a proper response. Proven by characterization test
(`detalharPorIdInexistenteDeveLancarEntityNotFoundException`) and confirmed
on the running server via Postman.
**Resolution:** [to be defined in Sprint 2]

### Create endpoint returns internal error for non-existent references
`POST /topicos` with a non-existent `autorId` in the body returns HTTP 500
instead of a proper 4xx client error. `getReferenceById()` returns a lazy
proxy without touching the database, so the invalid reference is only
rejected at INSERT time by the `topicos` foreign key — the resulting
`DataIntegrityViolationException` is unhandled (no error handler).
Proven by characterization test (`cadastrarComAutorIdInexistente...`) and
confirmed on the running server via Postman.
**Resolution:** [to be defined in Sprint 2]

### Update endpoint allows modification without ownership validation
`PUT /topicos` updates any existing topic with no authentication and no
ownership check - any caller can modify any topic. The characterization
tests for this endpoint run without a token and succeed (see Threat
Model T01). A dedicated authz-gap proof covering all routes is planned
(Sprints.md, item 3).
**Resolution:** [to be defined in Sprint 2 — AuthZ]

### Update endpoint returns internal error for non-existent ID
`PUT /topicos` with a non-existent `id` behaves like the detail endpoint:
`getReferenceById()` returns a lazy proxy and `atualizarInformacoes()`
triggers `EntityNotFoundException` when touching it - unhandled
(HTTP 500 in production). Third occurrence of the `getReferenceById`
family: detail (proxy exception), create (FK constraint), update
(proxy exception again).
**Resolution:** [to be defined in Sprint 2]

### Login endpoint is inoperable (StackOverflowError)
`POST /login` throws `StackOverflowError` for any credentials — valid,
invalid, or non-existent user. The `AuthenticationManager` bean exposed
by `SecurityConfigurations` (via
`AuthenticationConfiguration.getAuthenticationManager()`) delegates the
`authenticate()` call to itself: an AOP proxy re-entering its own
method in an infinite loop. Reproduced on two independent surfaces:
MockMvc (characterization test) and the real running server
(HTTP 500, verified via curl). The README describes login as a
working feature.
**Resolution:** [to be defined in Sprint 2 — authentication will be rebuilt]
