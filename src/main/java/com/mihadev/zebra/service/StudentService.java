package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.SFDto;
import com.mihadev.zebra.dto.StudentDto;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.StudentRepository;
import com.mihadev.zebra.security.JWTUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.mihadev.zebra.service.AbonService.setActiveAbons;
import static com.mihadev.zebra.utils.CollectionUtils.toList;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll() {
        Iterable<Student> all = studentRepository.findAll();
        all.forEach(s -> setActiveAbons(s.getAbons()));

        return toList(all);
    }

    public Student get(int studentId) {
        long startSql = System.currentTimeMillis();

        Student student = studentRepository.findById(studentId).orElseThrow(RuntimeException::new);

        long finishSql = System.currentTimeMillis();

        System.out.println("SQL -------------!!!!!!!! " + (finishSql - startSql) + "!!!!!!!!!!!----------------");

        long start = System.currentTimeMillis();
        setActiveAbons(student.getAbons());

        long finish = System.currentTimeMillis();
        System.out.println("set active abons -------------!!!!!!!! " + (finish - start) + "!!!!!!!!!!!----------------");

        return student;
    }


    public Student getByPhone(String phone) {
        Student empty = new Student();
        empty.setAbons(new HashSet<>());
        Student student = studentRepository.findByPhoneNumber(phone).orElse(empty);

        setActiveAbons(student.getAbons());

        return student;
    }

    public Student create(StudentDto dto) {
        Student student = toStudent(dto);
        studentRepository.save(student);
        return student;
    }

    public Student update(StudentDto dto) {
        Student student = toStudent(dto);
        studentRepository.save(student);
        return student;
    }

    private Student toStudent(StudentDto dto) {
        Student student = new Student();
        if (Objects.nonNull(dto.getId())) {
            student.setId(dto.getId());
        }
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDescription(dto.getDescription());
        student.setActive(dto.isActive());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setKid(dto.isKid());

        if (Objects.isNull(dto.getAdmin())) {
            JWTUser currentUser = AdminEntityService.getCurrentUser();
            student.setAdmin(new User(currentUser.getId()));
        } else {
            student.setAdmin(new User(dto.getAdmin().getId()));
        }

        return student;
    }

    public void saveAll(List<SFDto> dtos) {
        List<Student> students = new ArrayList<>();
        for (SFDto dto : dtos) {

            if (dto.getName() != null) {
                Student student = new Student();
                String[] fullName = dto.getName().split(" ");

                if (fullName.length == 2) {
                    student.setFirstName(fullName[1]);
                }

                student.setLastName(fullName[0]);

                student.setKid(dto.isKid());
                student.setActive(true);

                students.add(student);
            }

        }

        studentRepository.saveAll(students);
    }
}
