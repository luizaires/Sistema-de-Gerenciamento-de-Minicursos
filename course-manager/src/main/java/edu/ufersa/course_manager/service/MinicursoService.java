package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.repository.MinicursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinicursoService {
    private final MinicursoRepository repository;

    public MinicursoService(MinicursoRepository repository) {
        this.repository = repository;
    }

    public List<Minicurso> listarTodos() {
        return repository.findAll();
    }

    public Optional<Minicurso> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Minicurso salvar(Minicurso minicurso) {
        return repository.save(minicurso);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
