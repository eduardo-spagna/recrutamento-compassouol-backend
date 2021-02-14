package com.compassouol.backendrecruitment.utils;

import java.time.LocalDate;
import java.time.Period;

public class DateUtil {
    public static int getAgeFromBirthdate(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthdate, currentDate);
        return period.getYears();
    }
}
