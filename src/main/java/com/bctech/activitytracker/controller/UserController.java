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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// User controller class that handles customers details
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
    public String registerUser(@Valid @ModelAttribute("newUser") UserRequestDTO user, BindingResult result, RedirectAttributes redirectAttributes,
                               Model model) {
        if(result.hasErrors()){
            return "index";
        }
        try { userService.createUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "index";

        }
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
                        RedirectAttributes redirectAttributes,
                        Model model,
                        HttpSession session) {

        try {
            UserDto user = userService.validateUser(username, password);
            session.setAttribute("currentUser", user);
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "login";
        }
    }

//    method handler that add TaskDTo data to the model and redirects to home
    @GetMapping("/home")
    public String showHome(TaskDto task, Model model) {
        model.addAttribute("taskDto", task);
        return "home";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session ) {
        session.invalidate();
        return "redirect:/login";
    }

}
