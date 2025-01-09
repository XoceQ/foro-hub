package com.alura.foro_hub.service;

import com.alura.foro_hub.model.Topic;
import com.alura.foro_hub.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
    }

    public Topic updateTopic(Long id, Topic updatedTopic) {
        Topic existingTopic = getTopicById(id);
        existingTopic.setTitle(updatedTopic.getTitle());
        existingTopic.setMessage(updatedTopic.getMessage());
        existingTopic.setStatus(updatedTopic.getStatus());
        existingTopic.setAuthor(updatedTopic.getAuthor());
        existingTopic.setCourse(updatedTopic.getCourse());
        return topicRepository.save(existingTopic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }


}