package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Files;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilesService {
    private List<Files> allFiles = new ArrayList<>();

    public void saveFiles(Files files, Long dealId){
        files.setDealId(dealId);
        files.setFileId(dealId);
        allFiles.add(files);
    }
    public List<Files> getFilesByDealId(Long dealId) {
        List<Files> filesByDealId = new ArrayList<>();

        for (Files files : allFiles) {
            if (files.getFileId().equals(dealId)) {
                filesByDealId.add(files);
            }
        }

        return filesByDealId;
    }
}
