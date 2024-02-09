package com.example.fenikaCRM10.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files {
    @Id
    @Column(name = "fileId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;
    @Column(name = "dealId")
    private Long dealId;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "originalFileName")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "fileType")
    private String fileType;
    @Lob
    private byte[] bytes;
//    @Column(name = "fileId")
//    private String filePath;

}
