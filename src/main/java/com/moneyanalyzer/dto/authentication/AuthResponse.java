package com.moneyanalyzer.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    //private String token;
    private String name;
    private String email;
}
