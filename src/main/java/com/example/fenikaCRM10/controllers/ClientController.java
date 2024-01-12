package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @GetMapping("/")
    public String clients(Model model){
        model.addAttribute("clients", clientService.listClients());
        return "clients";
    }
}
