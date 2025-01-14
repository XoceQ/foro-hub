package com.alura.foro_hub.domain.topic.validations;

import com.alura.foro_hub.domain.topic.dtos.DtoRegisterTopic;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MessageLengthValidation implements TopicValidator {

    public void validate(DtoRegisterTopic dtoRegisterTopic) {
        if (dtoRegisterTopic.message().length() < 10) {
            throw new ValidationException("The message is too short, remember to be descriptive.");
        }
        if (dtoRegisterTopic.message().length() > 99) {
            throw new ValidationException("The message is too long");
        }
    }
}