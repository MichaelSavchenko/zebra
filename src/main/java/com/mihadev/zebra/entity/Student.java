package com.mihadev.zebra.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String description;
    private String phoneNumber;
    private boolean active;
    private boolean kid;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"students", "coach", "abonClazzes", "costPerStudent", "dateTime", "costPerStudent", "classType", "createdBy", "createdDate", "updatedBy", "updatedDate"})
    private Set<Clazz> classes = new HashSet<>();

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"students", "abonClazzes", "abonType", "startDate", "finishDate", "active", "paid", "numberOfClasses", "numberOfUsedClasses", "price", "notes", "autoCreated","createdBy", "createdDate", "updatedBy", "updatedDate" })
    private Set<Abon> abons = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnoreProperties({"userName", "firstName", "lastName", "password", "roles", "students"})
    private User admin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Clazz> getClasses() {
        return classes;
    }

    public void setClasses(Set<Clazz> classes) {
        this.classes = classes;
    }

    public Set<Abon> getAbons() {
        return abons;
    }

    public void setAbons(Set<Abon> abons) {
        this.abons = abons;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isKid() {
        return kid;
    }

    public void setKid(boolean kid) {
        this.kid = kid;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
