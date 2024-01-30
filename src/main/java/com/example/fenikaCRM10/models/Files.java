package com.example.fenikaCRM10.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Files {
    private Long fileId;
    private Long dealId;
    private String fileName;
    private String filePath;
}
