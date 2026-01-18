package com.moneyanalyzer.repository;

import com.moneyanalyzer.entity.Category;
import com.moneyanalyzer.entity.TransactionType;
import com.moneyanalyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserOrUserIsNull(User user); // supports global + user-defined categories
    List<Category> findByTypeAndUserOrUserIsNull(TransactionType type, User user);

    //Category findByName(String name);

    Category findByNameAndUserId(String name, Long id);
}
