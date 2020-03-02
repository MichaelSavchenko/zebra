package com.mihadev.zebra.startscripts;

import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.repository.CoachRepository;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@Service
public class CoachScript {

    private final CoachRepository coachRepository;

    public CoachScript(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public void insertCoaches() {

        coachRepository.deleteAll();

        Coach coach1 = new Coach();
        coach1.setFirstName("Настя");
        coach1.setLastName("Веремій");
        coach1.setActive(true);
        coach1.setPhone("+380632415104");

        Coach coach2 = new Coach();
        coach2.setFirstName("Діана");
        coach2.setLastName("Нерознак");
        coach2.setActive(true);
        coach2.setPhone("+380636025519");

        Coach coach3 = new Coach();
        coach3.setFirstName("Оксана");
        coach3.setLastName("Савченко");
        coach3.setActive(true);
        coach3.setPhone("+380634606073");

        Coach coach4 = new Coach();
        coach4.setFirstName("Свєта");
        coach4.setLastName("Славная");
        coach4.setActive(true);
        coach4.setPhone("+380988896465");

        Coach coach5 = new Coach();
        coach5.setFirstName("Марина");
        coach5.setLastName("Степура");
        coach5.setActive(true);
        coach5.setPhone("+380675978281");

        Coach coach6 = new Coach();
        coach6.setFirstName("Таня");
        coach6.setLastName("Щербак");
        coach6.setActive(true);
        coach6.setPhone("+380636050744");

        Coach coach7 = new Coach();
        coach7.setFirstName("Інна");
        coach7.setLastName("Куць");
        coach7.setActive(true);
        coach7.setPhone("+380634903255");

        Coach coach8 = new Coach();
        coach8.setFirstName("Іра");
        coach8.setLastName("Козоріз");
        coach8.setActive(true);
        coach8.setPhone("+380639319472");

        Coach coach9 = new Coach();
        coach9.setFirstName("Діана");
        coach9.setLastName("Ященко");
        coach9.setActive(true);
        coach9.setPhone("+380936532169");

        Coach coach10 = new Coach();
        coach10.setFirstName("Іра");
        coach10.setLastName("Калашник");
        coach10.setActive(true);
        coach10.setPhone("+380934830409");

        Coach coach11 = new Coach();
        coach11.setFirstName("Артем");
        coach11.setLastName("Дементьєв");
        coach11.setActive(true);
        coach11.setPhone("+380936532169");

        Coach coach12 = new Coach();
        coach12.setFirstName("Даша");
        coach12.setLastName("Добровольська");
        coach12.setActive(true);
        coach12.setPhone("+380636332129");

        Coach coach13 = new Coach();
        coach13.setFirstName("Яна");
        coach13.setLastName("Фурман");
        coach13.setActive(true);
        coach13.setPhone("+380939966936");

        coachRepository.saveAll(
                asList(coach1, coach2, coach3, coach4, coach5, coach6, coach7, coach8, coach9, coach10, coach11, coach12, coach13)
        );
    }
}
