package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Statuses;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusesService {
    private List<Statuses> allStatuses = new ArrayList<>();

    public void saveStatus(Statuses statuses, Long dealId){
        statuses.setId(dealId);
        statuses.setStatusId(dealId);
        statuses.setCurrentDate(DateService.getCurrentDate());
        allStatuses.add(statuses);
    }
    public List<Statuses> getStatusesByDealId(Long dealId) {
        List<Statuses> statusesByDealId = new ArrayList<>();

        for (Statuses statuses : allStatuses) {
            if (statuses.getStatusId().equals(dealId)) {
                statusesByDealId.add(statuses);
            }
        }

        return statusesByDealId;
    }
}
