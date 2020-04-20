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
import java.util.*;

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
            CoachRepository coachRepository,
            AbonClazzRepository abonClazzRepository
    ) {
        return args -> {
            System.out.println("Started");

            Set<Student> students = toSet(studentRepository.findAll());

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
                                    if (cl.getDateTime().isBefore(abonSt.getFinishDate().plusDays(1).atStartOfDay())) {
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
                        for (Clazz nonSt : nonStretchClasses) {


                            if (nonNull(pdAbon.getFinishDate())) {
                                if (nonSt.getDateTime().isBefore(pdAbon.getFinishDate().plusDays(1).atStartOfDay())) {
                                    nonStrAbonClazzes.add(new AbonClazz(pdAbon, nonSt));
                                }
                            } else {
                                LocalDateTime startAbon = isNull(pdAbon.getStartDate()) ?
                                        LocalDateTime.of(2020, 1, 1, 0, 1)
                                        : pdAbon.getStartDate().atStartOfDay();
                                if (nonSt.getDateTime().isAfter(startAbon)) {
                                    nonStrAbonClazzes.add(new AbonClazz(pdAbon, nonSt));
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

            System.out.println("finished");
        };
    }

}
