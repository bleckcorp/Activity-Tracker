package com.bctech.activitytracker.repository;

import com.bctech.activitytracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserId(Long id);
    List<Task> findAllByUserIdAndStatus(Long id, String status);

    Optional<Task> findByTaskIdAndUserId(Long taskId, Long userId);
}
