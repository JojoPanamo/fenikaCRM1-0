package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StatusesRepository extends JpaRepository<Statuses, Long>  {
    List<Statuses> findAllByDealId(Long dealId);
    @Query(value = "SELECT * FROM statuses s WHERE s.deal_id = :dealId ORDER BY s.status_id DESC LIMIT 1", nativeQuery = true)
    Statuses findLastStatusByDealId(@Param("dealId") Long dealId);
    Statuses findTopByDealIdOrderByCurrentDateDesc(Long dealId);



//    @Query("SELECT s FROM Statuses s WHERE s.deal_id = :dealId ORDER BY s.status_id DESC")
//    Optional<Statuses> findLatestStatusByDealId(@Param("dealId") Long dealId);


}
