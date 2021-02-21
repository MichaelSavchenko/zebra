package com.mihadev.zebra.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Clazz extends AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime dateTime;
    private int costPerStudent = 0;
    private ClassType classType;

    @ManyToMany
    @JoinTable(name = "class_student",
            joinColumns = {
                    @JoinColumn(name = "class_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    @JsonIgnoreProperties({"classes", "abons", "active", "description", "kid", "phoneNumber"})
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "coach_id")
    @JsonIgnoreProperties({"classes", "scheduleClasses", "active", "notes", "phone","dateOfBirth"})
    private Coach coach;

    @OneToMany(mappedBy = "clazz")
    @JsonIgnore
    private Set<AbonClazz> abonClazzes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getCostPerStudent() {
        return costPerStudent;
    }

    public void setCostPerStudent(int costPerStudent) {
        this.costPerStudent = costPerStudent;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public Set<AbonClazz> getAbonClazzes() {
        return abonClazzes;
    }

    public void setAbonClazzes(Set<AbonClazz> abonClazzes) {
        this.abonClazzes = abonClazzes;
    }
}
