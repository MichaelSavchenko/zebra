package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.dto.AbonResponce;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.service.AbonService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("abons")
@CrossOrigin
public class AbonController {

    private final AbonService abonService;

    public AbonController(AbonService abonService) {
        this.abonService = abonService;
    }

    @GetMapping
    public List<AbonResponce> getAll() {
        List<Abon> all = abonService.getAll();
        return all.stream().map(AbonResponce::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/{abonId}")
    public Abon get(@PathVariable int abonId) {
        return abonService.get(abonId);
    }

    @GetMapping("/all")
    public List<Abon> getAllByStudent(@RequestParam Integer userId) {
        return abonService.getAllByUser(userId);
    }

    @GetMapping("/all-year")
    public List<Abon> getAllByStudent(@RequestParam Integer userId, @RequestParam String year) {
        return abonService.getAllByUserByYear(userId, year);
    }

    @GetMapping("/without-classes")
    public List<Abon> getAllWithoutClasses() {
        return abonService.getAbonsWithoutAvailableClasses();
    }

    @DeleteMapping("/{abonId}")
    public void delete(@PathVariable int abonId) {
        abonService.delete(abonId);
    }

    @PostMapping
    public Abon createAbon(@RequestBody AbonDto abonDto) {
        return abonService.createAbon(abonDto);
    }

    @PutMapping
    public Abon updateAbon(@RequestBody AbonDto abonDto) {
        return abonService.updateAbon(abonDto);
    }

    @GetMapping("/by-period")
    public List<Abon> getAll(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return abonService.getAllByPeriod(start, end);
    }
}
