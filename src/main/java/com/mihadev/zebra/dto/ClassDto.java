package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ClassDto {

    private Integer id;
    private LocalTime time;
    private ClassType classType;
    private int coachId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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
}
