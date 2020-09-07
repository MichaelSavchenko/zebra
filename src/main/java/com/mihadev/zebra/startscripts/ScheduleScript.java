package com.mihadev.zebra.startscripts;

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

import static com.mihadev.zebra.entity.ClassType.*;
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
    private final static String Куць = "Куць";
    private final static String Козоріз = "Козоріз";
    private final static String Ященко = "Ященко";
    private final static String Калашник = "Калашник";
    private final static String Дементьєв = "Дементьєв";
    private final static String Добровольська = "Добровольська";
    private final static String Фурман = "Фурман";
    private final static String Ковтун = "Ковтун";

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

    public void setupSchedule() {
        deleteAll();
        setUpMyt();
        setUpKr();
    }

    private void setUpMyt() {
        Schedule schedule = new Schedule();
        scheduleRepository.save(schedule);

        Gym gym = new Gym();
        gym.setName("Митниця");
        gym.setSchedule(schedule);
        gymRepository.save(gym);

        Map<String, Coach> coaches = getCoaches();

        List<ScheduleDay> week = createWeek(schedule);
        List<ScheduleDay> monWedFri = asList(week.get(0), week.get(2), week.get(4));
        List<ScheduleDay> tueThu = asList(week.get(1), week.get(3));

        List<ScheduleClass> classes = new ArrayList<>(setUpMonWedFriMyt(coaches, monWedFri));
        classes.addAll(setUpTueThuSatMyt(coaches, tueThu));
        classes.addAll(setUpSatMyt(coaches, week.get(5)));

        scheduleClassRepository.saveAll(classes);
    }

    private void setUpKr() {
        Schedule schedule = new Schedule();
        scheduleRepository.save(schedule);

        Gym gym = new Gym();
        gym.setName("Крещатик");
        gym.setSchedule(schedule);
        gymRepository.save(gym);

        Map<String, Coach> coaches = getCoaches();

        List<ScheduleDay> week = createWeek(schedule);
        List<ScheduleDay> monWedFri = asList(week.get(0), week.get(2), week.get(4));
        List<ScheduleDay> tueThu = asList(week.get(1), week.get(3));

        List<ScheduleClass> classes = new ArrayList<>(setUpMonWedFriKr(coaches, monWedFri));
        classes.addAll(setUpTueThuKr(coaches, tueThu));
        classes.addAll(setUpSatKr(coaches, week.get(5)));

        scheduleClassRepository.saveAll(classes);
    }

    private List<ScheduleClass> setUpSatKr(Map<String, Coach> coaches, ScheduleDay saturday) {
        ScheduleClass class0 = new ScheduleClass(saturday);
        class0.setStartTime(LocalTime.of(11, 0));
        class0.setClassType(EXOT);
        class0.setCoach(coaches.get(Ященко));

        ScheduleClass class1 = new ScheduleClass(saturday);
        class1.setStartTime(LocalTime.of(12, 0));
        class1.setClassType(SILKS);
        class1.setCoach(coaches.get(Ященко));

        ScheduleClass class2 = new ScheduleClass(saturday);
        class2.setStartTime(LocalTime.of(13, 0));
        class2.setClassType(SILKS);
        class2.setCoach(coaches.get(Ященко));

        return new ArrayList<>(asList(class0, class1, class2));
    }


    private List<ScheduleClass> setUpTueThuKr(Map<String, Coach> coaches, List<ScheduleDay> tueThu) {
        List<ScheduleClass> classes = new ArrayList<>();

        for (ScheduleDay day : tueThu) {
            ScheduleClass class1 = new ScheduleClass(day);
            class1.setStartTime(LocalTime.of(11, 0));
            class1.setClassType(POLE_DANCE);
            class1.setCoach(coaches.get(Добровольська));

            ScheduleClass class2 = new ScheduleClass(day);
            class2.setStartTime(LocalTime.of(16, 0));
            class2.setClassType(POLE_DANCE_KIDS);
            class2.setCoach(coaches.get(Козоріз));

            ScheduleClass class3 = new ScheduleClass(day);
            class3.setStartTime(LocalTime.of(17, 0));
            class3.setClassType(POLE_DANCE_KIDS);
            class3.setCoach(coaches.get(Козоріз));

            ScheduleClass class4 = new ScheduleClass(day);
            class4.setStartTime(LocalTime.of(18, 0));
            class4.setClassType(POLE_DANCE);
            class4.setCoach(coaches.get(Ковтун));

            ScheduleClass class5 = new ScheduleClass(day);
            class5.setStartTime(LocalTime.of(19, 0));
            class5.setClassType(POLE_DANCE);
            class5.setCoach(coaches.get(Степура));

            ScheduleClass class6 = new ScheduleClass(day);
            class6.setStartTime(LocalTime.of(20, 0));
            class6.setClassType(POLE_DANCE);
            class6.setCoach(coaches.get(Степура));

            classes.addAll(asList(class1, class2, class3, class4, class5, class6));
        }

        return classes;
    }

    private List<ScheduleClass> setUpMonWedFriKr(Map<String, Coach> coaches, List<ScheduleDay> monWedFri) {
        List<ScheduleClass> classes = new ArrayList<>();

        for (ScheduleDay day : monWedFri) {
            ScheduleClass class1 = new ScheduleClass(day);
            class1.setStartTime(LocalTime.of(10, 0));
            class1.setClassType(POLE_DANCE);
            class1.setCoach(coaches.get(Славная));

            ScheduleClass class2 = new ScheduleClass(day);
            class2.setStartTime(LocalTime.of(11, 0));
            class2.setClassType(POLE_DANCE);
            class2.setCoach(coaches.get(Калашник));

            ScheduleClass class3 = new ScheduleClass(day);
            class3.setStartTime(LocalTime.of(12, 0));
            class3.setClassType(STRETCHING);
            class3.setCoach(coaches.get(Калашник));

            ScheduleClass class4 = new ScheduleClass(day);
            class4.setStartTime(LocalTime.of(16, 0));
            class4.setClassType(POLE_DANCE_KIDS);
            class4.setCoach(coaches.get(Нерознак));

            ScheduleClass class5 = new ScheduleClass(day);
            class5.setStartTime(LocalTime.of(17, 0));
            class5.setClassType(POLE_DANCE_KIDS);
            class5.setCoach(coaches.get(Нерознак));

            ScheduleClass class6 = new ScheduleClass(day);
            class6.setStartTime(LocalTime.of(18, 0));
            class6.setClassType(POLE_DANCE);
            class6.setCoach(coaches.get(Ковтун));

            ScheduleClass class7 = new ScheduleClass(day);
            class7.setStartTime(LocalTime.of(19, 0));
            class7.setClassType(POLE_DANCE);
            class7.setCoach(coaches.get(Ковтун));

            ScheduleClass class8 = new ScheduleClass(day);
            class8.setStartTime(LocalTime.of(20, 0));
            class8.setClassType(SILKS);
            class8.setCoach(coaches.get(Ященко));

            classes.addAll(asList(class1, class2, class3, class4, class5, class6, class7, class8));
        }

        return classes;
    }


    private List<ScheduleClass> setUpMonWedFriMyt(Map<String, Coach> coaches, List<ScheduleDay> monWedFri) {
        List<ScheduleClass> classes = new ArrayList<>();

        for (ScheduleDay day : monWedFri) {

            ScheduleClass class1 = new ScheduleClass(day);
            class1.setStartTime(LocalTime.of(9, 30));
            class1.setClassType(POLE_DANCE);
            class1.setCoach(coaches.get(Фурман));

            ScheduleClass class2 = new ScheduleClass(day);
            class2.setStartTime(LocalTime.of(10, 30));
            class2.setClassType(STRETCHING);
            class2.setCoach(coaches.get(Савченко));

            ScheduleClass class3 = new ScheduleClass(day);
            class3.setStartTime(LocalTime.of(12, 30));
            class3.setClassType(POLE_DANCE);
            class3.setCoach(coaches.get(Добровольська));

            ScheduleClass class4 = new ScheduleClass(day);
            class4.setStartTime(LocalTime.of(16, 0));
            class4.setClassType(POLE_DANCE_KIDS);
            class4.setCoach(coaches.get(Калашник));

            ScheduleClass class5 = new ScheduleClass(day);
            class5.setStartTime(LocalTime.of(17, 0));
            class5.setClassType(POLE_DANCE_KIDS);
            class5.setCoach(coaches.get(Калашник));

            ScheduleClass class6 = new ScheduleClass(day);
            class6.setStartTime(LocalTime.of(18, 0));
            class6.setClassType(POLE_DANCE);
            class6.setCoach(coaches.get(Калашник));

            ScheduleClass class7 = new ScheduleClass(day);
            class7.setStartTime(LocalTime.of(19, 0));
            class7.setClassType(POLE_DANCE);
            class7.setCoach(coaches.get(Дементьєв));

            ScheduleClass class8 = new ScheduleClass(day);
            class8.setStartTime(LocalTime.of(19, 0));
            class8.setClassType(STRETCHING);
            class8.setCoach(coaches.get(Ковтун));


            ScheduleClass class9 = new ScheduleClass(day);
            class9.setStartTime(LocalTime.of(20, 0));
            class9.setClassType(POLE_DANCE);
            class9.setCoach(coaches.get(Дементьєв));

            ScheduleClass class10 = new ScheduleClass(day);
            class10.setStartTime(LocalTime.of(21, 0));
            class10.setClassType(POLE_DANCE);
            class10.setCoach(coaches.get(Куць));

            classes.addAll(asList(class1, class2, class3, class4, class5, class6, class7, class8, class9, class10));
        }

        return classes;
    }

    private List<ScheduleClass> setUpTueThuSatMyt(Map<String, Coach> coaches, List<ScheduleDay> tueThu) {
        List<ScheduleClass> classes = new ArrayList<>();

        for (ScheduleDay day : tueThu) {
            ScheduleClass class1 = new ScheduleClass(day);
            class1.setStartTime(LocalTime.of(16, 0));
            class1.setClassType(POLE_DANCE_KIDS);
            class1.setCoach(coaches.get(Веремій));

            ScheduleClass class2 = new ScheduleClass(day);
            class2.setStartTime(LocalTime.of(17, 0));
            class2.setClassType(POLE_DANCE_KIDS);
            class2.setCoach(coaches.get(Веремій));

            ScheduleClass class3 = new ScheduleClass(day);
            class3.setStartTime(LocalTime.of(18, 0));
            class3.setClassType(POLE_DANCE);
            class3.setCoach(coaches.get(Веремій));

            ScheduleClass class4 = new ScheduleClass(day);
            class4.setStartTime(LocalTime.of(19, 0));
            class4.setClassType(POLE_DANCE);
            class4.setCoach(coaches.get(Козоріз));

            ScheduleClass class5 = new ScheduleClass(day);
            class5.setStartTime(LocalTime.of(19, 0));
            class5.setClassType(STRETCHING);
            class5.setCoach(coaches.get(Веремій));

            ScheduleClass class6 = new ScheduleClass(day);
            class6.setStartTime(LocalTime.of(20, 0));
            class6.setClassType(EXOT);
            class6.setCoach(coaches.get(Ященко));

            classes.addAll(asList(class1, class2, class3, class4, class5, class6));
        }

        return classes;
    }

    private List<ScheduleClass> setUpSatMyt(Map<String, Coach> coaches, ScheduleDay saturday) {

        ScheduleClass class1 = new ScheduleClass(saturday);
        class1.setStartTime(LocalTime.of(10, 0));
        class1.setClassType(POLE_DANCE_KIDS);
        class1.setCoach(coaches.get(Дементьєв));

        ScheduleClass class2 = new ScheduleClass(saturday);
        class2.setStartTime(LocalTime.of(11, 0));
        class2.setClassType(ACROBATICS);
        class2.setCoach(coaches.get(Дементьєв));

        ScheduleClass class3 = new ScheduleClass(saturday);
        class3.setStartTime(LocalTime.of(11, 0));
        class3.setClassType(STRETCHING);
        class3.setCoach(coaches.get(Веремій));

        ScheduleClass class4 = new ScheduleClass(saturday);
        class4.setStartTime(LocalTime.of(12, 0));
        class4.setClassType(POLE_DANCE);
        class4.setCoach(coaches.get(Веремій));

        ScheduleClass class5 = new ScheduleClass(saturday);
        class5.setStartTime(LocalTime.of(13, 0));
        class5.setClassType(ACROBATICS);
        class5.setCoach(coaches.get(Дементьєв));

        return new ArrayList<>(asList(class1, class2, class3, class4, class5));
    }

    private void deleteAll() {
        gymRepository.deleteAll();
        scheduleClassRepository.deleteAll();
        scheduleDayRepository.deleteAll();
        scheduleRepository.deleteAll();
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
