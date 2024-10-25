package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.repositories.StatusesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {
    private final DealRepository dealRepository;
    private final StatusesRepository statusesRepository;

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
        Statuses newStatus = new Statuses();
        newStatus.setDealId(deal.getDealId());  // Присваиваем ID сделки
        newStatus.setStatusChoose("Новая заявка");  // Устанавливаем статус "Новая заявка"
        newStatus.setStatusComment("связаться в течение 30 минут!");    // Устанавливаем комментарий
        newStatus.setCurrentDate(DateService.getCurrentDate());     // Устанавливаем текущую дату

        // Сохраняем новый статус в базу данных
        statusesRepository.save(newStatus);

        log.info("Сделке с ID {} присвоен статус 'Новая заявка' с комментарием 'связаться в течение 30 минут!'", deal.getDealId());
    }

    // Метод для получения сделки по ID
    public Deal getDealById(Long dealId) {
        return dealRepository.findById(dealId).orElse(null);  // Поиск сделки по ID
    }
    public List<Deal> findByUser(User user) {
        List<Deal> userDeals = dealRepository.findByUser(user);
        for (Deal deal : userDeals) {
            // Получаем последний статус сделки
            Statuses lastStatus = statusesRepository.findLastStatusByDealId(deal.getDealId());
            if (lastStatus != null) {
                deal.setLastStatus(lastStatus.getStatusChoose());  // Устанавливаем последний статус
            }
        }
        return userDeals;
    }
    // Метод для одного статуса
    public int countDealsByLastStatusAndUser(User user, String status) {
        return countDealsByStatusesAndUser(user, Collections.singletonList(status));
    }

    // Метод для списка статусов
    public int countDealsByStatusesAndUser(User user, List<String> statuses) {
        // Логируем входные параметры
        log.info("Входные параметры: пользователь {} и статусы {}", user.getEmail(), statuses);

        // Вызов репозитория
        int count = dealRepository.countDealsByLastStatusAndUser(user, statuses);

        // Логируем результат
        log.info("Количество сделок для пользователя {} и статусов {}: {}", user.getEmail(), statuses, count);

        return count;
    }
    public List<Deal> findDealsByStatuses(User user, List<String> statuses) {
        return dealRepository.findByUserAndStatuses(user, statuses);  // Поиск сделок по статусам и пользователю
    }

}
