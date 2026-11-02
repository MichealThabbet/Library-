package org.apache.poi.ss.usermodel;

import it.orsozoxi.android.orsonotes.R2;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class DateUtil {
    private static final int BAD_DATE = -1;
    private static final long DAY_MILLISECONDS = 86400000;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final Pattern TIME_SEPARATOR_PATTERN = Pattern.compile(":");
    private static final Pattern date_ptrn1 = Pattern.compile("^\\[\\$\\-.*?\\]");
    private static final Pattern date_ptrn2 = Pattern.compile("^\\[[a-zA-Z]+\\]");
    private static final Pattern date_ptrn3 = Pattern.compile("^[yYmMdDhHsS\\-/,. :\"\\\\]+0?[ampAMP/]*$");

    public static boolean isInternalDateFormat(int i) {
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                switch (i) {
                    case 45:
                    case 46:
                    case 47:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static boolean isValidExcelDate(double d) {
        return d > -4.9E-324d;
    }

    protected DateUtil() {
    }

    public static double getExcelDate(Date date) {
        return getExcelDate(date, false);
    }

    public static double getExcelDate(Date date, boolean z) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return internalGetExcelDate(gregorianCalendar, z);
    }

    public static double getExcelDate(Calendar calendar, boolean z) {
        return internalGetExcelDate((Calendar) calendar.clone(), z);
    }

    private static double internalGetExcelDate(Calendar calendar, boolean z) {
        if (!z && calendar.get(1) < 1900) {
            return -1.0d;
        }
        if (z && calendar.get(1) < 1904) {
            return -1.0d;
        }
        double dAbsoluteDay = (((((((calendar.get(11) * 60) + calendar.get(12)) * 60) + calendar.get(13)) * 1000) + calendar.get(14)) / 8.64E7d) + absoluteDay(dayStart(calendar), z);
        return (z || dAbsoluteDay < 60.0d) ? z ? dAbsoluteDay - 1.0d : dAbsoluteDay : dAbsoluteDay + 1.0d;
    }

    public static Date getJavaDate(double d) {
        return getJavaDate(d, false);
    }

    public static Date getJavaDate(double d, boolean z) {
        if (!isValidExcelDate(d)) {
            return null;
        }
        int iFloor = (int) Math.floor(d);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        setCalendar(gregorianCalendar, iFloor, (int) (((d - iFloor) * 8.64E7d) + 0.5d), z);
        return gregorianCalendar.getTime();
    }

    public static void setCalendar(Calendar calendar, int i, int i2, boolean z) {
        int i3;
        int i4;
        if (z) {
            i3 = 1;
            i4 = R2.dimen.mtrl_extended_fab_end_padding_icon;
        } else {
            i3 = i < 61 ? 0 : -1;
            i4 = R2.dimen.mtrl_extended_fab_disabled_elevation;
        }
        calendar.set(i4, 0, i + i3, 0, 0, 0);
        calendar.set(14, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isADateFormat(int r8, java.lang.String r9) {
        /*
            boolean r8 = isInternalDateFormat(r8)
            r0 = 1
            if (r8 == 0) goto L8
            return r0
        L8:
            r8 = 0
            if (r9 == 0) goto L8e
            int r1 = r9.length()
            if (r1 != 0) goto L13
            goto L8e
        L13:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            int r2 = r9.length()
            r1.<init>(r2)
            r2 = 0
        L1d:
            int r3 = r9.length()
            r4 = 59
            if (r2 >= r3) goto L51
            char r3 = r9.charAt(r2)
            int r5 = r9.length()
            int r5 = r5 - r0
            if (r2 >= r5) goto L4c
            int r5 = r2 + 1
            char r6 = r9.charAt(r5)
            r7 = 92
            if (r3 != r7) goto L44
            r4 = 32
            if (r6 == r4) goto L4f
            if (r6 == r7) goto L4f
            switch(r6) {
                case 44: goto L4f;
                case 45: goto L4f;
                case 46: goto L4f;
                default: goto L43;
            }
        L43:
            goto L4c
        L44:
            if (r3 != r4) goto L4c
            r4 = 64
            if (r6 != r4) goto L4c
            r2 = r5
            goto L4f
        L4c:
            r1.append(r3)
        L4f:
            int r2 = r2 + r0
            goto L1d
        L51:
            java.lang.String r9 = r1.toString()
            java.util.regex.Pattern r1 = org.apache.poi.ss.usermodel.DateUtil.date_ptrn1
            java.util.regex.Matcher r9 = r1.matcher(r9)
            java.lang.String r1 = ""
            java.lang.String r9 = r9.replaceAll(r1)
            java.util.regex.Pattern r2 = org.apache.poi.ss.usermodel.DateUtil.date_ptrn2
            java.util.regex.Matcher r9 = r2.matcher(r9)
            java.lang.String r9 = r9.replaceAll(r1)
            int r1 = r9.indexOf(r4)
            if (r1 <= 0) goto L84
            int r1 = r9.indexOf(r4)
            int r2 = r9.length()
            int r2 = r2 - r0
            if (r1 >= r2) goto L84
            int r0 = r9.indexOf(r4)
            java.lang.String r9 = r9.substring(r8, r0)
        L84:
            java.util.regex.Pattern r8 = org.apache.poi.ss.usermodel.DateUtil.date_ptrn3
            java.util.regex.Matcher r8 = r8.matcher(r9)
            boolean r8 = r8.matches()
        L8e:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.usermodel.DateUtil.isADateFormat(int, java.lang.String):boolean");
    }

    public static boolean isCellDateFormatted(Cell cell) {
        CellStyle cellStyle;
        if (cell == null || !isValidExcelDate(cell.getNumericCellValue()) || (cellStyle = cell.getCellStyle()) == null) {
            return false;
        }
        return isADateFormat(cellStyle.getDataFormat(), cellStyle.getDataFormatString());
    }

    public static boolean isCellInternalDateFormatted(Cell cell) {
        if (cell != null && isValidExcelDate(cell.getNumericCellValue())) {
            return isInternalDateFormat(cell.getCellStyle().getDataFormat());
        }
        return false;
    }

    protected static int absoluteDay(Calendar calendar, boolean z) {
        return calendar.get(6) + daysInPriorYears(calendar.get(1), z);
    }

    private static int daysInPriorYears(int i, boolean z) {
        int i2 = R2.dimen.mtrl_extended_fab_disabled_elevation;
        if ((!z && i < 1900) || (z && i < 1900)) {
            throw new IllegalArgumentException("'year' must be 1900 or greater");
        }
        int i3 = i - 1;
        int i4 = (((i3 / 4) - (i3 / 100)) + (i3 / 400)) - 460;
        if (z) {
            i2 = R2.dimen.mtrl_extended_fab_end_padding_icon;
        }
        return ((i - i2) * R2.attr.defaultValue) + i4;
    }

    private static Calendar dayStart(Calendar calendar) {
        calendar.get(11);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.get(11);
        return calendar;
    }

    private static final class FormatException extends Exception {
        public FormatException(String str) {
            super(str);
        }
    }

    public static double convertTime(String str) {
        try {
            return convertTimeInternal(str);
        } catch (FormatException e) {
            throw new IllegalArgumentException("Bad time format '" + str + "' expected 'HH:MM' or 'HH:MM:SS' - " + e.getMessage());
        }
    }

    private static double convertTimeInternal(String str) throws FormatException {
        String str2;
        int length = str.length();
        if (length < 4 || length > 8) {
            throw new FormatException("Bad length");
        }
        String[] strArrSplit = TIME_SEPARATOR_PATTERN.split(str);
        int length2 = strArrSplit.length;
        if (length2 == 2) {
            str2 = "00";
        } else if (length2 == 3) {
            str2 = strArrSplit[2];
        } else {
            throw new FormatException("Expected 2 or 3 fields but got (" + strArrSplit.length + ")");
        }
        String str3 = strArrSplit[0];
        String str4 = strArrSplit[1];
        int i = parseInt(str3, "hour", 24);
        return (parseInt(str2, "second", 60) + ((parseInt(str4, "minute", 60) + (i * 60)) * 60)) / 86400.0d;
    }

    public static Date parseYYYYMMDDDate(String str) {
        try {
            return parseYYYYMMDDDateInternal(str);
        } catch (FormatException e) {
            throw new IllegalArgumentException("Bad time format " + str + " expected 'YYYY/MM/DD' - " + e.getMessage());
        }
    }

    private static Date parseYYYYMMDDDateInternal(String str) throws NumberFormatException, FormatException {
        if (str.length() != 10) {
            throw new FormatException("Bad length");
        }
        String strSubstring = str.substring(0, 4);
        String strSubstring2 = str.substring(5, 7);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(parseInt(strSubstring, "year", -32768, 32767), parseInt(strSubstring2, "month", 1, 12) - 1, parseInt(str.substring(8, 10), "day", 1, 31), 0, 0, 0);
        gregorianCalendar.set(14, 0);
        return gregorianCalendar.getTime();
    }

    private static int parseInt(String str, String str2, int i) throws FormatException {
        return parseInt(str, str2, 0, i - 1);
    }

    private static int parseInt(String str, String str2, int i, int i2) throws NumberFormatException, FormatException {
        try {
            int i3 = Integer.parseInt(str);
            if (i3 >= i && i3 <= i2) {
                return i3;
            }
            throw new FormatException(str2 + " value (" + i3 + ") is outside the allowable range(0.." + i2 + ")");
        } catch (NumberFormatException unused) {
            throw new FormatException("Bad int format '" + str + "' for " + str2 + " field");
        }
    }
}
