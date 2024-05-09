package dev.anes.core.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;



public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final String DATETIME_PATTERN = "MM/dd/yyyy HH:mm:ss";
    public static final DateTimeFormatter DTF_DT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    public static final String[] MONTHS = {
        "JANUARY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"

    };

    public static String format(LocalDate date) {
        return date == null ? null : DTF.format(date);
    }

    public static String formatDateTime(LocalDateTime datetime){
        return datetime==null?null:DTF_DT.format(datetime);
    }

    public static LocalDate parse(String dateStr){
        try{
            return DTF.parse(dateStr,LocalDate::from);
        }catch(Exception e){
            return null;
        }
    }
    
    public static LocalDateTime parseDateTime(String dateTimeStr){
        try{
            return DTF_DT.parse(dateTimeStr,LocalDateTime::from);
        }catch(DateTimeException e){
            return null;
        }
    }

    public static boolean isValid(String dateString){
        return DateUtil.parse(dateString)!=null;
    }

    public static boolean isValidDateTime(String dateTimeString){
        return DateUtil.parseDateTime(dateTimeString)!=null;
    }

    public static String localize(LocalDate date){
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    public static String localize(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }
}
