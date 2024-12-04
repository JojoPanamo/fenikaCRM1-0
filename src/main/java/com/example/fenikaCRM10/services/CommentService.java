package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Comments;
import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
//    private List<Comments> allComments = new ArrayList<>();
//    private long commentId = 0;
//    private long id = 0;
//    {
//        allComments.add(new Comments(id, commentId, "отгрузка завтра", "28.06.1994"));
//        allComments.add(new Comments(++id, ++commentId, "отгрузка потом", "24.06.2021"));
//    }
//    public List<Comments> listComments(){
//        return allComments;
//    }
    public void saveComment(Comments comments, Long dealId){
        comments.setDealId(dealId);
//        comments.setCommentId(dealId);
        comments.setCurrentDate(DateService.getCurrentDate());
        commentRepository.save(comments);
    }

    public List<Comments> getCommentsByDealId(Long dealId) {
        return commentRepository.findAllByDealId(dealId);
    }

    public Long getDealByCommentId(Long commentId) {
        Comments comments = commentRepository.findById(commentId).orElse(null);
        if (comments != null) {
            return comments.getDealId();
        } else {
            return null;
        }
    }
    public String getLastCommentForDeal(Long dealId) {
        Comments latestComment = commentRepository.findLastCommentByDealId(dealId);
        return latestComment != null ? latestComment.getComment() : null;
    }

}
