package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AbonDto;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.repository.AbonRepository;
import com.mihadev.zebra.repository.StudentRepository;
import org.springframework.stereotype.Service;

import static com.mihadev.zebra.utils.CollectionUtils.toSet;

@Service
public class AbonService {

    private final AbonRepository abonRepository;
    private final StudentRepository studentRepository;

    public AbonService(AbonRepository abonRepository, StudentRepository studentRepository) {
        this.abonRepository = abonRepository;
        this.studentRepository = studentRepository;
    }

    public Abon createAbon(AbonDto abonDto) {
        Iterable<Student> students = studentRepository.findAllById(abonDto.getStudents());

        Abon abon = new Abon();
        abon.setStudents(toSet(students));
        abon.setStartDate(abonDto.getStartDate());
        abon.setFinishDate(abonDto.getFinishDate());
        abon.setActive(abonDto.isActive());
        abon.setPaid(abonDto.isPaid());
        abon.setPrice(abonDto.getPrice());
        abon.setNumberOfClasses(abonDto.getNumberOfClasses());
        abon.setAbonType(abonDto.getAbonType());

        abonRepository.save(abon);
        return abon;
    }
}
