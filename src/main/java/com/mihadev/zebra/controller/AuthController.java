package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.AuthDto;
import com.mihadev.zebra.dto.UserDto;
import com.mihadev.zebra.security.JWTTokenProvider;
import com.mihadev.zebra.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
@CrossOrigin
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

            UserDto byUserName = userService.findByUserName(userName);

            String token = jwtTokenProvider.createToken(userName, byUserName.getRoles());

            Map<String, Object> response = new HashMap<>();
            response.put("userName", userName);
            response.put("token", token);
            response.put("role", byUserName.getRoles().get(0).getName());
            response.put("id", byUserName.getId());
            response.put("firstName", byUserName.getFirstName());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid  user name or password");
        }
    }

    @GetMapping
    public boolean validateToken(@RequestParam String token) {
        try {
            return jwtTokenProvider.validateToken(token.substring(7));
        } catch (Exception e) {
            return false;
        }

    }
}
