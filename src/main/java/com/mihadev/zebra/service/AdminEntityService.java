package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.AdminEntity;
import com.mihadev.zebra.security.JWTUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

class AdminEntityService {

    static void setup(AdminEntity adminEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUser user = (JWTUser) authentication.getPrincipal();

        if (Objects.isNull(adminEntity.getCreatedBy()) || adminEntity.getCreatedBy().isEmpty()) {
            adminEntity.setCreatedBy(user.fullName());
        }

        if (Objects.isNull(adminEntity.getCreatedDate())) {
            adminEntity.setCreatedDate(LocalDateTime.now());
        }

        adminEntity.setUpdatedBy(user.fullName());
        adminEntity.setUpdatedDate(LocalDateTime.now());
    }
}
