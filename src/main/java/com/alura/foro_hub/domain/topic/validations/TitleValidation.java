package com.alura.foro_hub.domain.topic.validations;

import com.alura.foro_hub.domain.topic.dtos.DtoRegisterTopic;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TitleValidation implements TopicValidator{

    public void validate(DtoRegisterTopic dtoRegisterTopic) {
        if (dtoRegisterTopic.title().length() < 4) {
            throw new ValidationException("The title is too short, remember that it must be descriptive.");
        }
    }
}