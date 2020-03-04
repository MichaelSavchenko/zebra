package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.dto.ClassDto;
import com.mihadev.zebra.repository.ClassRepository;
import com.mihadev.zebra.service.ClassService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.mihadev.zebra.entity.ClassType.ACROBATICS;

@Service
public class ClazzScript {

    private final ClassService classService;
    private final ClassRepository classRepository;

    public ClazzScript(ClassService classService, ClassRepository classRepository) {
        this.classService = classService;
        this.classRepository = classRepository;
    }

    public void setup() {
        classRepository.deleteAll();

        ClassDto classDto = new ClassDto();
        classDto.setCoachId(1);
        classDto.setClassType(ACROBATICS);
        classDto.setDateTime(LocalDateTime.of(2020, 3, 3, 10, 0));

        classService.saveClass(classDto);
    }
}
