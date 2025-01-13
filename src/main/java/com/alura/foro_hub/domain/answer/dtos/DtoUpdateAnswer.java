package com.alura.foro_hub.domain.answer.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoUpdateAnswer(@NotNull
                               Long id,
                              String message,
                              Boolean solution
) {}