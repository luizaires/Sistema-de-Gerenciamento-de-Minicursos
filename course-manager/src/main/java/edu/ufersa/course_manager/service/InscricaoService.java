package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.model.Inscricao;
import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.repository.InscricaoRepository;
import edu.ufersa.course_manager.repository.MinicursoRepository;
import org.springframework.stereotype.Service;

@Service
public class InscricaoService {
    private final InscricaoRepository repository;
    private final MinicursoRepository minicursoRepository;

    public InscricaoService(InscricaoRepository repository, MinicursoRepository minicursoRepository) {
        this.repository = repository;
        this.minicursoRepository = minicursoRepository;
    }

    public String inscreverUsuario(Usuario usuario, Minicurso minicurso) {
        if (minicurso.getStatus() != Minicurso.Status.APROVADO) {
            return "Inscrição não permitida: minicurso encerrado.";
        }

        if (repository.findByUsuarioAndMinicurso(usuario, minicurso).isPresent()) {
            return "Você já está inscrito neste minicurso.";
        }

        int inscritos = repository.countByMinicurso(minicurso);
        if (inscritos >= minicurso.getVagas()) {
            return "Não há vagas disponíveis.";
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setInscrito(usuario);
        inscricao.setMinicurso(minicurso);

        repository.save(inscricao);
        return "Inscrição realizada com sucesso!";
    }
}