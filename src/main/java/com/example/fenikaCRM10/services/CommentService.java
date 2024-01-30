package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Comments;
import com.example.fenikaCRM10.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private List<Comments> allComments = new ArrayList<>();
    private long commentId = 0;
//    private long id = 0;
//    {
//        allComments.add(new Comments(id, commentId, "отгрузка завтра", "28.06.1994"));
//        allComments.add(new Comments(++id, ++commentId, "отгрузка потом", "24.06.2021"));
//    }
//    public List<Comments> listComments(){
//        return allComments;
//    }
    public void saveComment(Comments comments, Long dealId){
        comments.setId(dealId);
        comments.setCommentId(dealId);
        comments.setCurrentDate(DateService.getCurrentDate());
        allComments.add(comments);
    }

    public List<Comments> getCommentsByDealId(Long dealId) {
        List<Comments> commentsByDealId = new ArrayList<>();

        for (Comments comment : allComments) {
            if (comment.getCommentId().equals(dealId)) {
                commentsByDealId.add(comment);
            }
        }

        return commentsByDealId;
    }
}
