package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.models.enums.Role;
import com.example.fenikaCRM10.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) {
            return false; // Пользователь с такой электронной почтой уже существует
        }
        user.setActive(true);
        user.setPercentage(1);
        if (!user.getPassword().startsWith("$2a$")) { // "$2a$" — префикс для BCrypt-хешей
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles().isEmpty()) {
            user.getRoles().add(Role.ROLE_USER);
        }
        log.info("New user saved with email: {}", email);
        userRepository.save(user); // Сохраняем пользователя в базе данных
        return true; // Успешное создание пользователя
    }
    public Long findUserIdByPrincipal(Principal principal) {
        // Получаем пользователя по имени (которое является email в Principal)
        User user = userRepository.findByEmail(principal.getName());
        if (user != null) {
            return user.getUserId(); // Возвращаем id пользователя
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
    public User findByPrincipal(CustomUserDetails customUserDetails) {
        return userRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
}

