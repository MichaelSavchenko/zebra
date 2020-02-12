package com.mihadev.zebra.entity.schedule;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class ScheduleClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalTime startTime;
    private int coachId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "day_id")
    private ScheduleDay scheduleDay;

    public ScheduleClass(LocalTime startTime, int coachId) {
        this.startTime = startTime;
        this.coachId = coachId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduleDay getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(ScheduleDay scheduleDay) {
        this.scheduleDay = scheduleDay;
    }
}
