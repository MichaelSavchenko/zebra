package com.mihadev.zebra.entity.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mihadev.zebra.entity.Gym;

import javax.persistence.*;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JsonIgnoreProperties({"schedule", "classes"})
    private Gym gym;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.MERGE)
    private List<ScheduleDay> scheduleDays;
}
