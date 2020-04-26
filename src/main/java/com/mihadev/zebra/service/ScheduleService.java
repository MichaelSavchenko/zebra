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
import java.util.stream.Collectors;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static java.util.Collections.singleton;

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

            cache = new ArrayList<>(schedules);

            return schedules;
        }

        return cache;
    }

    public List<Schedule> getScheduleForDay(String coachLogin) {
        List<Schedule> all = getAll();
        List<Schedule> result = new ArrayList<>();
        DayOfWeek today = DayOfWeek.FRIDAY;

        for (Schedule sc : all) {

            ScheduleDay day = sc.getScheduleDays().stream()
                    .filter(scheduleDay -> scheduleDay.getDayOfWeek() == today)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Schedule for " + LocalDate.now().getDayOfWeek() + "is not found."));



            Set<ScheduleClass> classes = day.getScheduleClasses().stream()
                    .filter(scheduleClass -> scheduleClass.getCoach().getPhone().equals(coachLogin))
                    .collect(Collectors.toCollection(() -> new TreeSet<>(new ScheduleClassComparator())));

            ScheduleDay resultDay = new ScheduleDay();
            resultDay.setId(day.getId());
            resultDay.setDayOfWeek(today);
            resultDay.setScheduleClasses(classes);

            Schedule filteredSchedule = new Schedule();
            filteredSchedule.setGym(sc.getGym());
            filteredSchedule.setScheduleDays(singleton(resultDay));

            result.add(filteredSchedule);
        }

        return result;
    }

    public Schedule get(int id) {
        return scheduleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
