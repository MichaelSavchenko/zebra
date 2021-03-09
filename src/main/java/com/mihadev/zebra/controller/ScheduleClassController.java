package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.ScheduleClassDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
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

    @PostMapping
    public ScheduleClass saveScheduleClass(ScheduleClassDto dto) {
        System.out.println("********** --------- **********" + dto);
        return scheduleClassService.save(dto);
    }

    @PutMapping
    public ScheduleClass updateScheduleClass(ScheduleClassDto dto) {
        return scheduleClassService.save(dto);
    }

    @DeleteMapping
    public void deleteScheduleClass(int id) {
        scheduleClassService.delete(id);
    }
}
