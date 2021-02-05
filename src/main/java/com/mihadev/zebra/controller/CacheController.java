package com.mihadev.zebra.controller;

import com.mihadev.zebra.service.StudentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
@CrossOrigin
public class CacheController {

    private final StudentService studentService;

    public CacheController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public void clearStudentsCache() {
        //studentService.refreshStudentsCache();
    }
}
