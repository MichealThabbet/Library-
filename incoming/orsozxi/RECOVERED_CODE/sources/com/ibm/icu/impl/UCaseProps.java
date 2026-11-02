package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class UCaseProps {
    private static final int ABOVE = 64;
    private static final int CLOSURE_MAX_LENGTH = 15;
    private static final String DATA_FILE_NAME = "ucase.icu";
    private static final String DATA_NAME = "ucase";
    private static final String DATA_TYPE = "icu";
    private static final int DELTA_SHIFT = 7;
    private static final int DOT_MASK = 96;
    private static final int EXCEPTION = 8;
    private static final int EXC_CLOSURE = 6;
    private static final int EXC_CONDITIONAL_FOLD = 32768;
    private static final int EXC_CONDITIONAL_SPECIAL = 16384;
    private static final int EXC_DELTA = 4;
    private static final int EXC_DELTA_IS_NEGATIVE = 1024;
    private static final int EXC_DOT_SHIFT = 7;
    private static final int EXC_DOUBLE_SLOTS = 256;
    private static final int EXC_FOLD = 1;
    private static final int EXC_FULL_MAPPINGS = 7;
    private static final int EXC_LOWER = 0;
    private static final int EXC_NO_SIMPLE_CASE_FOLDING = 512;
    private static final int EXC_SENSITIVE = 2048;
    private static final int EXC_SHIFT = 4;
    private static final int EXC_TITLE = 3;
    private static final int EXC_UPPER = 2;
    private static final int FMT = 1665225541;
    static final int FOLD_CASE_OPTIONS_MASK = 7;
    private static final int FULL_LOWER = 15;
    static final int IGNORABLE = 4;
    public static final UCaseProps INSTANCE;
    private static final int IX_EXC_LENGTH = 3;
    private static final int IX_TOP = 16;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_UNFOLD_LENGTH = 4;
    static final int LOC_ARMENIAN = 6;
    public static final int LOC_DUTCH = 5;
    static final int LOC_GREEK = 4;
    static final int LOC_LITHUANIAN = 3;
    public static final int LOC_ROOT = 1;
    static final int LOC_TURKISH = 2;
    public static final int LOWER = 1;
    public static final int MAX_STRING_LENGTH = 31;
    public static final int NONE = 0;
    private static final int OTHER_ACCENT = 96;
    private static final int SENSITIVE = 16;
    private static final int SOFT_DOTTED = 32;
    public static final int TITLE = 3;
    public static final int TYPE_MASK = 3;
    private static final int UNFOLD_ROWS = 0;
    private static final int UNFOLD_ROW_WIDTH = 1;
    private static final int UNFOLD_STRING_WIDTH = 2;
    public static final int UPPER = 2;
    private static final String iDot = "i̇";
    private static final String iDotAcute = "i̇́";
    private static final String iDotGrave = "i̇̀";
    private static final String iDotTilde = "i̇̃";
    private static final String iOgonekDot = "į̇";
    private static final String jDot = "j̇";

    /* renamed from: exceptions, reason: collision with root package name */
    private String f15exceptions;
    private int[] indexes;
    private Trie2_16 trie;
    private char[] unfold;
    private static final byte[] flagsOffset = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8};
    public static final StringBuilder dummyStringBuilder = new StringBuilder();

    public interface ContextIterator {
        int next();

        void reset(int i);
    }

    static final int getDelta(int i) {
        return ((short) i) >> 7;
    }

    private static final int getExceptionsOffset(int i) {
        return i >> 4;
    }

    private static final int getTypeAndIgnorableFromProps(int i) {
        return i & 7;
    }

    static final int getTypeFromProps(int i) {
        return i & 3;
    }

    private static final boolean hasSlot(int i, int i2) {
        return (i & (1 << i2)) != 0;
    }

    static final boolean isUpperOrTitleFromProps(int i) {
        return (i & 2) != 0;
    }

    static final boolean propsHasException(int i) {
        return (i & 8) != 0;
    }

    private UCaseProps() throws IOException {
        readData(ICUBinary.getRequiredData(DATA_FILE_NAME));
    }

    private final void readData(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, FMT, new IsAcceptable());
        int i = byteBuffer.getInt();
        if (i < 16) {
            throw new IOException("indexes[0] too small in ucase.icu");
        }
        int[] iArr = new int[i];
        this.indexes = iArr;
        iArr[0] = i;
        for (int i2 = 1; i2 < i; i2++) {
            this.indexes[i2] = byteBuffer.getInt();
        }
        Trie2_16 trie2_16CreateFromSerialized = Trie2_16.createFromSerialized(byteBuffer);
        this.trie = trie2_16CreateFromSerialized;
        int i3 = this.indexes[2];
        int serializedLength = trie2_16CreateFromSerialized.getSerializedLength();
        if (serializedLength > i3) {
            throw new IOException("ucase.icu: not enough bytes for the trie");
        }
        ICUBinary.skipBytes(byteBuffer, i3 - serializedLength);
        int i4 = this.indexes[3];
        if (i4 > 0) {
            this.f15exceptions = ICUBinary.getString(byteBuffer, i4, 0);
        }
        int i5 = this.indexes[4];
        if (i5 > 0) {
            this.unfold = ICUBinary.getChars(byteBuffer, i5, 0);
        }
    }

    private static final class IsAcceptable implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override // com.ibm.icu.impl.ICUBinary.Authenticate
        public boolean isDataVersionAcceptable(byte[] bArr) {
            return bArr[0] == 4;
        }
    }

    public final void addPropertyStarts(UnicodeSet unicodeSet) {
        Iterator<Trie2.Range> it2 = this.trie.iterator();
        while (it2.hasNext()) {
            Trie2.Range next = it2.next();
            if (next.leadSurrogate) {
                return;
            } else {
                unicodeSet.add(next.startCodePoint);
            }
        }
    }

    static {
        try {
            INSTANCE = new UCaseProps();
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    private static final byte slotOffset(int i, int i2) {
        return flagsOffset[i & ((1 << i2) - 1)];
    }

    private final long getSlotValueAndOffset(int i, int i2, int i3) {
        int iSlotOffset;
        long jCharAt;
        if ((i & 256) == 0) {
            iSlotOffset = i3 + slotOffset(i, i2);
            jCharAt = this.f15exceptions.charAt(iSlotOffset);
        } else {
            long jCharAt2 = (this.f15exceptions.charAt(r7) << 16) | this.f15exceptions.charAt(r6);
            iSlotOffset = i3 + (slotOffset(i, i2) * 2) + 1;
            jCharAt = jCharAt2;
        }
        return jCharAt | (iSlotOffset << 32);
    }

    private final int getSlotValue(int i, int i2, int i3) {
        if ((i & 256) == 0) {
            return this.f15exceptions.charAt(i3 + slotOffset(i, i2));
        }
        int iSlotOffset = i3 + (slotOffset(i, i2) * 2);
        return (this.f15exceptions.charAt(iSlotOffset) << 16) | this.f15exceptions.charAt(iSlotOffset + 1);
    }

    public final int tolower(int i) {
        int i2 = this.trie.get(i);
        if (!propsHasException(i2)) {
            return isUpperOrTitleFromProps(i2) ? i + getDelta(i2) : i;
        }
        int exceptionsOffset = getExceptionsOffset(i2);
        int i3 = exceptionsOffset + 1;
        char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
        if (!hasSlot(cCharAt, 4) || !isUpperOrTitleFromProps(i2)) {
            return hasSlot(cCharAt, 0) ? getSlotValue(cCharAt, 0, i3) : i;
        }
        int slotValue = getSlotValue(cCharAt, 4, i3);
        return (cCharAt & 1024) == 0 ? i + slotValue : i - slotValue;
    }

    public final int toupper(int i) {
        int i2 = this.trie.get(i);
        if (!propsHasException(i2)) {
            return getTypeFromProps(i2) == 1 ? i + getDelta(i2) : i;
        }
        int exceptionsOffset = getExceptionsOffset(i2);
        int i3 = exceptionsOffset + 1;
        char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
        if (!hasSlot(cCharAt, 4) || getTypeFromProps(i2) != 1) {
            return hasSlot(cCharAt, 2) ? getSlotValue(cCharAt, 2, i3) : i;
        }
        int slotValue = getSlotValue(cCharAt, 4, i3);
        return (cCharAt & 1024) == 0 ? i + slotValue : i - slotValue;
    }

    public final int totitle(int i) {
        int i2 = this.trie.get(i);
        if (!propsHasException(i2)) {
            return getTypeFromProps(i2) == 1 ? i + getDelta(i2) : i;
        }
        int exceptionsOffset = getExceptionsOffset(i2);
        int i3 = exceptionsOffset + 1;
        char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
        if (hasSlot(cCharAt, 4) && getTypeFromProps(i2) == 1) {
            int slotValue = getSlotValue(cCharAt, 4, i3);
            return (cCharAt & 1024) == 0 ? i + slotValue : i - slotValue;
        }
        int i4 = 3;
        if (!hasSlot(cCharAt, 3)) {
            if (!hasSlot(cCharAt, 2)) {
                return i;
            }
            i4 = 2;
        }
        return getSlotValue(cCharAt, i4, i3);
    }

    public final void addCaseClosure(int i, UnicodeSet unicodeSet) {
        int i2;
        int delta;
        if (i == 73) {
            unicodeSet.add(105);
            return;
        }
        if (i == 105) {
            unicodeSet.add(73);
            return;
        }
        if (i == 304) {
            unicodeSet.add(iDot);
            return;
        }
        if (i != 305) {
            int i3 = this.trie.get(i);
            if (!propsHasException(i3)) {
                if (getTypeFromProps(i3) == 0 || (delta = getDelta(i3)) == 0) {
                    return;
                }
                unicodeSet.add(i + delta);
                return;
            }
            int exceptionsOffset = getExceptionsOffset(i3);
            int i4 = exceptionsOffset + 1;
            char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
            int charCount = 0;
            for (int i5 = 0; i5 <= 3; i5++) {
                if (hasSlot(cCharAt, i5)) {
                    i = getSlotValue(cCharAt, i5, i4);
                    unicodeSet.add(i);
                }
            }
            if (hasSlot(cCharAt, 4)) {
                int slotValue = getSlotValue(cCharAt, 4, i4);
                unicodeSet.add((cCharAt & 1024) == 0 ? i + slotValue : i - slotValue);
            }
            if (hasSlot(cCharAt, 6)) {
                long slotValueAndOffset = getSlotValueAndOffset(cCharAt, 6, i4);
                charCount = ((int) (slotValueAndOffset >> 32)) + 1;
                i2 = ((int) slotValueAndOffset) & 15;
            } else {
                i2 = 0;
            }
            if (hasSlot(cCharAt, 7)) {
                long slotValueAndOffset2 = getSlotValueAndOffset(cCharAt, 7, i4);
                int i6 = ((int) (slotValueAndOffset2 >> 32)) + 1;
                int i7 = 65535 & ((int) slotValueAndOffset2);
                int i8 = i6 + (i7 & 15);
                int i9 = i7 >> 4;
                int i10 = i9 & 15;
                if (i10 != 0) {
                    int i11 = i10 + i8;
                    unicodeSet.add(this.f15exceptions.substring(i8, i11));
                    i8 = i11;
                }
                int i12 = i9 >> 4;
                charCount = i8 + (i12 & 15) + (i12 >> 4);
            }
            int i13 = i2 + charCount;
            while (charCount < i13) {
                int iCodePointAt = this.f15exceptions.codePointAt(charCount);
                unicodeSet.add(iCodePointAt);
                charCount += UTF16.getCharCount(iCodePointAt);
            }
        }
    }

    private final int strcmpMax(String str, int i, int i2) {
        int length = str.length();
        int i3 = i2 - length;
        int i4 = 0;
        while (true) {
            int i5 = i4 + 1;
            char cCharAt = str.charAt(i4);
            char[] cArr = this.unfold;
            int i6 = i + 1;
            char c = cArr[i];
            if (c == 0) {
                return 1;
            }
            int i7 = cCharAt - c;
            if (i7 != 0) {
                return i7;
            }
            length--;
            if (length <= 0) {
                if (i3 == 0 || cArr[i6] == 0) {
                    return 0;
                }
                return -i3;
            }
            i4 = i5;
            i = i6;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [char, int] */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v3, types: [int] */
    /* JADX WARN: Type inference failed for: r4v0, types: [char] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3 */
    public final boolean addStringCaseClosure(String str, UnicodeSet unicodeSet) {
        int length;
        if (this.unfold == null || str == null || (length = str.length()) <= 1) {
            return false;
        }
        char[] cArr = this.unfold;
        char c = cArr[0];
        char c2 = cArr[1];
        char charCount = cArr[2];
        if (length > charCount) {
            return false;
        }
        int i = 0;
        while (i < c) {
            int i2 = (i + (c == true ? 1 : 0)) / 2;
            int i3 = i2 + 1;
            int i4 = i3 * c2;
            int iStrcmpMax = strcmpMax(str, i4, charCount);
            if (iStrcmpMax == 0) {
                while (charCount < c2) {
                    char[] cArr2 = this.unfold;
                    if (cArr2[i4 + charCount] == 0) {
                        break;
                    }
                    int iCharAt = UTF16.charAt(cArr2, i4, cArr2.length, charCount);
                    unicodeSet.add(iCharAt);
                    addCaseClosure(iCharAt, unicodeSet);
                    charCount += UTF16.getCharCount(iCharAt);
                }
                return true;
            }
            if (iStrcmpMax < 0) {
                c = i2;
            } else {
                i = i3;
            }
        }
        return false;
    }

    public final int getType(int i) {
        return getTypeFromProps(this.trie.get(i));
    }

    public final int getTypeOrIgnorable(int i) {
        return getTypeAndIgnorableFromProps(this.trie.get(i));
    }

    public final int getDotType(int i) {
        int i2 = this.trie.get(i);
        return !propsHasException(i2) ? i2 & 96 : (this.f15exceptions.charAt(getExceptionsOffset(i2)) >> 7) & 96;
    }

    public final boolean isSoftDotted(int i) {
        return getDotType(i) == 32;
    }

    public final boolean isCaseSensitive(int i) {
        int i2 = this.trie.get(i);
        return !propsHasException(i2) ? (i2 & 16) != 0 : (this.f15exceptions.charAt(getExceptionsOffset(i2)) & 2048) != 0;
    }

    static final class LatinCase {
        static final byte EXC = -128;
        static final char LIMIT = 384;
        static final char LONG_S = 383;
        static final byte[] TO_LOWER_NORMAL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128};
        static final byte[] TO_LOWER_TR_LT = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, -128, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128};
        static final byte[] TO_UPPER_NORMAL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128};
        static final byte[] TO_UPPER_TR = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128};

        LatinCase() {
        }
    }

    public static final int getCaseLocale(Locale locale) {
        return getCaseLocale(locale.getLanguage());
    }

    public static final int getCaseLocale(ULocale uLocale) {
        return getCaseLocale(uLocale.getLanguage());
    }

    private static final int getCaseLocale(String str) {
        if (str.length() == 2) {
            if (str.equals("en") || str.charAt(0) > 't') {
                return 1;
            }
            if (str.equals("tr") || str.equals("az")) {
                return 2;
            }
            if (str.equals("el")) {
                return 4;
            }
            if (str.equals("lt")) {
                return 3;
            }
            if (str.equals("nl")) {
                return 5;
            }
            if (str.equals("hy")) {
                return 6;
            }
        } else if (str.length() == 3) {
            if (str.equals("tur") || str.equals("aze")) {
                return 2;
            }
            if (str.equals("ell")) {
                return 4;
            }
            if (str.equals("lit")) {
                return 3;
            }
            if (str.equals("nld")) {
                return 5;
            }
            if (str.equals("hye")) {
                return 6;
            }
        }
        return 1;
    }

    private final boolean isFollowedByCasedLetter(ContextIterator contextIterator, int i) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(i);
        while (true) {
            int next = contextIterator.next();
            if (next < 0) {
                break;
            }
            int typeOrIgnorable = getTypeOrIgnorable(next);
            if ((typeOrIgnorable & 4) == 0) {
                if (typeOrIgnorable != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isPrecededBySoftDotted(ContextIterator contextIterator) {
        int dotType;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(-1);
        do {
            int next = contextIterator.next();
            if (next < 0) {
                break;
            }
            dotType = getDotType(next);
            if (dotType == 32) {
                return true;
            }
        } while (dotType == 96);
        return false;
    }

    private final boolean isPrecededBy_I(ContextIterator contextIterator) {
        int next;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(-1);
        do {
            next = contextIterator.next();
            if (next < 0) {
                break;
            }
            if (next == 73) {
                return true;
            }
        } while (getDotType(next) == 96);
        return false;
    }

    private final boolean isFollowedByMoreAbove(ContextIterator contextIterator) {
        int dotType;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(1);
        do {
            int next = contextIterator.next();
            if (next < 0) {
                break;
            }
            dotType = getDotType(next);
            if (dotType == 64) {
                return true;
            }
        } while (dotType == 96);
        return false;
    }

    private final boolean isFollowedByDotAbove(ContextIterator contextIterator) {
        int next;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(1);
        do {
            next = contextIterator.next();
            if (next < 0) {
                break;
            }
            if (next == 775) {
                return true;
            }
        } while (getDotType(next) == 96);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:97:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int toFullLower(int r18, com.ibm.icu.impl.UCaseProps.ContextIterator r19, java.lang.Appendable r20, int r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.UCaseProps.toFullLower(int, com.ibm.icu.impl.UCaseProps$ContextIterator, java.lang.Appendable, int):int");
    }

    private final int toUpperOrTitle(int i, ContextIterator contextIterator, Appendable appendable, int i2, boolean z) throws IOException {
        int slotValue;
        int i3 = this.trie.get(i);
        if (!propsHasException(i3)) {
            slotValue = getTypeFromProps(i3) == 1 ? getDelta(i3) + i : i;
        } else {
            int exceptionsOffset = getExceptionsOffset(i3);
            int i4 = exceptionsOffset + 1;
            char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
            int i5 = 3;
            if ((cCharAt & 16384) != 0) {
                if (i2 == 2 && i == 105) {
                    return 304;
                }
                if (i2 == 3 && i == 775 && isPrecededBySoftDotted(contextIterator)) {
                    return 0;
                }
                if (i == 1415) {
                    try {
                        if (i2 == 6) {
                            appendable.append(z ? "ԵՎ" : "Եվ");
                        } else {
                            appendable.append(z ? "ԵՒ" : "Եւ");
                        }
                        return 2;
                    } catch (IOException e) {
                        throw new ICUUncheckedIOException(e);
                    }
                }
            } else if (hasSlot(cCharAt, 7)) {
                long slotValueAndOffset = getSlotValueAndOffset(cCharAt, 7, i4);
                int i6 = ((int) slotValueAndOffset) & 65535;
                int i7 = ((int) (slotValueAndOffset >> 32)) + 1 + (i6 & 15);
                int i8 = i6 >> 4;
                int i9 = i7 + (i8 & 15);
                int i10 = i8 >> 4;
                if (!z) {
                    i9 += i10 & 15;
                    i10 >>= 4;
                }
                int i11 = i10 & 15;
                if (i11 != 0) {
                    try {
                        appendable.append(this.f15exceptions, i9, i9 + i11);
                        return i11;
                    } catch (IOException e2) {
                        throw new ICUUncheckedIOException(e2);
                    }
                }
            }
            if (hasSlot(cCharAt, 4) && getTypeFromProps(i3) == 1) {
                int slotValue2 = getSlotValue(cCharAt, 4, i4);
                return (cCharAt & 1024) == 0 ? i + slotValue2 : i - slotValue2;
            }
            if (z || !hasSlot(cCharAt, 3)) {
                if (!hasSlot(cCharAt, 2)) {
                    return ~i;
                }
                i5 = 2;
            }
            slotValue = getSlotValue(cCharAt, i5, i4);
        }
        return slotValue == i ? ~slotValue : slotValue;
    }

    public final int toFullUpper(int i, ContextIterator contextIterator, Appendable appendable, int i2) {
        return toUpperOrTitle(i, contextIterator, appendable, i2, true);
    }

    public final int toFullTitle(int i, ContextIterator contextIterator, Appendable appendable, int i2) {
        return toUpperOrTitle(i, contextIterator, appendable, i2, false);
    }

    public final int fold(int i, int i2) {
        int i3 = this.trie.get(i);
        if (!propsHasException(i3)) {
            return isUpperOrTitleFromProps(i3) ? i + getDelta(i3) : i;
        }
        int exceptionsOffset = getExceptionsOffset(i3);
        int i4 = exceptionsOffset + 1;
        char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
        if ((32768 & cCharAt) != 0) {
            if ((i2 & 7) == 0) {
                if (i == 73) {
                    return 105;
                }
                if (i == 304) {
                    return i;
                }
            } else {
                if (i == 73) {
                    return 305;
                }
                if (i == 304) {
                    return 105;
                }
            }
        }
        if ((cCharAt & 512) != 0) {
            return i;
        }
        if (hasSlot(cCharAt, 4) && isUpperOrTitleFromProps(i3)) {
            int slotValue = getSlotValue(cCharAt, 4, i4);
            return (cCharAt & 1024) == 0 ? i + slotValue : i - slotValue;
        }
        int i5 = 1;
        if (!hasSlot(cCharAt, 1)) {
            if (!hasSlot(cCharAt, 0)) {
                return i;
            }
            i5 = 0;
        }
        return getSlotValue(cCharAt, i5, i4);
    }

    public final int toFullFolding(int i, Appendable appendable, int i2) {
        int slotValue;
        int i3 = this.trie.get(i);
        if (!propsHasException(i3)) {
            slotValue = isUpperOrTitleFromProps(i3) ? getDelta(i3) + i : i;
        } else {
            int exceptionsOffset = getExceptionsOffset(i3);
            int i4 = exceptionsOffset + 1;
            char cCharAt = this.f15exceptions.charAt(exceptionsOffset);
            int i5 = 1;
            if ((32768 & cCharAt) != 0) {
                if ((i2 & 7) == 0) {
                    if (i == 73) {
                        return 105;
                    }
                    if (i == 304) {
                        try {
                            appendable.append(iDot);
                            return 2;
                        } catch (IOException e) {
                            throw new ICUUncheckedIOException(e);
                        }
                    }
                } else {
                    if (i == 73) {
                        return 305;
                    }
                    if (i == 304) {
                        return 105;
                    }
                }
            } else if (hasSlot(cCharAt, 7)) {
                long slotValueAndOffset = getSlotValueAndOffset(cCharAt, 7, i4);
                int i6 = ((int) slotValueAndOffset) & 65535;
                int i7 = ((int) (slotValueAndOffset >> 32)) + 1 + (i6 & 15);
                int i8 = (i6 >> 4) & 15;
                if (i8 != 0) {
                    try {
                        appendable.append(this.f15exceptions, i7, i7 + i8);
                        return i8;
                    } catch (IOException e2) {
                        throw new ICUUncheckedIOException(e2);
                    }
                }
            }
            if ((cCharAt & 512) != 0) {
                return ~i;
            }
            if (hasSlot(cCharAt, 4) && isUpperOrTitleFromProps(i3)) {
                int slotValue2 = getSlotValue(cCharAt, 4, i4);
                return (cCharAt & 1024) == 0 ? i + slotValue2 : i - slotValue2;
            }
            if (!hasSlot(cCharAt, 1)) {
                if (!hasSlot(cCharAt, 0)) {
                    return ~i;
                }
                i5 = 0;
            }
            slotValue = getSlotValue(cCharAt, i5, i4);
        }
        return slotValue == i ? ~slotValue : slotValue;
    }

    public final boolean hasBinaryProperty(int i, int i2) {
        if (i2 == 22) {
            return 1 == getType(i);
        }
        if (i2 == 27) {
            return isSoftDotted(i);
        }
        if (i2 == 30) {
            return 2 == getType(i);
        }
        if (i2 == 34) {
            return isCaseSensitive(i);
        }
        if (i2 != 55) {
            switch (i2) {
                case 49:
                    return getType(i) != 0;
                case 50:
                    return (getTypeOrIgnorable(i) >> 2) != 0;
                case 51:
                    dummyStringBuilder.setLength(0);
                    return toFullLower(i, null, dummyStringBuilder, 1) >= 0;
                case 52:
                    dummyStringBuilder.setLength(0);
                    return toFullUpper(i, null, dummyStringBuilder, 1) >= 0;
                case 53:
                    dummyStringBuilder.setLength(0);
                    return toFullTitle(i, null, dummyStringBuilder, 1) >= 0;
                default:
                    return false;
            }
        }
        dummyStringBuilder.setLength(0);
        return toFullLower(i, null, dummyStringBuilder, 1) >= 0 || toFullUpper(i, null, dummyStringBuilder, 1) >= 0 || toFullTitle(i, null, dummyStringBuilder, 1) >= 0;
    }

    static Trie2_16 getTrie() {
        return INSTANCE.trie;
    }
}
