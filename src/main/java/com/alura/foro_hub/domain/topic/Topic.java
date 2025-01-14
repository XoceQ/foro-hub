package com.alura.foro_hub.domain.topic;

import com.alura.foro_hub.domain.answer.Answer;
import com.alura.foro_hub.domain.course.Course;
import com.alura.foro_hub.domain.profile.Profile;
import com.alura.foro_hub.domain.topic.dtos.DtoUpdateTopic;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private LocalDateTime creation_date;
    private Boolean status;
    private Boolean active;

    @OneToMany(mappedBy = "topic")
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_course")
    private Course course;


    public Topic(Long id, String title, String message, LocalDateTime creation_date, Boolean status, Boolean active, List<Answer> answerList, Profile profile, Course course) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.creation_date = creation_date;
        this.status = status;
        this.active = active;
        this.answerList = answerList;
        this.profile = profile;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void updateData(DtoUpdateTopic dtoUpdateTopic) {
        if (dtoUpdateTopic.status() != null) {
            this.status = dtoUpdateTopic.status();
        }
        if (dtoUpdateTopic.title() != null) {
            this.title = dtoUpdateTopic.title();
        }
        if (dtoUpdateTopic.message() != null) {
            this.message = dtoUpdateTopic.message();
        }
    }

    public void deactivateTopic() {
        this.active = false;
    }
}