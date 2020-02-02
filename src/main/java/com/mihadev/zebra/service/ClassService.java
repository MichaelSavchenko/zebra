package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final CoachRepository coachRepository;
    private final AbonService abonService;
    private final PriceRepository priceRepository;

    public ClassService(
            ClassRepository classRepository,
            StudentRepository studentRepository,
            CoachRepository coachRepository,
            AbonService abonService,
            PriceRepository priceRepository
    ) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.coachRepository = coachRepository;
        this.abonService = abonService;
        this.priceRepository = priceRepository;
    }


    public int saveClass(ClassDto classDto) {
        Coach coach = coachRepository.findById(classDto.getCoachId()).orElseThrow(RuntimeException::new);

        Clazz clazz = new Clazz();
        clazz.setCoach(coach);
        clazz.setClassType(classDto.getClassType());
        int costPerStudent = priceRepository.findByClassType(classDto.getClassType()).getCostPerStudent();
        clazz.setCostPerStudent(costPerStudent);
        clazz.setDate(classDto.getDate());

        classRepository.save(clazz);
        return clazz.getId();
    }

    public void addUsers(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        Set<Student> students = toSet(studentRepository.findAllById(studentIds));
        clazz.setStudents(students);
        List<Abon> abons = abonService.checkAbons(clazz);
        clazz.setAbons(abons);
        classRepository.save(clazz);
    }

    public void removeUsers(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        Set<Student> toDelete = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().removeAll(toDelete);
        abonService.unCheckAbons(toDelete, clazz);
        classRepository.save(clazz);
    }

    public List<Clazz> getAll() {
        return toList(classRepository.findAll());
    }

    public Clazz getClass(int classId) {
        return classRepository.findById(classId).orElseThrow(RuntimeException::new);

    }

}
