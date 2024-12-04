package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByDealId(Long dealId);
    @Query(value = "SELECT * FROM comments s WHERE s.deal_id = :dealId ORDER BY s.comment_id DESC LIMIT 1", nativeQuery = true)
    Comments findLastCommentByDealId(@Param("dealId") Long dealId);
}
