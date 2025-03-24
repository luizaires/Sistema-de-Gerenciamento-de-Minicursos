package edu.ufersa.course_manager.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record MinicursoDto(
        @NotBlank String titulo,
        @NotBlank String descricao,
        int cargaHoraria,
        @NotBlank int vagas,
        @NotBlank LocalDate dataInicio,
        @NotBlank LocalDate dataFim
        ) {

}
