package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.repository.PriceRepository;
import org.springframework.stereotype.Service;

import static com.mihadev.zebra.entity.ClassType.ACROBATICS;

@Service
public class PriceScript {
    private final PriceRepository priceRepository;

    public PriceScript(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public void setup() {
        priceRepository.deleteAll();

        Price price = new Price();
        price.setClassType(ACROBATICS);
        price.setCostPerStudent(17);
        priceRepository.save(price);


    }
}
