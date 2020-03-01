package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.ClassType;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.entity.Gym;
import com.mihadev.zebra.entity.schedule.Schedule;
import com.mihadev.zebra.entity.schedule.ScheduleClass;
import com.mihadev.zebra.entity.schedule.ScheduleDay;
import com.mihadev.zebra.repository.*;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mihadev.zebra.utils.CollectionUtils.toList;
import static com.mihadev.zebra.utils.CollectionUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.function.Function.identity;


@Service
public class ScheduleScript {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDayRepository scheduleDayRepository;
    private final ScheduleClassRepository scheduleClassRepository;
    private final GymRepository gymRepository;
    private final CoachRepository coachRepository;

    private final static String Веремій = "Веремій";
    private final static String Нерознак = "Нерознак";
    private final static String Савченко = "Савченко";
    private final static String Славная = "Славная";
    private final static String Степура = "Степура";
    private final static String Щербак = "Щербак";
    private final static String Куць = "Куць";
    private final static String Козоріз = "Козоріз";
    private final static String Ященко = "Ященко";
    private final static String Калашник = "Калашник";
    private final static String Дементьєв = "Дементьєв";
    private final static String Добровольська = "Добровольська";
    private final static String Фурман = "Фурман";

    public ScheduleScript(
            ScheduleRepository scheduleRepository,
            ScheduleDayRepository scheduleDayRepository,
            ScheduleClassRepository scheduleClassRepository,
            GymRepository gymRepository,
            CoachRepository coachRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleDayRepository = scheduleDayRepository;
        this.scheduleClassRepository = scheduleClassRepository;
        this.gymRepository = gymRepository;
        this.coachRepository = coachRepository;
    }

    public void setUpMyt() {
        deleteAll();

        Schedule schedule = new Schedule();
        scheduleRepository.save(schedule);

        Gym gym = new Gym();
        gym.setName("Митниця");
        gym.setSchedule(schedule);
        gymRepository.save(gym);

        Map<String, Coach> coaches = getCoaches();
        List<ScheduleDay> week = createWeek(schedule);

        //Monday
        ScheduleClass class1 = new ScheduleClass();
        class1.setStartTime(LocalTime.of(10, 0));
        class1.setClassType(ClassType.POLE_DANCE);
        class1.setScheduleDay(week.get(0));
        class1.setCoach(coaches.get(Веремій));

        ScheduleClass class2 = new ScheduleClass();
        class2.setStartTime(LocalTime.of(11, 0));
        class2.setClassType(ClassType.POLE_DANCE);
        class2.setScheduleDay(week.get(0));
        class2.setCoach(coaches.get(Савченко));

        ScheduleClass class3 = new ScheduleClass();
        class3.setStartTime(LocalTime.of(12, 0));
        class3.setClassType(ClassType.POLE_DANCE);
        class3.setScheduleDay(week.get(0));
        class3.setCoach(coaches.get(Фурман));


        scheduleClassRepository.saveAll(asList(class1, class2, class3));
    }

    private void deleteAll() {
        scheduleRepository.deleteAll();
        scheduleDayRepository.deleteAll();
        scheduleClassRepository.deleteAll();
    }

    private List<ScheduleDay> createWeek(Schedule schedule) {
        List<ScheduleDay> scheduleDays = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            scheduleDays.add(new ScheduleDay(dayOfWeek, schedule));
        }

        return toList(scheduleDayRepository.saveAll(scheduleDays));
    }


    private Map<String, Coach> getCoaches() {
        return toStream(coachRepository.findAll())
                .collect(Collectors.toMap(Coach::getLastName, identity()));
    }
}
