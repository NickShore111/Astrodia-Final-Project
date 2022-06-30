package com.perscholas.astrodia.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Utilty help class used to convert String date objects
 * to Timestamp objects used to modify flights
 */
@Slf4j
public class StringTimestampConverter {
    String date; // "MM/dd/yyyy"
    String time; // "HH:mm"
    String meridiem; // "AM/PM"

    public Timestamp getTimestamp(String date, String time, String meridiem) {
        this.date = date.trim();
        this.time = time.trim();
        this.meridiem = meridiem.trim();
        String[] dateArr = date.split("/");
        Integer month = Integer.parseInt(dateArr[0]);
        Integer day = Integer.parseInt(dateArr[1]);
        Integer year = Integer.parseInt(dateArr[2]);
        String[] timeArr = time.split(":");
        Integer hours = Integer.parseInt(timeArr[0]);
        Integer minutes = Integer.parseInt(timeArr[1]);

        if (meridiem.equals("PM") || meridiem.equals("pm") || meridiem.equals("Pm")) {
            hours+=12;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);

        return new Timestamp(cal.getTime().getTime());
    }
}
