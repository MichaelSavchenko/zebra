package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.AbonClazz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AbonClazzRepository extends CrudRepository<AbonClazz, Integer> {

    List<AbonClazz> findAllByAbon(Abon abon);
}
