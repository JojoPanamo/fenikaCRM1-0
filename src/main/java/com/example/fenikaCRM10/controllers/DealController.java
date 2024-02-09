package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.UserServiceList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @GetMapping("/")
    public String deals(@RequestParam(name = "name", required = false) String name, Model model) {
        model.addAttribute("deals", dealService.listDeals(name));
        return "deals";
    }
    @PostMapping("/deal-create/deal-create-save")
    public String createDealSave(Deal deal) {
        dealService.saveDeal(deal);
        return "redirect:/";
    }
//    @GetMapping("/")
//    public String dealByName(@RequestParam(name = "name", required = false) String name, Model model){
//        model.addAttribute("dealByName", dealService.dealByName(name));
//        return "deals";
//    }

//    @PostMapping("/deal-info/deal/delete/{id}")
//    public String deleteDeal(@PathVariable Long id) {
//        dealService.deleteDeal(id);
//        return "redirect:/";
//    }

    @GetMapping("deal-info/{dealId}")
    public String dealInfo(@PathVariable Long dealId, Model model) {
        model.addAttribute("deal", dealService.getDealById(dealId));
        return "deal-info";
    }
    @GetMapping("/deal-create/")
    public String dealCreatePAge(Model model) {
        model.addAttribute("authors", UserServiceList.getAuthors());
//        model.addAttribute("deal", dealService.listDeals());
        return "deal-create";
    }
    @GetMapping ("/deal-info/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
        return "redirect:/";
    }
    @GetMapping ("/deal-create/back")
    public String onBackPressed1() {
        return "redirect:/";
    }
}
