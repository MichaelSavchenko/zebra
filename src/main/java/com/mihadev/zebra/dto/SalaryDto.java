package com.mihadev.zebra.dto;

import com.mihadev.zebra.entity.ClassType;

import java.util.Map;

public class SalaryDto {
    private String coachName;
    private Integer salary;
    private Map<ClassType, Long> numberOfClassesByType;

    public SalaryDto(String lastName, int salary, Map<ClassType, Long> collect) {
        this.coachName = lastName;
        this.salary = salary;
        this.numberOfClassesByType = collect;
    }

    public SalaryDto() {

    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Map<ClassType, Long> getNumberOfClassesByType() {
        return numberOfClassesByType;
    }

    public void setNumberOfClassesByType(Map<ClassType, Long> numberOfClassesByType) {
        this.numberOfClassesByType = numberOfClassesByType;
    }
}
