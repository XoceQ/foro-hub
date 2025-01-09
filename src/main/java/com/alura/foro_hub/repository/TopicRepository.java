package com.alura.foro_hub.repository;

import com.alura.foro_hub.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndMessage(String title, String message);

    @Query("SELECT t FROM Topic t WHERE t.course LIKE %:course% AND FUNCTION('YEAR', t.creationDate) = :year")
    Page<Topic> findTopicsByCourseAndYear(@Param("course") String course, @Param("year") int year, Pageable pageable);


}
