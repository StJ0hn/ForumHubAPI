package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AutenticacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Usuario usuario;

    @BeforeEach
    void cenario() {
        // LIMPEZA
        usuarioRepository.deleteAllInBatch();

        Usuario usuarioExistente = new Usuario(null, "joana", "lalala@gmail.com", passwordEncoder.encode("lalala"));
        usuario = usuarioRepository.save(usuarioExistente);

    }

    @Test
    void autenticacaoDeveLancarOkEDevolverTokenJWT() throws Exception {
        // ARRANGE
        String json = """
                {
                    "email": "lalala@gmail.com",
                    "senha": "lalala"
                }
                """;
        // ACT
        ResultActions response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(json));
        // ASSERT
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    void autenticacaoComSenhaErradaDeveLancarNaoAutorizadoEDevolverMensagem() throws Exception {
        // ARRANGE
        String json = """
                    {
                        "email": "lalala@gmail.com",
                        "senha": "lololo"
                    }
                """;
        //ACT
        ResultActions response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(json));
        //ASSERT
        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Credenciais invalidas"));
    }

    @Test
    void autenticarComEmailInexistenteDeveRetornarNaoAutorizadoEDevolverMensagem() throws Exception{
        //ARRANGE
        String json = """
                    {
                        "email": "lololo@gmail.com",
                        "senha": "lalala"
                    }
                """;
        //ACT
        ResultActions response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(json));
        //ASSERT
        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Credenciais invalidas"));
    }
}
