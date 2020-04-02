package com.mihadev.zebra;

import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.AbonRepository;
import com.mihadev.zebra.repository.ClassRepository;
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
import java.util.List;

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
            ClassRepository classRepository
    ) {
        return args -> {

            List<Clazz> byDateTimeBetween = classRepository.findByDateTimeLessThan(LocalDateTime.of(2020, 2, 29, 23, 59));
            System.out.println("number: "  + byDateTimeBetween.size());
            for (Clazz cl : byDateTimeBetween) {
                if (cl.getStudents().isEmpty()) {
                    classRepository.delete(cl);
                    System.out.println("deleted");
                    Thread.sleep(300);
                }
            }

            /* coachScript.insertCoaches();
            scheduleScript.setupSchedule();
            priceScript.setup();
            rolesScript.setup();

            User user = new User();
            user.setUserName("zebra");
            user.setPassword(userPassword);
            userService.register(user, "ROLE_USER");

            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword(adminPassword);
            userService.register(admin, "ROLE_ADMIN");*/
        };
    }
}
