package net.fortuna.ical4j.model;

import java.io.Serializable;
import java.util.Objects;
import net.fortuna.ical4j.util.Numbers;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class WeekDay implements Serializable {
    private static final long serialVersionUID = -4412000990022011469L;
    private Day day;
    private int offset;
    public static final WeekDay SU = new WeekDay(Day.SU, 0);
    public static final WeekDay MO = new WeekDay(Day.MO, 0);
    public static final WeekDay TU = new WeekDay(Day.TU, 0);
    public static final WeekDay WE = new WeekDay(Day.WE, 0);
    public static final WeekDay TH = new WeekDay(Day.TH, 0);
    public static final WeekDay FR = new WeekDay(Day.FR, 0);
    public static final WeekDay SA = new WeekDay(Day.SA, 0);

    public enum Day {
        SU,
        MO,
        TU,
        WE,
        TH,
        FR,
        SA
    }

    public WeekDay(String str) {
        if (str.length() > 2) {
            this.offset = Numbers.parseInt(str.substring(0, str.length() - 2));
        } else {
            this.offset = 0;
        }
        this.day = Day.valueOf(str.substring(str.length() - 2));
        validateDay();
    }

    private WeekDay(Day day, int i) {
        this.day = day;
        this.offset = i;
    }

    public WeekDay(WeekDay weekDay, int i) {
        this.day = weekDay.getDay();
        this.offset = i;
    }

    private void validateDay() {
        if (SU.day.equals(this.day) || MO.day.equals(this.day) || TU.day.equals(this.day) || WE.day.equals(this.day) || TH.day.equals(this.day) || FR.day.equals(this.day) || SA.day.equals(this.day)) {
            return;
        }
        throw new IllegalArgumentException("Invalid day: " + this.day);
    }

    public final Day getDay() {
        return this.day;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (getOffset() != 0) {
            sb.append(getOffset());
        }
        sb.append(getDay());
        return sb.toString();
    }

    /* renamed from: net.fortuna.ical4j.model.WeekDay$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day;

        static {
            int[] iArr = new int[Day.values().length];
            $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day = iArr;
            try {
                iArr[Day.SU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[Day.MO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[Day.TU.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[Day.WE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[Day.TH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[Day.FR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[Day.SA.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static WeekDay getWeekDay(Day day) {
        switch (AnonymousClass1.$SwitchMap$net$fortuna$ical4j$model$WeekDay$Day[day.ordinal()]) {
            case 1:
                return SU;
            case 2:
                return MO;
            case 3:
                return TU;
            case 4:
                return WE;
            case 5:
                return TH;
            case 6:
                return FR;
            case 7:
                return SA;
            default:
                return null;
        }
    }

    public static WeekDay getWeekDay(java.util.Calendar calendar) {
        return new WeekDay(getDay(calendar.get(7)), 0);
    }

    public static WeekDay getMonthlyOffset(java.util.Calendar calendar) {
        return new WeekDay(getDay(calendar.get(7)), calendar.get(8));
    }

    public static WeekDay getNegativeMonthlyOffset(java.util.Calendar calendar) {
        return new WeekDay(getDay(calendar.get(7)), calendar.get(8) - 6);
    }

    public static WeekDay getDay(int i) {
        switch (i) {
            case 1:
                return SU;
            case 2:
                return MO;
            case 3:
                return TU;
            case 4:
                return WE;
            case 5:
                return TH;
            case 6:
                return FR;
            case 7:
                return SA;
            default:
                return null;
        }
    }

    public static int getCalendarDay(WeekDay weekDay) {
        if (SU.getDay().equals(weekDay.getDay())) {
            return 1;
        }
        if (MO.getDay().equals(weekDay.getDay())) {
            return 2;
        }
        if (TU.getDay().equals(weekDay.getDay())) {
            return 3;
        }
        if (WE.getDay().equals(weekDay.getDay())) {
            return 4;
        }
        if (TH.getDay().equals(weekDay.getDay())) {
            return 5;
        }
        if (FR.getDay().equals(weekDay.getDay())) {
            return 6;
        }
        return SA.getDay().equals(weekDay.getDay()) ? 7 : -1;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof WeekDay)) {
            return false;
        }
        WeekDay weekDay = (WeekDay) obj;
        return Objects.equals(weekDay.getDay(), getDay()) && weekDay.getOffset() == getOffset();
    }

    public final int hashCode() {
        return new HashCodeBuilder().append(getDay()).append(getOffset()).toHashCode();
    }
}
