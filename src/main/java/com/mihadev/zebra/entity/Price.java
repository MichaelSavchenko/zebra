package com.mihadev.zebra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Price {
    @Id
    private ClassType classType;
    private int costPerStudent;
    private int costPerClass;

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

    public int getCostPerClass() {
        return costPerClass;
    }

    public void setCostPerClass(int costPerClass) {
        this.costPerClass = costPerClass;
    }
}
