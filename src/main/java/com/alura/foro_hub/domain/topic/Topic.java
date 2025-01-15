package com.alura.foro_hub.domain.topic;


import com.alura.foro_hub.domain.profile.Profile;
import com.alura.foro_hub.domain.topic.dtos.DtoUpdateTopic;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
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
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate; // Campo para registrar la última actualización


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Profile profile;


    public Topic() {

    }

    public Topic(String title, String message, Profile profile) {
        this.title = title;
        this.message = message;
        this.creation_date = LocalDateTime.now();  // Usamos la fecha actual para creation_date
        this.status = true;  // Estado por defecto
        this.active = true;  // Por defecto, el topic estará activo
        this.profile = profile;
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = LocalDateTime.now();
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



    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
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
        this.updateDate = LocalDateTime.now(); // Actualiza la fecha

    }

    public void deactivateTopic() {
        this.active = false;
    }
}