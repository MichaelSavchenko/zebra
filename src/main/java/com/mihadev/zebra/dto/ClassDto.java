package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

import java.time.LocalDateTime;

public class ClassDto {

    private Integer id;
    private LocalDateTime dateTime;
    private ClassType classType;
    private int coachId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
