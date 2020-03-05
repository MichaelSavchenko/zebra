package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.service.ScheduleClassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scheduleClass")
@CrossOrigin
public class ScheduleClassController {

    private final ScheduleClassService scheduleClassService;

    public ScheduleClassController(ScheduleClassService scheduleClassService) {
        this.scheduleClassService = scheduleClassService;
    }

    @GetMapping("{scheduleClassId}")
    public Clazz getOrCreate(@PathVariable int scheduleClassId) {
        return scheduleClassService.getOrCreateBySchedule(scheduleClassId);
    }
}
