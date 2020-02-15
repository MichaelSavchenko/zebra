package com.mihadev.zebra;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Price;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.*;
import com.mihadev.zebra.service.AbonService;
import com.mihadev.zebra.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mihadev.zebra.entity.ClassType.ACROBATICS;

@SpringBootApplication
public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(
            ClassRepository classRepository,
            StudentRepository studentRepository,
            CoachRepository coachRepository,
            PriceRepository priceRepository,
            ClassService classService,
            AbonService abonService,
            AbonRepository abonRepository) {
        return args -> {
            studentRepository.deleteAll();
            classRepository.deleteAll();
            abonRepository.deleteAll();

            Price price = new Price();
            price.setClassType(ACROBATICS);
            price.setCostPerStudent(17);
            priceRepository.save(price);

            Student student = new Student();
            student.setFirstName("firstName");
            student.setLastName("lastName");

            Student student1 = new Student();
            student1.setFirstName("firstName1");
            student1.setLastName("lastName1");

            Student student2 = new Student();
            student2.setFirstName("firstName2");
            student2.setLastName("lastName2");

            List<Student> students = new ArrayList<>(Arrays.asList(student, student1, student2));
            studentRepository.saveAll(students);

            Coach coach = new Coach();
            coach.setFirstName("first name");
            coach.setLastName("last name");
            coachRepository.save(coach);


            ClassDto classDto = new ClassDto();
            classDto.setCoachId(coach.getId());
            classDto.setClassType(ACROBATICS);
            classDto.setLocalDate(LocalDate.now());

            classService.saveClass(classDto);
        };

    }
}
