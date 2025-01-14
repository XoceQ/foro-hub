package com.alura.foro_hub.domain.profile.dtos;

import java.util.List;

public record DtoListProfile (
        Long id,
        String name,
        String email,
        List<String> topics
){}