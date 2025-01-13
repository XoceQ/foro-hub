package com.alura.foro_hub.domain.answer.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoAnswer(
    @NotNull
    String message,
    @NotNull
    Long idAutor,
    @NotNull
    Long idTopic
) {}
