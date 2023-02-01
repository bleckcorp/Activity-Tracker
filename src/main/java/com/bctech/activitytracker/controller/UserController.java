package com.bctech.activitytracker.controller;

import com.bctech.activitytracker.dto.TaskDto;
import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.dto.UserRequestDTO;
import com.bctech.activitytracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

// User controller class that handles customers and launderer's details
@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    Method handler that directs default call to the index page
    @GetMapping(value = {"/", "/signup"})
    public String index(Model model) {
        model.addAttribute("newUser", new UserRequestDTO());
        return "index";
    }

// Method handler that adds users details to database and redirects users to login
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("newUser") UserRequestDTO user, BindingResult result) {
        if(result.hasErrors()){
            return "index";
        }
       userService.createUser(user);
        return "redirect:/login";
    }

//    Sends login view page
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

//    Method handler that validates users password and username
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        UserDto user = userService.validateUser(username, password);
        if (user == null) {
            return "redirect:/login";
        }
        session.setAttribute("currentUser", user);
        return "redirect:/home";
    }

//    method handler that add TaskDTo data to the model and redirects to home
    @GetMapping("/home")
    public String showHome(TaskDto task, Model model) {
        model.addAttribute("taskDto", task);
        return "home";
    }

}
