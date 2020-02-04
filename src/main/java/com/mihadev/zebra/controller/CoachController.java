package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.CoachDto;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.service.CoachService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coaches")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public List<Coach> getAll(){
        return coachService.getAll();
    }

    @GetMapping("{coachId}")
    public Coach get(@PathVariable int coachId) {
        return coachService.get(coachId);
    }

    @PostMapping
    public Coach create(@RequestBody CoachDto coachDto) {
        return coachService.create(coachDto);
    }

    @PutMapping
    public Coach update(@RequestBody CoachDto coachDto) {
        return coachService.update(coachDto);
    }
}
