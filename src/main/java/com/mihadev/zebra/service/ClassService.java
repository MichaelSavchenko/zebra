package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.repository.CoachRepository;
import com.mihadev.zebra.repository.PriceRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
        AdminEntityService.setup(clazz);
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

    @Transactional
    public Clazz addStudents(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        AdminEntityService.setup(clazz);
        Set<Student> students = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().addAll(students);
        abonService.checkAbons(students, clazz);
        classRepository.save(clazz);
        return clazz;
    }

    @Transactional
    public Clazz removeStudents(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        AdminEntityService.setup(clazz);
        Set<Student> toDelete = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().removeAll(toDelete);
        abonService.unCheckAbons(toDelete, clazz);
        classRepository.save(clazz);
        return clazz;
    }

    Optional<Clazz> findByScheduleClass(ScheduleClass scheduleClass) {
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
        LocalDateTime twoMonthAgo = LocalDateTime.now().minusMonths(2);
        return toList(classRepository.findByDateTimeIsAfterOrderByDateTimeDesc(twoMonthAgo));
    }

    public List<Clazz> getAllByPeriod(LocalDate startDate, LocalDate endDate) {
        return toList(classRepository.findByDateTimeBetweenOrderByDateTimeDesc(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()));
    }

    public List<Clazz> getAllByStudent(Integer userId) {
        Set<Clazz> clazzes = studentRepository.findById(userId).map(Student::getClasses).orElse(new HashSet<>());

        clazzes.forEach(clazz -> clazz.setStudents(new HashSet<>()));

        return new ArrayList<>(clazzes);
    }

    public Clazz getClass(int classId) {
        return classRepository.findById(classId).orElseThrow(RuntimeException::new);

    }

    public List<Clazz> getClassesByCoach(String coachLogin, LocalDate start, LocalDate end) {
        Coach coach = coachRepository.findByPhone(coachLogin)
                .orElseThrow(() -> new RuntimeException("Can not find coach with login: " + coachLogin));

        return classRepository.findByCoachAndDateTimeBetween(coach, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
    }

    public List<Clazz> getAllByStudentByPeriod(Integer userId, LocalDate start, LocalDate end) {
        return getAllByStudent(userId).stream()
                .filter(clazz ->
                        clazz.getDateTime().isAfter(start.atStartOfDay()) &&
                                clazz.getDateTime().isBefore(end.plusDays(1).atStartOfDay()))
                .collect(Collectors.toList());
    }

    public List<Clazz> getClassesByCoachByDate(String coachLogin, LocalDate date) {
        Coach coach = coachRepository.findByPhone(coachLogin)
                .orElseThrow(() -> new RuntimeException("Can not find coach with login: " + coachLogin));

        return classRepository.findByCoachAndDateTimeBetween(coach, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }

    public List<Clazz> getClassesByCoachByDateByType(int classId, int numberOfWeeks) {
        Clazz aClass = getClass(classId);
        LocalDateTime dayOfClass = aClass.getDateTime();
        LocalDateTime fromDate = dayOfClass.minusWeeks(numberOfWeeks);

        return classRepository
                .findByCoachAndClassTypeAndDateTimeBetween(aClass.getCoach(), aClass.getClassType(), fromDate, dayOfClass)
                .stream()
                .filter(getDayTimePredicate(dayOfClass))
                .collect(Collectors.toList());
    }

    private Predicate<Clazz> getDayTimePredicate(LocalDateTime dayOfClass) {
        return clazz ->
                clazz.getDateTime().getDayOfWeek() == dayOfClass.getDayOfWeek()
                        && clazz.getDateTime().getHour() == dayOfClass.getHour();
    }
}
