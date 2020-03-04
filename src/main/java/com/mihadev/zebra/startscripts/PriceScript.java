package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.repository.PriceRepository;
import org.springframework.stereotype.Service;

import static com.mihadev.zebra.entity.ClassType.ACROBATICS;
import static com.mihadev.zebra.entity.ClassType.POLE_DANCE;

@Service
public class PriceScript {
    private final PriceRepository priceRepository;

    public PriceScript(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    //todo - add all real prises
    public void setup() {
        priceRepository.deleteAll();

        Price price1 = new Price();
        price1.setClassType(ACROBATICS);
        price1.setCostPerStudent(17);
        priceRepository.save(price1);

        Price price2 = new Price();
        price2.setClassType(POLE_DANCE);
        price2.setCostPerStudent(17);
        priceRepository.save(price2);

    }
}
