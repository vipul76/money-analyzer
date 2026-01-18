package com.moneyanalyzer.dto.category;

import com.moneyanalyzer.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private TransactionType type; // INCOME or EXPENSE
    private String icon;
}
