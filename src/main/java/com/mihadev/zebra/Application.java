package com.mihadev.zebra;

import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.service.UserService;
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
    public CommandLineRunner demo(UserService userService) {
        return args -> {
            System.out.println("Started");
            User user = new User();
            user.setUserName("+380934830409");
            user.setPassword("zebra123");
            user.setFirstName("Іра");
            user.setLastName("Калашник");
            userService.register(user, "ROLE_COACH");
            System.out.println("finished");
        }; }

}
