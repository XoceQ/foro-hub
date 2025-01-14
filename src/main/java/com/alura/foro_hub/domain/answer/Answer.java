package com.alura.foro_hub.domain.answer;

import com.alura.foro_hub.domain.answer.dtos.DtoUpdateAnswer;
import com.alura.foro_hub.domain.profile.Profile;
import com.alura.foro_hub.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Table(name = "answers")
@Entity(name = "Answer")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime creation_date;
    private Boolean solution;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topic")
    private Topic topic;

    public Answer(Long id, String message, LocalDateTime creation_date, Boolean solution, Boolean active, Profile profile, Topic topic) {
        this.id = id;
        this.message = message;
        this.creation_date = creation_date;
        this.solution = solution;
        this.active = active;
        this.profile = profile;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public Boolean getSolution() {
        return solution;
    }

    public Boolean getActive() {
        return active;
    }

    public Profile getProfile() {
        return profile;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setSolution(Boolean solution) {
        this.solution = solution;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void updateAnsw(DtoUpdateAnswer dtoUpdateAnswer) {
        if (dtoUpdateAnswer.solution()) {
            this.solution = dtoUpdateAnswer.solution();
        }
        if (dtoUpdateAnswer.message() != null) {
            this.message = dtoUpdateAnswer.message();
        }
    }

    public void deactivate() {
        this.active = false;
    }
}