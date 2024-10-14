package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.repositories.DealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {
    private final DealRepository dealRepository;

    // Метод для поиска сделок по userId
    public List<Deal> findDealsByUserId(Long userId) {
        return dealRepository.findByUser_UserId(userId);  // Поиск сделок по ID пользователя
    }

    // Метод для поиска сделок по имени
    public List<Deal> listDeals(String name) {
        if (name != null) {
            return dealRepository.findDealByName(name);  // Поиск сделок по имени
        } else {
            return dealRepository.findAll();  // Возвращаем все сделки
        }
    }

    // Метод для сохранения сделки
    public void saveDeal(Deal deal) {
        dealRepository.save(deal);  // Сохранение сделки в базу данных
    }

    // Метод для получения сделки по ID
    public Deal getDealById(Long dealId) {
        return dealRepository.findById(dealId).orElse(null);  // Поиск сделки по ID
    }
}
