package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.entity.*;
import com.mihadev.zebra.repository.AbonRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
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
        return toList(abonRepository.findAll());
    }

    public Abon get(int id) {
        return abonRepository.findById(id).orElseThrow(RuntimeException::new);
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
        return abon;
    }

    void checkAbons(Set<Student> newStudents, ClassType classType) {
        List<Abon> toUpdate = new ArrayList<>();
        for (Student student : newStudents) {
            List<Abon> abons = abonRepository.findByStudentsAndActiveIsTrueOrderByFinishDate(student);
            Optional<Abon> abonOfRightType = getAbonOfRightType(abons, classType);
            Abon abon = abonOfRightType.orElseGet(createdAbon(student));
            abon.setNumberOfUsedClasses(abon.getNumberOfUsedClasses() + 1);
            toUpdate.add(abon);
        }

        abonRepository.saveAll(toUpdate);
    }

    void unCheckAbons(Set<Student> students, Clazz clazz) {
        List<Abon> forRemove = new ArrayList<>();

        for (Student student : students) {
            List<Abon> abons = abonRepository.findByStudentsAndActiveIsTrueOrderByFinishDate(student);

            if(abons.isEmpty()) {
                abons = abonRepository.findByStudentsOrderByFinishDateDesc(student);
            }

            if (ClassType.STRETCHING == clazz.getClassType()) {
                getStretchingAbon(abons).ifPresent(forRemove::add);
            } else {
                getPoleDanceAbon(abons).ifPresent(forRemove::add);
            }
        }

        for(Abon abon : forRemove) {
            abon.setNumberOfUsedClasses(abon.getNumberOfUsedClasses() - 1);
        }

        abonRepository.saveAll(forRemove);
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
