package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.Role;
import com.mihadev.zebra.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RolesScript {

    private final RoleRepository roleRepository;

    public RolesScript(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void setup() {
        roleRepository.deleteAll();

        Role admin = new Role();
        admin.setName("ROLE_ADMIN");

        Role user = new Role();
        user.setName("ROLE_USER");

        roleRepository.saveAll(Arrays.asList(admin, user));
    }
}
