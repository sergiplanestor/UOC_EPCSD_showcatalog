package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.utils.LogUtils;
import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2(topic = "DateMapper")
public class DateMapper {

    private DateMapper() {
    }

    private static final String SIMPLE_DATE_PATTERN = "yyyy-MM-dd";
    private static final String SIMPLE_TIME_PATTERN = "HH:mm";
    private static final String SIMPLE_DATETIME_PATTERN = SIMPLE_DATE_PATTERN + " " + SIMPLE_TIME_PATTERN + ":ss";

    public static String mapDateTime(Date date) {
        return format(SIMPLE_DATETIME_PATTERN, date);
    }

    public static Date mapDateTime(String date) {
        return parse(SIMPLE_DATETIME_PATTERN, date);
    }

    public static String mapOnlyDate(Date date) {
        return format(SIMPLE_DATE_PATTERN, date);
    }

    public static Date mapOnlyDate(String date) {
        return parse(SIMPLE_DATE_PATTERN, date);
    }

    public static String mapOnlyTime(Date date) {
        return format(SIMPLE_TIME_PATTERN, date);
    }

    public static Date mapOnlyTime(String date) {
        return parse(SIMPLE_TIME_PATTERN, date);
    }

    private static Date parse(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            LogUtils.e(
                    log,
                    true,
                    "Provided date=[" + date + "] does not match with the expected pattern=[" + pattern + "]." +
                            "\nNested: " + e.getMessage()
            );
            return null;
        }
    }

    private static String format(String pattern, Date date) {
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (IllegalArgumentException e) {
            LogUtils.e(
                    log,
                    true,
                    "Provided date=[" + date + "] does not match with the expected pattern=[" + pattern + "]." +
                            "\nNested: " + e.getMessage()
            );
            return null;
        }
    }
}
