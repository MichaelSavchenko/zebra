package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.AdminEntity;
import com.mihadev.zebra.security.JWTUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

class AdminEntityService {

    static void setup(AdminEntity adminEntity) {
        JWTUser user = getCurrentUser();

        if (Objects.isNull(adminEntity.getCreatedBy()) || adminEntity.getCreatedBy().isEmpty()) {
            adminEntity.setCreatedBy(user.fullName());
        }

        if (Objects.isNull(adminEntity.getCreatedDate())) {
            adminEntity.setCreatedDate(LocalDateTime.now().plusHours(3));
        }

        adminEntity.setUpdatedBy(user.fullName());
        adminEntity.setUpdatedDate(LocalDateTime.now().plusHours(3));
    }

    public static JWTUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JWTUser) authentication.getPrincipal();
    }
}
