package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coach-app")
@CrossOrigin
public class CoachAppController {

    private final ClassService classService;

    public CoachAppController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public List<Clazz> getAllClassesByCoach(@RequestParam String coachLogin) {
        System.out.println(coachLogin + " tries to get his classes");
        return classService.getClassesByCoach("+" + coachLogin);
    }
}
