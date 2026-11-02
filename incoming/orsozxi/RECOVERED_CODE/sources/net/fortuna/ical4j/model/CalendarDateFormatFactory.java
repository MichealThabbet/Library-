package net.fortuna.ical4j.model;

import it.piapostolos.android.pinotes.R2;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public final class CalendarDateFormatFactory {
    private static final String DATETIME_PATTERN = "yyyyMMdd'T'HHmmss";
    private static final String DATETIME_UTC_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) CalendarDateFormatFactory.class);
    private static final String TIME_PATTERN = "HHmmss";
    private static final String TIME_UTC_PATTERN = "HHmmss'Z'";

    private CalendarDateFormatFactory() {
    }

    public static java.text.DateFormat getInstance(String str) {
        if (str.equals(DATETIME_PATTERN) || str.equals(DATETIME_UTC_PATTERN)) {
            return new DateTimeFormat(str);
        }
        if (str.equals(DATE_PATTERN)) {
            return new DateFormat(str);
        }
        if (str.equals(TIME_PATTERN) || str.equals(TIME_UTC_PATTERN)) {
            return new TimeFormat(str);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("unexpected date format pattern: " + str);
        }
        return new SimpleDateFormat(str);
    }

    private static abstract class CalendarDateFormat extends java.text.DateFormat {
        private static final java.util.TimeZone DEFAULT_TIME_ZONE = java.util.TimeZone.getDefault();
        private static final long serialVersionUID = -4191402739860280205L;
        private final String pattern;
        private boolean lenient = true;
        private java.util.TimeZone timeZone = DEFAULT_TIME_ZONE;

        public CalendarDateFormat(String str) {
            this.pattern = str;
        }

        @Override // java.text.DateFormat
        public java.util.TimeZone getTimeZone() {
            return this.timeZone;
        }

        @Override // java.text.DateFormat
        public void setTimeZone(java.util.TimeZone timeZone) {
            this.timeZone = timeZone;
        }

        @Override // java.text.DateFormat
        public void setLenient(boolean z) {
            this.lenient = z;
        }

        @Override // java.text.DateFormat
        public boolean isLenient() {
            return this.lenient;
        }

        @Override // java.text.DateFormat
        public java.util.Calendar getCalendar() {
            throw new UnsupportedOperationException();
        }

        @Override // java.text.DateFormat
        public void setCalendar(java.util.Calendar calendar) {
            throw new UnsupportedOperationException();
        }

        @Override // java.text.DateFormat
        public NumberFormat getNumberFormat() {
            throw new UnsupportedOperationException();
        }

        @Override // java.text.DateFormat
        public void setNumberFormat(NumberFormat numberFormat) {
            throw new UnsupportedOperationException();
        }

        @Override // java.text.DateFormat, java.text.Format
        public Object clone() {
            CalendarDateFormat calendarDateFormat = (CalendarDateFormat) CalendarDateFormatFactory.getInstance(this.pattern);
            calendarDateFormat.setTimeZone(getTimeZone());
            calendarDateFormat.setLenient(isLenient());
            return calendarDateFormat;
        }

        @Override // java.text.DateFormat
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            CalendarDateFormat calendarDateFormat = (CalendarDateFormat) obj;
            return this.lenient == calendarDateFormat.lenient && this.pattern.equals(calendarDateFormat.pattern) && this.timeZone.equals(calendarDateFormat.timeZone);
        }

        @Override // java.text.DateFormat
        public int hashCode() {
            return (((((super.hashCode() * 31) + this.pattern.hashCode()) * 31) + (this.lenient ? 1 : 0)) * 31) + this.timeZone.hashCode();
        }
    }

    private static class DateTimeFormat extends CalendarDateFormat {
        private static final long serialVersionUID = 3005824302269636122L;
        final boolean patternEndsWithZ;

        public DateTimeFormat(String str) {
            super(str);
            this.patternEndsWithZ = str.endsWith("'Z'");
        }

        @Override // java.text.DateFormat
        public StringBuffer format(java.util.Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar(getTimeZone());
            gregorianCalendar.setTimeInMillis(date.getTime());
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(1), 4);
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(2) + 1, 2);
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(5), 2);
            stringBuffer.append("T");
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(11), 2);
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(12), 2);
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(13), 2);
            if (this.patternEndsWithZ) {
                stringBuffer.append("Z");
            }
            return stringBuffer;
        }

        @Override // java.text.DateFormat
        public java.util.Date parse(String str, ParsePosition parsePosition) throws NumberFormatException {
            if (this.patternEndsWithZ) {
                if (str.length() > 20 && !isLenient()) {
                    parsePosition.setErrorIndex(20);
                    return null;
                }
            } else if (str.length() > 17 && !isLenient()) {
                parsePosition.setErrorIndex(17);
                return null;
            }
            try {
                if (str.charAt(8) != 'T') {
                    parsePosition.setErrorIndex(8);
                    return null;
                }
                if (this.patternEndsWithZ && str.charAt(15) != 'Z') {
                    parsePosition.setErrorIndex(15);
                    return null;
                }
                java.util.Date time = CalendarDateFormatFactory.makeCalendar(isLenient(), getTimeZone(), Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6)) - 1, Integer.parseInt(str.substring(6, 8)), Integer.parseInt(str.substring(9, 11)), Integer.parseInt(str.substring(11, 13)), Integer.parseInt(str.substring(13, 15))).getTime();
                parsePosition.setIndex(15);
                return time;
            } catch (RuntimeException unused) {
                return null;
            }
        }
    }

    private static class DateFormat extends CalendarDateFormat {
        private static final long serialVersionUID = -7626077667268431779L;

        public DateFormat(String str) {
            super(str);
        }

        @Override // java.text.DateFormat
        public StringBuffer format(java.util.Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            java.util.Calendar calendar = java.util.Calendar.getInstance(getTimeZone());
            calendar.setTimeInMillis(date.getTime());
            CalendarDateFormatFactory.appendPadded(stringBuffer, calendar.get(1), 4);
            CalendarDateFormatFactory.appendPadded(stringBuffer, calendar.get(2) + 1, 2);
            CalendarDateFormatFactory.appendPadded(stringBuffer, calendar.get(5), 2);
            return stringBuffer;
        }

        @Override // java.text.DateFormat
        public java.util.Date parse(String str, ParsePosition parsePosition) throws NumberFormatException {
            if (str.length() > 8 && !isLenient()) {
                parsePosition.setErrorIndex(8);
                return null;
            }
            try {
                java.util.Date time = CalendarDateFormatFactory.makeCalendar(isLenient(), getTimeZone(), Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6)) - 1, Integer.parseInt(str.substring(6, 8))).getTime();
                parsePosition.setIndex(8);
                return time;
            } catch (RuntimeException unused) {
                return null;
            }
        }
    }

    private static class TimeFormat extends CalendarDateFormat {
        private static final long serialVersionUID = -1367114409994225425L;
        final boolean patternEndsWithZ;

        public TimeFormat(String str) {
            super(str);
            this.patternEndsWithZ = str.endsWith("'Z'");
        }

        @Override // java.text.DateFormat
        public StringBuffer format(java.util.Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar(getTimeZone());
            gregorianCalendar.setTimeInMillis(date.getTime());
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(11), 2);
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(12), 2);
            CalendarDateFormatFactory.appendPadded(stringBuffer, gregorianCalendar.get(13), 2);
            if (this.patternEndsWithZ) {
                stringBuffer.append("Z");
            }
            return stringBuffer;
        }

        @Override // java.text.DateFormat
        public java.util.Date parse(String str, ParsePosition parsePosition) throws NumberFormatException {
            if (this.patternEndsWithZ) {
                if (str.length() > 9 && !isLenient()) {
                    parsePosition.setErrorIndex(9);
                    return null;
                }
            } else if (str.length() > 6 && !isLenient()) {
                parsePosition.setErrorIndex(6);
                return null;
            }
            try {
                if (this.patternEndsWithZ && str.charAt(6) != 'Z') {
                    parsePosition.setErrorIndex(6);
                    return null;
                }
                java.util.Date time = CalendarDateFormatFactory.makeCalendar(isLenient(), getTimeZone(), R2.dimen.mtrl_textinput_box_corner_radius_small, 0, 1, Integer.parseInt(str.substring(0, 2)), Integer.parseInt(str.substring(2, 4)), Integer.parseInt(str.substring(4, 6))).getTime();
                parsePosition.setIndex(6);
                return time;
            } catch (RuntimeException unused) {
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.Calendar makeCalendar(boolean z, java.util.TimeZone timeZone, int i, int i2, int i3, int i4, int i5, int i6) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
        gregorianCalendar.setLenient(z);
        gregorianCalendar.set(i, i2, i3, i4, i5, i6);
        gregorianCalendar.set(14, 0);
        return gregorianCalendar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.Calendar makeCalendar(boolean z, java.util.TimeZone timeZone, int i, int i2, int i3) {
        return makeCalendar(z, timeZone, i, i2, i3, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendPadded(StringBuffer stringBuffer, int i, int i2) {
        String string = Integer.toString(i);
        int length = i2 - string.length();
        for (int i3 = 0; i3 < length; i3++) {
            stringBuffer.append("0");
        }
        stringBuffer.append(string);
    }
}
