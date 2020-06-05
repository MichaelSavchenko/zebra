package com.mihadev.zebra.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class AbonClazz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "abon_id")
    @JsonIgnoreProperties({"abonClazzes", "students"})
    private Abon abon;

    @ManyToOne
    @JoinColumn(name = "clazz_id")
    @JsonIgnoreProperties({"abonClazzes", "students", "costPerStudent"})
    private Clazz clazz;


    public AbonClazz() {
    }

    public AbonClazz(Abon abon, Clazz clazz) {
        this.abon = abon;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Abon getAbon() {
        return abon;
    }

    public void setAbon(Abon abon) {
        this.abon = abon;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
