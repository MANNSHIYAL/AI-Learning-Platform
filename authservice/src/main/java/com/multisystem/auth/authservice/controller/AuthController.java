package com.multisystem.auth.authservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multisystem.auth.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody Map<String,String> request){
        return authService.register(request.get("email"), request.get("password"),request.get("userName"));
    }
    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> request){
        return authService.login(request.get("email"), request.get("password"));
    }
    @GetMapping("/test")
    public String test() {
        return "SECURED SUCCESS";
    }
}
