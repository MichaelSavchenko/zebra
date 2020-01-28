package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    private List<Student> toList(Iterable<Student> all) {
        return StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
    }
}
