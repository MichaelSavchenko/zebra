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
            AbonClazzRepository abonClazzRepository,
            SetupAbonClasses setupAbonClasses
    ) {
        return args -> {
            System.out.println("Started");

            setupAbonClasses.execute();

            System.out.println("finished");
        };
    }

}
