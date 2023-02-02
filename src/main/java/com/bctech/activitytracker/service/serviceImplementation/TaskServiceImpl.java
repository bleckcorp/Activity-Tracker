package com.bctech.activitytracker.service.serviceImplementation;


import com.bctech.activitytracker.dto.TaskDto;
import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.exceptions.CustomException;
import com.bctech.activitytracker.model.Task;

import com.bctech.activitytracker.model.User;
import com.bctech.activitytracker.repository.TaskRepository;

import com.bctech.activitytracker.repository.UserRepository;
import com.bctech.activitytracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Long saveTask(TaskDto taskRequest, Long userId) {
        log.info("service:: about to save task with task title :: {}", taskRequest.getTitle());

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("User detail cannot be found", HttpStatus.NOT_FOUND);
        });

        // when task is created, status is set to pending, hence dto status is not used
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status("PENDING")
                .user(user)
                .build();

        taskRepository.save(task);

        return task.getTaskId();
    }

    @Override
    public Long updateTask(TaskDto taskRequest) {
        log.info("service:: about to update task with task title :: {}", taskRequest.getTitle());
        Long id = taskRequest.getTaskId();
        Task task = taskRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("Task detail cannot be found", HttpStatus.NOT_FOUND);
        });
        // update task completed timestamp if task status is completed
        if (taskRequest.getStatus().equals("COMPLETED")) {
            task.setCompletedAt(LocalDateTime.now());
        }
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        taskRepository.save(task);

        return task.getTaskId();
    }

    @Override
    public List<TaskDto> getAllTasksOfUser(UserDto user) {
        log.info("service:: about to get all tasks of user :: {}", user.getId());
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        if (tasks.isEmpty()) {
            throw new CustomException("No tasks found", HttpStatus.NOT_FOUND);
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return tasks.stream().map(task ->
                TaskDto.builder()
                        .taskId(task.getTaskId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .completedAt(task.getCompletedAt() != null ? task.getCompletedAt().format(format) : "NOT SET")
                        .updatedAt(task.getUpdatedAt() != null ? task.getUpdatedAt().format(format) : "NOT SET")
                        .createdAt(task.getCreatedAt() != null ? task.getCreatedAt().format(format) : "NOT SET")
                        .status(task.getStatus())
                        .build()).toList();


    }

    @Override
    public List<TaskDto> getAllTasksOfUserAccordingToCategory(UserDto user, String status) {
        log.info("service:: about to get all tasks by status for user :: {}", user.getId());
        List<Task> tasks = taskRepository.findAllByUserIdAndStatus(user.getId(), status);
        if (tasks.isEmpty()) {
            throw new CustomException("No tasks found", HttpStatus.NOT_FOUND);
        }
//            throw new CustomException("No tasks found for this category", HttpStatus.NOT_FOUND);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return tasks.stream().map(task -> TaskDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .completedAt(task.getCompletedAt() != null ? task.getCompletedAt().format(format) : "NOT SET")
                .updatedAt(task.getUpdatedAt() != null ? task.getUpdatedAt().format(format) : "NOT SET")
                .createdAt(task.getCreatedAt() != null ? task.getCreatedAt().format(format) : "NOT SET")
                .description(task.getDescription())
                .status(task.getStatus())
                .build()).toList();
    }

    @Override
    public Boolean deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("Task detail cannot be found", HttpStatus.NOT_FOUND);
        });
        taskRepository.delete(task);
        return true;
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("Task detail cannot be found", HttpStatus.NOT_FOUND);
        });
        return TaskDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }


}
