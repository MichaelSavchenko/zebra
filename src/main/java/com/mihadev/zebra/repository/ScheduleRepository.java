package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {
}
