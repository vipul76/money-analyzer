package com.moneyanalyzer.repository;

import com.moneyanalyzer.entity.Budget;
import com.moneyanalyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    List<Budget> findByUserAndMonth(User user, String month);
}
