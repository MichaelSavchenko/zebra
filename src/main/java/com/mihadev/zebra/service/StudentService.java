package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.StudentDto;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.mihadev.zebra.utils.CollectionUtils.toList;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll() {
        Iterable<Student> all = studentRepository.findAll();
        return toList(all);
    }

    public Student get(int studentId) {
        return studentRepository.findById(studentId).orElseThrow(RuntimeException::new);
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
        return student;
    }

}
