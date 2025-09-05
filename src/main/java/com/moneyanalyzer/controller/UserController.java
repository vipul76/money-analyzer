package com.moneyanalyzer.controller;

import com.moneyanalyzer.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/")
    public String hi(){
        return "Hi";
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(User user){

    }

    public void logout(){

    }

    public void getAuth(){

    }


}
