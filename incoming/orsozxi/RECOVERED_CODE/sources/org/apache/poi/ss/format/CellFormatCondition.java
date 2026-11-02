package org.apache.poi.ss.format;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class CellFormatCondition {
    private static final int EQ = 4;
    private static final int GE = 3;
    private static final int GT = 2;
    private static final int LE = 1;
    private static final int LT = 0;
    private static final int NE = 5;
    private static final Map<String, Integer> TESTS;

    public abstract boolean pass(double d);

    static {
        HashMap map = new HashMap();
        TESTS = map;
        map.put("<", 0);
        TESTS.put("<=", 1);
        TESTS.put(">", 2);
        TESTS.put(">=", 3);
        TESTS.put(ContainerUtils.KEY_VALUE_DELIMITER, 4);
        TESTS.put("==", 4);
        TESTS.put("!=", 5);
        TESTS.put("<>", 5);
    }

    public static CellFormatCondition getInstance(String str, String str2) throws NumberFormatException {
        if (!TESTS.containsKey(str)) {
            throw new IllegalArgumentException("Unknown test: " + str);
        }
        int iIntValue = TESTS.get(str).intValue();
        final double d = Double.parseDouble(str2);
        if (iIntValue == 0) {
            return new CellFormatCondition() { // from class: org.apache.poi.ss.format.CellFormatCondition.1
                @Override // org.apache.poi.ss.format.CellFormatCondition
                public boolean pass(double d2) {
                    return d2 < d;
                }
            };
        }
        if (iIntValue == 1) {
            return new CellFormatCondition() { // from class: org.apache.poi.ss.format.CellFormatCondition.2
                @Override // org.apache.poi.ss.format.CellFormatCondition
                public boolean pass(double d2) {
                    return d2 <= d;
                }
            };
        }
        if (iIntValue == 2) {
            return new CellFormatCondition() { // from class: org.apache.poi.ss.format.CellFormatCondition.3
                @Override // org.apache.poi.ss.format.CellFormatCondition
                public boolean pass(double d2) {
                    return d2 > d;
                }
            };
        }
        if (iIntValue == 3) {
            return new CellFormatCondition() { // from class: org.apache.poi.ss.format.CellFormatCondition.4
                @Override // org.apache.poi.ss.format.CellFormatCondition
                public boolean pass(double d2) {
                    return d2 >= d;
                }
            };
        }
        if (iIntValue == 4) {
            return new CellFormatCondition() { // from class: org.apache.poi.ss.format.CellFormatCondition.5
                @Override // org.apache.poi.ss.format.CellFormatCondition
                public boolean pass(double d2) {
                    return d2 == d;
                }
            };
        }
        if (iIntValue == 5) {
            return new CellFormatCondition() { // from class: org.apache.poi.ss.format.CellFormatCondition.6
                @Override // org.apache.poi.ss.format.CellFormatCondition
                public boolean pass(double d2) {
                    return d2 != d;
                }
            };
        }
        throw new IllegalArgumentException("Cannot create for test number " + iIntValue + "(\"" + str + "\")");
    }
}
