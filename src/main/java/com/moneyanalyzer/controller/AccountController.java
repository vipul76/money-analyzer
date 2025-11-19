package com.moneyanalyzer.controller;

import com.moneyanalyzer.dto.account.AccountRequest;
import com.moneyanalyzer.entity.Account;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.exception.UserNotFoundException;
import com.moneyanalyzer.service.AccountService;
import com.moneyanalyzer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

            /*GET    | `/api/accounts`      | Get all accounts of the user |
            | GET    | `/api/accounts/{id}` | Get single account by ID     |
            | POST   | `/api/accounts`      | Create a new account         |
            | PUT    | `/api/accounts/{id}` | Update account details       |
            | DELETE | `/api/accounts/{id}` | Delete an account            |*/

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> userAccounts = accountService.getAllAccount();
        return userAccounts!=null ?
                new ResponseEntity<>(userAccounts, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public List<Account> createAccountForUser(@Valid @RequestBody AccountRequest request){
        List<Account> userAccounts = accountService.findByAccountUserId(request.getUserId());
        boolean accountExists = userAccounts.stream()
                .anyMatch(acc->acc.getAccountType()==request.getAccountType());

        if(!accountExists){
            User user = userService.findById(request.getUserId())
                    .orElseThrow(()-> new UserNotFoundException("User Not Found"));
           return accountService.createAccount(user,request);
        }
        return userAccounts;
    }
}