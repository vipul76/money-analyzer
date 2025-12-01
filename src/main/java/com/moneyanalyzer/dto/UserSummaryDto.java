package com.moneyanalyzer.dto;

import com.moneyanalyzer.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryDto {
    private long id;
    private String email;
    private String name;
    private String role;
}
