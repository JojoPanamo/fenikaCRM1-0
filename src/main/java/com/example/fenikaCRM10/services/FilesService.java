package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Files;
import com.example.fenikaCRM10.repositories.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilesService {
    private final FilesRepository filesRepository;

    public void saveFile(MultipartFile file, Long dealId) throws IOException {
        Files newFile = new Files();
        newFile.setDealId(dealId);
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        newFile.setFileName(file.getOriginalFilename());
        newFile.setSize(file.getSize());
        newFile.setFileType(file.getContentType());
        newFile.setBytes(file.getBytes());
        filesRepository.save(newFile);
    }

    public List<Files> getFilesByDealId(Long dealId) {
        return filesRepository.findAllByDealId(dealId);
    }

    public void deleteFileById(Long fileId) {
        filesRepository.deleteById(fileId);
    }

    public Files getFileById(Long fileId) {
        return filesRepository.findById(fileId).orElse(null);
    }

    public Long getDealByPaymentId(Long fileId) {
        Files files = filesRepository.findById(fileId).orElse(null);
        if (files != null) {
            return files.getDealId();
        } else {
            return null;
        }
    }
}
