package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.ClassStudent;
import com.mihadev.zebra.entity.Clazz;
import com.mihadev.zebra.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassStudentRepository extends CrudRepository<ClassStudent, Long> {

    @Transactional
    void deleteByClazzAndStudent(@Param("class_id") Clazz clazz,@Param("student_id") Student student);

    List<ClassStudent> findAllByStudent(@Param("student_id") Student student);
}
