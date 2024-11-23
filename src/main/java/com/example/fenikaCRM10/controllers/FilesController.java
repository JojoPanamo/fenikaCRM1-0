package com.example.fenikaCRM10.controllers;

import com.example.fenikaCRM10.models.Files;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.services.CustomUserDetails;
import com.example.fenikaCRM10.services.FilesService;
import com.example.fenikaCRM10.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FilesController {
    private final FilesService filesService;
    private final UserService userService;

    @GetMapping("/files/{dealId}")
    public String getFilesByDealId(@PathVariable Long dealId, Model model) {
        List<Files> files = filesService.getFilesByDealId(dealId);
        User currentUser = userService.findByPrincipal(
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("dealId", dealId);
        model.addAttribute("allFiles", files);
        return "files";
    }

    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
        Files file = filesService.getFileById(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(file.getBytes());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

    @PostMapping("/files/upload/{dealId}")
    public String uploadFile(@PathVariable Long dealId,
                             @RequestParam("file") MultipartFile multipartFile,
                             @RequestParam("fileComment") String fileComment) throws IOException {
        if (!multipartFile.isEmpty()) {
            filesService.saveFile(multipartFile, dealId, fileComment);
        }
        return "redirect:/files/" + dealId;
    }


    @PostMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId) {
        Long dealId = filesService.getDealByPaymentId(fileId);
        filesService.deleteFileById(fileId);
        return "redirect:/files/" + dealId;
    }
    @GetMapping ("/files/{dealId}/back")
    public String onBackPressed(@PathVariable Long dealId) {
        return "redirect:/deal-info/" + dealId;
    }
    @GetMapping ("/files/{dealId}/todeals")
    public String onBackToDealsPressed(@PathVariable Long dealId) {
        return "redirect:/";
    }
}