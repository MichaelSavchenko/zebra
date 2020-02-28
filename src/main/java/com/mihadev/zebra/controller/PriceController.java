package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.service.PriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("price")
@CrossOrigin
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<Price> getAll() {
        return priceService.getAll();
    }

    @GetMapping("/{type}")
    public Price get(@PathVariable String type) {
        return priceService.get(type);
    }

    @PutMapping
    public Price update(@RequestBody Price price) {
        return priceService.update(price);
    }
}
