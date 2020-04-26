package com.mihadev.zebra.entity.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Coach;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class ScheduleClass {
    public ScheduleClass() {
    }

    public ScheduleClass(ScheduleDay scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalTime startTime;
    private ClassType classType;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "day_id")
    @JsonIgnoreProperties({"scheduleClasses", "schedule"})
    private ScheduleDay scheduleDay;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id")
    @JsonIgnoreProperties({"scheduleClasses", "classes"})
    private Coach coach;

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

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public ScheduleDay getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(ScheduleDay scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleClass that = (ScheduleClass) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(startTime, that.startTime) &&
                classType == that.classType &&
                Objects.equals(scheduleDay, that.scheduleDay) &&
                Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, classType, scheduleDay, coach);
    }
}
