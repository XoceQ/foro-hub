package com.alura.foro_hub.domain.topic.validations;

import com.alura.foro_hub.domain.profile.ProfileRepository;
import com.alura.foro_hub.domain.topic.dtos.DtoRegisterTopic;
import com.alura.foro_hub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutorValidation implements TopicValidator{
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void validate(DtoRegisterTopic dtoRegisterTopic) {
        if (profileRepository.findById(dtoRegisterTopic.idAutor()).isEmpty()) {
            throw new IntegrityValidation("No profile was found with this id");
        }
    }
}