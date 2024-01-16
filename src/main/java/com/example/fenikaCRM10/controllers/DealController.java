package com.example.fenikaCRM10.controllers;
import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.fenikaCRM10.services.UserService;

@Controller
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @GetMapping("/")
    public String deals(Model model) {
        model.addAttribute("deals", dealService.listDeals());
        return "deals";
    }
    @PostMapping("/deal-create/deal-create-save")
    public String createDealSave(Deal deal) {
        dealService.saveDeal(deal);
        return "redirect:/";
    }

    @PostMapping("/deal-info/deal/delete/{id}")
    public String deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        return "redirect:/";
    }

    @GetMapping("deal-info/{id}")
    public String dealInfo(@PathVariable Long id, Model model) {
        model.addAttribute("deal", dealService.getDealById(id));
        return "deal-info";
    }
    @GetMapping("/deal-create/")
    public String dealCreatePAge(Model model) {
        model.addAttribute("authors", UserService.getAuthors());
//        model.addAttribute("deal", dealService.listDeals());
        return "deal-create";
    }
}
