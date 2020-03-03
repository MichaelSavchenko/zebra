package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.SFDto;
import com.mihadev.zebra.dto.StudentDto;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{studentId}")
    public Student get(@PathVariable int studentId) {
        return studentService.get(studentId);
    }

    @PostMapping
    public Student createStudent(@RequestBody StudentDto dto) {
        return studentService.create(dto);
    }

    @PutMapping
    public Student updateStudent(@RequestBody StudentDto dto) {
        return studentService.update(dto);
    }

    @PostMapping("/all")
    public void saveAll(@RequestBody List<SFDto> dtos) {
        studentService.saveAll(dtos);
    }
}
