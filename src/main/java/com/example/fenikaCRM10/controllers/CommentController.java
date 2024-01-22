package com.example.fenikaCRM10.controllers;


import com.example.fenikaCRM10.services.CommentService;
import com.example.fenikaCRM10.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final DealService dealService;

    @GetMapping("/comments/{id}")
    public String comments(Model model, @PathVariable Long id){
        model.addAttribute("dealId", dealService.getDealById(id));
        model.addAttribute("comments", commentService.getCommentsByDealId(id));
        return "comments";
    }
}
