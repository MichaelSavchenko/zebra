package com.mihadev.zebra.entity.schedule;

import java.util.Comparator;

public class ScheduleClassComparator implements Comparator<ScheduleClass> {
    @Override
    public int compare(ScheduleClass o1, ScheduleClass o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }
}
