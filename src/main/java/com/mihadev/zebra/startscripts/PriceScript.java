package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceScript {
    private final PriceRepository priceRepository;
    private static final int HIGH = 13;
    private static final int LOW = 11;


    public PriceScript(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    //todo - add all real prises
    public void setup() {
        priceRepository.deleteAll();

        List<Price> prices = new ArrayList<>();

        for (ClassType type : ClassType.values()) {
            Price price = new Price();
            price.setClassType(type);
            if (type == ClassType.EXOT || type == ClassType.SILKS || type == ClassType.POLE_DANCE) {
                price.setCostPerStudent(HIGH);
            } else {
                price.setCostPerStudent(LOW);
            }
            prices.add(price);
        }

        priceRepository.saveAll(prices);
    }
}
