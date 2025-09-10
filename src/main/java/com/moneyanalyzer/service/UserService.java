package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.UserResponse;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User validateUser(String email,String password);

    UserResponse registerUser(UserSignUpRequest signUpReq);

   /* User findByUserId(User userId);*/

    Optional<User> findById(Long userId);
}
