package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Deal;
import com.example.fenikaCRM10.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    // Поиск сделок по имени
    List<Deal> findDealByName(String name);

    // Поиск сделок по userId
    List<Deal> findByUser_UserId(Long userId);

    // Поиск сделок по user
    List<Deal> findByUser(User user);

    @Query("SELECT COUNT(d) FROM Deal d WHERE d.user = :user AND " +
            "(SELECT s.statusChoose FROM Statuses s WHERE s.dealId = d.dealId " +
            "ORDER BY s.statusId DESC LIMIT 1) IN :statuses")
    int countDealsByLastStatusAndUser(@Param("user") User user, @Param("statuses") List<String> statuses);



    @Query("SELECT d FROM Deal d WHERE d.user = :user AND (SELECT s.statusChoose FROM Statuses s WHERE s.dealId = d.dealId ORDER BY s.statusId DESC LIMIT 1) IN :statuses")
    List<Deal> findByUserAndStatuses(@Param("user") User user, @Param("statuses") List<String> statuses);

}