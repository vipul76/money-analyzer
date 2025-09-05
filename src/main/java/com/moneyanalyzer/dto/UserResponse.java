package com.moneyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
