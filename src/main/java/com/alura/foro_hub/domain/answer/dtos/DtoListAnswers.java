package com.alura.foro_hub.domain.answer.dtos;

import com.alura.foro_hub.domain.answer.Answer;

import java.time.LocalDateTime;

public record DtoListAnswers(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean esSolucion,
        Long idAutor,
        String autor,
        Long idTopic,
        String topic
) {
    public DtoListAnswers(Answer answer) {
        this(answer.getId(), answer.getMessage(), answer.getCreation_date(), answer.getSolution(),
                answer.getProfile().getId(), answer.getProfile().getName(),
                answer.getTopic().getId(), answer.getTopic().getTitle());
    }
}

