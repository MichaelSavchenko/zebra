package com.mihadev.zebra;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.*;
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
import java.util.*;

import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static org.hibernate.internal.util.collections.ArrayHelper.toList;

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

            Student student = new Student();
            student.setFirstName("firstName");
            student.setLastName("lastName");

            Student student1 = new Student();
            student1.setFirstName("firstName1");
            student1.setLastName("lastName1");

            List<Student> students = Arrays.asList(student, student1);
            studentRepository.saveAll(students);

            Coach coach = new Coach();
            coach.setFirstName("first name");
            coach.setLastName("last name");
            coachRepository.save(coach);


            Clazz clazz = new Clazz();
            clazz.setDate(LocalDate.now());
            Clazz clazz1 = new Clazz();
            clazz1.setDate(LocalDate.now().minusDays(1));
            Set<Clazz> classes = new HashSet<>(Arrays.asList(clazz, clazz1));

            classRepository.saveAll(classes);

            for (Clazz cl: classes) {
                cl.setStudents(new HashSet<>(students));
                cl.setCoach(coach);
            }

            classRepository.saveAll(classes);

            Price price = new Price();
            price.setClassType(ClassType.ACROBATICS);
            price.setCostPerStudent(17);
            priceRepository.save(price);

            Price byClassType = priceRepository.findByClassType(ClassType.ACROBATICS);


            ClassDto classDto = new ClassDto();
            classDto.setCoachId(coach.getId());
            classDto.setClassType(ClassType.ACROBATICS);
            classDto.setDate(LocalDate.now());

            int id = classService.saveClass(classDto);
            System.out.println(id);


            classService.addUsers(6, Arrays.asList(1,2));

            AbonDto abonDto = new AbonDto();
            abonDto.setAbonType(AbonType.PD);
            abonDto.setActive(true);
            abonDto.setPaid(true);
            abonDto.setStartDate(LocalDate.now());
            abonDto.setFinishDate(LocalDate.now().plusMonths(1));
            abonDto.setNumberOfClasses(16);
            abonDto.setStudents(new HashSet<>(Arrays.asList(1,2)));
            Abon abon = abonService.createAbon(abonDto);

            List<Abon> abon1 = abonRepository.findByStudentsAndActiveIsTrue(student);

            System.out.println(abon1.get(0).getId() + ":" + abon1.get(0).getAbonType());

            /*Clazz clazz2 = classRepository.findById(4).orElseThrow(RuntimeException::new);
            clazz2.getStudents().clear();
            classRepository.save(clazz2);
            System.out.println("fasdfsd");*/


           /* List<ClassStudent> classStudents = new ArrayList<>();
            for (Clazz zz : savedClasses) {
                for (Student st : savedStudents) {
                    classStudents.add(new ClassStudent(zz, st));
                }
            }

            Iterable<ClassStudent> savedClassStudents = classStudentRepository.saveAll(classStudents);

            List<Clazz> afterUpdate = new ArrayList<>();
            List<Student> stAfterUpdate = new ArrayList<>();

            for (ClassStudent classStudent : savedClassStudents) {
                Clazz clazz2 = classStudent.getClazz();
                clazz2.getStudents().add(classStudent);
                afterUpdate.add(clazz2);

                Student student2 = classStudent.getStudent();
                student2.getClasses().add(classStudent);
                stAfterUpdate.add(student2);
            }

            classRepository.saveAll(afterUpdate);
            studentRepository.saveAll(stAfterUpdate);

            classStudentRepository.deleteByClazzAndStudent(
                    toList(classRepository.findAll()).get(0),
                    toList(studentRepository.findAll()).get(0)
            );

            Iterable<Clazz> allClasses = classRepository.findAll();
            Iterable<Student> allStudents = studentRepository.findAll();

            for (Clazz cla: allClasses) {
                int size = cla.getStudents().size();
                System.out.println(size);
            }

            for (Student st: allStudents) {
                int size = st.getClasses().size();
                System.out.println(size);
            }*/
        };

    }
}
