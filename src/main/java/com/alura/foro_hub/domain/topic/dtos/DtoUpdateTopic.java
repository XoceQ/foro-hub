package com.alura.foro_hub.domain.topic.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoUpdateTopic(
                             @NotNull
                             Long id,
                             String title,
                             String message,
                             Boolean status
) {
}