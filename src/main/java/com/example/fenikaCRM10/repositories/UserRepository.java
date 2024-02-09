package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
