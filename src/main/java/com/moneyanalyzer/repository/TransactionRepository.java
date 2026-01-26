package com.moneyanalyzer.repository;

import com.moneyanalyzer.entity.TransactionEntity;
import com.moneyanalyzer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    //List<TransactionEntity> findByUser(User user);
    List<TransactionEntity> findByUserAndTransactionAtBetween(User user, LocalDateTime from, LocalDateTime to);

    Page<TransactionEntity> findByUser(User user, Pageable page);
}
