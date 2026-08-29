package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.model.Curso;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.CursoRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TopicoControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @BeforeEach
    void cenario(){
        //ARRANGE
        Usuario autor = usuarioRepository.save(new Usuario(null, "Aprendiz", "aprendiz@test.com", "senha"));
        Curso curso = cursoRepository.save(new Curso(null, "Spring Boot", "Backend"));

        Topico ativo = new Topico();
        ativo.setTitulo("topico ativo");
        ativo.setMensagem("mensagem de ativo");
        ativo.setAutor(autor);
        ativo.setCurso(curso);
        topicoRepository.save(ativo);

        Topico deletado = new Topico();
        deletado.setTitulo("topico deletado");
        deletado.setMensagem("mensagem do deletado");
        deletado.setAutor(autor);
        deletado.setCurso(curso);
        deletado.setStatus(false);
        topicoRepository.save(deletado);
    }

    @Test
    void listarDeveRetornarTodosOsTopicos() throws Exception{
        //ACT
        ResultActions resposta = mockMvc.perform(get("/topicos"));
        //ASSERT
        resposta.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void listarDeveRetornarTopicosComStatusFalsos() throws Exception{
        //ACT
        ResultActions resposta = mockMvc.perform(get("/topicos"));
        //ASSERT
        resposta.andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.status == false)].titulo", hasItem("topico deletado")));
    }
}



