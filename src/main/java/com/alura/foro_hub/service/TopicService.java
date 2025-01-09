package com.alura.foro_hub.service;

import com.alura.foro_hub.exception.ResourceNotFoundException;
import com.alura.foro_hub.model.Topic;
import com.alura.foro_hub.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Page<Topic> getAllTopics(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
    }


    public Page<Topic> getTopicsByCourseAndYear(String course, int year, Pageable pageable) {
        return topicRepository.findTopicsByCourseAndYear(course, year, pageable);
    }

    public Topic updateTopic(Long id, Topic updatedTopic) {
        // Buscar el tópico por su ID
        Optional<Topic> optionalTopic = topicRepository.findById(id);

        // Si el tópico no existe, lanzar una excepción
        if (optionalTopic.isEmpty()) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }

        // Obtener el tópico existente
        Topic existingTopic = optionalTopic.get();

        // Actualizar los campos del tópico con los valores proporcionados
        existingTopic.setTitle(updatedTopic.getTitle());
        existingTopic.setMessage(updatedTopic.getMessage());
        existingTopic.setStatus(updatedTopic.getStatus());
        existingTopic.setAuthor(updatedTopic.getAuthor());
        existingTopic.setCourse(updatedTopic.getCourse());
        existingTopic.setUpdatedDate(LocalDateTime.now()); // Establecer fecha de actualización


        // Guardar el tópico actualizado en la base de datos
        return topicRepository.save(existingTopic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }


}