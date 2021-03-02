package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClientAbonDto;
import com.mihadev.zebra.dto.ClientClassDto;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.AbonClazz;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Student;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@Service
public class ClientService {
    private final StudentService studentService;

    public ClientService(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<ClientAbonDto> getClientAbons(String phone) {
        Student student = studentService.getByPhone("+" + phone);
        Set<Abon> abons = student.getAbons();

        return abons.stream()
                .sorted(getAbonStartDateComparator())
                .limit(2)
                .map(toDto())
                .collect(toList());
    }

    private Comparator<Abon> getAbonStartDateComparator() {
        return (o1, o2) -> {
            if (o2.getStartDate().isAfter(o1.getStartDate())) {
                return 1;
            } else if (o1.getStartDate().isAfter(o2.getStartDate())) {
                return -1;
            }

            return 0;
        };
    }

    private Function<Abon, ClientAbonDto> toDto() {
        return abon -> {
            ClientAbonDto abonDto = new ClientAbonDto();
            abonDto.setId(abon.getId());
            abonDto.setAbonType(abon.getAbonType());
            abonDto.setActive(abon.isActive());
            abonDto.setPaid(abon.isPaid());
            abonDto.setStartDate(abon.getStartDate());
            abonDto.setFinishDate(abon.getFinishDate());
            abonDto.setNumberOfClasses(abon.getNumberOfClasses());

            List<ClientClassDto> clientClassDtos = convertToClientClassDto(abon.getAbonClazzes());
            abonDto.setClasses(clientClassDtos);

            return abonDto;
        };
    }

    private List<ClientClassDto> convertToClientClassDto(@NotNull List<AbonClazz> abonClazzes) {
        return abonClazzes
                .stream()
                .map(AbonClazz::getClazz)
                .map(toClientClassDto())
                .collect(toList());
    }

    private Function<Clazz, ClientClassDto> toClientClassDto() {
        return clazz -> {
            ClientClassDto classDto = new ClientClassDto();
            classDto.setClassType(clazz.getClassType());
            classDto.setDateTime(clazz.getDateTime());
            classDto.setCoachLastName(clazz.getCoach().getLastName());

            return classDto;
        };
    }
}
