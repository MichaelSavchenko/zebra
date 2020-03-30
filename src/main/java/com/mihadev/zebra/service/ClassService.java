package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.repository.CoachRepository;
import com.mihadev.zebra.repository.PriceRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static java.util.Objects.*;
import static java.util.Objects.isNull;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final CoachRepository coachRepository;
    private final AbonService abonService;
    private final PriceRepository priceRepository;

    public ClassService(
            ClassRepository classRepository,
            StudentRepository studentRepository,
            CoachRepository coachRepository,
            AbonService abonService,
            PriceRepository priceRepository
    ) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.coachRepository = coachRepository;
        this.abonService = abonService;
        this.priceRepository = priceRepository;
    }


    public Clazz saveClass(ClassDto classDto) {
        Clazz clazz = fromDto(classDto);
        classRepository.save(clazz);
        return clazz;
    }

    private Clazz fromDto(ClassDto classDto) {
        Coach coach = nonNull(classDto.getCoachId()) ?
                coachRepository.findById(classDto.getCoachId()).orElseThrow(RuntimeException::new) :
                coachRepository.findByPhone(classDto.getCoachLogin()).orElseThrow(RuntimeException::new);

        Clazz clazz = isNull(classDto.getId()) ? new Clazz()
                : classRepository.findById(classDto.getId()).orElseThrow(RuntimeException::new);
        clazz.setCoach(coach);
        clazz.setClassType(classDto.getClassType());
        int costPerStudent = priceRepository.findByClassType(classDto.getClassType()).getCostPerStudent();
        clazz.setCostPerStudent(costPerStudent);
        clazz.setDateTime(classDto.getDateTime());

        return clazz;
    }

    //todo test
    public Clazz addStudents(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        Set<Student> students = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().addAll(students);
        abonService.checkAbons(students, clazz.getClassType());
        classRepository.save(clazz);
        return clazz;
    }

    //todo test
    public Clazz removeStudents(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        Set<Student> toDelete = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().removeAll(toDelete);
        abonService.unCheckAbons(toDelete, clazz);
        classRepository.save(clazz);
        return clazz;
    }

    public Optional<Clazz> findByScheduleClass(ScheduleClass scheduleClass) {
        LocalDate date = getLocalDateDependingOnToday(scheduleClass.getScheduleDay().getDayOfWeek());
        LocalDateTime localDateTime = LocalDateTime.of(date, scheduleClass.getStartTime());
        List<Clazz> result = classRepository.findByClassTypeAndDateTimeAndCoach(scheduleClass.getClassType(), localDateTime, scheduleClass.getCoach());
        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }

    static LocalDate getLocalDateDependingOnToday(DayOfWeek dayOfWeek) {
        return LocalDate.now().minusDays(
                LocalDate.now().getDayOfWeek().ordinal() - dayOfWeek.ordinal());
    }

    public List<Clazz> getAll() {
        return toList(classRepository.findTop1000ByOrderByDateTimeDesc());
    }

    public Clazz getClass(int classId) {
        return classRepository.findById(classId).orElseThrow(RuntimeException::new);

    }
}
