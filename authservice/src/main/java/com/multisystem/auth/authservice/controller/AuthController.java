package com.multisystem.auth.authservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multisystem.auth.authservice.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> request){
        return jwtService.generateToken(request.get("userName"));
    }
}
