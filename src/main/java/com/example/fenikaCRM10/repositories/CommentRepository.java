package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByDealId(Long dealId);
}
