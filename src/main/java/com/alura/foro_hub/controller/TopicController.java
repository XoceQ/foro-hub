package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.topic.CreateTopicService;
import com.alura.foro_hub.domain.topic.Topic;
import com.alura.foro_hub.domain.topic.TopicRepository;
import com.alura.foro_hub.domain.topic.dtos.DtoRegisterTopic;
import com.alura.foro_hub.domain.topic.dtos.DtoTopicList;
import com.alura.foro_hub.domain.topic.dtos.DtoUpdateTopic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")

public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CreateTopicService createTopicService;

    @GetMapping
    @Operation(summary = "Gets all topics")
    public ResponseEntity<Page<DtoTopicList>> listTopics(@PageableDefault(size = 5) Pageable pagination)  {
        return ResponseEntity.ok(topicRepository.findByActiveTrue(pagination).map(DtoTopicList::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets the record of a topic with ID")
    public ResponseEntity<DtoTopicList> findTopicById(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);

        return ResponseEntity.ok(new DtoTopicList(topic));
    }

    @PostMapping
    @Operation(summary = "Register a new topic in the database")
    public ResponseEntity<DtoTopicList> create(@RequestBody @Valid DtoRegisterTopic dtoRegisterTopic, UriComponentsBuilder uriComponentsBuilder) {
        DtoTopicList result = createTopicService.create(dtoRegisterTopic);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(result.id()).toUri();
        return  ResponseEntity.created(url).body(result);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update the data of an existing topic")
    public ResponseEntity<DtoTopicList> updateTopic(@RequestBody @Valid DtoUpdateTopic dtoUpdateTopic) {
        Topic topic = topicRepository.getReferenceById(dtoUpdateTopic.id());
        topic.updateData(dtoUpdateTopic);
        return ResponseEntity.ok(new DtoTopicList(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Mark a registered topic as inactive")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topic.deactivateTopic();
        return ResponseEntity.noContent().build();
    }
}