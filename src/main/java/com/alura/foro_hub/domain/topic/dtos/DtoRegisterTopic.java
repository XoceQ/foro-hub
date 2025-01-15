package com.alura.foro_hub.domain.topic.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoRegisterTopic(
                               @NotNull
                               String title,
                               @NotNull
                               String message,
                               @NotNull
                               Long idAutor

) { }
