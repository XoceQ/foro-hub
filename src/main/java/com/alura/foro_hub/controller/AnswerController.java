package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.answer.Answer;
import com.alura.foro_hub.domain.answer.AnswerRepository;
import com.alura.foro_hub.domain.answer.AnswerService;
import com.alura.foro_hub.domain.answer.dtos.DtoAnswer;
import com.alura.foro_hub.domain.answer.dtos.DtoListAnswers;
import com.alura.foro_hub.domain.answer.dtos.DtoUpdateAnswer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/answers")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Answers", description = "CRUD operations in the entity answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping
    @Operation(summary = "Gets all the answers")
    public ResponseEntity<Page<DtoListAnswers>> listAnswers(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(answerRepository.findByActiveTrue(pageable).map(DtoListAnswers::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets the record of a response by id")
    public ResponseEntity<DtoListAnswers> findAnwerById(@PathVariable Long id) {
        Answer answer = answerRepository.getReferenceById(id);
        return ResponseEntity.ok(new DtoListAnswers(answer));
    }

    @PostMapping
    @Operation(summary = "Records a response in the database")
    public ResponseEntity<DtoListAnswers> createAnswer(@RequestBody @Valid DtoAnswer dtoCreateAnswer, UriComponentsBuilder uriComponentsBuilder) {
        DtoListAnswers dtoListAnswers = answerService.create(dtoCreateAnswer);

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(dtoListAnswers.id()).toUri();
        return ResponseEntity.created(url).body(dtoListAnswers);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update the data of an answer")
    public ResponseEntity<DtoListAnswers> updateAnswer(@RequestBody @Valid DtoUpdateAnswer dtoUpdateAnswer) {
        Answer answer = answerRepository.getReferenceById(dtoUpdateAnswer.id());
        answer.updateAnsw(dtoUpdateAnswer);
        return ResponseEntity.ok(new DtoListAnswers(answer));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Mark a response as inactive")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        Answer answer = answerRepository.getReferenceById(id);
        answer.deactivate();
        return ResponseEntity.noContent().build();
    }

}