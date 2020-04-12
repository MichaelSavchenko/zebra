package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    @Query("SELECT s FROM Student s WHERE s.lastName = ''")
    List<Student> findAllByLastNameIsEmpty();

    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.lastName = ''")
    void delteEmptyStudents();
}
