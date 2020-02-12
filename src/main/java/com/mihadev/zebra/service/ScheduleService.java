package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.dto.DayDto;
import com.mihadev.zebra.dto.ScheduleDto;
import com.mihadev.zebra.entity.Gym;
import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.GymRepository;
import com.mihadev.zebra.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mihadev.zebra.utils.CollectionUtils.toList;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GymRepository gymRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, GymRepository gymRepository) {
        this.scheduleRepository = scheduleRepository;
        this.gymRepository = gymRepository;
    }

    public List<Schedule> getAll() {
        return toList(scheduleRepository.findAll());
    }

    public Schedule get(int id) {
        return scheduleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Schedule save(ScheduleDto dto) {
        Schedule schedule = fromDto(dto);
        return scheduleRepository.save(schedule);
    }

    private Schedule fromDto(ScheduleDto dto) {
        Schedule schedule = new Schedule();
        if (Objects.nonNull(dto.getId())) {
            schedule.setId(dto.getId());
        }
        Gym gym = gymRepository.findById(dto.getGymDto().getId()).orElseThrow(RuntimeException::new);
        schedule.setGym(gym);
        List<ScheduleDay> scheduleDays = getScheduleDays(dto.getDays());
        schedule.setScheduleDays(scheduleDays);
        return schedule;
    }

    private List<ScheduleDay> getScheduleDays(List<DayDto> days) {
        return days.stream()
                .map(dayDto -> {
                    ScheduleDay scheduleDay = new ScheduleDay();
                    scheduleDay.setDayOfWeek(dayDto.getDayOfWeek());
                    List<ScheduleClass> scheduleClasses = toScheduleClasses(dayDto.getClassDtos());
                    scheduleDay.setScheduleClasses(scheduleClasses);
                    return scheduleDay;
                })
                .collect(Collectors.toList());
    }

    private List<ScheduleClass> toScheduleClasses(List<ClassDto> classDtos) {
        return classDtos.stream()
                .map(classDto -> {
                    ScheduleClass scheduleClass = new ScheduleClass();
                    scheduleClass.setCoachId(classDto.getCoachId());
                    scheduleClass.setStartTime(classDto.getTime());
                    return scheduleClass;
                })
                .collect(Collectors.toList());
    }

}
