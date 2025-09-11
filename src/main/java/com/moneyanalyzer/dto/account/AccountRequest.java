package com.moneyanalyzer.dto.account;

import com.moneyanalyzer.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountRequest {

    @NotNull(message = "UserId should not be null")
    private Long userId;

    @NotNull(message = "Type of account cannot be null")
    private AccountType accountType;
    private Double initialBalance;
}