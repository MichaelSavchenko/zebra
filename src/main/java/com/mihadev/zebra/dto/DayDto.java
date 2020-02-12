package com.mihadev.zebra.dto;

import java.time.DayOfWeek;
import java.util.List;

public class DayDto {
    private DayOfWeek dayOfWeek;
    private List<ClassDto> classDtos;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ClassDto> getClassDtos() {
        return classDtos;
    }

    public void setClassDtos(List<ClassDto> classDtos) {
        this.classDtos = classDtos;
    }
}
