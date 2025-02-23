package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.models.WeeklyManagerProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WeeklyManagerProfitRepository extends JpaRepository<WeeklyManagerProfit, Long> {

//    List<WeeklyManagerProfit> findByUserAndYearAndMonth(User user, int year, int month);
//
//    Optional<WeeklyManagerProfit> findByUserAndYearAndMonthAndWeek(User user, int year, int month, int week);

    @Query("SELECT w FROM WeeklyManagerProfit w WHERE w.user = :user AND w.year = :year AND w.month = :month")
    List<WeeklyManagerProfit> findByUserAndMonthAndYear(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month
    );

    Optional<WeeklyManagerProfit> findByUserAndYearAndMonthAndWeek(User user, int year, int month, int week);
}

