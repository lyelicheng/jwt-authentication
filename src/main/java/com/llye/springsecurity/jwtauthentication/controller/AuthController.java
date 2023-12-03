package com.llye.springsecurity.jwtauthentication.controller;

import com.llye.springsecurity.jwtauthentication.dto.UserRequestDto;
import com.llye.springsecurity.jwtauthentication.entity.User;
import com.llye.springsecurity.jwtauthentication.exception.BusinessException;
import com.llye.springsecurity.jwtauthentication.model.LoginCredentialDto;
import com.llye.springsecurity.jwtauthentication.security.JWTUtil;
import com.llye.springsecurity.jwtauthentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public AuthController(JWTUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody UserRequestDto userRequestDto) throws BusinessException {
        User user = userService.save(userRequestDto);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentialDto body) throws BusinessException {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException ex) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }
}
