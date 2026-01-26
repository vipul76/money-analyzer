package com.moneyanalyzer.dto;

import java.math.BigDecimal;

public record StatementSummary(BigDecimal totalDebit,
                               BigDecimal totalCredit,
                               int transactionCount)
{ }
