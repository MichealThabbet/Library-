package it.feio.android.springpadimporter.utils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes2.dex */
public class Utils {
    public static Date getDate(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            return new SimpleDateFormat(Constants.DATE_FORMAT).parse(str);
        } catch (ParseException e) {
            Date date = new Date();
            e.printStackTrace();
            return date;
        }
    }

    public static List<String[]> getFieldNamesAndValues(Object obj) throws IllegalAccessException, IllegalArgumentException {
        Class<?> cls = obj.getClass();
        ArrayList arrayList = new ArrayList();
        Field[] declaredFields = cls.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            String name = declaredFields[i].getName();
            declaredFields[i].setAccessible(true);
            arrayList.add(new String[]{name, String.valueOf(declaredFields[i].get(obj))});
        }
        return arrayList;
    }
}
