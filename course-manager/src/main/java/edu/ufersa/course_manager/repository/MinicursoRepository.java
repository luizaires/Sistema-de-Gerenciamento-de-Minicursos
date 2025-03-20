package edu.ufersa.course_manager.repository;

import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinicursoRepository extends JpaRepository<Minicurso, Long> {
    List<Minicurso> findByInstrutor(Usuario instrutor);
    List<Minicurso> findByTituloContainingIgnoreCase(String titulo);
}