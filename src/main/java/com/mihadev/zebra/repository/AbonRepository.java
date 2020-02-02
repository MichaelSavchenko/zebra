package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AbonRepository extends CrudRepository<Abon, Integer> {

    List<Abon> findByStudentsAndActiveIsTrue(Student student);
}
