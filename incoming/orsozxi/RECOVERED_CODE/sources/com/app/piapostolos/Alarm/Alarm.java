package com.app.piapostolos.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import com.app.piapostolos.Alarm.Alert.AlarmAlertBroadcastReciever;
import com.app.piapostolos.R;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class Alarm implements Serializable {
    private static final long serialVersionUID = 8699489847426803789L;
    private int id;
    private Boolean alarmActive = true;
    private Calendar alarmTime = Calendar.getInstance();
    private Day[] days = {Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY};
    private String alarmTonePath = RingtoneManager.getDefaultUri(4).toString();
    private Boolean vibrate = true;
    private String alarmName = "Alarm Clock";
    private Difficulty difficulty = Difficulty.EASY;

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD;

        @Override // java.lang.Enum
        public String toString() {
            int iOrdinal = ordinal();
            return iOrdinal != 0 ? iOrdinal != 1 ? iOrdinal != 2 ? super.toString() : "Hard" : "Medium" : "Easy";
        }
    }

    public enum Day {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        Day;

        @Override // java.lang.Enum
        public String toString() {
            switch (this) {
                case SUNDAY:
                    return "Sunday";
                case MONDAY:
                    return "Monday";
                case TUESDAY:
                    return "Tuesday";
                case WEDNESDAY:
                    return "Wednesday";
                case THURSDAY:
                    return "Thursday";
                case FRIDAY:
                    return "Friday";
                case SATURDAY:
                    return "Saturday";
                default:
                    return super.toString();
            }
        }
    }

    public Boolean getAlarmActive() {
        return this.alarmActive;
    }

    public void setAlarmActive(Boolean bool) {
        this.alarmActive = bool;
    }

    public Calendar getAlarmTime() {
        if (this.alarmTime.before(Calendar.getInstance())) {
            this.alarmTime.add(5, 1);
        }
        while (!Arrays.asList(getDays()).contains(Day.values()[this.alarmTime.get(7) - 1])) {
            this.alarmTime.add(5, 1);
        }
        return this.alarmTime;
    }

    public String getAlarmTimeString() {
        String str = ((this.alarmTime.get(11) <= 9 ? "0" : "") + String.valueOf(this.alarmTime.get(11))) + ":";
        if (this.alarmTime.get(12) <= 9) {
            str = str + "0";
        }
        return str + String.valueOf(this.alarmTime.get(12));
    }

    public void setAlarmTime(Calendar calendar) {
        this.alarmTime = calendar;
    }

    public void setAlarmTime(String str) {
        String[] strArrSplit = str.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, Integer.parseInt(strArrSplit[0]));
        calendar.set(12, Integer.parseInt(strArrSplit[1]));
        calendar.set(13, 0);
        setAlarmTime(calendar);
    }

    public Day[] getDays() {
        return this.days;
    }

    public void setDays(Day[] dayArr) {
        this.days = dayArr;
    }

    public void addDay(Day day) {
        boolean z = false;
        for (Day day2 : getDays()) {
            if (day2.equals(day)) {
                z = true;
            }
        }
        if (z) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (Day day3 : getDays()) {
            linkedList.add(day3);
        }
        linkedList.add(day);
        setDays((Day[]) linkedList.toArray(new Day[linkedList.size()]));
    }

    public void removeDay(Day day) {
        LinkedList linkedList = new LinkedList();
        for (Day day2 : getDays()) {
            if (!day2.equals(day)) {
                linkedList.add(day2);
            }
        }
        setDays((Day[]) linkedList.toArray(new Day[linkedList.size()]));
    }

    public String getAlarmTonePath() {
        return this.alarmTonePath;
    }

    public void setAlarmTonePath(String str) {
        this.alarmTonePath = str;
    }

    public Boolean getVibrate() {
        return this.vibrate;
    }

    public void setVibrate(Boolean bool) {
        this.vibrate = bool;
    }

    public String getAlarmName() {
        return this.alarmName;
    }

    public void setAlarmName(String str) {
        this.alarmName = str;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getRepeatDaysString(Context context) {
        StringBuilder sb = new StringBuilder();
        if (getDays().length == Day.values().length) {
            sb.append(context.getString(R.string.every_day));
        } else {
            Arrays.sort(getDays(), new Comparator<Day>() { // from class: com.app.piapostolos.Alarm.Alarm.1
                @Override // java.util.Comparator
                public int compare(Day day, Day day2) {
                    return day.ordinal() - day2.ordinal();
                }
            });
            for (Day day : getDays()) {
                switch (AnonymousClass2.$SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[day.ordinal()]) {
                    case 1:
                        sb.append(getDayName(day.toString(), context));
                        break;
                    case 2:
                        sb.append(getDayName(day.toString(), context));
                        break;
                    case 3:
                        sb.append(getDayName(day.toString(), context));
                        break;
                    case 4:
                        sb.append(getDayName(day.toString(), context));
                        break;
                    case 5:
                        sb.append(getDayName(day.toString(), context));
                        break;
                    case 6:
                        sb.append(getDayName(day.toString(), context));
                        break;
                    case 7:
                        sb.append(getDayName(day.toString(), context));
                        break;
                }
                sb.append(',');
            }
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /* renamed from: com.app.piapostolos.Alarm.Alarm$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day;

        static {
            int[] iArr = new int[Day.values().length];
            $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day = iArr;
            try {
                iArr[Day.SUNDAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[Day.MONDAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[Day.TUESDAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[Day.WEDNESDAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[Day.THURSDAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[Day.FRIDAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Alarm$Day[Day.SATURDAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private String getDayName(String str, Context context) {
        if (str.equals("Sunday")) {
            return context.getString(R.string.sun);
        }
        if (str.equals("Monday")) {
            return context.getString(R.string.mon);
        }
        if (str.equals("Tuesday")) {
            return context.getString(R.string.tues);
        }
        if (str.equals("Wednesday")) {
            return context.getString(R.string.wed);
        }
        if (str.equals("Thursday")) {
            return context.getString(R.string.thurs);
        }
        if (str.equals("Friday")) {
            return context.getString(R.string.fri);
        }
        if (str.equals("Saturday")) {
            return context.getString(R.string.satur);
        }
        return null;
    }

    public void schedule(Context context) {
        PendingIntent broadcast;
        setAlarmActive(true);
        Intent intent = new Intent(context, (Class<?>) AlarmAlertBroadcastReciever.class);
        intent.putExtra("alarm", new Gson().toJson(this));
        if (Build.VERSION.SDK_INT >= 23) {
            broadcast = PendingIntent.getBroadcast(context, 0, intent, 335544320);
        } else {
            broadcast = PendingIntent.getBroadcast(context, 0, intent, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(0, getAlarmTime().getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(0, getAlarmTime().getTimeInMillis(), broadcast);
        } else {
            alarmManager.set(0, getAlarmTime().getTimeInMillis(), broadcast);
        }
    }

    public String getTimeUntilNextAlarmMessage(Context context) {
        long timeInMillis = getAlarmTime().getTimeInMillis() - System.currentTimeMillis();
        long j = timeInMillis / 86400000;
        long j2 = 24 * j;
        long j3 = (timeInMillis / 3600000) - j2;
        long j4 = j2 * 60;
        long j5 = j3 * 60;
        long j6 = ((timeInMillis / 60000) - j4) - j5;
        long j7 = (((timeInMillis / 1000) - (j4 * 60)) - (j5 * 60)) - (60 * j6);
        String string = context.getString(R.string.alarm_time);
        if (j > 0) {
            return string + String.format("%d " + context.getString(R.string.day) + ", %d " + context.getString(R.string.hour) + ", %d " + context.getString(R.string.minute) + " " + context.getString(R.string.and) + " %d " + context.getString(R.string.second), Long.valueOf(j), Long.valueOf(j3), Long.valueOf(j6), Long.valueOf(j7));
        }
        if (j3 > 0) {
            return string + String.format("%d " + context.getString(R.string.hour) + ", %d " + context.getString(R.string.minute) + " " + context.getString(R.string.and) + " %d " + context.getString(R.string.second), Long.valueOf(j3), Long.valueOf(j6), Long.valueOf(j7));
        }
        if (j6 > 0) {
            return string + String.format("%d " + context.getString(R.string.minute) + " " + context.getString(R.string.and) + " %d " + context.getString(R.string.second), Long.valueOf(j6), Long.valueOf(j7));
        }
        return string + String.format("%d " + context.getString(R.string.second), Long.valueOf(j7));
    }
}
