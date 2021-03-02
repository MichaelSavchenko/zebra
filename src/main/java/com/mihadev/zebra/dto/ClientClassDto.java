package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

import java.time.LocalDateTime;

public class ClientClassDto {
    private LocalDateTime dateTime;
    private ClassType classType;
    private String coachLastName;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public String getCoachLastName() {
        return coachLastName;
    }

    public void setCoachLastName(String coachLastName) {
        this.coachLastName = coachLastName;
    }
}
