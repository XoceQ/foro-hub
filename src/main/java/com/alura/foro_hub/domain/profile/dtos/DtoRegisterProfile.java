package com.alura.foro_hub.domain.profile.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoRegisterProfile (
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        Long id_user
) {
}