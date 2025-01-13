package com.alura.foro_hub.domain.topic.validations;

import com.alura.foro_hub.domain.topic.dtos.DtoRegisterTopic;

public interface TopicValidator {
    void validate(DtoRegisterTopic dtoRegisterTopic);

}
