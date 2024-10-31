package com.example.fenikaCRM10.configirations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Создание BCryptPasswordEncoder с дефолтной силой шифрования
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Разрешить доступ к публичным ресурсам
                        .requestMatchers("/**", "/login", "/public/**").permitAll()
                        .requestMatchers("/registration").hasRole("ADMIN")
                        .anyRequest().authenticated() // Остальные запросы требуют аутентификации

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/statistics", true) // После успешного входа перенаправить сюда
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для логаута
                        .logoutSuccessUrl("/login?logout") // Редирект после логаута
                        .permitAll()

                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Отключаем сессии
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Включение сессий при необходимости
                )
                .csrf(csrf -> csrf.disable()); // Отключаем CSRF для использования с Stateless


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}