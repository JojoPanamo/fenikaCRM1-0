package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Statuses;
import com.example.fenikaCRM10.repositories.StatusesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusesService {
    private final StatusesRepository statusesRepository;
//    private List<Statuses> allStatuses = new ArrayList<>();

    public void saveStatus(Statuses statuses, Long dealId){
        statuses.setDealId(dealId);
        statuses.setCurrentDate(DateService.getCurrentDate());
        statusesRepository.save(statuses);
    }

    public List<Statuses> getStatusesByDealId(Long dealId) {
        return statusesRepository.findAllByDealId(dealId);
    }
}
