package com.ibm.icu.impl;

import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class EraRules {
    private static final int DAY_MASK = 255;
    private static final int MAX_ENCODED_START_YEAR = 32767;
    private static final int MONTH_MASK = 65280;
    private static final int YEAR_MASK = -65536;
    private int currentEra;
    private int numEras;
    private int[] startDates;
    private static final int MIN_ENCODED_START_YEAR = -32768;
    public static final int MIN_ENCODED_START = encodeDate(MIN_ENCODED_START_YEAR, 1, 1);

    private static int encodeDate(int i, int i2, int i3) {
        return (i << 16) | (i2 << 8) | i3;
    }

    private static boolean isSet(int i) {
        return i != 0;
    }

    private static boolean isValidRuleStartDate(int i, int i2, int i3) {
        return i >= MIN_ENCODED_START_YEAR && i <= MAX_ENCODED_START_YEAR && i2 >= 1 && i2 <= 12 && i3 >= 1 && i3 <= 31;
    }

    private EraRules(int[] iArr, int i) {
        this.startDates = iArr;
        this.numEras = i;
        initCurrentEra();
    }

    public static EraRules getInstance(CalType calType, boolean z) {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("calendarData").get(calType.getId()).get("eras");
        int size = uResourceBundle.getSize();
        int[] iArr = new int[size];
        UResourceBundleIterator iterator = uResourceBundle.getIterator();
        int i = Integer.MAX_VALUE;
        while (iterator.hasNext()) {
            UResourceBundle next = iterator.next();
            String key = next.getKey();
            try {
                int i2 = Integer.parseInt(key);
                if (i2 < 0 || i2 >= size) {
                    throw new ICUException("Era rule key:" + key + " in era rule data for " + calType.getId() + " must be in range [0, " + (size - 1) + "]");
                }
                if (isSet(iArr[i2])) {
                    throw new ICUException("Dupulicated era rule for rule key:" + key + " in era rule data for " + calType.getId());
                }
                UResourceBundleIterator iterator2 = next.getIterator();
                boolean z2 = false;
                boolean z3 = true;
                while (iterator2.hasNext()) {
                    UResourceBundle next2 = iterator2.next();
                    String key2 = next2.getKey();
                    if (key2.equals("start")) {
                        int[] intVector = next2.getIntVector();
                        if (intVector.length != 3 || !isValidRuleStartDate(intVector[0], intVector[1], intVector[2])) {
                            throw new ICUException("Invalid era rule date data:" + Arrays.toString(intVector) + " in era rule data for " + calType.getId());
                        }
                        iArr[i2] = encodeDate(intVector[0], intVector[1], intVector[2]);
                    } else if (key2.equals("named")) {
                        if (next2.getString().equals("false")) {
                            z3 = false;
                        }
                    } else if (key2.equals("end")) {
                        z2 = true;
                    }
                }
                if (!isSet(iArr[i2])) {
                    if (!z2) {
                        throw new ICUException("Missing era start/end rule date for key:" + key + " in era rule data for " + calType.getId());
                    }
                    if (i2 != 0) {
                        throw new ICUException("Era data for " + key + " in era rule data for " + calType.getId() + " has only end rule.");
                    }
                    iArr[i2] = MIN_ENCODED_START;
                }
                if (z3) {
                    if (i2 >= i) {
                        throw new ICUException("Non-tentative era(" + i2 + ") must be placed before the first tentative era");
                    }
                } else if (i2 < i) {
                    i = i2;
                }
            } catch (NumberFormatException unused) {
                throw new ICUException("Invald era rule key:" + key + " in era rule data for " + calType.getId());
            }
        }
        if (i < Integer.MAX_VALUE && !z) {
            return new EraRules(iArr, i);
        }
        return new EraRules(iArr, size);
    }

    public int getNumberOfEras() {
        return this.numEras;
    }

    public int[] getStartDate(int i, int[] iArr) {
        if (i < 0 || i >= this.numEras) {
            throw new IllegalArgumentException("eraIdx is out of range");
        }
        return decodeDate(this.startDates[i], iArr);
    }

    public int getStartYear(int i) {
        if (i < 0 || i >= this.numEras) {
            throw new IllegalArgumentException("eraIdx is out of range");
        }
        return decodeDate(this.startDates[i], null)[0];
    }

    public int getEraIndex(int i, int i2, int i3) {
        if (i2 < 1 || i2 > 12 || i3 < 1 || i3 > 31) {
            throw new IllegalArgumentException("Illegal date - year:" + i + "month:" + i2 + "day:" + i3);
        }
        int i4 = this.numEras;
        int currentEraIndex = compareEncodedDateWithYMD(this.startDates[getCurrentEraIndex()], i, i2, i3) <= 0 ? getCurrentEraIndex() : 0;
        while (currentEraIndex < i4 - 1) {
            int i5 = (currentEraIndex + i4) / 2;
            if (compareEncodedDateWithYMD(this.startDates[i5], i, i2, i3) <= 0) {
                currentEraIndex = i5;
            } else {
                i4 = i5;
            }
        }
        return currentEraIndex;
    }

    public int getCurrentEraIndex() {
        return this.currentEra;
    }

    private void initCurrentEra() {
        int[] iArrTimeToFields = Grego.timeToFields(System.currentTimeMillis() + TimeZone.getDefault().getOffset(r0), null);
        int iEncodeDate = encodeDate(iArrTimeToFields[0], iArrTimeToFields[1] + 1, iArrTimeToFields[2]);
        int i = this.numEras - 1;
        while (i > 0 && iEncodeDate < this.startDates[i]) {
            i--;
        }
        this.currentEra = i;
    }

    private static int[] decodeDate(int i, int[] iArr) {
        int i2;
        int i3;
        int i4;
        if (i == MIN_ENCODED_START) {
            i4 = Integer.MIN_VALUE;
            i3 = 1;
            i2 = 1;
        } else {
            i2 = (65280 & i) >> 8;
            i3 = i & 255;
            i4 = ((-65536) & i) >> 16;
        }
        if (iArr == null || iArr.length < 3) {
            return new int[]{i4, i2, i3};
        }
        iArr[0] = i4;
        iArr[1] = i2;
        iArr[2] = i3;
        return iArr;
    }

    private static int compareEncodedDateWithYMD(int i, int i2, int i3, int i4) {
        int iEncodeDate;
        if (i2 < MIN_ENCODED_START_YEAR) {
            if (i == MIN_ENCODED_START) {
                return (i2 > Integer.MIN_VALUE || i3 > 1 || i4 > 1) ? -1 : 0;
            }
            return 1;
        }
        if (i2 <= MAX_ENCODED_START_YEAR && i >= (iEncodeDate = encodeDate(i2, i3, i4))) {
            return i == iEncodeDate ? 0 : 1;
        }
        return -1;
    }
}
