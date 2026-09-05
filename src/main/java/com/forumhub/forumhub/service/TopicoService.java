package com.forumhub.forumhub.service;

import com.forumhub.forumhub.dto.DadosAtualizacaoTopico;
import com.forumhub.forumhub.dto.DadosCadastroTopico;
import com.forumhub.forumhub.dto.DadosListagemTopico;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.repository.CursoRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<DadosListagemTopico> listarTodos() {
        return repository.findAllByStatusTrue()
                .stream()
                .map(DadosListagemTopico::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DadosListagemTopico detalhar(Long id) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        return new DadosListagemTopico(topico);
    }

    @Transactional
    public void cadastrar(DadosCadastroTopico dados) {
        var autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        var curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        
        var topico = new Topico(dados, autor, curso);
        repository.save(topico);
    }

    @Transactional
    public DadosListagemTopico atualizar(DadosAtualizacaoTopico dados) {
        var topico = repository.findById(dados.id())
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        
        topico.atualizarInformacoes(dados);
        return new DadosListagemTopico(topico);
    }
}