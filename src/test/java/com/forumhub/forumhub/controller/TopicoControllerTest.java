package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.model.Curso;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.CursoRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TopicoControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    private Topico topicoAtivo;
    private Usuario autorSalvo;
    private Curso cursoSalvo;

    @BeforeEach
    void cenario(){
        //LIMPEZA
        topicoRepository.deleteAllInBatch();
        cursoRepository.deleteAllInBatch();
        usuarioRepository.deleteAllInBatch();

        //ARRANGE
        Usuario autor = usuarioRepository.save(new Usuario(null, "Aprendiz", "aprendiz@test.com", "senha"));
        Curso curso = cursoRepository.save(new Curso(null, "Spring Boot", "Backend"));

        Topico ativo = new Topico();
        ativo.setTitulo("topico ativo");
        ativo.setMensagem("mensagem de ativo");
        ativo.setAutor(autor);
        ativo.setCurso(curso);

        Topico deletado = new Topico();
        deletado.setTitulo("topico deletado");
        deletado.setMensagem("mensagem do deletado");
        deletado.setAutor(autor);
        deletado.setCurso(curso);
        deletado.setStatus(false);
        topicoRepository.save(deletado);
        topicoAtivo = topicoRepository.save(ativo);

        Usuario autor2 = new Usuario(null, "joana", "lalala@gmail.com", "nada");
        Curso curso2 = new Curso(null, "Banco de dados", "Backend");
        this.autorSalvo = autor2;
        this.cursoSalvo = curso2;
        usuarioRepository.save(autorSalvo);
        cursoRepository.save(cursoSalvo);
    }
    //endpoint GET "/topicos"
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

    //endpoint GET "/topicos/{id}"
    @Test
    void detalharPorIdExistenteDeveRetornarTopicoDoIdSolicitado() throws Exception{
        //ACT
        ResultActions resposta = mockMvc.perform(get("/topicos/{id}", topicoAtivo.getId()));
        //ASSERT
        resposta.andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("topico ativo")))
                .andExpect(jsonPath("$.mensagem", is("mensagem de ativo")))
                .andExpect(jsonPath("$.autor", is("Aprendiz")))
                .andExpect(jsonPath("$.curso", is("Spring Boot")))
                .andExpect(jsonPath("$.status", is(true)));
    }

    @Test
    void detalharPorIdInexistenteDeveLancarEntityNotFoundException(){
        //ACT
        ServletException excecao = assertThrows(ServletException.class, () -> mockMvc.perform(get("/topicos/999999")));
        //ASSERT
        assertTrue(excecao.getCause() instanceof EntityNotFoundException);
    }

    //endpoint POST "/topicos"
    @Test
    void cadastrarTopicoDeveRetornarOk() throws Exception{
        //ARRANGE
        String json = """
                {
                    "titulo": "topico de teste",
                    "mensagem": "mensagem de teste",
                    "autorId": %d,
                    "cursoId": %d
                }
                """.formatted(autorSalvo.getId(), cursoSalvo.getId());
        //ACT
        ResultActions resposta = mockMvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        //ASSERT
        resposta.andExpect(status().isOk())
                .andExpect(content().string(""));
        assertEquals(3, topicoRepository.count()); //Verificar se há 3 tópicos, 2 do ARRANGE no cenario() + 1 do POST
    }

    @Test
    void cadastrarTopicoDeveRetornarBadRequest() throws Exception {
        //ARRANGE
        String json = """
                {
                    "mensagem": "mensagem teste",
                    "autorId": %d,
                    "cursoId": %d
                }
                """.formatted(autorSalvo.getId(), cursoSalvo.getId());
        //ACT
        ResultActions resposta = mockMvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        //ASSERT
        resposta.andExpect(status().isBadRequest());
    }

    @Test
    void cadastrarComAutorIdInexistenteDeveLancarDataIntegrityViolationException(){
        //ARRANGE
        String json = """
                {
                    "titulo": "titulo teste",
                    "mensagem": "mensagem teste",
                    "autorId": %d,
                    "cursoId": %d
                }
                """.formatted(999999, cursoSalvo.getId());

        //ACT
        ServletException excecao = assertThrows(ServletException.class,
                () -> mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)));
        //ASSERT
        assertTrue(excecao.getCause() instanceof DataIntegrityViolationException);
    }

    //endpoint PUT "/topics"
    @Test
    void atualizarTopicoExistenteDeveRetornarOkETopicoAtualizado() throws Exception{
        //ARRANGE
        String jsonRequest = """
                {
                    "id": "%d",
                    "titulo": "Atualizacao teste",
                    "mensagem": "mensagem atualizacao teste"
                }
                """.formatted(topicoAtivo.getId());
        Topico atualizado = topicoRepository.findById(topicoAtivo.getId()).orElseThrow();
        //ACT
        ResultActions resposta = mockMvc.perform(put("/topicos").contentType(MediaType.APPLICATION_JSON).content(jsonRequest));
        //ASSERT
        resposta.andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Atualizacao teste")))
                .andExpect(jsonPath("$.mensagem", is("mensagem atualizacao teste")))
                .andExpect(jsonPath("$.autor", is("Aprendiz")));
        assertEquals("Atualizacao teste", atualizado.getTitulo());
    }

    @Test
    void atualizarTopicoComIdInexistenteDeveRetornarEntityNotFound(){
        //ARRANGE
        String json = """
                {
                    "id": %d,
                    "titulo": "titulo teste atulizar",
                    "mensagem": "mensagem teste atualizar"
                }
                """.formatted(999999);

        //ACT
        ServletException excecao = assertThrows(ServletException.class,
                () -> mockMvc.perform(put("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)));

        //ASSERT
        assertTrue(excecao.getCause() instanceof EntityNotFoundException);
    }
}



