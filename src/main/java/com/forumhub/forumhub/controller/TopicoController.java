package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.dto.DadosAtualizacaoTopico;
import com.forumhub.forumhub.dto.DadosCadastroTopico;
import com.forumhub.forumhub.dto.DadosListagemTopico;
import com.forumhub.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        topicoService.cadastrar(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemTopico>> listar() {
        return ResponseEntity.ok(topicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemTopico> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.detalhar(id));
    }

    @PutMapping
    public ResponseEntity<DadosListagemTopico> atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        return ResponseEntity.ok(topicoService.atualizar(dados));
    }
}