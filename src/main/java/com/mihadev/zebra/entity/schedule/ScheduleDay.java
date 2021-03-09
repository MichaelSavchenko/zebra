package com.mihadev.zebra.entity.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class ScheduleDay {
    public ScheduleDay() {
    }

    public ScheduleDay(DayOfWeek dayOfWeek, Schedule schedule) {
        this.dayOfWeek = dayOfWeek;
        this.schedule = schedule;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "scheduleDay", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"scheduleDay"})
    private Set<ScheduleClass> scheduleClasses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    @JsonIgnoreProperties({"scheduleDays", "gym"})
    private Schedule schedule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Set<ScheduleClass> getScheduleClasses() {
        return scheduleClasses;
    }

    public void setScheduleClasses(Set<ScheduleClass> scheduleClasses) {
        this.scheduleClasses = scheduleClasses;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
