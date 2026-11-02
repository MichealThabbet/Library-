package com.ibm.icu.impl;

import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.OutputInt;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class UnicodeSetStringSpan {
    public static final int ALL = 127;
    static final short ALL_CP_CONTAINED = 255;
    public static final int BACK = 16;
    public static final int BACK_UTF16_CONTAINED = 18;
    public static final int BACK_UTF16_NOT_CONTAINED = 17;
    public static final int CONTAINED = 2;
    public static final int FWD = 32;
    public static final int FWD_UTF16_CONTAINED = 34;
    public static final int FWD_UTF16_NOT_CONTAINED = 33;
    static final short LONG_SPAN = 254;
    public static final int NOT_CONTAINED = 1;
    public static final int WITH_COUNT = 64;
    private boolean all;
    private final int maxLength16;
    private OffsetList offsets;
    private boolean someRelevant;
    private short[] spanLengths;
    private UnicodeSet spanNotSet;
    private UnicodeSet spanSet;
    private ArrayList<String> strings;

    static short makeSpanLengthByte(int i) {
        if (i < 254) {
            return (short) i;
        }
        return (short) 254;
    }

    public UnicodeSetStringSpan(UnicodeSet unicodeSet, ArrayList<String> arrayList, int i) {
        this.spanSet = new UnicodeSet(0, 1114111);
        this.strings = arrayList;
        this.all = i == 127;
        this.spanSet.retainAll(unicodeSet);
        int i2 = i & 1;
        if (i2 != 0) {
            this.spanNotSet = this.spanSet;
        }
        this.offsets = new OffsetList();
        int size = this.strings.size();
        this.someRelevant = false;
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            String str = this.strings.get(i3);
            int length = str.length();
            if (length == 0) {
                this.strings.remove(i3);
                size--;
            } else {
                if (this.spanSet.span(str, UnicodeSet.SpanCondition.CONTAINED) < length) {
                    this.someRelevant = true;
                }
                i4 = length > i4 ? length : i4;
                i3++;
            }
        }
        this.maxLength16 = i4;
        if (this.someRelevant || (i & 64) != 0) {
            if (this.all) {
                this.spanSet.freeze();
            }
            this.spanLengths = new short[this.all ? size * 2 : size];
            int i5 = this.all ? size : 0;
            for (int i6 = 0; i6 < size; i6++) {
                String str2 = this.strings.get(i6);
                int length2 = str2.length();
                int iSpan = this.spanSet.span(str2, UnicodeSet.SpanCondition.CONTAINED);
                if (iSpan < length2) {
                    if ((i & 2) != 0) {
                        if ((i & 32) != 0) {
                            this.spanLengths[i6] = makeSpanLengthByte(iSpan);
                        }
                        if ((i & 16) != 0) {
                            this.spanLengths[i5 + i6] = makeSpanLengthByte(length2 - this.spanSet.spanBack(str2, length2, UnicodeSet.SpanCondition.CONTAINED));
                        }
                    } else {
                        short[] sArr = this.spanLengths;
                        sArr[i5 + i6] = 0;
                        sArr[i6] = 0;
                    }
                    if (i2 != 0) {
                        if ((i & 32) != 0) {
                            addToSpanNotSet(str2.codePointAt(0));
                        }
                        if ((i & 16) != 0) {
                            addToSpanNotSet(str2.codePointBefore(length2));
                        }
                    }
                } else if (this.all) {
                    short[] sArr2 = this.spanLengths;
                    sArr2[i5 + i6] = 255;
                    sArr2[i6] = 255;
                } else {
                    this.spanLengths[i6] = 255;
                }
            }
            if (this.all) {
                this.spanNotSet.freeze();
            }
        }
    }

    public UnicodeSetStringSpan(UnicodeSetStringSpan unicodeSetStringSpan, ArrayList<String> arrayList) {
        this.spanSet = unicodeSetStringSpan.spanSet;
        this.strings = arrayList;
        this.maxLength16 = unicodeSetStringSpan.maxLength16;
        this.someRelevant = unicodeSetStringSpan.someRelevant;
        this.all = true;
        if (Utility.sameObjects(unicodeSetStringSpan.spanNotSet, unicodeSetStringSpan.spanSet)) {
            this.spanNotSet = this.spanSet;
        } else {
            this.spanNotSet = (UnicodeSet) unicodeSetStringSpan.spanNotSet.clone();
        }
        this.offsets = new OffsetList();
        this.spanLengths = (short[]) unicodeSetStringSpan.spanLengths.clone();
    }

    public boolean needsStringSpanUTF16() {
        return this.someRelevant;
    }

    public boolean contains(int i) {
        return this.spanSet.contains(i);
    }

    private void addToSpanNotSet(int i) {
        if (Utility.sameObjects(this.spanNotSet, null) || Utility.sameObjects(this.spanNotSet, this.spanSet)) {
            if (this.spanSet.contains(i)) {
                return;
            } else {
                this.spanNotSet = this.spanSet.cloneAsThawed();
            }
        }
        this.spanNotSet.add(i);
    }

    public int span(CharSequence charSequence, int i, UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return spanNot(charSequence, i, null);
        }
        int iSpan = this.spanSet.span(charSequence, i, UnicodeSet.SpanCondition.CONTAINED);
        return iSpan == charSequence.length() ? iSpan : spanWithStrings(charSequence, i, iSpan, spanCondition);
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x00db, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized int spanWithStrings(java.lang.CharSequence r18, int r19, int r20, com.ibm.icu.text.UnicodeSet.SpanCondition r21) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.UnicodeSetStringSpan.spanWithStrings(java.lang.CharSequence, int, int, com.ibm.icu.text.UnicodeSet$SpanCondition):int");
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public int spanAndCount(java.lang.CharSequence r9, int r10, com.ibm.icu.text.UnicodeSet.SpanCondition r11, com.ibm.icu.util.OutputInt r12) {
        /*
            r8 = this;
            com.ibm.icu.text.UnicodeSet$SpanCondition r0 = com.ibm.icu.text.UnicodeSet.SpanCondition.NOT_CONTAINED
            if (r11 != r0) goto L9
            int r9 = r8.spanNot(r9, r10, r12)
            return r9
        L9:
            com.ibm.icu.text.UnicodeSet$SpanCondition r0 = com.ibm.icu.text.UnicodeSet.SpanCondition.CONTAINED
            if (r11 != r0) goto L12
            int r9 = r8.spanContainedAndCount(r9, r10, r12)
            return r9
        L12:
            java.util.ArrayList<java.lang.String> r11 = r8.strings
            int r11 = r11.size()
            int r0 = r9.length()
            int r1 = r0 - r10
            r2 = 0
            r3 = 0
        L20:
            if (r1 == 0) goto L53
            com.ibm.icu.text.UnicodeSet r4 = r8.spanSet
            int r4 = spanOne(r4, r9, r10, r1)
            if (r4 <= 0) goto L2b
            goto L2c
        L2b:
            r4 = 0
        L2c:
            r5 = 0
        L2d:
            if (r5 >= r11) goto L49
            java.util.ArrayList<java.lang.String> r6 = r8.strings
            java.lang.Object r6 = r6.get(r5)
            java.lang.String r6 = (java.lang.String) r6
            int r7 = r6.length()
            if (r4 >= r7) goto L46
            if (r7 > r1) goto L46
            boolean r6 = matches16CPB(r9, r10, r0, r6, r7)
            if (r6 == 0) goto L46
            r4 = r7
        L46:
            int r5 = r5 + 1
            goto L2d
        L49:
            if (r4 != 0) goto L4e
            r12.value = r3
            return r10
        L4e:
            int r3 = r3 + 1
            int r10 = r10 + r4
            int r1 = r1 - r4
            goto L20
        L53:
            r12.value = r3
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.UnicodeSetStringSpan.spanAndCount(java.lang.CharSequence, int, com.ibm.icu.text.UnicodeSet$SpanCondition, com.ibm.icu.util.OutputInt):int");
    }

    private synchronized int spanContainedAndCount(CharSequence charSequence, int i, OutputInt outputInt) {
        this.offsets.setMaxLength(this.maxLength16);
        int size = this.strings.size();
        int length = charSequence.length();
        int i2 = length - i;
        int i3 = 0;
        while (i2 != 0) {
            int iSpanOne = spanOne(this.spanSet, charSequence, i, i2);
            if (iSpanOne > 0) {
                this.offsets.addOffsetAndCount(iSpanOne, i3 + 1);
            }
            for (int i4 = 0; i4 < size; i4++) {
                String str = this.strings.get(i4);
                int length2 = str.length();
                if (length2 <= i2) {
                    int i5 = i3 + 1;
                    if (!this.offsets.hasCountAtOffset(length2, i5) && matches16CPB(charSequence, i, length, str, length2)) {
                        this.offsets.addOffsetAndCount(length2, i5);
                    }
                }
            }
            if (this.offsets.isEmpty()) {
                outputInt.value = i3;
                return i;
            }
            int iPopMinimum = this.offsets.popMinimum(outputInt);
            i += iPopMinimum;
            i2 -= iPopMinimum;
            i3 = outputInt.value;
        }
        outputInt.value = i3;
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x00f7, code lost:
    
        return r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized int spanBack(java.lang.CharSequence r18, int r19, com.ibm.icu.text.UnicodeSet.SpanCondition r20) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.UnicodeSetStringSpan.spanBack(java.lang.CharSequence, int, com.ibm.icu.text.UnicodeSet$SpanCondition):int");
    }

    private int spanNot(CharSequence charSequence, int i, OutputInt outputInt) {
        int iSpanAndCount;
        int i2;
        int iSpanOne;
        String str;
        int length;
        int length2 = charSequence.length();
        int size = this.strings.size();
        int i3 = 0;
        do {
            if (outputInt == null) {
                iSpanAndCount = this.spanNotSet.span(charSequence, i, UnicodeSet.SpanCondition.NOT_CONTAINED);
            } else {
                iSpanAndCount = this.spanNotSet.spanAndCount(charSequence, i, UnicodeSet.SpanCondition.NOT_CONTAINED, outputInt);
                i3 += outputInt.value;
                outputInt.value = i3;
            }
            if (iSpanAndCount == length2) {
                return length2;
            }
            i2 = length2 - iSpanAndCount;
            iSpanOne = spanOne(this.spanSet, charSequence, iSpanAndCount, i2);
            if (iSpanOne > 0) {
                return iSpanAndCount;
            }
            for (int i4 = 0; i4 < size; i4++) {
                if (this.spanLengths[i4] != 255 && (length = (str = this.strings.get(i4)).length()) <= i2 && matches16CPB(charSequence, iSpanAndCount, length2, str, length)) {
                    return iSpanAndCount;
                }
            }
            i = iSpanAndCount - iSpanOne;
            i3++;
        } while (i2 + iSpanOne != 0);
        if (outputInt != null) {
            outputInt.value = i3;
        }
        return length2;
    }

    private int spanNotBack(CharSequence charSequence, int i) {
        String str;
        int length;
        int size = this.strings.size();
        int i2 = i;
        do {
            int iSpanBack = this.spanNotSet.spanBack(charSequence, i2, UnicodeSet.SpanCondition.NOT_CONTAINED);
            if (iSpanBack == 0) {
                return 0;
            }
            int iSpanOneBack = spanOneBack(this.spanSet, charSequence, iSpanBack);
            if (iSpanOneBack > 0) {
                return iSpanBack;
            }
            for (int i3 = 0; i3 < size; i3++) {
                if (this.spanLengths[i3] != 255 && (length = (str = this.strings.get(i3)).length()) <= iSpanBack && matches16CPB(charSequence, iSpanBack - length, i, str, length)) {
                    return iSpanBack;
                }
            }
            i2 = iSpanBack + iSpanOneBack;
        } while (i2 != 0);
        return 0;
    }

    private static boolean matches16(CharSequence charSequence, int i, String str, int i2) {
        int i3 = i + i2;
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return true;
            }
            i3--;
            if (charSequence.charAt(i3) != str.charAt(i4)) {
                return false;
            }
            i2 = i4;
        }
    }

    static boolean matches16CPB(CharSequence charSequence, int i, int i2, String str, int i3) {
        int i4;
        return matches16(charSequence, i, str, i3) && !((i > 0 && Character.isHighSurrogate(charSequence.charAt(i + (-1))) && Character.isLowSurrogate(charSequence.charAt(i))) || ((i4 = i + i3) < i2 && Character.isHighSurrogate(charSequence.charAt(i4 + (-1))) && Character.isLowSurrogate(charSequence.charAt(i4))));
    }

    static int spanOne(UnicodeSet unicodeSet, CharSequence charSequence, int i, int i2) {
        char cCharAt = charSequence.charAt(i);
        if (cCharAt >= 55296 && cCharAt <= 56319 && i2 >= 2) {
            char cCharAt2 = charSequence.charAt(i + 1);
            if (UTF16.isTrailSurrogate(cCharAt2)) {
                return unicodeSet.contains(Character.toCodePoint(cCharAt, cCharAt2)) ? 2 : -2;
            }
        }
        return unicodeSet.contains(cCharAt) ? 1 : -1;
    }

    static int spanOneBack(UnicodeSet unicodeSet, CharSequence charSequence, int i) {
        char cCharAt = charSequence.charAt(i - 1);
        if (cCharAt >= 56320 && cCharAt <= 57343 && i >= 2) {
            char cCharAt2 = charSequence.charAt(i - 2);
            if (UTF16.isLeadSurrogate(cCharAt2)) {
                return unicodeSet.contains(Character.toCodePoint(cCharAt2, cCharAt)) ? 2 : -2;
            }
        }
        return unicodeSet.contains(cCharAt) ? 1 : -1;
    }

    private static final class OffsetList {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int length;
        private int[] list = new int[16];
        private int start;

        public void setMaxLength(int i) {
            if (i > this.list.length) {
                this.list = new int[i];
            }
            clear();
        }

        public void clear() {
            int length = this.list.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    this.list[i] = 0;
                    length = i;
                } else {
                    this.length = 0;
                    this.start = 0;
                    return;
                }
            }
        }

        public boolean isEmpty() {
            return this.length == 0;
        }

        public void shift(int i) {
            int length = this.start + i;
            int[] iArr = this.list;
            if (length >= iArr.length) {
                length -= iArr.length;
            }
            int[] iArr2 = this.list;
            if (iArr2[length] != 0) {
                iArr2[length] = 0;
                this.length--;
            }
            this.start = length;
        }

        public void addOffset(int i) {
            int length = this.start + i;
            int[] iArr = this.list;
            if (length >= iArr.length) {
                length -= iArr.length;
            }
            this.list[length] = 1;
            this.length++;
        }

        public void addOffsetAndCount(int i, int i2) {
            int length = this.start + i;
            int[] iArr = this.list;
            if (length >= iArr.length) {
                length -= iArr.length;
            }
            int[] iArr2 = this.list;
            if (iArr2[length] == 0) {
                iArr2[length] = i2;
                this.length++;
            } else if (i2 < iArr2[length]) {
                iArr2[length] = i2;
            }
        }

        public boolean containsOffset(int i) {
            int length = this.start + i;
            int[] iArr = this.list;
            if (length >= iArr.length) {
                length -= iArr.length;
            }
            return this.list[length] != 0;
        }

        public boolean hasCountAtOffset(int i, int i2) {
            int length = this.start + i;
            int[] iArr = this.list;
            if (length >= iArr.length) {
                length -= iArr.length;
            }
            int i3 = this.list[length];
            return i3 != 0 && i3 <= i2;
        }

        public int popMinimum(OutputInt outputInt) {
            int[] iArr;
            int[] iArr2;
            int i;
            int i2;
            int i3 = this.start;
            do {
                i3++;
                iArr = this.list;
                if (i3 < iArr.length) {
                    i2 = iArr[i3];
                } else {
                    int length = iArr.length - this.start;
                    int i4 = 0;
                    while (true) {
                        iArr2 = this.list;
                        i = iArr2[i4];
                        if (i != 0) {
                            break;
                        }
                        i4++;
                    }
                    iArr2[i4] = 0;
                    this.length--;
                    this.start = i4;
                    if (outputInt != null) {
                        outputInt.value = i;
                    }
                    return length + i4;
                }
            } while (i2 == 0);
            iArr[i3] = 0;
            this.length--;
            int i5 = i3 - this.start;
            this.start = i3;
            if (outputInt != null) {
                outputInt.value = i2;
            }
            return i5;
        }
    }
}
