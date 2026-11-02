package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.MutableCodePointTrie;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class Normalizer2Impl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CANON_HAS_COMPOSITIONS = 1073741824;
    private static final int CANON_HAS_SET = 2097152;
    private static final int CANON_NOT_SEGMENT_STARTER = Integer.MIN_VALUE;
    private static final int CANON_VALUE_MASK = 2097151;
    public static final int COMP_1_LAST_TUPLE = 32768;
    public static final int COMP_1_TRAIL_LIMIT = 13312;
    public static final int COMP_1_TRAIL_MASK = 32766;
    public static final int COMP_1_TRAIL_SHIFT = 9;
    public static final int COMP_1_TRIPLE = 1;
    public static final int COMP_2_TRAIL_MASK = 65472;
    public static final int COMP_2_TRAIL_SHIFT = 6;
    private static final int DATA_FORMAT = 1316121906;
    public static final int DELTA_SHIFT = 3;
    public static final int DELTA_TCCC_0 = 0;
    public static final int DELTA_TCCC_1 = 2;
    public static final int DELTA_TCCC_GT_1 = 4;
    public static final int DELTA_TCCC_MASK = 6;
    public static final int HAS_COMP_BOUNDARY_AFTER = 1;
    public static final int INERT = 1;
    public static final int IX_COUNT = 20;
    public static final int IX_EXTRA_DATA_OFFSET = 1;
    public static final int IX_LIMIT_NO_NO = 12;
    public static final int IX_MIN_COMP_NO_MAYBE_CP = 9;
    public static final int IX_MIN_DECOMP_NO_CP = 8;
    public static final int IX_MIN_LCCC_CP = 18;
    public static final int IX_MIN_MAYBE_YES = 13;
    public static final int IX_MIN_NO_NO = 11;
    public static final int IX_MIN_NO_NO_COMP_BOUNDARY_BEFORE = 15;
    public static final int IX_MIN_NO_NO_COMP_NO_MAYBE_CC = 16;
    public static final int IX_MIN_NO_NO_EMPTY = 17;
    public static final int IX_MIN_YES_NO = 10;
    public static final int IX_MIN_YES_NO_MAPPINGS_ONLY = 14;
    public static final int IX_NORM_TRIE_OFFSET = 0;
    public static final int IX_RESERVED3_OFFSET = 3;
    public static final int IX_SMALL_FCD_OFFSET = 2;
    public static final int IX_TOTAL_SIZE = 7;
    public static final int JAMO_L = 2;
    public static final int JAMO_VT = 65024;
    public static final int MAPPING_HAS_CCC_LCCC_WORD = 128;
    public static final int MAPPING_HAS_RAW_MAPPING = 64;
    public static final int MAPPING_LENGTH_MASK = 31;
    public static final int MAX_DELTA = 64;
    public static final int MIN_NORMAL_MAYBE_YES = 64512;
    public static final int MIN_YES_YES_WITH_CC = 65026;
    public static final int OFFSET_SHIFT = 1;
    private CodePointTrie canonIterData;
    private ArrayList<UnicodeSet> canonStartSets;
    private int centerNoNoDelta;
    private VersionInfo dataVersion;
    private String extraData;
    private int limitNoNo;
    private String maybeYesCompositions;
    private int minCompNoMaybeCP;
    private int minDecompNoCP;
    private int minLcccCP;
    private int minMaybeYes;
    private int minNoNo;
    private int minNoNoCompBoundaryBefore;
    private int minNoNoCompNoMaybeCC;
    private int minNoNoEmpty;
    private int minYesNo;
    private int minYesNoMappingsOnly;
    private CodePointTrie.Fast16 normTrie;
    private byte[] smallFCD;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    private static final CodePointMap.ValueFilter segmentStarterMapper = new CodePointMap.ValueFilter() { // from class: com.ibm.icu.impl.Normalizer2Impl.1
        @Override // com.ibm.icu.util.CodePointMap.ValueFilter
        public int apply(int i) {
            return i & Integer.MIN_VALUE;
        }
    };

    public static int getCCFromNormalYesOrMaybe(int i) {
        return (i >> 1) & 255;
    }

    private static boolean isInert(int i) {
        return i == 1;
    }

    private static boolean isJamoL(int i) {
        return i == 2;
    }

    private static boolean isJamoVT(int i) {
        return i == 65024;
    }

    public static final class Hangul {
        public static final int HANGUL_BASE = 44032;
        public static final int HANGUL_COUNT = 11172;
        public static final int HANGUL_END = 55203;
        public static final int HANGUL_LIMIT = 55204;
        public static final int JAMO_L_BASE = 4352;
        public static final int JAMO_L_COUNT = 19;
        public static final int JAMO_L_END = 4370;
        public static final int JAMO_L_LIMIT = 4371;
        public static final int JAMO_T_BASE = 4519;
        public static final int JAMO_T_COUNT = 28;
        public static final int JAMO_T_END = 4546;
        public static final int JAMO_VT_COUNT = 588;
        public static final int JAMO_V_BASE = 4449;
        public static final int JAMO_V_COUNT = 21;
        public static final int JAMO_V_END = 4469;
        public static final int JAMO_V_LIMIT = 4470;

        public static boolean isHangul(int i) {
            return 44032 <= i && i < 55204;
        }

        public static boolean isJamo(int i) {
            return 4352 <= i && i <= 4546 && (i <= 4370 || ((4449 <= i && i <= 4469) || 4519 < i));
        }

        public static boolean isJamoL(int i) {
            return 4352 <= i && i < 4371;
        }

        public static boolean isJamoT(int i) {
            int i2 = i - 4519;
            return i2 > 0 && i2 < 28;
        }

        public static boolean isJamoV(int i) {
            return 4449 <= i && i < 4470;
        }

        public static boolean isHangulLV(int i) {
            int i2 = i - HANGUL_BASE;
            return i2 >= 0 && i2 < 11172 && i2 % 28 == 0;
        }

        public static int decompose(int i, Appendable appendable) throws IOException {
            int i2 = i - HANGUL_BASE;
            try {
                int i3 = i2 % 28;
                int i4 = i2 / 28;
                appendable.append((char) ((i4 / 21) + 4352));
                appendable.append((char) ((i4 % 21) + 4449));
                if (i3 == 0) {
                    return 2;
                }
                appendable.append((char) (i3 + 4519));
                return 3;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public static void getRawDecomposition(int i, Appendable appendable) throws IOException {
            int i2 = i - HANGUL_BASE;
            try {
                int i3 = i2 % 28;
                if (i3 == 0) {
                    int i4 = i2 / 28;
                    appendable.append((char) ((i4 / 21) + 4352));
                    appendable.append((char) ((i4 % 21) + 4449));
                } else {
                    appendable.append((char) (i - i3));
                    appendable.append((char) (i3 + 4519));
                }
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }
    }

    public static final class ReorderingBuffer implements Appendable {
        private final Appendable app;
        private final boolean appIsStringBuilder;
        private int codePointLimit;
        private int codePointStart;
        private final Normalizer2Impl impl;
        private int lastCC;
        private int reorderStart;
        private final StringBuilder str;

        public ReorderingBuffer(Normalizer2Impl normalizer2Impl, Appendable appendable, int i) {
            this.impl = normalizer2Impl;
            this.app = appendable;
            if (appendable instanceof StringBuilder) {
                this.appIsStringBuilder = true;
                StringBuilder sb = (StringBuilder) appendable;
                this.str = sb;
                sb.ensureCapacity(i);
                this.reorderStart = 0;
                if (this.str.length() == 0) {
                    this.lastCC = 0;
                    return;
                }
                setIterator();
                int iPreviousCC = previousCC();
                this.lastCC = iPreviousCC;
                if (iPreviousCC > 1) {
                    while (previousCC() > 1) {
                    }
                }
                this.reorderStart = this.codePointLimit;
                return;
            }
            this.appIsStringBuilder = false;
            this.str = new StringBuilder();
            this.reorderStart = 0;
            this.lastCC = 0;
        }

        public boolean isEmpty() {
            return this.str.length() == 0;
        }

        public int length() {
            return this.str.length();
        }

        public int getLastCC() {
            return this.lastCC;
        }

        public StringBuilder getStringBuilder() {
            return this.str;
        }

        public boolean equals(CharSequence charSequence, int i, int i2) {
            StringBuilder sb = this.str;
            return UTF16Plus.equal(sb, 0, sb.length(), charSequence, i, i2);
        }

        public void append(int i, int i2) {
            if (this.lastCC <= i2 || i2 == 0) {
                this.str.appendCodePoint(i);
                this.lastCC = i2;
                if (i2 <= 1) {
                    this.reorderStart = this.str.length();
                    return;
                }
                return;
            }
            insert(i, i2);
        }

        public void append(CharSequence charSequence, int i, int i2, boolean z, int i3, int i4) {
            int cc;
            if (i == i2) {
                return;
            }
            if (this.lastCC <= i3 || i3 == 0) {
                if (i4 <= 1) {
                    this.reorderStart = this.str.length() + (i2 - i);
                } else if (i3 <= 1) {
                    this.reorderStart = this.str.length() + 1;
                }
                this.str.append(charSequence, i, i2);
                this.lastCC = i4;
                return;
            }
            int iCodePointAt = Character.codePointAt(charSequence, i);
            int iCharCount = i + Character.charCount(iCodePointAt);
            insert(iCodePointAt, i3);
            while (iCharCount < i2) {
                int iCodePointAt2 = Character.codePointAt(charSequence, iCharCount);
                iCharCount += Character.charCount(iCodePointAt2);
                if (iCharCount >= i2) {
                    cc = i4;
                } else if (z) {
                    cc = Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(iCodePointAt2));
                } else {
                    Normalizer2Impl normalizer2Impl = this.impl;
                    cc = normalizer2Impl.getCC(normalizer2Impl.getNorm16(iCodePointAt2));
                }
                append(iCodePointAt2, cc);
            }
        }

        @Override // java.lang.Appendable
        public ReorderingBuffer append(char c) {
            this.str.append(c);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
            return this;
        }

        public void appendZeroCC(int i) {
            this.str.appendCodePoint(i);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }

        @Override // java.lang.Appendable
        public ReorderingBuffer append(CharSequence charSequence) {
            if (charSequence.length() != 0) {
                this.str.append(charSequence);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }

        @Override // java.lang.Appendable
        public ReorderingBuffer append(CharSequence charSequence, int i, int i2) {
            if (i != i2) {
                this.str.append(charSequence, i, i2);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }

        public void flush() throws IOException {
            if (this.appIsStringBuilder) {
                this.reorderStart = this.str.length();
            } else {
                try {
                    this.app.append(this.str);
                    this.str.setLength(0);
                    this.reorderStart = 0;
                } catch (IOException e) {
                    throw new ICUUncheckedIOException(e);
                }
            }
            this.lastCC = 0;
        }

        public ReorderingBuffer flushAndAppendZeroCC(CharSequence charSequence, int i, int i2) throws IOException {
            if (this.appIsStringBuilder) {
                this.str.append(charSequence, i, i2);
                this.reorderStart = this.str.length();
            } else {
                try {
                    this.app.append(this.str).append(charSequence, i, i2);
                    this.str.setLength(0);
                    this.reorderStart = 0;
                } catch (IOException e) {
                    throw new ICUUncheckedIOException(e);
                }
            }
            this.lastCC = 0;
            return this;
        }

        public void remove() {
            this.str.setLength(0);
            this.lastCC = 0;
            this.reorderStart = 0;
        }

        public void removeSuffix(int i) {
            int length = this.str.length();
            this.str.delete(length - i, length);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }

        private void insert(int i, int i2) {
            setIterator();
            skipPrevious();
            while (previousCC() > i2) {
            }
            if (i <= 65535) {
                this.str.insert(this.codePointLimit, (char) i);
                if (i2 <= 1) {
                    this.reorderStart = this.codePointLimit + 1;
                    return;
                }
                return;
            }
            this.str.insert(this.codePointLimit, Character.toChars(i));
            if (i2 <= 1) {
                this.reorderStart = this.codePointLimit + 2;
            }
        }

        private void setIterator() {
            this.codePointStart = this.str.length();
        }

        private void skipPrevious() {
            int i = this.codePointStart;
            this.codePointLimit = i;
            this.codePointStart = this.str.offsetByCodePoints(i, -1);
        }

        private int previousCC() {
            int i = this.codePointStart;
            this.codePointLimit = i;
            if (this.reorderStart >= i) {
                return 0;
            }
            int iCodePointBefore = this.str.codePointBefore(i);
            this.codePointStart -= Character.charCount(iCodePointBefore);
            return this.impl.getCCFromYesOrMaybeCP(iCodePointBefore);
        }
    }

    public static final class UTF16Plus {
        public static boolean isLeadSurrogate(int i) {
            return (i & (-1024)) == 55296;
        }

        public static boolean isSurrogate(int i) {
            return (i & (-2048)) == 55296;
        }

        public static boolean isSurrogateLead(int i) {
            return (i & 1024) == 0;
        }

        public static boolean isTrailSurrogate(int i) {
            return (i & (-1024)) == 56320;
        }

        public static boolean equal(CharSequence charSequence, CharSequence charSequence2) {
            if (charSequence == charSequence2) {
                return true;
            }
            int length = charSequence.length();
            if (length != charSequence2.length()) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                    return false;
                }
            }
            return true;
        }

        public static boolean equal(CharSequence charSequence, int i, int i2, CharSequence charSequence2, int i3, int i4) {
            if (i2 - i != i4 - i3) {
                return false;
            }
            if (charSequence == charSequence2 && i == i3) {
                return true;
            }
            while (i < i2) {
                int i5 = i + 1;
                int i6 = i3 + 1;
                if (charSequence.charAt(i) != charSequence2.charAt(i3)) {
                    return false;
                }
                i = i5;
                i3 = i6;
            }
            return true;
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

    public Normalizer2Impl load(ByteBuffer byteBuffer) {
        try {
            this.dataVersion = ICUBinary.readHeaderAndDataVersion(byteBuffer, DATA_FORMAT, IS_ACCEPTABLE);
            int i = byteBuffer.getInt() / 4;
            if (i <= 18) {
                throw new ICUUncheckedIOException("Normalizer2 data: not enough indexes");
            }
            int[] iArr = new int[i];
            iArr[0] = i * 4;
            for (int i2 = 1; i2 < i; i2++) {
                iArr[i2] = byteBuffer.getInt();
            }
            this.minDecompNoCP = iArr[8];
            this.minCompNoMaybeCP = iArr[9];
            this.minLcccCP = iArr[18];
            this.minYesNo = iArr[10];
            this.minYesNoMappingsOnly = iArr[14];
            this.minNoNo = iArr[11];
            this.minNoNoCompBoundaryBefore = iArr[15];
            this.minNoNoCompNoMaybeCC = iArr[16];
            this.minNoNoEmpty = iArr[17];
            this.limitNoNo = iArr[12];
            this.minMaybeYes = iArr[13];
            this.centerNoNoDelta = ((r0 >> 3) - 64) - 1;
            int i3 = iArr[0];
            int i4 = iArr[1];
            int iPosition = byteBuffer.position();
            this.normTrie = CodePointTrie.Fast16.fromBinary(byteBuffer);
            int iPosition2 = byteBuffer.position() - iPosition;
            int i5 = i4 - i3;
            if (iPosition2 > i5) {
                throw new ICUUncheckedIOException("Normalizer2 data: not enough bytes for normTrie");
            }
            ICUBinary.skipBytes(byteBuffer, i5 - iPosition2);
            int i6 = (iArr[2] - i4) / 2;
            if (i6 != 0) {
                String string = ICUBinary.getString(byteBuffer, i6, 0);
                this.maybeYesCompositions = string;
                this.extraData = string.substring((MIN_NORMAL_MAYBE_YES - this.minMaybeYes) >> 1);
            }
            byte[] bArr = new byte[256];
            this.smallFCD = bArr;
            byteBuffer.get(bArr);
            return this;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    public Normalizer2Impl load(String str) {
        return load(ICUBinary.getRequiredData(str));
    }

    public void addLcccChars(UnicodeSet unicodeSet) {
        CodePointMap.Range range = new CodePointMap.Range();
        int i = 0;
        while (this.normTrie.getRange(i, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
            int end = range.getEnd();
            int value = range.getValue();
            if (value > 64512 && value != 65024) {
                unicodeSet.add(i, end);
            } else if (this.minNoNoCompNoMaybeCC <= value && value < this.limitNoNo && getFCD16(i) > 255) {
                unicodeSet.add(i, end);
            }
            i = end + 1;
        }
    }

    public void addPropertyStarts(UnicodeSet unicodeSet) {
        CodePointMap.Range range = new CodePointMap.Range();
        int i = 0;
        while (this.normTrie.getRange(i, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
            int end = range.getEnd();
            int value = range.getValue();
            unicodeSet.add(i);
            if (i != end && isAlgorithmicNoNo(value) && (value & 6) > 2) {
                int fcd16 = getFCD16(i);
                while (true) {
                    i++;
                    if (i <= end) {
                        int fcd162 = getFCD16(i);
                        if (fcd162 != fcd16) {
                            unicodeSet.add(i);
                            fcd16 = fcd162;
                        }
                    }
                }
            }
            i = end + 1;
        }
        for (int i2 = Hangul.HANGUL_BASE; i2 < 55204; i2 += 28) {
            unicodeSet.add(i2);
            unicodeSet.add(i2 + 1);
        }
        unicodeSet.add(Hangul.HANGUL_LIMIT);
    }

    public void addCanonIterPropertyStarts(UnicodeSet unicodeSet) {
        ensureCanonIterData();
        CodePointMap.Range range = new CodePointMap.Range();
        for (int end = 0; this.canonIterData.getRange(end, segmentStarterMapper, range); end = range.getEnd() + 1) {
            unicodeSet.add(end);
        }
    }

    public synchronized Normalizer2Impl ensureCanonIterData() {
        int iMapAlgorithmic;
        int rawNorm16;
        int i;
        if (this.canonIterData == null) {
            int i2 = 0;
            MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(0, 0);
            this.canonStartSets = new ArrayList<>();
            CodePointMap.Range range = new CodePointMap.Range();
            while (this.normTrie.getRange(i2, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
                int end = range.getEnd();
                int value = range.getValue();
                if (!isInert(value) && (this.minYesNo > value || value >= this.minNoNo)) {
                    while (i2 <= end) {
                        int i3 = mutableCodePointTrie.get(i2);
                        if (isMaybeOrNonZeroCC(value)) {
                            i = i3 | Integer.MIN_VALUE;
                            if (value < 64512) {
                                i |= 1073741824;
                            }
                        } else if (value < this.minYesNo) {
                            i = i3 | 1073741824;
                        } else {
                            if (isDecompNoAlgorithmic(value)) {
                                iMapAlgorithmic = mapAlgorithmic(i2, value);
                                rawNorm16 = getRawNorm16(iMapAlgorithmic);
                            } else {
                                iMapAlgorithmic = i2;
                                rawNorm16 = value;
                            }
                            if (rawNorm16 > this.minYesNo) {
                                int i4 = rawNorm16 >> 1;
                                char cCharAt = this.extraData.charAt(i4);
                                int i5 = cCharAt & 31;
                                i = ((cCharAt & 128) == 0 || i2 != iMapAlgorithmic || (this.extraData.charAt(i4 + (-1)) & 255) == 0) ? i3 : i3 | Integer.MIN_VALUE;
                                if (i5 != 0) {
                                    int iCharCount = i4 + 1;
                                    int i6 = i5 + iCharCount;
                                    int iCodePointAt = this.extraData.codePointAt(iCharCount);
                                    addToStartSet(mutableCodePointTrie, i2, iCodePointAt);
                                    if (rawNorm16 >= this.minNoNo) {
                                        while (true) {
                                            iCharCount += Character.charCount(iCodePointAt);
                                            if (iCharCount >= i6) {
                                                break;
                                            }
                                            iCodePointAt = this.extraData.codePointAt(iCharCount);
                                            int i7 = mutableCodePointTrie.get(iCodePointAt);
                                            if ((i7 & Integer.MIN_VALUE) == 0) {
                                                mutableCodePointTrie.set(iCodePointAt, i7 | Integer.MIN_VALUE);
                                            }
                                        }
                                    }
                                }
                            } else {
                                addToStartSet(mutableCodePointTrie, i2, iMapAlgorithmic);
                                i = i3;
                            }
                        }
                        if (i != i3) {
                            mutableCodePointTrie.set(i2, i);
                        }
                        i2++;
                    }
                }
                i2 = end + 1;
            }
            this.canonIterData = mutableCodePointTrie.buildImmutable(CodePointTrie.Type.SMALL, CodePointTrie.ValueWidth.BITS_32);
        }
        return this;
    }

    public int getNorm16(int i) {
        if (UTF16Plus.isLeadSurrogate(i)) {
            return 1;
        }
        return this.normTrie.get(i);
    }

    public int getRawNorm16(int i) {
        return this.normTrie.get(i);
    }

    public int getCompQuickCheck(int i) {
        if (i < this.minNoNo || 65026 <= i) {
            return 1;
        }
        return this.minMaybeYes <= i ? 2 : 0;
    }

    public boolean isAlgorithmicNoNo(int i) {
        return this.limitNoNo <= i && i < this.minMaybeYes;
    }

    public boolean isCompNo(int i) {
        return this.minNoNo <= i && i < this.minMaybeYes;
    }

    public boolean isDecompYes(int i) {
        return i < this.minYesNo || this.minMaybeYes <= i;
    }

    public int getCC(int i) {
        if (i >= 64512) {
            return getCCFromNormalYesOrMaybe(i);
        }
        if (i < this.minNoNo || this.limitNoNo <= i) {
            return 0;
        }
        return getCCFromNoNo(i);
    }

    public static int getCCFromYesOrMaybe(int i) {
        if (i >= 64512) {
            return getCCFromNormalYesOrMaybe(i);
        }
        return 0;
    }

    public int getCCFromYesOrMaybeCP(int i) {
        if (i < this.minCompNoMaybeCP) {
            return 0;
        }
        return getCCFromYesOrMaybe(getNorm16(i));
    }

    public int getFCD16(int i) {
        if (i < this.minDecompNoCP) {
            return 0;
        }
        if (i > 65535 || singleLeadMightHaveNonZeroFCD16(i)) {
            return getFCD16FromNormData(i);
        }
        return 0;
    }

    public boolean singleLeadMightHaveNonZeroFCD16(int i) {
        byte b = this.smallFCD[i >> 8];
        return (b == 0 || ((b >> ((i >> 5) & 7)) & 1) == 0) ? false : true;
    }

    public int getFCD16FromNormData(int i) {
        int norm16 = getNorm16(i);
        if (norm16 >= this.limitNoNo) {
            if (norm16 >= 64512) {
                int cCFromNormalYesOrMaybe = getCCFromNormalYesOrMaybe(norm16);
                return cCFromNormalYesOrMaybe | (cCFromNormalYesOrMaybe << 8);
            }
            if (norm16 >= this.minMaybeYes) {
                return 0;
            }
            int i2 = norm16 & 6;
            if (i2 <= 2) {
                return i2 >> 1;
            }
            norm16 = getRawNorm16(mapAlgorithmic(i, norm16));
        }
        if (norm16 <= this.minYesNo || isHangulLVT(norm16)) {
            return 0;
        }
        int i3 = norm16 >> 1;
        char cCharAt = this.extraData.charAt(i3);
        int i4 = cCharAt >> '\b';
        return (cCharAt & 128) != 0 ? i4 | (this.extraData.charAt(i3 - 1) & 65280) : i4;
    }

    public String getDecomposition(int i) {
        if (i >= this.minDecompNoCP) {
            int norm16 = getNorm16(i);
            if (!isMaybeOrNonZeroCC(norm16)) {
                int i2 = -1;
                if (isDecompNoAlgorithmic(norm16)) {
                    i = mapAlgorithmic(i, norm16);
                    norm16 = getRawNorm16(i);
                    i2 = i;
                }
                if (norm16 < this.minYesNo) {
                    if (i2 < 0) {
                        return null;
                    }
                    return UTF16.valueOf(i2);
                }
                if (isHangulLV(norm16) || isHangulLVT(norm16)) {
                    StringBuilder sb = new StringBuilder();
                    Hangul.decompose(i, sb);
                    return sb.toString();
                }
                int i3 = norm16 >> 1;
                int i4 = i3 + 1;
                return this.extraData.substring(i4, (this.extraData.charAt(i3) & 31) + i4);
            }
        }
        return null;
    }

    public String getRawDecomposition(int i) throws IOException {
        if (i < this.minDecompNoCP) {
            return null;
        }
        int norm16 = getNorm16(i);
        if (isDecompYes(norm16)) {
            return null;
        }
        if (isHangulLV(norm16) || isHangulLVT(norm16)) {
            StringBuilder sb = new StringBuilder();
            Hangul.getRawDecomposition(i, sb);
            return sb.toString();
        }
        if (isDecompNoAlgorithmic(norm16)) {
            return UTF16.valueOf(mapAlgorithmic(i, norm16));
        }
        int i2 = norm16 >> 1;
        char cCharAt = this.extraData.charAt(i2);
        int i3 = cCharAt & 31;
        if ((cCharAt & '@') != 0) {
            int i4 = (i2 - ((cCharAt >> 7) & 1)) - 1;
            char cCharAt2 = this.extraData.charAt(i4);
            if (cCharAt2 <= 31) {
                return this.extraData.substring(i4 - cCharAt2, i4);
            }
            StringBuilder sb2 = new StringBuilder(i3 - 1);
            sb2.append(cCharAt2);
            sb2.append((CharSequence) this.extraData, i2 + 3, (i3 + r5) - 2);
            return sb2.toString();
        }
        int i5 = i2 + 1;
        return this.extraData.substring(i5, i3 + i5);
    }

    public boolean isCanonSegmentStarter(int i) {
        return this.canonIterData.get(i) >= 0;
    }

    public boolean getCanonStartSet(int i, UnicodeSet unicodeSet) {
        int i2 = this.canonIterData.get(i) & Integer.MAX_VALUE;
        if (i2 == 0) {
            return false;
        }
        unicodeSet.clear();
        int i3 = 2097151 & i2;
        if ((2097152 & i2) != 0) {
            unicodeSet.addAll(this.canonStartSets.get(i3));
        } else if (i3 != 0) {
            unicodeSet.add(i3);
        }
        if ((i2 & 1073741824) != 0) {
            int rawNorm16 = getRawNorm16(i);
            if (rawNorm16 == 2) {
                int i4 = ((i - 4352) * 588) + Hangul.HANGUL_BASE;
                unicodeSet.add(i4, (i4 + 588) - 1);
            } else {
                addComposites(getCompositionsList(rawNorm16), unicodeSet);
            }
        }
        return true;
    }

    public Appendable decompose(CharSequence charSequence, StringBuilder sb) throws IOException {
        decompose(charSequence, 0, charSequence.length(), sb, charSequence.length());
        return sb;
    }

    public void decompose(CharSequence charSequence, int i, int i2, StringBuilder sb, int i3) throws IOException {
        if (i3 < 0) {
            i3 = i2 - i;
        }
        sb.setLength(0);
        decompose(charSequence, i, i2, new ReorderingBuffer(this, sb, i3));
    }

    public int decompose(CharSequence charSequence, int i, int i2, ReorderingBuffer reorderingBuffer) throws IOException {
        int cCFromYesOrMaybe;
        int i3 = this.minDecompNoCP;
        int i4 = i;
        int iCharAt = 0;
        int iBmpGet = 0;
        int i5 = 0;
        while (true) {
            int i6 = i;
            while (i6 != i2) {
                iCharAt = charSequence.charAt(i6);
                if (iCharAt >= i3) {
                    iBmpGet = this.normTrie.bmpGet(iCharAt);
                    if (!isMostDecompYesAndZeroCC(iBmpGet)) {
                        if (!UTF16Plus.isLeadSurrogate(iCharAt)) {
                            break;
                        }
                        int i7 = i6 + 1;
                        if (i7 != i2) {
                            char cCharAt = charSequence.charAt(i7);
                            if (Character.isLowSurrogate(cCharAt)) {
                                iCharAt = Character.toCodePoint((char) iCharAt, cCharAt);
                                iBmpGet = this.normTrie.suppGet(iCharAt);
                                if (!isMostDecompYesAndZeroCC(iBmpGet)) {
                                    break;
                                }
                                i6 += 2;
                            }
                        }
                        i6 = i7;
                    }
                }
                i6++;
            }
            if (i6 != i) {
                if (reorderingBuffer != null) {
                    reorderingBuffer.flushAndAppendZeroCC(charSequence, i, i6);
                } else {
                    i4 = i6;
                    i5 = 0;
                }
            }
            if (i6 == i2) {
                return i6;
            }
            i = Character.charCount(iCharAt) + i6;
            if (reorderingBuffer != null) {
                decompose(iCharAt, iBmpGet, reorderingBuffer);
            } else {
                if (!isDecompYes(iBmpGet) || (i5 > (cCFromYesOrMaybe = getCCFromYesOrMaybe(iBmpGet)) && cCFromYesOrMaybe != 0)) {
                    break;
                }
                if (cCFromYesOrMaybe <= 1) {
                    i4 = i;
                }
                i5 = cCFromYesOrMaybe;
            }
        }
        return i4;
    }

    public void decomposeAndAppend(CharSequence charSequence, boolean z, ReorderingBuffer reorderingBuffer) throws IOException {
        int i;
        int length = charSequence.length();
        if (length == 0) {
            return;
        }
        int iCharCount = 0;
        if (z) {
            decompose(charSequence, 0, length, reorderingBuffer);
            return;
        }
        int iCodePointAt = Character.codePointAt(charSequence, 0);
        int cc = getCC(getNorm16(iCodePointAt));
        int cc2 = cc;
        int i2 = cc2;
        while (true) {
            if (cc2 == 0) {
                i = i2;
                break;
            }
            iCharCount += Character.charCount(iCodePointAt);
            if (iCharCount >= length) {
                i = cc2;
                break;
            }
            iCodePointAt = Character.codePointAt(charSequence, iCharCount);
            i2 = cc2;
            cc2 = getCC(getNorm16(iCodePointAt));
        }
        reorderingBuffer.append(charSequence, 0, iCharCount, false, cc, i);
        reorderingBuffer.append(charSequence, iCharCount, length);
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0199 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0056 A[PHI: r3 r4 r5
      0x0056: PHI (r3v1 int) = (r3v0 int), (r3v14 int) binds: [B:16:0x0034, B:22:0x0054] A[DONT_GENERATE, DONT_INLINE]
      0x0056: PHI (r4v2 int) = (r4v1 int), (r4v8 int) binds: [B:16:0x0034, B:22:0x0054] A[DONT_GENERATE, DONT_INLINE]
      0x0056: PHI (r5v2 int) = (r5v1 int), (r5v11 int) binds: [B:16:0x0034, B:22:0x0054] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean compose(java.lang.CharSequence r17, int r18, int r19, boolean r20, boolean r21, com.ibm.icu.impl.Normalizer2Impl.ReorderingBuffer r22) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 464
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.Normalizer2Impl.compose(java.lang.CharSequence, int, int, boolean, boolean, com.ibm.icu.impl.Normalizer2Impl$ReorderingBuffer):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000b, code lost:
    
        return r9 | r2;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00ba A[EDGE_INSN: B:67:0x00ba->B:55:0x00ba BREAK  A[LOOP:0: B:3:0x0005->B:73:0x0005], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int composeQuickCheck(java.lang.CharSequence r9, int r10, int r11, boolean r12, boolean r13) {
        /*
            r8 = this;
            int r0 = r8.minCompNoMaybeCP
            r1 = 0
            r1 = r10
            r2 = 0
        L5:
            r3 = 1
            if (r10 != r11) goto Lc
            int r9 = r10 << 1
        La:
            r9 = r9 | r2
            return r9
        Lc:
            char r4 = r9.charAt(r10)
            if (r4 < r0) goto Lc0
            com.ibm.icu.util.CodePointTrie$Fast16 r5 = r8.normTrie
            int r5 = r5.bmpGet(r4)
            boolean r6 = r8.isCompYesAndZeroCC(r5)
            if (r6 == 0) goto L20
            goto Lc0
        L20:
            int r6 = r10 + 1
            boolean r7 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isLeadSurrogate(r4)
            if (r7 != 0) goto L29
            goto L48
        L29:
            if (r6 == r11) goto Lbd
            char r5 = r9.charAt(r6)
            boolean r7 = java.lang.Character.isLowSurrogate(r5)
            if (r7 == 0) goto Lbd
            int r6 = r6 + 1
            char r4 = (char) r4
            int r4 = java.lang.Character.toCodePoint(r4, r5)
            com.ibm.icu.util.CodePointTrie$Fast16 r5 = r8.normTrie
            int r5 = r5.suppGet(r4)
            boolean r4 = r8.isCompYesAndZeroCC(r5)
            if (r4 != 0) goto Lbd
        L48:
            if (r1 == r10) goto L66
            boolean r1 = r8.norm16HasCompBoundaryBefore(r5)
            if (r1 != 0) goto L65
            int r1 = java.lang.Character.codePointBefore(r9, r10)
            int r4 = r8.getNorm16(r1)
            boolean r7 = r8.norm16HasCompBoundaryAfter(r4, r12)
            if (r7 != 0) goto L65
            int r1 = java.lang.Character.charCount(r1)
            int r1 = r10 - r1
            goto L67
        L65:
            r1 = r10
        L66:
            r4 = 1
        L67:
            boolean r10 = r8.isMaybeOrNonZeroCC(r5)
            if (r10 == 0) goto Lba
            int r10 = getCCFromYesOrMaybe(r5)
            if (r12 == 0) goto L7c
            if (r10 == 0) goto L7c
            int r4 = r8.getTrailCCFromCompYesAndZeroCC(r4)
            if (r4 <= r10) goto L7c
            goto Lba
        L7c:
            r4 = 65026(0xfe02, float:9.1121E-41)
            if (r5 >= r4) goto L88
            if (r13 != 0) goto L85
            r2 = 1
            goto L88
        L85:
            int r9 = r1 << 1
            return r9
        L88:
            if (r6 != r11) goto L8e
            int r9 = r6 << 1
            goto La
        L8e:
            int r4 = java.lang.Character.codePointAt(r9, r6)
            int r5 = r8.getNorm16(r4)
            boolean r7 = r8.isMaybeOrNonZeroCC(r5)
            if (r7 == 0) goto Lac
            int r7 = getCCFromYesOrMaybe(r5)
            if (r10 <= r7) goto La5
            if (r7 == 0) goto La5
            goto Lac
        La5:
            int r10 = java.lang.Character.charCount(r4)
            int r6 = r6 + r10
            r10 = r7
            goto L7c
        Lac:
            boolean r10 = r8.isCompYesAndZeroCC(r5)
            if (r10 == 0) goto Lba
            int r10 = java.lang.Character.charCount(r4)
            int r10 = r10 + r6
            r1 = r6
            goto L5
        Lba:
            int r9 = r1 << 1
            return r9
        Lbd:
            r10 = r6
            goto L5
        Lc0:
            int r10 = r10 + 1
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.Normalizer2Impl.composeQuickCheck(java.lang.CharSequence, int, int, boolean, boolean):int");
    }

    public void composeAndAppend(CharSequence charSequence, boolean z, boolean z2, ReorderingBuffer reorderingBuffer) throws IOException {
        int i;
        int iFindNextCompBoundary;
        int length = charSequence.length();
        if (reorderingBuffer.isEmpty() || (iFindNextCompBoundary = findNextCompBoundary(charSequence, 0, length, z2)) == 0) {
            i = 0;
        } else {
            int iFindPreviousCompBoundary = findPreviousCompBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length(), z2);
            StringBuilder sb = new StringBuilder((reorderingBuffer.length() - iFindPreviousCompBoundary) + iFindNextCompBoundary + 16);
            sb.append((CharSequence) reorderingBuffer.getStringBuilder(), iFindPreviousCompBoundary, reorderingBuffer.length());
            reorderingBuffer.removeSuffix(reorderingBuffer.length() - iFindPreviousCompBoundary);
            sb.append(charSequence, 0, iFindNextCompBoundary);
            compose(sb, 0, sb.length(), z2, true, reorderingBuffer);
            i = iFindNextCompBoundary;
        }
        if (z) {
            compose(charSequence, i, length, z2, true, reorderingBuffer);
        } else {
            reorderingBuffer.append(charSequence, i, length);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00b4, code lost:
    
        return r5;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00c0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int makeFCD(java.lang.CharSequence r17, int r18, int r19, com.ibm.icu.impl.Normalizer2Impl.ReorderingBuffer r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.Normalizer2Impl.makeFCD(java.lang.CharSequence, int, int, com.ibm.icu.impl.Normalizer2Impl$ReorderingBuffer):int");
    }

    public void makeFCDAndAppend(CharSequence charSequence, boolean z, ReorderingBuffer reorderingBuffer) throws IOException {
        int iFindNextFCDBoundary;
        int length = charSequence.length();
        int i = 0;
        if (!reorderingBuffer.isEmpty() && (iFindNextFCDBoundary = findNextFCDBoundary(charSequence, 0, length)) != 0) {
            int iFindPreviousFCDBoundary = findPreviousFCDBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length());
            StringBuilder sb = new StringBuilder((reorderingBuffer.length() - iFindPreviousFCDBoundary) + iFindNextFCDBoundary + 16);
            sb.append((CharSequence) reorderingBuffer.getStringBuilder(), iFindPreviousFCDBoundary, reorderingBuffer.length());
            reorderingBuffer.removeSuffix(reorderingBuffer.length() - iFindPreviousFCDBoundary);
            sb.append(charSequence, 0, iFindNextFCDBoundary);
            makeFCD(sb, 0, sb.length(), reorderingBuffer);
            i = iFindNextFCDBoundary;
        }
        if (z) {
            makeFCD(charSequence, i, length, reorderingBuffer);
        } else {
            reorderingBuffer.append(charSequence, i, length);
        }
    }

    public boolean hasDecompBoundaryBefore(int i) {
        return i < this.minLcccCP || (i <= 65535 && !singleLeadMightHaveNonZeroFCD16(i)) || norm16HasDecompBoundaryBefore(getNorm16(i));
    }

    public boolean norm16HasDecompBoundaryBefore(int i) {
        if (i < this.minNoNoCompNoMaybeCC) {
            return true;
        }
        if (i >= this.limitNoNo) {
            return i <= 64512 || i == 65024;
        }
        int i2 = i >> 1;
        return (this.extraData.charAt(i2) & 128) == 0 || (this.extraData.charAt(i2 - 1) & 65280) == 0;
    }

    public boolean hasDecompBoundaryAfter(int i) {
        if (i < this.minDecompNoCP) {
            return true;
        }
        if (i > 65535 || singleLeadMightHaveNonZeroFCD16(i)) {
            return norm16HasDecompBoundaryAfter(getNorm16(i));
        }
        return true;
    }

    public boolean norm16HasDecompBoundaryAfter(int i) {
        if (i <= this.minYesNo || isHangulLVT(i)) {
            return true;
        }
        if (i >= this.limitNoNo) {
            return isMaybeOrNonZeroCC(i) ? i <= 64512 || i == 65024 : (i & 6) <= 2;
        }
        int i2 = i >> 1;
        char cCharAt = this.extraData.charAt(i2);
        if (cCharAt > 511) {
            return false;
        }
        return cCharAt <= 255 || (cCharAt & 128) == 0 || (this.extraData.charAt(i2 - 1) & 65280) == 0;
    }

    public boolean isDecompInert(int i) {
        return isDecompYesAndZeroCC(getNorm16(i));
    }

    public boolean hasCompBoundaryBefore(int i) {
        return i < this.minCompNoMaybeCP || norm16HasCompBoundaryBefore(getNorm16(i));
    }

    public boolean hasCompBoundaryAfter(int i, boolean z) {
        return norm16HasCompBoundaryAfter(getNorm16(i), z);
    }

    public boolean isCompInert(int i, boolean z) {
        int norm16 = getNorm16(i);
        return isCompYesAndZeroCC(norm16) && (norm16 & 1) != 0 && (!z || isInert(norm16) || this.extraData.charAt(norm16 >> 1) <= 511);
    }

    public boolean hasFCDBoundaryBefore(int i) {
        return hasDecompBoundaryBefore(i);
    }

    public boolean hasFCDBoundaryAfter(int i) {
        return hasDecompBoundaryAfter(i);
    }

    public boolean isFCDInert(int i) {
        return getFCD16(i) <= 1;
    }

    private boolean isMaybe(int i) {
        return this.minMaybeYes <= i && i <= 65024;
    }

    private boolean isMaybeOrNonZeroCC(int i) {
        return i >= this.minMaybeYes;
    }

    private int hangulLVT() {
        return this.minYesNoMappingsOnly | 1;
    }

    private boolean isHangulLV(int i) {
        return i == this.minYesNo;
    }

    private boolean isHangulLVT(int i) {
        return i == hangulLVT();
    }

    private boolean isCompYesAndZeroCC(int i) {
        return i < this.minNoNo;
    }

    private boolean isDecompYesAndZeroCC(int i) {
        return i < this.minYesNo || i == 65024 || (this.minMaybeYes <= i && i <= 64512);
    }

    private boolean isMostDecompYesAndZeroCC(int i) {
        return i < this.minYesNo || i == 64512 || i == 65024;
    }

    private boolean isDecompNoAlgorithmic(int i) {
        return i >= this.limitNoNo;
    }

    private int getCCFromNoNo(int i) {
        int i2 = i >> 1;
        if ((this.extraData.charAt(i2) & 128) != 0) {
            return this.extraData.charAt(i2 - 1) & 255;
        }
        return 0;
    }

    int getTrailCCFromCompYesAndZeroCC(int i) {
        if (i <= this.minYesNo) {
            return 0;
        }
        return this.extraData.charAt(i >> 1) >> '\b';
    }

    private int mapAlgorithmic(int i, int i2) {
        return (i + (i2 >> 3)) - this.centerNoNoDelta;
    }

    private int getCompositionsListForDecompYes(int i) {
        if (i < 2 || 64512 <= i) {
            return -1;
        }
        int i2 = i - this.minMaybeYes;
        if (i2 < 0) {
            i2 += MIN_NORMAL_MAYBE_YES;
        }
        return i2 >> 1;
    }

    private int getCompositionsListForComposite(int i) {
        int i2 = ((MIN_NORMAL_MAYBE_YES - this.minMaybeYes) + i) >> 1;
        return i2 + 1 + (this.maybeYesCompositions.charAt(i2) & 31);
    }

    private int getCompositionsListForMaybe(int i) {
        return (i - this.minMaybeYes) >> 1;
    }

    private int getCompositionsList(int i) {
        if (isDecompYes(i)) {
            return getCompositionsListForDecompYes(i);
        }
        return getCompositionsListForComposite(i);
    }

    private int decomposeShort(CharSequence charSequence, int i, int i2, boolean z, boolean z2, ReorderingBuffer reorderingBuffer) throws IOException {
        while (i < i2) {
            int iCodePointAt = Character.codePointAt(charSequence, i);
            if (z && iCodePointAt < this.minCompNoMaybeCP) {
                return i;
            }
            int norm16 = getNorm16(iCodePointAt);
            if (!z || !norm16HasCompBoundaryBefore(norm16)) {
                i += Character.charCount(iCodePointAt);
                decompose(iCodePointAt, norm16, reorderingBuffer);
                if (z && norm16HasCompBoundaryAfter(norm16, z2)) {
                    break;
                }
            } else {
                return i;
            }
        }
        return i;
    }

    private void decompose(int i, int i2, ReorderingBuffer reorderingBuffer) throws IOException {
        if (i2 >= this.limitNoNo) {
            if (isMaybeOrNonZeroCC(i2)) {
                reorderingBuffer.append(i, getCCFromYesOrMaybe(i2));
                return;
            } else {
                i = mapAlgorithmic(i, i2);
                i2 = getRawNorm16(i);
            }
        }
        if (i2 < this.minYesNo) {
            reorderingBuffer.append(i, 0);
            return;
        }
        if (isHangulLV(i2) || isHangulLVT(i2)) {
            Hangul.decompose(i, reorderingBuffer);
            return;
        }
        int i3 = i2 >> 1;
        char cCharAt = this.extraData.charAt(i3);
        int i4 = i3 + 1;
        reorderingBuffer.append(this.extraData, i4, i4 + (cCharAt & 31), true, (cCharAt & 128) != 0 ? this.extraData.charAt(i3 - 1) >> '\b' : 0, cCharAt >> '\b');
    }

    private static int combine(String str, int i, int i2) {
        char cCharAt;
        if (i2 < 13312) {
            int i3 = i2 << 1;
            while (true) {
                cCharAt = str.charAt(i);
                if (i3 <= cCharAt) {
                    break;
                }
                i += (cCharAt & 1) + 2;
            }
            if (i3 != (cCharAt & 32766)) {
                return -1;
            }
            if ((cCharAt & 1) != 0) {
                return str.charAt(i + 2) | (str.charAt(i + 1) << 16);
            }
            return str.charAt(i + 1);
        }
        int i4 = ((i2 >> 9) & (-2)) + COMP_1_TRAIL_LIMIT;
        int i5 = (i2 << 6) & 65535;
        while (true) {
            char cCharAt2 = str.charAt(i);
            if (i4 > cCharAt2) {
                i += (cCharAt2 & 1) + 2;
            } else {
                if (i4 != (cCharAt2 & 32766)) {
                    return -1;
                }
                char cCharAt3 = str.charAt(i + 1);
                if (i5 <= cCharAt3) {
                    if (i5 != (65472 & cCharAt3)) {
                        return -1;
                    }
                    return str.charAt(i + 2) | (('?' & cCharAt3) << 16);
                }
                if ((cCharAt2 & 32768) != 0) {
                    return -1;
                }
                i += 3;
            }
        }
    }

    private void addComposites(int i, UnicodeSet unicodeSet) {
        char cCharAt;
        int iCharAt;
        do {
            cCharAt = this.maybeYesCompositions.charAt(i);
            if ((cCharAt & 1) == 0) {
                iCharAt = this.maybeYesCompositions.charAt(i + 1);
                i += 2;
            } else {
                iCharAt = ((this.maybeYesCompositions.charAt(i + 1) & '?') << 16) | this.maybeYesCompositions.charAt(i + 2);
                i += 3;
            }
            int i2 = iCharAt >> 1;
            if ((iCharAt & 1) != 0) {
                addComposites(getCompositionsListForComposite(getRawNorm16(i2)), unicodeSet);
            }
            unicodeSet.add(i2);
        } while ((cCharAt & 32768) == 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e8 A[EDGE_INSN: B:58:0x00e8->B:45:0x00e8 BREAK  A[LOOP:0: B:6:0x0015->B:76:0x0015], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void recompose(com.ibm.icu.impl.Normalizer2Impl.ReorderingBuffer r16, int r17, boolean r18) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 267
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.Normalizer2Impl.recompose(com.ibm.icu.impl.Normalizer2Impl$ReorderingBuffer, int, boolean):void");
    }

    public int composePair(int i, int i2) {
        int compositionsListForMaybe;
        int norm16 = getNorm16(i);
        if (isInert(norm16)) {
            return -1;
        }
        if (norm16 < this.minYesNoMappingsOnly) {
            if (isJamoL(norm16)) {
                int i3 = i2 - 4449;
                if (i3 < 0 || i3 >= 21) {
                    return -1;
                }
                return ((((i - 4352) * 21) + i3) * 28) + Hangul.HANGUL_BASE;
            }
            if (isHangulLV(norm16)) {
                int i4 = i2 - 4519;
                if (i4 <= 0 || i4 >= 28) {
                    return -1;
                }
                return i + i4;
            }
            compositionsListForMaybe = ((MIN_NORMAL_MAYBE_YES - this.minMaybeYes) + norm16) >> 1;
            if (norm16 > this.minYesNo) {
                compositionsListForMaybe += (this.maybeYesCompositions.charAt(compositionsListForMaybe) & 31) + 1;
            }
        } else {
            if (norm16 >= this.minMaybeYes && 64512 > norm16) {
                compositionsListForMaybe = getCompositionsListForMaybe(norm16);
            }
            return -1;
        }
        if (i2 >= 0 && 1114111 >= i2) {
            return combine(this.maybeYesCompositions, compositionsListForMaybe, i2) >> 1;
        }
        return -1;
    }

    private boolean hasCompBoundaryBefore(int i, int i2) {
        return i < this.minCompNoMaybeCP || norm16HasCompBoundaryBefore(i2);
    }

    private boolean norm16HasCompBoundaryBefore(int i) {
        return i < this.minNoNoCompNoMaybeCC || isAlgorithmicNoNo(i);
    }

    private boolean hasCompBoundaryBefore(CharSequence charSequence, int i, int i2) {
        return i == i2 || hasCompBoundaryBefore(Character.codePointAt(charSequence, i));
    }

    private boolean norm16HasCompBoundaryAfter(int i, boolean z) {
        return (i & 1) != 0 && (!z || isTrailCC01ForCompBoundaryAfter(i));
    }

    private boolean hasCompBoundaryAfter(CharSequence charSequence, int i, int i2, boolean z) {
        return i == i2 || hasCompBoundaryAfter(Character.codePointBefore(charSequence, i2), z);
    }

    private boolean isTrailCC01ForCompBoundaryAfter(int i) {
        if (isInert(i)) {
            return true;
        }
        if (isDecompNoAlgorithmic(i)) {
            if ((i & 6) <= 2) {
                return true;
            }
        } else if (this.extraData.charAt(i >> 1) <= 511) {
            return true;
        }
        return false;
    }

    private int findPreviousCompBoundary(CharSequence charSequence, int i, boolean z) {
        while (i > 0) {
            int iCodePointBefore = Character.codePointBefore(charSequence, i);
            int norm16 = getNorm16(iCodePointBefore);
            if (norm16HasCompBoundaryAfter(norm16, z)) {
                break;
            }
            i -= Character.charCount(iCodePointBefore);
            if (hasCompBoundaryBefore(iCodePointBefore, norm16)) {
                break;
            }
        }
        return i;
    }

    private int findNextCompBoundary(CharSequence charSequence, int i, int i2, boolean z) {
        while (i < i2) {
            int iCodePointAt = Character.codePointAt(charSequence, i);
            int i3 = this.normTrie.get(iCodePointAt);
            if (hasCompBoundaryBefore(iCodePointAt, i3)) {
                break;
            }
            i += Character.charCount(iCodePointAt);
            if (norm16HasCompBoundaryAfter(i3, z)) {
                break;
            }
        }
        return i;
    }

    private int findPreviousFCDBoundary(CharSequence charSequence, int i) {
        while (i > 0) {
            int iCodePointBefore = Character.codePointBefore(charSequence, i);
            if (iCodePointBefore < this.minDecompNoCP) {
                break;
            }
            int norm16 = getNorm16(iCodePointBefore);
            if (norm16HasDecompBoundaryAfter(norm16)) {
                break;
            }
            i -= Character.charCount(iCodePointBefore);
            if (norm16HasDecompBoundaryBefore(norm16)) {
                break;
            }
        }
        return i;
    }

    private int findNextFCDBoundary(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            int iCodePointAt = Character.codePointAt(charSequence, i);
            if (iCodePointAt < this.minLcccCP) {
                break;
            }
            int norm16 = getNorm16(iCodePointAt);
            if (norm16HasDecompBoundaryBefore(norm16)) {
                break;
            }
            i += Character.charCount(iCodePointAt);
            if (norm16HasDecompBoundaryAfter(norm16)) {
                break;
            }
        }
        return i;
    }

    private int getPreviousTrailCC(CharSequence charSequence, int i, int i2) {
        if (i == i2) {
            return 0;
        }
        return getFCD16(Character.codePointBefore(charSequence, i2));
    }

    private void addToStartSet(MutableCodePointTrie mutableCodePointTrie, int i, int i2) {
        UnicodeSet unicodeSet;
        int i3 = mutableCodePointTrie.get(i2);
        if ((4194303 & i3) == 0 && i != 0) {
            mutableCodePointTrie.set(i2, i | i3);
            return;
        }
        if ((i3 & 2097152) == 0) {
            int i4 = i3 & 2097151;
            mutableCodePointTrie.set(i2, (i3 & (-2097152)) | 2097152 | this.canonStartSets.size());
            ArrayList<UnicodeSet> arrayList = this.canonStartSets;
            unicodeSet = new UnicodeSet();
            arrayList.add(unicodeSet);
            if (i4 != 0) {
                unicodeSet.add(i4);
            }
        } else {
            unicodeSet = this.canonStartSets.get(i3 & 2097151);
        }
        unicodeSet.add(i);
    }
}
