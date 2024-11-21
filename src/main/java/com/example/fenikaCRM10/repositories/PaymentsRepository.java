package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Payments;
import com.example.fenikaCRM10.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    List<Payments> findAllByDealId(Long dealId);
    List<Payments> findByUser(User user);
    List<Payments> findByDealId(Long dealId);
    @Query("SELECT SUM(p.sum) FROM Payments p WHERE p.dealId = :dealId")
    Double getTotalPaymentsByDealId(@Param("dealId") Long dealId);

}
