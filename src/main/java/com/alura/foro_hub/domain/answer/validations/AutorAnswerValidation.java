package com.alura.foro_hub.domain.answer.validations;

import com.alura.foro_hub.domain.answer.dtos.DtoAnswer;
import com.alura.foro_hub.domain.profile.ProfileRepository;
import com.alura.foro_hub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AutorAnswerValidation implements AnswerValidation{

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void validate(DtoAnswer dtoCreateAnswer) {
        if (profileRepository.findById(dtoCreateAnswer.idAutor()).isEmpty()) {
            throw new IntegrityValidation("No fue encontrado un perfil con este id");
        }
    }

}
