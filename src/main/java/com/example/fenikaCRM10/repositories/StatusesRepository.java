package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusesRepository extends JpaRepository<Statuses, Long>  {
    List<Statuses> findAllByDealId(Long dealId);
}
