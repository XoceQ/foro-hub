package com.alura.foro_hub.domain.answer.validations;

import com.alura.foro_hub.domain.answer.dtos.DtoAnswer;
import com.alura.foro_hub.domain.topic.TopicRepository;
import com.alura.foro_hub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TopicValidation implements AnswerValidation{

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(DtoAnswer dtoCreateAnswer) {
        if (topicRepository.findById(dtoCreateAnswer.idTopic()).isEmpty()) {
            throw new IntegrityValidation("No topic was found with this id");
        }
    }

}
