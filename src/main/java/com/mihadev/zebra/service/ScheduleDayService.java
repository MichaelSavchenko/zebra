package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.ScheduleDayRepository;

import java.time.DayOfWeek;

public class ScheduleDayService {

    private final static int MYTNYTSYA_SCHEDULE = 740782;

    private final ScheduleDayRepository scheduleDayRepository;

    public ScheduleDayService(ScheduleDayRepository scheduleDayRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
    }

    public void saveSunday() {
        Schedule schedule = new Schedule();
        schedule.setId(MYTNYTSYA_SCHEDULE);
        ScheduleDay sunday = new ScheduleDay(DayOfWeek.SUNDAY, schedule);
        scheduleDayRepository.save(sunday);
    }
}
