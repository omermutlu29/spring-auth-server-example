package com.authserver2.authserver2.controller;

import com.authserver2.authserver2.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public String login(){
        return "Login";
    }

    @PostMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/logout")
    public String logout(){
        return "logout";
    }
}
