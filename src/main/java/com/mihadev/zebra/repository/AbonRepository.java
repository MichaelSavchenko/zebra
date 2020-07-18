package com.mihadev.zebra.repository;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AbonRepository extends CrudRepository<Abon, Integer> {

    List<Abon> findByStudentsAndActiveIsTrueOrderByFinishDate(Student student);

    List<Abon> findByStudentsOrderByFinishDateDesc(Student student);

    List<Abon> findByStudents(Student student);

    List<Abon> findByStartDateIsAfter(LocalDate startDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM Abon a WHERE a.notes = :notes")
    void removeByNotes(@Param("notes") String notes);
}
