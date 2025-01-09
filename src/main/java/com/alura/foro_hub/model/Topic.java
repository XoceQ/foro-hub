package com.alura.foro_hub.model;

import com.alura.foro_hub.model.enums.TopicStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String message;

    @Column(name = "creation_date", updatable = false) // Evita que se actualice
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('ACTIVE', 'CLOSED', 'ARCHIVED')")
    private TopicStatus status = TopicStatus.ACTIVE;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String course;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El título no puede estar vacío") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "El título no puede estar vacío") String title) {
        this.title = title;
    }

    public @NotBlank(message = "El mensaje no puede estar vacío") String getMessage() {
        return message;
    }

    public void setMessage(@NotBlank(message = "El mensaje no puede estar vacío") String message) {
        this.message = message;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public TopicStatus getStatus() {
        return status;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}