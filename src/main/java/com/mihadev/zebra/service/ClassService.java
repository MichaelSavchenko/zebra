package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.repository.AbonRepository;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.repository.CoachRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mihadev.zebra.utils.CollectionUtils.toList;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final CoachRepository coachRepository;
    private final AbonRepository abonRepository;

    public ClassService(
            ClassRepository classRepository,
            StudentRepository studentRepository,
            CoachRepository coachRepository,
            AbonRepository abonRepository
    ) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.coachRepository = coachRepository;
        this.abonRepository = abonRepository;
    }


    public int saveClass(ClassDto classDto) {
        Coach coach = coachRepository.findById(classDto.getCoachId()).orElseThrow(RuntimeException::new);

        Clazz clazz = new Clazz();
        clazz.setCoach(coach);
        clazz.setClassType(classDto.getClassType());

        return 1;
    }

    public List<Clazz> getAll() {
        return toList(classRepository.findAll());
    }

    public Clazz getClass(int classId) {
        return classRepository.findById(classId).orElseThrow(RuntimeException::new);

    }

}
