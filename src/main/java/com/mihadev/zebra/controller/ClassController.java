package com.mihadev.zebra.controller;

import com.mihadev.zebra.ClassDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public List<Clazz> getAll() {
        return classService.getAll();
    }

    @PutMapping("/{classId}")
    public void addStudents(@PathVariable Long classId,@RequestBody ClassDto dto) {
        classService.addStudents(classId, dto.getStudentIds());
    }

    @GetMapping("/{classId}")
    public Clazz getClass(@PathVariable Long classId) {
        return classService.getClass(classId);
    }
}
