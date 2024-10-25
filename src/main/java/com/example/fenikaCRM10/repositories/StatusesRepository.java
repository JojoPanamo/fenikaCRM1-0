package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusesRepository extends JpaRepository<Statuses, Long>  {
    List<Statuses> findAllByDealId(Long dealId);
    @Query(value = "SELECT * FROM statuses s WHERE s.deal_id = :dealId ORDER BY s.status_id DESC LIMIT 1", nativeQuery = true)
    Statuses findLastStatusByDealId(@Param("dealId") Long dealId);
    Statuses findTopByDealIdOrderByCurrentDateDesc(Long dealId);

}
