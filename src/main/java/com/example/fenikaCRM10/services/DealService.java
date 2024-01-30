package com.example.fenikaCRM10.services;
import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.repositories.DealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {
    private final DealRepository dealRepository;
    private List<Deal> deals = new ArrayList<>();
    private long ID = 0;
    //Test deal
//    {
//        deals.add(new Deal(++ID, "Igor Petrov",
//                "+79218589636", "фальц",
//                "Хернекялла, Гачимучинское снт",  "Жорка суперпродавец"
//                ));
//    }
//    public List<Deal> dealByName(String name){
//
//    }
    public List<Deal> listDeals(String name) {
        if (name != null) {
            return dealRepository.findDealByName(name);
        }else {
            return dealRepository.findAll();
        }
    }
    public void saveDeal(Deal deal){
        dealRepository.save(deal);
    }
//    public void deleteDeal(Long id){
//        deals.removeIf(deal -> deal.getId().equals(id));
//    }

    public Deal getDealById(Long id) {
       return dealRepository.findById(id).orElse(null);
    }

}
