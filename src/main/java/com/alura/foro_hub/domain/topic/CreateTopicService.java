package com.alura.foro_hub.domain.topic;


import com.alura.foro_hub.domain.profile.Profile;
import com.alura.foro_hub.domain.profile.ProfileRepository;
import com.alura.foro_hub.domain.topic.dtos.DtoRegisterTopic;
import com.alura.foro_hub.domain.topic.dtos.DtoTopicList;
import com.alura.foro_hub.domain.topic.validations.TopicValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateTopicService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    List<TopicValidator> validatorList;

    public DtoTopicList create(DtoRegisterTopic dtoRegisterTopic) {
        // validations
        validatorList.forEach(v -> v.validate(dtoRegisterTopic));
        // relations
        Profile profile = profileRepository.findById(dtoRegisterTopic.idAutor()).get();
        // instance of topic
        Topic topic = new Topic(dtoRegisterTopic.title(), dtoRegisterTopic.message(), profile);
        topicRepository.save(topic);

        return new DtoTopicList(topic);
    }
}
