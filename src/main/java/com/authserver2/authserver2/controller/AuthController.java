package com.authserver2.authserver2.controller;

import com.authserver2.authserver2.model.User;
import com.authserver2.authserver2.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @PostMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/register")
    public User register() {
        return userDetailServiceImp.createUser();
    }

    @GetMapping("/hello")
    public String hello(Principal userPrincipal){
        return userPrincipal.getName();
    }
}
