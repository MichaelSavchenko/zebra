package com.mihadev.zebra.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mihadev.zebra.entity.schedule.ScheduleClass;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean active;
    private String notes;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "coach",  cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"coach", "students", "abons", "createdBy", "createdDate", "classType", "costPerStudent", "dateTime", "updatedBy", "updatedDate"})
    private Set<Clazz> classes;

    @OneToMany(mappedBy = "coach",  cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"coach", "scheduleDay", "classType", "startTime"})
    private Set<ScheduleClass> scheduleClasses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Clazz> getClasses() {
        return classes;
    }

    public void setClasses(Set<Clazz> classes) {
        this.classes = classes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<ScheduleClass> getScheduleClasses() {
        return scheduleClasses;
    }

    public void setScheduleClasses(Set<ScheduleClass> scheduleClasses) {
        this.scheduleClasses = scheduleClasses;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
