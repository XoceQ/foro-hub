package com.alura.foro_hub.controller;

import com.alura.foro_hub.exception.ResourceNotFoundException;
import com.alura.foro_hub.model.Topic;
import com.alura.foro_hub.repository.TopicRepository;
import com.alura.foro_hub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    // Crear un nuevo tópico
    @PostMapping
    public ResponseEntity<String> createTopic(@Valid @RequestBody Topic topic) {
        // Verificar si el tópico con el título y mensaje ya existe
        if (topicRepository.existsByTitleAndMessage(topic.getTitle(), topic.getMessage())) {
            return new ResponseEntity<>("Duplicate topic. The title and message must be unique.", HttpStatus.BAD_REQUEST);
        }
        // Asignar creationDate automáticamente si es nulo
        if (topic.getCreationDate() == null) {
            topic.setCreationDate(LocalDateTime.now());
        }


        topicRepository.save(topic);
        return new ResponseEntity<>("Topic created successfully.", HttpStatus.CREATED);
    }

    // Obtener todos los tópicos
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    // Obtener un tópico específico
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);

        // Si no se encuentra el tópico, lanzar una excepción
        if (topic == null) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        return ResponseEntity.ok(topic);
    }

    // Actualizar un tópico
    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Topic topic = topicService.updateTopic(id, updatedTopic);

        // Si no se encuentra el tópico, lanzar una excepción
        if (topic == null) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        return ResponseEntity.ok(topic);
    }

    // Eliminar un tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        // Verificar si el tópico existe antes de eliminar
        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}