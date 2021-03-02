package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.ClientAbonDto;
import com.mihadev.zebra.dto.ClientClassDto;
import com.mihadev.zebra.dto.ClientResponse;
import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.AbonClazz;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Student;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class ClientService {
    private final StudentService studentService;

    public ClientService(StudentService studentService) {
        this.studentService = studentService;
    }

    public ClientResponse getClientAbons(String phone) {
        Student student = studentService.getByPhone("+" + phone);

        ClientResponse result = new ClientResponse();

        result.setFirstName(student.getFirstName());
        result.setLastName(student.getLastName());
        result.setAbons(student.getAbons().stream()
                .sorted(getAbonStartDateComparator())
                .limit(2)
                .map(toDto())
                .collect(toList()));

        return result;
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
                .sorted(getClassComparator())
                .collect(toList());
    }

    private Comparator<ClientClassDto> getClassComparator() {
        return (o1, o2) -> {
            if (o2.getDateTime().isAfter(o1.getDateTime())) {
                return 1;
            } else if (o1.getDateTime().isAfter(o2.getDateTime())) {
                return -1;
            }

            return 0;
        };
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
