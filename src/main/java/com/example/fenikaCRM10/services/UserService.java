package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.models.enums.Role;
import com.example.fenikaCRM10.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public boolean createUser(User user){
        String email = user.getEmail();
        if (userRepository.findByEmail(user.getEmail())!=null)
            return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        log.info("new user saved with email: {}", email);
        return true;
    }
}
