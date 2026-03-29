package com.multisystem.auth.authservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multisystem.auth.authservice.entity.User;
import com.multisystem.auth.authservice.reopsitory.UserRepository;
import com.multisystem.auth.authservice.staticvalues.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User already exist!");
        }

        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.USER);

        userRepository.save(newUser);

        return "User Registered";
    }
    public String login(String email,String password){
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found!"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Invalid Password!");
        }
        return jwtService.generateToken(email);
    }
}
