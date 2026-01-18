package com.moneyanalyzer.serviceImpl;

import com.moneyanalyzer.dto.UserResponse;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.entity.Account;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.exception.InvalidCredentialsException;
import com.moneyanalyzer.repository.UserRepository;
import com.moneyanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    @Lazy
    AccountServiceImpl accountServiceImpl;

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

        Account acc = new Account();
        acc.setUser(savedUser);
        acc.setName(savedUser.getName());
        acc.setBalance(BigDecimal.ZERO);
        accountServiceImpl.saveAndFlush(acc);
        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),savedUser.getCreatedAt());
    }


    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("user not found with email : "+email));
    }
}
/*
 Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent() && userOptional.get().getPassword().equals(password)){
            return userOptional.get();
        }
 */