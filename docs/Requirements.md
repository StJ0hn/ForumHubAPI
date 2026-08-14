# Requirements

## Problem
Create an application to centralize discussion around topics opened by users.
Resolution of challenge of ONE program.

## Objective
[Descreva o objetivo principal do sistema.]

## Scope

### In Scope
- [Funcionalidade]
- [Funcionalidade]

### Out of Scope
- [Funcionalidade explicitamente fora do escopo]

## Actors

<!-- DECISÃO PENDENTE: hoje `Usuario` implementa ROLE_USER fixo, sem uso real (AuthZ ausente — ver Threat Model T01).
     Antes da Sprint 2 implementar autorização de verdade, definir aqui formalmente se existe mais de um Actor
     (ex: User comum vs. Moderator/Admin) ou se o sistema mantém um único perfil de usuário. -->

- **[Actor]** — [Descrição]

## Functional Requirements

### RF01 — [Nome]
[O sistema deve...]

### RF02 — [Nome]
[O sistema deve...]

<!-- NOVOS RFs A ESCREVER — origem: achados de auditoria (ver checklist de decisões)

RF0X — Topic deletion
O sistema deve permitir a exclusão lógica (soft delete) de um tópico.
Contexto: o campo/método de soft delete já existe na entidade Topico, mas não está
conectado a nenhum endpoint hoje — é código morto.

RF0Y — Paginated topic listing
O sistema deve retornar a listagem de tópicos de forma paginada.
Contexto: hoje o endpoint de listagem retorna a lista completa via findAll(), sem paginação.
-->

## Non-Functional Requirements

### RNF01 — [Nome]
[O sistema deve...]

### RNF02 — [Nome]
[O sistema deve...]

## Business Rules

### RN01 — [Nome]
[Regra de negócio.]

### RN02 — [Nome]
[Regra de negócio.]