package com.moneyanalyzer.repository;

import com.moneyanalyzer.entity.Account;
import com.moneyanalyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    List<Account> findByUser(User user);
}
