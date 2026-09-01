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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AutenticacaoControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    public Usuario usuario;

    @BeforeEach
    void cenario(){
        //LIMPEZA
        usuarioRepository.deleteAllInBatch();

        Usuario usuarioExistente = new Usuario(null, "joana", "lalala@gmail.com", passwordEncoder.encode("lalala"));
        usuario = usuarioRepository.save(usuarioExistente);

    }

    @Test
    void autenticacaoDeveLancarStackOverflowError(){
        //ARRANGE
        String json = """
                {
                    "email": "lalala@gmail.com",
                    "senha": "lalala"
                }
                """;
        //ACT
        ServletException excecao = assertThrows(ServletException.class,
                () -> mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)));

        //ASSERT
        assertTrue(excecao.getCause() instanceof StackOverflowError);
    }
}
