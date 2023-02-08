package com.bctech.activitytracker.service;

import com.bctech.activitytracker.dto.TaskDto;
import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.model.Task;

import java.util.List;

public interface TaskService {


    //    method signature for handling saving of data
    Task saveTask(TaskDto taskRequest, Long userId);


    Long updateTask(TaskDto taskRequest, UserDto user);

    //    method signature for handling the fetching of our datas
    List<TaskDto> getAllTasksOfUser(UserDto user);


    List<TaskDto> getAllTasksOfUserAccordingToCategory(UserDto user, String status);

    //    method signature for deleting a specific post
    Boolean deleteTask(Long id,UserDto user);

    //    method signature for seleting a specific post
    TaskDto getTaskById(Long id);
}
