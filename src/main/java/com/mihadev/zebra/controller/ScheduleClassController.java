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

    @GetMapping("/schedule-class/{scheduleClassId}")
    public ScheduleClassDto get(@PathVariable int scheduleClassId) {
        return scheduleClassService.getScheduleClass(scheduleClassId);
    }

    @PostMapping
    public ScheduleClass saveScheduleClass(@RequestBody ScheduleClassDto dto) {
        return scheduleClassService.save(dto);
    }

    @PutMapping
    public ScheduleClass updateScheduleClass(@RequestBody ScheduleClassDto dto) {
        return scheduleClassService.save(dto);
    }

    @DeleteMapping
    public void deleteScheduleClass(int id) {
        scheduleClassService.delete(id);
    }
}
