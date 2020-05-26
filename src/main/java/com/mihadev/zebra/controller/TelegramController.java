package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.service.ScheduleService;
import com.mihadev.zebra.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("telegram")
@CrossOrigin
public class TelegramController {

    private final ScheduleService scheduleService;
    private final StudentService studentService;

    public TelegramController(ScheduleService scheduleService, StudentService studentService) {
        this.scheduleService = scheduleService;
        this.studentService = studentService;
    }

    @GetMapping("/schedule")
    public List<Schedule> getAll() {
        long start = System.currentTimeMillis();
        List<Schedule> all = scheduleService.getAll();
        System.out.println(System.currentTimeMillis() - start);

        return all;
    }

    @GetMapping("/abons}")
    public Set<Abon> getAbons(@RequestParam String phone) {
        Student student = studentService.getByPhone("+" + phone);
        return student.getAbons();
    }
}
