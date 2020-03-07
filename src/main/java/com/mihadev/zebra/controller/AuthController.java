package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.AuthDto;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.security.JWTTokenProvider;
import com.mihadev.zebra.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity login(@RequestBody AuthDto dto) {
        try {

            String userName = dto.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, dto.getPassword()));

            User byUserName = userService.findByUserName(userName);

            String token = jwtTokenProvider.createToken(userName, byUserName.getRoles());

            Map<String, Object> response = new HashMap<>();
            response.put("userName", userName);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid  user name or password");
        }
    }
}
