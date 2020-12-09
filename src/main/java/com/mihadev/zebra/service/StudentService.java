package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.SFDto;
import com.mihadev.zebra.dto.StudentDto;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.StudentRepository;
import com.mihadev.zebra.security.JWTUser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.mihadev.zebra.service.AbonService.setActiveAbons;
import static com.mihadev.zebra.utils.CollectionUtils.toList;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private Map<Integer, Student> cache;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.cache = new HashMap<>();
    }

    public List<Student> getAll() {
       /* if (cache.isEmpty()) {
            Iterable<Student> all = studentRepository.findAll();
            cache = toList(all).stream().collect(Collectors.toMap(Student::getId, student -> student));
        }

        return new ArrayList<>(cache.values());*/

        return toList(studentRepository.findAll());
    }


    public Student get(int studentId) {
        return studentRepository.findById(studentId).orElseThrow(RuntimeException::new);
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
        cache.clear();
        return student;
    }

    public Student update(StudentDto dto) {
        Student student = toStudent(dto);
        studentRepository.save(student);
        cache.clear();
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
        cache.clear();
    }
}
