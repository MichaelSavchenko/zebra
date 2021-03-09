package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

import java.time.LocalTime;

public class ScheduleClassDto {

    private Integer id;
    private LocalTime startTime;
    private int coachId;
    private ClassType classType;
    private int scheduleDayId;

    public int getScheduleDayId() {
        return scheduleDayId;
    }

    public void setScheduleDayId(int scheduleDayId) {
        this.scheduleDayId = scheduleDayId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }
}
