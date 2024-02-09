package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("")
    public String createUser(User user, Model model){
        if (!userService.createUser(user)){
            model.addAttribute("errorMessage",
                    "Пользователь с почтой: " + user.getEmail() + "уже  есть");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/welcome")
    public String securityUrl(){
        return "welcome";
    }
}
