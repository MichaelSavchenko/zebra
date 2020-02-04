package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AbonRepository extends CrudRepository<Abon, Integer> {

    List<Abon> findByStudentsAndActiveIsTrueOrderByFinishDate(Student student);

    List<Abon> findByStudents(Student student);
}
