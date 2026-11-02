package com.ibm.icu.text;

import androidx.core.view.InputDeviceCompat;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.text.SearchIterator;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class StringSearch extends SearchIterator {
    private static int CE_LEVEL2_BASE = 5;
    private static int CE_LEVEL3_BASE = 327680;
    private static final int CE_MATCH = -1;
    private static final int CE_NO_MATCH = 0;
    private static final int CE_SKIP_PATN = 2;
    private static final int CE_SKIP_TARG = 1;
    private static final int INITIAL_ARRAY_SIZE_ = 256;
    private static final int PRIMARYORDERMASK = -65536;
    private static final int SECONDARYORDERMASK = 65280;
    private static final int TERTIARYORDERMASK = 255;
    int ceMask_;
    private RuleBasedCollator collator_;
    private Normalizer2 nfd_;
    private Pattern pattern_;
    private int strength_;
    private CollationElementIterator textIter_;
    private CollationPCE textProcessedIter_;
    private boolean toShift_;
    private CollationElementIterator utilIter_;
    int variableTop_;

    private static int getMask(int i) {
        if (i == 0) {
            return -65536;
        }
        if (i != 1) {
            return -1;
        }
        return InputDeviceCompat.SOURCE_ANY;
    }

    private static final boolean isOutOfBounds(int i, int i2, int i3) {
        return i3 < i || i3 > i2;
    }

    public StringSearch(String str, CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator, BreakIterator breakIterator) {
        super(characterIterator, breakIterator);
        if (ruleBasedCollator.getNumericCollation()) {
            throw new UnsupportedOperationException("Numeric collation is not supported by StringSearch");
        }
        this.collator_ = ruleBasedCollator;
        int strength = ruleBasedCollator.getStrength();
        this.strength_ = strength;
        this.ceMask_ = getMask(strength);
        this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
        this.variableTop_ = ruleBasedCollator.getVariableTop();
        this.nfd_ = Normalizer2.getNFDInstance();
        this.pattern_ = new Pattern(str);
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.utilIter_ = null;
        this.textIter_ = new CollationElementIterator(characterIterator, ruleBasedCollator);
        this.textProcessedIter_ = null;
        ULocale locale = ruleBasedCollator.getLocale(ULocale.VALID_LOCALE);
        this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance(locale == null ? ULocale.ROOT : locale);
        this.search_.internalBreakIter_.setText((CharacterIterator) characterIterator.clone());
        initialize();
    }

    public StringSearch(String str, CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator) {
        this(str, characterIterator, ruleBasedCollator, null);
    }

    public StringSearch(String str, CharacterIterator characterIterator, Locale locale) {
        this(str, characterIterator, ULocale.forLocale(locale));
    }

    public StringSearch(String str, CharacterIterator characterIterator, ULocale uLocale) {
        this(str, characterIterator, (RuleBasedCollator) Collator.getInstance(uLocale), null);
    }

    public StringSearch(String str, String str2) {
        this(str, new java.text.StringCharacterIterator(str2), (RuleBasedCollator) Collator.getInstance(), null);
    }

    public RuleBasedCollator getCollator() {
        return this.collator_;
    }

    public void setCollator(RuleBasedCollator ruleBasedCollator) {
        if (ruleBasedCollator == null) {
            throw new IllegalArgumentException("Collator can not be null");
        }
        this.collator_ = ruleBasedCollator;
        this.ceMask_ = getMask(ruleBasedCollator.getStrength());
        ULocale locale = ruleBasedCollator.getLocale(ULocale.VALID_LOCALE);
        SearchIterator.Search search = this.search_;
        if (locale == null) {
            locale = ULocale.ROOT;
        }
        search.internalBreakIter_ = BreakIterator.getCharacterInstance(locale);
        this.search_.internalBreakIter_.setText((CharacterIterator) this.search_.text().clone());
        this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
        this.variableTop_ = ruleBasedCollator.getVariableTop();
        this.textIter_ = new CollationElementIterator(this.pattern_.text_, ruleBasedCollator);
        this.utilIter_ = new CollationElementIterator(this.pattern_.text_, ruleBasedCollator);
        initialize();
    }

    public String getPattern() {
        return this.pattern_.text_;
    }

    public void setPattern(String str) {
        if (str == null || str.length() <= 0) {
            throw new IllegalArgumentException("Pattern to search for can not be null or of length 0");
        }
        this.pattern_.text_ = str;
        initialize();
    }

    public boolean isCanonical() {
        return this.search_.isCanonicalMatch_;
    }

    public void setCanonical(boolean z) {
        this.search_.isCanonicalMatch_ = z;
    }

    @Override // com.ibm.icu.text.SearchIterator
    public void setTarget(CharacterIterator characterIterator) {
        super.setTarget(characterIterator);
        this.textIter_.setText(characterIterator);
    }

    @Override // com.ibm.icu.text.SearchIterator
    public int getIndex() {
        int offset = this.textIter_.getOffset();
        if (isOutOfBounds(this.search_.beginIndex(), this.search_.endIndex(), offset)) {
            return -1;
        }
        return offset;
    }

    @Override // com.ibm.icu.text.SearchIterator
    public void setIndex(int i) {
        super.setIndex(i);
        this.textIter_.setOffset(i);
    }

    @Override // com.ibm.icu.text.SearchIterator
    public void reset() {
        int strength = this.collator_.getStrength();
        boolean z = (this.strength_ >= 3 || strength < 3) && (this.strength_ < 3 || strength >= 3);
        int strength2 = this.collator_.getStrength();
        this.strength_ = strength2;
        int mask = getMask(strength2);
        if (this.ceMask_ != mask) {
            this.ceMask_ = mask;
            z = false;
        }
        boolean zIsAlternateHandlingShifted = this.collator_.isAlternateHandlingShifted();
        if (this.toShift_ != zIsAlternateHandlingShifted) {
            this.toShift_ = zIsAlternateHandlingShifted;
            z = false;
        }
        int variableTop = this.collator_.getVariableTop();
        if (this.variableTop_ != variableTop) {
            this.variableTop_ = variableTop;
            z = false;
        }
        if (!z) {
            initialize();
        }
        this.textIter_.setText(this.search_.text());
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
    }

    @Override // com.ibm.icu.text.SearchIterator
    protected int handleNext(int i) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = this.search_.matchedIndex_ == -1 ? getIndex() : this.search_.matchedIndex_ + 1;
            this.search_.setMatchedLength(0);
            this.textIter_.setOffset(this.search_.matchedIndex_);
            if (this.search_.matchedIndex_ == this.search_.endIndex()) {
                this.search_.matchedIndex_ = -1;
            }
            return -1;
        }
        if (this.search_.matchedLength() <= 0) {
            this.search_.matchedIndex_ = i - 1;
        }
        this.textIter_.setOffset(i);
        if (this.search_.isCanonicalMatch_) {
            handleNextCanonical();
        } else {
            handleNextExact();
        }
        if (this.search_.matchedIndex_ == -1) {
            this.textIter_.setOffset(this.search_.endIndex());
        } else {
            this.textIter_.setOffset(this.search_.matchedIndex_);
        }
        return this.search_.matchedIndex_;
    }

    @Override // com.ibm.icu.text.SearchIterator
    protected int handlePrevious(int i) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = this.search_.matchedIndex_ == -1 ? getIndex() : this.search_.matchedIndex_;
            if (this.search_.matchedIndex_ == this.search_.beginIndex()) {
                setMatchNotFound();
            } else {
                SearchIterator.Search search = this.search_;
                search.matchedIndex_--;
                this.textIter_.setOffset(this.search_.matchedIndex_);
                this.search_.setMatchedLength(0);
            }
        } else {
            this.textIter_.setOffset(i);
            if (this.search_.isCanonicalMatch_) {
                handlePreviousCanonical();
            } else {
                handlePreviousExact();
            }
        }
        return this.search_.matchedIndex_;
    }

    private int getCE(int i) {
        int i2 = i & this.ceMask_;
        if (this.toShift_) {
            if (this.variableTop_ <= i2) {
                return i2;
            }
            if (this.strength_ >= 3) {
                return i2 & (-65536);
            }
            return 0;
        }
        if (this.strength_ < 3 || i2 != 0) {
            return i2;
        }
        return 65535;
    }

    private static int[] addToIntArray(int[] iArr, int i, int i2, int i3) {
        int length = iArr.length;
        if (i + 1 == length) {
            int[] iArr2 = new int[length + i3];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            iArr = iArr2;
        }
        iArr[i] = i2;
        return iArr;
    }

    private static long[] addToLongArray(long[] jArr, int i, int i2, long j, int i3) {
        if (i + 1 == i2) {
            long[] jArr2 = new long[i2 + i3];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            jArr = jArr2;
        }
        jArr[i] = j;
        return jArr;
    }

    private int initializePatternCETable() {
        int[] iArrAddToIntArray = new int[256];
        int length = this.pattern_.text_.length();
        CollationElementIterator collationElementIterator = this.utilIter_;
        if (collationElementIterator == null) {
            collationElementIterator = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = collationElementIterator;
        } else {
            collationElementIterator.setText(this.pattern_.text_);
        }
        int i = 0;
        int maxExpansion = 0;
        while (true) {
            int next = collationElementIterator.next();
            if (next != -1) {
                int ce = getCE(next);
                if (ce != 0) {
                    iArrAddToIntArray = addToIntArray(iArrAddToIntArray, i, ce, (length - collationElementIterator.getOffset()) + 1);
                    i++;
                }
                maxExpansion += collationElementIterator.getMaxExpansion(next) - 1;
            } else {
                iArrAddToIntArray[i] = 0;
                this.pattern_.CE_ = iArrAddToIntArray;
                this.pattern_.CELength_ = i;
                return maxExpansion;
            }
        }
    }

    private int initializePatternPCETable() {
        long[] jArr = new long[256];
        int length = this.pattern_.text_.length();
        CollationElementIterator collationElementIterator = this.utilIter_;
        if (collationElementIterator == null) {
            collationElementIterator = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = collationElementIterator;
        } else {
            collationElementIterator.setText(this.pattern_.text_);
        }
        CollationPCE collationPCE = new CollationPCE(collationElementIterator);
        long[] jArrAddToLongArray = jArr;
        int i = 0;
        while (true) {
            long jNextProcessed = collationPCE.nextProcessed(null);
            if (jNextProcessed != -1) {
                jArrAddToLongArray = addToLongArray(jArrAddToLongArray, i, 256, jNextProcessed, (length - collationElementIterator.getOffset()) + 1);
                i++;
            } else {
                jArrAddToLongArray[i] = 0;
                this.pattern_.PCE_ = jArrAddToLongArray;
                this.pattern_.PCELength_ = i;
                return 0;
            }
        }
    }

    private int initializePattern() {
        this.pattern_.PCE_ = null;
        return initializePatternCETable();
    }

    private void initialize() {
        initializePattern();
    }

    @Override // com.ibm.icu.text.SearchIterator
    @Deprecated
    protected void setMatchNotFound() {
        super.setMatchNotFound();
        if (this.search_.isForwardSearching_) {
            this.textIter_.setOffset(this.search_.text().getEndIndex());
        } else {
            this.textIter_.setOffset(0);
        }
    }

    private boolean checkIdentical(int i, int i2) {
        if (this.strength_ != 15) {
            return true;
        }
        String string = getString(this.targetText, i, i2 - i);
        if (Normalizer.quickCheck(string, Normalizer.NFD, 0) == Normalizer.NO) {
            string = Normalizer.decompose(string, false);
        }
        String strDecompose = this.pattern_.text_;
        if (Normalizer.quickCheck(strDecompose, Normalizer.NFD, 0) == Normalizer.NO) {
            strDecompose = Normalizer.decompose(strDecompose, false);
        }
        return string.equals(strDecompose);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initTextProcessedIter() {
        CollationPCE collationPCE = this.textProcessedIter_;
        if (collationPCE == null) {
            this.textProcessedIter_ = new CollationPCE(this.textIter_);
            return true;
        }
        collationPCE.init(this.textIter_);
        return true;
    }

    private int nextBoundaryAfter(int i) {
        BreakIterator breakIteratorBreakIter = this.search_.breakIter();
        if (breakIteratorBreakIter == null) {
            breakIteratorBreakIter = this.search_.internalBreakIter_;
        }
        return breakIteratorBreakIter != null ? breakIteratorBreakIter.following(i) : i;
    }

    private boolean isBreakBoundary(int i) {
        BreakIterator breakIteratorBreakIter = this.search_.breakIter();
        if (breakIteratorBreakIter == null) {
            breakIteratorBreakIter = this.search_.internalBreakIter_;
        }
        return breakIteratorBreakIter != null && breakIteratorBreakIter.isBoundary(i);
    }

    private static int compareCE64s(long j, long j2, SearchIterator.ElementComparisonType elementComparisonType) {
        if (j == j2) {
            return -1;
        }
        if (elementComparisonType == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
            return 0;
        }
        long j3 = j >>> 32;
        long j4 = j2 >>> 32;
        int i = (int) (j3 & Collation.MAX_PRIMARY);
        int i2 = (int) (j4 & Collation.MAX_PRIMARY);
        if (i != i2) {
            if (i == 0) {
                return 1;
            }
            return (i2 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) ? 2 : 0;
        }
        int i3 = (int) (j3 & 65535);
        int i4 = (int) (j4 & 65535);
        if (i3 == i4) {
            int i5 = (int) (j & Collation.MAX_PRIMARY);
            int i6 = (int) (j2 & Collation.MAX_PRIMARY);
            if (i5 == i6 || i6 == CE_LEVEL3_BASE) {
                return -1;
            }
            return (elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && i5 == CE_LEVEL3_BASE) ? -1 : 0;
        }
        if (i3 == 0) {
            return 1;
        }
        if (i4 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
            return 2;
        }
        if (i4 != CE_LEVEL2_BASE) {
            return (elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && i3 == CE_LEVEL2_BASE) ? -1 : 0;
        }
        return -1;
    }

    private static class Match {
        int limit_;
        int start_;

        private Match() {
            this.start_ = -1;
            this.limit_ = -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x017d A[EDGE_INSN: B:113:0x017d->B:101:0x017d BREAK  A[LOOP:0: B:12:0x0033->B:106:0x0188], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0188 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x017a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean search(int r27, com.ibm.icu.text.StringSearch.Match r28) {
        /*
            Method dump skipped, instructions count: 479
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.StringSearch.search(int, com.ibm.icu.text.StringSearch$Match):boolean");
    }

    private static int codePointAt(CharacterIterator characterIterator, int i) {
        int index = characterIterator.getIndex();
        char index2 = characterIterator.setIndex(i);
        boolean zIsHighSurrogate = Character.isHighSurrogate(index2);
        int codePoint = index2;
        if (zIsHighSurrogate) {
            char next = characterIterator.next();
            codePoint = index2;
            if (Character.isLowSurrogate(next)) {
                codePoint = Character.toCodePoint(index2, next);
            }
        }
        characterIterator.setIndex(index);
        return codePoint;
    }

    private static int codePointBefore(CharacterIterator characterIterator, int i) {
        int index = characterIterator.getIndex();
        characterIterator.setIndex(i);
        char cPrevious = characterIterator.previous();
        boolean zIsLowSurrogate = Character.isLowSurrogate(cPrevious);
        int codePoint = cPrevious;
        if (zIsLowSurrogate) {
            char cPrevious2 = characterIterator.previous();
            codePoint = cPrevious;
            if (Character.isHighSurrogate(cPrevious2)) {
                codePoint = Character.toCodePoint(cPrevious2, cPrevious);
            }
        }
        characterIterator.setIndex(index);
        return codePoint;
    }

    private boolean searchBackwards(int i, Match match) {
        int i2;
        int i3;
        boolean z;
        int i4;
        int iNextBoundaryAfter;
        int i5;
        int i6;
        boolean z2;
        if (this.pattern_.CELength_ == 0 || i < this.search_.beginIndex() || i > this.search_.endIndex()) {
            throw new IllegalArgumentException("searchBackwards(" + i + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            initializePatternPCETable();
        }
        CEBuffer cEBuffer = new CEBuffer(this);
        if (i < this.search_.endIndex()) {
            this.textIter_.setOffset(this.search_.internalBreakIter_.following(i));
            i2 = 0;
            while (cEBuffer.getPrevious(i2).lowIndex_ >= i) {
                i2++;
            }
        } else {
            this.textIter_.setOffset(i);
            i2 = 0;
        }
        CEI previous = null;
        int i7 = -1;
        int i8 = -1;
        while (true) {
            CEI previous2 = cEBuffer.getPrevious(i2);
            if (previous2 == null) {
                throw new ICUException("CEBuffer.getPrevious(" + i2 + ") returned null.");
            }
            int i9 = this.pattern_.PCELength_ - 1;
            int i10 = 0;
            while (true) {
                if (i9 < 0) {
                    i3 = i7;
                    z = true;
                    break;
                }
                long j = this.pattern_.PCE_[i9];
                previous = cEBuffer.getPrevious((((this.pattern_.PCELength_ + i2) - 1) - i9) + i10);
                i3 = i7;
                int iCompareCE64s = compareCE64s(previous.ce_, j, this.search_.elementComparisonType_);
                if (iCompareCE64s == 0) {
                    z = false;
                    break;
                }
                if (iCompareCE64s > 0) {
                    if (iCompareCE64s == 1) {
                        i9++;
                        i10++;
                    } else {
                        i10--;
                    }
                }
                i9--;
                i7 = i3;
            }
            if (z) {
                i4 = i10;
            } else {
                if (previous != null) {
                    i4 = i10;
                    if (previous.ce_ != -1) {
                    }
                    i2++;
                }
                i7 = i3;
                i2++;
            }
            if (!z) {
                i7 = i3;
                break;
            }
            CEI previous3 = cEBuffer.getPrevious(((this.pattern_.PCELength_ + i2) - 1) + i4);
            int i11 = previous3.lowIndex_;
            if (!isBreakBoundary(i11)) {
                z = false;
            }
            if (i11 == previous3.highIndex_) {
                z = false;
            }
            int i12 = previous2.lowIndex_;
            if (i2 > 0) {
                CEI previous4 = cEBuffer.getPrevious(i2 - 1);
                if (previous4.lowIndex_ == previous4.highIndex_) {
                    i5 = i12;
                    if (previous4.ce_ != -1) {
                        z = false;
                    }
                } else {
                    i5 = i12;
                }
                int i13 = previous4.lowIndex_;
                if (this.breakIterator != null || ((previous4.ce_ >>> 32) & Collation.MAX_PRIMARY) == 0 || i13 < previous2.highIndex_ || previous4.highIndex_ <= i13 || !(this.nfd_.hasBoundaryBefore(codePointAt(this.targetText, i13)) || this.nfd_.hasBoundaryAfter(codePointBefore(this.targetText, i13)))) {
                    i6 = i5;
                    z2 = false;
                } else {
                    i6 = i5;
                    z2 = true;
                }
                if (i6 >= i13 || (iNextBoundaryAfter = nextBoundaryAfter(i6)) < previous2.highIndex_ || (z2 && iNextBoundaryAfter >= i13)) {
                    iNextBoundaryAfter = i13;
                }
                if (!z2) {
                    if (iNextBoundaryAfter > i13) {
                        z = false;
                    }
                    if (!isBreakBoundary(iNextBoundaryAfter)) {
                        z = false;
                    }
                }
            } else {
                iNextBoundaryAfter = nextBoundaryAfter(i12);
                if (iNextBoundaryAfter <= 0 || i <= iNextBoundaryAfter) {
                    iNextBoundaryAfter = i;
                }
            }
            i8 = iNextBoundaryAfter;
            if (!checkIdentical(i11, i8)) {
                z = false;
            }
            i7 = i11;
            if (z) {
                break;
            }
            i2++;
        }
        if (!z) {
            i7 = -1;
            i8 = -1;
        }
        if (match != null) {
            match.start_ = i7;
            match.limit_ = i8;
        }
        return z;
    }

    private boolean handleNextExact() {
        return handleNextCommonImpl();
    }

    private boolean handleNextCanonical() {
        return handleNextCommonImpl();
    }

    private boolean handleNextCommonImpl() {
        int offset = this.textIter_.getOffset();
        Match match = new Match();
        if (search(offset, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        setMatchNotFound();
        return false;
    }

    private boolean handlePreviousExact() {
        return handlePreviousCommonImpl();
    }

    private boolean handlePreviousCanonical() {
        return handlePreviousCommonImpl();
    }

    private boolean handlePreviousCommonImpl() {
        int offset;
        if (this.search_.isOverlap_) {
            if (this.search_.matchedIndex_ != -1) {
                offset = (this.search_.matchedIndex_ + this.search_.matchedLength()) - 1;
            } else {
                initializePatternPCETable();
                if (!initTextProcessedIter()) {
                    setMatchNotFound();
                    return false;
                }
                for (int i = 0; i < this.pattern_.PCELength_ - 1 && this.textProcessedIter_.nextProcessed(null) != -1; i++) {
                }
                offset = this.textIter_.getOffset();
            }
        } else {
            offset = this.textIter_.getOffset();
        }
        Match match = new Match();
        if (searchBackwards(offset, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        setMatchNotFound();
        return false;
    }

    private static final String getString(CharacterIterator characterIterator, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2);
        int index = characterIterator.getIndex();
        characterIterator.setIndex(i);
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(characterIterator.current());
            characterIterator.next();
        }
        characterIterator.setIndex(index);
        return sb.toString();
    }

    private static final class Pattern {
        int[] CE_;
        long[] PCE_;
        String text_;
        int PCELength_ = 0;
        int CELength_ = 0;

        protected Pattern(String str) {
            this.text_ = str;
        }
    }

    private static class CollationPCE {
        private static final int BUFFER_GROW = 8;
        private static final int CONTINUATION_MARKER = 192;
        private static final int DEFAULT_BUFFER_SIZE = 16;
        private static final int PRIMARYORDERMASK = -65536;
        public static final long PROCESSED_NULLORDER = -1;
        private CollationElementIterator cei_;
        private boolean isShifted_;
        private PCEBuffer pceBuffer_ = new PCEBuffer();
        private int strength_;
        private boolean toShift_;
        private int variableTop_;

        public static final class Range {
            int ixHigh_;
            int ixLow_;
        }

        private static boolean isContinuation(int i) {
            return (i & 192) == 192;
        }

        public CollationPCE(CollationElementIterator collationElementIterator) {
            init(collationElementIterator);
        }

        public void init(CollationElementIterator collationElementIterator) {
            this.cei_ = collationElementIterator;
            init(collationElementIterator.getRuleBasedCollator());
        }

        private void init(RuleBasedCollator ruleBasedCollator) {
            this.strength_ = ruleBasedCollator.getStrength();
            this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
            this.isShifted_ = false;
            this.variableTop_ = ruleBasedCollator.getVariableTop();
        }

        private long processCE(int i) {
            long jTertiaryOrder;
            long jSecondaryOrder;
            int i2 = this.strength_;
            if (i2 != 0) {
                jTertiaryOrder = i2 != 1 ? CollationElementIterator.tertiaryOrder(i) : 0L;
                jSecondaryOrder = CollationElementIterator.secondaryOrder(i);
            } else {
                jTertiaryOrder = 0;
                jSecondaryOrder = 0;
            }
            long jPrimaryOrder = CollationElementIterator.primaryOrder(i);
            if ((!this.toShift_ || this.variableTop_ <= i || jPrimaryOrder == 0) && !(this.isShifted_ && jPrimaryOrder == 0)) {
                j = this.strength_ >= 3 ? 65535L : 0L;
                this.isShifted_ = false;
                long j = j;
                j = jPrimaryOrder;
                jPrimaryOrder = j;
            } else {
                if (jPrimaryOrder == 0) {
                    return 0L;
                }
                if (this.strength_ < 3) {
                    jPrimaryOrder = 0;
                }
                this.isShifted_ = true;
                jTertiaryOrder = 0;
                jSecondaryOrder = 0;
            }
            return (j << 48) | (jSecondaryOrder << 32) | (jTertiaryOrder << 16) | jPrimaryOrder;
        }

        public long nextProcessed(Range range) {
            int offset;
            int offset2;
            long jProcessCE;
            this.pceBuffer_.reset();
            while (true) {
                offset = this.cei_.getOffset();
                int next = this.cei_.next();
                offset2 = this.cei_.getOffset();
                if (next == -1) {
                    jProcessCE = -1;
                    break;
                }
                jProcessCE = processCE(next);
                if (jProcessCE != 0) {
                    break;
                }
            }
            if (range != null) {
                range.ixLow_ = offset;
                range.ixHigh_ = offset2;
            }
            return jProcessCE;
        }

        public long previousProcessed(Range range) {
            while (this.pceBuffer_.empty()) {
                RCEBuffer rCEBuffer = new RCEBuffer();
                boolean z = false;
                while (true) {
                    int offset = this.cei_.getOffset();
                    int iPrevious = this.cei_.previous();
                    int offset2 = this.cei_.getOffset();
                    if (iPrevious == -1) {
                        if (rCEBuffer.empty()) {
                            z = true;
                        }
                    } else {
                        rCEBuffer.put(iPrevious, offset2, offset);
                        if (((-65536) & iPrevious) != 0 && !isContinuation(iPrevious)) {
                            break;
                        }
                    }
                }
                if (z) {
                    break;
                }
                while (!rCEBuffer.empty()) {
                    RCEI rcei = rCEBuffer.get();
                    long jProcessCE = processCE(rcei.ce_);
                    if (jProcessCE != 0) {
                        this.pceBuffer_.put(jProcessCE, rcei.low_, rcei.high_);
                    }
                }
            }
            if (this.pceBuffer_.empty()) {
                if (range == null) {
                    return -1L;
                }
                range.ixLow_ = -1;
                range.ixHigh_ = -1;
                return -1L;
            }
            PCEI pcei = this.pceBuffer_.get();
            if (range != null) {
                range.ixLow_ = pcei.low_;
                range.ixHigh_ = pcei.high_;
            }
            return pcei.ce_;
        }

        private static final class PCEI {
            long ce_;
            int high_;
            int low_;

            private PCEI() {
            }
        }

        private static final class PCEBuffer {
            private int bufferIndex_;
            private PCEI[] buffer_;

            private PCEBuffer() {
                this.buffer_ = new PCEI[16];
                this.bufferIndex_ = 0;
            }

            void reset() {
                this.bufferIndex_ = 0;
            }

            boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            void put(long j, int i, int i2) {
                int i3 = this.bufferIndex_;
                PCEI[] pceiArr = this.buffer_;
                if (i3 >= pceiArr.length) {
                    PCEI[] pceiArr2 = new PCEI[pceiArr.length + 8];
                    System.arraycopy(pceiArr, 0, pceiArr2, 0, pceiArr.length);
                    this.buffer_ = pceiArr2;
                }
                this.buffer_[this.bufferIndex_] = new PCEI();
                this.buffer_[this.bufferIndex_].ce_ = j;
                this.buffer_[this.bufferIndex_].low_ = i;
                this.buffer_[this.bufferIndex_].high_ = i2;
                this.bufferIndex_++;
            }

            PCEI get() {
                int i = this.bufferIndex_;
                if (i <= 0) {
                    return null;
                }
                PCEI[] pceiArr = this.buffer_;
                int i2 = i - 1;
                this.bufferIndex_ = i2;
                return pceiArr[i2];
            }
        }

        private static final class RCEI {
            int ce_;
            int high_;
            int low_;

            private RCEI() {
            }
        }

        private static final class RCEBuffer {
            private int bufferIndex_;
            private RCEI[] buffer_;

            private RCEBuffer() {
                this.buffer_ = new RCEI[16];
                this.bufferIndex_ = 0;
            }

            boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            void put(int i, int i2, int i3) {
                int i4 = this.bufferIndex_;
                RCEI[] rceiArr = this.buffer_;
                if (i4 >= rceiArr.length) {
                    RCEI[] rceiArr2 = new RCEI[rceiArr.length + 8];
                    System.arraycopy(rceiArr, 0, rceiArr2, 0, rceiArr.length);
                    this.buffer_ = rceiArr2;
                }
                this.buffer_[this.bufferIndex_] = new RCEI();
                this.buffer_[this.bufferIndex_].ce_ = i;
                this.buffer_[this.bufferIndex_].low_ = i2;
                this.buffer_[this.bufferIndex_].high_ = i3;
                this.bufferIndex_++;
            }

            RCEI get() {
                int i = this.bufferIndex_;
                if (i <= 0) {
                    return null;
                }
                RCEI[] rceiArr = this.buffer_;
                int i2 = i - 1;
                this.bufferIndex_ = i2;
                return rceiArr[i2];
            }
        }
    }

    private static class CEI {
        long ce_;
        int highIndex_;
        int lowIndex_;

        private CEI() {
        }
    }

    private static class CEBuffer {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CEBUFFER_EXTRA = 32;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_JAMO_L = 8;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_OTHER = 3;
        int bufSize_;
        CEI[] buf_;
        int firstIx_;
        int limitIx_;
        StringSearch strSearch_;

        static boolean MIGHT_BE_JAMO_L(char c) {
            return (c >= 4352 && c <= 4446) || (c >= 12593 && c <= 12622) || (c >= 12645 && c <= 12678);
        }

        CEBuffer(StringSearch stringSearch) {
            String str;
            this.strSearch_ = stringSearch;
            this.bufSize_ = stringSearch.pattern_.PCELength_ + 32;
            if (stringSearch.search_.elementComparisonType_ != SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON && (str = stringSearch.pattern_.text_) != null) {
                for (int i = 0; i < str.length(); i++) {
                    if (MIGHT_BE_JAMO_L(str.charAt(i))) {
                        this.bufSize_ += 8;
                    } else {
                        this.bufSize_ += 3;
                    }
                }
            }
            this.firstIx_ = 0;
            this.limitIx_ = 0;
            if (stringSearch.initTextProcessedIter()) {
                this.buf_ = new CEI[this.bufSize_];
            }
        }

        CEI get(int i) {
            int i2 = i % this.bufSize_;
            if (i >= this.firstIx_ && i < this.limitIx_) {
                return this.buf_[i2];
            }
            int i3 = this.limitIx_;
            if (i != i3) {
                return null;
            }
            int i4 = i3 + 1;
            this.limitIx_ = i4;
            int i5 = this.firstIx_;
            if (i4 - i5 >= this.bufSize_) {
                this.firstIx_ = i5 + 1;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            CEI[] ceiArr = this.buf_;
            if (ceiArr[i2] == null) {
                ceiArr[i2] = new CEI();
            }
            this.buf_[i2].ce_ = this.strSearch_.textProcessedIter_.nextProcessed(range);
            this.buf_[i2].lowIndex_ = range.ixLow_;
            this.buf_[i2].highIndex_ = range.ixHigh_;
            return this.buf_[i2];
        }

        CEI getPrevious(int i) {
            int i2 = i % this.bufSize_;
            if (i >= this.firstIx_ && i < this.limitIx_) {
                return this.buf_[i2];
            }
            int i3 = this.limitIx_;
            if (i != i3) {
                return null;
            }
            int i4 = i3 + 1;
            this.limitIx_ = i4;
            int i5 = this.firstIx_;
            if (i4 - i5 >= this.bufSize_) {
                this.firstIx_ = i5 + 1;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            CEI[] ceiArr = this.buf_;
            if (ceiArr[i2] == null) {
                ceiArr[i2] = new CEI();
            }
            this.buf_[i2].ce_ = this.strSearch_.textProcessedIter_.previousProcessed(range);
            this.buf_[i2].lowIndex_ = range.ixLow_;
            this.buf_[i2].highIndex_ = range.ixHigh_;
            return this.buf_[i2];
        }
    }
}
