package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Clients;
import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.ClientService;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final UserService userService;

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Clients>> searchClients(@RequestParam String query) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Clients> clients = clientService.searchClients(query);
        return ResponseEntity.ok(clients);
    }

    @GetMapping
    public String allClients(Model model,
                             @RequestParam(name = "userId", required = false) Long userId,
                             @RequestParam(name = "searchQuery", required = false) String searchQuery) {
        // Получение текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<Clients> allClients;

        if (isAdmin) {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                allClients = clientService.searchClients(searchQuery);
            } else {
                allClients = clientService.getAllClients();
            }
        } else {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                allClients = clientService.searchClientsForUser(currentUser, searchQuery);
            } else {
                allClients = clientService.getClientsByUser(currentUser);
            }
        }

        // Добавляем данные в модель
        model.addAttribute("users", userService.findAll()); // Для фильтрации по пользователям
        model.addAttribute("selectedUserId", userId != null ? userId : "");
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("allClients", allClients);
        model.addAttribute("searchQuery", searchQuery);

        return "clients";
    }




    @GetMapping("/validate")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> validateClient(
            @RequestParam String clientName,
            @RequestParam String phoneNumber) {
        boolean exists = clientService.existsByNameAndPhone(clientName, phoneNumber);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }
    @GetMapping("client-info/{clientId}")
    public String clientInfo(@PathVariable Long clientId, Model model) {
        // Получение текущего пользователя
        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetailsInfo.getId());

        // Проверка роли администратора
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Получение данных о клиенте
        Clients clients = clientService.getClientByIdForInfo(clientId);


        // Подсчет прибыли компании
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("clients", clients);

        return "client-info";
    }

    @GetMapping ("/client-info/{clientId}/back")
    public String onBackPressed(@PathVariable Long clientId) {
        return "redirect:/clients";
    }

//    @PostMapping("/update-client")
//    public String updateThinkSum(@RequestParam("clientId") Long clientId, Clients clients, Model model) {
//        CustomUserDetails userDetailsInfo = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User currentUser = userService.findById(userDetailsInfo.getId());
//
//        // Проверка роли администратора
//        boolean isAdmin = currentUser.getRoles().stream()
//                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
//        // Обновляем сумму через сервис
//        clientService.updateClient(clientId, clients);
//
//        model.addAttribute("isAdmin", isAdmin);
//        // Перенаправляем обратно на страницу информации о сделке
//        return "redirect:/clients/client-info/" + clientId;
//    }
    @PostMapping("/update-client")
    public String updateClient(
            @RequestParam("clientId") Long clientId,
            @ModelAttribute Clients clients) {

        // Обновляем клиента через сервис
        clientService.updateClient(clientId, clients);

        // Перенаправляем обратно на страницу информации о клиенте
        return "redirect:/clients/client-info/" + clientId;
    }

    @GetMapping("/client-create")
    public String clientCreatePage(Model model) {
        // Получаем текущего пользователя
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());

        // Создаем пустую модель клиента
        Clients client = new Clients();
        client.setUser(currentUser); // Устанавливаем текущего пользователя как ответственного

        // Передаем атрибуты в модель
        model.addAttribute("client", client);
        model.addAttribute("userName", currentUser.getName());

        return "client-create"; // HTML-шаблон для страницы создания клиента
    }

    @PostMapping("/client-create-btn")
    public String saveClient(@ModelAttribute Clients client) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());
        // Сохраняем клиента через сервис
        client.setUser(currentUser);
        clientService.saveClient(client);

        // Перенаправляем на страницу списка клиентов
        return "redirect:/clients";
    }

    @GetMapping("/filter-clients")
    @ResponseBody
    public ResponseEntity<List<Clients>> filterClients(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "userId", required = false) Long userId) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findById(userDetails.getId());// Получение текущего пользователя


        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<Clients> filteredClients;

        if (isAdmin) {
            // Если администратор, искать по всем клиентам
            filteredClients = clientService.searchClients(query);
        } else {
            // Если обычный пользователь, искать только своих клиентов
            filteredClients = clientService.searchClientsForUser(currentUser, query);
        }

        return ResponseEntity.ok(filteredClients);
    }





}
