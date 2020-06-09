package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    Optional<Student> findByPhoneNumber(String phoneNumber);

    @Query("SELECT s FROM Student s WHERE s.lastName = ''")
    List<Student> findAllByLastNameIsEmpty();

    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.lastName = '0'")
    void delteEmptyStudents();
}
