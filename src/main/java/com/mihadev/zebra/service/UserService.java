package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AdminDto;
import com.mihadev.zebra.entity.Role;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.RoleRepository;
import com.mihadev.zebra.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user, String role) {
        Role roleUser = roleRepository.findByName(role);
        user.setRoles(singletonList(roleUser));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
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
