package com.moneyanalyzer.repository;

import com.moneyanalyzer.entity.Transaction;
import com.moneyanalyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndTransactionAtBetween(User user, LocalDateTime from, LocalDateTime to);
}
