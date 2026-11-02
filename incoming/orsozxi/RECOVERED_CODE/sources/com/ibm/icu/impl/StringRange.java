package com.ibm.icu.impl;

import com.ibm.icu.lang.CharSequences;
import com.ibm.icu.util.ICUException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class StringRange {
    public static final Comparator<int[]> COMPARE_INT_ARRAYS = new Comparator<int[]>() { // from class: com.ibm.icu.impl.StringRange.1
        @Override // java.util.Comparator
        public int compare(int[] iArr, int[] iArr2) {
            int iMin = Math.min(iArr.length, iArr2.length);
            for (int i = 0; i < iMin; i++) {
                int i2 = iArr[i] - iArr2[i];
                if (i2 != 0) {
                    return i2;
                }
            }
            return iArr.length - iArr2.length;
        }
    };
    private static final boolean DEBUG = false;

    public interface Adder {
        void add(String str, String str2);
    }

    public static void compact(Set<String> set, Adder adder, boolean z, boolean z2) {
        int iCodePointAt;
        if (!z2) {
            String strSubstring = null;
            String str = null;
            String strSubstring2 = null;
            int length = 0;
            int iCodePointBefore = 0;
            for (String str2 : set) {
                if (str != null) {
                    if (str2.regionMatches(0, str, 0, length) && (iCodePointAt = str2.codePointAt(length)) == iCodePointBefore + 1 && str2.length() == Character.charCount(iCodePointAt) + length) {
                        strSubstring2 = str2;
                        iCodePointBefore = iCodePointAt;
                    } else {
                        if (strSubstring2 == null) {
                            strSubstring2 = null;
                        } else if (z) {
                            strSubstring2 = strSubstring2.substring(length, strSubstring2.length());
                        }
                        adder.add(str, strSubstring2);
                    }
                }
                iCodePointBefore = str2.codePointBefore(str2.length());
                length = str2.length() - Character.charCount(iCodePointBefore);
                strSubstring2 = null;
                str = str2;
            }
            if (strSubstring2 != null) {
                strSubstring = !z ? strSubstring2 : strSubstring2.substring(length, strSubstring2.length());
            }
            adder.add(str, strSubstring);
            return;
        }
        Relation relationOf = Relation.of(new TreeMap(), TreeSet.class);
        Iterator<String> it2 = set.iterator();
        while (it2.hasNext()) {
            Ranges ranges = new Ranges(it2.next());
            relationOf.put(ranges.size(), ranges);
        }
        for (Map.Entry entry : relationOf.keyValuesSet()) {
            Iterator<Ranges> it3 = compact(((Integer) entry.getKey()).intValue(), (Set) entry.getValue()).iterator();
            while (it3.hasNext()) {
                Ranges next = it3.next();
                adder.add(next.start(), next.end(z));
            }
        }
    }

    public static void compact(Set<String> set, Adder adder, boolean z) {
        compact(set, adder, z, false);
    }

    private static LinkedList<Ranges> compact(int i, Set<Ranges> set) {
        LinkedList<Ranges> linkedList = new LinkedList<>(set);
        for (int i2 = i - 1; i2 >= 0; i2--) {
            Ranges ranges = null;
            Iterator<Ranges> it2 = linkedList.iterator();
            while (it2.hasNext()) {
                Ranges next = it2.next();
                if (ranges != null && ranges.merge(i2, next)) {
                    it2.remove();
                } else {
                    ranges = next;
                }
            }
        }
        return linkedList;
    }

    static final class Range implements Comparable<Range> {
        int max;
        int min;

        public Range(int i, int i2) {
            this.min = i;
            this.max = i2;
        }

        public boolean equals(Object obj) {
            return this == obj || (obj != null && (obj instanceof Range) && compareTo((Range) obj) == 0);
        }

        @Override // java.lang.Comparable
        public int compareTo(Range range) {
            int i = this.min - range.min;
            return i != 0 ? i : this.max - range.max;
        }

        public int hashCode() {
            return (this.min * 37) + this.max;
        }

        public String toString() {
            StringBuilder sbAppendCodePoint = new StringBuilder().appendCodePoint(this.min);
            if (this.min != this.max) {
                sbAppendCodePoint.append('~');
                sbAppendCodePoint = sbAppendCodePoint.appendCodePoint(this.max);
            }
            return sbAppendCodePoint.toString();
        }
    }

    static final class Ranges implements Comparable<Ranges> {
        private final Range[] ranges;

        public Ranges(String str) {
            int[] iArrCodePoints = CharSequences.codePoints(str);
            this.ranges = new Range[iArrCodePoints.length];
            for (int i = 0; i < iArrCodePoints.length; i++) {
                this.ranges[i] = new Range(iArrCodePoints[i], iArrCodePoints[i]);
            }
        }

        public boolean merge(int i, Ranges ranges) {
            for (int length = this.ranges.length - 1; length >= 0; length--) {
                if (length == i) {
                    if (this.ranges[length].max != ranges.ranges[length].min - 1) {
                        return false;
                    }
                } else if (!this.ranges[length].equals(ranges.ranges[length])) {
                    return false;
                }
            }
            this.ranges[i].max = ranges.ranges[i].max;
            return true;
        }

        public String start() {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                Range[] rangeArr = this.ranges;
                if (i < rangeArr.length) {
                    sb.appendCodePoint(rangeArr[i].min);
                    i++;
                } else {
                    return sb.toString();
                }
            }
        }

        public String end(boolean z) {
            int iFirstDifference = firstDifference();
            if (iFirstDifference == this.ranges.length) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            if (!z) {
                iFirstDifference = 0;
            }
            while (true) {
                Range[] rangeArr = this.ranges;
                if (iFirstDifference < rangeArr.length) {
                    sb.appendCodePoint(rangeArr[iFirstDifference].max);
                    iFirstDifference++;
                } else {
                    return sb.toString();
                }
            }
        }

        public int firstDifference() {
            int i = 0;
            while (true) {
                Range[] rangeArr = this.ranges;
                if (i < rangeArr.length) {
                    if (rangeArr[i].min != this.ranges[i].max) {
                        return i;
                    }
                    i++;
                } else {
                    return rangeArr.length;
                }
            }
        }

        public Integer size() {
            return Integer.valueOf(this.ranges.length);
        }

        @Override // java.lang.Comparable
        public int compareTo(Ranges ranges) {
            int length = this.ranges.length - ranges.ranges.length;
            if (length != 0) {
                return length;
            }
            int i = 0;
            while (true) {
                Range[] rangeArr = this.ranges;
                if (i >= rangeArr.length) {
                    return 0;
                }
                int iCompareTo = rangeArr[i].compareTo(ranges.ranges[i]);
                if (iCompareTo != 0) {
                    return iCompareTo;
                }
                i++;
            }
        }

        public String toString() {
            String strStart = start();
            String strEnd = end(false);
            if (strEnd == null) {
                return strStart;
            }
            return strStart + "~" + strEnd;
        }
    }

    public static Collection<String> expand(String str, String str2, boolean z, Collection<String> collection) {
        if (str == null || str2 == null) {
            throw new ICUException("Range must have 2 valid strings");
        }
        int[] iArrCodePoints = CharSequences.codePoints(str);
        int[] iArrCodePoints2 = CharSequences.codePoints(str2);
        int length = iArrCodePoints.length - iArrCodePoints2.length;
        if (z && length != 0) {
            throw new ICUException("Range must have equal-length strings");
        }
        if (length < 0) {
            throw new ICUException("Range must have start-length ≥ end-length");
        }
        if (iArrCodePoints2.length == 0) {
            throw new ICUException("Range must have end-length > 0");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.appendCodePoint(iArrCodePoints[i]);
        }
        add(0, length, iArrCodePoints, iArrCodePoints2, sb, collection);
        return collection;
    }

    private static void add(int i, int i2, int[] iArr, int[] iArr2, StringBuilder sb, Collection<String> collection) {
        int i3 = iArr[i + i2];
        int i4 = iArr2[i];
        if (i3 > i4) {
            throw new ICUException("Range must have xᵢ ≤ yᵢ for each index i");
        }
        boolean z = i == iArr2.length - 1;
        int length = sb.length();
        for (int i5 = i3; i5 <= i4; i5++) {
            sb.appendCodePoint(i5);
            if (z) {
                collection.add(sb.toString());
            } else {
                add(i + 1, i2, iArr, iArr2, sb, collection);
            }
            sb.setLength(length);
        }
    }
}
