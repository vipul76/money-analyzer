package com.moneyanalyzer.dto.category;

import com.moneyanalyzer.entity.TransactionType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequestDto {
    private String name;
    private TransactionType type; // INCOME or EXPENSE
    private String icon;
    private Long userId;
}
