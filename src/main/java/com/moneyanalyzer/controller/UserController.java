package com.moneyanalyzer.controller;

import com.moneyanalyzer.dto.UserLoginRequest;
import com.moneyanalyzer.dto.UserLoginResponse;
import com.moneyanalyzer.dto.UserResponse;
import com.moneyanalyzer.dto.UserSignUpRequest;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody(required = false) UserSignUpRequest signUpReq){
        if(signUpReq ==null ||
                signUpReq.getName() == null || signUpReq.getName().trim().isEmpty() ||
                signUpReq.getEmail()==null || signUpReq.getEmail().trim().isEmpty() ||
                signUpReq.getPassword() ==null || signUpReq.getPassword().trim().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Enter user details");
        }

        UserResponse userResponse = userService.registerUser(signUpReq);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required=false) UserLoginRequest userRequest){
        if(userRequest==null ||
                userRequest.getEmail()==null || userRequest.getEmail().trim().isEmpty() ||
                userRequest.getPassword()==null || userRequest.getPassword().trim().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email and Password must be provided");
        }

        User verifiedUser = userService.validateUser(userRequest.getEmail(), userRequest.getPassword());
        return verifiedUser!=null? new ResponseEntity<>(new UserLoginResponse(verifiedUser),HttpStatus.OK):
                new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/hello")
    public String logout(){
        return "Hello I am here active";
    }

    public void getAuth(){

    }
}
