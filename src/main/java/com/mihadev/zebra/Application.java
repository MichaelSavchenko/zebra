package com.mihadev.zebra;

import com.mihadev.zebra.entity.*;
import com.mihadev.zebra.repository.*;
import com.mihadev.zebra.service.UserService;
import com.mihadev.zebra.startscripts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mihadev.zebra.entity.AbonType.PD;
import static com.mihadev.zebra.entity.AbonType.ST;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;

@SpringBootApplication
public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${userPassword}")
    private String userPassword;


    @Value("${adminPassword}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(
            CoachScript coachScript,
            ScheduleScript scheduleScript,
            ClazzScript clazzScript,
            PriceScript priceScript,
            RolesScript rolesScript,
            UserService userService,
            AbonRepository abonRepository,
            ClassRepository classRepository,
            StudentRepository studentRepository,
            UserRepository userRepository,
            CoachRepository coachRepository
    ) {
        return args -> {
            System.out.println("Started");

            Set<Student> students = toSet(studentRepository.findAll());

            for (Student s : students) {
                Set<Clazz> classes = s.getClasses();

                Set<Clazz> nonStretchClasses = new HashSet<>(classes);

                Map<AbonType, List<Abon>> abons = s.getAbons().stream()
                        .collect(groupingBy(Abon::getAbonType));


                if (nonNull(abons.get(ST))) {
                    for (Abon abonSt : abons.get(ST)) {

                        for (Clazz cl : classes) {

                            if (cl.getClassType() == ClassType.STRETCHING) {

                                if (isNull(abonSt.getClazzes())) {
                                    abonSt.setClazzes(new HashSet<>());
                                }

                                if (nonNull(abonSt.getFinishDate())) {
                                    if (cl.getDateTime().isBefore(abonSt.getFinishDate().plusDays(1).atStartOfDay())) {
                                        abonSt.getClazzes().add(cl);
                                        nonStretchClasses.remove(cl);
                                    }
                                } else {
                                    if (cl.getDateTime().isAfter(abonSt.getStartDate().atStartOfDay())) {
                                        abonSt.getClazzes().add(cl);
                                        nonStretchClasses.remove(cl);
                                    }
                                }
                            }
                        }

                    }

                    saveAbons(abonRepository, s, abons.get(ST));
                }


                if (nonNull(abons.get(PD))) {

                    for (Abon pdAbon : abons.get(PD)) {
                        for (Clazz nonSt : nonStretchClasses) {

                            if (nonSt.getClassType() != ClassType.STRETCHING) {

                                if (isNull(pdAbon.getClazzes())) {
                                    pdAbon.setClazzes(new HashSet<>());
                                }

                                if (nonNull(pdAbon.getFinishDate())) {
                                    if (nonSt.getDateTime().isBefore(pdAbon.getFinishDate().plusDays(1).atStartOfDay())) {
                                        pdAbon.getClazzes().add(nonSt);
                                    }
                                } else {
                                    LocalDateTime startAbon = isNull(pdAbon.getStartDate()) ?
                                            LocalDateTime.of(2020, 1, 1, 0, 1)
                                            : pdAbon.getStartDate().atStartOfDay();
                                    if (nonSt.getDateTime().isAfter(startAbon)) {
                                        pdAbon.getClazzes().add(nonSt);
                                    }
                                }

                            }
                        }
                    }

                    saveAbons(abonRepository, s, abons.get(PD));
                }


            }

            System.out.println("finished");
        };
    }

    private void saveAbons(AbonRepository abonRepository, Student s, List<Abon> abonList) {
        abonList.forEach(abon ->
                abon.getClazzes().forEach(clazz -> {
                    System.out.println(s.getLastName() + "---" +
                            abon.getStartDate() + " : " + abon.getFinishDate() + " : " + abon.getAbonType() + " -> " +
                            clazz.getDateTime() + " - " + clazz.getClassType());
                }));

        abonRepository.saveAll(abonList);
    }
}
