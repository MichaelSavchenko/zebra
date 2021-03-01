package com.mihadev.zebra.controller;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private StudentService studentService;

    public ClientController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{phone}")
    public Set<Abon> getByClientPhone(@PathVariable String phone) {
        Student student = studentService.getByPhone("+" + phone);
        return student.getAbons();
    }
}
