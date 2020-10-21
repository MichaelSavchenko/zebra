package com.mihadev.zebra;

import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.repository.AbonClazzRepository;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.service.UserService;
import com.mihadev.zebra.startscripts.ScheduleScript;
import com.mihadev.zebra.startscripts.SetupAbonClasses;
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
    public CommandLineRunner demo(SetupAbonClasses setupAbonClasses, UserService userService, AbonClazzRepository abonClazzRepository, ScheduleScript scheduleScript, ClassRepository classRepository) {
        return args -> {
            System.out.println("Started");
            List<Clazz> byDateTimeIsAfterOrderByDateTimeDesc = classRepository.findByDateTimeIsAfterOrderByDateTimeDesc(LocalDateTime.of(2020, 9, 30, 23, 59));
            byDateTimeIsAfterOrderByDateTimeDesc.forEach(clazz -> clazz.setCostPerStudent(13));
            classRepository.saveAll(byDateTimeIsAfterOrderByDateTimeDesc);
            System.out.println("finished");
        };
    }

}
