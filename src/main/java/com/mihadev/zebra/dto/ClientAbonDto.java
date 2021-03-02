package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.AbonType;

import java.time.LocalDate;
import java.util.List;

public class ClientAbonDto {
    private int id;
    private AbonType abonType;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int numberOfClasses;
    private boolean active;
    private boolean paid;
    private String notes;
    private List<ClientClassDto> classes;

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

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
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

    public List<ClientClassDto> getClasses() {
        return classes;
    }

    public void setClasses(List<ClientClassDto> classes) {
        this.classes = classes;
    }
}
