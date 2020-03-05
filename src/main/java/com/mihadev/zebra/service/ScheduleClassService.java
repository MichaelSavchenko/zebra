package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.repository.ScheduleClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.mihadev.zebra.service.ClassService.getLocalDateDependingOnToday;

@Service
public class ScheduleClassService {

    private final ScheduleClassRepository scheduleClassRepository;
    private final ClassService classService;

    public ScheduleClassService(ScheduleClassRepository scheduleClassRepository, ClassService classService) {
        this.scheduleClassRepository = scheduleClassRepository;
        this.classService = classService;
    }

    public Clazz getOrCreateBySchedule(int scheduleClassId) {
        ScheduleClass scheduleClass = scheduleClassRepository.findById(scheduleClassId).orElseThrow(RuntimeException::new);

        return classService.findByScheduleClass(scheduleClass)
                .orElseGet(() -> {
                    ClassDto classDto = new ClassDto();
                    classDto.setClassType(scheduleClass.getClassType());
                    LocalDate localDateDependingOnToday = getLocalDateDependingOnToday(scheduleClass.getScheduleDay().getDayOfWeek());
                    classDto.setDateTime(LocalDateTime.of(localDateDependingOnToday, scheduleClass.getStartTime()));
                    classDto.setCoachId(scheduleClass.getCoach().getId());

                    return classService.saveClass(classDto);
                });
    }
}
