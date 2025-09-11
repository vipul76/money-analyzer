package com.moneyanalyzer.serviceImpl;

import com.moneyanalyzer.dto.account.AccountRequest;
import com.moneyanalyzer.entity.Account;
import com.moneyanalyzer.entity.AccountType;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.exception.UserNotFoundException;
import com.moneyanalyzer.repository.AccountRepository;
import com.moneyanalyzer.repository.UserRepository;
import com.moneyanalyzer.service.AccountService;
import com.moneyanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserService userService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }


    @Override
    public List<Account> findByAccountUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public List<Account> createAccount(User userId, AccountRequest request) {
        User savedUser = userRepository.findById(userId.getId())
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
        Account acc = new Account();
        acc.setUser(savedUser);
        acc.setName(savedUser.getName());
        acc.setAccountType(request.getAccountType());
        acc.setBalance(BigDecimal.valueOf(request.getInitialBalance()));
        Account savedAccount = accountRepository.save(acc);
        return List.of(savedAccount);
    }

    @Override
    public Optional<Object> findByUserId(Long userId) {
        return Optional.ofNullable(accountRepository.findByUserId(userId));
    }

    public Account saveAndFlush(Account acc) {
        return accountRepository.saveAndFlush(acc);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}