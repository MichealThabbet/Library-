package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.transform.Transformer;
import net.fortuna.ical4j.transform.recurrence.ByDayRule;
import net.fortuna.ical4j.transform.recurrence.ByHourRule;
import net.fortuna.ical4j.transform.recurrence.ByMinuteRule;
import net.fortuna.ical4j.transform.recurrence.ByMonthDayRule;
import net.fortuna.ical4j.transform.recurrence.ByMonthRule;
import net.fortuna.ical4j.transform.recurrence.BySecondRule;
import net.fortuna.ical4j.transform.recurrence.BySetPosRule;
import net.fortuna.ical4j.transform.recurrence.ByWeekNoRule;
import net.fortuna.ical4j.transform.recurrence.ByYearDayRule;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Configurator;
import net.fortuna.ical4j.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class Recur implements Serializable {
    private static final String BYDAY = "BYDAY";
    private static final String BYHOUR = "BYHOUR";
    private static final String BYMINUTE = "BYMINUTE";
    private static final String BYMONTH = "BYMONTH";
    private static final String BYMONTHDAY = "BYMONTHDAY";
    private static final String BYSECOND = "BYSECOND";
    private static final String BYSETPOS = "BYSETPOS";
    private static final String BYWEEKNO = "BYWEEKNO";
    private static final String BYYEARDAY = "BYYEARDAY";
    private static final String COUNT = "COUNT";

    @Deprecated
    public static final String DAILY = "DAILY";
    private static final String FREQ = "FREQ";

    @Deprecated
    public static final String HOURLY = "HOURLY";
    private static final String INTERVAL = "INTERVAL";

    @Deprecated
    public static final String MINUTELY = "MINUTELY";

    @Deprecated
    public static final String MONTHLY = "MONTHLY";

    @Deprecated
    public static final String SECONDLY = "SECONDLY";
    private static final String UNTIL = "UNTIL";

    @Deprecated
    public static final String WEEKLY = "WEEKLY";
    private static final String WKST = "WKST";

    @Deprecated
    public static final String YEARLY = "YEARLY";
    private static final long serialVersionUID = -7333226591784095142L;
    private int calIncField;
    private int calendarWeekStartDay;
    private Integer count;
    private WeekDayList dayList;
    private Map<String, String> experimentalValues;
    private Frequency frequency;
    private NumberList hourList;
    private Integer interval;
    private transient Logger log;
    private NumberList minuteList;
    private NumberList monthDayList;
    private NumberList monthList;
    private NumberList secondList;
    private NumberList setPosList;
    private Map<String, Transformer<DateList>> transformers;
    private Date until;
    private NumberList weekNoList;
    private WeekDay.Day weekStartDay;
    private NumberList yearDayList;
    public static final String KEY_MAX_INCREMENT_COUNT = "net.fortuna.ical4j.recur.maxincrementcount";
    private static int maxIncrementCount = Configurator.getIntProperty(KEY_MAX_INCREMENT_COUNT).orElse(1000).intValue();

    public enum Frequency {
        SECONDLY,
        MINUTELY,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

    private Recur() {
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
        this.experimentalValues = new HashMap();
        this.calendarWeekStartDay = 2;
        initTransformers();
    }

    public Recur(String str) throws ParseException {
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
        this.experimentalValues = new HashMap();
        this.calendarWeekStartDay = 2;
        Iterator<String> it2 = Arrays.asList(str.split("[;=]")).iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            if (FREQ.equals(next)) {
                this.frequency = Frequency.valueOf(nextToken(it2, next));
            } else if (UNTIL.equals(next)) {
                String strNextToken = nextToken(it2, next);
                if (strNextToken != null && strNextToken.contains("T")) {
                    DateTime dateTime = new DateTime(strNextToken);
                    this.until = dateTime;
                    dateTime.setUtc(true);
                } else {
                    this.until = new Date(strNextToken);
                }
            } else if (COUNT.equals(next)) {
                this.count = Integer.valueOf(Integer.parseInt(nextToken(it2, next)));
            } else if (INTERVAL.equals(next)) {
                this.interval = Integer.valueOf(Integer.parseInt(nextToken(it2, next)));
            } else if (BYSECOND.equals(next)) {
                this.secondList = new NumberList(nextToken(it2, next), 0, 59, false);
            } else if (BYMINUTE.equals(next)) {
                this.minuteList = new NumberList(nextToken(it2, next), 0, 59, false);
            } else if (BYHOUR.equals(next)) {
                this.hourList = new NumberList(nextToken(it2, next), 0, 23, false);
            } else if (BYDAY.equals(next)) {
                this.dayList = new WeekDayList(nextToken(it2, next));
            } else if (BYMONTHDAY.equals(next)) {
                this.monthDayList = new NumberList(nextToken(it2, next), 1, 31, true);
            } else if (BYYEARDAY.equals(next)) {
                this.yearDayList = new NumberList(nextToken(it2, next), 1, 366, true);
            } else if (BYWEEKNO.equals(next)) {
                this.weekNoList = new NumberList(nextToken(it2, next), 1, 53, true);
            } else if (BYMONTH.equals(next)) {
                this.monthList = new NumberList(nextToken(it2, next), 1, 12, false);
            } else if (BYSETPOS.equals(next)) {
                this.setPosList = new NumberList(nextToken(it2, next), 1, 366, true);
            } else if (WKST.equals(next)) {
                WeekDay.Day dayValueOf = WeekDay.Day.valueOf(nextToken(it2, next));
                this.weekStartDay = dayValueOf;
                this.calendarWeekStartDay = WeekDay.getCalendarDay(WeekDay.getWeekDay(dayValueOf));
            } else if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                this.experimentalValues.put(next, nextToken(it2, next));
            } else {
                throw new IllegalArgumentException(String.format("Invalid recurrence rule part: %s=%s", next, nextToken(it2, next)));
            }
        }
        validateFrequency();
        initTransformers();
    }

    private String nextToken(Iterator<String> it2, String str) {
        try {
            return it2.next();
        } catch (NoSuchElementException unused) {
            throw new IllegalArgumentException("Missing expected token, last token: " + str);
        }
    }

    @Deprecated
    public Recur(String str, Date date) {
        this(Frequency.valueOf(str), date);
    }

    public Recur(Frequency frequency, Date date) {
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
        this.experimentalValues = new HashMap();
        this.calendarWeekStartDay = 2;
        this.frequency = frequency;
        this.until = date;
        validateFrequency();
        initTransformers();
    }

    @Deprecated
    public Recur(String str, int i) {
        this(Frequency.valueOf(str), i);
    }

    public Recur(Frequency frequency, int i) {
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
        this.experimentalValues = new HashMap();
        this.calendarWeekStartDay = 2;
        this.frequency = frequency;
        this.count = Integer.valueOf(i);
        validateFrequency();
        initTransformers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTransformers() {
        HashMap map = new HashMap();
        this.transformers = map;
        NumberList numberList = this.secondList;
        if (numberList != null) {
            map.put(BYSECOND, new BySecondRule(numberList, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.secondList = new NumberList(0, 59, false);
        }
        NumberList numberList2 = this.minuteList;
        if (numberList2 != null) {
            this.transformers.put(BYMINUTE, new ByMinuteRule(numberList2, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.minuteList = new NumberList(0, 59, false);
        }
        NumberList numberList3 = this.hourList;
        if (numberList3 != null) {
            this.transformers.put(BYHOUR, new ByHourRule(numberList3, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.hourList = new NumberList(0, 23, false);
        }
        NumberList numberList4 = this.monthDayList;
        if (numberList4 != null) {
            this.transformers.put(BYMONTHDAY, new ByMonthDayRule(numberList4, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.monthDayList = new NumberList(1, 31, true);
        }
        NumberList numberList5 = this.yearDayList;
        if (numberList5 != null) {
            this.transformers.put(BYYEARDAY, new ByYearDayRule(numberList5, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.yearDayList = new NumberList(1, 366, true);
        }
        NumberList numberList6 = this.weekNoList;
        if (numberList6 != null) {
            this.transformers.put(BYWEEKNO, new ByWeekNoRule(numberList6, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.weekNoList = new NumberList(1, 53, true);
        }
        NumberList numberList7 = this.monthList;
        if (numberList7 != null) {
            this.transformers.put(BYMONTH, new ByMonthRule(numberList7, this.frequency, Optional.ofNullable(this.weekStartDay)));
        } else {
            this.monthList = new NumberList(1, 12, false);
        }
        WeekDayList weekDayList = this.dayList;
        if (weekDayList != null) {
            this.transformers.put(BYDAY, new ByDayRule(weekDayList, deriveFilterType(), Optional.ofNullable(this.weekStartDay)));
        } else {
            this.dayList = new WeekDayList();
        }
        NumberList numberList8 = this.setPosList;
        if (numberList8 != null) {
            this.transformers.put(BYSETPOS, new BySetPosRule(numberList8));
        } else {
            this.setPosList = new NumberList(1, 366, true);
        }
    }

    private Frequency deriveFilterType() {
        if (this.frequency == Frequency.DAILY || !getYearDayList().isEmpty() || !getMonthDayList().isEmpty()) {
            return Frequency.DAILY;
        }
        if (this.frequency == Frequency.WEEKLY || !getWeekNoList().isEmpty()) {
            return Frequency.WEEKLY;
        }
        if (this.frequency == Frequency.MONTHLY || !getMonthList().isEmpty()) {
            return Frequency.MONTHLY;
        }
        if (this.frequency == Frequency.YEARLY) {
            return Frequency.YEARLY;
        }
        return null;
    }

    public final WeekDayList getDayList() {
        return this.dayList;
    }

    public final NumberList getHourList() {
        return this.hourList;
    }

    public final NumberList getMinuteList() {
        return this.minuteList;
    }

    public final NumberList getMonthDayList() {
        return this.monthDayList;
    }

    public final NumberList getMonthList() {
        return this.monthList;
    }

    public final NumberList getSecondList() {
        return this.secondList;
    }

    public final NumberList getSetPosList() {
        return this.setPosList;
    }

    public final NumberList getWeekNoList() {
        return this.weekNoList;
    }

    public final NumberList getYearDayList() {
        return this.yearDayList;
    }

    public final int getCount() {
        return ((Integer) Optional.ofNullable(this.count).orElse(-1)).intValue();
    }

    public final Map<String, String> getExperimentalValues() {
        return this.experimentalValues;
    }

    public final Frequency getFrequency() {
        return this.frequency;
    }

    public final int getInterval() {
        return ((Integer) Optional.ofNullable(this.interval).orElse(-1)).intValue();
    }

    public final Date getUntil() {
        return this.until;
    }

    public final WeekDay.Day getWeekStartDay() {
        return this.weekStartDay;
    }

    @Deprecated
    public final void setWeekStartDay(WeekDay.Day day) {
        this.weekStartDay = day;
        if (day != null) {
            this.calendarWeekStartDay = WeekDay.getCalendarDay(WeekDay.getWeekDay(day));
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FREQ);
        sb.append('=');
        sb.append(this.frequency);
        if (this.weekStartDay != null) {
            sb.append(';');
            sb.append(WKST);
            sb.append('=');
            sb.append(this.weekStartDay);
        }
        if (this.until != null) {
            sb.append(';');
            sb.append(UNTIL);
            sb.append('=');
            sb.append(this.until);
        }
        if (this.count != null) {
            sb.append(';');
            sb.append(COUNT);
            sb.append('=');
            sb.append(this.count);
        }
        if (this.interval != null) {
            sb.append(';');
            sb.append(INTERVAL);
            sb.append('=');
            sb.append(this.interval);
        }
        if (!this.monthList.isEmpty()) {
            sb.append(';');
            sb.append(BYMONTH);
            sb.append('=');
            sb.append(this.monthList);
        }
        if (!this.weekNoList.isEmpty()) {
            sb.append(';');
            sb.append(BYWEEKNO);
            sb.append('=');
            sb.append(this.weekNoList);
        }
        if (!this.yearDayList.isEmpty()) {
            sb.append(';');
            sb.append(BYYEARDAY);
            sb.append('=');
            sb.append(this.yearDayList);
        }
        if (!this.monthDayList.isEmpty()) {
            sb.append(';');
            sb.append(BYMONTHDAY);
            sb.append('=');
            sb.append(this.monthDayList);
        }
        if (!this.dayList.isEmpty()) {
            sb.append(';');
            sb.append(BYDAY);
            sb.append('=');
            sb.append(this.dayList);
        }
        if (!this.hourList.isEmpty()) {
            sb.append(';');
            sb.append(BYHOUR);
            sb.append('=');
            sb.append(this.hourList);
        }
        if (!this.minuteList.isEmpty()) {
            sb.append(';');
            sb.append(BYMINUTE);
            sb.append('=');
            sb.append(this.minuteList);
        }
        if (!this.secondList.isEmpty()) {
            sb.append(';');
            sb.append(BYSECOND);
            sb.append('=');
            sb.append(this.secondList);
        }
        if (!this.setPosList.isEmpty()) {
            sb.append(';');
            sb.append(BYSETPOS);
            sb.append('=');
            sb.append(this.setPosList);
        }
        return sb.toString();
    }

    public final DateList getDates(Date date, Date date2, Value value) {
        return getDates(date, date, date2, value, -1);
    }

    public final DateList getDates(Date date, Period period, Value value) {
        return getDates(date, period.getStart(), period.getEnd(), value, -1);
    }

    public final DateList getDates(Date date, Date date2, Date date3, Value value) {
        return getDates(date, date2, date3, value, -1);
    }

    public final DateList getDates(Date date, Date date2, Date date3, Value value, int i) {
        DateList dateList = new DateList(value);
        if (date instanceof DateTime) {
            DateTime dateTime = (DateTime) date;
            if (dateTime.isUtc()) {
                dateList.setUtc(true);
            } else {
                dateList.setTimeZone(dateTime.getTimeZone());
            }
        }
        java.util.Calendar calendarInstance = getCalendarInstance(date, true);
        java.util.Calendar calendar = (java.util.Calendar) calendarInstance.clone();
        if (this.count == null) {
            java.util.Calendar calendarSmartIncrement = (java.util.Calendar) calendarInstance.clone();
            while (calendarSmartIncrement.getTime().before(date2)) {
                calendarInstance.setTime(calendarSmartIncrement.getTime());
                calendarSmartIncrement = smartIncrement(calendarSmartIncrement);
                if (calendarSmartIncrement == null) {
                    return dateList;
                }
            }
        }
        HashSet hashSet = new HashSet();
        Date next = null;
        int i2 = 0;
        do {
            if (i >= 0 && dateList.size() >= i) {
                break;
            }
            Date dates = Dates.getInstance(calendarInstance.getTime(), value);
            if ((getUntil() != null && next != null && next.after(getUntil())) || ((date3 != null && next != null && next.after(date3)) || (getCount() >= 1 && dateList.size() + hashSet.size() >= getCount()))) {
                break;
            }
            if (dates instanceof DateTime) {
                if (dateList.isUtc()) {
                    ((DateTime) dates).setUtc(true);
                } else {
                    ((DateTime) dates).setTimeZone(dateList.getTimeZone());
                }
            }
            DateList candidates = getCandidates(calendar, dates, value);
            if (!candidates.isEmpty()) {
                Collections.sort(candidates);
                Iterator<Date> it2 = candidates.iterator();
                while (it2.hasNext()) {
                    next = it2.next();
                    if (!next.before(date)) {
                        if (next.before(date2) || next.after(date3)) {
                            hashSet.add(next);
                        } else {
                            if (getCount() >= 1 && dateList.size() + hashSet.size() >= getCount()) {
                                break;
                            }
                            if (!next.before(date2) && !next.after(date3) && (getUntil() == null || !next.after(getUntil()))) {
                                dateList.add(next);
                            }
                        }
                    }
                }
                i2 = 0;
            } else {
                i2++;
                int i3 = maxIncrementCount;
                if (i3 > 0 && i2 > i3) {
                    break;
                }
            }
            calendarInstance = smartIncrement(calendarInstance);
        } while (calendarInstance != null);
        Collections.sort(dateList);
        return dateList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00cd, code lost:
    
        return r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final net.fortuna.ical4j.model.Date getNextDate(net.fortuna.ical4j.model.Date r13, net.fortuna.ical4j.model.Date r14) {
        /*
            r12 = this;
            r0 = 1
            java.util.Calendar r1 = r12.getCalendarInstance(r13, r0)
            java.lang.Object r2 = r1.clone()
            java.util.Calendar r2 = (java.util.Calendar) r2
            java.lang.Integer r3 = r12.count
            if (r3 != 0) goto L2a
            java.lang.Object r3 = r1.clone()
            java.util.Calendar r3 = (java.util.Calendar) r3
        L15:
            java.util.Date r4 = r3.getTime()
            boolean r4 = r4.before(r14)
            if (r4 == 0) goto L2a
            java.util.Date r4 = r3.getTime()
            r1.setTime(r4)
            r12.increment(r3)
            goto L15
        L2a:
            boolean r3 = r13 instanceof net.fortuna.ical4j.model.DateTime
            if (r3 == 0) goto L31
            net.fortuna.ical4j.model.parameter.Value r3 = net.fortuna.ical4j.model.parameter.Value.DATE_TIME
            goto L33
        L31:
            net.fortuna.ical4j.model.parameter.Value r3 = net.fortuna.ical4j.model.parameter.Value.DATE
        L33:
            r4 = 0
            r5 = 0
            r6 = r4
            r7 = 0
            r8 = 0
        L38:
            java.util.Date r9 = r1.getTime()
            net.fortuna.ical4j.model.Date r9 = net.fortuna.ical4j.util.Dates.getInstance(r9, r3)
            net.fortuna.ical4j.model.Date r10 = r12.getUntil()
            if (r10 == 0) goto L54
            if (r6 == 0) goto L54
            net.fortuna.ical4j.model.Date r10 = r12.getUntil()
            boolean r10 = r6.after(r10)
            if (r10 == 0) goto L54
            goto Ld7
        L54:
            int r10 = r12.getCount()
            if (r10 <= 0) goto L62
            int r10 = r12.getCount()
            if (r7 < r10) goto L62
            goto Ld7
        L62:
            net.fortuna.ical4j.model.parameter.Value r10 = net.fortuna.ical4j.model.parameter.Value.DATE_TIME
            boolean r10 = r10.equals(r3)
            if (r10 == 0) goto L84
            r10 = r13
            net.fortuna.ical4j.model.DateTime r10 = (net.fortuna.ical4j.model.DateTime) r10
            boolean r11 = r10.isUtc()
            if (r11 == 0) goto L7a
            r10 = r9
            net.fortuna.ical4j.model.DateTime r10 = (net.fortuna.ical4j.model.DateTime) r10
            r10.setUtc(r0)
            goto L84
        L7a:
            r11 = r9
            net.fortuna.ical4j.model.DateTime r11 = (net.fortuna.ical4j.model.DateTime) r11
            net.fortuna.ical4j.model.TimeZone r10 = r10.getTimeZone()
            r11.setTimeZone(r10)
        L84:
            net.fortuna.ical4j.model.DateList r9 = r12.getCandidates(r2, r9, r3)
            boolean r10 = r9.isEmpty()
            if (r10 != 0) goto Ld0
            java.util.Collections.sort(r9)
            java.util.Iterator r8 = r9.iterator()
        L95:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto Lce
            java.lang.Object r6 = r8.next()
            net.fortuna.ical4j.model.Date r6 = (net.fortuna.ical4j.model.Date) r6
            boolean r9 = r6.before(r13)
            if (r9 != 0) goto L95
            boolean r9 = r6.after(r14)
            if (r9 != 0) goto Lb0
            int r7 = r7 + 1
            goto L95
        Lb0:
            int r9 = r12.getCount()
            if (r9 <= 0) goto Lbd
            int r9 = r12.getCount()
            if (r7 < r9) goto Lbd
            goto Lce
        Lbd:
            net.fortuna.ical4j.model.Date r9 = r12.getUntil()
            if (r9 == 0) goto Lcd
            net.fortuna.ical4j.model.Date r9 = r12.getUntil()
            boolean r9 = r6.after(r9)
            if (r9 != 0) goto L95
        Lcd:
            return r6
        Lce:
            r8 = 0
            goto Ld8
        Ld0:
            int r8 = r8 + r0
            int r9 = net.fortuna.ical4j.model.Recur.maxIncrementCount
            if (r9 <= 0) goto Ld8
            if (r8 <= r9) goto Ld8
        Ld7:
            return r4
        Ld8:
            r12.increment(r1)
            goto L38
        */
        throw new UnsupportedOperationException("Method not decompiled: net.fortuna.ical4j.model.Recur.getNextDate(net.fortuna.ical4j.model.Date, net.fortuna.ical4j.model.Date):net.fortuna.ical4j.model.Date");
    }

    private void increment(java.util.Calendar calendar) {
        calendar.add(this.calIncField, getInterval() >= 1 ? getInterval() : 1);
    }

    private java.util.Calendar smartIncrement(java.util.Calendar calendar) {
        java.util.Calendar calendar2;
        int interval = getInterval() >= 1 ? getInterval() : 1;
        int i = this.calIncField;
        if (i != 2 && i != 1) {
            java.util.Calendar calendar3 = (java.util.Calendar) calendar.clone();
            calendar3.add(this.calIncField, interval);
            return calendar3;
        }
        int i2 = 1;
        do {
            calendar2 = (java.util.Calendar) calendar.clone();
            calendar2.add(this.calIncField, interval * i2);
            i2++;
            if (calendar2.get(5) == calendar.get(5)) {
                break;
            }
        } while (i2 <= 12);
        if (i2 <= 12) {
            return (java.util.Calendar) calendar2.clone();
        }
        return null;
    }

    private DateList getCandidates(java.util.Calendar calendar, Date date, Value value) {
        DateList dateList = new DateList(value);
        if (date instanceof DateTime) {
            DateTime dateTime = (DateTime) date;
            if (dateTime.isUtc()) {
                dateList.setUtc(true);
            } else {
                dateList.setTimeZone(dateTime.getTimeZone());
            }
        }
        dateList.add(date);
        if (this.transformers.get(BYMONTH) != null) {
            dateList = this.transformers.get(BYMONTH).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYMONTH processing: " + dateList);
            }
        }
        if (this.transformers.get(BYWEEKNO) != null) {
            dateList = this.transformers.get(BYWEEKNO).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYWEEKNO processing: " + dateList);
            }
        }
        if (this.transformers.get(BYYEARDAY) != null) {
            dateList = this.transformers.get(BYYEARDAY).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYYEARDAY processing: " + dateList);
            }
        }
        if (this.transformers.get(BYMONTHDAY) != null) {
            dateList = this.transformers.get(BYMONTHDAY).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYMONTHDAY processing: " + dateList);
            }
        } else if (this.frequency == Frequency.MONTHLY || (this.frequency == Frequency.YEARLY && this.yearDayList.isEmpty() && this.weekNoList.isEmpty() && this.dayList.isEmpty())) {
            NumberList numberList = new NumberList();
            numberList.add(Integer.valueOf(calendar.get(5)));
            dateList = new ByMonthDayRule(numberList, this.frequency, Optional.ofNullable(this.weekStartDay)).transform(dateList);
        }
        if (this.transformers.get(BYDAY) != null) {
            dateList = this.transformers.get(BYDAY).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYDAY processing: " + dateList);
            }
        } else if (this.frequency == Frequency.WEEKLY || (this.frequency == Frequency.YEARLY && this.yearDayList.isEmpty() && !this.weekNoList.isEmpty() && this.monthDayList.isEmpty())) {
            dateList = new ByDayRule(new WeekDayList(WeekDay.getWeekDay(calendar)), deriveFilterType(), Optional.ofNullable(this.weekStartDay)).transform(dateList);
        }
        if (this.transformers.get(BYHOUR) != null) {
            dateList = this.transformers.get(BYHOUR).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYHOUR processing: " + dateList);
            }
        }
        if (this.transformers.get(BYMINUTE) != null) {
            dateList = this.transformers.get(BYMINUTE).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYMINUTE processing: " + dateList);
            }
        }
        if (this.transformers.get(BYSECOND) != null) {
            dateList = this.transformers.get(BYSECOND).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after BYSECOND processing: " + dateList);
            }
        }
        if (this.transformers.get(BYSETPOS) != null) {
            dateList = this.transformers.get(BYSETPOS).transform(dateList);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Dates after SETPOS processing: " + dateList);
            }
        }
        return dateList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateFrequency() {
        if (this.frequency == null) {
            throw new IllegalArgumentException("A recurrence rule MUST contain a FREQ rule part.");
        }
        if (Frequency.SECONDLY.equals(getFrequency())) {
            this.calIncField = 13;
            return;
        }
        if (Frequency.MINUTELY.equals(getFrequency())) {
            this.calIncField = 12;
            return;
        }
        if (Frequency.HOURLY.equals(getFrequency())) {
            this.calIncField = 11;
            return;
        }
        if (Frequency.DAILY.equals(getFrequency())) {
            this.calIncField = 6;
            return;
        }
        if (Frequency.WEEKLY.equals(getFrequency())) {
            this.calIncField = 3;
            return;
        }
        if (Frequency.MONTHLY.equals(getFrequency())) {
            this.calIncField = 2;
            return;
        }
        if (Frequency.YEARLY.equals(getFrequency())) {
            this.calIncField = 1;
            return;
        }
        throw new IllegalArgumentException("Invalid FREQ rule part '" + this.frequency + "' in recurrence rule");
    }

    @Deprecated
    public final void setCount(int i) {
        this.count = Integer.valueOf(i);
        this.until = null;
    }

    @Deprecated
    public final void setFrequency(String str) {
        this.frequency = Frequency.valueOf(str);
        validateFrequency();
    }

    @Deprecated
    public final void setInterval(int i) {
        this.interval = Integer.valueOf(i);
    }

    @Deprecated
    public final void setUntil(Date date) {
        this.until = date;
        this.count = -1;
    }

    private java.util.Calendar getCalendarInstance(Date date, boolean z) {
        java.util.Calendar calendarInstance = Dates.getCalendarInstance(date);
        calendarInstance.setMinimalDaysInFirstWeek(4);
        calendarInstance.setFirstDayOfWeek(this.calendarWeekStartDay);
        calendarInstance.setLenient(z);
        calendarInstance.setTime(date);
        return calendarInstance;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
    }

    public static class Builder {
        private Integer count;
        private WeekDayList dayList;
        private Frequency frequency;
        private NumberList hourList;
        private Integer interval;
        private NumberList minuteList;
        private NumberList monthDayList;
        private NumberList monthList;
        private NumberList secondList;
        private NumberList setPosList;
        private Date until;
        private NumberList weekNoList;
        private WeekDay.Day weekStartDay;
        private NumberList yearDayList;

        public Builder frequency(Frequency frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder until(Date date) {
            this.until = date;
            return this;
        }

        public Builder count(Integer num) {
            this.count = num;
            return this;
        }

        public Builder interval(Integer num) {
            this.interval = num;
            return this;
        }

        public Builder secondList(NumberList numberList) {
            this.secondList = numberList;
            return this;
        }

        public Builder minuteList(NumberList numberList) {
            this.minuteList = numberList;
            return this;
        }

        public Builder hourList(NumberList numberList) {
            this.hourList = numberList;
            return this;
        }

        public Builder dayList(WeekDayList weekDayList) {
            this.dayList = weekDayList;
            return this;
        }

        public Builder monthDayList(NumberList numberList) {
            this.monthDayList = numberList;
            return this;
        }

        public Builder yearDayList(NumberList numberList) {
            this.yearDayList = numberList;
            return this;
        }

        public Builder weekNoList(NumberList numberList) {
            this.weekNoList = numberList;
            return this;
        }

        public Builder monthList(NumberList numberList) {
            this.monthList = numberList;
            return this;
        }

        public Builder setPosList(NumberList numberList) {
            this.setPosList = numberList;
            return this;
        }

        public Builder weekStartDay(WeekDay.Day day) {
            this.weekStartDay = day;
            return this;
        }

        public Recur build() {
            Recur recur = new Recur();
            recur.frequency = this.frequency;
            recur.until = this.until;
            recur.count = this.count;
            recur.interval = this.interval;
            recur.secondList = this.secondList;
            recur.minuteList = this.minuteList;
            recur.hourList = this.hourList;
            recur.dayList = this.dayList;
            recur.monthDayList = this.monthDayList;
            recur.yearDayList = this.yearDayList;
            recur.weekNoList = this.weekNoList;
            recur.monthList = this.monthList;
            recur.setPosList = this.setPosList;
            recur.weekStartDay = this.weekStartDay;
            recur.validateFrequency();
            recur.initTransformers();
            return recur;
        }
    }
}
