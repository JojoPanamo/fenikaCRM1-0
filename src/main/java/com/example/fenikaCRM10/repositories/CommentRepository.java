package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
