package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.account.AccountRequest;
import com.moneyanalyzer.entity.Account;
import com.moneyanalyzer.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    List<Account> getAllAccount();
    List<Account> findByAccountUserId(Long userId);
    Optional<Account> getAccountById(Long id);
    List<Account> createAccount(User userId, AccountRequest request);
    Optional<Object> findByUserId(Long userId);
}
