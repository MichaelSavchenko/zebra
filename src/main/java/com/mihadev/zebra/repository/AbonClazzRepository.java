package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.AbonClazz;
import org.springframework.data.repository.CrudRepository;

public interface AbonClazzRepository extends CrudRepository<AbonClazz, Integer> {
    public void deleteAllByAbon(Abon abon);

    public void deleteById(Integer id);
}
