package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.schedule.ScheduleDay;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;

public interface ScheduleDayRepository extends CrudRepository<ScheduleDay, DayOfWeek> {
}
