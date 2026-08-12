# API Design

<!-- Este documento descreve o estado ALVO: nomenclatura em inglês, endpoints completos
     (incluindo DELETE e paginação, ainda não implementados hoje). Sem versionamento de
     URL (/api/v1) — decisão KISS, sem consumidor externo no momento. -->

## Overview
[Descreva o propósito da API.]

## Base URL
```text
/
```

## Authentication
[Descreva como a API autentica e autoriza os consumidores.]

<!-- Nota: hoje a autorização não é aplicada em nenhuma rota (permitAll() — ver Threat Model T01).
     Este documento descreve como DEVE funcionar após a Sprint 2. -->

## Resources

### Topics

#### POST /topics
**Purpose:**
[Descrição.]

**Request:**
```json
{
  "field": "value"
}
```

**Response:** 201 Created
```json
{
  "id": "uuid",
  "field": "value"
}
```

#### GET /topics
**Purpose:**
[Descrição — incluir parâmetros de paginação (page, size, sort).]

<!-- NOVO — endpoint hoje não é paginado, isso é parte do alvo -->

#### GET /topics/{id}
**Purpose:**
[Descrição.]

**Response:** 200 OK
```json
{
  "id": "uuid",
  "field": "value"
}
```

#### PUT /topics/{id}
**Purpose:**
[Descrição.]

#### DELETE /topics/{id}
**Purpose:**
[Descrição.]

<!-- NOVO — endpoint não existe hoje. O campo/método de soft delete já existe na entidade,
     mas não está conectado a nenhuma rota. -->

## Validation

| Field | Rule |
|---|---|
| [field] | [Rule] |

## Errors

| Status | Meaning |
|---|---|
| 400 | Invalid request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Resource not found |
| 409 | Conflict |
| 500 | Internal server error |

## Versioning
Não aplicável neste momento — decisão consciente de manter simples (KISS), sem consumidor
externo dependendo da API hoje. Pode ser reavaliado no futuro se necessário.

## OpenAPI
[Link ou referência para a especificação OpenAPI, caso exista.]