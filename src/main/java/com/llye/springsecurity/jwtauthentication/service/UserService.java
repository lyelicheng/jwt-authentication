package com.llye.springsecurity.jwtauthentication.service;

import com.llye.springsecurity.jwtauthentication.dto.UserRequestDto;
import com.llye.springsecurity.jwtauthentication.entity.User;
import com.llye.springsecurity.jwtauthentication.exception.BusinessException;
import com.llye.springsecurity.jwtauthentication.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(UserRequestDto userRequestDto) throws BusinessException {
        String email = userRequestDto.getEmail();
        boolean userExists = isUserExists(email);
        if (userExists) {
            throw new BusinessException(HttpStatus.CONFLICT, "User already exists");
        }

        User user = new User();
        user.setEmail(email);

        // encrypt password
        String encodedPass = passwordEncoder.encode(userRequestDto.getPassword());
        user.setPassword(encodedPass);

        return userRepo.save(user);
    }

    private boolean isUserExists(String email) throws BusinessException {
        User user;
        try {
            user = findByEmail(email);
            if (Objects.nonNull(user)) {
                return true;
            }
        } catch (BusinessException ex) {
            if ("User not found".equals(ex.getErrorMessage())) {
                return false;
            }
            throw ex;
        }
        throw new IllegalArgumentException("isUserExists: internal server error");
    }

    public User findByEmail(String email) throws BusinessException {
        Optional<User> maybeUser = userRepo.findByEmail(email);
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
