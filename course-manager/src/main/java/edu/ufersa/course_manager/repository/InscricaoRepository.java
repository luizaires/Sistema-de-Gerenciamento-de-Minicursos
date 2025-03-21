package edu.ufersa.course_manager.repository;

import edu.ufersa.course_manager.model.Inscricao;
import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByMinicurso(Minicurso minicurso);
    int countByMinicurso(Minicurso minicurso);
    Optional<Object> findByUsuarioAndMinicurso(Usuario usuario, Minicurso minicurso);
    boolean existsByMinicursoAndUsuario(Minicurso minicurso, Usuario usuario);
    Optional<Inscricao> findByMinicursoAndUsuario(Minicurso minicurso, Usuario usuario);
    List<Inscricao> findByUsuario(Usuario usuario);
}