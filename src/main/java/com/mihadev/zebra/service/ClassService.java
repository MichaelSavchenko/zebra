package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.ClassStudent;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.repository.ClassStudentRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mihadev.zebra.utils.CollectionUtils.*;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final ClassStudentRepository classStudentRepository;

    public ClassService(ClassRepository classRepository, StudentRepository studentRepository, ClassStudentRepository classStudentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.classStudentRepository = classStudentRepository;
    }


    public Clazz saveClass(Clazz clazz) {
        return classRepository.save(clazz);
    }

    public void addStudents(Long classId, List<Long> studentIds) {
        Iterable<Student> students = studentRepository.findAllById(studentIds);
        Map<Long, Student> studentsMap = toStream(students)
                .collect(Collectors.toMap(Student::getId, Function.identity()));
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);


        List<ClassStudent> classStudents = new ArrayList<>();
        for (Long studentId: studentIds) {
            classStudents.add(new ClassStudent(clazz, studentsMap.get(studentId)));
        }

        Iterable<ClassStudent> savedClassStudents = classStudentRepository.saveAll(classStudents);
    }

    public List<Clazz> getUserClasses(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(RuntimeException::new);

        return classStudentRepository.findAllByStudent(student).stream()
                .map(ClassStudent::getClazz)
                .collect(Collectors.toList());
    }

    public List<Clazz> getAll() {
        return toList(classRepository.findAll());
    }

    public Clazz getClass(Long classId) {
        return classRepository.findById(classId).orElseThrow(RuntimeException::new);
    }
}
