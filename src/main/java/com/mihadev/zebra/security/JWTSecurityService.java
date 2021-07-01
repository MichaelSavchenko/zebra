package com.mihadev.zebra.security;

import com.mihadev.zebra.dto.UserDto;
import com.mihadev.zebra.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTSecurityService implements UserDetailsService {
    private final UserService userService;

    public JWTSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }


        return JWTUser.fromUser(user);
    }
}
