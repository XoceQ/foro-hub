package com.alura.foro_hub.domain.profile.dtos;

import jakarta.validation.constraints.NotNull;

public record DtoUpdateProfile(
                               @NotNull
                               Long id,
                               String name,
                               String email
) { }