package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.service.AbonClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("abon-classes")
@CrossOrigin
public class AbonClassController {

    private final AbonClassService abonClassService;

    public AbonClassController(AbonClassService abonClassService) {
        this.abonClassService = abonClassService;
    }

    @GetMapping("/{abonId}")
    public List<Clazz> getByAbon(@PathVariable int abonId) {
        return abonClassService.getByAbon(abonId);
    }
}
