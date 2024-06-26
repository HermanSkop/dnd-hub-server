package com.example.dndhub.controllers;

import com.example.dndhub.configuration.AppConfig;
import com.example.dndhub.dtos.CurrentUserDto;
import com.example.dndhub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/current")
    public CurrentUserDto getCurrentUser() {
        return userService.getUserById(AppConfig.testPlayerId);
    }
}
