package org.apache.poi.hssf.util;

/* loaded from: classes3.dex */
public final class RKUtil {
    private RKUtil() {
    }

    public static double decodeNumber(int i) {
        long j = i >> 2;
        double dLongBitsToDouble = (i & 2) == 2 ? j : Double.longBitsToDouble(j << 34);
        return (i & 1) == 1 ? dLongBitsToDouble / 100.0d : dLongBitsToDouble;
    }
}
