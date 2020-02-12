package com.mihadev.zebra.entity.schedule;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Entity
class ScheduleDay {
    @Id
    private DayOfWeek dayOfWeek;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<ScheduleClass> scheduleClasses;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "schedule_id")
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
