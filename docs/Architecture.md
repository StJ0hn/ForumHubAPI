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
`TopicoService.listarTodos()` já implementa a lógica correta de filtragem (soft delete via
`findAllByStatusTrue()`), mas `TopicoController` chama `TopicoRepository` diretamente na maioria
dos endpoints, ignorando essa camada. Apenas `detalhar()` passa pelo Service.

**Resolution:** [a definir na Sprint 2]

### Legacy CLI entry point
Existe uma tentativa antiga de interface de linha de comando (`principal/`), sem propósito
funcional numa API REST. Resquício de uma fase inicial do projeto.

**Resolution:** [a definir — remover ou isolar antes da Sprint 1]