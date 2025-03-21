package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.repository.MinicursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinicursoService {
    private final MinicursoRepository minicursoRepository;

    public MinicursoService(MinicursoRepository minicursoRepository) {
        this.minicursoRepository = minicursoRepository;
    }

    public List<Minicurso> listarMinicursos() {
        return minicursoRepository.findAll();
    }

    public Optional<Minicurso> buscarPorId(Long id) {
        return minicursoRepository.findById(id);
    }

    public List<Minicurso> buscarPorInstrutor(Usuario instrutor) {
        return minicursoRepository.findByInstrutor(instrutor);
    }

    public List<Minicurso> buscarPorTitulo(String titulo) {
        return minicursoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Minicurso cadastrarMinicurso(Minicurso minicurso) {
        return minicursoRepository.save(minicurso);
    }

    public void deletarMinicursoPorId(Long id) {
        minicursoRepository.deleteById(id);
    }

    public void incrementarNumeroDeInscritos(Minicurso minicurso) {
        if (minicurso.getNumeroInscritos() < minicurso.getVagas()) {
            minicurso.setNumeroInscritos(minicurso.getNumeroInscritos() + 1);
            minicursoRepository.save(minicurso);
        }
    }
}
