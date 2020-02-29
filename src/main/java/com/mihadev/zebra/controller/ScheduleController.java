package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.ScheduleDto;
import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedule")
@CrossOrigin
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleService.getAll();
    }

    @GetMapping("/{scheduleId}")
    public Schedule get(@PathVariable int scheduleId) {
        return scheduleService.get(scheduleId);
    }

    /*@PutMapping
    public Schedule save(ScheduleDto dto) {
        return scheduleService.save(dto);
    }*/
}
