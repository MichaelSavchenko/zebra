package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.entity.*;
import com.mihadev.zebra.repository.AbonClazzRepository;
import com.mihadev.zebra.repository.AbonRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class AbonService {

    private final AbonRepository abonRepository;
    private final AbonClazzRepository abonClazzRepository;
    private final StudentRepository studentRepository;

    private Map<Integer, Abon> cache;

    public AbonService(
            AbonRepository abonRepository,
            AbonClazzRepository abonClazzRepository,
            StudentRepository studentRepository) {
        this.abonRepository = abonRepository;
        this.abonClazzRepository = abonClazzRepository;
        this.studentRepository = studentRepository;
        this.cache = new HashMap<>();
    }

    public Abon createAbon(AbonDto abonDto) {
        Abon abon = fromDto(abonDto);
        AdminEntityService.setup(abon);
        abonRepository.save(abon);
        cache.clear();
        return abon;
    }

    public Abon updateAbon(AbonDto abonDto) {
        Abon abon = fromDto(abonDto);
        AdminEntityService.setup(abon);
        abonRepository.save(abon);
        cache.clear();
        return abon;
    }

    public List<Abon> getAll() {
        if (cache.isEmpty()) {
            List<Abon> abons = getAbonsFor2Month();
            checkMultiplyActiveAbons(abons);

            cache = abons.stream().collect(Collectors.toMap(Abon::getId, abon -> abon));

            return abons;
        }

        return new ArrayList<>(cache.values());
    }

    public List<Abon> getAbonsWithoutAvailableClasses() {
        List<Abon> abons = getAbonsFor2Month();

        List<Abon> result = new ArrayList<>();

        abons.forEach(a -> {
            int numberOfUsedClasses = a.getAbonClazzes().size();
            if (numberOfUsedClasses - a.getNumberOfClasses() > 0) {
                result.add(a);
            }
        });

        return result;
    }

    private List<Abon> getAbonsFor2Month() {
        //todo change back to 2 month
        LocalDate twoMonthAgo = LocalDate.now().minusMonths(3);
        return toList(abonRepository.findByStartDateIsAfter(twoMonthAgo));
    }

    private void checkMultiplyActiveAbons(List<Abon> abons) {
        Map<Student, List<Abon>> studentAbons = abons.stream()
                .collect(Collectors.groupingBy(abon ->
                        abon.getStudents().stream()
                                .findFirst()
                                .orElse(new Student())));

        for (List<Abon> abonsOfSingleStudent : studentAbons.values()) {
            setActiveAbons(new HashSet<>(abonsOfSingleStudent));
        }
    }

    public Abon get(int id) {
        Abon abon = abonRepository.findById(id).orElseThrow(RuntimeException::new);
        checkActive(abon);
        return abon;
    }

    public List<Abon> getAllByUser(Integer userId) {
        Student st = new Student();
        st.setId(userId);
        List<Abon> abonList = abonRepository.findByStudents(st);
        checkMultiplyActiveAbons(abonList);

        return abonList;
    }

    private void checkActive(Abon abon) {
        if (!abon.getStudents().isEmpty()) {
            List<Abon> byStudents = abonRepository.findByStudents(abon.getStudents().stream().findFirst()
                    .orElseThrow(RuntimeException::new));

            setActiveAbons(new HashSet<>(byStudents));
        } else {
            abon.setActive(false);
        }
    }

    static void setActiveAbons(Set<Abon> studentAbons) {
        studentAbons.forEach(abon -> abon.setActive(false));

        List<Abon> pdAbons = getPoleDanceAbons(studentAbons);

        calculateActiveAbonForStudent(pdAbons, LocalDate.now())
                .flatMap(activeAbon -> studentAbons.stream()
                        .filter(abon -> abon.getId() == activeAbon.getId())
                        .findFirst())
                .ifPresent(abon -> abon.setActive(true));

        List<Abon> stAbons = getStretchingAbons(studentAbons);

        calculateActiveAbonForStudent(stAbons, LocalDate.now())
                .flatMap(activeAbon -> studentAbons.stream()
                        .filter(abon -> abon.getId() == activeAbon.getId())
                        .findFirst())
                .ifPresent(abon -> abon.setActive(true));
    }

    public void delete(int id) {
        abonRepository.findById(id).ifPresent(abon -> {
            abonClazzRepository.deleteAllByAbon(abon);
            abonRepository.delete(abon);
        });
    }

    private Abon fromDto(AbonDto abonDto) {
        Iterable<Student> students = studentRepository.findAllById(abonDto.getStudents());

        Abon abon = nonNull(abonDto.getId()) ?
                abonRepository.findById(abonDto.getId()).orElseThrow(RuntimeException::new)
                : new Abon();

        abon.setStudents(toSet(students));
        abon.setStartDate(abonDto.getStartDate());
        abon.setFinishDate(abonDto.getFinishDate());
        abon.setActive(abonDto.isActive());
        abon.setPaid(abonDto.isPaid());
        abon.setPrice(abonDto.getPrice());
        abon.setNumberOfClasses(abonDto.getNumberOfClasses());
        abon.setAbonType(abonDto.getAbonType());
        abon.setNotes(abonDto.getNotes());
        return abon;
    }

    void checkAbons(Set<Student> newStudents, Clazz clazz) {
        List<AbonClazz> toUpdate = new ArrayList<>();
        List<Abon> abonToUpdate = new ArrayList<>();
        for (Student student : newStudents) {
            Abon abon = getAbonOfRightType(clazz, student);
            abonToUpdate.add(abon);
            toUpdate.add(new AbonClazz(abon, clazz));
        }

        abonRepository.saveAll(abonToUpdate);
        abonClazzRepository.saveAll(toUpdate);
    }

    void unCheckAbons(Set<Student> students, Clazz clazz) {
        List<Abon> forUpdate = new ArrayList<>();
        List<Integer> deleted = new ArrayList<>();

        for (Student student : students) {
            List<Abon> abons = new ArrayList<>(student.getAbons());
            forUpdate.addAll(abons);

            for (Abon abon : abons) {
                for (AbonClazz abonClazz : abon.getAbonClazzes()) {
                    if (abonClazz.getAbon().getId() == abon.getId() &&
                            abonClazz.getClazz().getId() == clazz.getId() &&
                            !deleted.contains(abonClazz.getId())
                    ) {
                        System.out.println("For remove " + abonClazz.getId());
                        deleted.add(abonClazz.getId());
                        abonClazzRepository.delete(abonClazz);
                    }
                }
            }
        }

        abonRepository.saveAll(forUpdate);
    }

    public static Optional<Abon> calculateActiveAbonForStudent(List<Abon> abons, LocalDate clazzDate) {
        return calculateActiveAbonForStudent(new HashSet<>(abons), clazzDate);
    }

    public static Optional<Abon> calculateActiveAbonForStudent(Set<Abon> abons, LocalDate clazzDate) {
        List<Abon> afterToday = abons.stream()
                .filter(abon -> {
                    if (isNull(abon.getFinishDate())) {
                        return true;
                    } else {
                        return abon.getFinishDate().isEqual(clazzDate) || abon.getFinishDate().isAfter(clazzDate);
                    }
                })
                .collect(Collectors.toList());

        if (afterToday.isEmpty()) {
            return Optional.empty();
        } else if (afterToday.size() == 1) {
            return afterToday.stream().findFirst();
        } else {

            List<Abon> withClasses = afterToday.stream()
                    .filter(abon -> (abon.getNumberOfClasses() - abon.getAbonClazzes().size()) > 0)
                    .collect(Collectors.toList());

            if (withClasses.isEmpty()) {
                return afterToday.stream().max(finishDateComparator());
            } else if (withClasses.size() == 1) {
                return withClasses.stream().findFirst();
            } else {

                List<Abon> withUsedClasses = withClasses.stream()
                        .filter(a -> a.getNumberOfUsedClasses() > 0)
                        .collect(Collectors.toList());

                if (withUsedClasses.isEmpty()) {
                    return withClasses.stream().min(finishDateComparator());
                } else {
                    return withUsedClasses.stream().max(finishDateComparator());
                }
            }
        }
    }

    private static boolean hasNoMoreClasses(Abon abon) {
        return nonNull(abon.getAbonClazzes()) && ((abon.getNumberOfClasses() - abon.getAbonClazzes().size()) <= 0);
    }

    private static Comparator<Abon> finishDateComparator() {
        return (o1, o2) -> {
            if (isNull(o1.getFinishDate()) && isNull(o2.getFinishDate())) {
                return 0;
            } else if (isNull(o1.getFinishDate())) {
                return 1;
            } else if (isNull(o2.getFinishDate())) {
                return -1;
            } else if (o1.getFinishDate().isEqual(o2.getFinishDate())) {
                return 0;
            } else if (o1.getFinishDate().isAfter(o2.getFinishDate())) {
                return 1;
            } else return -1;
        };
    }

    private Supplier<Abon> createdAbon(Student student, LocalDate clazzDate) {
        return () -> {
            Abon abon = new Abon();
            abon.setPaid(false);
            abon.setAbonType(AbonType.PD);
            abon.setNumberOfClasses(1);
            abon.setStartDate(clazzDate);
            abon.setFinishDate(clazzDate.plusMonths(1));
            abon.setStudents(Collections.singleton(student));
            abon.setAutoCreated(true);
            return abon;
        };
    }

    private Abon getAbonOfRightType(Clazz clazz, Student student) {
        Set<Abon> abons = student.getAbons();
        LocalDate clazzDate = clazz.getDateTime().toLocalDate();

        if (ClassType.STRETCHING == clazz.getClassType() || ClassType.STRIP_PLASTIC == clazz.getClassType()) {
            List<Abon> stretchingAbons = getStretchingAbons(abons);

            if (!stretchingAbons.isEmpty()) {
                Optional<Abon> abon = calculateActiveAbonForStudent(stretchingAbons, clazzDate);

                if (abon.isPresent()) {
                    return abon.get();
                }
            }
        }

        return calculateActiveAbonForStudent(getPoleDanceAbons(abons), clazzDate)
                .orElseGet(createdAbon(student, clazzDate));
    }

    private static List<Abon> getPoleDanceAbons(Set<Abon> abons) {
        return abons.stream()
                .filter(abon -> abon.getAbonType() == AbonType.PD)
                .collect(Collectors.toList());
    }

    private static List<Abon> getStretchingAbons(Set<Abon> abons) {
        return abons.stream()
                .filter(abon -> abon.getAbonType() == AbonType.ST)
                .collect(Collectors.toList());
    }

    public List<Abon> getAllByPeriod(LocalDate start, LocalDate end) {
        return toList(abonRepository.findByDateTimeBetweenOrderByDateTimeDesc(start.atStartOfDay(), end.plusDays(1).atStartOfDay()));
    }
}
