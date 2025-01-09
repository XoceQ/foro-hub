package com.alura.foro_hub.controller;

import com.alura.foro_hub.exception.ResourceNotFoundException;
import com.alura.foro_hub.model.Topic;
import com.alura.foro_hub.repository.TopicRepository;
import com.alura.foro_hub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    // Obtener todos los tópicos con paginación
    @GetMapping
    public ResponseEntity<Page<Topic>> getAllTopics(@PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Topic> topics = topicService.getAllTopics(pageable);
        return ResponseEntity.ok(topics);
    }


    // Obtener un tópico específico
    @GetMapping("{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);

        // Si no se encuentra el tópico, lanzar una excepción
        if (topic == null) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        return ResponseEntity.ok(topic);
    }

    @GetMapping("/topics")
    public ResponseEntity<Page<Topic>> getTopics(
            @RequestParam(required = false) String course, // Parámetro opcional
            @RequestParam(required = false) Integer year, // Parámetro opcional
            @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Topic> topics;

        if (course != null && year != null) {
            // Filtrar por curso y año
            topics = topicService.getTopicsByCourseAndYear(course, year, pageable);
        } else {
            // Si no se proporcionan filtros, devolver todos los tópicos
            topics = topicService.getAllTopics(pageable);
        }

        return ResponseEntity.ok(topics);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Topic>> filterTopics(
            @RequestParam String course,
            @RequestParam int year,
            @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Topic> topics = topicService.getTopicsByCourseAndYear(course, year, pageable);
        return ResponseEntity.ok(topics);
    }



    // Actualizar un tópico
    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody @Valid Topic updatedTopic) {
        // Llamar al servicio para actualizar el tópico
        Topic updated = topicService.updateTopic(id, updatedTopic);

        // Devolver el tópico actualizado con estado 200 OK
        return ResponseEntity.ok(updated);
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