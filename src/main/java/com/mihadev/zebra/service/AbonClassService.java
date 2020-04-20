package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.AbonClazz;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.repository.AbonClazzRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbonClassService {
    private final AbonClazzRepository abonClazzRepository;
    private final AbonService abonService;

    public AbonClassService(AbonClazzRepository abonClazzRepository, AbonService abonService) {
        this.abonClazzRepository = abonClazzRepository;
        this.abonService = abonService;
    }

    public List<Clazz> getByAbon(int abonId) {
        Abon abon = abonService.get(abonId);

        List<AbonClazz> allByAbon = abonClazzRepository.findAllByAbon(abon);

        List<Clazz> result = new ArrayList<>();

        allByAbon.forEach(abonClazz -> result.add(abonClazz.getClazz()));

        return result;
    }
}
