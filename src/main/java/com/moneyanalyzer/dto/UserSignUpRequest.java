package com.moneyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignUpRequest {
    private String name;
    private String email;
    private String password;
}
