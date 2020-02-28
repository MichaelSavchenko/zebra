package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.dto.StudentsDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("classes")
@CrossOrigin
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public List<Clazz> getAll() {
        return classService.getAll();
    }

    @GetMapping("/{classId}")
    public Clazz getClass(@PathVariable int classId) {
        return classService.getClass(classId);
    }

    @PostMapping
    public Clazz createClass(@RequestBody ClassDto dto) {
        return classService.saveClass(dto);
    }

    @PutMapping
    public Clazz updateClass(@RequestBody ClassDto dto) {
        return classService.saveClass(dto);
    }

    @PostMapping("/{classId}/students")
    public Clazz addStudents(@PathVariable int classId, @RequestBody StudentsDto studentsDto) {
        return classService.addStudents(classId, studentsDto.getStudentIds());
    }

    @DeleteMapping("/{classId}/students")
    public Clazz removeStudents(@PathVariable int classId, @RequestBody StudentsDto studentsDto) {
        return classService.removeUsers(classId, studentsDto.getStudentIds());
    }
}
