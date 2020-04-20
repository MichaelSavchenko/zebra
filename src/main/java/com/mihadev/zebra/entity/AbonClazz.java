package com.mihadev.zebra.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class AbonClazz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "abon_id")
    @JsonIgnoreProperties({"abonClazzes", "students"})
    private Abon abon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clazz_id")
    @JsonIgnoreProperties({"abonClazzes", "students", "coach"})
    private Clazz clazz;

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
