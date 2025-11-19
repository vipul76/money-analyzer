package com.moneyanalyzer.serviceImpl;

import com.moneyanalyzer.dto.authentication.AuthResponse;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.repository.UserRepository;
import com.moneyanalyzer.securities.JwtUtils;
import com.moneyanalyzer.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtil) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(String name, String email, String rawPassword) {
        if(userRepo.existsByEmail(email)){
            throw new IllegalArgumentException("Username Already Exist");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Collections.singleton("ROLE_USER"));
        user.setEnabled(true);
        User savedUser = userRepo.save(user);

        //String accessToken = jwtUtil.generateToken(savedUser.getEmail());
        //String refreshToken = jwtUtil.generateRefreshToken(savedUser);

        //return new AuthResponse(accessToken,savedUser.getName(), savedUser.getEmail());
        return new AuthResponse(savedUser.getName(), savedUser.getEmail());

    }
}
