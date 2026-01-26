package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.UserResponse;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.entity.User;

import java.util.Optional;

public interface UserService {

    User validateUser(String email,String password);
    UserResponse registerUser(UserSignUpRequest signUpReq);

    //User findByUserId(User userId);
    Optional<User> findById(Long userId);

    User findByUsername(String username);

    Optional<User> getCurrentUser();
}
