package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.ScheduleDayRepository;
import com.mihadev.zebra.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class ScheduleDayService {

    private final static int MYTNYTSYA_SCHEDULE = 740781;

    private final ScheduleDayRepository scheduleDayRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleDayService(ScheduleDayRepository scheduleDayRepository, ScheduleRepository scheduleRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void saveSunday() {
       scheduleDayRepository.delete(scheduleDayRepository.findById(740789).orElseThrow(() -> new RuntimeException("not deleted")));
    }
}
