package com.multisystem.auth.authservice.reopsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multisystem.auth.authservice.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
}
