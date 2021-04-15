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

    public static final int MONTHS_TO_SUBTRACT = 2;
    private final AbonRepository abonRepository;
    private final AbonClazzRepository abonClazzRepository;
    private final StudentService studentService;

    public AbonService(
            AbonRepository abonRepository,
            AbonClazzRepository abonClazzRepository,
            StudentService studentService) {
        this.abonRepository = abonRepository;
        this.abonClazzRepository = abonClazzRepository;
        this.studentService = studentService;
    }

    public Abon createAbon(AbonDto abonDto) {
        Abon abon = fromDto(abonDto);
        AdminEntityService.setup(abon);
        abonRepository.save(abon);
        return abon;
    }

    public Abon updateAbon(AbonDto abonDto) {
        Abon abon = fromDto(abonDto);
        AdminEntityService.setup(abon);
        abonRepository.save(abon);
        return abon;
    }

    public List<Abon> getAll() {
        long startTime = System.currentTimeMillis();
        List<Abon> result = checkMultiplyActiveAbons();
        long finishTime = System.currentTimeMillis();
        System.out.println("Check Multiply abons for cache took: " + (finishTime - startTime));

        return result;
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
        LocalDate twoMonthAgo = LocalDate.now().minusMonths(2);
        return toList(abonRepository.findByStartDateIsAfter(twoMonthAgo));
    }

    private List<Abon> checkMultiplyActiveAbons() {
        long start = System.currentTimeMillis();
        Map<Integer, List<Abon>> test = studentService.getAll().stream()
                .flatMap(student -> student.getAbons().stream())
                .filter(abon -> abon.getStartDate().isAfter(LocalDate.now().minusMonths(MONTHS_TO_SUBTRACT)))
                .collect(Collectors.groupingBy(abon ->
                        abon.getStudents().stream()
                                .findFirst()
                                .map(Student::getId)
                                .orElse(-1)));

        System.out.println("mapping Map<Integer, List<Abon>>:" + (System.currentTimeMillis() - start));

        List<Abon> result = new ArrayList<>();
        for (List<Abon> abonsOfSingleStudent : test.values()) {
            setActiveAbons(new HashSet<>(abonsOfSingleStudent));
            result.addAll(abonsOfSingleStudent);
        }

        return result;
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
        setActiveAbons(new HashSet<>(abonList));

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
        Iterable<Student> students = studentService.findAllById(abonDto.getStudents());

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

        studentService.refreshStudentsCache(newStudents);
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
        studentService.refreshStudentsCache(students);
    }

    public static Optional<Abon> calculateActiveAbonForStudent(List<Abon> abons, LocalDate clazzDate) {
        return calculateActiveAbonForStudent(new HashSet<>(abons), clazzDate);
    }

    public static Optional<Abon> calculateActiveAbonForStudent(Set<Abon> abons, LocalDate clazzDate) {
        List<Abon> afterClazzDate = abons.stream()
                .filter(abon -> {
                    if (isNull(abon.getFinishDate())) {
                        return true;
                    } else {
                        return abon.getFinishDate().isEqual(clazzDate) || abon.getFinishDate().isAfter(clazzDate);
                    }
                })
                .collect(Collectors.toList());

        if (afterClazzDate.isEmpty()) {
            return Optional.empty();
        } else if (afterClazzDate.size() == 1) {
            return afterClazzDate.stream().findFirst();
        } else {
            List<Abon> withClasses = afterClazzDate.stream()
                    .filter(abon -> (abon.getNumberOfClasses() - abon.getAbonClazzes().size()) > 0)
                    .collect(Collectors.toList());

            if (withClasses.isEmpty()) {
                return afterClazzDate.stream().max(finishDateComparator());
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

        System.out.println("Get abon for Student: " + student.getLastName());

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
        return toList(abonRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(start.atStartOfDay(), end.plusDays(1).atStartOfDay()));
    }

    public List<Abon> getAllByUserByYear(Integer userId, String year) {
        return getAllByUser(userId).stream()
                .filter(abon -> abon.getStartDate().getYear() == Integer.parseInt(year))
                .collect(Collectors.toList());
    }

    public void refreshAbonCache() {
        getAll();
    }
}
