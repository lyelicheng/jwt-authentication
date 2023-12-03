package com.llye.springsecurity.jwtauthentication.repository;

import com.llye.springsecurity.jwtauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
