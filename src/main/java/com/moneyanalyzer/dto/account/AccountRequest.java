package com.moneyanalyzer.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountRequest {
    private Long userId;
    private String accountType;
    private Double initialBalance;
}
