package com.ibm.icu.impl.coll;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.view.ViewCompat;
import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.coll.CollationDataBuilder;
import com.ibm.icu.impl.coll.CollationRuleParser;
import com.ibm.icu.text.CanonicalIterator;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UnicodeSetIterator;
import com.ibm.icu.util.ULocale;
import java.text.ParseException;

/* loaded from: classes2.dex */
public final class CollationBuilder extends CollationRuleParser.Sink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final UnicodeSet COMPOSITES;
    private static final boolean DEBUG = false;
    private static final int HAS_BEFORE2 = 64;
    private static final int HAS_BEFORE3 = 32;
    private static final int IS_TAILORED = 8;
    private static final int MAX_INDEX = 1048575;
    private CollationTailoring base;
    private CollationData baseData;
    private CollationRootElements rootElements;
    private UnicodeSet optimizeSet = new UnicodeSet();
    private long[] ces = new long[31];
    private Normalizer2 nfd = Normalizer2.getNFDInstance();
    private Normalizer2 fcd = Norm2AllModes.getFCDNormalizer2();
    private Normalizer2Impl nfcImpl = Norm2AllModes.getNFCInstance().impl;
    private long variableTop = 0;
    private CollationDataBuilder dataBuilder = new CollationDataBuilder();
    private boolean fastLatinEnabled = true;
    private int cesLength = 0;
    private UVector32 rootPrimaryIndexes = new UVector32();
    private UVector64 nodes = new UVector64();

    private static final int alignWeightRight(int i) {
        if (i != 0) {
            while ((i & 255) == 0) {
                i >>>= 8;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexFromTempCE(long j) {
        long j2 = j - 4629700417037541376L;
        return (((int) (j2 >> 24)) & 63) | (1040384 & ((int) (j2 >> 43))) | (((int) (j2 >> 42)) & 8128);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexFromTempCE32(int i) {
        int i2 = i - 1077937696;
        return ((i2 >> 8) & 63) | ((i2 >> 11) & 1040384) | ((i2 >> 10) & 8128);
    }

    private static boolean isTailoredNode(long j) {
        return (j & 8) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTempCE(long j) {
        int i = ((int) j) >>> 24;
        return 6 <= i && i <= 69;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTempCE32(int i) {
        int i2;
        return (i & 255) >= 2 && 6 <= (i2 = (i >> 8) & 255) && i2 <= 69;
    }

    private static int nextIndexFromNode(long j) {
        return (((int) j) >> 8) & MAX_INDEX;
    }

    private static long nodeFromNextIndex(int i) {
        return i << 8;
    }

    private static long nodeFromPreviousIndex(int i) {
        return i << 28;
    }

    private static long nodeFromStrength(int i) {
        return i;
    }

    private static long nodeFromWeight16(int i) {
        return i << 48;
    }

    private static long nodeFromWeight32(long j) {
        return j << 32;
    }

    private static boolean nodeHasAnyBefore(long j) {
        return (j & 96) != 0;
    }

    private static boolean nodeHasBefore2(long j) {
        return (j & 64) != 0;
    }

    private static boolean nodeHasBefore3(long j) {
        return (j & 32) != 0;
    }

    private static int previousIndexFromNode(long j) {
        return MAX_INDEX & ((int) (j >> 28));
    }

    private static int strengthFromNode(long j) {
        return ((int) j) & 3;
    }

    private static int strengthFromTempCE(long j) {
        return (((int) j) >> 8) & 3;
    }

    private static long tempCEFromIndexAndStrength(int i, int i2) {
        return ((1040384 & i) << 43) + 4629700417037541376L + ((i & 8128) << 42) + ((i & 63) << 24) + (i2 << 8);
    }

    private static int weight16FromNode(long j) {
        return 65535 & ((int) (j >> 48));
    }

    private static long weight32FromNode(long j) {
        return j >>> 32;
    }

    private static final class BundleImporter implements CollationRuleParser.Importer {
        BundleImporter() {
        }

        @Override // com.ibm.icu.impl.coll.CollationRuleParser.Importer
        public String getRules(String str, String str2) {
            return CollationLoader.loadRules(new ULocale(str), str2);
        }
    }

    public CollationBuilder(CollationTailoring collationTailoring) {
        this.base = collationTailoring;
        this.baseData = collationTailoring.data;
        this.rootElements = new CollationRootElements(collationTailoring.data.rootElements);
        this.nfcImpl.ensureCanonIterData();
        this.dataBuilder.initForTailoring(this.baseData);
    }

    public CollationTailoring parseAndBuild(String str) throws ParseException {
        if (this.baseData.rootElements == null) {
            throw new UnsupportedOperationException("missing root elements data, tailoring not supported");
        }
        CollationTailoring collationTailoring = new CollationTailoring(this.base.settings);
        CollationRuleParser collationRuleParser = new CollationRuleParser(this.baseData);
        this.variableTop = ((CollationSettings) this.base.settings.readOnly()).variableTop;
        collationRuleParser.setSink(this);
        collationRuleParser.setImporter(new BundleImporter());
        CollationSettings collationSettings = (CollationSettings) collationTailoring.settings.copyOnWrite();
        collationRuleParser.parse(str, collationSettings);
        if (this.dataBuilder.hasMappings()) {
            makeTailoredCEs();
            closeOverComposites();
            finalizeCEs();
            this.optimizeSet.add(0, 127);
            this.optimizeSet.add(192, 255);
            this.optimizeSet.remove(Normalizer2Impl.Hangul.HANGUL_BASE, Normalizer2Impl.Hangul.HANGUL_END);
            this.dataBuilder.optimize(this.optimizeSet);
            collationTailoring.ensureOwnedData();
            if (this.fastLatinEnabled) {
                this.dataBuilder.enableFastLatin();
            }
            this.dataBuilder.build(collationTailoring.ownedData);
            this.dataBuilder = null;
        } else {
            collationTailoring.data = this.baseData;
        }
        collationSettings.fastLatinOptions = CollationFastLatin.getOptions(collationTailoring.data, collationSettings, collationSettings.fastLatinPrimaries);
        collationTailoring.setRules(str);
        collationTailoring.setVersion(this.base.version, 0);
        return collationTailoring;
    }

    @Override // com.ibm.icu.impl.coll.CollationRuleParser.Sink
    void addReset(int i, CharSequence charSequence) {
        int iFindOrInsertWeakNode;
        int iWeight16FromNode;
        if (charSequence.charAt(0) == 65534) {
            this.ces[0] = getSpecialResetPosition(charSequence);
            this.cesLength = 1;
        } else {
            int cEs = this.dataBuilder.getCEs(this.nfd.normalize(charSequence), this.ces, 0);
            this.cesLength = cEs;
            if (cEs > 31) {
                throw new IllegalArgumentException("reset position maps to too many collation elements (more than 31)");
            }
        }
        if (i == 15) {
            return;
        }
        int iFindOrInsertNodeForCEs = findOrInsertNodeForCEs(i);
        long jElementAti = this.nodes.elementAti(iFindOrInsertNodeForCEs);
        while (strengthFromNode(jElementAti) > i) {
            iFindOrInsertNodeForCEs = previousIndexFromNode(jElementAti);
            jElementAti = this.nodes.elementAti(iFindOrInsertNodeForCEs);
        }
        if (strengthFromNode(jElementAti) == i && isTailoredNode(jElementAti)) {
            iFindOrInsertWeakNode = previousIndexFromNode(jElementAti);
        } else if (i == 0) {
            long jWeight32FromNode = weight32FromNode(jElementAti);
            if (jWeight32FromNode == 0) {
                throw new UnsupportedOperationException("reset primary-before ignorable not possible");
            }
            if (jWeight32FromNode <= this.rootElements.getFirstPrimary()) {
                throw new UnsupportedOperationException("reset primary-before first non-ignorable not supported");
            }
            if (jWeight32FromNode == 4278321664L) {
                throw new UnsupportedOperationException("reset primary-before [first trailing] not supported");
            }
            iFindOrInsertWeakNode = findOrInsertNodeForPrimary(this.rootElements.getPrimaryBefore(jWeight32FromNode, this.baseData.isCompressiblePrimary(jWeight32FromNode)));
            while (true) {
                int iNextIndexFromNode = nextIndexFromNode(this.nodes.elementAti(iFindOrInsertWeakNode));
                if (iNextIndexFromNode == 0) {
                    break;
                } else {
                    iFindOrInsertWeakNode = iNextIndexFromNode;
                }
            }
        } else {
            int iFindCommonNode = findCommonNode(iFindOrInsertNodeForCEs, 1);
            if (i >= 2) {
                iFindCommonNode = findCommonNode(iFindCommonNode, 2);
            }
            long jElementAti2 = this.nodes.elementAti(iFindCommonNode);
            if (strengthFromNode(jElementAti2) == i) {
                if (weight16FromNode(jElementAti2) == 0) {
                    throw new UnsupportedOperationException(i == 1 ? "reset secondary-before secondary ignorable not possible" : "reset tertiary-before completely ignorable not possible");
                }
                int weight16Before = getWeight16Before(iFindCommonNode, jElementAti2, i);
                int iPreviousIndexFromNode = previousIndexFromNode(jElementAti2);
                int iPreviousIndexFromNode2 = iPreviousIndexFromNode;
                while (true) {
                    long jElementAti3 = this.nodes.elementAti(iPreviousIndexFromNode2);
                    int iStrengthFromNode = strengthFromNode(jElementAti3);
                    if (iStrengthFromNode >= i) {
                        if (iStrengthFromNode != i || isTailoredNode(jElementAti3)) {
                            iPreviousIndexFromNode2 = previousIndexFromNode(jElementAti3);
                        } else {
                            iWeight16FromNode = weight16FromNode(jElementAti3);
                            break;
                        }
                    } else {
                        iWeight16FromNode = 1280;
                        break;
                    }
                }
                if (iWeight16FromNode != weight16Before) {
                    iPreviousIndexFromNode = insertNodeBetween(iPreviousIndexFromNode, iFindCommonNode, nodeFromWeight16(weight16Before) | nodeFromStrength(i));
                }
                iFindOrInsertWeakNode = iPreviousIndexFromNode;
            } else {
                iFindOrInsertWeakNode = findOrInsertWeakNode(iFindCommonNode, getWeight16Before(iFindCommonNode, jElementAti2, i), i);
            }
            i = ceStrength(this.ces[this.cesLength - 1]);
        }
        this.ces[this.cesLength - 1] = tempCEFromIndexAndStrength(iFindOrInsertWeakNode, i);
    }

    private int getWeight16Before(int i, long j, int i2) {
        int iWeight16FromNode = strengthFromNode(j) == 2 ? weight16FromNode(j) : 1280;
        while (strengthFromNode(j) > 1) {
            j = this.nodes.elementAti(previousIndexFromNode(j));
        }
        if (isTailoredNode(j)) {
            return 256;
        }
        int iWeight16FromNode2 = strengthFromNode(j) == 1 ? weight16FromNode(j) : 1280;
        while (strengthFromNode(j) > 0) {
            j = this.nodes.elementAti(previousIndexFromNode(j));
        }
        if (isTailoredNode(j)) {
            return 256;
        }
        long jWeight32FromNode = weight32FromNode(j);
        if (i2 == 1) {
            return this.rootElements.getSecondaryBefore(jWeight32FromNode, iWeight16FromNode2);
        }
        return this.rootElements.getTertiaryBefore(jWeight32FromNode, iWeight16FromNode2, iWeight16FromNode);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x014a A[LOOP:0: B:54:0x014a->B:63:0x0168, LOOP_START, PHI: r6 r7
      0x014a: PHI (r6v2 int) = (r6v1 int), (r6v3 int) binds: [B:37:0x00d2, B:63:0x0168] A[DONT_GENERATE, DONT_INLINE]
      0x014a: PHI (r7v2 long) = (r7v1 long), (r7v3 long) binds: [B:37:0x00d2, B:63:0x0168] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private long getSpecialResetPosition(java.lang.CharSequence r10) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationBuilder.getSpecialResetPosition(java.lang.CharSequence):long");
    }

    /* renamed from: com.ibm.icu.impl.coll.CollationBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position;

        static {
            int[] iArr = new int[CollationRuleParser.Position.values().length];
            $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position = iArr;
            try {
                iArr[CollationRuleParser.Position.FIRST_TERTIARY_IGNORABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_TERTIARY_IGNORABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.FIRST_SECONDARY_IGNORABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_SECONDARY_IGNORABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.FIRST_PRIMARY_IGNORABLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_PRIMARY_IGNORABLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.FIRST_VARIABLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_VARIABLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.FIRST_REGULAR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_REGULAR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.FIRST_IMPLICIT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_IMPLICIT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.FIRST_TRAILING.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[CollationRuleParser.Position.LAST_TRAILING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    @Override // com.ibm.icu.impl.coll.CollationRuleParser.Sink
    void addRelation(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        String strNormalize = charSequence.length() == 0 ? "" : this.nfd.normalize(charSequence);
        String strNormalize2 = this.nfd.normalize(charSequence2);
        int length = strNormalize2.length();
        if (length >= 2) {
            char cCharAt = strNormalize2.charAt(0);
            if (Normalizer2Impl.Hangul.isJamoL(cCharAt) || Normalizer2Impl.Hangul.isJamoV(cCharAt)) {
                throw new UnsupportedOperationException("contractions starting with conjoining Jamo L or V not supported");
            }
            char cCharAt2 = strNormalize2.charAt(length - 1);
            if (Normalizer2Impl.Hangul.isJamoL(cCharAt2) || (Normalizer2Impl.Hangul.isJamoV(cCharAt2) && Normalizer2Impl.Hangul.isJamoL(strNormalize2.charAt(length - 2)))) {
                throw new UnsupportedOperationException("contractions ending with conjoining Jamo L or L+V not supported");
            }
        }
        if (i != 15) {
            int iFindOrInsertNodeForCEs = findOrInsertNodeForCEs(i);
            long j = this.ces[this.cesLength - 1];
            if (i == 0 && !isTempCE(j) && (j >>> 32) == 0) {
                throw new UnsupportedOperationException("tailoring primary after ignorables not supported");
            }
            if (i == 3 && j == 0) {
                throw new UnsupportedOperationException("tailoring quaternary after tertiary ignorables not supported");
            }
            int iInsertTailoredNodeAfter = insertTailoredNodeAfter(iFindOrInsertNodeForCEs, i);
            int iCeStrength = ceStrength(j);
            if (i >= iCeStrength) {
                i = iCeStrength;
            }
            this.ces[this.cesLength - 1] = tempCEFromIndexAndStrength(iInsertTailoredNodeAfter, i);
        }
        setCaseBits(strNormalize2);
        int i2 = this.cesLength;
        if (charSequence3.length() != 0) {
            int cEs = this.dataBuilder.getCEs(this.nfd.normalize(charSequence3), this.ces, this.cesLength);
            this.cesLength = cEs;
            if (cEs > 31) {
                throw new IllegalArgumentException("extension string adds too many collation elements (more than 31 total)");
            }
        }
        addWithClosure(strNormalize, strNormalize2, this.ces, this.cesLength, ((strNormalize.contentEquals(charSequence) && strNormalize2.contentEquals(charSequence2)) || ignorePrefix(charSequence) || ignoreString(charSequence2)) ? -1 : addIfDifferent(charSequence, charSequence2, this.ces, this.cesLength, -1));
        this.cesLength = i2;
    }

    private int findOrInsertNodeForCEs(int i) {
        long j;
        while (true) {
            int i2 = this.cesLength;
            if (i2 == 0) {
                j = 0;
                this.ces[0] = 0;
                this.cesLength = 1;
                break;
            }
            j = this.ces[i2 - 1];
            if (ceStrength(j) <= i) {
                break;
            }
            this.cesLength--;
        }
        if (isTempCE(j)) {
            return indexFromTempCE(j);
        }
        if (((int) (j >>> 56)) == 254) {
            throw new UnsupportedOperationException("tailoring relative to an unassigned code point not supported");
        }
        return findOrInsertNodeForRootCE(j, i);
    }

    private int findOrInsertNodeForRootCE(long j, int i) {
        int iFindOrInsertNodeForPrimary = findOrInsertNodeForPrimary(j >>> 32);
        if (i < 1) {
            return iFindOrInsertNodeForPrimary;
        }
        int i2 = (int) j;
        int iFindOrInsertWeakNode = findOrInsertWeakNode(iFindOrInsertNodeForPrimary, i2 >>> 16, 1);
        return i >= 2 ? findOrInsertWeakNode(iFindOrInsertWeakNode, i2 & Collation.ONLY_TERTIARY_MASK, 2) : iFindOrInsertWeakNode;
    }

    private static final int binarySearchForRootPrimaryNode(int[] iArr, int i, long[] jArr, long j) {
        if (i == 0) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            int i3 = (int) ((i2 + i) / 2);
            long j2 = jArr[iArr[i3]] >>> 32;
            if (j == j2) {
                return i3;
            }
            if (j < j2) {
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

    private int findOrInsertNodeForPrimary(long j) {
        int iBinarySearchForRootPrimaryNode = binarySearchForRootPrimaryNode(this.rootPrimaryIndexes.getBuffer(), this.rootPrimaryIndexes.size(), this.nodes.getBuffer(), j);
        if (iBinarySearchForRootPrimaryNode >= 0) {
            return this.rootPrimaryIndexes.elementAti(iBinarySearchForRootPrimaryNode);
        }
        int size = this.nodes.size();
        this.nodes.addElement(nodeFromWeight32(j));
        this.rootPrimaryIndexes.insertElementAt(size, ~iBinarySearchForRootPrimaryNode);
        return size;
    }

    private int findOrInsertWeakNode(int i, int i2, int i3) {
        int iNextIndexFromNode;
        if (i2 == 1280) {
            return findCommonNode(i, i3);
        }
        long jElementAti = this.nodes.elementAti(i);
        if (i2 != 0 && i2 < 1280) {
            long j = i3 == 1 ? 64 : 32;
            if ((jElementAti & j) == 0) {
                long jNodeFromWeight16 = nodeFromWeight16(1280) | nodeFromStrength(i3);
                if (i3 == 1) {
                    jNodeFromWeight16 |= 32 & jElementAti;
                    jElementAti &= -33;
                }
                this.nodes.setElementAt(jElementAti | j, i);
                int iNextIndexFromNode2 = nextIndexFromNode(jElementAti);
                int iInsertNodeBetween = insertNodeBetween(i, iNextIndexFromNode2, nodeFromStrength(i3) | nodeFromWeight16(i2));
                insertNodeBetween(iInsertNodeBetween, iNextIndexFromNode2, jNodeFromWeight16);
                return iInsertNodeBetween;
            }
        }
        while (true) {
            iNextIndexFromNode = nextIndexFromNode(jElementAti);
            if (iNextIndexFromNode == 0) {
                break;
            }
            jElementAti = this.nodes.elementAti(iNextIndexFromNode);
            int iStrengthFromNode = strengthFromNode(jElementAti);
            if (iStrengthFromNode <= i3) {
                if (iStrengthFromNode < i3) {
                    break;
                }
                if (isTailoredNode(jElementAti)) {
                    continue;
                } else {
                    int iWeight16FromNode = weight16FromNode(jElementAti);
                    if (iWeight16FromNode == i2) {
                        return iNextIndexFromNode;
                    }
                    if (iWeight16FromNode > i2) {
                        break;
                    }
                }
            }
            i = iNextIndexFromNode;
        }
        return insertNodeBetween(i, iNextIndexFromNode, nodeFromStrength(i3) | nodeFromWeight16(i2));
    }

    private int insertTailoredNodeAfter(int i, int i2) {
        int iNextIndexFromNode;
        if (i2 >= 1) {
            i = findCommonNode(i, 1);
            if (i2 >= 2) {
                i = findCommonNode(i, 2);
            }
        }
        long jElementAti = this.nodes.elementAti(i);
        while (true) {
            iNextIndexFromNode = nextIndexFromNode(jElementAti);
            if (iNextIndexFromNode == 0) {
                break;
            }
            long jElementAti2 = this.nodes.elementAti(iNextIndexFromNode);
            if (strengthFromNode(jElementAti2) <= i2) {
                break;
            }
            i = iNextIndexFromNode;
            jElementAti = jElementAti2;
        }
        return insertNodeBetween(i, iNextIndexFromNode, 8 | nodeFromStrength(i2));
    }

    private int insertNodeBetween(int i, int i2, long j) {
        int size = this.nodes.size();
        this.nodes.addElement(j | nodeFromPreviousIndex(i) | nodeFromNextIndex(i2));
        this.nodes.setElementAt(changeNodeNextIndex(this.nodes.elementAti(i), size), i);
        if (i2 != 0) {
            this.nodes.setElementAt(changeNodePreviousIndex(this.nodes.elementAti(i2), size), i2);
        }
        return size;
    }

    private int findCommonNode(int i, int i2) {
        long jElementAti = this.nodes.elementAti(i);
        if (strengthFromNode(jElementAti) >= i2) {
            return i;
        }
        if (i2 != 1 ? !nodeHasBefore3(jElementAti) : !nodeHasBefore2(jElementAti)) {
            return i;
        }
        long jElementAti2 = this.nodes.elementAti(nextIndexFromNode(jElementAti));
        while (true) {
            int iNextIndexFromNode = nextIndexFromNode(jElementAti2);
            jElementAti2 = this.nodes.elementAti(iNextIndexFromNode);
            if (!isTailoredNode(jElementAti2) && strengthFromNode(jElementAti2) <= i2 && weight16FromNode(jElementAti2) >= 1280) {
                return iNextIndexFromNode;
            }
        }
    }

    private void setCaseBits(CharSequence charSequence) {
        int i = 0;
        for (int i2 = 0; i2 < this.cesLength; i2++) {
            if (ceStrength(this.ces[i2]) == 0) {
                i++;
            }
        }
        long j = 0;
        if (i > 0) {
            UTF16CollationIterator uTF16CollationIterator = new UTF16CollationIterator(this.baseData, false, charSequence, 0);
            int iFetchCEs = uTF16CollationIterator.fetchCEs() - 1;
            long j2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (i3 >= iFetchCEs) {
                    break;
                }
                long ce = uTF16CollationIterator.getCE(i3);
                if ((ce >>> 32) != 0) {
                    i4++;
                    int i6 = (((int) ce) >> 14) & 3;
                    if (i4 >= i) {
                        if (i4 != i) {
                            if (i6 != i5) {
                                i5 = 1;
                                break;
                            }
                        } else {
                            i5 = i6;
                        }
                    } else {
                        j2 |= i6 << ((i4 - 1) * 2);
                    }
                }
                i3++;
            }
            j = i4 >= i ? j2 | (i5 << ((i - 1) * 2)) : j2;
        }
        for (int i7 = 0; i7 < this.cesLength; i7++) {
            long j3 = (-49153) & this.ces[i7];
            int iCeStrength = ceStrength(j3);
            if (iCeStrength == 0) {
                j3 |= (3 & j) << 14;
                j >>>= 2;
            } else if (iCeStrength == 2) {
                j3 |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
            }
            this.ces[i7] = j3;
        }
    }

    @Override // com.ibm.icu.impl.coll.CollationRuleParser.Sink
    void suppressContractions(UnicodeSet unicodeSet) {
        this.dataBuilder.suppressContractions(unicodeSet);
    }

    @Override // com.ibm.icu.impl.coll.CollationRuleParser.Sink
    void optimize(UnicodeSet unicodeSet) {
        this.optimizeSet.addAll(unicodeSet);
    }

    private int addWithClosure(CharSequence charSequence, CharSequence charSequence2, long[] jArr, int i, int i2) {
        int iAddOnlyClosure = addOnlyClosure(charSequence, charSequence2, jArr, i, addIfDifferent(charSequence, charSequence2, jArr, i, i2));
        addTailComposites(charSequence, charSequence2);
        return iAddOnlyClosure;
    }

    private int addOnlyClosure(CharSequence charSequence, CharSequence charSequence2, long[] jArr, int i, int i2) {
        if (charSequence.length() == 0) {
            CanonicalIterator canonicalIterator = new CanonicalIterator(charSequence2.toString());
            int iAddIfDifferent = i2;
            while (true) {
                String next = canonicalIterator.next();
                if (next == null) {
                    return iAddIfDifferent;
                }
                if (!ignoreString(next) && !next.contentEquals(charSequence2)) {
                    iAddIfDifferent = addIfDifferent("", next, jArr, i, iAddIfDifferent);
                }
            }
        } else {
            CanonicalIterator canonicalIterator2 = new CanonicalIterator(charSequence.toString());
            CanonicalIterator canonicalIterator3 = new CanonicalIterator(charSequence2.toString());
            while (true) {
                String next2 = canonicalIterator2.next();
                if (next2 == null) {
                    return i2;
                }
                if (!ignorePrefix(next2)) {
                    boolean zContentEquals = next2.contentEquals(charSequence);
                    int iAddIfDifferent2 = i2;
                    while (true) {
                        String next3 = canonicalIterator3.next();
                        if (next3 == null) {
                            break;
                        }
                        if (!ignoreString(next3) && (!zContentEquals || !next3.contentEquals(charSequence2))) {
                            iAddIfDifferent2 = addIfDifferent(next2, next3, jArr, i, iAddIfDifferent2);
                        }
                    }
                    canonicalIterator3.reset();
                    i2 = iAddIfDifferent2;
                }
            }
        }
    }

    private void addTailComposites(CharSequence charSequence, CharSequence charSequence2) {
        int cEs;
        int iAddIfDifferent;
        int length = charSequence2.length();
        while (length != 0) {
            int iCodePointBefore = Character.codePointBefore(charSequence2, length);
            if (this.nfd.getCombiningClass(iCodePointBefore) != 0) {
                length -= Character.charCount(iCodePointBefore);
            } else {
                if (Normalizer2Impl.Hangul.isJamoL(iCodePointBefore)) {
                    return;
                }
                UnicodeSet unicodeSet = new UnicodeSet();
                if (this.nfcImpl.getCanonStartSet(iCodePointBefore, unicodeSet)) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    long[] jArr = new long[31];
                    UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
                    while (unicodeSetIterator.next()) {
                        int i = unicodeSetIterator.codepoint;
                        if (mergeCompositeIntoString(charSequence2, length, i, this.nfd.getDecomposition(i), sb, sb2) && (cEs = this.dataBuilder.getCEs(charSequence, sb, jArr, 0)) <= 31 && (iAddIfDifferent = addIfDifferent(charSequence, sb2, jArr, cEs, -1)) != -1) {
                            addOnlyClosure(charSequence, sb, jArr, cEs, iAddIfDifferent);
                        }
                    }
                    return;
                }
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
    
        if (r3 < 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:
    
        if (r4 >= r5) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0046, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0047, code lost:
    
        r12.append(r8, r9, r8.length());
        r13.append(r8, r9, r8.length());
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
    
        if (r2 >= r11.length()) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005c, code lost:
    
        r12.append(r11, r2, r11.length());
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0042 A[EDGE_INSN: B:36:0x0042->B:16:0x0042 BREAK  A[LOOP:0: B:9:0x0029->B:34:0x0090], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean mergeCompositeIntoString(java.lang.CharSequence r8, int r9, int r10, java.lang.CharSequence r11, java.lang.StringBuilder r12, java.lang.StringBuilder r13) {
        /*
            r7 = this;
            r0 = 0
            r1 = 1
            int r2 = java.lang.Character.offsetByCodePoints(r11, r0, r1)
            int r3 = r11.length()
            if (r2 != r3) goto Ld
            return r0
        Ld:
            boolean r3 = r7.equalSubSequences(r8, r9, r11, r2)
            if (r3 == 0) goto L14
            return r0
        L14:
            r12.setLength(r0)
            r12.append(r8, r0, r9)
            r13.setLength(r0)
            int r3 = r9 - r2
            r13.append(r8, r0, r3)
            r13.appendCodePoint(r10)
            r10 = -1
            r3 = -1
            r4 = 0
            r5 = 0
        L29:
            if (r3 >= 0) goto L3c
            int r6 = r8.length()
            if (r9 < r6) goto L32
            goto L42
        L32:
            int r3 = java.lang.Character.codePointAt(r8, r9)
            com.ibm.icu.text.Normalizer2 r4 = r7.nfd
            int r4 = r4.getCombiningClass(r3)
        L3c:
            int r6 = r11.length()
            if (r2 < r6) goto L64
        L42:
            if (r3 < 0) goto L56
            if (r4 >= r5) goto L47
            return r0
        L47:
            int r10 = r8.length()
            r12.append(r8, r9, r10)
            int r10 = r8.length()
            r13.append(r8, r9, r10)
            goto L63
        L56:
            int r8 = r11.length()
            if (r2 >= r8) goto L63
            int r8 = r11.length()
            r12.append(r11, r2, r8)
        L63:
            return r1
        L64:
            int r5 = java.lang.Character.codePointAt(r11, r2)
            com.ibm.icu.text.Normalizer2 r6 = r7.nfd
            int r6 = r6.getCombiningClass(r5)
            if (r6 != 0) goto L71
            return r0
        L71:
            if (r4 >= r6) goto L74
            return r0
        L74:
            if (r6 >= r4) goto L7f
            r12.appendCodePoint(r5)
            int r5 = java.lang.Character.charCount(r5)
            int r2 = r2 + r5
            goto L90
        L7f:
            if (r5 == r3) goto L82
            return r0
        L82:
            r12.appendCodePoint(r5)
            int r3 = java.lang.Character.charCount(r5)
            int r2 = r2 + r3
            int r3 = java.lang.Character.charCount(r5)
            int r9 = r9 + r3
            r3 = -1
        L90:
            r5 = r6
            goto L29
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationBuilder.mergeCompositeIntoString(java.lang.CharSequence, int, int, java.lang.CharSequence, java.lang.StringBuilder, java.lang.StringBuilder):boolean");
    }

    private boolean equalSubSequences(CharSequence charSequence, int i, CharSequence charSequence2, int i2) {
        int length = charSequence.length();
        if (length - i != charSequence2.length() - i2) {
            return false;
        }
        while (i < length) {
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (charSequence.charAt(i) != charSequence2.charAt(i2)) {
                return false;
            }
            i = i3;
            i2 = i4;
        }
        return true;
    }

    private boolean ignorePrefix(CharSequence charSequence) {
        return !isFCD(charSequence);
    }

    private boolean ignoreString(CharSequence charSequence) {
        return !isFCD(charSequence) || Normalizer2Impl.Hangul.isHangul(charSequence.charAt(0));
    }

    private boolean isFCD(CharSequence charSequence) {
        return this.fcd.isNormalized(charSequence);
    }

    static {
        UnicodeSet unicodeSet = new UnicodeSet("[:NFD_QC=N:]");
        COMPOSITES = unicodeSet;
        unicodeSet.remove(Normalizer2Impl.Hangul.HANGUL_BASE, Normalizer2Impl.Hangul.HANGUL_END);
    }

    private void closeOverComposites() {
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(COMPOSITES);
        while (unicodeSetIterator.next()) {
            int cEs = this.dataBuilder.getCEs(this.nfd.getDecomposition(unicodeSetIterator.codepoint), this.ces, 0);
            this.cesLength = cEs;
            if (cEs <= 31) {
                addIfDifferent("", unicodeSetIterator.getString(), this.ces, this.cesLength, -1);
            }
        }
    }

    private int addIfDifferent(CharSequence charSequence, CharSequence charSequence2, long[] jArr, int i, int i2) {
        long[] jArr2 = new long[31];
        if (!sameCEs(jArr, i, jArr2, this.dataBuilder.getCEs(charSequence, charSequence2, jArr2, 0))) {
            if (i2 == -1) {
                i2 = this.dataBuilder.encodeCEs(jArr, i);
            }
            this.dataBuilder.addCE32(charSequence, charSequence2, i2);
        }
        return i2;
    }

    private static boolean sameCEs(long[] jArr, int i, long[] jArr2, int i2) {
        if (i != i2) {
            return false;
        }
        for (int i3 = 0; i3 < i; i3++) {
            if (jArr[i3] != jArr2[i3]) {
                return false;
            }
        }
        return true;
    }

    private void makeTailoredCEs() {
        int i;
        int secondaryBoundary;
        int i2;
        long j;
        int tertiaryBoundary;
        CollationWeights collationWeights = new CollationWeights();
        CollationWeights collationWeights2 = new CollationWeights();
        CollationWeights collationWeights3 = new CollationWeights();
        long[] buffer = this.nodes.getBuffer();
        int i3 = 0;
        while (i3 < this.rootPrimaryIndexes.size()) {
            long j2 = buffer[this.rootPrimaryIndexes.elementAti(i3)];
            long jWeight32FromNode = weight32FromNode(j2);
            int i4 = jWeight32FromNode == 0 ? 0 : 1280;
            int iFindPrimary = jWeight32FromNode == 0 ? 0 : this.rootElements.findPrimary(jWeight32FromNode);
            int iNextIndexFromNode = nextIndexFromNode(j2);
            int iWeight16FromNode = i4;
            int iWeight16FromNode2 = iWeight16FromNode;
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            long jNextWeight = jWeight32FromNode;
            int i5 = 0;
            while (iNextIndexFromNode != 0) {
                long j3 = buffer[iNextIndexFromNode];
                int iNextIndexFromNode2 = nextIndexFromNode(j3);
                int iStrengthFromNode = strengthFromNode(j3);
                long j4 = jNextWeight;
                if (iStrengthFromNode != 3) {
                    if (iStrengthFromNode != 2) {
                        int i6 = iWeight16FromNode2;
                        i = i3;
                        if (iStrengthFromNode == 1) {
                            if (isTailoredNode(j3)) {
                                if (!z2) {
                                    int iCountTailoredNodes = countTailoredNodes(buffer, iNextIndexFromNode2, 1) + 1;
                                    int lastCommonSecondary = i6;
                                    if (lastCommonSecondary == 0) {
                                        int secondaryBoundary2 = this.rootElements.getSecondaryBoundary() - 256;
                                        secondaryBoundary = (int) (this.rootElements.getFirstSecondaryCE() >> 16);
                                        lastCommonSecondary = secondaryBoundary2;
                                    } else if (!z3) {
                                        secondaryBoundary = this.rootElements.getSecondaryAfter(iFindPrimary, lastCommonSecondary);
                                    } else {
                                        secondaryBoundary = lastCommonSecondary == 256 ? 1280 : this.rootElements.getSecondaryBoundary();
                                    }
                                    if (lastCommonSecondary == 1280) {
                                        lastCommonSecondary = this.rootElements.getLastCommonSecondary();
                                    }
                                    collationWeights2.initForSecondary();
                                    if (!collationWeights2.allocWeights(lastCommonSecondary, secondaryBoundary, iCountTailoredNodes)) {
                                        throw new UnsupportedOperationException("secondary tailoring gap too small");
                                    }
                                    z2 = true;
                                }
                                iWeight16FromNode2 = (int) collationWeights2.nextWeight();
                                jNextWeight = j4;
                            } else {
                                iWeight16FromNode2 = weight16FromNode(j3);
                                jNextWeight = j4;
                                z2 = false;
                            }
                        } else {
                            if (!z3) {
                                int iCountTailoredNodes2 = countTailoredNodes(buffer, iNextIndexFromNode2, 0) + 1;
                                boolean zIsCompressiblePrimary = this.baseData.isCompressiblePrimary(j4);
                                long primaryAfter = this.rootElements.getPrimaryAfter(j4, iFindPrimary, zIsCompressiblePrimary);
                                collationWeights.initForPrimary(zIsCompressiblePrimary);
                                if (!collationWeights.allocWeights(j4, primaryAfter, iCountTailoredNodes2)) {
                                    throw new UnsupportedOperationException("primary tailoring gap too small");
                                }
                                z3 = true;
                            }
                            jNextWeight = collationWeights.nextWeight();
                            iWeight16FromNode2 = 1280;
                            z2 = false;
                        }
                        iWeight16FromNode = iWeight16FromNode2 == 0 ? 0 : 1280;
                        z = false;
                    } else if (isTailoredNode(j3)) {
                        if (z) {
                            i2 = iWeight16FromNode2;
                            j = j4;
                            i = i3;
                        } else {
                            int iCountTailoredNodes3 = countTailoredNodes(buffer, iNextIndexFromNode2, 2) + 1;
                            if (iWeight16FromNode == 0) {
                                iWeight16FromNode = this.rootElements.getTertiaryBoundary() - 256;
                                tertiaryBoundary = ((int) this.rootElements.getFirstTertiaryCE()) & Collation.ONLY_TERTIARY_MASK;
                            } else if (!z3 && !z2) {
                                tertiaryBoundary = this.rootElements.getTertiaryAfter(iFindPrimary, iWeight16FromNode2, iWeight16FromNode);
                            } else {
                                tertiaryBoundary = iWeight16FromNode == 256 ? 1280 : this.rootElements.getTertiaryBoundary();
                            }
                            collationWeights3.initForTertiary();
                            j = j4;
                            i = i3;
                            i2 = iWeight16FromNode2;
                            if (!collationWeights3.allocWeights(iWeight16FromNode, tertiaryBoundary, iCountTailoredNodes3)) {
                                throw new UnsupportedOperationException("tertiary tailoring gap too small");
                            }
                            z = true;
                        }
                        iWeight16FromNode = (int) collationWeights3.nextWeight();
                        jNextWeight = j;
                        iWeight16FromNode2 = i2;
                    } else {
                        i = i3;
                        iWeight16FromNode = weight16FromNode(j3);
                        jNextWeight = j4;
                        z = false;
                    }
                    i5 = 0;
                } else {
                    if (i5 == 3) {
                        throw new UnsupportedOperationException("quaternary tailoring gap too small");
                    }
                    i5++;
                    jNextWeight = j4;
                    i = i3;
                }
                if (isTailoredNode(j3)) {
                    buffer[iNextIndexFromNode] = Collation.makeCE(jNextWeight, iWeight16FromNode2, iWeight16FromNode, i5);
                }
                iNextIndexFromNode = iNextIndexFromNode2;
                i3 = i;
            }
            i3++;
        }
    }

    private static int countTailoredNodes(long[] jArr, int i, int i2) {
        int i3 = 0;
        while (i != 0) {
            long j = jArr[i];
            if (strengthFromNode(j) < i2) {
                break;
            }
            if (strengthFromNode(j) == i2) {
                if (!isTailoredNode(j)) {
                    break;
                }
                i3++;
            }
            i = nextIndexFromNode(j);
        }
        return i3;
    }

    private static final class CEFinalizer implements CollationDataBuilder.CEModifier {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private long[] finalCEs;

        CEFinalizer(long[] jArr) {
            this.finalCEs = jArr;
        }

        @Override // com.ibm.icu.impl.coll.CollationDataBuilder.CEModifier
        public long modifyCE32(int i) {
            return CollationBuilder.isTempCE32(i) ? this.finalCEs[CollationBuilder.indexFromTempCE32(i)] | ((i & 192) << 8) : Collation.NO_CE;
        }

        @Override // com.ibm.icu.impl.coll.CollationDataBuilder.CEModifier
        public long modifyCE(long j) {
            if (!CollationBuilder.isTempCE(j)) {
                return Collation.NO_CE;
            }
            return (j & 49152) | this.finalCEs[CollationBuilder.indexFromTempCE(j)];
        }
    }

    private void finalizeCEs() {
        CollationDataBuilder collationDataBuilder = new CollationDataBuilder();
        collationDataBuilder.initForTailoring(this.baseData);
        collationDataBuilder.copyFrom(this.dataBuilder, new CEFinalizer(this.nodes.getBuffer()));
        this.dataBuilder = collationDataBuilder;
    }

    private static int ceStrength(long j) {
        if (isTempCE(j)) {
            return strengthFromTempCE(j);
        }
        if (((-72057594037927936L) & j) != 0) {
            return 0;
        }
        if ((((int) j) & ViewCompat.MEASURED_STATE_MASK) != 0) {
            return 1;
        }
        return j != 0 ? 2 : 15;
    }

    private static long changeNodePreviousIndex(long j, int i) {
        return (j & (-281474708275201L)) | nodeFromPreviousIndex(i);
    }

    private static long changeNodeNextIndex(long j, int i) {
        return (j & (-268435201)) | nodeFromNextIndex(i);
    }
}
