package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.repository.CoachRepository;
import com.mihadev.zebra.repository.PriceRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toSet;
import static java.util.Objects.isNull;

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


    public Clazz saveClass(ClassDto classDto) {
        Clazz clazz = fromDto(classDto);
        classRepository.save(clazz);
        return clazz;
    }

    private Clazz fromDto(ClassDto classDto) {
        Coach coach = coachRepository.findById(classDto.getCoachId()).orElseThrow(RuntimeException::new);

        Clazz clazz = isNull(classDto.getId()) ? new Clazz()
                : classRepository.findById(classDto.getId()).orElseThrow(RuntimeException::new);
        clazz.setCoach(coach);
        clazz.setClassType(classDto.getClassType());
        int costPerStudent = priceRepository.findByClassType(classDto.getClassType()).getCostPerStudent();
        clazz.setCostPerStudent(costPerStudent);
        clazz.setDate(classDto.getDate());

        return clazz;
    }

    //todo test
    public Clazz addStudents(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        Set<Student> students = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().addAll(students);
        List<Abon> abons = abonService.checkAbons(students, clazz.getClassType());
        clazz.getAbons().addAll(abons);
        classRepository.save(clazz);
        return clazz;
    }

    //todo test
    public Clazz removeUsers(int classId, List<Integer> studentIds) {
        Clazz clazz = classRepository.findById(classId).orElseThrow(RuntimeException::new);
        Set<Student> toDelete = toSet(studentRepository.findAllById(studentIds));
        clazz.getStudents().removeAll(toDelete);
        abonService.unCheckAbons(toDelete, clazz);
        classRepository.save(clazz);
        return clazz;
    }

    public List<Clazz> getAll() {
        return toList(classRepository.findAll());
    }

    public Clazz getClass(int classId) {
        return classRepository.findById(classId).orElseThrow(RuntimeException::new);

    }
}
