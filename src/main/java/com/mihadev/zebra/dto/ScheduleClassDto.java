package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

public class ScheduleClassDto {

    private Integer id;
    private String startTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
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
