package com.alura.foro_hub.domain.answer.validations;

import com.alura.foro_hub.domain.answer.dtos.DtoAnswer;

public interface AnswerValidation {
    void validate(DtoAnswer dtoCreateAnswer);

}
