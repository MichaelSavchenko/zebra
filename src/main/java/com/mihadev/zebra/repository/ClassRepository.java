package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassRepository extends CrudRepository<Clazz, Integer> {

    List<Clazz> findByDateTimeBetween(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);

    List<Clazz> findTop1000ByOrderByDateTimeDesc();

    List<Clazz> findByClassTypeAndDateTimeAndCoach(ClassType classType, LocalDateTime dateTimeStart, Coach coach);
}
