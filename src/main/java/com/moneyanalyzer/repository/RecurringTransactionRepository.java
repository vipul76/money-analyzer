package com.moneyanalyzer.repository;

import com.moneyanalyzer.entity.RecurringTransaction;
import com.moneyanalyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByUser(User user);
    List<RecurringTransaction> findByNextDateBefore(LocalDateTime date);
}
