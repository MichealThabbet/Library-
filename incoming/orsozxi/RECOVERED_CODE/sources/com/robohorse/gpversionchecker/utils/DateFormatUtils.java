package com.robohorse.gpversionchecker.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DateFormatUtils {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.UK);

    public Date formatTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        try {
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}
