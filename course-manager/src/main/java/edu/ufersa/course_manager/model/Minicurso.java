package edu.ufersa.course_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "minicurso")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Minicurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Usuario instrutor;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false)
    private Integer vagas;

    @Column(name = "numero_de_inscritos", nullable = false)
    private Integer numeroInscritos;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDENTE;

    public enum Status{
        PENDENTE, APROVADO, CANCELADO
    }
}
