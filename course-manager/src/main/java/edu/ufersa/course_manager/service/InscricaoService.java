package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.model.Inscricao;
import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.repository.InscricaoRepository;
import edu.ufersa.course_manager.repository.MinicursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class InscricaoService {
    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private MinicursoRepository minicursoRepository;

    @Autowired
    private MinicursoService minicursoService;


    public List<Inscricao> listarInscricoesPorMinicurso(Minicurso minicurso) {
        return inscricaoRepository.findByMinicurso(minicurso);
    }

    public List<Inscricao> listarIncricoesPorUsuario(Usuario usuario) {
        return inscricaoRepository.findByUsuario(usuario);
    }

    public boolean usuarioEstaInscrito(Minicurso minicurso, Usuario usuario) {
        return inscricaoRepository.existsByMinicursoAndUsuario(minicurso, usuario);
    }

    public String inscreverUsuarioEmMinicurso(Usuario usuario, Long idMinicurso) {
        Optional<Minicurso> minicursoOptional = minicursoService.buscarPorId(idMinicurso);
        if(minicursoOptional.isEmpty()){
            return "Não existe minicurso de ID:" + idMinicurso;
        }
        Minicurso minicurso = minicursoOptional.get();
        if (minicurso.getStatus() != Minicurso.Status.APROVADO) {
           return "Inscrição não permitida: minicurso não aprovado.";
        }

        if (minicurso.getNumeroInscritos() >= minicurso.getVagas()) {
            return "O curso não possui vagas disponíveis.";
        }

        if (usuarioEstaInscrito(minicurso, usuario)) {
            return "Você já está inscrito neste minicurso.";
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setMinicurso(minicurso);
        inscricao.setDataInscricao(LocalDateTime.now());

        minicursoService.incrementarNumeroDeInscritos(minicurso);
        return String.valueOf(inscricaoRepository.save(inscricao));
    }

    public String cancelarInscricao(Minicurso minicurso, Usuario usuario) {
        Optional<Inscricao> inscricao = inscricaoRepository.findByMinicursoAndUsuario(minicurso, usuario);

        if (inscricao.isEmpty()) {
            return "Usuário não inscrito no minicurso ";
        }
        inscricaoRepository.delete(inscricao.get());
        minicurso.setNumeroInscritos(minicurso.getNumeroInscritos() - 1);

        return "Inscrição cancelada";
    }
}