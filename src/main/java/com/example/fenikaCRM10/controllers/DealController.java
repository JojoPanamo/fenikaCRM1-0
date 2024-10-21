package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.DealService;
import com.example.fenikaCRM10.services.UserService;
import com.example.fenikaCRM10.services.UserServiceList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;
    private final UserService userService;
    private final DealRepository dealRepository;

    //    @GetMapping("/deals/")
//    public String deals(@RequestParam(name = "name", required = false) String name, Model model) {
//        model.addAttribute("deals", dealService.listDeals(name));
//        return "deals";
//    }
    @GetMapping("/deals")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUserDeals(Model model) {
        // Получаем текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Находим сделки этого пользователя
        List<Deal> userDeals = dealService.findByUser(currentUser);

        // Добавляем сделки в модель для отображения на странице
        model.addAttribute("deals", userDeals);

        return "deals"; // Отображаем страницу со списком сделок
    }

    @PostMapping("/deal-create-save")
    public String createDealSave(@ModelAttribute Deal deal, Principal principal) {
        // Получаем текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Устанавливаем пользователя сделки
        deal.setUser(currentUser);

        // Устанавливаем автора сделки
        deal.setAuthor(currentUser.getName());

        // Сохраняем сделку
        dealService.saveDeal(deal);

        return "redirect:/deals";
    }

//    @PostMapping("/deal-create")
//    public String createDealSave(@ModelAttribute Deal deal, Principal principal) {
//        // Получаем текущего пользователя из контекста безопасности
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // Получаем ID пользователя
//        Long userId = userDetails.getId(); // Предположим, что в вашем CustomUserDetails есть метод getId()
//
//        // Ищем пользователя по ID и устанавливаем его для сделки
//        User currentUser = userService.findById(userId);
//
//        // Устанавливаем пользователя для сделки
//        deal.setUser(currentUser);
//
//        // Сохраняем сделку
//        dealService.saveDeal(deal);
//
//        return "redirect:/deals/";
//    }
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
    @GetMapping("/deal-create")
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
