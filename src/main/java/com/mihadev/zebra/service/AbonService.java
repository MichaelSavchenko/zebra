package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.entity.*;
import com.mihadev.zebra.repository.AbonRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

import static com.mihadev.zebra.utils.CollectionUtils.toSet;

@Service
public class AbonService {

    private final AbonRepository abonRepository;
    private final StudentRepository studentRepository;

    public AbonService(AbonRepository abonRepository, StudentRepository studentRepository) {
        this.abonRepository = abonRepository;
        this.studentRepository = studentRepository;
    }

    public Abon createAbon(AbonDto abonDto) {
        Iterable<Student> students = studentRepository.findAllById(abonDto.getStudents());

        Abon abon = new Abon();
        abon.setStudents(toSet(students));
        abon.setStartDate(abonDto.getStartDate());
        abon.setFinishDate(abonDto.getFinishDate());
        abon.setActive(abonDto.isActive());
        abon.setPaid(abonDto.isPaid());
        abon.setPrice(abonDto.getPrice());
        abon.setNumberOfClasses(abonDto.getNumberOfClasses());
        abon.setAbonType(abonDto.getAbonType());

        abonRepository.save(abon);
        return abon;
    }

    List<Abon> checkAbons(Clazz clazz) {
        List<Abon> forSave = new ArrayList<>();

        for (Student student: clazz.getStudents()) {
            List<Abon> abons = abonRepository.findByStudentsAndActiveIsTrueOrderByFinishDate(student);

            Optional<Abon> abonOfRightType = getAbonOfRightType(abons, clazz.getClassType());

            Abon abon = abonOfRightType.orElseGet(createdAbon(student));

            abon.getClasses().add(clazz);

            forSave.add(abon);
        }

        abonRepository.saveAll(forSave);

        return forSave;
    }
    List<Abon> unCheckAbons(Set<Student> students, Clazz clazz) {
        List<Abon> persistentAbons = new ArrayList<>(clazz.getAbons());
        Set<Abon> forRemove = new HashSet<>();

        for (Student student: students) {
            List<Abon> abons = abonRepository.findByStudentsAndActiveIsTrueOrderByFinishDate(student);

            if (ClassType.STRETCHING == clazz.getClassType()) {
                getStretchingAbon(abons).ifPresent(forRemove::add);
            } else {
                getPoleDanceAbon(abons).ifPresent(forRemove::add);
            }

        }

        persistentAbons.removeAll(forRemove);
        return persistentAbons;
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

    private Optional<Abon> getAbonOfRightType(List<Abon> abons, ClassType classType) {
        if (ClassType.STRETCHING == classType) {
            Optional<Abon> stretchingAbon = getStretchingAbon(abons);

            if (stretchingAbon.isPresent()) {
                return stretchingAbon;
            }
        }

        return getPoleDanceAbon(abons);

    }

    private Optional<Abon> getPoleDanceAbon(List<Abon> abons) {
        return abons.stream()
                .filter(abon -> abon.getAbonType() == AbonType.PD)
                .findFirst();
    }

    private Optional<Abon> getStretchingAbon(List<Abon> abons) {
        return abons.stream()
                        .filter(abon -> abon.getAbonType() == AbonType.ST)
                        .findFirst();
    }
}
