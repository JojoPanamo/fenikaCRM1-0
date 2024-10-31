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

    @Query("SELECT d FROM Deal d WHERE MONTH(d.creationDate) = :month AND YEAR(d.creationDate) = :year")
    List<Deal> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM Deal d WHERE (SELECT s.statusChoose FROM Statuses s WHERE s.dealId = d.dealId ORDER BY s.statusId DESC LIMIT 1) IN :statuses")
    List<Deal> findAllDealsByStatuses(@Param("statuses") List<String> statuses);

    @Query("SELECT COUNT(d) FROM Deal d WHERE d.status = :status AND d.dealId IN " +
            "(SELECT s.dealId FROM Statuses s WHERE s.statusChoose = :status " +
            "AND s.statusId = (SELECT MAX(sInner.statusId) FROM Statuses sInner WHERE sInner.dealId = s.dealId))")
    int countDealsByLastStatus(@Param("status") String status);


    @Query("SELECT COUNT(d) FROM Deal d WHERE d.dealId IN " +
            "(SELECT s.dealId FROM Statuses s WHERE s.statusChoose IN :statuses " +
            "AND s.statusId = (SELECT MAX(sInner.statusId) FROM Statuses sInner WHERE sInner.dealId = s.dealId))")
    int countDealsByLastStatuses(@Param("statuses") List<String> statuses);



//    int countByStatus(String status);
//
//    int countByStatusIn(List<String> statuses);



}