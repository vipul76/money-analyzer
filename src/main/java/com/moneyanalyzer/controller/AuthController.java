package com.moneyanalyzer.controller;

import com.moneyanalyzer.dto.UserLoginRequest;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.dto.authentication.AuthResponse;
import com.moneyanalyzer.securities.JwtUtils;
import com.moneyanalyzer.service.AuthService;
import com.moneyanalyzer.serviceImpl.CustomUserDetailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    /*POST   | `/api/auth/register` | Register new user                         |
            | POST   | `/api/auth/login`    | Authenticate user & return JWT token      |
            | GET    | `/api/auth/me`       | Get current logged-in user info           |
            | POST   | `/api/auth/logout`   | Logout (optional, for token i*/

    private final AuthService authService;
    private final AuthenticationManager authManger;
    private final JwtUtils jwtUtil;
    private final CustomUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService,
                          AuthenticationManager authManager,
                          JwtUtils jwtUtil,
                          CustomUserDetailService userDetailService,
                          PasswordEncoder passwordEncoder){
        this.authService = authService;
        this.authManger = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    //public record RegisterRequest(String userName, String password ){}
    //public record LoginRequest(String userName, String password){}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserSignUpRequest req){
        AuthResponse user = authService.register(req.getName(), req.getEmail(), req.getPassword());
        return ResponseEntity.ok(Map.of("name", user.getName(),"email",user.getEmail()));

        //return ResponseEntity.ok(Map.of("token",user.getToken(), "name", user.getName(),"email",user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest req){
        try{
            authManger.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        }
        catch (BadCredentialsException ex){
            return ResponseEntity.status(401).body(Map.of("error", "InvalidCredentials"));
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(req.getEmail());

        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(Map.of("accessToken", token, "tokenType", "Bearer", "expiresInMS", 90000));
    }
}
