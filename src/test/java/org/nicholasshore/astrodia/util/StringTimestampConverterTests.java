package org.nicholasshore.astrodia.util;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTimestampConverterTests {
    String date; // "MM/dd/yyyy"
    String time; // "HH:mm"
    String meridiem; // "AM/PM"
    @Test
    void getTimestampAMTest(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        Timestamp expectedTimestamp = new Timestamp(cal.getTime().getTime());
        date = "01/01/2022";
        time = "01:01";
        meridiem = "am";
        StringTimestampConverter converter = new StringTimestampConverter();
        Timestamp actualTimestamp = converter.getTimestamp(date, time, meridiem);

        assertThat(expectedTimestamp).isInSameDayAs(actualTimestamp);
    }
    @Test
    void getTimestampPMTest(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        Timestamp expectedTimestamp = new Timestamp(cal.getTime().getTime());
        date = "01/01/2022";
        time = "01:01";
        meridiem = "pm";
        StringTimestampConverter converter = new StringTimestampConverter();
        Timestamp actualTimestamp = converter.getTimestamp(date, time, meridiem);

        assertThat(expectedTimestamp).isInSameDayAs(actualTimestamp);
    }
}
