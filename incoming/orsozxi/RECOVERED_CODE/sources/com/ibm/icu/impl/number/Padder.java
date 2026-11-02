package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;

/* loaded from: classes2.dex */
public class Padder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String FALLBACK_PADDING_STRING = " ";
    public static final Padder NONE = new Padder(null, -1, null);
    String paddingString;
    PadPosition position;
    int targetWidth;

    public enum PadPosition {
        BEFORE_PREFIX,
        AFTER_PREFIX,
        BEFORE_SUFFIX,
        AFTER_SUFFIX;

        public static PadPosition fromOld(int i) {
            if (i == 0) {
                return BEFORE_PREFIX;
            }
            if (i == 1) {
                return AFTER_PREFIX;
            }
            if (i == 2) {
                return BEFORE_SUFFIX;
            }
            if (i == 3) {
                return AFTER_SUFFIX;
            }
            throw new IllegalArgumentException("Don't know how to map " + i);
        }

        public int toOld() {
            int i = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[ordinal()];
            if (i == 1) {
                return 0;
            }
            if (i == 2) {
                return 1;
            }
            if (i != 3) {
                return i != 4 ? -1 : 3;
            }
            return 2;
        }
    }

    /* renamed from: com.ibm.icu.impl.number.Padder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition;

        static {
            int[] iArr = new int[PadPosition.values().length];
            $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition = iArr;
            try {
                iArr[PadPosition.BEFORE_PREFIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[PadPosition.AFTER_PREFIX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[PadPosition.BEFORE_SUFFIX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[PadPosition.AFTER_SUFFIX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public Padder(String str, int i, PadPosition padPosition) {
        this.paddingString = str == null ? " " : str;
        this.targetWidth = i;
        this.position = padPosition == null ? PadPosition.BEFORE_PREFIX : padPosition;
    }

    public static Padder none() {
        return NONE;
    }

    public static Padder codePoints(int i, int i2, PadPosition padPosition) {
        if (i2 >= 0) {
            return new Padder(String.valueOf(Character.toChars(i)), i2, padPosition);
        }
        throw new IllegalArgumentException("Padding width must not be negative");
    }

    public static Padder forProperties(DecimalFormatProperties decimalFormatProperties) {
        return new Padder(decimalFormatProperties.getPadString(), decimalFormatProperties.getFormatWidth(), decimalFormatProperties.getPadPosition());
    }

    public boolean isValid() {
        return this.targetWidth > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int padAndApply(com.ibm.icu.impl.number.Modifier r5, com.ibm.icu.impl.number.Modifier r6, com.ibm.icu.impl.FormattedStringBuilder r7, int r8, int r9) {
        /*
            r4 = this;
            int r0 = r5.getCodePointCount()
            int r1 = r6.getCodePointCount()
            int r0 = r0 + r1
            int r1 = r4.targetWidth
            int r1 = r1 - r0
            int r0 = r7.codePointCount()
            int r1 = r1 - r0
            r0 = 0
            if (r1 > 0) goto L20
            int r5 = r5.apply(r7, r8, r9)
            int r5 = r5 + r0
            int r9 = r9 + r5
            int r6 = r6.apply(r7, r8, r9)
            int r5 = r5 + r6
            return r5
        L20:
            com.ibm.icu.impl.number.Padder$PadPosition r2 = r4.position
            com.ibm.icu.impl.number.Padder$PadPosition r3 = com.ibm.icu.impl.number.Padder.PadPosition.AFTER_PREFIX
            if (r2 != r3) goto L2e
            java.lang.String r2 = r4.paddingString
            int r2 = addPaddingHelper(r2, r1, r7, r8)
        L2c:
            int r0 = r0 + r2
            goto L3d
        L2e:
            com.ibm.icu.impl.number.Padder$PadPosition r2 = r4.position
            com.ibm.icu.impl.number.Padder$PadPosition r3 = com.ibm.icu.impl.number.Padder.PadPosition.BEFORE_SUFFIX
            if (r2 != r3) goto L3d
            java.lang.String r2 = r4.paddingString
            int r3 = r9 + 0
            int r2 = addPaddingHelper(r2, r1, r7, r3)
            goto L2c
        L3d:
            int r2 = r9 + r0
            int r5 = r5.apply(r7, r8, r2)
            int r0 = r0 + r5
            int r5 = r9 + r0
            int r5 = r6.apply(r7, r8, r5)
            int r0 = r0 + r5
            com.ibm.icu.impl.number.Padder$PadPosition r5 = r4.position
            com.ibm.icu.impl.number.Padder$PadPosition r6 = com.ibm.icu.impl.number.Padder.PadPosition.BEFORE_PREFIX
            if (r5 != r6) goto L59
            java.lang.String r5 = r4.paddingString
            int r5 = addPaddingHelper(r5, r1, r7, r8)
        L57:
            int r0 = r0 + r5
            goto L67
        L59:
            com.ibm.icu.impl.number.Padder$PadPosition r5 = r4.position
            com.ibm.icu.impl.number.Padder$PadPosition r6 = com.ibm.icu.impl.number.Padder.PadPosition.AFTER_SUFFIX
            if (r5 != r6) goto L67
            java.lang.String r5 = r4.paddingString
            int r9 = r9 + r0
            int r5 = addPaddingHelper(r5, r1, r7, r9)
            goto L57
        L67:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.Padder.padAndApply(com.ibm.icu.impl.number.Modifier, com.ibm.icu.impl.number.Modifier, com.ibm.icu.impl.FormattedStringBuilder, int, int):int");
    }

    private static int addPaddingHelper(String str, int i, FormattedStringBuilder formattedStringBuilder, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            formattedStringBuilder.insert(i2, str, (Object) null);
        }
        return str.length() * i;
    }
}
