package com.alura.foro_hub.domain.profile;

import com.alura.foro_hub.domain.answer.Answer;
import com.alura.foro_hub.domain.profile.dtos.DtoUpdateProfile;
import com.alura.foro_hub.domain.topic.Topic;
import com.alura.foro_hub.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "profiles")
@Entity(name = "Profile")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "profile",  cascade = CascadeType.ALL)
    private List<Topic> topicList;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Answer> answerList;

    public Profile(Long id, String name, String email, Boolean active, User user, List<Topic> topicList, List<Answer> answerList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.user = user;
        this.topicList = topicList;
        this.answerList = answerList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public void updateData(DtoUpdateProfile dtoUpdateProfile) {
        if (dtoUpdateProfile.name() != null) {
            this.name = dtoUpdateProfile.name();
        }
        if (dtoUpdateProfile.email() != null) {
            this.email = dtoUpdateProfile.email();
        }
    }

    public void deactivateProfile() {
        this.active = false;
    }
}