package com.bnuz.aed.common.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Leia Liang
 */
public class DateUtils {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date stringToDate(String string) throws ParseException {
        return simpleDateFormat.parse(string);
    }

    public static Date DateToDate(Date sourceDate) throws ParseException {
        return simpleDateFormat.parse(simpleDateFormat.format(sourceDate));
    }
}
