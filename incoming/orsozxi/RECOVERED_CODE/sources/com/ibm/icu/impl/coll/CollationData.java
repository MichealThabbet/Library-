package com.ibm.icu.impl.coll;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public final class CollationData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    static final int JAMO_CE32S_LENGTH = 67;
    static final int MAX_NUM_SPECIAL_REORDER_CODES = 8;
    static final int REORDER_RESERVED_AFTER_LATIN = 4111;
    static final int REORDER_RESERVED_BEFORE_LATIN = 4110;
    public CollationData base;
    int[] ce32s;
    long[] ces;
    public boolean[] compressibleBytes;
    String contexts;
    public char[] fastLatinTable;
    char[] fastLatinTableHeader;
    public Normalizer2Impl nfcImpl;
    int numScripts;
    public long[] rootElements;
    char[] scriptStarts;
    char[] scriptsIndex;
    Trie2_32 trie;
    UnicodeSet unsafeBackwardSet;
    int[] jamoCE32s = new int[67];
    long numericPrimary = 301989888;

    CollationData(Normalizer2Impl normalizer2Impl) {
        this.nfcImpl = normalizer2Impl;
    }

    public int getCE32(int i) {
        return this.trie.get(i);
    }

    int getCE32FromSupplementary(int i) {
        return this.trie.get(i);
    }

    boolean isDigit(int i) {
        if (i < 1632) {
            return i <= 57 && 48 <= i;
        }
        return Collation.hasCE32Tag(getCE32(i), 10);
    }

    public boolean isUnsafeBackward(int i, boolean z) {
        return this.unsafeBackwardSet.contains(i) || (z && isDigit(i));
    }

    public boolean isCompressibleLeadByte(int i) {
        return this.compressibleBytes[i];
    }

    public boolean isCompressiblePrimary(long j) {
        return isCompressibleLeadByte(((int) j) >>> 24);
    }

    int getCE32FromContexts(int i) {
        return this.contexts.charAt(i + 1) | (this.contexts.charAt(i) << 16);
    }

    int getIndirectCE32(int i) {
        int iTagFromCE32 = Collation.tagFromCE32(i);
        if (iTagFromCE32 == 10) {
            return this.ce32s[Collation.indexFromCE32(i)];
        }
        if (iTagFromCE32 == 13) {
            return -1;
        }
        return iTagFromCE32 == 11 ? this.ce32s[0] : i;
    }

    int getFinalCE32(int i) {
        return Collation.isSpecialCE32(i) ? getIndirectCE32(i) : i;
    }

    long getCEFromOffsetCE32(int i, int i2) {
        return Collation.makeCE(Collation.getThreeBytePrimaryForOffsetData(i, this.ces[Collation.indexFromCE32(i2)]));
    }

    long getSingleCE(int i) {
        CollationData collationData;
        int ce32 = getCE32(i);
        if (ce32 == 192) {
            CollationData collationData2 = this.base;
            collationData = collationData2;
            ce32 = collationData2.getCE32(i);
        } else {
            collationData = this;
        }
        while (Collation.isSpecialCE32(ce32)) {
            switch (Collation.tagFromCE32(ce32)) {
                case 0:
                case 3:
                    throw new AssertionError(String.format("unexpected CE32 tag for U+%04X (CE32 0x%08x)", Integer.valueOf(i), Integer.valueOf(ce32)));
                case 1:
                    return Collation.ceFromLongPrimaryCE32(ce32);
                case 2:
                    return Collation.ceFromLongSecondaryCE32(ce32);
                case 4:
                case 7:
                case 8:
                case 9:
                case 12:
                case 13:
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", Integer.valueOf(i), Integer.valueOf(ce32)));
                case 5:
                    if (Collation.lengthFromCE32(ce32) == 1) {
                        ce32 = collationData.ce32s[Collation.indexFromCE32(ce32)];
                        break;
                    } else {
                        throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", Integer.valueOf(i), Integer.valueOf(ce32)));
                    }
                case 6:
                    if (Collation.lengthFromCE32(ce32) == 1) {
                        return collationData.ces[Collation.indexFromCE32(ce32)];
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", Integer.valueOf(i), Integer.valueOf(ce32)));
                case 10:
                    ce32 = collationData.ce32s[Collation.indexFromCE32(ce32)];
                    break;
                case 11:
                    ce32 = collationData.ce32s[0];
                    break;
                case 14:
                    return collationData.getCEFromOffsetCE32(i, ce32);
                case 15:
                    return Collation.unassignedCEFromCodePoint(i);
            }
        }
        return Collation.ceFromSimpleCE32(ce32);
    }

    int getFCD16(int i) {
        return this.nfcImpl.getFCD16(i);
    }

    long getFirstPrimaryForGroup(int i) {
        if (getScriptIndex(i) == 0) {
            return 0L;
        }
        return this.scriptStarts[r3] << 16;
    }

    public long getLastPrimaryForGroup(int i) {
        if (getScriptIndex(i) == 0) {
            return 0L;
        }
        return (this.scriptStarts[r5 + 1] << 16) - 1;
    }

    public int getGroupForPrimary(long j) {
        long j2 = j >> 16;
        char[] cArr = this.scriptStarts;
        int i = 1;
        if (j2 >= cArr[1] && cArr[cArr.length - 1] > j2) {
            while (true) {
                int i2 = i + 1;
                if (j2 < this.scriptStarts[i2]) {
                    break;
                }
                i = i2;
            }
            for (int i3 = 0; i3 < this.numScripts; i3++) {
                if (this.scriptsIndex[i3] == i) {
                    return i3;
                }
            }
            for (int i4 = 0; i4 < 8; i4++) {
                if (this.scriptsIndex[this.numScripts + i4] == i) {
                    return i4 + 4096;
                }
            }
        }
        return -1;
    }

    private int getScriptIndex(int i) {
        int i2;
        if (i < 0) {
            return 0;
        }
        int i3 = this.numScripts;
        if (i < i3) {
            return this.scriptsIndex[i];
        }
        if (i >= 4096 && i - 4096 < 8) {
            return this.scriptsIndex[i3 + i2];
        }
        return 0;
    }

    public int[] getEquivalentScripts(int i) {
        int scriptIndex = getScriptIndex(i);
        if (scriptIndex == 0) {
            return EMPTY_INT_ARRAY;
        }
        if (i >= 4096) {
            return new int[]{i};
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.numScripts; i3++) {
            if (this.scriptsIndex[i3] == scriptIndex) {
                i2++;
            }
        }
        int[] iArr = new int[i2];
        if (i2 == 1) {
            iArr[0] = i;
            return iArr;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < this.numScripts; i5++) {
            if (this.scriptsIndex[i5] == scriptIndex) {
                iArr[i4] = i5;
                i4++;
            }
        }
        return iArr;
    }

    void makeReorderRanges(int[] iArr, UVector32 uVector32) {
        makeReorderRanges(iArr, false, uVector32);
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0165, code lost:
    
        if (r5 != 0) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x016b, code lost:
    
        if (r1 >= (r18.scriptStarts.length - 1)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x016d, code lost:
    
        r21.addElement((r5 & 65535) | (r18.scriptStarts[r1] << 16));
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x017f, code lost:
    
        if (r1 != (r18.scriptStarts.length - 1)) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0181, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0182, code lost:
    
        r1 = r1 + 1;
        r5 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0116, code lost:
    
        r4 = 1;
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0117, code lost:
    
        r5 = r18.scriptStarts;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x011b, code lost:
    
        if (r4 >= (r5.length - 1)) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x011f, code lost:
    
        if (r7[r4] == 0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0122, code lost:
    
        r5 = r5[r4];
        r10 = r10;
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0124, code lost:
    
        if (r3 != false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0126, code lost:
    
        if (r5 <= r10) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0128, code lost:
    
        r10 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0129, code lost:
    
        r10 = addLowScriptRange(r7, r4, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x012e, code lost:
    
        r4 = r4 + 1;
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0131, code lost:
    
        if (r10 <= r8) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0138, code lost:
    
        if (((r10 == true ? 1 : 0) - (65280 & r11)) > r8) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x013a, code lost:
    
        makeReorderRanges(r19, true, r21);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x013d, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0145, code lost:
    
        throw new com.ibm.icu.util.ICUException("setReorderCodes(): reordering too many partial-primary-lead-byte scripts");
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0146, code lost:
    
        r1 = 1;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0148, code lost:
    
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0149, code lost:
    
        r4 = r18.scriptStarts;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x014d, code lost:
    
        if (r1 >= (r4.length - 1)) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x014f, code lost:
    
        r8 = r7[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0151, code lost:
    
        if (r8 != 255) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0156, code lost:
    
        r3 = r8 - (r4[r1] >> '\b');
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x015d, code lost:
    
        if (r3 == r5) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0160, code lost:
    
        r1 = r1 + 1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15, types: [int] */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v19, types: [int] */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v22 */
    /* JADX WARN: Type inference failed for: r10v24 */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v4, types: [int] */
    /* JADX WARN: Type inference failed for: r10v6, types: [int] */
    /* JADX WARN: Type inference failed for: r18v0, types: [com.ibm.icu.impl.coll.CollationData] */
    /* JADX WARN: Type inference failed for: r5v11, types: [int] */
    /* JADX WARN: Type inference failed for: r8v16, types: [int] */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18, types: [int] */
    /* JADX WARN: Type inference failed for: r8v5, types: [char] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void makeReorderRanges(int[] r19, boolean r20, com.ibm.icu.impl.coll.UVector32 r21) {
        /*
            Method dump skipped, instructions count: 391
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationData.makeReorderRanges(int[], boolean, com.ibm.icu.impl.coll.UVector32):void");
    }

    private int addLowScriptRange(short[] sArr, int i, int i2) {
        char c = this.scriptStarts[i];
        if ((c & 255) < (i2 & 255)) {
            i2 += 256;
        }
        sArr[i] = (short) (i2 >> 8);
        char c2 = this.scriptStarts[i + 1];
        return (c2 & 255) | ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + ((c2 & 65280) - (65280 & c)));
    }

    private int addHighScriptRange(short[] sArr, int i, int i2) {
        char c = this.scriptStarts[i + 1];
        if ((c & 255) > (i2 & 255)) {
            i2 += InputDeviceCompat.SOURCE_ANY;
        }
        char c2 = this.scriptStarts[i];
        int i3 = ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) - ((c & 65280) - (65280 & c2))) | (c2 & 255);
        sArr[i] = (short) (i3 >> 8);
        return i3;
    }

    private static String scriptCodeString(int i) {
        if (i < 4096) {
            return Integer.toString(i);
        }
        return "0x" + Integer.toHexString(i);
    }
}
