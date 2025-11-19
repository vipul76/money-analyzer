package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.authentication.AuthResponse;
import com.moneyanalyzer.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse register(String name, String email, String password);
}
