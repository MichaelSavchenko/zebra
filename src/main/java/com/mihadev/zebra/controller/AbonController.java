package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.dto.AbonResponce;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.service.AbonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Abon> getAllByStudent(@RequestParam String ids) {
        List<Integer> collect = Stream.of(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return abonService.getAllByUser(collect);
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


}
