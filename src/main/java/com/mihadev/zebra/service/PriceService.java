package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mihadev.zebra.utils.CollectionUtils.toList;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> getAll() {
        return toList(priceRepository.findAll());
    }

    public Price get(String type) {
        return priceRepository.findByClassType(ClassType.valueOf(type));
    }

    public Price update(Price price) {
        priceRepository.save(price);
        return price;
    }
}
