package com.ibm.icu.impl.coll;

import com.ibm.icu.util.CharsTrie;
import it.piapostolos.android.pinotes.R2;
import java.lang.reflect.Array;

/* loaded from: classes2.dex */
final class CollationFastLatinBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long CONTRACTION_FLAG = 2147483648L;
    private static final int NUM_SPECIAL_GROUPS = 4;
    private long[][] charCEs = (long[][]) Array.newInstance((Class<?>) long.class, R2.attr.fabSize, 2);
    long[] lastSpecialPrimaries = new long[4];
    private StringBuilder result = new StringBuilder();
    private long ce0 = 0;
    private long ce1 = 0;
    private UVector64 contractionCEs = new UVector64();
    private UVector64 uniqueCEs = new UVector64();
    private char[] miniCEs = null;
    private long firstDigitPrimary = 0;
    private long firstLatinPrimary = 0;
    private long lastLatinPrimary = 0;
    private long firstShortPrimary = 0;
    private boolean shortPrimaryOverflow = false;
    private int headerLength = 0;

    private static final int compareInt64AsUnsigned(long j, long j2) {
        long j3 = j - Long.MIN_VALUE;
        long j4 = j2 - Long.MIN_VALUE;
        if (j3 < j4) {
            return -1;
        }
        return j3 > j4 ? 1 : 0;
    }

    private static boolean isContractionCharCE(long j) {
        return (j >>> 32) == 1 && j != Collation.NO_CE;
    }

    private static final int binarySearch(long[] jArr, int i, long j) {
        if (i == 0) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            int i3 = (int) ((i2 + i) / 2);
            int iCompareInt64AsUnsigned = compareInt64AsUnsigned(j, jArr[i3]);
            if (iCompareInt64AsUnsigned == 0) {
                return i3;
            }
            if (iCompareInt64AsUnsigned < 0) {
                if (i3 == i2) {
                    return ~i2;
                }
                i = i3;
            } else {
                if (i3 == i2) {
                    return ~(i2 + 1);
                }
                i2 = i3;
            }
        }
    }

    CollationFastLatinBuilder() {
    }

    boolean forData(CollationData collationData) {
        if (this.result.length() != 0) {
            throw new IllegalStateException("attempt to reuse a CollationFastLatinBuilder");
        }
        if (!loadGroups(collationData)) {
            return false;
        }
        this.firstShortPrimary = this.firstDigitPrimary;
        getCEs(collationData);
        encodeUniqueCEs();
        if (this.shortPrimaryOverflow) {
            this.firstShortPrimary = this.firstLatinPrimary;
            resetCEs();
            getCEs(collationData);
            encodeUniqueCEs();
        }
        boolean z = !this.shortPrimaryOverflow;
        if (z) {
            encodeCharCEs();
            encodeContractions();
        }
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        return z;
    }

    char[] getHeader() {
        int i = this.headerLength;
        char[] cArr = new char[i];
        this.result.getChars(0, i, cArr, 0);
        return cArr;
    }

    char[] getTable() {
        int length = this.result.length();
        int i = this.headerLength;
        char[] cArr = new char[length - i];
        StringBuilder sb = this.result;
        sb.getChars(i, sb.length(), cArr, 0);
        return cArr;
    }

    private boolean loadGroups(CollationData collationData) {
        this.headerLength = 5;
        this.result.append((char) (5 | 512));
        for (int i = 0; i < 4; i++) {
            this.lastSpecialPrimaries[i] = collationData.getLastPrimaryForGroup(i + 4096);
            if (this.lastSpecialPrimaries[i] == 0) {
                return false;
            }
            this.result.append(0);
        }
        this.firstDigitPrimary = collationData.getFirstPrimaryForGroup(4100);
        this.firstLatinPrimary = collationData.getFirstPrimaryForGroup(25);
        this.lastLatinPrimary = collationData.getLastPrimaryForGroup(25);
        return (this.firstDigitPrimary == 0 || this.firstLatinPrimary == 0) ? false : true;
    }

    private boolean inSameGroup(long j, long j2) {
        long j3 = this.firstShortPrimary;
        if (j >= j3) {
            return j2 >= j3;
        }
        if (j2 >= j3) {
            return false;
        }
        long j4 = this.lastSpecialPrimaries[3];
        if (j > j4) {
            return j2 > j4;
        }
        if (j2 > j4) {
            return false;
        }
        int i = 0;
        while (true) {
            long j5 = this.lastSpecialPrimaries[i];
            if (j <= j5) {
                return j2 <= j5;
            }
            if (j2 <= j5) {
                return false;
            }
            i++;
        }
    }

    private void resetCEs() {
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        this.shortPrimaryOverflow = false;
        this.result.setLength(this.headerLength);
    }

    private void getCEs(CollationData collationData) {
        int ce32;
        CollationData collationData2;
        char c = 0;
        int i = 0;
        while (true) {
            if (c == 384) {
                c = 8192;
            } else if (c == 8256) {
                this.contractionCEs.addElement(511L);
                return;
            }
            int ce322 = collationData.getCE32(c);
            if (ce322 == 192) {
                collationData2 = collationData.base;
                ce32 = collationData2.getCE32(c);
            } else {
                ce32 = ce322;
                collationData2 = collationData;
            }
            if (getCEsFromCE32(collationData2, c, ce32)) {
                long[][] jArr = this.charCEs;
                long[] jArr2 = jArr[i];
                long j = this.ce0;
                jArr2[0] = j;
                jArr[i][1] = this.ce1;
                addUniqueCE(j);
                addUniqueCE(this.ce1);
            } else {
                long[][] jArr3 = this.charCEs;
                long[] jArr4 = jArr3[i];
                this.ce0 = Collation.NO_CE;
                jArr4[0] = 4311744768L;
                long[] jArr5 = jArr3[i];
                this.ce1 = 0L;
                jArr5[1] = 0;
            }
            if (c == 0 && !isContractionCharCE(this.ce0)) {
                addContractionEntry(511, this.ce0, this.ce1);
                long[][] jArr6 = this.charCEs;
                jArr6[0][0] = 6442450944L;
                jArr6[0][1] = 0;
            }
            i++;
            c = (char) (c + 1);
        }
    }

    private boolean getCEsFromCE32(CollationData collationData, int i, int i2) {
        int i3;
        int finalCE32 = collationData.getFinalCE32(i2);
        this.ce1 = 0L;
        if (Collation.isSimpleOrLongCE32(finalCE32)) {
            this.ce0 = Collation.ceFromCE32(finalCE32);
        } else {
            int iTagFromCE32 = Collation.tagFromCE32(finalCE32);
            if (iTagFromCE32 == 4) {
                this.ce0 = Collation.latinCE0FromCE32(finalCE32);
                this.ce1 = Collation.latinCE1FromCE32(finalCE32);
            } else if (iTagFromCE32 == 5) {
                int iIndexFromCE32 = Collation.indexFromCE32(finalCE32);
                int iLengthFromCE32 = Collation.lengthFromCE32(finalCE32);
                if (iLengthFromCE32 > 2) {
                    return false;
                }
                this.ce0 = Collation.ceFromCE32(collationData.ce32s[iIndexFromCE32]);
                if (iLengthFromCE32 == 2) {
                    this.ce1 = Collation.ceFromCE32(collationData.ce32s[iIndexFromCE32 + 1]);
                }
            } else if (iTagFromCE32 == 6) {
                int iIndexFromCE322 = Collation.indexFromCE32(finalCE32);
                int iLengthFromCE322 = Collation.lengthFromCE32(finalCE32);
                if (iLengthFromCE322 > 2) {
                    return false;
                }
                this.ce0 = collationData.ces[iIndexFromCE322];
                if (iLengthFromCE322 == 2) {
                    this.ce1 = collationData.ces[iIndexFromCE322 + 1];
                }
            } else {
                if (iTagFromCE32 == 9) {
                    return getCEsFromContractionCE32(collationData, finalCE32);
                }
                if (iTagFromCE32 != 14) {
                    return false;
                }
                this.ce0 = collationData.getCEFromOffsetCE32(i, finalCE32);
            }
        }
        long j = this.ce0;
        if (j == 0) {
            return this.ce1 == 0;
        }
        long j2 = j >>> 32;
        if (j2 == 0 || j2 > this.lastLatinPrimary) {
            return false;
        }
        int i4 = (int) j;
        if ((j2 < this.firstShortPrimary && (i4 & (-16384)) != 83886080) || (i3 = i4 & Collation.ONLY_TERTIARY_MASK) < 1280) {
            return false;
        }
        long j3 = this.ce1;
        if (j3 != 0) {
            long j4 = j3 >>> 32;
            if (j4 != 0 ? !inSameGroup(j2, j4) : j2 < this.firstShortPrimary) {
                return false;
            }
            int i5 = (int) this.ce1;
            if ((i5 >>> 16) == 0) {
                return false;
            }
            if ((j4 != 0 && j4 < this.firstShortPrimary && (i5 & (-16384)) != 83886080) || i3 < 1280) {
                return false;
            }
        }
        return ((this.ce0 | this.ce1) & 192) == 0;
    }

    private boolean getCEsFromContractionCE32(CollationData collationData, int i) {
        int iIndexFromCE32 = Collation.indexFromCE32(i);
        int cE32FromContexts = collationData.getCE32FromContexts(iIndexFromCE32);
        int size = this.contractionCEs.size();
        if (getCEsFromCE32(collationData, -1, cE32FromContexts)) {
            addContractionEntry(511, this.ce0, this.ce1);
        } else {
            addContractionEntry(511, Collation.NO_CE, 0L);
        }
        CharsTrie.Iterator it2 = CharsTrie.iterator(collationData.contexts, iIndexFromCE32 + 2, 0);
        boolean z = false;
        int i2 = -1;
        while (it2.hasNext()) {
            CharsTrie.Entry next = it2.next();
            CharSequence charSequence = next.chars;
            int charIndex = CollationFastLatin.getCharIndex(charSequence.charAt(0));
            if (charIndex >= 0) {
                if (charIndex != i2) {
                    if (z) {
                        int i3 = i2;
                        i2 = charIndex;
                        addContractionEntry(i3, this.ce0, this.ce1);
                    } else {
                        i2 = charIndex;
                    }
                    int i4 = next.value;
                    if (charSequence.length() == 1 && getCEsFromCE32(collationData, -1, i4)) {
                        z = true;
                    } else {
                        addContractionEntry(i2, Collation.NO_CE, 0L);
                        z = false;
                    }
                } else if (z) {
                    addContractionEntry(charIndex, Collation.NO_CE, 0L);
                    z = false;
                }
            }
        }
        if (z) {
            addContractionEntry(i2, this.ce0, this.ce1);
        }
        this.ce0 = 6442450944L | size;
        this.ce1 = 0L;
        return true;
    }

    private void addContractionEntry(int i, long j, long j2) {
        this.contractionCEs.addElement(i);
        this.contractionCEs.addElement(j);
        this.contractionCEs.addElement(j2);
        addUniqueCE(j);
        addUniqueCE(j2);
    }

    private void addUniqueCE(long j) {
        long j2;
        int iBinarySearch;
        if (j == 0 || (j >>> 32) == 1 || (iBinarySearch = binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), (j2 = j & (-49153)))) >= 0) {
            return;
        }
        this.uniqueCEs.insertElementAt(j2, ~iBinarySearch);
    }

    private int getMiniCE(long j) {
        return this.miniCEs[binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), j & (-49153))];
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d7 A[PHI: r9 r10 r11
      0x00d7: PHI (r9v3 int) = (r9v2 int), (r9v9 int) binds: [B:31:0x008b, B:57:0x00cd] A[DONT_GENERATE, DONT_INLINE]
      0x00d7: PHI (r10v3 int) = (r10v2 int), (r10v4 int) binds: [B:31:0x008b, B:57:0x00cd] A[DONT_GENERATE, DONT_INLINE]
      0x00d7: PHI (r11v3 int) = (r11v2 int), (r11v6 int) binds: [B:31:0x008b, B:57:0x00cd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e9 A[PHI: r11
      0x00e9: PHI (r11v4 int) = (r11v3 int), (r11v5 int) binds: [B:60:0x00db, B:63:0x00e0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void encodeUniqueCEs() {
        /*
            Method dump skipped, instructions count: 263
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationFastLatinBuilder.encodeUniqueCEs():void");
    }

    private void encodeCharCEs() {
        int length = this.result.length();
        for (int i = 0; i < 448; i++) {
            this.result.append(0);
        }
        int length2 = this.result.length();
        for (int i2 = 0; i2 < 448; i2++) {
            long j = this.charCEs[i2][0];
            if (!isContractionCharCE(j)) {
                int i3 = 1;
                int iEncodeTwoCEs = encodeTwoCEs(j, this.charCEs[i2][1]);
                if ((iEncodeTwoCEs >>> 16) > 0) {
                    int length3 = this.result.length() - length2;
                    if (length3 <= 1023) {
                        StringBuilder sb = this.result;
                        sb.append((char) (iEncodeTwoCEs >> 16));
                        sb.append((char) iEncodeTwoCEs);
                        i3 = length3 | 2048;
                    }
                } else {
                    i3 = iEncodeTwoCEs;
                }
                this.result.setCharAt(length + i2, (char) i3);
            }
        }
    }

    private void encodeContractions() {
        int i = this.headerLength + R2.attr.fabSize;
        int length = this.result.length();
        for (int i2 = 0; i2 < 448; i2++) {
            long j = this.charCEs[i2][0];
            if (isContractionCharCE(j)) {
                int length2 = this.result.length() - i;
                if (length2 > 1023) {
                    this.result.setCharAt(this.headerLength + i2, (char) 1);
                } else {
                    int i3 = Integer.MAX_VALUE & ((int) j);
                    boolean z = true;
                    while (true) {
                        if (this.contractionCEs.elementAti(i3) == 511 && !z) {
                            break;
                        }
                        int iEncodeTwoCEs = encodeTwoCEs(this.contractionCEs.elementAti(i3 + 1), this.contractionCEs.elementAti(i3 + 2));
                        if (iEncodeTwoCEs == 1) {
                            this.result.append((char) (r10 | 512));
                        } else if ((iEncodeTwoCEs >>> 16) == 0) {
                            this.result.append((char) (r10 | 1024));
                            this.result.append((char) iEncodeTwoCEs);
                        } else {
                            this.result.append((char) (r10 | 1536));
                            StringBuilder sb = this.result;
                            sb.append((char) (iEncodeTwoCEs >> 16));
                            sb.append((char) iEncodeTwoCEs);
                        }
                        i3 += 3;
                        z = false;
                    }
                    this.result.setCharAt(this.headerLength + i2, (char) (length2 | 1024));
                }
            }
        }
        if (this.result.length() > length) {
            this.result.append((char) 511);
        }
    }

    private int encodeTwoCEs(long j, long j2) {
        if (j == 0) {
            return 0;
        }
        if (j == Collation.NO_CE) {
            return 1;
        }
        int miniCE = getMiniCE(j);
        if (miniCE == 1) {
            return miniCE;
        }
        if (miniCE >= 4096) {
            miniCE |= ((((int) j) & Collation.CASE_MASK) >> 11) + 8;
        }
        if (j2 == 0) {
            return miniCE;
        }
        int miniCE2 = getMiniCE(j2);
        if (miniCE2 == 1) {
            return miniCE2;
        }
        int i = ((int) j2) & Collation.CASE_MASK;
        if (miniCE >= 4096 && (miniCE & R2.attr.spSelectedOptionTextColor) == 160) {
            int i2 = miniCE2 & R2.attr.spSelectedOptionTextColor;
            int i3 = miniCE2 & 7;
            if (i2 >= 384 && i == 0 && i3 == 0) {
                return (miniCE & (-993)) | i2;
            }
        }
        if (miniCE2 <= 992 || 4096 <= miniCE2) {
            miniCE2 |= (i >> 11) + 8;
        }
        return miniCE2 | (miniCE << 16);
    }
}
