package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AdminDto;
import com.mihadev.zebra.dto.UserDto;
import com.mihadev.zebra.entity.Role;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.RoleRepository;
import com.mihadev.zebra.repository.UserRepository;
import com.mihadev.zebra.utils.CollectionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private Map<String, UserDto> userCache = new ConcurrentHashMap<>();

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //if some ui addition of users will be provided - empty cache
    public User register(User user, String role) {
        Role roleUser = roleRepository.findByName(role);
        user.setRoles(singletonList(roleUser));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public UserDto findByUserName(String userName) {
        if (userCache.isEmpty()) {
            userCache = CollectionUtils.toList(userRepository.findAll()).stream()
                    .map(UserDto::fromUser)
                    .collect(Collectors.toConcurrentMap(UserDto::getUserName, Function.identity()));
        }

        return userCache.get(userName);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public AdminDto getAdmin(int userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    AdminDto adminDto = new AdminDto();
                    adminDto.setId(userId);
                    adminDto.setName(user.getFirstName());
                    return adminDto;
                })
                .orElseThrow(RuntimeException::new);
    }
}
