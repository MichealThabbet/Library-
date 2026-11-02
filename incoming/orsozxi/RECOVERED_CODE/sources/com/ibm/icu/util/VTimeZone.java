package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.BasicTimeZone;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public class VTimeZone extends BasicTimeZone {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String COLON = ":";
    private static final String COMMA = ",";
    private static final int DEF_DSTSAVINGS = 3600000;
    private static final long DEF_TZSTARTTIME = 0;
    private static final String EQUALS_SIGN = "=";
    private static final int ERR = 3;
    private static final String ICAL_BEGIN = "BEGIN";
    private static final String ICAL_BEGIN_VTIMEZONE = "BEGIN:VTIMEZONE";
    private static final String ICAL_BYDAY = "BYDAY";
    private static final String ICAL_BYMONTH = "BYMONTH";
    private static final String ICAL_BYMONTHDAY = "BYMONTHDAY";
    private static final String ICAL_DAYLIGHT = "DAYLIGHT";
    private static final String ICAL_DTSTART = "DTSTART";
    private static final String ICAL_END = "END";
    private static final String ICAL_END_VTIMEZONE = "END:VTIMEZONE";
    private static final String ICAL_FREQ = "FREQ";
    private static final String ICAL_LASTMOD = "LAST-MODIFIED";
    private static final String ICAL_RDATE = "RDATE";
    private static final String ICAL_RRULE = "RRULE";
    private static final String ICAL_STANDARD = "STANDARD";
    private static final String ICAL_TZID = "TZID";
    private static final String ICAL_TZNAME = "TZNAME";
    private static final String ICAL_TZOFFSETFROM = "TZOFFSETFROM";
    private static final String ICAL_TZOFFSETTO = "TZOFFSETTO";
    private static final String ICAL_TZURL = "TZURL";
    private static final String ICAL_UNTIL = "UNTIL";
    private static final String ICAL_VTIMEZONE = "VTIMEZONE";
    private static final String ICAL_YEARLY = "YEARLY";
    private static final String ICU_TZINFO_PROP = "X-TZINFO";
    private static String ICU_TZVERSION = null;
    private static final int INI = 0;
    private static final long MAX_TIME = Long.MAX_VALUE;
    private static final long MIN_TIME = Long.MIN_VALUE;
    private static final String NEWLINE = "\r\n";
    private static final String SEMICOLON = ";";
    private static final int TZI = 2;
    private static final int VTZ = 1;
    private static final long serialVersionUID = -6851467294127795902L;
    private volatile transient boolean isFrozen;
    private Date lastmod;
    private String olsonzid;
    private BasicTimeZone tz;
    private String tzurl;
    private List<String> vtzlines;
    private static final String[] ICAL_DOW_NAMES = {"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
    private static final int[] MONTHLENGTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static VTimeZone create(String str) {
        BasicTimeZone frozenICUTimeZone = TimeZone.getFrozenICUTimeZone(str, true);
        if (frozenICUTimeZone == null) {
            return null;
        }
        VTimeZone vTimeZone = new VTimeZone(str);
        BasicTimeZone basicTimeZone = (BasicTimeZone) frozenICUTimeZone.cloneAsThawed();
        vTimeZone.tz = basicTimeZone;
        vTimeZone.olsonzid = basicTimeZone.getID();
        return vTimeZone;
    }

    public static VTimeZone create(Reader reader) {
        VTimeZone vTimeZone = new VTimeZone();
        if (vTimeZone.load(reader)) {
            return vTimeZone;
        }
        return null;
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.tz.getOffset(i, i2, i3, i4, i5, i6);
    }

    @Override // com.ibm.icu.util.TimeZone
    public void getOffset(long j, boolean z, int[] iArr) {
        this.tz.getOffset(j, z, iArr);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public void getOffsetFromLocal(long j, BasicTimeZone.LocalOption localOption, BasicTimeZone.LocalOption localOption2, int[] iArr) {
        this.tz.getOffsetFromLocal(j, localOption, localOption2, iArr);
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getRawOffset() {
        return this.tz.getRawOffset();
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean inDaylightTime(Date date) {
        return this.tz.inDaylightTime(date);
    }

    @Override // com.ibm.icu.util.TimeZone
    public void setRawOffset(int i) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.tz.setRawOffset(i);
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean useDaylightTime() {
        return this.tz.useDaylightTime();
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean observesDaylightTime() {
        return this.tz.observesDaylightTime();
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (timeZone instanceof VTimeZone) {
            return this.tz.hasSameRules(((VTimeZone) timeZone).tz);
        }
        return this.tz.hasSameRules(timeZone);
    }

    public String getTZURL() {
        return this.tzurl;
    }

    public void setTZURL(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.tzurl = str;
    }

    public Date getLastModified() {
        return this.lastmod;
    }

    public void setLastModified(Date date) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.lastmod = date;
    }

    public void write(Writer writer) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        List<String> list = this.vtzlines;
        if (list != null) {
            for (String str : list) {
                if (str.startsWith("TZURL:")) {
                    if (this.tzurl != null) {
                        bufferedWriter.write("TZURL");
                        bufferedWriter.write(COLON);
                        bufferedWriter.write(this.tzurl);
                        bufferedWriter.write("\r\n");
                    }
                } else if (str.startsWith("LAST-MODIFIED:")) {
                    if (this.lastmod != null) {
                        bufferedWriter.write("LAST-MODIFIED");
                        bufferedWriter.write(COLON);
                        bufferedWriter.write(getUTCDateTimeString(this.lastmod.getTime()));
                        bufferedWriter.write("\r\n");
                    }
                } else {
                    bufferedWriter.write(str);
                    bufferedWriter.write("\r\n");
                }
            }
            bufferedWriter.flush();
            return;
        }
        String[] strArr = null;
        if (this.olsonzid != null && ICU_TZVERSION != null) {
            strArr = new String[]{"X-TZINFO:" + this.olsonzid + "[" + ICU_TZVERSION + "]"};
        }
        writeZone(writer, this.tz, strArr);
    }

    public void write(Writer writer, long j) throws IOException {
        TimeZoneRule[] timeZoneRules = this.tz.getTimeZoneRules(j);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule) timeZoneRules[0]);
        for (int i = 1; i < timeZoneRules.length; i++) {
            ruleBasedTimeZone.addTransitionRule(timeZoneRules[i]);
        }
        String[] strArr = null;
        if (this.olsonzid != null && ICU_TZVERSION != null) {
            strArr = new String[]{"X-TZINFO:" + this.olsonzid + "[" + ICU_TZVERSION + "/Partial@" + j + "]"};
        }
        writeZone(writer, ruleBasedTimeZone, strArr);
    }

    public void writeSimple(Writer writer, long j) throws IOException {
        TimeZoneRule[] simpleTimeZoneRulesNear = this.tz.getSimpleTimeZoneRulesNear(j);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule) simpleTimeZoneRulesNear[0]);
        for (int i = 1; i < simpleTimeZoneRulesNear.length; i++) {
            ruleBasedTimeZone.addTransitionRule(simpleTimeZoneRulesNear[i]);
        }
        String[] strArr = null;
        if (this.olsonzid != null && ICU_TZVERSION != null) {
            strArr = new String[]{"X-TZINFO:" + this.olsonzid + "[" + ICU_TZVERSION + "/Simple@" + j + "]"};
        }
        writeZone(writer, ruleBasedTimeZone, strArr);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneTransition getNextTransition(long j, boolean z) {
        return this.tz.getNextTransition(j, z);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneTransition getPreviousTransition(long j, boolean z) {
        return this.tz.getPreviousTransition(j, z);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public boolean hasEquivalentTransitions(TimeZone timeZone, long j, long j2) {
        if (this == timeZone) {
            return true;
        }
        return this.tz.hasEquivalentTransitions(timeZone, j, j2);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneRule[] getTimeZoneRules() {
        return this.tz.getTimeZoneRules();
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneRule[] getTimeZoneRules(long j) {
        return this.tz.getTimeZoneRules(j);
    }

    @Override // com.ibm.icu.util.TimeZone
    public Object clone() {
        return isFrozen() ? this : cloneAsThawed();
    }

    static {
        try {
            ICU_TZVERSION = TimeZone.getTZDataVersion();
        } catch (MissingResourceException unused) {
            ICU_TZVERSION = null;
        }
    }

    private VTimeZone() {
        this.olsonzid = null;
        this.tzurl = null;
        this.lastmod = null;
        this.isFrozen = false;
    }

    private VTimeZone(String str) {
        super(str);
        this.olsonzid = null;
        this.tzurl = null;
        this.lastmod = null;
        this.isFrozen = false;
    }

    private boolean load(Reader reader) throws IOException {
        boolean z;
        try {
            this.vtzlines = new LinkedList();
            StringBuilder sb = new StringBuilder();
            boolean z2 = false;
            loop0: while (true) {
                boolean z3 = false;
                while (true) {
                    int i = reader.read();
                    z = true;
                    if (i == -1) {
                        if (z2 && sb.toString().startsWith(ICAL_END_VTIMEZONE)) {
                            this.vtzlines.add(sb.toString());
                        } else {
                            z = false;
                        }
                    } else if (i != 13) {
                        if (z3) {
                            if (i != 9 && i != 32) {
                                if (z2 && sb.length() > 0) {
                                    this.vtzlines.add(sb.toString());
                                }
                                sb.setLength(0);
                                if (i != 10) {
                                    sb.append((char) i);
                                }
                            }
                        } else if (i == 10) {
                            if (z2) {
                                if (sb.toString().startsWith(ICAL_END_VTIMEZONE)) {
                                    this.vtzlines.add(sb.toString());
                                    break loop0;
                                }
                            } else if (sb.toString().startsWith(ICAL_BEGIN_VTIMEZONE)) {
                                this.vtzlines.add(sb.toString());
                                sb.setLength(0);
                                z2 = true;
                                break;
                            }
                            z3 = true;
                        } else {
                            sb.append((char) i);
                        }
                    }
                }
            }
            if (z) {
                return parse();
            }
            return false;
        } catch (IOException unused) {
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x01fb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x015e A[PHI: r12 r24 r25
      0x015e: PHI (r12v15 com.ibm.icu.util.TimeZoneRule) = 
      (r12v14 com.ibm.icu.util.TimeZoneRule)
      (r12v21 com.ibm.icu.util.TimeZoneRule)
      (r12v21 com.ibm.icu.util.TimeZoneRule)
      (r12v21 com.ibm.icu.util.TimeZoneRule)
     binds: [B:84:0x015d, B:73:0x0137, B:164:0x015e, B:76:0x0144] A[DONT_GENERATE, DONT_INLINE]
      0x015e: PHI (r24v9 boolean) = (r24v8 boolean), (r24v12 boolean), (r24v12 boolean), (r24v12 boolean) binds: [B:84:0x015d, B:73:0x0137, B:164:0x015e, B:76:0x0144] A[DONT_GENERATE, DONT_INLINE]
      0x015e: PHI (r25v9 java.lang.String) = (r25v8 java.lang.String), (r25v12 java.lang.String), (r25v12 java.lang.String), (r25v12 java.lang.String) binds: [B:84:0x015d, B:73:0x0137, B:164:0x015e, B:76:0x0144] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0164  */
    /* JADX WARN: Type inference failed for: r5v11, types: [com.ibm.icu.util.TimeArrayTimeZoneRule] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean parse() {
        /*
            Method dump skipped, instructions count: 773
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.VTimeZone.parse():boolean");
    }

    private static String getDefaultTZName(String str, boolean z) {
        if (z) {
            return str + "(DST)";
        }
        return str + "(STD)";
    }

    private static TimeZoneRule createRuleByRRULE(String str, int i, int i2, long j, List<String> list, int i3) throws NumberFormatException {
        int[] iArr;
        int i4;
        int length;
        int i5;
        DateTimeRule dateTimeRule;
        boolean z;
        if (list == null || list.size() == 0) {
            return null;
        }
        long[] jArr = new long[1];
        int[] rrule = parseRRULE(list.get(0), jArr);
        if (rrule == null) {
            return null;
        }
        int i6 = rrule[0];
        int i7 = rrule[1];
        int i8 = rrule[2];
        int i9 = 3;
        int i10 = rrule[3];
        int i11 = -1;
        if (list.size() == 1) {
            if (rrule.length > 4) {
                if (rrule.length != 10 || i6 == -1 || i7 == 0) {
                    return null;
                }
                int[] iArr2 = new int[7];
                i10 = 31;
                for (int i12 = 0; i12 < 7; i12++) {
                    iArr2[i12] = rrule[i12 + 3];
                    iArr2[i12] = iArr2[i12] > 0 ? iArr2[i12] : MONTHLENGTH[i6] + iArr2[i12] + 1;
                    if (iArr2[i12] < i10) {
                        i10 = iArr2[i12];
                    }
                }
                for (int i13 = 1; i13 < 7; i13++) {
                    int i14 = 0;
                    while (true) {
                        if (i14 >= 7) {
                            z = false;
                            break;
                        }
                        if (iArr2[i14] == i10 + i13) {
                            z = true;
                            break;
                        }
                        i14++;
                    }
                    if (!z) {
                        return null;
                    }
                }
            }
            iArr = null;
            i4 = i3;
        } else {
            if (i6 == -1 || i7 == 0 || i10 == 0) {
                return null;
            }
            if (list.size() > 7) {
                return null;
            }
            int length2 = rrule.length - 3;
            int i15 = 31;
            for (int i16 = 0; i16 < length2; i16++) {
                int i17 = rrule[i16 + 3];
                if (i17 <= 0) {
                    i17 = MONTHLENGTH[i6] + i17 + 1;
                }
                int i18 = i17;
                if (i18 < i15) {
                    i15 = i18;
                }
            }
            int i19 = i6;
            int i20 = 1;
            int i21 = -1;
            while (i20 < list.size()) {
                long[] jArr2 = new long[1];
                int[] rrule2 = parseRRULE(list.get(i20), jArr2);
                if (jArr2[0] > jArr[0]) {
                    jArr = jArr2;
                }
                if (rrule2[0] == i11 || rrule2[1] == 0 || rrule2[i9] == 0 || (length2 = length2 + (length = rrule2.length - i9)) > 7 || rrule2[1] != i7) {
                    return null;
                }
                if (rrule2[0] != i6) {
                    if (i21 == -1) {
                        int i22 = rrule2[0] - i6;
                        if (i22 == -11 || i22 == -1) {
                            i19 = rrule2[0];
                            i21 = i19;
                            i15 = 31;
                        } else {
                            if (i22 != 11 && i22 != 1) {
                                return null;
                            }
                            i21 = rrule2[0];
                        }
                    } else if (rrule2[0] != i6 && rrule2[0] != i21) {
                        return null;
                    }
                }
                if (rrule2[0] == i19) {
                    for (int i23 = 0; i23 < length; i23++) {
                        int i24 = rrule2[i23 + 3];
                        if (i24 <= 0) {
                            i24 = MONTHLENGTH[rrule2[0]] + i24 + 1;
                        }
                        if (i24 < i15) {
                            i15 = i24;
                        }
                    }
                }
                i20++;
                i9 = 3;
                i11 = -1;
            }
            iArr = null;
            if (length2 != 7) {
                return null;
            }
            i4 = i3;
            i6 = i19;
            i10 = i15;
        }
        int[] iArrTimeToFields = Grego.timeToFields(j + i4, iArr);
        int i25 = iArrTimeToFields[0];
        int i26 = i6 == -1 ? iArrTimeToFields[1] : i6;
        if (i7 == 0 && i8 == 0 && i10 == 0) {
            i10 = iArrTimeToFields[2];
        }
        int i27 = iArrTimeToFields[5];
        if (jArr[0] != Long.MIN_VALUE) {
            Grego.timeToFields(jArr[0], iArrTimeToFields);
            i5 = iArrTimeToFields[0];
        } else {
            i5 = Integer.MAX_VALUE;
        }
        if (i7 == 0 && i8 == 0 && i10 != 0) {
            dateTimeRule = new DateTimeRule(i26, i10, i27, 0);
        } else if (i7 != 0 && i8 != 0 && i10 == 0) {
            dateTimeRule = new DateTimeRule(i26, i8, i7, i27, 0);
        } else {
            if (i7 == 0 || i8 != 0 || i10 == 0) {
                return null;
            }
            dateTimeRule = new DateTimeRule(i26, i10, i7, true, i27, 0);
        }
        return new AnnualTimeZoneRule(str, i, i2, dateTimeRule, i25, i5);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00ab A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00fd A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00fd A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int[] parseRRULE(java.lang.String r16, long[] r17) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 297
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.VTimeZone.parseRRULE(java.lang.String, long[]):int[]");
    }

    private static TimeZoneRule createRuleByRDATE(String str, int i, int i2, long j, List<String> list, int i3) {
        long[] jArr;
        int i4 = 0;
        if (list == null || list.size() == 0) {
            jArr = new long[]{j};
        } else {
            long[] jArr2 = new long[list.size()];
            try {
                Iterator<String> it2 = list.iterator();
                while (it2.hasNext()) {
                    int i5 = i4 + 1;
                    jArr2[i4] = parseDateTimeString(it2.next(), i3);
                    i4 = i5;
                }
                jArr = jArr2;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        return new TimeArrayTimeZoneRule(str, i, i2, jArr, 2);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void writeZone(java.io.Writer r67, com.ibm.icu.util.BasicTimeZone r68, java.lang.String[] r69) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.VTimeZone.writeZone(java.io.Writer, com.ibm.icu.util.BasicTimeZone, java.lang.String[]):void");
    }

    private static boolean isEquivalentDateRule(int i, int i2, int i3, DateTimeRule dateTimeRule) {
        if (i != dateTimeRule.getRuleMonth() || i3 != dateTimeRule.getRuleDayOfWeek() || dateTimeRule.getTimeRuleType() != 0) {
            return false;
        }
        if (dateTimeRule.getDateRuleType() == 1 && dateTimeRule.getRuleWeekInMonth() == i2) {
            return true;
        }
        int ruleDayOfMonth = dateTimeRule.getRuleDayOfMonth();
        if (dateTimeRule.getDateRuleType() == 2) {
            if (ruleDayOfMonth % 7 == 1 && (ruleDayOfMonth + 6) / 7 == i2) {
                return true;
            }
            if (i != 1) {
                int[] iArr = MONTHLENGTH;
                if ((iArr[i] - ruleDayOfMonth) % 7 == 6 && i2 == (((iArr[i] - ruleDayOfMonth) + 1) / 7) * (-1)) {
                    return true;
                }
            }
        }
        if (dateTimeRule.getDateRuleType() == 3) {
            if (ruleDayOfMonth % 7 == 0 && ruleDayOfMonth / 7 == i2) {
                return true;
            }
            if (i != 1) {
                int[] iArr2 = MONTHLENGTH;
                if ((iArr2[i] - ruleDayOfMonth) % 7 == 0 && i2 == (((iArr2[i] - ruleDayOfMonth) / 7) + 1) * (-1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void writeZonePropsByTime(Writer writer, boolean z, String str, int i, int i2, long j, boolean z2) throws IOException {
        beginZoneProps(writer, z, str, i, i2, j);
        if (z2) {
            writer.write("RDATE");
            writer.write(COLON);
            writer.write(getDateTimeString(j + i));
            writer.write("\r\n");
        }
        endZoneProps(writer, z);
    }

    private static void writeZonePropsByDOM(Writer writer, boolean z, String str, int i, int i2, int i3, int i4, long j, long j2) throws IOException {
        beginZoneProps(writer, z, str, i, i2, j);
        beginRRULE(writer, i3);
        writer.write(ICAL_BYMONTHDAY);
        writer.write("=");
        writer.write(Integer.toString(i4));
        if (j2 != Long.MAX_VALUE) {
            appendUNTIL(writer, getDateTimeString(j2 + i));
        }
        writer.write("\r\n");
        endZoneProps(writer, z);
    }

    private static void writeZonePropsByDOW(Writer writer, boolean z, String str, int i, int i2, int i3, int i4, int i5, long j, long j2) throws IOException {
        beginZoneProps(writer, z, str, i, i2, j);
        beginRRULE(writer, i3);
        writer.write(ICAL_BYDAY);
        writer.write("=");
        writer.write(Integer.toString(i4));
        writer.write(ICAL_DOW_NAMES[i5 - 1]);
        if (j2 != Long.MAX_VALUE) {
            appendUNTIL(writer, getDateTimeString(j2 + i));
        }
        writer.write("\r\n");
        endZoneProps(writer, z);
    }

    private static void writeZonePropsByDOW_GEQ_DOM(Writer writer, boolean z, String str, int i, int i2, int i3, int i4, int i5, long j, long j2) throws IOException {
        int i6;
        int i7;
        if (i4 % 7 == 1) {
            writeZonePropsByDOW(writer, z, str, i, i2, i3, (i4 + 6) / 7, i5, j, j2);
            return;
        }
        if (i3 != 1) {
            int[] iArr = MONTHLENGTH;
            if ((iArr[i3] - i4) % 7 == 6) {
                writeZonePropsByDOW(writer, z, str, i, i2, i3, (((iArr[i3] - i4) + 1) / 7) * (-1), i5, j, j2);
                return;
            }
        }
        beginZoneProps(writer, z, str, i, i2, j);
        if (i4 <= 0) {
            int i8 = 1 - i4;
            int i9 = 7 - i8;
            int i10 = i3 - 1;
            writeZonePropsByDOW_GEQ_DOM_sub(writer, i10 < 0 ? 11 : i10, -i8, i5, i8, Long.MAX_VALUE, i);
            i7 = i9;
            i6 = 1;
        } else {
            int i11 = i4 + 6;
            int[] iArr2 = MONTHLENGTH;
            if (i11 > iArr2[i3]) {
                int i12 = i11 - iArr2[i3];
                int i13 = 7 - i12;
                int i14 = i3 + 1;
                writeZonePropsByDOW_GEQ_DOM_sub(writer, i14 > 11 ? 0 : i14, 1, i5, i12, Long.MAX_VALUE, i);
                i6 = i4;
                i7 = i13;
            } else {
                i6 = i4;
                i7 = 7;
            }
        }
        writeZonePropsByDOW_GEQ_DOM_sub(writer, i3, i6, i5, i7, j2, i);
        endZoneProps(writer, z);
    }

    private static void writeZonePropsByDOW_GEQ_DOM_sub(Writer writer, int i, int i2, int i3, int i4, long j, int i5) throws IOException {
        boolean z = i == 1;
        if (i2 < 0 && !z) {
            i2 = MONTHLENGTH[i] + i2 + 1;
        }
        beginRRULE(writer, i);
        writer.write(ICAL_BYDAY);
        writer.write("=");
        writer.write(ICAL_DOW_NAMES[i3 - 1]);
        writer.write(SEMICOLON);
        writer.write(ICAL_BYMONTHDAY);
        writer.write("=");
        writer.write(Integer.toString(i2));
        for (int i6 = 1; i6 < i4; i6++) {
            writer.write(",");
            writer.write(Integer.toString(i2 + i6));
        }
        if (j != Long.MAX_VALUE) {
            appendUNTIL(writer, getDateTimeString(j + i5));
        }
        writer.write("\r\n");
    }

    private static void writeZonePropsByDOW_LEQ_DOM(Writer writer, boolean z, String str, int i, int i2, int i3, int i4, int i5, long j, long j2) throws IOException {
        if (i4 % 7 == 0) {
            writeZonePropsByDOW(writer, z, str, i, i2, i3, i4 / 7, i5, j, j2);
            return;
        }
        if (i3 != 1) {
            int[] iArr = MONTHLENGTH;
            if ((iArr[i3] - i4) % 7 == 0) {
                writeZonePropsByDOW(writer, z, str, i, i2, i3, (((iArr[i3] - i4) / 7) + 1) * (-1), i5, j, j2);
                return;
            }
        }
        if (i3 == 1 && i4 == 29) {
            writeZonePropsByDOW(writer, z, str, i, i2, 1, -1, i5, j, j2);
        } else {
            writeZonePropsByDOW_GEQ_DOM(writer, z, str, i, i2, i3, i4 - 6, i5, j, j2);
        }
    }

    private static void writeFinalRule(Writer writer, boolean z, AnnualTimeZoneRule annualTimeZoneRule, int i, int i2, long j) throws IOException {
        DateTimeRule wallTimeRule = toWallTimeRule(annualTimeZoneRule.getRule(), i, i2);
        int ruleMillisInDay = wallTimeRule.getRuleMillisInDay();
        long j2 = ruleMillisInDay < 0 ? j + (0 - ruleMillisInDay) : ruleMillisInDay >= 86400000 ? j - (ruleMillisInDay - 86399999) : j;
        int rawOffset = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings();
        int dateRuleType = wallTimeRule.getDateRuleType();
        if (dateRuleType == 0) {
            writeZonePropsByDOM(writer, z, annualTimeZoneRule.getName(), i + i2, rawOffset, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleDayOfMonth(), j2, Long.MAX_VALUE);
            return;
        }
        if (dateRuleType == 1) {
            writeZonePropsByDOW(writer, z, annualTimeZoneRule.getName(), i + i2, rawOffset, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleWeekInMonth(), wallTimeRule.getRuleDayOfWeek(), j2, Long.MAX_VALUE);
        } else if (dateRuleType == 2) {
            writeZonePropsByDOW_GEQ_DOM(writer, z, annualTimeZoneRule.getName(), i + i2, rawOffset, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleDayOfMonth(), wallTimeRule.getRuleDayOfWeek(), j2, Long.MAX_VALUE);
        } else {
            if (dateRuleType != 3) {
                return;
            }
            writeZonePropsByDOW_LEQ_DOM(writer, z, annualTimeZoneRule.getName(), i + i2, rawOffset, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleDayOfMonth(), wallTimeRule.getRuleDayOfWeek(), j2, Long.MAX_VALUE);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0083 A[PHI: r0 r1 r4 r5
      0x0083: PHI (r0v6 int) = (r0v5 int), (r0v11 int), (r0v11 int) binds: [B:18:0x0040, B:36:0x0076, B:40:0x007e] A[DONT_GENERATE, DONT_INLINE]
      0x0083: PHI (r1v2 int) = (r1v1 int), (r1v6 int), (r1v6 int) binds: [B:18:0x0040, B:36:0x0076, B:40:0x007e] A[DONT_GENERATE, DONT_INLINE]
      0x0083: PHI (r4v1 int) = (r4v0 int), (r4v0 int), (r4v3 int) binds: [B:18:0x0040, B:36:0x0076, B:40:0x007e] A[DONT_GENERATE, DONT_INLINE]
      0x0083: PHI (r5v1 int) = (r5v0 int), (r5v4 int), (r5v4 int) binds: [B:18:0x0040, B:36:0x0076, B:40:0x007e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.ibm.icu.util.DateTimeRule toWallTimeRule(com.ibm.icu.util.DateTimeRule r11, int r12, int r13) {
        /*
            int r0 = r11.getTimeRuleType()
            if (r0 != 0) goto L7
            return r11
        L7:
            int r0 = r11.getRuleMillisInDay()
            int r1 = r11.getTimeRuleType()
            r2 = 2
            r3 = 1
            if (r1 != r2) goto L16
            int r12 = r12 + r13
            int r0 = r0 + r12
            goto L1d
        L16:
            int r12 = r11.getTimeRuleType()
            if (r12 != r3) goto L1d
            int r0 = r0 + r13
        L1d:
            r12 = 86400000(0x5265c00, float:7.82218E-36)
            r13 = 0
            if (r0 >= 0) goto L27
            int r0 = r0 + r12
            r12 = -1
            r9 = r0
            goto L2f
        L27:
            if (r0 < r12) goto L2d
            int r0 = r0 - r12
            r9 = r0
            r12 = 1
            goto L2f
        L2d:
            r9 = r0
            r12 = 0
        L2f:
            int r0 = r11.getRuleMonth()
            int r1 = r11.getRuleDayOfMonth()
            int r4 = r11.getRuleDayOfWeek()
            int r5 = r11.getDateRuleType()
            r6 = 7
            if (r12 == 0) goto L83
            if (r5 != r3) goto L5a
            int r11 = r11.getRuleWeekInMonth()
            if (r11 <= 0) goto L51
            int r11 = r11 - r3
            int r11 = r11 * 7
            int r1 = r11 + 1
            r5 = 2
            goto L5a
        L51:
            r5 = 3
            int[] r1 = com.ibm.icu.util.VTimeZone.MONTHLENGTH
            r1 = r1[r0]
            int r11 = r11 + r3
            int r11 = r11 * 7
            int r1 = r1 + r11
        L5a:
            int r1 = r1 + r12
            r11 = 11
            if (r1 != 0) goto L6a
            int r0 = r0 + (-1)
            if (r0 >= 0) goto L65
            r0 = 11
        L65:
            int[] r11 = com.ibm.icu.util.VTimeZone.MONTHLENGTH
            r1 = r11[r0]
            goto L76
        L6a:
            int[] r7 = com.ibm.icu.util.VTimeZone.MONTHLENGTH
            r7 = r7[r0]
            if (r1 <= r7) goto L76
            int r0 = r0 + 1
            if (r0 <= r11) goto L75
            r0 = 0
        L75:
            r1 = 1
        L76:
            if (r5 == 0) goto L83
            int r4 = r4 + r12
            if (r4 >= r3) goto L7e
            r6 = r1
            r7 = 7
            goto L85
        L7e:
            if (r4 <= r6) goto L83
            r6 = r1
            r7 = 1
            goto L85
        L83:
            r6 = r1
            r7 = r4
        L85:
            if (r5 != 0) goto L8d
            com.ibm.icu.util.DateTimeRule r11 = new com.ibm.icu.util.DateTimeRule
            r11.<init>(r0, r6, r9, r13)
            goto L9a
        L8d:
            com.ibm.icu.util.DateTimeRule r11 = new com.ibm.icu.util.DateTimeRule
            if (r5 != r2) goto L93
            r8 = 1
            goto L94
        L93:
            r8 = 0
        L94:
            r10 = 0
            r4 = r11
            r5 = r0
            r4.<init>(r5, r6, r7, r8, r9, r10)
        L9a:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.VTimeZone.toWallTimeRule(com.ibm.icu.util.DateTimeRule, int, int):com.ibm.icu.util.DateTimeRule");
    }

    private static void beginZoneProps(Writer writer, boolean z, String str, int i, int i2, long j) throws IOException {
        writer.write("BEGIN");
        writer.write(COLON);
        if (z) {
            writer.write("DAYLIGHT");
        } else {
            writer.write("STANDARD");
        }
        writer.write("\r\n");
        writer.write("TZOFFSETTO");
        writer.write(COLON);
        writer.write(millisToOffset(i2));
        writer.write("\r\n");
        writer.write("TZOFFSETFROM");
        writer.write(COLON);
        writer.write(millisToOffset(i));
        writer.write("\r\n");
        writer.write("TZNAME");
        writer.write(COLON);
        writer.write(str);
        writer.write("\r\n");
        writer.write("DTSTART");
        writer.write(COLON);
        writer.write(getDateTimeString(j + i));
        writer.write("\r\n");
    }

    private static void endZoneProps(Writer writer, boolean z) throws IOException {
        writer.write("END");
        writer.write(COLON);
        if (z) {
            writer.write("DAYLIGHT");
        } else {
            writer.write("STANDARD");
        }
        writer.write("\r\n");
    }

    private static void beginRRULE(Writer writer, int i) throws IOException {
        writer.write("RRULE");
        writer.write(COLON);
        writer.write(ICAL_FREQ);
        writer.write("=");
        writer.write("YEARLY");
        writer.write(SEMICOLON);
        writer.write(ICAL_BYMONTH);
        writer.write("=");
        writer.write(Integer.toString(i + 1));
        writer.write(SEMICOLON);
    }

    private static void appendUNTIL(Writer writer, String str) throws IOException {
        if (str != null) {
            writer.write(SEMICOLON);
            writer.write(ICAL_UNTIL);
            writer.write("=");
            writer.write(str);
        }
    }

    private void writeHeader(Writer writer) throws IOException {
        writer.write("BEGIN");
        writer.write(COLON);
        writer.write("VTIMEZONE");
        writer.write("\r\n");
        writer.write("TZID");
        writer.write(COLON);
        writer.write(this.tz.getID());
        writer.write("\r\n");
        if (this.tzurl != null) {
            writer.write("TZURL");
            writer.write(COLON);
            writer.write(this.tzurl);
            writer.write("\r\n");
        }
        if (this.lastmod != null) {
            writer.write("LAST-MODIFIED");
            writer.write(COLON);
            writer.write(getUTCDateTimeString(this.lastmod.getTime()));
            writer.write("\r\n");
        }
    }

    private static void writeFooter(Writer writer) throws IOException {
        writer.write("END");
        writer.write(COLON);
        writer.write("VTIMEZONE");
        writer.write("\r\n");
    }

    private static String getDateTimeString(long j) {
        int[] iArrTimeToFields = Grego.timeToFields(j, null);
        StringBuilder sb = new StringBuilder(15);
        sb.append(numToString(iArrTimeToFields[0], 4));
        sb.append(numToString(iArrTimeToFields[1] + 1, 2));
        sb.append(numToString(iArrTimeToFields[2], 2));
        sb.append('T');
        int i = iArrTimeToFields[5];
        int i2 = i / 3600000;
        int i3 = i % 3600000;
        sb.append(numToString(i2, 2));
        sb.append(numToString(i3 / 60000, 2));
        sb.append(numToString((i3 % 60000) / 1000, 2));
        return sb.toString();
    }

    private static String getUTCDateTimeString(long j) {
        return getDateTimeString(j) + "Z";
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static long parseDateTimeString(java.lang.String r11, int r12) throws java.lang.NumberFormatException {
        /*
            r0 = 1
            r1 = 0
            if (r11 != 0) goto L5
            goto L27
        L5:
            int r2 = r11.length()
            r3 = 16
            r4 = 15
            if (r2 == r4) goto L12
            if (r2 == r3) goto L12
            goto L27
        L12:
            r5 = 8
            char r6 = r11.charAt(r5)
            r7 = 84
            if (r6 == r7) goto L1d
            goto L27
        L1d:
            if (r2 != r3) goto L32
            char r2 = r11.charAt(r4)
            r3 = 90
            if (r2 == r3) goto L30
        L27:
            r11 = 0
            r0 = 0
            r2 = 0
            r3 = 0
            r5 = 0
            r7 = 0
            r10 = 0
            goto L9a
        L30:
            r2 = 1
            goto L33
        L32:
            r2 = 0
        L33:
            r3 = 4
            java.lang.String r6 = r11.substring(r1, r3)     // Catch: java.lang.NumberFormatException -> L92
            int r6 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.NumberFormatException -> L92
            r7 = 6
            java.lang.String r3 = r11.substring(r3, r7)     // Catch: java.lang.NumberFormatException -> L8f
            int r3 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.NumberFormatException -> L8f
            int r3 = r3 - r0
            java.lang.String r5 = r11.substring(r7, r5)     // Catch: java.lang.NumberFormatException -> L90
            int r5 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.NumberFormatException -> L90
            r7 = 9
            r8 = 11
            java.lang.String r7 = r11.substring(r7, r8)     // Catch: java.lang.NumberFormatException -> L95
            int r7 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.NumberFormatException -> L95
            r9 = 13
            java.lang.String r10 = r11.substring(r8, r9)     // Catch: java.lang.NumberFormatException -> L96
            int r10 = java.lang.Integer.parseInt(r10)     // Catch: java.lang.NumberFormatException -> L96
            java.lang.String r11 = r11.substring(r9, r4)     // Catch: java.lang.NumberFormatException -> L97
            int r11 = java.lang.Integer.parseInt(r11)     // Catch: java.lang.NumberFormatException -> L97
            int r4 = com.ibm.icu.impl.Grego.monthLength(r6, r3)
            if (r6 < 0) goto L8d
            if (r3 < 0) goto L8d
            if (r3 > r8) goto L8d
            if (r5 < r0) goto L8d
            if (r5 > r4) goto L8d
            if (r7 < 0) goto L8d
            r4 = 24
            if (r7 >= r4) goto L8d
            if (r10 < 0) goto L8d
            r4 = 60
            if (r10 >= r4) goto L8d
            if (r11 < 0) goto L8d
            if (r11 < r4) goto L8b
            goto L8d
        L8b:
            r1 = r6
            goto L9a
        L8d:
            r1 = r6
            goto L99
        L8f:
            r3 = 0
        L90:
            r5 = 0
            goto L95
        L92:
            r3 = 0
            r5 = 0
            r6 = 0
        L95:
            r7 = 0
        L96:
            r10 = 0
        L97:
            r1 = r6
            r11 = 0
        L99:
            r0 = 0
        L9a:
            if (r0 == 0) goto Lba
            long r0 = com.ibm.icu.impl.Grego.fieldsToDay(r1, r3, r5)
            r3 = 86400000(0x5265c00, double:4.2687272E-316)
            long r0 = r0 * r3
            r3 = 3600000(0x36ee80, float:5.044674E-39)
            int r7 = r7 * r3
            r3 = 60000(0xea60, float:8.4078E-41)
            int r10 = r10 * r3
            int r7 = r7 + r10
            int r11 = r11 * 1000
            int r7 = r7 + r11
            long r3 = (long) r7
            long r0 = r0 + r3
            if (r2 != 0) goto Lb9
            long r11 = (long) r12
            long r0 = r0 - r11
        Lb9:
            return r0
        Lba:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "Invalid date time string format"
            r11.<init>(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.VTimeZone.parseDateTimeString(java.lang.String, int):long");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int offsetStrToMillis(java.lang.String r8) throws java.lang.NumberFormatException {
        /*
            r0 = 1
            r1 = 0
            if (r8 != 0) goto L5
            goto L46
        L5:
            int r2 = r8.length()
            r3 = 7
            r4 = 5
            if (r2 == r4) goto L10
            if (r2 == r3) goto L10
            goto L46
        L10:
            char r5 = r8.charAt(r1)
            r6 = 43
            if (r5 != r6) goto L1a
            r5 = 1
            goto L1f
        L1a:
            r6 = 45
            if (r5 != r6) goto L46
            r5 = -1
        L1f:
            r6 = 3
            java.lang.String r7 = r8.substring(r0, r6)     // Catch: java.lang.NumberFormatException -> L40
            int r7 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.NumberFormatException -> L40
            java.lang.String r6 = r8.substring(r6, r4)     // Catch: java.lang.NumberFormatException -> L3e
            int r6 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.NumberFormatException -> L3e
            if (r2 != r3) goto L3b
            java.lang.String r8 = r8.substring(r4, r3)     // Catch: java.lang.NumberFormatException -> L42
            int r8 = java.lang.Integer.parseInt(r8)     // Catch: java.lang.NumberFormatException -> L42
            r1 = r8
        L3b:
            r8 = r1
            r1 = r5
            goto L4a
        L3e:
            r6 = 0
            goto L42
        L40:
            r6 = 0
            r7 = 0
        L42:
            r1 = r5
            r8 = 0
            r0 = 0
            goto L4a
        L46:
            r8 = 0
            r0 = 0
            r6 = 0
            r7 = 0
        L4a:
            if (r0 == 0) goto L57
            int r7 = r7 * 60
            int r7 = r7 + r6
            int r7 = r7 * 60
            int r7 = r7 + r8
            int r1 = r1 * r7
            int r1 = r1 * 1000
            return r1
        L57:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Bad offset string"
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.VTimeZone.offsetStrToMillis(java.lang.String):int");
    }

    private static String millisToOffset(int i) {
        StringBuilder sb = new StringBuilder(7);
        if (i >= 0) {
            sb.append('+');
        } else {
            sb.append(Soundex.SILENT_MARKER);
            i = -i;
        }
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 - i3) / 60;
        sb.append(numToString(i4 / 60, 2));
        sb.append(numToString(i4 % 60, 2));
        sb.append(numToString(i3, 2));
        return sb.toString();
    }

    private static String numToString(int i, int i2) {
        String string = Integer.toString(i);
        int length = string.length();
        if (length >= i2) {
            return string.substring(length - i2, length);
        }
        StringBuilder sb = new StringBuilder(i2);
        while (length < i2) {
            sb.append('0');
            length++;
        }
        sb.append(string);
        return sb.toString();
    }

    @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
    public TimeZone cloneAsThawed() {
        VTimeZone vTimeZone = (VTimeZone) super.cloneAsThawed();
        vTimeZone.tz = (BasicTimeZone) this.tz.cloneAsThawed();
        vTimeZone.isFrozen = false;
        return vTimeZone;
    }
}
