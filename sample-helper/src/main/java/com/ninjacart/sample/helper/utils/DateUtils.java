package com.ninjacart.sample.helper.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }



    public static Date addMinutes(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, hours);
        return calendar.getTime();
    }


    public static boolean  isDateAfter(Date from,Date to){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(from);
        cal2.setTime(to);
        return cal1.after(cal2);
    }
}
