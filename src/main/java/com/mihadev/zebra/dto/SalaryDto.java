package com.mihadev.zebra.dto;

public class SalaryDto {
    private String coachName;
    private Integer salary;

    public SalaryDto() {
    }

    public SalaryDto(String coachName, Integer salary) {
        this.coachName = coachName;
        this.salary = salary;
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
}
