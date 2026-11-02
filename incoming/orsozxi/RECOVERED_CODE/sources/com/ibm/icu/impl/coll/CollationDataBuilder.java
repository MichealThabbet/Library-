package com.ibm.icu.impl.coll;

import androidx.core.view.MotionEventCompat;
import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Trie2Writable;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UnicodeSetIterator;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.StringTrieBuilder;
import it.orsozoxi.android.orsonotes.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes2.dex */
final class CollationDataBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int IS_BUILDER_JAMO_CE32 = 256;
    protected UnicodeSet contextChars = new UnicodeSet();
    protected StringBuilder contexts = new StringBuilder();
    protected UnicodeSet unsafeBackwardSet = new UnicodeSet();
    protected Normalizer2Impl nfcImpl = Norm2AllModes.getNFCInstance().impl;
    protected CollationData base = null;
    protected CollationSettings baseSettings = null;
    protected Trie2Writable trie = null;
    protected UVector32 ce32s = new UVector32();
    protected UVector64 ce64s = new UVector64();
    protected ArrayList<ConditionalCE32> conditionalCE32s = new ArrayList<>();
    protected boolean modified = false;
    protected boolean fastLatinEnabled = false;
    protected CollationFastLatinBuilder fastLatinBuilder = null;
    protected DataBuilderCollationIterator collIter = null;

    interface CEModifier {
        long modifyCE(long j);

        long modifyCE32(int i);
    }

    protected static int jamoCpFromIndex(int i) {
        if (i < 19) {
            return i + 4352;
        }
        int i2 = i - 19;
        return i2 < 21 ? i2 + 4449 : (i2 - 21) + R2.style.Text_Normal_Widget;
    }

    CollationDataBuilder() {
        this.ce32s.addElement(0);
    }

    void initForTailoring(CollationData collationData) {
        if (this.trie != null) {
            throw new IllegalStateException("attempt to reuse a CollationDataBuilder");
        }
        if (collationData == null) {
            throw new IllegalArgumentException("null CollationData");
        }
        this.base = collationData;
        this.trie = new Trie2Writable(192, -195323);
        for (int i = 192; i <= 255; i++) {
            this.trie.set(i, 192);
        }
        this.trie.setRange(Normalizer2Impl.Hangul.HANGUL_BASE, Normalizer2Impl.Hangul.HANGUL_END, Collation.makeCE32FromTagAndIndex(12, 0), true);
        this.unsafeBackwardSet.addAll(collationData.unsafeBackwardSet);
    }

    boolean isCompressibleLeadByte(int i) {
        return this.base.isCompressibleLeadByte(i);
    }

    boolean isCompressiblePrimary(long j) {
        return isCompressibleLeadByte(((int) j) >>> 24);
    }

    boolean hasMappings() {
        return this.modified;
    }

    boolean isAssigned(int i) {
        return Collation.isAssignedCE32(this.trie.get(i));
    }

    void add(CharSequence charSequence, CharSequence charSequence2, long[] jArr, int i) {
        addCE32(charSequence, charSequence2, encodeCEs(jArr, i));
    }

    int encodeCEs(long[] jArr, int i) {
        if (i < 0 || i > 31) {
            throw new IllegalArgumentException("mapping to too many CEs");
        }
        if (!isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        if (i == 0) {
            return encodeOneCEAsCE32(0L);
        }
        if (i == 1) {
            return encodeOneCE(jArr[0]);
        }
        if (i == 2) {
            long j = jArr[0];
            long j2 = jArr[1];
            long j3 = j >>> 32;
            if ((72057594037862655L & j) == 83886080 && ((-4278190081L) & j2) == 1280 && j3 != 0) {
                return ((int) j3) | ((((int) j) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 8) | ((((int) j2) >> 16) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | 192 | 4;
            }
        }
        int[] iArr = new int[31];
        for (int i2 = 0; i2 != i; i2++) {
            int iEncodeOneCEAsCE32 = encodeOneCEAsCE32(jArr[i2]);
            if (iEncodeOneCEAsCE32 != 1) {
                iArr[i2] = iEncodeOneCEAsCE32;
            } else {
                return encodeExpansion(jArr, 0, i);
            }
        }
        return encodeExpansion32(iArr, 0, i);
    }

    void addCE32(CharSequence charSequence, CharSequence charSequence2, int i) {
        ConditionalCE32 conditionalCE32ForCE32;
        if (charSequence2.length() == 0) {
            throw new IllegalArgumentException("mapping from empty string");
        }
        if (!isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        int iCodePointAt = Character.codePointAt(charSequence2, 0);
        int iCharCount = Character.charCount(iCodePointAt);
        int iCopyFromBaseCE32 = this.trie.get(iCodePointAt);
        boolean z = charSequence.length() != 0 || charSequence2.length() > iCharCount;
        if (iCopyFromBaseCE32 == 192) {
            CollationData collationData = this.base;
            int finalCE32 = collationData.getFinalCE32(collationData.getCE32(iCodePointAt));
            if (z || Collation.ce32HasContext(finalCE32)) {
                iCopyFromBaseCE32 = copyFromBaseCE32(iCodePointAt, finalCE32, true);
                this.trie.set(iCodePointAt, iCopyFromBaseCE32);
            }
        }
        if (!z) {
            if (!isBuilderContextCE32(iCopyFromBaseCE32)) {
                this.trie.set(iCodePointAt, i);
            } else {
                ConditionalCE32 conditionalCE32ForCE322 = getConditionalCE32ForCE32(iCopyFromBaseCE32);
                conditionalCE32ForCE322.builtCE32 = 1;
                conditionalCE32ForCE322.ce32 = i;
            }
        } else {
            if (!isBuilderContextCE32(iCopyFromBaseCE32)) {
                int iAddConditionalCE32 = addConditionalCE32("\u0000", iCopyFromBaseCE32);
                this.trie.set(iCodePointAt, makeBuilderContextCE32(iAddConditionalCE32));
                this.contextChars.add(iCodePointAt);
                conditionalCE32ForCE32 = getConditionalCE32(iAddConditionalCE32);
            } else {
                conditionalCE32ForCE32 = getConditionalCE32ForCE32(iCopyFromBaseCE32);
                conditionalCE32ForCE32.builtCE32 = 1;
            }
            CharSequence charSequenceSubSequence = charSequence2.subSequence(iCharCount, charSequence2.length());
            StringBuilder sb = new StringBuilder();
            sb.append((char) charSequence.length());
            sb.append(charSequence);
            sb.append(charSequenceSubSequence);
            String string = sb.toString();
            this.unsafeBackwardSet.addAll(charSequenceSubSequence);
            while (true) {
                int i2 = conditionalCE32ForCE32.next;
                if (i2 < 0) {
                    conditionalCE32ForCE32.next = addConditionalCE32(string, i);
                    break;
                }
                ConditionalCE32 conditionalCE32 = getConditionalCE32(i2);
                int iCompareTo = string.compareTo(conditionalCE32.context);
                if (iCompareTo < 0) {
                    int iAddConditionalCE322 = addConditionalCE32(string, i);
                    conditionalCE32ForCE32.next = iAddConditionalCE322;
                    getConditionalCE32(iAddConditionalCE322).next = i2;
                    break;
                } else {
                    if (iCompareTo == 0) {
                        conditionalCE32.ce32 = i;
                        break;
                    }
                    conditionalCE32ForCE32 = conditionalCE32;
                }
            }
        }
        this.modified = true;
    }

    void copyFrom(CollationDataBuilder collationDataBuilder, CEModifier cEModifier) {
        if (!isMutable()) {
            throw new IllegalStateException("attempt to copyFrom() after build()");
        }
        CopyHelper copyHelper = new CopyHelper(collationDataBuilder, this, cEModifier);
        Iterator<Trie2.Range> it2 = collationDataBuilder.trie.iterator();
        while (it2.hasNext()) {
            Trie2.Range next = it2.next();
            if (next.leadSurrogate) {
                break;
            } else {
                enumRangeForCopy(next.startCodePoint, next.endCodePoint, next.value, copyHelper);
            }
        }
        this.modified = collationDataBuilder.modified | this.modified;
    }

    void optimize(UnicodeSet unicodeSet) {
        if (unicodeSet.isEmpty()) {
            return;
        }
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            int i = unicodeSetIterator.codepoint;
            if (this.trie.get(i) == 192) {
                CollationData collationData = this.base;
                this.trie.set(i, copyFromBaseCE32(i, collationData.getFinalCE32(collationData.getCE32(i)), true));
            }
        }
        this.modified = true;
    }

    void suppressContractions(UnicodeSet unicodeSet) {
        if (unicodeSet.isEmpty()) {
            return;
        }
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            int i = unicodeSetIterator.codepoint;
            int i2 = this.trie.get(i);
            if (i2 == 192) {
                CollationData collationData = this.base;
                int finalCE32 = collationData.getFinalCE32(collationData.getCE32(i));
                if (Collation.ce32HasContext(finalCE32)) {
                    this.trie.set(i, copyFromBaseCE32(i, finalCE32, false));
                }
            } else if (isBuilderContextCE32(i2)) {
                this.trie.set(i, getConditionalCE32ForCE32(i2).ce32);
                this.contextChars.remove(i);
            }
        }
        this.modified = true;
    }

    void enableFastLatin() {
        this.fastLatinEnabled = true;
    }

    void build(CollationData collationData) {
        buildMappings(collationData);
        CollationData collationData2 = this.base;
        if (collationData2 != null) {
            collationData.numericPrimary = collationData2.numericPrimary;
            collationData.compressibleBytes = this.base.compressibleBytes;
            collationData.numScripts = this.base.numScripts;
            collationData.scriptsIndex = this.base.scriptsIndex;
            collationData.scriptStarts = this.base.scriptStarts;
        }
        buildFastLatinTable(collationData);
    }

    int getCEs(CharSequence charSequence, long[] jArr, int i) {
        return getCEs(charSequence, 0, jArr, i);
    }

    int getCEs(CharSequence charSequence, CharSequence charSequence2, long[] jArr, int i) {
        int length = charSequence.length();
        if (length == 0) {
            return getCEs(charSequence2, 0, jArr, i);
        }
        StringBuilder sb = new StringBuilder(charSequence);
        sb.append(charSequence2);
        return getCEs(sb, length, jArr, i);
    }

    private static final class ConditionalCE32 {
        int ce32;
        String context;
        int defaultCE32 = 1;
        int builtCE32 = 1;
        int next = -1;

        ConditionalCE32(String str, int i) {
            this.context = str;
            this.ce32 = i;
        }

        boolean hasContext() {
            return this.context.length() > 1;
        }

        int prefixLength() {
            return this.context.charAt(0);
        }
    }

    protected int getCE32FromOffsetCE32(boolean z, int i, int i2) {
        int iIndexFromCE32 = Collation.indexFromCE32(i2);
        return Collation.makeLongPrimaryCE32(Collation.getThreeBytePrimaryForOffsetData(i, z ? this.base.ces[iIndexFromCE32] : this.ce64s.elementAti(iIndexFromCE32)));
    }

    protected int addCE(long j) {
        int size = this.ce64s.size();
        for (int i = 0; i < size; i++) {
            if (j == this.ce64s.elementAti(i)) {
                return i;
            }
        }
        this.ce64s.addElement(j);
        return size;
    }

    protected int addCE32(int i) {
        int size = this.ce32s.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i == this.ce32s.elementAti(i2)) {
                return i2;
            }
        }
        this.ce32s.addElement(i);
        return size;
    }

    protected int addConditionalCE32(String str, int i) {
        int size = this.conditionalCE32s.size();
        if (size > 524287) {
            throw new IndexOutOfBoundsException("too many context-sensitive mappings");
        }
        this.conditionalCE32s.add(new ConditionalCE32(str, i));
        return size;
    }

    protected ConditionalCE32 getConditionalCE32(int i) {
        return this.conditionalCE32s.get(i);
    }

    protected ConditionalCE32 getConditionalCE32ForCE32(int i) {
        return getConditionalCE32(Collation.indexFromCE32(i));
    }

    protected static int makeBuilderContextCE32(int i) {
        return Collation.makeCE32FromTagAndIndex(7, i);
    }

    protected static boolean isBuilderContextCE32(int i) {
        return Collation.hasCE32Tag(i, 7);
    }

    protected static int encodeOneCEAsCE32(long j) {
        long j2 = j >>> 32;
        int i = (int) j;
        int i2 = 65535 & i;
        if ((281470698455295L & j) == 0) {
            return ((int) j2) | (i >>> 16) | (i2 >> 8);
        }
        if ((j & 1099511627775L) == 83887360) {
            return Collation.makeLongPrimaryCE32(j2);
        }
        if (j2 == 0 && (i2 & 255) == 0) {
            return Collation.makeLongSecondaryCE32(i);
        }
        return 1;
    }

    protected int encodeOneCE(long j) {
        int iEncodeOneCEAsCE32 = encodeOneCEAsCE32(j);
        if (iEncodeOneCEAsCE32 != 1) {
            return iEncodeOneCEAsCE32;
        }
        int iAddCE = addCE(j);
        if (iAddCE > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        return Collation.makeCE32FromTagIndexAndLength(6, iAddCE, 1);
    }

    protected int encodeExpansion(long[] jArr, int i, int i2) {
        long j = jArr[i];
        int size = this.ce64s.size() - i2;
        for (int i3 = 0; i3 <= size; i3++) {
            if (j == this.ce64s.elementAti(i3)) {
                if (i3 > 524287) {
                    throw new IndexOutOfBoundsException("too many mappings");
                }
                for (int i4 = 1; i4 != i2; i4++) {
                    if (this.ce64s.elementAti(i3 + i4) != jArr[i + i4]) {
                        break;
                    }
                }
                return Collation.makeCE32FromTagIndexAndLength(6, i3, i2);
            }
        }
        int size2 = this.ce64s.size();
        if (size2 > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        for (int i5 = 0; i5 < i2; i5++) {
            this.ce64s.addElement(jArr[i + i5]);
        }
        return Collation.makeCE32FromTagIndexAndLength(6, size2, i2);
    }

    protected int encodeExpansion32(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        int size = this.ce32s.size() - i2;
        for (int i4 = 0; i4 <= size; i4++) {
            if (i3 == this.ce32s.elementAti(i4)) {
                if (i4 > 524287) {
                    throw new IndexOutOfBoundsException("too many mappings");
                }
                for (int i5 = 1; i5 != i2; i5++) {
                    if (this.ce32s.elementAti(i4 + i5) != iArr[i + i5]) {
                        break;
                    }
                }
                return Collation.makeCE32FromTagIndexAndLength(5, i4, i2);
            }
        }
        int size2 = this.ce32s.size();
        if (size2 > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        for (int i6 = 0; i6 < i2; i6++) {
            this.ce32s.addElement(iArr[i + i6]);
        }
        return Collation.makeCE32FromTagIndexAndLength(5, size2, i2);
    }

    protected int copyFromBaseCE32(int i, int i2, boolean z) {
        int iTagFromCE32;
        int iAddConditionalCE32;
        int iCopyContractionsFromBaseCE32;
        if (!Collation.isSpecialCE32(i2) || (iTagFromCE32 = Collation.tagFromCE32(i2)) == 1 || iTagFromCE32 == 2 || iTagFromCE32 == 4) {
            return i2;
        }
        if (iTagFromCE32 == 5) {
            return encodeExpansion32(this.base.ce32s, Collation.indexFromCE32(i2), Collation.lengthFromCE32(i2));
        }
        if (iTagFromCE32 == 6) {
            return encodeExpansion(this.base.ces, Collation.indexFromCE32(i2), Collation.lengthFromCE32(i2));
        }
        if (iTagFromCE32 != 8) {
            if (iTagFromCE32 != 9) {
                if (iTagFromCE32 == 12) {
                    throw new UnsupportedOperationException("We forbid tailoring of Hangul syllables.");
                }
                if (iTagFromCE32 == 14) {
                    return getCE32FromOffsetCE32(true, i, i2);
                }
                if (iTagFromCE32 == 15) {
                    return encodeOneCE(Collation.unassignedCEFromCodePoint(i));
                }
                throw new AssertionError("copyFromBaseCE32(c, ce32, withContext) requires ce32 == base.getFinalCE32(ce32)");
            }
            if (!z) {
                return copyFromBaseCE32(i, this.base.getCE32FromContexts(Collation.indexFromCE32(i2)), false);
            }
            ConditionalCE32 conditionalCE32 = new ConditionalCE32("", 0);
            copyContractionsFromBaseCE32(new StringBuilder("\u0000"), i, i2, conditionalCE32);
            int iMakeBuilderContextCE32 = makeBuilderContextCE32(conditionalCE32.next);
            this.contextChars.add(i);
            return iMakeBuilderContextCE32;
        }
        int iIndexFromCE32 = Collation.indexFromCE32(i2);
        int cE32FromContexts = this.base.getCE32FromContexts(iIndexFromCE32);
        if (!z) {
            return copyFromBaseCE32(i, cE32FromContexts, false);
        }
        ConditionalCE32 conditionalCE322 = new ConditionalCE32("", 0);
        StringBuilder sb = new StringBuilder("\u0000");
        if (Collation.isContractionCE32(cE32FromContexts)) {
            iAddConditionalCE32 = copyContractionsFromBaseCE32(sb, i, cE32FromContexts, conditionalCE322);
        } else {
            iAddConditionalCE32 = addConditionalCE32(sb.toString(), copyFromBaseCE32(i, cE32FromContexts, true));
            conditionalCE322.next = iAddConditionalCE32;
        }
        ConditionalCE32 conditionalCE323 = getConditionalCE32(iAddConditionalCE32);
        CharsTrie.Iterator it2 = CharsTrie.iterator(this.base.contexts, iIndexFromCE32 + 2, 0);
        while (it2.hasNext()) {
            CharsTrie.Entry next = it2.next();
            sb.setLength(0);
            sb.append(next.chars);
            sb.reverse().insert(0, (char) next.chars.length());
            int i3 = next.value;
            if (Collation.isContractionCE32(i3)) {
                iCopyContractionsFromBaseCE32 = copyContractionsFromBaseCE32(sb, i, i3, conditionalCE323);
            } else {
                int iAddConditionalCE322 = addConditionalCE32(sb.toString(), copyFromBaseCE32(i, i3, true));
                conditionalCE323.next = iAddConditionalCE322;
                iCopyContractionsFromBaseCE32 = iAddConditionalCE322;
            }
            conditionalCE323 = getConditionalCE32(iCopyContractionsFromBaseCE32);
        }
        int iMakeBuilderContextCE322 = makeBuilderContextCE32(conditionalCE322.next);
        this.contextChars.add(i);
        return iMakeBuilderContextCE322;
    }

    protected int copyContractionsFromBaseCE32(StringBuilder sb, int i, int i2, ConditionalCE32 conditionalCE32) {
        int iAddConditionalCE32;
        int iIndexFromCE32 = Collation.indexFromCE32(i2);
        if ((i2 & 256) != 0) {
            iAddConditionalCE32 = -1;
        } else {
            iAddConditionalCE32 = addConditionalCE32(sb.toString(), copyFromBaseCE32(i, this.base.getCE32FromContexts(iIndexFromCE32), true));
            conditionalCE32.next = iAddConditionalCE32;
            conditionalCE32 = getConditionalCE32(iAddConditionalCE32);
        }
        int length = sb.length();
        CharsTrie.Iterator it2 = CharsTrie.iterator(this.base.contexts, iIndexFromCE32 + 2, 0);
        while (it2.hasNext()) {
            CharsTrie.Entry next = it2.next();
            sb.append(next.chars);
            iAddConditionalCE32 = addConditionalCE32(sb.toString(), copyFromBaseCE32(i, next.value, true));
            conditionalCE32.next = iAddConditionalCE32;
            conditionalCE32 = getConditionalCE32(iAddConditionalCE32);
            sb.setLength(length);
        }
        return iAddConditionalCE32;
    }

    private static final class CopyHelper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        CollationDataBuilder dest;
        long[] modifiedCEs = new long[31];
        CEModifier modifier;
        CollationDataBuilder src;

        CopyHelper(CollationDataBuilder collationDataBuilder, CollationDataBuilder collationDataBuilder2, CEModifier cEModifier) {
            this.src = collationDataBuilder;
            this.dest = collationDataBuilder2;
            this.modifier = cEModifier;
        }

        void copyRangeCE32(int i, int i2, int i3) {
            int iCopyCE32 = copyCE32(i3);
            this.dest.trie.setRange(i, i2, iCopyCE32, true);
            if (CollationDataBuilder.isBuilderContextCE32(iCopyCE32)) {
                this.dest.contextChars.add(i, i2);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0069  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        int copyCE32(int r15) {
            /*
                Method dump skipped, instructions count: 307
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationDataBuilder.CopyHelper.copyCE32(int):int");
        }
    }

    private static void enumRangeForCopy(int i, int i2, int i3, CopyHelper copyHelper) {
        if (i3 == -1 || i3 == 192) {
            return;
        }
        copyHelper.copyRangeCE32(i, i2, i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean getJamoCE32s(int[] r12) {
        /*
            r11 = this;
            com.ibm.icu.impl.coll.CollationData r0 = r11.base
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L8
            r0 = 1
            goto L9
        L8:
            r0 = 0
        L9:
            r3 = 0
            r4 = 0
        Lb:
            r5 = 67
            r6 = 192(0xc0, float:2.69E-43)
            if (r3 >= r5) goto L62
            int r5 = jamoCpFromIndex(r3)
            com.ibm.icu.impl.Trie2Writable r7 = r11.trie
            int r7 = r7.get(r5)
            boolean r8 = com.ibm.icu.impl.coll.Collation.isAssignedCE32(r7)
            r0 = r0 | r8
            if (r7 != r6) goto L2a
            com.ibm.icu.impl.coll.CollationData r7 = r11.base
            int r7 = r7.getCE32(r5)
            r8 = 1
            goto L2b
        L2a:
            r8 = 0
        L2b:
            boolean r9 = com.ibm.icu.impl.coll.Collation.isSpecialCE32(r7)
            if (r9 == 0) goto L5c
            int r9 = com.ibm.icu.impl.coll.Collation.tagFromCE32(r7)
            if (r9 == 0) goto L47
            r10 = 3
            if (r9 == r10) goto L47
            switch(r9) {
                case 5: goto L43;
                case 6: goto L43;
                case 7: goto L47;
                case 8: goto L43;
                case 9: goto L43;
                case 10: goto L47;
                case 11: goto L47;
                case 12: goto L47;
                case 13: goto L47;
                case 14: goto L3e;
                case 15: goto L45;
                default: goto L3d;
            }
        L3d:
            goto L5c
        L3e:
            int r6 = r11.getCE32FromOffsetCE32(r8, r5, r7)
            goto L5d
        L43:
            if (r8 == 0) goto L5c
        L45:
            r4 = 1
            goto L5d
        L47:
            java.lang.AssertionError r12 = new java.lang.AssertionError
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r0[r1] = r2
            java.lang.String r1 = "unexpected special tag in ce32=0x%08x"
            java.lang.String r0 = java.lang.String.format(r1, r0)
            r12.<init>(r0)
            throw r12
        L5c:
            r6 = r7
        L5d:
            r12[r3] = r6
            int r3 = r3 + 1
            goto Lb
        L62:
            if (r0 == 0) goto L7f
            if (r4 == 0) goto L7f
        L66:
            if (r1 >= r5) goto L7f
            r3 = r12[r1]
            if (r3 != r6) goto L7c
            int r3 = jamoCpFromIndex(r1)
            com.ibm.icu.impl.coll.CollationData r4 = r11.base
            int r4 = r4.getCE32(r3)
            int r3 = r11.copyFromBaseCE32(r3, r4, r2)
            r12[r1] = r3
        L7c:
            int r1 = r1 + 1
            goto L66
        L7f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationDataBuilder.getJamoCE32s(int[]):boolean");
    }

    protected void setDigitTags() {
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(new UnicodeSet("[:Nd:]"));
        while (unicodeSetIterator.next()) {
            int i = unicodeSetIterator.codepoint;
            int i2 = this.trie.get(i);
            if (i2 != 192 && i2 != -1) {
                int iAddCE32 = addCE32(i2);
                if (iAddCE32 > 524287) {
                    throw new IndexOutOfBoundsException("too many mappings");
                }
                this.trie.set(i, Collation.makeCE32FromTagIndexAndLength(10, iAddCE32, UCharacter.digit(i)));
            }
        }
    }

    protected void setLeadSurrogates() {
        int i;
        for (char c = 55296; c < 56320; c = (char) (c + 1)) {
            Iterator<Trie2.Range> itIteratorForLeadSurrogate = this.trie.iteratorForLeadSurrogate(c);
            int i2 = -1;
            while (itIteratorForLeadSurrogate.hasNext()) {
                int i3 = itIteratorForLeadSurrogate.next().value;
                if (i3 != -1) {
                    if (i3 != 192) {
                        i2 = 512;
                        break;
                    }
                    i = 256;
                } else {
                    i = 0;
                }
                if (i2 >= 0) {
                    if (i2 != i) {
                        i2 = 512;
                        break;
                        break;
                    }
                } else {
                    i2 = i;
                }
            }
            this.trie.setForLeadSurrogateCodeUnit(c, Collation.makeCE32FromTagAndIndex(13, 0) | i2);
        }
    }

    protected void buildMappings(CollationData collationData) {
        boolean z;
        if (!isMutable()) {
            throw new IllegalStateException("attempt to build() after build()");
        }
        buildContexts();
        int[] iArr = new int[67];
        int size = -1;
        boolean jamoCE32s = getJamoCE32s(iArr);
        int i = Normalizer2Impl.Hangul.HANGUL_BASE;
        if (jamoCE32s) {
            size = this.ce32s.size();
            for (int i2 = 0; i2 < 67; i2++) {
                this.ce32s.addElement(iArr[i2]);
            }
            int i3 = 19;
            while (true) {
                if (i3 >= 67) {
                    z = false;
                    break;
                } else {
                    if (Collation.isSpecialCE32(iArr[i3])) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
            int iMakeCE32FromTagAndIndex = Collation.makeCE32FromTagAndIndex(12, 0);
            int i4 = 0;
            while (i4 < 19) {
                int i5 = i + 588;
                this.trie.setRange(i, i5 - 1, (z || Collation.isSpecialCE32(iArr[i4])) ? iMakeCE32FromTagAndIndex : iMakeCE32FromTagAndIndex | 256, true);
                i4++;
                i = i5;
            }
        } else {
            while (i < 55204) {
                int i6 = i + 588;
                this.trie.setRange(i, i6 - 1, this.base.getCE32(i), true);
                i = i6;
            }
        }
        setDigitTags();
        setLeadSurrogates();
        this.ce32s.setElementAt(this.trie.get(0), 0);
        this.trie.set(0, Collation.makeCE32FromTagAndIndex(11, 0));
        collationData.trie = this.trie.toTrie2_32();
        int i7 = 65536;
        char c = 55296;
        while (c < 56320) {
            if (this.unsafeBackwardSet.containsSome(i7, i7 + 1023)) {
                this.unsafeBackwardSet.add(c);
            }
            c = (char) (c + 1);
            i7 += 1024;
        }
        this.unsafeBackwardSet.freeze();
        collationData.ce32s = this.ce32s.getBuffer();
        collationData.ces = this.ce64s.getBuffer();
        collationData.contexts = this.contexts.toString();
        collationData.base = this.base;
        if (size >= 0) {
            collationData.jamoCE32s = iArr;
        } else {
            collationData.jamoCE32s = this.base.jamoCE32s;
        }
        collationData.unsafeBackwardSet = this.unsafeBackwardSet;
    }

    protected void clearContexts() {
        this.contexts.setLength(0);
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.contextChars);
        while (unicodeSetIterator.next()) {
            getConditionalCE32ForCE32(this.trie.get(unicodeSetIterator.codepoint)).builtCE32 = 1;
        }
    }

    protected void buildContexts() {
        this.contexts.setLength(0);
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.contextChars);
        while (unicodeSetIterator.next()) {
            int i = unicodeSetIterator.codepoint;
            int i2 = this.trie.get(i);
            if (!isBuilderContextCE32(i2)) {
                throw new AssertionError("Impossible: No context data for c in contextChars.");
            }
            this.trie.set(i, buildContext(getConditionalCE32ForCE32(i2)));
        }
    }

    protected int buildContext(ConditionalCE32 conditionalCE32) {
        int i;
        int i2;
        ConditionalCE32 conditionalCE322;
        CharsTrieBuilder charsTrieBuilder;
        int iMakeCE32FromTagAndIndex;
        CharsTrieBuilder charsTrieBuilder2 = new CharsTrieBuilder();
        CharsTrieBuilder charsTrieBuilder3 = new CharsTrieBuilder();
        ConditionalCE32 conditionalCE323 = conditionalCE32;
        while (true) {
            int iPrefixLength = conditionalCE323.prefixLength();
            StringBuilder sb = new StringBuilder();
            int i3 = iPrefixLength + 1;
            int i4 = 0;
            sb.append((CharSequence) conditionalCE323.context, 0, i3);
            String string = sb.toString();
            ConditionalCE32 conditionalCE324 = conditionalCE323;
            while (conditionalCE324.next >= 0) {
                ConditionalCE32 conditionalCE325 = getConditionalCE32(conditionalCE324.next);
                if (!conditionalCE325.context.startsWith(string)) {
                    break;
                }
                conditionalCE324 = conditionalCE325;
            }
            if (conditionalCE324.context.length() == i3) {
                iMakeCE32FromTagAndIndex = conditionalCE324.ce32;
            } else {
                charsTrieBuilder3.clear();
                if (conditionalCE323.context.length() == i3) {
                    i = conditionalCE323.ce32;
                    conditionalCE322 = getConditionalCE32(conditionalCE323.next);
                    i2 = 0;
                } else {
                    ConditionalCE32 conditionalCE326 = conditionalCE32;
                    int i5 = 1;
                    while (true) {
                        int iPrefixLength2 = conditionalCE326.prefixLength();
                        if (iPrefixLength2 == iPrefixLength) {
                            break;
                        }
                        if (conditionalCE326.defaultCE32 != 1) {
                            if (iPrefixLength2 != 0) {
                                charsTrieBuilder = charsTrieBuilder2;
                                if (string.regionMatches(sb.length() - iPrefixLength2, conditionalCE326.context, 1, iPrefixLength2)) {
                                }
                            } else {
                                charsTrieBuilder = charsTrieBuilder2;
                            }
                            i5 = conditionalCE326.defaultCE32;
                        } else {
                            charsTrieBuilder = charsTrieBuilder2;
                        }
                        conditionalCE326 = getConditionalCE32(conditionalCE326.next);
                        charsTrieBuilder2 = charsTrieBuilder;
                        i4 = 0;
                    }
                    i = i5;
                    i2 = 256;
                    conditionalCE322 = conditionalCE323;
                }
                int i6 = i2 | 512;
                while (true) {
                    String strSubstring = conditionalCE322.context.substring(i3);
                    if (this.nfcImpl.getFCD16(strSubstring.codePointAt(i4)) <= 255) {
                        i6 &= -513;
                    }
                    if (this.nfcImpl.getFCD16(strSubstring.codePointBefore(strSubstring.length())) > 255) {
                        i6 |= 1024;
                    }
                    charsTrieBuilder3.add(strSubstring, conditionalCE322.ce32);
                    if (conditionalCE322 == conditionalCE324) {
                        break;
                    }
                    i4 = 0;
                    conditionalCE322 = getConditionalCE32(conditionalCE322.next);
                }
                int iAddContextTrie = addContextTrie(i, charsTrieBuilder3);
                if (iAddContextTrie > 524287) {
                    throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                }
                iMakeCE32FromTagAndIndex = Collation.makeCE32FromTagAndIndex(9, iAddContextTrie) | i6;
                conditionalCE324 = conditionalCE322;
            }
            conditionalCE323.defaultCE32 = iMakeCE32FromTagAndIndex;
            if (iPrefixLength == 0) {
                if (conditionalCE324.next < 0) {
                    return iMakeCE32FromTagAndIndex;
                }
            } else {
                sb.delete(0, 1);
                sb.reverse();
                charsTrieBuilder2.add(sb, iMakeCE32FromTagAndIndex);
                if (conditionalCE324.next < 0) {
                    int iAddContextTrie2 = addContextTrie(conditionalCE32.defaultCE32, charsTrieBuilder2);
                    if (iAddContextTrie2 > 524287) {
                        throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                    }
                    return Collation.makeCE32FromTagAndIndex(8, iAddContextTrie2);
                }
            }
            conditionalCE323 = getConditionalCE32(conditionalCE324.next);
        }
    }

    protected int addContextTrie(int i, CharsTrieBuilder charsTrieBuilder) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) (i >> 16));
        sb.append((char) i);
        sb.append(charsTrieBuilder.buildCharSequence(StringTrieBuilder.Option.SMALL));
        int iIndexOf = this.contexts.indexOf(sb.toString());
        if (iIndexOf >= 0) {
            return iIndexOf;
        }
        int length = this.contexts.length();
        this.contexts.append((CharSequence) sb);
        return length;
    }

    protected void buildFastLatinTable(CollationData collationData) {
        if (this.fastLatinEnabled) {
            CollationFastLatinBuilder collationFastLatinBuilder = new CollationFastLatinBuilder();
            this.fastLatinBuilder = collationFastLatinBuilder;
            if (collationFastLatinBuilder.forData(collationData)) {
                char[] header = this.fastLatinBuilder.getHeader();
                char[] table = this.fastLatinBuilder.getTable();
                CollationData collationData2 = this.base;
                if (collationData2 != null && Arrays.equals(header, collationData2.fastLatinTableHeader) && Arrays.equals(table, this.base.fastLatinTable)) {
                    this.fastLatinBuilder = null;
                    header = this.base.fastLatinTableHeader;
                    table = this.base.fastLatinTable;
                }
                collationData.fastLatinTableHeader = header;
                collationData.fastLatinTable = table;
                return;
            }
            this.fastLatinBuilder = null;
        }
    }

    protected int getCEs(CharSequence charSequence, int i, long[] jArr, int i2) {
        if (this.collIter == null) {
            DataBuilderCollationIterator dataBuilderCollationIterator = new DataBuilderCollationIterator(this, new CollationData(this.nfcImpl));
            this.collIter = dataBuilderCollationIterator;
            if (dataBuilderCollationIterator == null) {
                return 0;
            }
        }
        return this.collIter.fetchCEs(charSequence, i, jArr, i2);
    }

    private static final class DataBuilderCollationIterator extends CollationIterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected final CollationDataBuilder builder;
        protected final CollationData builderData;
        protected final int[] jamoCE32s;
        protected int pos;
        protected CharSequence s;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        DataBuilderCollationIterator(CollationDataBuilder collationDataBuilder, CollationData collationData) {
            super(collationData, false);
            this.jamoCE32s = new int[67];
            this.builder = collationDataBuilder;
            this.builderData = collationData;
            collationData.base = collationDataBuilder.base;
            for (int i = 0; i < 67; i++) {
                this.jamoCE32s[i] = Collation.makeCE32FromTagAndIndex(7, CollationDataBuilder.jamoCpFromIndex(i)) | 256;
            }
            this.builderData.jamoCE32s = this.jamoCE32s;
        }

        int fetchCEs(CharSequence charSequence, int i, long[] jArr, int i2) {
            int ce32;
            CollationData collationData;
            this.builderData.ce32s = this.builder.ce32s.getBuffer();
            this.builderData.ces = this.builder.ce64s.getBuffer();
            this.builderData.contexts = this.builder.contexts.toString();
            reset();
            this.s = charSequence;
            this.pos = i;
            while (this.pos < this.s.length()) {
                clearCEs();
                int iCodePointAt = Character.codePointAt(this.s, this.pos);
                this.pos += Character.charCount(iCodePointAt);
                int i3 = this.builder.trie.get(iCodePointAt);
                if (i3 == 192) {
                    collationData = this.builder.base;
                    ce32 = this.builder.base.getCE32(iCodePointAt);
                } else {
                    ce32 = i3;
                    collationData = this.builderData;
                }
                appendCEsFromCE32(collationData, iCodePointAt, ce32, true);
                for (int i4 = 0; i4 < getCEsLength(); i4++) {
                    long ce = getCE(i4);
                    if (ce != 0) {
                        if (i2 < 31) {
                            jArr[i2] = ce;
                        }
                        i2++;
                    }
                }
            }
            return i2;
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        public void resetToOffset(int i) {
            reset();
            this.pos = i;
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        public int getOffset() {
            return this.pos;
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        public int nextCodePoint() {
            if (this.pos == this.s.length()) {
                return -1;
            }
            int iCodePointAt = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(iCodePointAt);
            return iCodePointAt;
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        public int previousCodePoint() {
            int i = this.pos;
            if (i == 0) {
                return -1;
            }
            int iCodePointBefore = Character.codePointBefore(this.s, i);
            this.pos -= Character.charCount(iCodePointBefore);
            return iCodePointBefore;
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        protected void forwardNumCodePoints(int i) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, i);
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        protected void backwardNumCodePoints(int i) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, -i);
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        protected int getDataCE32(int i) {
            return this.builder.trie.get(i);
        }

        @Override // com.ibm.icu.impl.coll.CollationIterator
        protected int getCE32FromBuilderData(int i) {
            if ((i & 256) != 0) {
                return this.builder.trie.get(Collation.indexFromCE32(i));
            }
            ConditionalCE32 conditionalCE32ForCE32 = this.builder.getConditionalCE32ForCE32(i);
            if (conditionalCE32ForCE32.builtCE32 == 1) {
                try {
                    conditionalCE32ForCE32.builtCE32 = this.builder.buildContext(conditionalCE32ForCE32);
                } catch (IndexOutOfBoundsException unused) {
                    this.builder.clearContexts();
                    conditionalCE32ForCE32.builtCE32 = this.builder.buildContext(conditionalCE32ForCE32);
                }
                this.builderData.contexts = this.builder.contexts.toString();
            }
            return conditionalCE32ForCE32.builtCE32;
        }
    }

    protected final boolean isMutable() {
        UnicodeSet unicodeSet;
        return (this.trie == null || (unicodeSet = this.unsafeBackwardSet) == null || unicodeSet.isFrozen()) ? false : true;
    }
}
