package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserService userService;

    @GetMapping("/user-settings")

    public String getUserSettings(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-settings";
    }

    @PostMapping("/update-percentage")
    public String updatePercentage(@RequestParam Long userId, @RequestParam double percentage) {
        User user = userService.findById(userId); // Получаем пользователя по ID
        if (user != null) {
            user.setPercentage(percentage);       // Устанавливаем новый процент
            userService.saveUser(user);           // Сохраняем изменения
        }
        return "redirect:/user-settings";         // Перенаправляем на страницу настроек
    }


}
