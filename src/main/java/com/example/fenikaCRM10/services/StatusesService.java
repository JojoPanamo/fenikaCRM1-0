package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.repositories.DealRepository;
import com.example.fenikaCRM10.repositories.StatusesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusesService {
    private final StatusesRepository statusesRepository;
    private final DealRepository dealRepository;
//    private List<Statuses> allStatuses = new ArrayList<>();

    public void saveStatus(Statuses statuses, Long dealId) {
        // Сохраняем статус в базе данных
        statuses.setDealId(dealId);
        statuses.setCurrentDate(DateService.getCurrentDate());
        statusesRepository.save(statuses);

        // Загружаем сделку из базы данных
        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new RuntimeException("Сделка с ID " + dealId + " не найдена"));

        // Обновляем последний статус сделки
        Statuses lastStatus = statusesRepository.findLastStatusByDealId(deal.getDealId());
        if (lastStatus != null) {
            deal.setLastStatus(lastStatus.getStatusChoose());
        }

        // Сохраняем обновленную сделку
        dealRepository.save(deal);
    }



    public List<Statuses> getStatusesByDealId(Long dealId) {
        return statusesRepository.findAllByDealId(dealId);
    }
//    public String getLastStatusForDeal(Long dealId) {
//        // Получаем последний статус по ID сделки, сортируя по дате или ID
//        Statuses lastStatus = statusesRepository.findTopByDealIdOrderByCurrentDateDesc(dealId);
//
//        // Если статус найден, возвращаем его, иначе возвращаем сообщение о том, что статус не установлен
//        if (lastStatus != null) {
//            return lastStatus.getStatusChoose();
//        } else {
//            return "Статус не установлен";
//        }
//    }
    public String getLastStatusForDeal(Long dealId) {
        Statuses latestStatus = statusesRepository.findLastStatusByDealId(dealId);
        return latestStatus != null ? latestStatus.getStatusChoose() : null;
    }

    public String getLastStatusDateForDeal(Long dealId) {
        return statusesRepository.findTopByDealIdOrderByStatusIdDesc(dealId)
                .map(Statuses::getCurrentDate)
                .orElse(null);
    }

}
