package com.mihadev.zebra.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<ClassStudent> students = new HashSet<>();

    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ClassStudent> getStudents() {
        return students;
    }

    public void setStudents(Set<ClassStudent> students) {
        this.students = students;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
