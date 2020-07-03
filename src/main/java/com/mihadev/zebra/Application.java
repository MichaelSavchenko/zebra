package com.mihadev.zebra;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.ClassRepository;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public CommandLineRunner demo(SetupAbonClasses setupAbonClasses, UserService userService) {
        return args -> {
            System.out.println("Started");
            User user = new User();
            user.setFirstName("Діана");
            user.setLastName("Нерознак");
            user.setUserName("+380636025519");
            user.setPassword("zebra246");
            userService.register(user, "ROLE_COACH");
            System.out.println(user.getLastName() + " registered");

            User user1 = new User();
            user1.setFirstName("Свєта");
            user1.setLastName("Славная");
            user1.setUserName("+380988896465");
            user1.setPassword("zebra951");
            userService.register(user1, "ROLE_COACH");
            System.out.println(user1.getLastName() + " registered");

            User user2 = new User();
            user2.setFirstName("Степура");
            user2.setLastName("Марина");
            user2.setUserName("+380675978281");
            user2.setPassword("zebra967");
            userService.register(user2, "ROLE_COACH");
            System.out.println(user2.getLastName() + " registered");

            User user3 = new User();
            user3.setFirstName("Щербак");
            user3.setLastName("Таня");
            user3.setUserName("+380636050744");
            user3.setPassword("zebra879");
            userService.register(user3, "ROLE_COACH");
            System.out.println(user3.getLastName() + " registered");

            User user4 = new User();
            user4.setFirstName("Куць");
            user4.setLastName("Інна");
            user4.setUserName("+380634903255");
            user4.setPassword("zebra476");
            userService.register(user4, "ROLE_COACH");
            System.out.println(user4.getLastName() + " registered");

            User user5 = new User();
            user5.setFirstName("Козоріз");
            user5.setLastName("Іра");
            user5.setUserName("+380639319472");
            user5.setPassword("zebra167");
            userService.register(user5, "ROLE_COACH");
            System.out.println(user5.getLastName() + " registered");

            User user6 = new User();
            user6.setFirstName("Ященко");
            user6.setLastName("Діана");
            user6.setUserName("+380936532169");
            user6.setPassword("zebra456");
            userService.register(user6, "ROLE_COACH");
            System.out.println(user6.getLastName() + " registered");

            User tema = userService.findByUserName("+380966704267");
            if (tema != null) {
                tema.setPassword("zebra888");
                userService.register(tema, "ROLE_COACH");
            } else {
                User user7 = new User();
                user7.setFirstName("Дементьєв");
                user7.setLastName("Артем");
                user7.setUserName("+380966704267");
                user7.setPassword("zebra888");
                userService.register(user7, "ROLE_COACH");
                System.out.println(user7.getLastName() + " registered");
            }

            System.out.println("finished");
        };
    }

}
