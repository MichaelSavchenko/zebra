package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.entity.*;
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
    private final StudentRepository studentRepository;

    public AbonService(AbonRepository abonRepository, StudentRepository studentRepository) {
        this.abonRepository = abonRepository;
        this.studentRepository = studentRepository;
    }

    public Abon createAbon(AbonDto abonDto) {
        Abon abon = fromDto(abonDto);
        abonRepository.save(abon);
        return abon;
    }

    public Abon updateAbon(AbonDto abonDto) {
        Abon abon = fromDto(abonDto);
        abonRepository.save(abon);
        return abon;
    }

    public List<Abon> getAll() {
        List<Abon> abons = toList(abonRepository.findAll());
        abons.forEach(this::checkActive);
        return abons;
    }

    public Abon get(int id) {
        Abon abon = abonRepository.findById(id).orElseThrow(RuntimeException::new);
        checkActive(abon);
        return abon;
    }

    private void checkActive(Abon abon) {
        if(! abon.getStudents().isEmpty()) {
            List<Abon> byStudents = abonRepository.findByStudents(abon.getStudents().stream().findFirst()
                    .orElseThrow(RuntimeException::new));

            setActiveAbons(new HashSet<>(byStudents));
        } else {
            abon.setActive(false);
        }
    }

    static void setActiveAbons(Set<Abon> studentAbons) {
        studentAbons.forEach(abon -> abon.setActive(false));

        List<Abon> pdAbons = studentAbons.stream().filter(abon -> abon.getAbonType() == AbonType.PD)
                .collect(Collectors.toList());

        calculateActiveAbonForStudent(pdAbons).ifPresent(activeAbon -> {
            studentAbons.stream()
                    .filter(abon -> abon.getId() == activeAbon.getId())
                    .findFirst()
                    .ifPresent(abon -> abon.setActive(true));
        });


        List<Abon> stAbons = studentAbons.stream().filter(abon -> abon.getAbonType() == AbonType.ST)
                .collect(Collectors.toList());

        calculateActiveAbonForStudent(stAbons).ifPresent(activeAbon -> {
            studentAbons.stream()
                    .filter(abon -> abon.getId() == activeAbon.getId())
                    .findFirst()
                    .ifPresent(abon -> abon.setActive(true));
        });
    }

    public void delete(int id) {
        abonRepository.findById(id).ifPresent(abonRepository::delete);
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

    void checkAbons(Set<Student> newStudents, ClassType classType) {
        List<Abon> toUpdate = new ArrayList<>();
        for (Student student : newStudents) {
            List<Abon> abons = new ArrayList<>(student.getAbons());

            List<Abon> abonsOfRightType = getAbonsOfRightType(abons, classType);
            Abon abon = calculateActiveAbonForStudent(abonsOfRightType)
                    .orElseGet(createdAbon(student));
            abon.setNumberOfUsedClasses(abon.getNumberOfUsedClasses() + 1);
            toUpdate.add(abon);
        }

        abonRepository.saveAll(toUpdate);
    }

    void unCheckAbons(Set<Student> students, Clazz clazz) {
        List<Abon> forRemove = new ArrayList<>();

        for (Student student : students) {
            List<Abon> abons = new ArrayList<>(student.getAbons());

            if (ClassType.STRETCHING == clazz.getClassType()) {
                List<Abon> stretchingAbons = getStretchingAbons(abons);

                if (!stretchingAbons.isEmpty()) {
                    resolveAbonForRemoval(forRemove, stretchingAbons);
                } else {
                    List<Abon> poleDanceAbons = getPoleDanceAbons(abons);
                    resolveAbonForRemoval(forRemove, poleDanceAbons);
                }
            } else {
                List<Abon> poleDanceAbons = getPoleDanceAbons(abons);
                resolveAbonForRemoval(forRemove, poleDanceAbons);
            }

        }

        for (Abon abon : forRemove) {
            abon.setNumberOfUsedClasses(abon.getNumberOfUsedClasses() - 1);
        }

        abonRepository.saveAll(forRemove);
    }

    private void resolveAbonForRemoval(List<Abon> forRemove, List<Abon> poleDanceAbons) {
        Optional<Abon> activePdAbon = calculateActiveAbonForStudent(poleDanceAbons);

        if (activePdAbon.isPresent()) {
            forRemove.add(activePdAbon.get());
        } else {
            poleDanceAbons.stream().max(finishDateComparator()).ifPresent(forRemove::add);
        }
    }


    public static Optional<Abon> calculateActiveAbonForStudent(List<Abon> abons) {
        return calculateActiveAbonForStudent(new HashSet<>(abons));
    }

    public static Optional<Abon> calculateActiveAbonForStudent(Set<Abon> abons) {
        Set<Abon> afterToday = abons.stream()
                .filter(abon -> {
                    if (isNull(abon.getFinishDate())) {
                        return true;
                    } else {
                        return abon.getFinishDate().isEqual(LocalDate.now()) || abon.getFinishDate().isAfter(LocalDate.now());
                    }
                })
                .collect(Collectors.toSet());

        if (afterToday.isEmpty()) {
            return Optional.empty();
        } else if (afterToday.size() == 1) {
            return afterToday.stream().findFirst();
        } else {


            List<Abon> withClasses = afterToday.stream()
                    .filter(abon -> (abon.getNumberOfClasses() - abon.getNumberOfUsedClasses()) > 0)
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

    private Supplier<Abon> createdAbon(Student student) {
        return () -> {
            Abon abon = new Abon();
            abon.setPaid(false);
            abon.setAbonType(AbonType.PD);
            abon.setNumberOfClasses(1);
            abon.setStartDate(LocalDate.now());
            abon.setFinishDate(LocalDate.now());
            abon.setStudents(Collections.singleton(student));
            abon.setAutoCreated(true);
            return abon;
        };
    }

    private List<Abon> getAbonsOfRightType(List<Abon> abons, ClassType classType) {
        if (ClassType.STRETCHING == classType) {
            List<Abon> stretchingAbons = getStretchingAbons(abons);

            if (!stretchingAbons.isEmpty()) {
                return stretchingAbons;
            }
        }

        return getPoleDanceAbons(abons);

    }

    private List<Abon> getPoleDanceAbons(List<Abon> abons) {
        return abons.stream()
                .filter(abon -> abon.getAbonType() == AbonType.PD)
                .collect(Collectors.toList());
    }

    private List<Abon> getStretchingAbons(List<Abon> abons) {
        return abons.stream()
                .filter(abon -> abon.getAbonType() == AbonType.ST)
                .collect(Collectors.toList());
    }
}
