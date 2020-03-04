package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Clazz;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassRepository extends CrudRepository<Clazz, Integer> {

    List<Clazz> findByDateTimeBetween(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);

    List<Clazz> findTop1000ByOrderByDateTimeDesc();
}
