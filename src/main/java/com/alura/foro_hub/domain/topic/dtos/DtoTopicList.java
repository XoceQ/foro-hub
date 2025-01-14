package com.alura.foro_hub.domain.topic.dtos;

import com.alura.foro_hub.domain.topic.Topic;

import java.time.LocalDateTime;

public record DtoTopicList(
        Long id,
        String title,
        String message,
        Boolean active,
        Long authorId,
        String author,
        Long courseId,
        String course,
        LocalDateTime creationDate
) {
    public DtoTopicList(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getStatus(),
                topic.getProfile().getId(),
                topic.getProfile().getName(),
                topic.getCourse().getId(),
                topic.getCourse().getName(),
                topic.getCreation_date()
        );
    }
}