package com.example.fenikaCRM10.configirations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
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
                        .requestMatchers("/", "/login", "/registration", "/public/**").permitAll()
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

//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final CustomUserDetailsService userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(
//                                "/",
//                                "/deal-info/**",
//                                "/deal-create/**",
//                                "/files/**",
//                                "/payments/**",
//                                "/statuses/**",
//                                "/deals/**",
//                                "/comments/**",
//                                "/registration"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/deals/", true) // Убедись, что это URL существующей страницы
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }