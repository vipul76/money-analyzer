package com.moneyanalyzer.serviceImpl;

import com.moneyanalyzer.dto.UserResponse;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.exception.InvalidCredentialsException;
import com.moneyanalyzer.repository.UserRepository;
import com.moneyanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) {
        return userRepository
                .findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(()-> new InvalidCredentialsException("Invalid email or password"));
    }

    @Override
    public UserResponse registerUser(UserSignUpRequest signUpReq) {
        userRepository.findByEmail(signUpReq.getEmail())
                .ifPresent(u -> {
                   throw new InvalidCredentialsException("Email already Exist");
                });

        User user = new User();
        user.setName(signUpReq.getName());
        user.setEmail(signUpReq.getEmail());
        user.setPassword(signUpReq.getPassword());

        User savedUser = userRepository.saveAndFlush(user);
        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),savedUser.getCreatedAt());
    }
}

/*
 Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent() && userOptional.get().getPassword().equals(password)){
            return userOptional.get();
        }
 */