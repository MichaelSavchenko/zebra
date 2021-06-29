package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.AdminDto;
import com.mihadev.zebra.dto.SFDto;
import com.mihadev.zebra.dto.StudentDto;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Student;
import com.mihadev.zebra.entity.User;
import com.mihadev.zebra.repository.StudentRepository;
import com.mihadev.zebra.security.JWTUser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mihadev.zebra.service.AbonService.setActiveAbons;
import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassService classService;

    private Map<Integer, StudentDto> cache = new ConcurrentHashMap<>();

    public StudentService(StudentRepository studentRepository, ClassService classService) {
        this.studentRepository = studentRepository;
        this.classService = classService;
    }

    public List<StudentDto> getAll() {
        if (cache.isEmpty()) {
            cache = toList(studentRepository.findAll()).stream()
                    .map(toDto())
                    .collect(toMap(StudentDto::getId, identity()));
            return new ArrayList<>(cache.values());
        }

        return new ArrayList<>(cache.values());
    }

    private Function<Student, StudentDto> toDto() {
        return student -> {
            AdminDto adminDto = new AdminDto();
            if (Objects.nonNull(student.getAdmin())) {
                adminDto.setId(student.getAdmin().getId());
                adminDto.setName(student.getAdmin().getFirstName());
            }

            StudentDto dto = new StudentDto();
            dto.setId(student.getId());
            dto.setActive(student.isActive());
            dto.setAdmin(adminDto);
            dto.setDescription(student.getDescription());
            dto.setKid(student.isKid());
            dto.setPhoneNumber(student.getPhoneNumber());
            dto.setFirstName(student.getFirstName());
            dto.setLastName(student.getLastName());

            return dto;
        };
    }

    public Student get(int studentId) {
        return studentRepository.findById(studentId).orElseThrow(RuntimeException::new);
    }

    public Student getByPhone(String phone) {
        Student empty = new Student();
        empty.setAbons(new HashSet<>());
        Student student = studentRepository.findByPhoneNumber(phone).orElse(empty);

        setActiveAbons(student.getAbons());

        return student;
    }

    public Student create(StudentDto dto) {
        Student student = toStudent(dto);
        studentRepository.save(student);
        clearStudentsCache();
        return student;
    }

    public Student update(StudentDto dto) {
        Student student = toStudent(dto);
        studentRepository.save(student);
        clearStudentsCache();
        return student;
    }

    private Student toStudent(StudentDto dto) {
        Student student = new Student();
        if (Objects.nonNull(dto.getId())) {
            student.setId(dto.getId());
        }
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDescription(dto.getDescription());
        student.setActive(dto.isActive());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setKid(dto.isKid());

        if (Objects.isNull(dto.getAdmin())) {
            JWTUser currentUser = AdminEntityService.getCurrentUser();
            student.setAdmin(new User(currentUser.getId()));
        } else {
            student.setAdmin(new User(dto.getAdmin().getId()));
        }

        return student;
    }


    public void saveAll(List<SFDto> dtos) {
        List<Student> students = new ArrayList<>();
        for (SFDto dto : dtos) {

            if (dto.getName() != null) {
                Student student = new Student();
                String[] fullName = dto.getName().split(" ");

                if (fullName.length == 2) {
                    student.setFirstName(fullName[1]);
                }

                student.setLastName(fullName[0]);

                student.setKid(dto.isKid());
                student.setActive(true);

                students.add(student);
            }

        }

        studentRepository.saveAll(students);
        clearStudentsCache();
    }

    private void clearStudentsCache() {
        cache = new ConcurrentHashMap<>();
    }

    public void refreshStudentsCache() {
        getAll();
    }

    public List<Integer> getByClass(int classId) {
        int numberOfWeeksToSearch = 4;
        long start = System.currentTimeMillis();
        List<Clazz> matchedClasses = classService.getClassesByCoachByDateByType(classId, numberOfWeeksToSearch);

        Map<Integer, Long> studentIdToVisitedClasses = matchedClasses.stream()
                .flatMap(clazz -> clazz.getStudents().stream())
                .collect(Collectors.groupingBy(Student::getId, Collectors.counting()));

        studentIdToVisitedClasses.forEach((id, numberOfClassesVisited) -> System.out.println("id: " + id + "number of entries: " + numberOfClassesVisited));

        List<Integer> result = studentIdToVisitedClasses.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());

        long duration = System.currentTimeMillis() - start;
        System.out.println("time in millis for getting top 10 students: " + duration);

        return result;
    }
}
