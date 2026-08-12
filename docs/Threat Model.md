# Threat Model

## Security Objective
[Descreva o que precisa ser protegido e quais propriedades de segurança são importantes.]

## Assets
- **[Asset]** — [Descrição]
- **[Asset]** — [Descrição]

## Entry Points
- **[Entry Point]** — [Descrição]
- **[Entry Point]** — [Descrição]

## Trust Boundaries
[Descreva onde ocorre uma mudança no nível de confiança.]

```text
[Untrusted Zone]
        ↓
[Application]
        ↓
[Trusted Zone]
```

## Threats

### T01 — Missing route-level authorization
**Category:** Elevation of Privilege
**Risk:** High

**Description:**
`SecurityConfigurations` define `anyRequest().permitAll()` — todas as rotas da API estão
publicamente acessíveis hoje, inclusive operações de escrita (create, update), sem exigir
token válido. `Usuario` implementa `GrantedAuthority` com `ROLE_USER` fixo, mas nenhuma
regra de autorização de fato o utiliza.

**Mitigation:**
[Descreva como o sistema pretende reduzir ou eliminar o risco.]

### T02 — JWT signing secret committed to a public repository
**Category:** Information Disclosure
**Risk:** High

**Description:**
`application.properties` contém `api.security.token.secret` em texto plano, versionado no
repositório (agora público). Qualquer pessoa com acesso ao repo pode ler o segredo e, em
tese, forjar tokens JWT válidos para a API.

**Mitigation:**
[Descreva como o sistema pretende reduzir ou eliminar o risco — ex: mover para variável de
ambiente, rotacionar o segredo atual, nunca commitar `.env`/secrets.]

### T03 — [Threat Name]
**Category:** [STRIDE category]
**Risk:** [Low / Medium / High / Critical]

**Description:**
[Descreva como a ameaça poderia ocorrer.]

**Mitigation:**
[Descreva como o sistema pretende reduzir ou eliminar o risco.]

## Security Assumptions
- [Premissa de segurança]
- [Premissa de segurança]

## Residual Risks
- [Risco que permanece mesmo após as mitigações.]