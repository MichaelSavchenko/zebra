package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Coach;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoachRepository extends CrudRepository<Coach, Integer> {

    Optional<Coach> findByPhone(String phone);
}
