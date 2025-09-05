package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.UserResponse;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.entity.User;

public interface UserService {
    User validateUser(String email,String password);

    UserResponse registerUser(UserSignUpRequest signUpReq);
}
