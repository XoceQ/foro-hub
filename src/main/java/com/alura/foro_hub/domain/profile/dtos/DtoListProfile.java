package com.alura.foro_hub.domain.profile.dtos;

import java.util.List;

public record DtoListProfile (
        Long id,
        String nombre,
        String email,
        List<String> topicos
){}