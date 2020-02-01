package com.mihadev.zebra.entity;

import javax.persistence.*;

@Entity
public class ClassStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Clazz clazz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public ClassStudent() {
    }

    public ClassStudent(Clazz clazz, Student student) {
        this.clazz = clazz;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
