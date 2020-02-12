package com.mihadev.zebra.entity;

import com.mihadev.zebra.entity.schedule.Schedule;

import javax.persistence.*;
import java.util.List;

@Entity
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToOne
    private Schedule schedule;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Clazz> classes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Clazz> getClasses() {
        return classes;
    }

    public void setClasses(List<Clazz> classes) {
        this.classes = classes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
