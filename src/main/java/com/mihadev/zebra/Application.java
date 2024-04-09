package com.mihadev.zebra;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.*;
import com.mihadev.zebra.service.ScheduleDayService;
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
            SetupAbonClasses setupAbonClasses,
            UserService userService,
            AbonClazzRepository abonClazzRepository,
            ScheduleScript scheduleScript,
            ClassRepository classRepository,
            StudentRepository studentRepository,
            PriceRepository priceRepository,
            CoachRepository coachRepository,
            UserRepository userRepository,
            ScheduleDayService scheduleDayService) {
        return args -> {
            System.out.println("Started");


            User user = new User();
            user.setFirstName("Ольга");
            user.setLastName("Макаренко");
            user.setUserName("+380632067232");
            user.setPassword("zebra663");
            userService.register(user, "ROLE_COACH");
            System.out.println(user.getLastName() + " registered");

            System.out.println("finished");
        };
    }

}
