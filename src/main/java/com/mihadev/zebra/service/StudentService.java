package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
