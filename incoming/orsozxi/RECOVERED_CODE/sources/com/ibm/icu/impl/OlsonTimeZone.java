package com.ibm.icu.impl;

import com.ibm.icu.util.AnnualTimeZoneRule;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.DateTimeRule;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.SimpleTimeZone;
import com.ibm.icu.util.TimeArrayTimeZoneRule;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneRule;
import com.ibm.icu.util.TimeZoneTransition;
import com.ibm.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.Date;
import java.util.MissingResourceException;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class OlsonTimeZone extends BasicTimeZone {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = ICUDebug.enabled("olson");
    private static final int MAX_OFFSET_SECONDS = 86400;
    private static final int SECONDS_PER_DAY = 86400;
    private static final String ZONEINFORES = "zoneinfo64";
    private static final int currentSerialVersion = 1;
    static final long serialVersionUID = -6281977362477515376L;
    private volatile String canonicalID;
    private double finalStartMillis;
    private int finalStartYear;
    private SimpleTimeZone finalZone;
    private transient SimpleTimeZone finalZoneWithStartYear;
    private transient TimeZoneTransition firstFinalTZTransition;
    private transient TimeZoneTransition firstTZTransition;
    private transient int firstTZTransitionIdx;
    private transient TimeArrayTimeZoneRule[] historicRules;
    private transient InitialTimeZoneRule initialRule;
    private volatile transient boolean isFrozen;
    private int serialVersionOnStream;
    private int transitionCount;
    private transient boolean transitionRulesInitialized;
    private long[] transitionTimes64;
    private int typeCount;
    private byte[] typeMapData;
    private int[] typeOffsets;

    private int getInt(byte b) {
        return b & 255;
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i3 < 0 || i3 > 11) {
            throw new IllegalArgumentException("Month is not in the legal range: " + i3);
        }
        return getOffset(i, i2, i3, i4, i5, i6, Grego.monthLength(i2, i3));
    }

    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if ((i != 1 && i != 0) || i3 < 0 || i3 > 11 || i4 < 1 || i4 > i7 || i5 < 1 || i5 > 7 || i6 < 0 || i6 >= 86400000 || i7 < 28 || i7 > 31) {
            throw new IllegalArgumentException();
        }
        if (i == 0) {
            i2 = -i2;
        }
        int i8 = i2;
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null && i8 >= this.finalStartYear) {
            return simpleTimeZone.getOffset(i, i8, i3, i4, i5, i6);
        }
        int[] iArr = new int[2];
        getHistoricalOffset((Grego.fieldsToDay(i8, i3, i4) * 86400000) + i6, true, 3, 1, iArr);
        return iArr[0] + iArr[1];
    }

    @Override // com.ibm.icu.util.TimeZone
    public void setRawOffset(int i) {
        int i2;
        DateTimeRule rule;
        DateTimeRule rule2;
        TimeZoneTransition previousTransition;
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
        }
        if (getRawOffset() == i) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis < this.finalStartMillis) {
            SimpleTimeZone simpleTimeZone = new SimpleTimeZone(i, getID());
            boolean zUseDaylightTime = useDaylightTime();
            if (zUseDaylightTime) {
                TimeZoneRule[] simpleTimeZoneRulesNear = getSimpleTimeZoneRulesNear(jCurrentTimeMillis);
                if (simpleTimeZoneRulesNear.length != 3 && (previousTransition = getPreviousTransition(jCurrentTimeMillis, false)) != null) {
                    simpleTimeZoneRulesNear = getSimpleTimeZoneRulesNear(previousTransition.getTime() - 1);
                }
                if (simpleTimeZoneRulesNear.length == 3 && (simpleTimeZoneRulesNear[1] instanceof AnnualTimeZoneRule) && (simpleTimeZoneRulesNear[2] instanceof AnnualTimeZoneRule)) {
                    AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule) simpleTimeZoneRulesNear[1];
                    AnnualTimeZoneRule annualTimeZoneRule2 = (AnnualTimeZoneRule) simpleTimeZoneRulesNear[2];
                    int rawOffset = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings();
                    int rawOffset2 = annualTimeZoneRule2.getRawOffset() + annualTimeZoneRule2.getDSTSavings();
                    if (rawOffset > rawOffset2) {
                        rule = annualTimeZoneRule.getRule();
                        rule2 = annualTimeZoneRule2.getRule();
                        i2 = rawOffset - rawOffset2;
                    } else {
                        DateTimeRule rule3 = annualTimeZoneRule2.getRule();
                        DateTimeRule rule4 = annualTimeZoneRule.getRule();
                        i2 = rawOffset2 - rawOffset;
                        rule = rule3;
                        rule2 = rule4;
                    }
                    simpleTimeZone.setStartRule(rule.getRuleMonth(), rule.getRuleWeekInMonth(), rule.getRuleDayOfWeek(), rule.getRuleMillisInDay());
                    simpleTimeZone.setEndRule(rule2.getRuleMonth(), rule2.getRuleWeekInMonth(), rule2.getRuleDayOfWeek(), rule2.getRuleMillisInDay());
                    simpleTimeZone.setDSTSavings(i2);
                } else {
                    simpleTimeZone.setStartRule(0, 1, 0);
                    simpleTimeZone.setEndRule(11, 31, 86399999);
                }
            }
            this.finalStartYear = Grego.timeToFields(jCurrentTimeMillis, null)[0];
            this.finalStartMillis = Grego.fieldsToDay(r0[0], 0, 1);
            if (zUseDaylightTime) {
                simpleTimeZone.setStartYear(this.finalStartYear);
            }
            this.finalZone = simpleTimeZone;
        } else {
            this.finalZone.setRawOffset(i);
        }
        this.transitionRulesInitialized = false;
    }

    @Override // com.ibm.icu.util.TimeZone
    public Object clone() {
        return isFrozen() ? this : cloneAsThawed();
    }

    @Override // com.ibm.icu.util.TimeZone
    public void getOffset(long j, boolean z, int[] iArr) {
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null && j >= this.finalStartMillis) {
            simpleTimeZone.getOffset(j, z, iArr);
        } else {
            getHistoricalOffset(j, z, 4, 12, iArr);
        }
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public void getOffsetFromLocal(long j, BasicTimeZone.LocalOption localOption, BasicTimeZone.LocalOption localOption2, int[] iArr) {
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null && j >= this.finalStartMillis) {
            simpleTimeZone.getOffsetFromLocal(j, localOption, localOption2, iArr);
        } else {
            getHistoricalOffset(j, true, getLocalOptionValue(localOption), getLocalOptionValue(localOption2), iArr);
        }
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getRawOffset() {
        int[] iArr = new int[2];
        getOffset(System.currentTimeMillis(), false, iArr);
        return iArr[0];
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean useDaylightTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null && jCurrentTimeMillis >= this.finalStartMillis) {
            return simpleTimeZone != null && simpleTimeZone.useDaylightTime();
        }
        int[] iArrTimeToFields = Grego.timeToFields(jCurrentTimeMillis, null);
        long jFieldsToDay = Grego.fieldsToDay(iArrTimeToFields[0], 0, 1) * 86400;
        long jFieldsToDay2 = Grego.fieldsToDay(iArrTimeToFields[0] + 1, 0, 1) * 86400;
        for (int i = 0; i < this.transitionCount; i++) {
            long[] jArr = this.transitionTimes64;
            if (jArr[i] >= jFieldsToDay2) {
                break;
            }
            if ((jArr[i] >= jFieldsToDay && dstOffsetAt(i) != 0) || (this.transitionTimes64[i] > jFieldsToDay && i > 0 && dstOffsetAt(i - 1) != 0)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean observesDaylightTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            if (simpleTimeZone.useDaylightTime()) {
                return true;
            }
            if (jCurrentTimeMillis >= this.finalStartMillis) {
                return false;
            }
        }
        long jFloorDivide = Grego.floorDivide(jCurrentTimeMillis, 1000L);
        int i = this.transitionCount - 1;
        if (dstOffsetAt(i) != 0) {
            return true;
        }
        while (i >= 0 && this.transitionTimes64[i] > jFloorDivide) {
            if (dstOffsetAt(i - 1) != 0) {
                return true;
            }
            i--;
        }
        return false;
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getDSTSavings() {
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            return simpleTimeZone.getDSTSavings();
        }
        return super.getDSTSavings();
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean inDaylightTime(Date date) {
        int[] iArr = new int[2];
        getOffset(date.getTime(), false, iArr);
        return iArr[1] != 0;
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (!super.hasSameRules(timeZone) || !(timeZone instanceof OlsonTimeZone)) {
            return false;
        }
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone) timeZone;
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone == null) {
            if (olsonTimeZone.finalZone != null) {
                return false;
            }
        } else {
            SimpleTimeZone simpleTimeZone2 = olsonTimeZone.finalZone;
            if (simpleTimeZone2 != null && this.finalStartYear == olsonTimeZone.finalStartYear && simpleTimeZone.hasSameRules(simpleTimeZone2)) {
            }
        }
        return this.transitionCount == olsonTimeZone.transitionCount && Arrays.equals(this.transitionTimes64, olsonTimeZone.transitionTimes64) && this.typeCount == olsonTimeZone.typeCount && Arrays.equals(this.typeMapData, olsonTimeZone.typeMapData) && Arrays.equals(this.typeOffsets, olsonTimeZone.typeOffsets);
    }

    public String getCanonicalID() {
        if (this.canonicalID == null) {
            synchronized (this) {
                if (this.canonicalID == null) {
                    this.canonicalID = getCanonicalID(getID());
                    if (this.canonicalID == null) {
                        this.canonicalID = getID();
                    }
                }
            }
        }
        return this.canonicalID;
    }

    private void constructEmpty() {
        this.transitionCount = 0;
        this.transitionTimes64 = null;
        this.typeMapData = null;
        this.typeCount = 1;
        this.typeOffsets = new int[]{0, 0};
        this.finalZone = null;
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.transitionRulesInitialized = false;
    }

    public OlsonTimeZone(UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2, String str) {
        super(str);
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.finalZone = null;
        this.canonicalID = null;
        this.serialVersionOnStream = 1;
        this.isFrozen = false;
        construct(uResourceBundle, uResourceBundle2, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void construct(UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2, String str) {
        int[] intVector;
        int[] intVector2;
        int[] intVector3;
        SimpleTimeZone simpleTimeZone;
        String string;
        int i;
        if (uResourceBundle == null || uResourceBundle2 == null) {
            throw new IllegalArgumentException();
        }
        if (DEBUG) {
            System.out.println("OlsonTimeZone(" + uResourceBundle2.getKey() + ")");
        }
        this.transitionCount = 0;
        int i2 = 2;
        try {
            intVector = uResourceBundle2.get("transPre32").getIntVector();
        } catch (MissingResourceException unused) {
            intVector = null;
        }
        if (intVector.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.transitionCount += intVector.length / 2;
        try {
            intVector2 = uResourceBundle2.get("trans").getIntVector();
            try {
                this.transitionCount += intVector2.length;
            } catch (MissingResourceException unused2) {
            }
        } catch (MissingResourceException unused3) {
            intVector2 = null;
        }
        try {
            intVector3 = uResourceBundle2.get("transPost32").getIntVector();
        } catch (MissingResourceException unused4) {
            intVector3 = null;
        }
        if (intVector3.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.transitionCount += intVector3.length / 2;
        int i3 = this.transitionCount;
        if (i3 > 0) {
            this.transitionTimes64 = new long[i3];
            char c = TokenParser.SP;
            if (intVector != null) {
                int i4 = 0;
                i = 0;
                while (i4 < intVector.length / i2) {
                    int i5 = i4 * 2;
                    this.transitionTimes64[i] = ((intVector[i5] & 4294967295L) << c) | (r17[i5 + 1] & 4294967295L);
                    i4++;
                    i++;
                    intVector = intVector;
                    i2 = 2;
                    c = TokenParser.SP;
                }
            } else {
                i = 0;
            }
            if (intVector2 != null) {
                int i6 = 0;
                while (i6 < intVector2.length) {
                    this.transitionTimes64[i] = intVector2[i6];
                    i6++;
                    i++;
                }
            }
            if (intVector3 != null) {
                int i7 = 0;
                while (i7 < intVector3.length / 2) {
                    int i8 = i7 * 2;
                    this.transitionTimes64[i] = ((intVector3[i8] & 4294967295L) << 32) | (intVector3[i8 + 1] & 4294967295L);
                    i7++;
                    i++;
                }
            }
        } else {
            this.transitionTimes64 = null;
        }
        int[] intVector4 = uResourceBundle2.get("typeOffsets").getIntVector();
        this.typeOffsets = intVector4;
        if (intVector4.length < 2 || intVector4.length > 32766 || intVector4.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.typeCount = intVector4.length / 2;
        if (this.transitionCount > 0) {
            byte[] binary = uResourceBundle2.get("typeMap").getBinary(null);
            this.typeMapData = binary;
            if (binary == null || binary.length != this.transitionCount) {
                throw new IllegalArgumentException("Invalid Format");
            }
            simpleTimeZone = 0;
        } else {
            simpleTimeZone = 0;
            this.typeMapData = null;
        }
        this.finalZone = simpleTimeZone;
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        try {
            string = uResourceBundle2.getString("finalRule");
            try {
                int i9 = uResourceBundle2.get("finalRaw").getInt() * 1000;
                int[] intVector5 = loadRule(uResourceBundle, string).getIntVector();
                if (intVector5 == null || intVector5.length != 11) {
                    throw new IllegalArgumentException("Invalid Format");
                }
                this.finalZone = new SimpleTimeZone(i9, str, intVector5[0], intVector5[1], intVector5[2], intVector5[3] * 1000, intVector5[4], intVector5[5], intVector5[6], intVector5[7], intVector5[8] * 1000, intVector5[9], intVector5[10] * 1000);
                this.finalStartYear = uResourceBundle2.get("finalYear").getInt();
                this.finalStartMillis = Grego.fieldsToDay(r1, 0, 1) * 86400000;
            } catch (MissingResourceException unused5) {
                if (string != null) {
                    throw new IllegalArgumentException("Invalid Format");
                }
            }
        } catch (MissingResourceException unused6) {
            string = simpleTimeZone;
        }
    }

    public OlsonTimeZone(String str) {
        super(str);
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.finalZone = null;
        this.canonicalID = null;
        this.serialVersionOnStream = 1;
        this.isFrozen = false;
        UResourceBundle bundleInstance = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ZONEINFORES, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        construct(bundleInstance, ZoneMeta.openOlsonResource(bundleInstance, str), str);
    }

    @Override // com.ibm.icu.util.TimeZone
    public void setID(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
        }
        if (this.canonicalID == null) {
            this.canonicalID = getCanonicalID(getID());
            if (this.canonicalID == null) {
                this.canonicalID = getID();
            }
        }
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            simpleTimeZone.setID(str);
        }
        super.setID(str);
        this.transitionRulesInitialized = false;
    }

    private void getHistoricalOffset(long j, boolean z, int i, int i2, int[] iArr) {
        int i3;
        int i4;
        if (this.transitionCount != 0) {
            long jFloorDivide = Grego.floorDivide(j, 1000L);
            if (!z && jFloorDivide < this.transitionTimes64[0]) {
                iArr[0] = initialRawOffset() * 1000;
                iArr[1] = initialDstOffset() * 1000;
                return;
            }
            int i5 = this.transitionCount - 1;
            while (i5 >= 0) {
                long j2 = this.transitionTimes64[i5];
                if (z && jFloorDivide >= j2 - 86400) {
                    int i6 = i5 - 1;
                    int iZoneOffsetAt = zoneOffsetAt(i6);
                    boolean z2 = dstOffsetAt(i6) != 0;
                    int iZoneOffsetAt2 = zoneOffsetAt(i5);
                    boolean z3 = dstOffsetAt(i5) != 0;
                    boolean z4 = z2 && !z3;
                    boolean z5 = !z2 && z3;
                    j2 += (iZoneOffsetAt2 - iZoneOffsetAt < 0 ? !((i3 = i2 & 3) == 1 && z4) && (!(i3 == 3 && z5) && ((i3 == 1 && z5) || ((i3 == 3 && z4) || (i2 & 12) == 4))) : ((i4 = i & 3) == 1 && z4) || ((i4 == 3 && z5) || (!(i4 == 1 && z5) && (!(i4 == 3 && z4) && (i & 12) == 12)))) ? iZoneOffsetAt : iZoneOffsetAt2;
                }
                if (jFloorDivide >= j2) {
                    break;
                } else {
                    i5--;
                }
            }
            iArr[0] = rawOffsetAt(i5) * 1000;
            iArr[1] = dstOffsetAt(i5) * 1000;
            return;
        }
        iArr[0] = initialRawOffset() * 1000;
        iArr[1] = initialDstOffset() * 1000;
    }

    private int zoneOffsetAt(int i) {
        int i2 = i >= 0 ? getInt(this.typeMapData[i]) * 2 : 0;
        int[] iArr = this.typeOffsets;
        return iArr[i2] + iArr[i2 + 1];
    }

    private int rawOffsetAt(int i) {
        return this.typeOffsets[i >= 0 ? getInt(this.typeMapData[i]) * 2 : 0];
    }

    private int dstOffsetAt(int i) {
        return this.typeOffsets[(i >= 0 ? getInt(this.typeMapData[i]) * 2 : 0) + 1];
    }

    private int initialRawOffset() {
        return this.typeOffsets[0];
    }

    private int initialDstOffset() {
        return this.typeOffsets[1];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('[');
        sb.append("transitionCount=" + this.transitionCount);
        sb.append(",typeCount=" + this.typeCount);
        sb.append(",transitionTimes=");
        if (this.transitionTimes64 != null) {
            sb.append('[');
            for (int i = 0; i < this.transitionTimes64.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(Long.toString(this.transitionTimes64[i]));
            }
            sb.append(']');
        } else {
            sb.append("null");
        }
        sb.append(",typeOffsets=");
        if (this.typeOffsets != null) {
            sb.append('[');
            for (int i2 = 0; i2 < this.typeOffsets.length; i2++) {
                if (i2 > 0) {
                    sb.append(',');
                }
                sb.append(Integer.toString(this.typeOffsets[i2]));
            }
            sb.append(']');
        } else {
            sb.append("null");
        }
        sb.append(",typeMapData=");
        if (this.typeMapData != null) {
            sb.append('[');
            for (int i3 = 0; i3 < this.typeMapData.length; i3++) {
                if (i3 > 0) {
                    sb.append(',');
                }
                sb.append(Byte.toString(this.typeMapData[i3]));
            }
        } else {
            sb.append("null");
        }
        sb.append(",finalStartYear=" + this.finalStartYear);
        sb.append(",finalStartMillis=" + this.finalStartMillis);
        sb.append(",finalZone=" + this.finalZone);
        sb.append(']');
        return sb.toString();
    }

    private static UResourceBundle loadRule(UResourceBundle uResourceBundle, String str) {
        return uResourceBundle.get("Rules").get(str);
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean equals(Object obj) {
        SimpleTimeZone simpleTimeZone;
        SimpleTimeZone simpleTimeZone2;
        if (!super.equals(obj)) {
            return false;
        }
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone) obj;
        if (!Utility.arrayEquals(this.typeMapData, (Object) olsonTimeZone.typeMapData)) {
            if (this.finalStartYear != olsonTimeZone.finalStartYear) {
                return false;
            }
            if ((this.finalZone != null || olsonTimeZone.finalZone != null) && ((simpleTimeZone = this.finalZone) == null || (simpleTimeZone2 = olsonTimeZone.finalZone) == null || !simpleTimeZone.equals(simpleTimeZone2) || this.transitionCount != olsonTimeZone.transitionCount || this.typeCount != olsonTimeZone.typeCount || !Utility.arrayEquals((Object) this.transitionTimes64, (Object) olsonTimeZone.transitionTimes64) || !Utility.arrayEquals(this.typeOffsets, (Object) olsonTimeZone.typeOffsets) || !Utility.arrayEquals(this.typeMapData, (Object) olsonTimeZone.typeMapData))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.ibm.icu.util.TimeZone
    public int hashCode() {
        int i = this.finalStartYear;
        int i2 = this.transitionCount;
        int i3 = 0;
        int iDoubleToLongBits = (int) (((i ^ ((i >>> 4) + i2)) ^ ((i2 >>> 6) + this.typeCount)) ^ ((((r2 >>> 8) + Double.doubleToLongBits(this.finalStartMillis)) + (this.finalZone == null ? 0 : r2.hashCode())) + super.hashCode()));
        if (this.transitionTimes64 != null) {
            int i4 = 0;
            while (true) {
                long[] jArr = this.transitionTimes64;
                if (i4 >= jArr.length) {
                    break;
                }
                iDoubleToLongBits = (int) (iDoubleToLongBits + ((jArr[i4] >>> 8) ^ jArr[i4]));
                i4++;
            }
        }
        int i5 = 0;
        while (true) {
            int[] iArr = this.typeOffsets;
            if (i5 >= iArr.length) {
                break;
            }
            iDoubleToLongBits += (iArr[i5] >>> 8) ^ iArr[i5];
            i5++;
        }
        if (this.typeMapData != null) {
            while (true) {
                byte[] bArr = this.typeMapData;
                if (i3 >= bArr.length) {
                    break;
                }
                iDoubleToLongBits += bArr[i3] & 255;
                i3++;
            }
        }
        return iDoubleToLongBits;
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneTransition getNextTransition(long j, boolean z) {
        initTransitionRules();
        if (this.finalZone != null) {
            if (z && j == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (j >= this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getNextTransition(j, z);
                }
                return null;
            }
        }
        if (this.historicRules == null) {
            return null;
        }
        int i = this.transitionCount;
        while (true) {
            i--;
            if (i < this.firstTZTransitionIdx) {
                break;
            }
            long j2 = this.transitionTimes64[i] * 1000;
            if (j > j2 || (!z && j == j2)) {
                break;
            }
        }
        if (i == this.transitionCount - 1) {
            return this.firstFinalTZTransition;
        }
        if (i < this.firstTZTransitionIdx) {
            return this.firstTZTransition;
        }
        int i2 = i + 1;
        TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[getInt(this.typeMapData[i2])];
        TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[getInt(this.typeMapData[i])];
        long j3 = this.transitionTimes64[i2] * 1000;
        if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
            return getNextTransition(j3, false);
        }
        return new TimeZoneTransition(j3, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneTransition getPreviousTransition(long j, boolean z) {
        initTransitionRules();
        if (this.finalZone != null) {
            if (z && j == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (j > this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getPreviousTransition(j, z);
                }
                return this.firstFinalTZTransition;
            }
        }
        if (this.historicRules == null) {
            return null;
        }
        int i = this.transitionCount;
        while (true) {
            i--;
            if (i < this.firstTZTransitionIdx) {
                break;
            }
            long j2 = this.transitionTimes64[i] * 1000;
            if (j > j2 || (z && j == j2)) {
                break;
            }
        }
        int i2 = this.firstTZTransitionIdx;
        if (i < i2) {
            return null;
        }
        if (i == i2) {
            return this.firstTZTransition;
        }
        TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[getInt(this.typeMapData[i])];
        TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[getInt(this.typeMapData[i - 1])];
        long j3 = this.transitionTimes64[i] * 1000;
        if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
            return getPreviousTransition(j3, false);
        }
        return new TimeZoneTransition(j3, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneRule[] getTimeZoneRules() {
        int i;
        int i2;
        initTransitionRules();
        if (this.historicRules != null) {
            int i3 = 0;
            i = 1;
            while (true) {
                TimeArrayTimeZoneRule[] timeArrayTimeZoneRuleArr = this.historicRules;
                if (i3 >= timeArrayTimeZoneRuleArr.length) {
                    break;
                }
                if (timeArrayTimeZoneRuleArr[i3] != null) {
                    i++;
                }
                i3++;
            }
        } else {
            i = 1;
        }
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            i = simpleTimeZone.useDaylightTime() ? i + 2 : i + 1;
        }
        TimeZoneRule[] timeZoneRuleArr = new TimeZoneRule[i];
        timeZoneRuleArr[0] = this.initialRule;
        if (this.historicRules != null) {
            int i4 = 0;
            i2 = 1;
            while (true) {
                TimeArrayTimeZoneRule[] timeArrayTimeZoneRuleArr2 = this.historicRules;
                if (i4 >= timeArrayTimeZoneRuleArr2.length) {
                    break;
                }
                if (timeArrayTimeZoneRuleArr2[i4] != null) {
                    timeZoneRuleArr[i2] = timeArrayTimeZoneRuleArr2[i4];
                    i2++;
                }
                i4++;
            }
        } else {
            i2 = 1;
        }
        SimpleTimeZone simpleTimeZone2 = this.finalZone;
        if (simpleTimeZone2 != null) {
            if (simpleTimeZone2.useDaylightTime()) {
                TimeZoneRule[] timeZoneRules = this.finalZoneWithStartYear.getTimeZoneRules();
                timeZoneRuleArr[i2] = timeZoneRules[1];
                timeZoneRuleArr[i2 + 1] = timeZoneRules[2];
            } else {
                timeZoneRuleArr[i2] = new TimeArrayTimeZoneRule(getID() + "(STD)", this.finalZone.getRawOffset(), 0, new long[]{(long) this.finalStartMillis}, 2);
            }
        }
        return timeZoneRuleArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.ibm.icu.util.TimeZoneRule] */
    private synchronized void initTransitionRules() {
        TimeArrayTimeZoneRule timeArrayTimeZoneRule;
        int i;
        if (this.transitionRulesInitialized) {
            return;
        }
        this.initialRule = null;
        this.firstTZTransition = null;
        this.firstFinalTZTransition = null;
        this.historicRules = null;
        this.firstTZTransitionIdx = 0;
        this.finalZoneWithStartYear = null;
        String str = getID() + "(STD)";
        String str2 = getID() + "(DST)";
        int iInitialRawOffset = initialRawOffset() * 1000;
        int iInitialDstOffset = initialDstOffset() * 1000;
        this.initialRule = new InitialTimeZoneRule(iInitialDstOffset == 0 ? str : str2, iInitialRawOffset, iInitialDstOffset);
        if (this.transitionCount > 0) {
            int i2 = 0;
            while (i2 < this.transitionCount && getInt(this.typeMapData[i2]) == 0) {
                this.firstTZTransitionIdx++;
                i2++;
            }
            if (i2 != this.transitionCount) {
                long[] jArr = new long[this.transitionCount];
                int i3 = 0;
                while (true) {
                    long j = 1000;
                    if (i3 >= this.typeCount) {
                        break;
                    }
                    int i4 = this.firstTZTransitionIdx;
                    int i5 = 0;
                    while (i4 < this.transitionCount) {
                        if (i3 == getInt(this.typeMapData[i4])) {
                            long j2 = this.transitionTimes64[i4] * j;
                            i = i3;
                            if (j2 < this.finalStartMillis) {
                                jArr[i5] = j2;
                                i5++;
                            }
                        } else {
                            i = i3;
                        }
                        i4++;
                        i3 = i;
                        j = 1000;
                    }
                    int i6 = i3;
                    if (i5 > 0) {
                        long[] jArr2 = new long[i5];
                        System.arraycopy(jArr, 0, jArr2, 0, i5);
                        int i7 = i6 * 2;
                        int i8 = this.typeOffsets[i7] * 1000;
                        int i9 = this.typeOffsets[i7 + 1] * 1000;
                        if (this.historicRules == null) {
                            this.historicRules = new TimeArrayTimeZoneRule[this.typeCount];
                        }
                        this.historicRules[i6] = new TimeArrayTimeZoneRule(i9 == 0 ? str : str2, i8, i9, jArr2, 2);
                    }
                    i3 = i6 + 1;
                }
                this.firstTZTransition = new TimeZoneTransition(this.transitionTimes64[this.firstTZTransitionIdx] * 1000, this.initialRule, this.historicRules[getInt(this.typeMapData[this.firstTZTransitionIdx])]);
            }
        }
        if (this.finalZone != null) {
            long time = (long) this.finalStartMillis;
            if (this.finalZone.useDaylightTime()) {
                SimpleTimeZone simpleTimeZone = (SimpleTimeZone) this.finalZone.clone();
                this.finalZoneWithStartYear = simpleTimeZone;
                simpleTimeZone.setStartYear(this.finalStartYear);
                TimeZoneTransition nextTransition = this.finalZoneWithStartYear.getNextTransition(time, false);
                timeArrayTimeZoneRule = nextTransition.getTo();
                time = nextTransition.getTime();
            } else {
                this.finalZoneWithStartYear = this.finalZone;
                timeArrayTimeZoneRule = new TimeArrayTimeZoneRule(this.finalZone.getID(), this.finalZone.getRawOffset(), 0, new long[]{time}, 2);
            }
            TimeZoneRule timeZoneRule = this.transitionCount > 0 ? this.historicRules[getInt(this.typeMapData[this.transitionCount - 1])] : null;
            if (timeZoneRule == null) {
                timeZoneRule = this.initialRule;
            }
            this.firstFinalTZTransition = new TimeZoneTransition(time, timeZoneRule, timeArrayTimeZoneRule);
        }
        this.transitionRulesInitialized = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void readObject(java.io.ObjectInputStream r6) throws java.lang.ClassNotFoundException, java.io.IOException {
        /*
            r5 = this;
            r6.defaultReadObject()
            int r6 = r5.serialVersionOnStream
            r0 = 0
            r1 = 1
            if (r6 >= r1) goto L28
            java.lang.String r6 = r5.getID()
            if (r6 == 0) goto L22
            java.lang.String r2 = "com/ibm/icu/impl/data/icudt69b"
            java.lang.String r3 = "zoneinfo64"
            java.lang.ClassLoader r4 = com.ibm.icu.impl.ICUResourceBundle.ICU_DATA_CLASS_LOADER     // Catch: java.lang.Exception -> L22
            com.ibm.icu.util.UResourceBundle r2 = com.ibm.icu.util.UResourceBundle.getBundleInstance(r2, r3, r4)     // Catch: java.lang.Exception -> L22
            com.ibm.icu.util.UResourceBundle r3 = com.ibm.icu.impl.ZoneMeta.openOlsonResource(r2, r6)     // Catch: java.lang.Exception -> L22
            r5.construct(r2, r3, r6)     // Catch: java.lang.Exception -> L22
            goto L23
        L22:
            r1 = 0
        L23:
            if (r1 != 0) goto L28
            r5.constructEmpty()
        L28:
            r5.transitionRulesInitialized = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.OlsonTimeZone.readObject(java.io.ObjectInputStream):void");
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
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone) super.cloneAsThawed();
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            olsonTimeZone.finalZone = (SimpleTimeZone) simpleTimeZone.clone();
        }
        olsonTimeZone.isFrozen = false;
        return olsonTimeZone;
    }
}
