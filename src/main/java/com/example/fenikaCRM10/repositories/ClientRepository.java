package com.example.fenikaCRM10.repositories;

import com.example.fenikaCRM10.models.Clients;
import com.example.fenikaCRM10.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Clients, Long> {

    Optional<Clients> findByClientNameAndPhoneNumber(String clientName, String phoneNumber);

    @Query("SELECT c FROM Clients c WHERE LOWER(c.clientName) LIKE LOWER(CONCAT('%', :query, '%')) OR c.phoneNumber LIKE CONCAT('%', :query, '%')")
    List<Clients> searchClients1(@Param("query") String query);

    List<Clients> findAll();

    boolean existsByClientNameAndPhoneNumber(String clientName, String phoneNumber);

    @Query("SELECT c FROM Clients c WHERE c.user.userId = :userId")
    List<Clients> findAllByUser_UserId(@Param("userId") Long userId);

    List<Clients> findByUser(User user);

    @Query("SELECT c FROM Clients c WHERE LOWER(c.clientName) LIKE LOWER(CONCAT('%', :query, '%')) OR c.phoneNumber LIKE CONCAT('%', :query, '%')")
    List<Clients> searchByNameOrPhone(@Param("query") String query);

    @Query("SELECT c FROM Clients c WHERE c.user = :user AND (LOWER(c.clientName) LIKE LOWER(CONCAT('%', :query, '%')) OR c.phoneNumber LIKE CONCAT('%', :query, '%'))")
    List<Clients> searchByNameOrPhoneForUser(@Param("user") User user, @Param("query") String query);

    @Query("SELECT c FROM Clients c WHERE c.user.id = :userId AND " +
            "(LOWER(c.clientName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Clients> searchClientsForUser(@Param("userId") Long userId, @Param("query") String query);


}
