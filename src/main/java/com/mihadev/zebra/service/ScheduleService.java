package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.entity.schedule.ScheduleClassComparator;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.GymRepository;
import com.mihadev.zebra.repository.ScheduleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.mihadev.zebra.utils.CollectionUtils.toList;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GymRepository gymRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, GymRepository gymRepository) {
        this.scheduleRepository = scheduleRepository;
        this.gymRepository = gymRepository;
    }

    @Cacheable("schedule")
    public List<Schedule> getAll() {
        List<Schedule> schedules = toList(scheduleRepository.findAll());
        for (Schedule sc : schedules) {
            Set<ScheduleDay> scheduleDays = sc.getScheduleDays();
            for (ScheduleDay day : scheduleDays) {
                TreeSet<ScheduleClass> sorted = new TreeSet<>(new ScheduleClassComparator());
                sorted.addAll(day.getScheduleClasses());
                day.setScheduleClasses(sorted);
            }
        }

        return schedules;
    }

    public Schedule get(int id) {
        return scheduleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
