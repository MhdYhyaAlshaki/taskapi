package com.example.taskapi.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
           "WHERE (:userId IS NULL OR t.user.id = :userId) " +
           "AND (:completed IS NULL OR t.completed = :completed) " +
           "AND (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) ESCAPE '!')")
    Page<Task> search(@Param("userId") Long userId,
                      @Param("completed") Boolean completed,
                      @Param("title") String title,
                      Pageable pageable);
}
