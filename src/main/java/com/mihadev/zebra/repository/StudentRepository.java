package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
