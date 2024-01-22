package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Comments;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private List<Comments> allComments = new ArrayList<>();
    private long commentId = 1;
    private long id = 1;
    {
        allComments.add(new Comments(id, commentId, "отгрузка завтра", "28.06.1994"));
        allComments.add(new Comments(++id, ++commentId, "отгрузка потом", "24.06.2021"));
    }
    public List<Comments> listComments(){
        return allComments;
    }
    public void saveComment(Comments comments){
        comments.setId(++commentId);
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
