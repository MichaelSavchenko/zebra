package com.mihadev.zebra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Price {
    @Id
    private ClassType classType;
    private int costPerStudent;

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public int getCostPerStudent() {
        return costPerStudent;
    }

    public void setCostPerStudent(int costPerStudent) {
        this.costPerStudent = costPerStudent;
    }
}
