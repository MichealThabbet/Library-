package net.fortuna.ical4j.model;

import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.StringTokenizer;
import net.fortuna.ical4j.util.Dates;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Marker;

@Deprecated
/* loaded from: classes3.dex */
public class Dur implements Comparable<Dur>, Serializable {
    private static final int DAYS_PER_WEEK = 7;
    private static final int DAYS_PER_YEAR = 365;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final long serialVersionUID = 5013232281547134583L;
    private int days;
    private int hours;
    private int minutes;
    private boolean negative;
    private int seconds;
    private int weeks;

    public Dur(String str) {
        this.negative = false;
        this.weeks = 0;
        this.days = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        StringTokenizer stringTokenizer = new StringTokenizer(str, "+-PWDTHMS", true);
        String str2 = null;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (Marker.ANY_NON_NULL_MARKER.equals(strNextToken)) {
                this.negative = false;
            } else if (LanguageTag.SEP.equals(strNextToken)) {
                this.negative = true;
            } else if ("W".equals(strNextToken)) {
                this.weeks = Integer.parseInt(str2);
            } else if ("D".equals(strNextToken)) {
                this.days = Integer.parseInt(str2);
            } else if (DateFormat.HOUR24.equals(strNextToken)) {
                this.hours = Integer.parseInt(str2);
            } else if (DateFormat.NUM_MONTH.equals(strNextToken)) {
                this.minutes = Integer.parseInt(str2);
            } else if ("S".equals(strNextToken)) {
                this.seconds = Integer.parseInt(str2);
            }
            str2 = strNextToken;
        }
    }

    public Dur(int i) {
        this.weeks = Math.abs(i);
        this.days = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.negative = i < 0;
    }

    public Dur(int i, int i2, int i3, int i4) {
        if ((i < 0 || i2 < 0 || i3 < 0 || i4 < 0) && (i > 0 || i2 > 0 || i3 > 0 || i4 > 0)) {
            throw new IllegalArgumentException("Invalid duration representation");
        }
        this.weeks = 0;
        this.days = Math.abs(i);
        this.hours = Math.abs(i2);
        this.minutes = Math.abs(i3);
        this.seconds = Math.abs(i4);
        this.negative = i < 0 || i2 < 0 || i3 < 0 || i4 < 0;
    }

    public Dur(java.util.Date date, java.util.Date date2) {
        java.util.Calendar calendar;
        boolean z = date.compareTo(date2) > 0;
        this.negative = z;
        if (z) {
            date2 = date;
            date = date2;
        }
        if (date instanceof Date) {
            calendar = Dates.getCalendarInstance((Date) date);
        } else {
            calendar = java.util.Calendar.getInstance();
        }
        calendar.setTime(date);
        java.util.Calendar calendar2 = java.util.Calendar.getInstance(calendar.getTimeZone());
        calendar2.setTime(date2);
        long j = 0;
        int i = calendar2.get(1);
        int i2 = calendar.get(1);
        while (true) {
            int i3 = i - i2;
            if (i3 <= 0) {
                break;
            }
            int i4 = i3 * 365;
            calendar.add(5, i4);
            j += i4;
            i = calendar2.get(1);
            i2 = calendar.get(1);
        }
        long j2 = ((((((j + (calendar2.get(6) - calendar.get(6))) * 24) + (calendar2.get(11) - calendar.get(11))) * 60) + (calendar2.get(12) - calendar.get(12))) * 60) + (calendar2.get(13) - calendar.get(13));
        int i5 = (int) (j2 % 60);
        this.seconds = i5;
        long j3 = j2 / 60;
        int i6 = (int) (j3 % 60);
        this.minutes = i6;
        long j4 = j3 / 60;
        int i7 = (int) (j4 % 24);
        this.hours = i7;
        int i8 = (int) (j4 / 24);
        this.days = i8;
        this.weeks = 0;
        if (i5 == 0 && i6 == 0 && i7 == 0 && i8 % 7 == 0) {
            this.weeks = i8 / 7;
            this.days = 0;
        }
    }

    public final java.util.Date getTime(java.util.Date date) {
        java.util.Calendar calendar;
        if (date instanceof Date) {
            calendar = Dates.getCalendarInstance((Date) date);
        } else {
            calendar = java.util.Calendar.getInstance();
        }
        calendar.setTime(date);
        if (isNegative()) {
            calendar.add(3, -this.weeks);
            calendar.add(7, -this.days);
            calendar.add(11, -this.hours);
            calendar.add(12, -this.minutes);
            calendar.add(13, -this.seconds);
        } else {
            calendar.add(3, this.weeks);
            calendar.add(7, this.days);
            calendar.add(11, this.hours);
            calendar.add(12, this.minutes);
            calendar.add(13, this.seconds);
        }
        return calendar.getTime();
    }

    public final Dur negate() {
        Dur dur = new Dur(this.days, this.hours, this.minutes, this.seconds);
        dur.weeks = this.weeks;
        dur.negative = !this.negative;
        return dur;
    }

    public final Dur add(Dur dur) {
        int i;
        int i2;
        int i3;
        Dur dur2;
        int i4;
        if ((!isNegative() && dur.isNegative()) || (isNegative() && !dur.isNegative())) {
            throw new IllegalArgumentException("Cannot add a negative and a positive duration");
        }
        int i5 = this.weeks;
        if (i5 > 0 && (i4 = dur.weeks) > 0) {
            dur2 = new Dur(i5 + i4);
        } else {
            int i6 = this.weeks;
            int i7 = i6 > 0 ? (i6 * 7) + this.days : this.days;
            int i8 = this.hours;
            int i9 = this.minutes;
            int i10 = this.seconds;
            int i11 = dur.seconds;
            if ((i10 + i11) / 60 > 0) {
                i9 += (i10 + i11) / 60;
                i = (i10 + i11) % 60;
            } else {
                i = i10 + i11;
            }
            int i12 = dur.minutes;
            if ((i9 + i12) / 60 > 0) {
                i8 += (i9 + i12) / 60;
                i2 = (i9 + i12) % 60;
            } else {
                i2 = i9 + i12;
            }
            int i13 = dur.hours;
            if ((i8 + i13) / 24 > 0) {
                i7 += (i8 + i13) / 24;
                i3 = (i8 + i13) % 24;
            } else {
                i3 = i8 + i13;
            }
            int i14 = dur.weeks;
            dur2 = new Dur(i7 + (i14 > 0 ? (i14 * 7) + dur.days : dur.days), i3, i2, i);
        }
        dur2.negative = this.negative;
        return dur2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.negative) {
            sb.append(Soundex.SILENT_MARKER);
        }
        sb.append('P');
        int i = this.weeks;
        if (i > 0) {
            sb.append(i);
            sb.append('W');
        } else {
            int i2 = this.days;
            if (i2 > 0) {
                sb.append(i2);
                sb.append('D');
            }
            if (this.hours > 0 || this.minutes > 0 || this.seconds > 0) {
                sb.append('T');
                int i3 = this.hours;
                if (i3 > 0) {
                    sb.append(i3);
                    sb.append('H');
                }
                int i4 = this.minutes;
                if (i4 > 0) {
                    sb.append(i4);
                    sb.append('M');
                }
                int i5 = this.seconds;
                if (i5 > 0) {
                    sb.append(i5);
                    sb.append('S');
                }
            }
            if (this.hours + this.minutes + this.seconds + this.days + this.weeks == 0) {
                sb.append("T0S");
            }
        }
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Dur dur) {
        int seconds;
        int seconds2;
        if (isNegative() != dur.isNegative()) {
            return isNegative() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        if (getWeeks() != dur.getWeeks()) {
            seconds = getWeeks();
            seconds2 = dur.getWeeks();
        } else if (getDays() != dur.getDays()) {
            seconds = getDays();
            seconds2 = dur.getDays();
        } else if (getHours() != dur.getHours()) {
            seconds = getHours();
            seconds2 = dur.getHours();
        } else if (getMinutes() != dur.getMinutes()) {
            seconds = getMinutes();
            seconds2 = dur.getMinutes();
        } else {
            seconds = getSeconds();
            seconds2 = dur.getSeconds();
        }
        int i = seconds - seconds2;
        return isNegative() ? -i : i;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Dur) {
            return ((Dur) obj).compareTo(this) == 0;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.weeks).append(this.days).append(this.hours).append(this.minutes).append(this.seconds).append(this.negative).toHashCode();
    }

    public final int getDays() {
        return this.days;
    }

    public final int getHours() {
        return this.hours;
    }

    public final int getMinutes() {
        return this.minutes;
    }

    public final boolean isNegative() {
        return this.negative;
    }

    public final int getSeconds() {
        return this.seconds;
    }

    public final int getWeeks() {
        return this.weeks;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
    }
}
