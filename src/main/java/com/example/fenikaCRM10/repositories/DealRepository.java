package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    // Поиск сделок по имени
    List<Deal> findDealByName(String name);

    // Поиск сделок по userId
    List<Deal> findByUser_UserId(Long userId);
}