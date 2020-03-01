package com.mihadev.zebra;

import com.mihadev.zebra.entity.*;
import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.*;
import com.mihadev.zebra.service.AbonService;
import com.mihadev.zebra.service.ClassService;
import com.mihadev.zebra.startscripts.CoachScript;
import com.mihadev.zebra.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static java.util.Collections.*;

@SpringBootApplication
public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(CoachScript coachScript) {
        return args -> {
            coachScript.insertCoaches();

/*            coachRepository.deleteAll();
            scheduleClassRepository.deleteAll();
            scheduleDayRepository.deleteAll();
            scheduleRepository.deleteAll();
            gymRepository.deleteAll();

            Schedule schedule = new Schedule();
            scheduleRepository.save(schedule);

            Gym gym = new Gym();
            gym.setName("Митниця");
            gym.setSchedule(schedule);
            gymRepository.save(gym);


            List<ScheduleDay> days = new ArrayList<>();
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                ScheduleDay day = new ScheduleDay();
                day.setDayOfWeek(dayOfWeek);
                day.setSchedule(schedule);
                days.add(day);
            }

            scheduleDayRepository.saveAll(days);

            Coach coach = new Coach();
            coach.setFirstName("coach name");
            coach.setLastName("coach last name");
            coachRepository.save(coach);


            List<ScheduleClass> classes = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                ScheduleClass scheduleClass = new ScheduleClass();
                scheduleClass.setStartTime(LocalTime.of(10, 0));
                scheduleClass.setClassType(ClassType.EXOT);
                scheduleClass.setCoach(coach);
                scheduleClass.setScheduleDay(days.get(i));
                classes.add(scheduleClass);
            }

            scheduleClassRepository.saveAll(classes);*/



           /* studentRepository.deleteAll();
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
            classDto.setTime(LocalTime.of(10, 0));

            classService.saveClass(classDto);*/
        };

    }
}
