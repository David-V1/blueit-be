package com.blueitapp.blueit.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateStampUtil {

    public static String setDateTimeStamp() {
        LocalDateTime newDate = java.time.LocalDateTime.now();
        DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String dateCreated = newDate.format(myDateFormat);
        return dateCreated;
    }
}
