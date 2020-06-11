package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.*;
import com.mihadev.zebra.repository.AbonClazzRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

import static com.mihadev.zebra.entity.AbonType.PD;
import static com.mihadev.zebra.entity.AbonType.ST;
import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;

@Component
public class SetupAbonClasses {
    private final StudentRepository studentRepository;
    private final AbonClazzRepository abonClazzRepository;

    public SetupAbonClasses(StudentRepository studentRepository, AbonClazzRepository abonClazzRepository) {
        this.studentRepository = studentRepository;
        this.abonClazzRepository = abonClazzRepository;
    }

    public void execute() {

        abonClazzRepository.deleteAll();

        System.out.println("DELETED");


        List<Student> students = toList(studentRepository.findAll());

        for (Student s : students) {
            Set<Clazz> classes = s.getClasses();

            Set<Clazz> nonStretchClasses = new HashSet<>(classes);

            Map<AbonType, List<Abon>> abons = s.getAbons().stream()
                    .collect(groupingBy(Abon::getAbonType));

            List<AbonClazz> strAbonClazzes = new ArrayList<>();

            if (nonNull(abons.get(ST))) {
                for (Abon abonSt : abons.get(ST)) {

                    for (Clazz cl : classes) {

                        if (cl.getClassType() == ClassType.STRETCHING) {

                            if (nonNull(abonSt.getFinishDate())) {
                                if (cl.getDateTime().isBefore(abonSt.getFinishDate().plusDays(1).atStartOfDay()) &&
                                cl.getDateTime().isAfter(abonSt.getStartDate().atStartOfDay())) {
                                    strAbonClazzes.add(new AbonClazz(abonSt, cl));
                                    nonStretchClasses.remove(cl);
                                }
                            } else {
                                if (cl.getDateTime().isAfter(abonSt.getStartDate().atStartOfDay())) {
                                    strAbonClazzes.add(new AbonClazz(abonSt, cl));
                                    nonStretchClasses.remove(cl);
                                }
                            }
                        }
                    }

                }

                abonClazzRepository.saveAll(strAbonClazzes).forEach(abonClazz -> {
                    System.out.println(abonClazz.getClazz().getDateTime() + ":" + abonClazz.getClazz().getClassType()
                            + " -> " + abonClazz.getAbon().getAbonType() + " --- " + abonClazz.getAbon().getStartDate() + " : " + abonClazz.getAbon().getFinishDate());
                });
            }

            List<AbonClazz> nonStrAbonClazzes = new ArrayList<>();

            if (nonNull(abons.get(PD))) {

                for (Abon pdAbon : abons.get(PD)) {
                    for (Clazz nonStClazz : nonStretchClasses) {


                        if (nonNull(pdAbon.getFinishDate())) {
                            if (nonStClazz.getDateTime().isBefore(pdAbon.getFinishDate().plusDays(1).atStartOfDay()) &&
                            nonStClazz.getDateTime().isAfter(pdAbon.getStartDate().atStartOfDay())) {
                                nonStrAbonClazzes.add(new AbonClazz(pdAbon, nonStClazz));
                            }
                        } else {
                            LocalDateTime startAbon = isNull(pdAbon.getStartDate()) ?
                                    LocalDateTime.of(2020, 1, 1, 0, 1)
                                    : pdAbon.getStartDate().atStartOfDay();
                            if (nonStClazz.getDateTime().isAfter(startAbon)) {
                                nonStrAbonClazzes.add(new AbonClazz(pdAbon, nonStClazz));
                            }
                        }
                    }
                }

                abonClazzRepository.saveAll(nonStrAbonClazzes).forEach(abonClazz -> {
                    System.out.println(abonClazz.getClazz().getDateTime() + ":" + abonClazz.getClazz().getClassType()
                            + " -> " + abonClazz.getAbon().getAbonType() + " --- " + abonClazz.getAbon().getStartDate() + " : " + abonClazz.getAbon().getFinishDate());
                });
            }
        }
    }
}
