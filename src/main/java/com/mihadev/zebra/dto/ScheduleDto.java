package com.mihadev.zebra.dto;

import java.util.List;

public class ScheduleDto {
    private Integer id;
    private GymDto gymDto;
    private List<DayDto> days;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GymDto getGymDto() {
        return gymDto;
    }

    public void setGymDto(GymDto gymDto) {
        this.gymDto = gymDto;
    }

    public List<DayDto> getDays() {
        return days;
    }

    public void setDays(List<DayDto> days) {
        this.days = days;
    }
}
