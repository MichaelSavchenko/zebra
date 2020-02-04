package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.AbonType;

import java.time.LocalDate;
import java.util.Set;

public class AbonDto {

    private AbonType abonType;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int numberOfClasses;
    private int price;
    private boolean active;
    private boolean paid;
    private Set<Integer> students;


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

    public Set<Integer> getStudents() {
        return students;
    }

    public void setStudents(Set<Integer> students) {
        this.students = students;
    }
}
