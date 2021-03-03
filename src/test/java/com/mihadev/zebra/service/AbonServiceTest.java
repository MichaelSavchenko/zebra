/*
package com.mihadev.zebra.service;

import com.mihadev.zebra.entity.Abon;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class AbonServiceTest {

    @Test
    public void activeWitClassNow() {
        Abon inactivePast = new Abon();
        inactivePast.setId(1);
        inactivePast.setFinishDate(LocalDate.now().minusDays(1));

        Abon inactivePast1 = new Abon();
        inactivePast1.setId(2);
        inactivePast1.setFinishDate(LocalDate.now().minusDays(10));

        Abon nowWithOutClasses = new Abon();
        nowWithOutClasses.setId(3);
        nowWithOutClasses.setFinishDate(LocalDate.now());
        nowWithOutClasses.setNumberOfClasses(1);
        nowWithOutClasses.setNumberOfUsedClasses(1);

        Abon nowWithClasses = new Abon();
        nowWithClasses.setId(4);
        nowWithClasses.setFinishDate(LocalDate.now());
        nowWithClasses.setNumberOfClasses(2);
        nowWithClasses.setNumberOfUsedClasses(1);

        Abon future = new Abon();
        future.setId(5);
        future.setFinishDate(LocalDate.now().plusDays(1));
        future.setNumberOfClasses(1);
        future.setNumberOfUsedClasses(0);

        Abon futureNull = new Abon();
        futureNull.setId(6);
        futureNull.setNumberOfClasses(1);
        futureNull.setNumberOfUsedClasses(0);

        List<Abon> abons = Arrays.asList(inactivePast, inactivePast1, nowWithClasses, nowWithOutClasses, future, futureNull);

        Optional<Abon> result = AbonService.calculateActiveAbonForStudent(new HashSet<>(abons), LocalDate.now());

        Assert.assertEquals(nowWithClasses, result.get());
    }

    @Test
    public void noActive() {
        Abon inactivePast = new Abon();
        inactivePast.setId(1);
        inactivePast.setFinishDate(LocalDate.now().minusDays(1));

        Abon inactivePast1 = new Abon();
        inactivePast1.setId(2);
        inactivePast1.setFinishDate(LocalDate.now().minusDays(10));

        List<Abon> abons = Arrays.asList(inactivePast, inactivePast1);

        Optional<Abon> result = AbonService.calculateActiveAbonForStudent(new HashSet<>(abons), LocalDate.now());

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void twoActiveWitClassNow() {
        Abon inactivePast = new Abon();
        inactivePast.setId(1);
        inactivePast.setFinishDate(LocalDate.now().minusDays(1));

        Abon inactivePast1 = new Abon();
        inactivePast1.setId(2);
        inactivePast1.setFinishDate(LocalDate.now().minusDays(10));

        Abon nowWithOutClasses = new Abon();
        nowWithOutClasses.setId(3);
        nowWithOutClasses.setFinishDate(LocalDate.now());
        nowWithOutClasses.setNumberOfClasses(1);
        nowWithOutClasses.setNumberOfUsedClasses(1);

        Abon nowWithClasses = new Abon();
        nowWithClasses.setId(4);
        nowWithClasses.setFinishDate(LocalDate.now());
        nowWithClasses.setNumberOfClasses(2);
        nowWithClasses.setNumberOfUsedClasses(1);

        Abon nowWithClasses1 = new Abon();
        nowWithClasses1.setId(777);
        nowWithClasses1.setFinishDate(LocalDate.now().plusDays(1));
        nowWithClasses1.setNumberOfClasses(2);
        nowWithClasses1.setNumberOfUsedClasses(1);

        Abon future = new Abon();
        future.setId(5);
        future.setFinishDate(LocalDate.now().plusDays(1));
        future.setNumberOfClasses(1);
        future.setNumberOfUsedClasses(0);

        Abon futureNull = new Abon();
        futureNull.setId(6);
        futureNull.setNumberOfClasses(1);
        futureNull.setNumberOfUsedClasses(0);

        List<Abon> abons = Arrays.asList(inactivePast, inactivePast1, nowWithClasses, nowWithOutClasses, future, futureNull);

        Optional<Abon> result = AbonService.calculateActiveAbonForStudent(new HashSet<>(abons), LocalDate.now());

        Assert.assertEquals(nowWithClasses, result.get());
    }

    @Test
    public void twoFutureOneWithNullActiveWitClass() {
        Abon inactivePast = new Abon();
        inactivePast.setId(1);
        inactivePast.setFinishDate(LocalDate.now().minusDays(1));

        Abon inactivePast1 = new Abon();
        inactivePast1.setId(2);
        inactivePast1.setFinishDate(LocalDate.now().minusDays(10));

        Abon nowWithOutClasses = new Abon();
        nowWithOutClasses.setId(3);
        nowWithOutClasses.setFinishDate(LocalDate.now());
        nowWithOutClasses.setNumberOfClasses(1);
        nowWithOutClasses.setNumberOfUsedClasses(1);


        Abon future = new Abon();
        future.setId(5);
        future.setFinishDate(LocalDate.now().plusDays(1));
        future.setNumberOfClasses(1);
        future.setNumberOfUsedClasses(0);

        Abon futureNull = new Abon();
        futureNull.setId(6);
        futureNull.setNumberOfClasses(1);
        futureNull.setNumberOfUsedClasses(0);

        List<Abon> abons = Arrays.asList(inactivePast, inactivePast1, nowWithOutClasses, future, futureNull);

        Optional<Abon> result = AbonService.calculateActiveAbonForStudent(new HashSet<>(abons), LocalDate.now());

        Assert.assertEquals(future, result.get());
    }

    @Test
    public void twoFutureActiveWitClass() {
        Abon inactivePast = new Abon();
        inactivePast.setId(1);
        inactivePast.setFinishDate(LocalDate.now().minusDays(1));

        Abon inactivePast1 = new Abon();
        inactivePast1.setId(2);
        inactivePast1.setFinishDate(LocalDate.now().minusDays(10));

        Abon nowWithOutClasses = new Abon();
        nowWithOutClasses.setId(3);
        nowWithOutClasses.setFinishDate(LocalDate.now());
        nowWithOutClasses.setNumberOfClasses(1);
        nowWithOutClasses.setNumberOfUsedClasses(1);


        Abon future = new Abon();
        future.setId(5);
        future.setFinishDate(LocalDate.now().plusDays(1));
        future.setNumberOfClasses(1);
        future.setNumberOfUsedClasses(0);

        Abon future2 = new Abon();
        future2.setFinishDate(LocalDate.now().plusDays(2));
        future2.setId(6);
        future2.setNumberOfClasses(1);
        future2.setNumberOfUsedClasses(0);

        List<Abon> abons = Arrays.asList(inactivePast, inactivePast1, nowWithOutClasses, future, future2);

        Optional<Abon> result = AbonService.calculateActiveAbonForStudent(new HashSet<>(abons), LocalDate.now());

        Assert.assertEquals(future, result.get());
    }
}
*/
