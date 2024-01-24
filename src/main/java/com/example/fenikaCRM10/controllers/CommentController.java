package com.example.fenikaCRM10.controllers;


import com.example.fenikaCRM10.models.Comments;
import com.example.fenikaCRM10.services.CommentService;
import com.example.fenikaCRM10.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final DealService dealService;

    @GetMapping("/comments/{id}")
    public String allComments(Model model, @PathVariable Long id){
        model.addAttribute("dealId", dealService.getDealById(id));
        model.addAttribute("comments", commentService.getCommentsByDealId(id));
        return "comments";
    }
    @PostMapping("/saveComment/{id}")
    public String saveComment(Comments comments, @PathVariable Long id) {
//        commentService.saveComment(comments.setId(id));
//        commentService.saveComment(comments.setCommentId(id));
        commentService.saveComment(comments, id);
        return "redirect:/comments/" + id;
    }
}
