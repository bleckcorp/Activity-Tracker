package com.bctech.activitytracker.controller;

import com.bctech.activitytracker.dto.TaskDto;
import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


// Post controller class handles all customers post and directs request flow
@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


//    Method that adds form input to the database and redirects to viewPost method handler
    @PostMapping("/addTask")
    public String addTask(@Valid TaskDto task, @SessionAttribute("currentUser") UserDto user) {
        taskService.saveTask(task, user.getId());
        return "redirect:/viewTasks";
    }


//    Method handler retrieves all data from table and adds it to a model
    @GetMapping("/viewTasks")
    public String viewAllPost(Model model, @SessionAttribute("currentUser") UserDto user){

        List<TaskDto> allTasks = taskService.getAllTasksOfUser(user);
        model.addAttribute("allTasks", allTasks);
        return "tasks";
    }

// Method handle enables user to delete specific task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id){
        taskService.deleteTask(id);
        return "redirect:/viewTasks";
    }

// Method handler enbles the user to edit a specific task
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable ("id") Long id, Model model, HttpSession session){
        TaskDto task = new TaskDto();
        model.addAttribute("editTask", task);
        session.setAttribute("task", id);
        return "edit";
    }

// Method handler for saving back edited post
    @PostMapping("/editTask")
    public String updateTask(@Valid @ModelAttribute ("editTask") TaskDto task, @SessionAttribute("task") Long id) {
        task.setTaskId(id);
        taskService.updateTask(task);
        return "redirect:/viewTasks";
    }

}
