package com.mihadev.zebra;

import com.mihadev.zebra.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public CommandLineRunner demo(ScheduleService scheduleService) {
        return args -> {
            System.out.println("Started");
            scheduleService.getScheduleForDay("+380966704267").forEach(schedule -> {
                System.out.println(schedule.getGym() + " " + schedule.getScheduleDays());
            });
            System.out.println("finished");
        };
    }

}
