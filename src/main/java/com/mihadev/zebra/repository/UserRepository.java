package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUserName(String userName);
}
