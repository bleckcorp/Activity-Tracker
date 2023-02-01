package com.bctech.activitytracker.service;

import com.bctech.activitytracker.dto.TaskDto;
import com.bctech.activitytracker.dto.UserDto;

import java.util.List;

public interface TaskService {


    //    method signature for handling saving of data
    Long saveTask(TaskDto taskRequest, Long userId);

    Long updateTask(TaskDto taskRequest);

    //    method signature for handling the fetching of our datas
    List<TaskDto> getAllTasksOfUser(UserDto user);

    List<TaskDto> getAllTasksOfUserAccordingToStatus(UserDto user, String status);
    //    method signature for deleting a specific post
    Boolean deleteTask(Long id);

    //    method signature for seleting a specific post
    TaskDto getTaskById(Long id);
}
