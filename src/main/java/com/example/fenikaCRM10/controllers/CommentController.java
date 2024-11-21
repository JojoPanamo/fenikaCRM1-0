package com.example.fenikaCRM10.controllers;


import com.example.fenikaCRM10.models.Comments;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.CommentService;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    @GetMapping("/comments/{dealId}")
    public String allComments(Model model, @PathVariable Long dealId){
        // Получение текущего пользователя
        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetailsInfo.getId());

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("dealId", dealService.getDealById(dealId));
        model.addAttribute("comments", commentService.getCommentsByDealId(dealId));
        return "comments";
    }
    @PostMapping("/saveComment/{dealId}")
    public String saveComment(Comments comments, @PathVariable Long dealId) {
//        commentService.saveComment(comments.setId(id));
//        commentService.saveComment(comments.setCommentId(id));
        commentService.saveComment(comments, dealId);
        return "redirect:/comments/"+ dealId;
    }
    @GetMapping ("/comments/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
//        dealId = commentService.getDealByCommentId(commentId);
        return "redirect:/deal-info/" + dealId;
    }
    @GetMapping ("/comments/{dealId}/todeals")
    public String onBackToDealsPressed(@PathVariable Long dealId) {
        return "redirect:/";
    }
}
