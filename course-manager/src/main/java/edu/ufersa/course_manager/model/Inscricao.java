package edu.ufersa.course_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "minicurso_id", nullable = false)
    private Minicurso minicurso;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao;

    @Override
    public String toString() {
        return "Inscricao{" +
                "id=" + id +
                ", usuario=" + usuario.getNome() +
                ", minicurso=" + minicurso.getTitulo() +
                ", dataInscricao=" + dataInscricao +
                '}';
    }
}
