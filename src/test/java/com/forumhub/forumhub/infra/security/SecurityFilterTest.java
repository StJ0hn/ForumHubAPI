package com.forumhub.forumhub.infra.security;

import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.UsuarioRepository;
import com.forumhub.forumhub.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private String tokenValido;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAllInBatch();
        Usuario novoUsuario = new Usuario(null, "Test User", "test@example.com", "senha");
        usuario = usuarioRepository.save(novoUsuario);
        tokenValido = tokenService.gerarToken(usuario);
    }

    @Test
    void requisicaoSemTokenEmRotaProtegidaDeveRetornar403() throws Exception {
        mockMvc.perform(get("/topicos"))
                .andExpect(status().isForbidden());
    }

    @Test
    void requisicaoComTokenValidoDeveRetornar200() throws Exception {
        mockMvc.perform(get("/topicos")
                        .header("Authorization", "Bearer " + tokenValido))
                .andExpect(status().isOk());
    }

    @Test
    void requisicaoComTokenInvalidoDeveRetornar403() throws Exception {
        mockMvc.perform(get("/topicos")
                        .header("Authorization", "Bearer token.invalido.jwt"))
                .andExpect(status().isForbidden());
    }
}
