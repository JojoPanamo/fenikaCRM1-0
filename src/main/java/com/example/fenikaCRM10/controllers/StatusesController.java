package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.StatusesListService;
import com.example.fenikaCRM10.services.StatusesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StatusesController {
    private final StatusesService statusesService;
    private final DealService dealService;
    @GetMapping("/statuses/{id}")
    public String allStatuses(Model model, @PathVariable Long id) {
        model.addAttribute("dealId", dealService.getDealById(id));
        model.addAttribute("allStatuses", statusesService.getStatusesByDealId(id));
        model.addAttribute("statusList", StatusesListService.getStatusesList());
        return "statuses";
    }
    @PostMapping("/saveStatus/{id}")
    public String saveStatus(Statuses statuses, @PathVariable Long id) {
//        commentService.saveComment(comments.setId(id));
//        commentService.saveComment(comments.setCommentId(id));
        statusesService.saveStatus(statuses, id);
        return "redirect:/statuses/" + id;
    }
}
