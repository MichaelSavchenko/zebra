package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.AdminDto;
import com.mihadev.zebra.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public AdminDto get(@PathVariable int userId) {
        return userService.getAdmin(userId);
    }
}
