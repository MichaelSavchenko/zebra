package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.SalaryDto;
import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    private final ClassRepository classRepository;
    private final PriceService priceService;
    private final static Set<ClassType> perClassTypes = new HashSet<>();
    private static Map<ClassType, Integer> perClassPrices = new HashMap<>();

    public SalaryService(ClassRepository classRepository, PriceService priceService) {
        this.classRepository = classRepository;
        this.priceService = priceService;
        perClassTypes.add(ClassType.FITNESS);
    }

    public List<SalaryDto> getSalary(LocalDate start, LocalDate end) {
        setupPerClassPrices();

        List<Clazz> classes = classRepository.findByDateTimeBetween(start.atStartOfDay(), end.atTime(LocalTime.MAX));

        Map<Coach, List<Clazz>> coachToClasses = classes.stream()
                .collect(Collectors.groupingBy(Clazz::getCoach));

        List<SalaryDto> result = new ArrayList<>();

        for (Coach coach : coachToClasses.keySet()) {
            List<Clazz> clazzes = coachToClasses.get(coach);

            Map<ClassType, Long> collect = clazzes.stream()
                    .collect(Collectors.groupingBy(Clazz::getClassType, Collectors.summingLong(value -> value.getStudents().size())));

            int salary = 0;

            for (Clazz clazz : clazzes) {
                int forOneClass;
                ClassType classType = clazz.getClassType();
                int numberOfStudents = clazz.getStudents().size();
                if (perClassTypes.contains(classType) && numberOfStudents > 0) {
                    forOneClass = perClassPrices.get(classType);
                } else {
                    forOneClass = numberOfStudents * clazz.getCostPerStudent();
                }
                salary = salary + forOneClass;
            }

            result.add(new SalaryDto(coach.getLastName(), salary, collect));
        }


        return result;
    }

    private void setupPerClassPrices() {
        if (perClassPrices.isEmpty()) {
            for (ClassType type: perClassTypes) {
                Price price = priceService.get(type);
                perClassPrices.put(type, price.getCostPerClass());
            }
        }
    }
}
