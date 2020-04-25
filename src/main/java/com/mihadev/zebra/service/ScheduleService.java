package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.entity.schedule.ScheduleClassComparator;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;

@Service
public class ScheduleService {

    private List<Schedule> cache = new ArrayList<>();
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAll() {
        if (cache.isEmpty()) {
            List<Schedule> schedules = toList(scheduleRepository.findAll());
            for (Schedule sc : schedules) {
                Set<ScheduleDay> scheduleDays = sc.getScheduleDays();
                for (ScheduleDay day : scheduleDays) {
                    TreeSet<ScheduleClass> sorted = new TreeSet<>(new ScheduleClassComparator());
                    sorted.addAll(day.getScheduleClasses());
                    day.setScheduleClasses(sorted);
                }
            }

            cache = schedules;

            return schedules;
        }

        return cache;
    }

    public List<Schedule> getScheduleForDay(String coachLogin) {
        List<Schedule> all = getAll();
        List<Schedule> result = new ArrayList<>();

        for (Schedule sc : all) {

            ScheduleDay day = sc.getScheduleDays().stream()
                    .filter(scheduleDay -> scheduleDay.getDayOfWeek() == DayOfWeek.FRIDAY)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Schedule for " + LocalDate.now().getDayOfWeek() + "is not found."));

            Set<ScheduleClass> classes = day.getScheduleClasses().stream()
                    .filter(scheduleClass -> scheduleClass.getCoach().getPhone().equals(coachLogin))
                    .collect(toSet());

            day.setScheduleClasses(classes);
            Schedule filteredSchedule = new Schedule();
            filteredSchedule.setGym(sc.getGym());
            filteredSchedule.setScheduleDays(singleton(day));
            result.add(filteredSchedule);
        }

        return result;
    }

    public Schedule get(int id) {
        return scheduleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
