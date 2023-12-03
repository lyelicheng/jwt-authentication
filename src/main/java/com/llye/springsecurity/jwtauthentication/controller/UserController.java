package com.llye.springsecurity.jwtauthentication.controller;

import com.llye.springsecurity.jwtauthentication.entity.User;
import com.llye.springsecurity.jwtauthentication.exception.BusinessException;
import com.llye.springsecurity.jwtauthentication.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public User getUserDetails() throws BusinessException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(email);
    }
}
