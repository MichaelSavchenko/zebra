package com.mihadev.zebra;

import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.AbonClazzRepository;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.repository.PriceRepository;
import com.mihadev.zebra.repository.StudentRepository;
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
            PriceRepository priceRepository) {
        return args -> {
            System.out.println("Started");


            User user = new User();
            user.setFirstName("Лєра");
            user.setLastName("Кабан");
            user.setUserName("+380996171174");
            user.setPassword("zebra367");
            userService.register(user, "ROLE_COACH");
            System.out.println(user.getLastName() + " registered");

            User user1 = new User();
            user1.setFirstName("Маша");
            user1.setLastName("Зубрій");
            user1.setUserName("+380635671774");
            user1.setPassword("zebra763");
            userService.register(user1, "ROLE_COACH");
            System.out.println(user1.getLastName() + " registered");

            User user2 = new User();
            user2.setFirstName("Альона");
            user2.setLastName("Руденок");
            user2.setUserName("+380973380658");
            user2.setPassword("zebra991");
            userService.register(user2, "ROLE_COACH");
            System.out.println(user2.getLastName() + " registered");

            System.out.println("finished");
        };
    }

}
