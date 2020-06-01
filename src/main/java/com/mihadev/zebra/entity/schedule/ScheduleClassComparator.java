package com.mihadev.zebra.entity.schedule;

import java.util.Comparator;

public class ScheduleClassComparator implements Comparator<ScheduleClass> {
    @Override
    public int compare(ScheduleClass o1, ScheduleClass o2) {
        int timeComparator = o1.getStartTime().compareTo(o2.getStartTime());

        if (timeComparator == 0) {
            return Integer.compare(o1.getId(), o2.getId());
        }

        return timeComparator;
    }
}
