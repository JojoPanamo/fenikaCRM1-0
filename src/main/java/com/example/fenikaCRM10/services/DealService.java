package com.example.fenikaCRM10.services;
import com.example.fenikaCRM10.models.Deal;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class DealService {
    private List<Deal> deals = new ArrayList<>();
    private long ID = 0;
    {
        deals.add(new Deal(++ID, "Igor Petrov",
                "+79218589636", "фальц",
                "Хернекялла, Гачимучинское снт",  "Жорка суперпродавец"
                ));
    }
    public List<Deal> listDeals() {
        return deals;
    }
    public void saveDeal(Deal deal){
        deal.setId(++ID);
        deals.add(deal);
    }
    public void deleteDeal(Long id){
        deals.removeIf(deal -> deal.getId().equals(id));
    }

    public Deal getDealById(Long id) {
       for (Deal deal : deals) {
           if (deal.getId().equals(id))
               return deal;
       }
       return null;
    }

}
