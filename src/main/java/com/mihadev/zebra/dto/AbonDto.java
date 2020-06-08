package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.Abon;
import com.mihadev.zebra.entity.AbonType;
import com.mihadev.zebra.entity.Student;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class AbonDto {

    private Integer id;
    private AbonType abonType;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int numberOfClasses;
    private int price;
    private boolean active;
    private boolean paid;
    private String notes;
    private Set<Integer> students;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Integer> getStudents() {
        return students;
    }

    public void setStudents(Set<Integer> students) {
        this.students = students;
    }

    public static AbonDto fromEntity(Abon abon) {
        AbonDto dto = new AbonDto();
        dto.setId(abon.getId());
        dto.setAbonType(abon.getAbonType());
        dto.setActive(abon.isActive());
        dto.setStartDate(abon.getStartDate());
        dto.setFinishDate(abon.getFinishDate());
        dto.setPrice(abon.getPrice());
        dto.setNumberOfClasses(abon.getNumberOfClasses());
        dto.setNotes(dto.getNotes());

        Set<Integer> studentIds = new HashSet<>();
        if (abon.getStudents() != null && !abon.getStudents().isEmpty()) {
            studentIds = abon.getStudents().stream().map(Student::getId).collect(toSet());
        }

        dto.setStudents(studentIds);

        return dto;
    }
}
