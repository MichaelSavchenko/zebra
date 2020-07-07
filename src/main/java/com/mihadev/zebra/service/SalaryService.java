package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.SalaryDto;
import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    private final ClassRepository classRepository;

    public SalaryService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<SalaryDto> getSalary(LocalDate start, LocalDate end) {
        List<Clazz> classes = classRepository.findByDateTimeBetween(start.atStartOfDay(), end.atTime(LocalTime.MAX));

        Map<Coach, List<Clazz>> coachToClasses = classes.stream()
                .collect(Collectors.groupingBy(Clazz::getCoach));

        List<SalaryDto> result = new ArrayList<>();

        for (Coach coach : coachToClasses.keySet()) {
            List<Clazz> clazzes = coachToClasses.get(coach);

            Map<ClassType, Long> collect = clazzes.stream()
                    .collect(Collectors.groupingBy(Clazz::getClassType, Collectors.counting()));

            int salary = 0;

            for (Clazz clazz : clazzes) {
                int forOneClass = clazz.getStudents().size() * clazz.getCostPerStudent();
                salary = salary + forOneClass;
            }

            result.add(new SalaryDto(coach.getLastName(), salary, collect));
        }


        return result;
    }
}
