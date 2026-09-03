# ForumHubAPI — Sprints & Tasks

> Uma ação por vez.

---

## Sprint 1 — Auditoria e Caracterização

### 1. Ambiente
- [x] Projeto rodando local, sem erro
- [x] CLI órfã (`principal/`) removida

### 2. Requirements
- [x] Mapear no `Requirements.md` o que o sistema faz hoje: Problem, Objective, Scope, Actors, RFs, RNFs, Business Rules
- [x] Adicionar RFs identificados: deleção lógica de tópico, listagem paginada

### 3. Characterization tests
- [x] `test/login-characterization` — `POST /login`
- [x] `test/topics-list-characterization` — `GET /topicos`
- [x] `test/topic-detail-characterization` — `GET /topicos/{id}`
- [x] `test/topic-create-characterization` — `POST /topicos`
- [x] `test/topic-update-characterization` — `PUT /topicos`
- [x] `test/authz-gap-proof` — provar que `permitAll()` libera tudo hoje

### 4. Threat Model
- [x] Preencher `ThreatModel.md`: Security Objective, Assets, Entry Points, Trust Boundaries
- [x] Escrever Mitigation de T01 (AuthZ ausente) e T02 (JWT secret exposto)

### 5. Architecture
- [x] Preencher `Architecture.md`: Overview, Style, Components, Data Flow (estado alvo)
- [x] Confirmar Known Deviations (Service órfão — já mapeado)

### 6. Fechar Sprint 1
- [ ] Revisar os 4 documentos juntos, checar contradição entre eles
- [ ] Atualizar README se algo mudou

---

## Sprint 2 — Refactoring (Fowler)

- [ ] Corrigir Service órfão
- [ ] Implementar AuthZ real (resolve T01)
- [ ] Mover JWT secret e credenciais para variável de ambiente (resolve T02)
- [ ] Implementar DELETE (soft delete)
- [ ] Implementar paginação
- [ ] Traduzir aplicação para inglês (rotas, DTOs, entidades, métodos)

## Sprint 3 — React Frontend

- [ ] Detalhar quando a Sprint 2 estiver em andamento

## Sprint 4 — CI/CD & Deploy

- [ ] Detalhar quando a Sprint 3 estiver em andamento
- [ ] Plataforma de deploy — decisão adiada
- [ ] Escopo do pipeline — decisão adiada