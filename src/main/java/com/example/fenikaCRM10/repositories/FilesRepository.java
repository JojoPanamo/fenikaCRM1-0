package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {
    List<Files> findAllByDealId(Long dealId);
}
