package com.multisystem.auth.authservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multisystem.auth.authservice.entity.User;
import com.multisystem.auth.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        return authService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> request){
        return authService.login(request.get("email"), request.get("password"));
    }
}
