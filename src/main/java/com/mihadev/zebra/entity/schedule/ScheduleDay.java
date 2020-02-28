package com.mihadev.zebra.entity.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Entity
public class ScheduleDay {
    @Id
    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "scheduleDay", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"scheduleDay"})
    private List<ScheduleClass> scheduleClasses;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    @JsonIgnoreProperties({"scheduleDays", "gym"})
    private Schedule schedule;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ScheduleClass> getScheduleClasses() {
        return scheduleClasses;
    }

    public void setScheduleClasses(List<ScheduleClass> scheduleClasses) {
        this.scheduleClasses = scheduleClasses;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
