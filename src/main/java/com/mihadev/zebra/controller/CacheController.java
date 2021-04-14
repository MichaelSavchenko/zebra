package com.mihadev.zebra.controller;

import com.mihadev.zebra.service.AbonService;
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
    private final AbonService abonService;

    public CacheController(StudentService studentService, AbonService abonService) {
        this.studentService = studentService;
        this.abonService = abonService;
    }

    @GetMapping("/students")
    public void clearStudentsCache() {
        studentService.refreshStudentsCache();
        abonService.refreshAbonCache();
    }
}
