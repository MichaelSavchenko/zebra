package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.service.ScheduleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("telegram")
@CrossOrigin
public class TelegramController {

    private final ScheduleService scheduleService;

    public TelegramController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public List<Schedule> getAll() {
        long start = System.currentTimeMillis();
        List<Schedule> all = scheduleService.getAll();
        System.out.println(System.currentTimeMillis() - start);

        return all;
    }
}
