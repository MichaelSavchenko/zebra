package com.mihadev.zebra.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Abon extends AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private AbonType abonType;
    private LocalDate startDate;
    private LocalDate finishDate;
    private boolean active;
    private boolean paid;
    private int numberOfClasses;
    private int numberOfUsedClasses = 0;
    private int price;
    private String notes;
    private boolean autoCreated;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "abon_student",
            joinColumns = {
                    @JoinColumn(name = "abon_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    @JsonIgnoreProperties({"abons", "classes",})
    private Set<Student> students;

    @OneToMany(mappedBy = "abon", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"abon"})
    private List<AbonClazz> abonClazzes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AbonType getAbonType() {
        return abonType;
    }

    public void setAbonType(AbonType abonType) {
        this.abonType = abonType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isAutoCreated() {
        return autoCreated;
    }

    public void setAutoCreated(boolean autoCreated) {
        this.autoCreated = autoCreated;
    }

    public int getNumberOfUsedClasses() {
        return numberOfUsedClasses;
    }

    public void setNumberOfUsedClasses(int numberOfUsedClasses) {
        this.numberOfUsedClasses = numberOfUsedClasses;
    }

    public List<AbonClazz> getAbonClazzes() {
        return abonClazzes;
    }

    public void setAbonClazzes(List<AbonClazz> abonClazzes) {
        this.abonClazzes = abonClazzes;
    }
}
