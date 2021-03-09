package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.dto.ScheduleClassDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.ScheduleClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.mihadev.zebra.service.ClassService.getLocalDateDependingOnToday;

@Service
public class ScheduleClassService {

    private final ScheduleClassRepository scheduleClassRepository;
    private final ClassService classService;

    public ScheduleClassService(
            ScheduleClassRepository scheduleClassRepository,
            ClassService classService) {
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

    public void delete(int id) {
        ScheduleClass scheduleClass = new ScheduleClass();
        scheduleClass.setId(id);
        scheduleClassRepository.delete(scheduleClass);
    }

    public ScheduleClass save(ScheduleClassDto dto) {
        ScheduleClass scheduleClass = fromDto(dto);
        return scheduleClassRepository.save(scheduleClass);
    }

    private ScheduleClass fromDto(ScheduleClassDto dto) {
        ScheduleClass scheduleClass = new ScheduleClass();

        if (Objects.nonNull(dto.getId())) {
            scheduleClass.setId(dto.getId());
        }
        scheduleClass.setScheduleDay(getScheduleDay(dto));
        scheduleClass.setClassType(dto.getClassType());
        scheduleClass.setCoach(getCoach(dto.getCoachId()));
        scheduleClass.setClassType(dto.getClassType());
        scheduleClass.setStartTime(LocalTime.parse(dto.getStartTime(), DateTimeFormatter.ISO_LOCAL_TIME));

        System.out.println("*************** ---------------" + scheduleClass.getStartTime());

        return scheduleClass;
    }

    private Coach getCoach(int coachId) {
        Coach coach = new Coach();
        coach.setId(coachId);
        return coach;
    }

    private ScheduleDay getScheduleDay(ScheduleClassDto dto) {
        ScheduleDay scheduleDay = new ScheduleDay();
        scheduleDay.setId(dto.getScheduleDayId());

        return scheduleDay;
    }

}
