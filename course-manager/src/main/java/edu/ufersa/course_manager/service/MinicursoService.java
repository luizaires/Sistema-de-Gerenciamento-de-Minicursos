package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.controller.MinicursoDto;
import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.repository.MinicursoRepository;
import edu.ufersa.course_manager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinicursoService {
    @Autowired
    private MinicursoRepository minicursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Minicurso> listarMinicursos() {
        return minicursoRepository.findAll();
    }


    public Optional<Minicurso> buscarPorId(Long id) {
        return minicursoRepository.findById(id);
    }

    /*
    public List<Minicurso> buscarPorInstrutor(Usuario instrutor) {
        return minicursoRepository.findByInstrutor(instrutor);
    }

    public List<Minicurso> buscarPorTitulo(String titulo) {
        return minicursoRepository.findByTituloContainingIgnoreCase(titulo);
    }
    */

    public Optional<Minicurso> cadastrarMinicurso(MinicursoDto minicursoDto, Authentication auth) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Minicurso minicurso = new Minicurso();
        minicurso.setTitulo(minicursoDto.titulo());
        minicurso.setDescricao(minicursoDto.descricao());
        minicurso.setCargaHoraria(minicursoDto.cargaHoraria());
        minicurso.setVagas(minicursoDto.vagas());
        minicurso.setNumeroInscritos(0);
        minicurso.setDataInicio(minicursoDto.dataInicio());
        minicurso.setDataFim(minicursoDto.dataFim());
        if(usuario.getTipo() == Usuario.Tipo.PROFESSOR){
            minicurso.setStatus(Minicurso.Status.APROVADO);
        }else{
            minicurso.setStatus(Minicurso.Status.PENDENTE);
        }
        minicurso.setInstrutor(usuario);


        return Optional.of(minicursoRepository.save(minicurso));
    }

    public String deletarMinicursoPorId(Long id, Authentication auth) {
        Optional<Minicurso> minicursoOptional = minicursoRepository.findById(id);
        if(minicursoOptional.isEmpty()){
            return "Não existe minicurso com esse id";
        }
        Optional<Usuario> usuarioLogado = usuarioRepository.findByUsername(auth.getName());
        if(usuarioLogado.isEmpty()){
            return "Usuário não está logado";
        }
        Minicurso minicurso = minicursoOptional.get();
        Usuario usuario = usuarioLogado.get();
        if(!minicurso.getInstrutor().getId().equals(usuario.getId())){
            return "Usuário não é o criador do minicurso";
        }
        minicursoRepository.deleteById(id);
        return "Minicurso deletado com sucesso.";
    }

    public void incrementarNumeroDeInscritos(Minicurso minicurso) {
        if (minicurso.getNumeroInscritos() < minicurso.getVagas()) {
            minicurso.setNumeroInscritos(minicurso.getNumeroInscritos() + 1);
            minicursoRepository.save(minicurso);
        }
    }

    public String validarMinicurso(Long id, Authentication auth, Boolean validar){
        Optional<Usuario> usuarioLogado = usuarioRepository.findByUsername(auth.getName());
        if(usuarioLogado.isEmpty()){
            return "Usuário não está logado";
        }

        Usuario usuario = usuarioLogado.get();
        if(usuario.getTipo() != Usuario.Tipo.PROFESSOR){
            return "Apenas professores podem validar minicursos";
        }

        Optional<Minicurso> minicursoOptional = minicursoRepository.findById(id);
        if(minicursoOptional.isEmpty()){
            return "Não existe minicurso com esse id";
        }

        Minicurso minicurso = minicursoOptional.get();
        if(validar){
            minicurso.setStatus(Minicurso.Status.APROVADO);
        }else{
            minicurso.setStatus(Minicurso.Status.CANCELADO);
        }
        minicursoRepository.save(minicurso);
        return "Status de minicurso atualizado";

    }
}
