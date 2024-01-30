package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findDealByName(String name);
}
