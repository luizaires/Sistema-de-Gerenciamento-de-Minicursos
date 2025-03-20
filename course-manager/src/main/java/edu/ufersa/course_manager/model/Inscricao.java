package edu.ufersa.course_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "minicurso_id", nullable = false)
    private Minicurso minicurso;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario inscrito;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao;
}
