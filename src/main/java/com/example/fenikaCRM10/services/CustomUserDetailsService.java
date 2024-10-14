package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Получаем пользователя из репозитория по email
        User user = userRepository.findByEmail(email);

        // Если пользователь не найден, логируем ошибку и выбрасываем исключение
        if (user == null) {
            log.error("User not found: {}", email);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("User found: {}", email);

        // Возвращаем объект CustomUserDetails, который реализует интерфейс UserDetails
        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.getRoles()
        );
    }
}
