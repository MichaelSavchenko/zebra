package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

import java.time.LocalDateTime;

public class ClassDto {

    private Integer id;
    private LocalDateTime dateTime;
    private ClassType classType;
    private Integer coachId;
    private String coachLogin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getCoachLogin() {
        return coachLogin;
    }

    public void setCoachLogin(String coachLogin) {
        this.coachLogin = coachLogin;
    }
}
