package com.mihadev.zebra.controller;

import com.mihadev.zebra.service.ClassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

  /*  @GetMapping
    public List<Clazz> getAll() {
        return classService.getAll();
    }

    @PutMapping("/{classId}")
    public void addStudents(@PathVariable Long classId,@RequestBody StudentsDto dto) {
        classService.addStudents(classId, dto.getStudentIds());
    }

    @GetMapping("/{classId}")
    public Clazz getClass(@PathVariable Long classId) {
        return classService.getClass(classId);*/

}
