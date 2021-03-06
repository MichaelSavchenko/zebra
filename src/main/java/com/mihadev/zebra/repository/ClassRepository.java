package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassRepository extends CrudRepository<Clazz, Integer> {

    List<Clazz> findByDateTimeBetween(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);

    @Modifying
    @Transactional
    @Query("DELETE FROM Clazz c WHERE c.dateTime < :dateTime")
    void removeOlderThan(@Param("dateTime") LocalDateTime dateTime);

    List<Clazz> findTop1000ByOrderByDateTimeDesc();

    List<Clazz> findByDateTimeIsAfterOrderByDateTimeDesc(LocalDateTime dateTime);

    List<Clazz> findByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime startTime, LocalDateTime finishTime);

    List<Clazz> findByClassTypeAndDateTimeAndCoach(ClassType classType, LocalDateTime dateTimeStart, Coach coach);


    List<Clazz> findByCoachAndDateTimeAfter(Coach coach, LocalDateTime dateTime);

    List<Clazz> findByCoachAndDateTimeBetween(Coach coach, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);

    List<Clazz> findByCoachAndClassTypeAndDateTimeBetween(Coach coach, ClassType classType, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);
}
