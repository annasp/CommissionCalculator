package com.example.commissioncalculator.repository;

import com.example.commissioncalculator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE EXTRACT (month FROM t.date) = :month AND t.clientId = :clientId")
    List<Transaction> findByMonthAndClientId(@Param("month") Integer month, @Param("clientId") Long clientId);

}
