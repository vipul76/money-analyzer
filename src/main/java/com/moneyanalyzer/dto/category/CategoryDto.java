package com.moneyanalyzer.dto.category;

import com.moneyanalyzer.dto.UserSummaryDto;
import com.moneyanalyzer.entity.TransactionEntity;
import com.moneyanalyzer.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private TransactionType type; // INCOME or EXPENSE
    private String icon;
    private UserSummaryDto userSummaryDto;
    private List<TransactionEntity> transactions;
}
