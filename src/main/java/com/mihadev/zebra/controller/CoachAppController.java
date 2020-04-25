package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.service.ClassService;
import com.mihadev.zebra.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coach-app")
@CrossOrigin
public class CoachAppController {

    private final ClassService classService;
    private final ScheduleService scheduleService;

    public CoachAppController(ClassService classService, ScheduleService scheduleService) {
        this.classService = classService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/classes")
    public List<Clazz> getAllClassesByCoach(@RequestParam String coachLogin) {
        System.out.println(coachLogin + " tries to get his classes");
        return classService.getClassesByCoach("+" + coachLogin);
    }

    @GetMapping("/schedule-day")
    public List<Schedule> getScheduleForDay(@RequestParam String coachLogin) {
        System.out.println(coachLogin + " tries to get her schedule");
        return scheduleService.getScheduleForDay("+" + coachLogin);
    }
}
