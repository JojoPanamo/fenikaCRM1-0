package com.example.fenikaCRM10.configirations;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.models.enums.Role;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Создаем первого администратора
        if (userService.findByEmail("stroy.live@mail.ru") == null) {
            User svetlana = new User();
            svetlana.setName("Светлана Халина");
            svetlana.setPhoneNumber("89319652535");
            svetlana.setEmail("stroy.live@mail.ru");
            svetlana.setPercentage(1);
            svetlana.setPassword(passwordEncoder.encode("ThoR558028!@#$"));
            svetlana.setRoles(new HashSet<>(Set.of(Role.ROLE_ADMIN)));
            svetlana.setActive(true);
            userService.createUser(svetlana);
        }

        // Создаем второго администратора
        if (userService.findByEmail("panamo.work@gmail.com") == null) {
            User georgiy = new User();
            georgiy.setName("Георгий Панус");
            georgiy.setPhoneNumber("89811419791");
            georgiy.setEmail("panamo.work@gmail.com");
            georgiy.setPercentage(1);
            georgiy.setPassword(passwordEncoder.encode("19042015GsP!@#$"));
            georgiy.setRoles(new HashSet<>(Set.of(Role.ROLE_ADMIN)));
            georgiy.setActive(true);
            userService.createUser(georgiy);
        }
//        if (userService.findByEmail("work@gmail.com") == null) {
//            User snezha = new User();
//            snezha.setName("Снежана Юрченко");
//            snezha.setPhoneNumber("89811419730");
//            snezha.setEmail("work@gmail.com");
//            snezha.setPercentage(1);
//            snezha.setPassword(passwordEncoder.encode("19042015GsP!@#$"));
//            snezha.setRoles(new HashSet<>(Set.of(Role.ROLE_USER)));
//            snezha.setActive(true);
//            userService.createUser(snezha);
//        }
    }
}
