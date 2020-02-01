package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Clazz;
import org.springframework.data.repository.CrudRepository;

public interface ClassRepository extends CrudRepository<Clazz, Long> {
}
