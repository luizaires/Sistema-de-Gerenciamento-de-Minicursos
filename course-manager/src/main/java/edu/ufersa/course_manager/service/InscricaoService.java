package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.model.Inscricao;
import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.repository.InscricaoRepository;
import edu.ufersa.course_manager.repository.MinicursoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class InscricaoService {
    private final InscricaoRepository inscricaoRepository;
    private final MinicursoRepository minicursoRepository;
    private final MinicursoService minicursoService;

    public InscricaoService(InscricaoRepository inscricaoRepository, MinicursoRepository minicursoRepository, MinicursoService minicursoService) {
        this.inscricaoRepository = inscricaoRepository;
        this.minicursoRepository = minicursoRepository;
        this.minicursoService = minicursoService;
    }
    public List<Inscricao> listarInscricoesPorMinicurso(Minicurso minicurso) {
        return inscricaoRepository.findByMinicurso(minicurso);
    }

    public List<Inscricao> listarIncricoesPorUsuario(Usuario usuario) {
        return inscricaoRepository.findByUsuario(usuario);
    }

    public boolean usuarioEstaInscrito(Minicurso minicurso, Usuario usuario) {
        return inscricaoRepository.existsByMinicursoAndUsuario(minicurso, usuario);
    }

    public Inscricao inscreverUsuarioEmMinicurso(Usuario usuario, Minicurso minicurso) {
        if (minicurso.getStatus() != Minicurso.Status.APROVADO) {
            throw new IllegalStateException("Inscrição não permitida: minicurso não aprovado.");
        }

        if (minicurso.getNumeroInscritos() >= minicurso.getVagas()) {
            throw new IllegalStateException("O curso não possui vagas disponíveis.");
        }

        if (usuarioEstaInscrito(minicurso, usuario)) {
            throw new IllegalStateException("Você já está inscrito neste minicurso.");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setMinicurso(minicurso);

        minicursoService.incrementarNumeroDeInscritos(minicurso);
        return inscricaoRepository.save(inscricao);
    }

    public void cancelarInscricao(Minicurso minicurso, Usuario usuario) {
        Optional<Inscricao> inscricao = inscricaoRepository.findByMinicursoAndUsuario(minicurso, usuario);

        if (inscricao.isPresent()) {
            inscricaoRepository.delete(inscricao.get());

            minicurso.setNumeroInscritos(minicurso.getNumeroInscritos() - 1);
            minicursoService.cadastrarMinicurso(minicurso);
        }
    }
}