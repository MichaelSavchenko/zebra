package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.service.AbonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("abons")
public class AbonController {

    private final AbonService abonService;

    public AbonController(AbonService abonService) {
        this.abonService = abonService;
    }

    @GetMapping
    public List<Abon> getAll() {
        return abonService.getAll();
    }

    @GetMapping("/{abonId}")
    public Abon get(@PathVariable int abonId) {
        return abonService.get(abonId);
    }

    @PostMapping
    public Abon createAbon(@RequestBody AbonDto abonDto) {
        return abonService.createAbon(abonDto);
    }

    @PutMapping
    public Abon updateAbon(@RequestBody AbonDto abonDto) {
        return abonService.updateAbon(abonDto);
    }
}
