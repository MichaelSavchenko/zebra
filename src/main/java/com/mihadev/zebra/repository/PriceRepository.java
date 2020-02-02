package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Integer> {

    Price findByClassType(ClassType classType);
}
