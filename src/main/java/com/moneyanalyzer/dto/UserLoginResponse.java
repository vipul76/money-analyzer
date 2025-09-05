package com.moneyanalyzer.dto;

import com.moneyanalyzer.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResponse {
    private String name;
    private String email;
    private String message;

    public UserLoginResponse(User verifiedUser) {
        this.name= verifiedUser.getName();
        this.email= verifiedUser.getEmail();
        this.message="Login Successful";
    }
}
