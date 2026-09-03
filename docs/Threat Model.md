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

### ### T02 - Secrets committed to a public repository (JWT secret + database password)
**Category:** Information Disclosure
**Risk:** High

**Description:**
In addition to the secret, `spring.datasource.password` is also versioned in plain text.
While token forgery requires crafting signed requests,
the exposed database credential allows direct access to the data—same root cause,
distinct attack vectors.

**Mitigation:**
Three complementary measures:

1. **Immediate rotation:** the current secret and password must be considered
   permanently compromised—they appear in the Git history of a public
   repository. Moving them to environment variables does not undo the leak:
   the values must be **replaced** with new ones (new secret, new password),
   thereby invalidating the exposed ones.
2. **Secrets excluded from version control:** `application.properties` is updated
   to reference environment variables (`api.security.token.secret=${JWT_SECRET}`,
   `spring.datasource.password=${DB_PASSWORD}`); the actual values reside only
   in the local/deployment environment, with the variables file kept outside
   the repository and ignored via `.gitignore`.
3. **Residual risk:** anyone who cloned the repository prior to the rotation
   still possesses the old values—rotation invalidates them, but the past
   exposure is irreversible. Leaks of environment variables (e.g., in logs or
   dumps) are outside the scope of this study project.

**Resolution:** Sprint 2

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