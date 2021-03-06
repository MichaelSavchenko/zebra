package com.mihadev.zebra.entity.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mihadev.zebra.entity.Gym;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(mappedBy = "schedule")
    @JsonIgnoreProperties({"schedule"})
    private Gym gym;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("schedule")
    private Set<ScheduleDay> scheduleDays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public Set<ScheduleDay> getScheduleDays() {
        return scheduleDays;
    }

    public void setScheduleDays(Set<ScheduleDay> scheduleDays) {
        this.scheduleDays = scheduleDays;
    }
}
