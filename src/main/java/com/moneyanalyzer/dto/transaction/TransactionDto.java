package com.moneyanalyzer.dto.transaction;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private String type;
}
