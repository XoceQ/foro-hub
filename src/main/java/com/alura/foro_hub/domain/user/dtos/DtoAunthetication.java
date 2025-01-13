package com.alura.foro_hub.domain.user.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoAunthetication (
        @NotNull
        String username,
        @NotNull
        String password
) {}
