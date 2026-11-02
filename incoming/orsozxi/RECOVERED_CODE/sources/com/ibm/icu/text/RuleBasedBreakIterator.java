package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.RBBIDataWrapper;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import com.ibm.icu.util.CodePointTrie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class RuleBasedBreakIterator extends BreakIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String RBBI_DEBUG_ARG = "rbbi";
    private static final int RBBI_END = 2;
    private static final int RBBI_RUN = 1;
    private static final int RBBI_START = 0;
    private static final int START_STATE = 1;
    private static final int STOP_STATE = 0;
    private static final boolean TRACE;

    @Deprecated
    public static final String fDebugEnv;
    private static final List<LanguageBreakEngine> gAllBreakEngines;
    private static final UnhandledBreakEngine gUnhandledBreakEngine;
    private BreakCache fBreakCache;
    private List<LanguageBreakEngine> fBreakEngines;
    private DictionaryCache fDictionaryCache;
    private int fDictionaryCharCount;
    private boolean fDone;
    private int[] fLookAheadMatches;
    private int fPosition;

    @Deprecated
    public RBBIDataWrapper fRData;
    private int fRuleStatusIndex;
    private CharacterIterator fText;

    private RuleBasedBreakIterator() {
        this.fText = new java.text.StringCharacterIterator("");
        this.fBreakCache = new BreakCache();
        this.fDictionaryCache = new DictionaryCache();
        this.fDictionaryCharCount = 0;
        synchronized (gAllBreakEngines) {
            this.fBreakEngines = new ArrayList(gAllBreakEngines);
        }
    }

    public static RuleBasedBreakIterator getInstanceFromCompiledRules(InputStream inputStream) throws IOException {
        RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        RBBIDataWrapper rBBIDataWrapper = RBBIDataWrapper.get(ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream));
        ruleBasedBreakIterator.fRData = rBBIDataWrapper;
        ruleBasedBreakIterator.fLookAheadMatches = new int[rBBIDataWrapper.fFTable.fLookAheadResultsSize];
        return ruleBasedBreakIterator;
    }

    @Deprecated
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(ByteBuffer byteBuffer) throws IOException {
        RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        RBBIDataWrapper rBBIDataWrapper = RBBIDataWrapper.get(byteBuffer);
        ruleBasedBreakIterator.fRData = rBBIDataWrapper;
        ruleBasedBreakIterator.fLookAheadMatches = new int[rBBIDataWrapper.fFTable.fLookAheadResultsSize];
        return ruleBasedBreakIterator;
    }

    public RuleBasedBreakIterator(String str) {
        this();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            compileRules(str, byteArrayOutputStream);
            RBBIDataWrapper rBBIDataWrapper = RBBIDataWrapper.get(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
            this.fRData = rBBIDataWrapper;
            this.fLookAheadMatches = new int[rBBIDataWrapper.fFTable.fLookAheadResultsSize];
        } catch (IOException e) {
            throw new RuntimeException("RuleBasedBreakIterator rule compilation internal error: " + e.getMessage());
        }
    }

    @Override // com.ibm.icu.text.BreakIterator
    public Object clone() {
        RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator) super.clone();
        CharacterIterator characterIterator = this.fText;
        if (characterIterator != null) {
            ruleBasedBreakIterator.fText = (CharacterIterator) characterIterator.clone();
        }
        synchronized (gAllBreakEngines) {
            ruleBasedBreakIterator.fBreakEngines = new ArrayList(gAllBreakEngines);
        }
        ruleBasedBreakIterator.fLookAheadMatches = new int[this.fRData.fFTable.fLookAheadResultsSize];
        ruleBasedBreakIterator.getClass();
        ruleBasedBreakIterator.fBreakCache = ruleBasedBreakIterator.new BreakCache(this.fBreakCache);
        ruleBasedBreakIterator.getClass();
        ruleBasedBreakIterator.fDictionaryCache = ruleBasedBreakIterator.new DictionaryCache(this.fDictionaryCache);
        return ruleBasedBreakIterator;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        try {
            RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator) obj;
            if (this.fRData != ruleBasedBreakIterator.fRData && (this.fRData == null || ruleBasedBreakIterator.fRData == null)) {
                return false;
            }
            if (this.fRData != null && ruleBasedBreakIterator.fRData != null && !this.fRData.fRuleSource.equals(ruleBasedBreakIterator.fRData.fRuleSource)) {
                return false;
            }
            if (this.fText == null && ruleBasedBreakIterator.fText == null) {
                return true;
            }
            if (this.fText != null && ruleBasedBreakIterator.fText != null && this.fText.equals(ruleBasedBreakIterator.fText)) {
                return this.fPosition == ruleBasedBreakIterator.fPosition;
            }
            return false;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public String toString() {
        RBBIDataWrapper rBBIDataWrapper = this.fRData;
        return rBBIDataWrapper != null ? rBBIDataWrapper.fRuleSource : "";
    }

    public int hashCode() {
        return this.fRData.fRuleSource.hashCode();
    }

    static {
        TRACE = ICUDebug.enabled(RBBI_DEBUG_ARG) && ICUDebug.value(RBBI_DEBUG_ARG).indexOf("trace") >= 0;
        gUnhandledBreakEngine = new UnhandledBreakEngine();
        ArrayList arrayList = new ArrayList();
        gAllBreakEngines = arrayList;
        arrayList.add(gUnhandledBreakEngine);
        fDebugEnv = ICUDebug.enabled(RBBI_DEBUG_ARG) ? ICUDebug.value(RBBI_DEBUG_ARG) : null;
    }

    @Deprecated
    public void dump(PrintStream printStream) {
        if (printStream == null) {
            printStream = System.out;
        }
        this.fRData.dump(printStream);
    }

    public static void compileRules(String str, OutputStream outputStream) throws IOException {
        RBBIRuleBuilder.compileRules(str, outputStream);
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int first() {
        CharacterIterator characterIterator = this.fText;
        if (characterIterator == null) {
            return -1;
        }
        characterIterator.first();
        int index = this.fText.getIndex();
        if (!this.fBreakCache.seek(index)) {
            this.fBreakCache.populateNear(index);
        }
        this.fBreakCache.current();
        return this.fPosition;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int last() {
        CharacterIterator characterIterator = this.fText;
        if (characterIterator == null) {
            return -1;
        }
        int endIndex = characterIterator.getEndIndex();
        isBoundary(endIndex);
        int i = this.fPosition;
        return endIndex;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int next(int i) {
        int iPrevious = 0;
        if (i > 0) {
            while (i > 0 && iPrevious != -1) {
                iPrevious = next();
                i--;
            }
            return iPrevious;
        }
        if (i >= 0) {
            return current();
        }
        while (i < 0 && iPrevious != -1) {
            iPrevious = previous();
            i++;
        }
        return iPrevious;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int next() {
        this.fBreakCache.next();
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int previous() {
        this.fBreakCache.previous();
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int following(int i) {
        if (i < this.fText.getBeginIndex()) {
            return first();
        }
        this.fBreakCache.following(CISetIndex32(this.fText, i));
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int preceding(int i) {
        CharacterIterator characterIterator = this.fText;
        if (characterIterator == null || i > characterIterator.getEndIndex()) {
            return last();
        }
        if (i < this.fText.getBeginIndex()) {
            return first();
        }
        this.fBreakCache.preceding(i);
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    protected static final void checkOffset(int i, CharacterIterator characterIterator) {
        if (i < characterIterator.getBeginIndex() || i > characterIterator.getEndIndex()) {
            throw new IllegalArgumentException("offset out of bounds");
        }
    }

    @Override // com.ibm.icu.text.BreakIterator
    public boolean isBoundary(int i) {
        checkOffset(i, this.fText);
        int iCISetIndex32 = CISetIndex32(this.fText, i);
        boolean z = false;
        if ((this.fBreakCache.seek(iCISetIndex32) || this.fBreakCache.populateNear(iCISetIndex32)) && this.fBreakCache.current() == i) {
            z = true;
        }
        if (!z) {
            next();
        }
        return z;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int current() {
        if (this.fText != null) {
            return this.fPosition;
        }
        return -1;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int getRuleStatus() {
        return this.fRData.fStatusTable[this.fRuleStatusIndex + this.fRData.fStatusTable[this.fRuleStatusIndex]];
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int getRuleStatusVec(int[] iArr) {
        int i = this.fRData.fStatusTable[this.fRuleStatusIndex];
        if (iArr != null) {
            int iMin = Math.min(i, iArr.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                iArr[i2] = this.fRData.fStatusTable[this.fRuleStatusIndex + i2 + 1];
            }
        }
        return i;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public CharacterIterator getText() {
        return this.fText;
    }

    @Override // com.ibm.icu.text.BreakIterator
    public void setText(CharacterIterator characterIterator) {
        if (characterIterator != null) {
            this.fBreakCache.reset(characterIterator.getBeginIndex(), 0);
        } else {
            this.fBreakCache.reset();
        }
        this.fDictionaryCache.reset();
        this.fText = characterIterator;
        first();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LanguageBreakEngine getLanguageBreakEngine(int i) {
        LanguageBreakEngine cjkBreakEngine;
        for (LanguageBreakEngine languageBreakEngine : this.fBreakEngines) {
            if (languageBreakEngine.handles(i)) {
                return languageBreakEngine;
            }
        }
        synchronized (gAllBreakEngines) {
            for (LanguageBreakEngine languageBreakEngine2 : gAllBreakEngines) {
                if (languageBreakEngine2.handles(i)) {
                    this.fBreakEngines.add(languageBreakEngine2);
                    return languageBreakEngine2;
                }
            }
            int intPropertyValue = UCharacter.getIntPropertyValue(i, 4106);
            if (intPropertyValue == 22 || intPropertyValue == 20) {
                intPropertyValue = 17;
            }
            try {
                if (intPropertyValue == 17) {
                    cjkBreakEngine = new CjkBreakEngine(false);
                } else if (intPropertyValue == 18) {
                    cjkBreakEngine = new CjkBreakEngine(true);
                } else if (intPropertyValue == 23) {
                    cjkBreakEngine = new KhmerBreakEngine();
                } else if (intPropertyValue == 24) {
                    cjkBreakEngine = new LaoBreakEngine();
                } else if (intPropertyValue == 28) {
                    cjkBreakEngine = new BurmeseBreakEngine();
                } else if (intPropertyValue == 38) {
                    cjkBreakEngine = new ThaiBreakEngine();
                } else {
                    gUnhandledBreakEngine.handleChar(i);
                    cjkBreakEngine = gUnhandledBreakEngine;
                }
            } catch (IOException unused) {
                cjkBreakEngine = null;
            }
            if (cjkBreakEngine != null && cjkBreakEngine != gUnhandledBreakEngine) {
                gAllBreakEngines.add(cjkBreakEngine);
                this.fBreakEngines.add(cjkBreakEngine);
            }
            return cjkBreakEngine;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleNext() {
        int i;
        int index;
        char c;
        int i2;
        short s;
        CodePointTrie codePointTrie;
        int i3;
        if (TRACE) {
            System.out.println("Handle Next   pos      char  state category");
        }
        this.fRuleStatusIndex = 0;
        this.fDictionaryCharCount = 0;
        CharacterIterator characterIterator = this.fText;
        CodePointTrie codePointTrie2 = this.fRData.fTrie;
        char[] cArr = this.fRData.fFTable.fTable;
        int i4 = this.fPosition;
        characterIterator.setIndex(i4);
        int iCurrent = characterIterator.current();
        int i5 = 1;
        if (iCurrent >= 55296 && (iCurrent = CharacterIteration.nextTrail32(characterIterator, iCurrent)) == Integer.MAX_VALUE) {
            this.fDone = true;
            return -1;
        }
        int rowIndex = this.fRData.getRowIndex(1);
        int i6 = this.fRData.fFTable.fFlags;
        int i7 = this.fRData.fFTable.fDictCategoriesStart;
        if ((i6 & 2) != 0) {
            if (TRACE) {
                System.out.print("            " + RBBIDataWrapper.intToString(characterIterator.getIndex(), 5));
                System.out.print(RBBIDataWrapper.intToHexString(iCurrent, 10));
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(RBBIDataWrapper.intToString(1, 7));
                i = 2;
                sb.append(RBBIDataWrapper.intToString(2, 6));
                printStream.println(sb.toString());
            } else {
                i = 2;
            }
            index = i4;
            c = 1;
            i2 = 0;
            s = 2;
        } else {
            i = 2;
            index = i4;
            c = 1;
            i2 = 1;
            s = 3;
        }
        while (c != 0) {
            if (iCurrent == Integer.MAX_VALUE) {
                if (i2 == i) {
                    break;
                }
                codePointTrie = codePointTrie2;
                i2 = 2;
                s = 1;
            } else if (i2 == i5) {
                short s2 = (short) codePointTrie2.get(iCurrent);
                if (s2 >= i7) {
                    this.fDictionaryCharCount += i5;
                }
                if (TRACE) {
                    PrintStream printStream2 = System.out;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("            ");
                    codePointTrie = codePointTrie2;
                    sb2.append(RBBIDataWrapper.intToString(characterIterator.getIndex(), 5));
                    printStream2.print(sb2.toString());
                    System.out.print(RBBIDataWrapper.intToHexString(iCurrent, 10));
                    System.out.println(RBBIDataWrapper.intToString(c, 7) + RBBIDataWrapper.intToString(s2, 6));
                } else {
                    codePointTrie = codePointTrie2;
                }
                int next = characterIterator.next();
                if (next >= 55296) {
                    next = CharacterIteration.nextTrail32(characterIterator, next);
                }
                iCurrent = next;
                s = s2;
            } else {
                codePointTrie = codePointTrie2;
                i2 = 1;
            }
            char c2 = cArr[rowIndex + 3 + s];
            int rowIndex2 = this.fRData.getRowIndex(c2);
            char c3 = cArr[rowIndex2 + 0];
            if (c3 == 1) {
                int index2 = characterIterator.getIndex();
                if (iCurrent >= 65536 && iCurrent <= 1114111) {
                    index2--;
                }
                index = index2;
                this.fRuleStatusIndex = cArr[rowIndex2 + 2];
            } else if (c3 > 1 && (i3 = this.fLookAheadMatches[c3]) >= 0) {
                this.fRuleStatusIndex = cArr[rowIndex2 + 2];
                this.fPosition = i3;
                return i3;
            }
            char c4 = cArr[rowIndex2 + 1];
            if (c4 != 0) {
                int index3 = characterIterator.getIndex();
                if (iCurrent >= 65536 && iCurrent <= 1114111) {
                    index3--;
                }
                this.fLookAheadMatches[c4] = index3;
            }
            c = c2;
            rowIndex = rowIndex2;
            codePointTrie2 = codePointTrie;
            i5 = 1;
            i = 2;
        }
        if (index == i4) {
            if (TRACE) {
                System.out.println("Iterator did not move. Advancing by 1.");
            }
            characterIterator.setIndex(i4);
            CharacterIteration.next32(characterIterator);
            index = characterIterator.getIndex();
            this.fRuleStatusIndex = 0;
        }
        this.fPosition = index;
        if (TRACE) {
            System.out.println("result = " + index);
        }
        return index;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleSafePrevious(int i) {
        CharacterIterator characterIterator = this.fText;
        CodePointTrie codePointTrie = this.fRData.fTrie;
        char[] cArr = this.fRData.fRTable.fTable;
        CISetIndex32(characterIterator, i);
        if (TRACE) {
            System.out.print("Handle Previous   pos   char  state category");
        }
        if (characterIterator.getIndex() == characterIterator.getBeginIndex()) {
            return -1;
        }
        char c = 1;
        int rowIndex = this.fRData.getRowIndex(1);
        for (int iPrevious32 = CharacterIteration.previous32(characterIterator); iPrevious32 != Integer.MAX_VALUE; iPrevious32 = CharacterIteration.previous32(characterIterator)) {
            short s = (short) codePointTrie.get(iPrevious32);
            if (TRACE) {
                System.out.print("            " + RBBIDataWrapper.intToString(characterIterator.getIndex(), 5));
                System.out.print(RBBIDataWrapper.intToHexString(iPrevious32, 10));
                System.out.println(RBBIDataWrapper.intToString(c, 7) + RBBIDataWrapper.intToString(s, 6));
            }
            c = cArr[rowIndex + 3 + s];
            rowIndex = this.fRData.getRowIndex(c);
            if (c == 0) {
                break;
            }
        }
        int index = characterIterator.getIndex();
        if (TRACE) {
            System.out.println("result = " + index);
        }
        return index;
    }

    private static int CISetIndex32(CharacterIterator characterIterator, int i) {
        if (i <= characterIterator.getBeginIndex()) {
            characterIterator.first();
        } else if (i >= characterIterator.getEndIndex()) {
            characterIterator.setIndex(characterIterator.getEndIndex());
        } else if (Character.isLowSurrogate(characterIterator.setIndex(i)) && !Character.isHighSurrogate(characterIterator.previous())) {
            characterIterator.next();
        }
        return characterIterator.getIndex();
    }

    class DictionaryCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int fBoundary;
        DictionaryBreakEngine.DequeI fBreaks;
        int fFirstRuleStatusIndex;
        int fLimit;
        int fOtherRuleStatusIndex;
        int fPositionInCache;
        int fStart;
        int fStatusIndex;

        void reset() {
            this.fPositionInCache = -1;
            this.fStart = 0;
            this.fLimit = 0;
            this.fFirstRuleStatusIndex = 0;
            this.fOtherRuleStatusIndex = 0;
            this.fBreaks.removeAllElements();
        }

        boolean following(int i) {
            if (i >= this.fLimit || i < this.fStart) {
                this.fPositionInCache = -1;
                return false;
            }
            int i2 = this.fPositionInCache;
            if (i2 >= 0 && i2 < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == i) {
                int i3 = this.fPositionInCache + 1;
                this.fPositionInCache = i3;
                if (i3 >= this.fBreaks.size()) {
                    this.fPositionInCache = -1;
                    return false;
                }
                this.fBoundary = this.fBreaks.elementAt(this.fPositionInCache);
                this.fStatusIndex = this.fOtherRuleStatusIndex;
                return true;
            }
            this.fPositionInCache = 0;
            while (this.fPositionInCache < this.fBreaks.size()) {
                int iElementAt = this.fBreaks.elementAt(this.fPositionInCache);
                if (iElementAt <= i) {
                    this.fPositionInCache++;
                } else {
                    this.fBoundary = iElementAt;
                    this.fStatusIndex = this.fOtherRuleStatusIndex;
                    return true;
                }
            }
            this.fPositionInCache = -1;
            return false;
        }

        boolean preceding(int i) {
            int i2;
            if (i <= this.fStart || i > (i2 = this.fLimit)) {
                this.fPositionInCache = -1;
                return false;
            }
            if (i == i2) {
                this.fPositionInCache = this.fBreaks.size() - 1;
            }
            int i3 = this.fPositionInCache;
            if (i3 > 0 && i3 < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == i) {
                int i4 = this.fPositionInCache - 1;
                this.fPositionInCache = i4;
                int iElementAt = this.fBreaks.elementAt(i4);
                this.fBoundary = iElementAt;
                this.fStatusIndex = iElementAt == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                return true;
            }
            if (this.fPositionInCache == 0) {
                this.fPositionInCache = -1;
                return false;
            }
            int size = this.fBreaks.size();
            while (true) {
                this.fPositionInCache = size - 1;
                int i5 = this.fPositionInCache;
                if (i5 >= 0) {
                    int iElementAt2 = this.fBreaks.elementAt(i5);
                    if (iElementAt2 >= i) {
                        size = this.fPositionInCache;
                    } else {
                        this.fBoundary = iElementAt2;
                        this.fStatusIndex = iElementAt2 == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                        return true;
                    }
                } else {
                    this.fPositionInCache = -1;
                    return false;
                }
            }
        }

        void populateDictionary(int i, int i2, int i3, int i4) {
            int i5;
            if (i2 - i <= 1) {
                return;
            }
            reset();
            this.fFirstRuleStatusIndex = i3;
            this.fOtherRuleStatusIndex = i4;
            RuleBasedBreakIterator.this.fText.setIndex(i);
            int iCurrent32 = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
            short s = (short) RuleBasedBreakIterator.this.fRData.fTrie.get(iCurrent32);
            int i6 = RuleBasedBreakIterator.this.fRData.fFTable.fDictCategoriesStart;
            int iFindBreaks = 0;
            while (true) {
                int index = RuleBasedBreakIterator.this.fText.getIndex();
                if (index < i2 && s < i6) {
                    iCurrent32 = CharacterIteration.next32(RuleBasedBreakIterator.this.fText);
                    i5 = RuleBasedBreakIterator.this.fRData.fTrie.get(iCurrent32);
                } else {
                    if (index >= i2) {
                        break;
                    }
                    LanguageBreakEngine languageBreakEngine = RuleBasedBreakIterator.this.getLanguageBreakEngine(iCurrent32);
                    if (languageBreakEngine != null) {
                        iFindBreaks += languageBreakEngine.findBreaks(RuleBasedBreakIterator.this.fText, i, i2, this.fBreaks);
                    }
                    iCurrent32 = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
                    i5 = RuleBasedBreakIterator.this.fRData.fTrie.get(iCurrent32);
                }
                s = (short) i5;
            }
            if (iFindBreaks > 0) {
                if (i < this.fBreaks.elementAt(0)) {
                    this.fBreaks.offer(i);
                }
                if (i2 > this.fBreaks.peek()) {
                    this.fBreaks.push(i2);
                }
                this.fPositionInCache = 0;
                this.fStart = this.fBreaks.elementAt(0);
                this.fLimit = this.fBreaks.peek();
            }
        }

        DictionaryCache() {
            this.fPositionInCache = -1;
            this.fBreaks = new DictionaryBreakEngine.DequeI();
        }

        DictionaryCache(DictionaryCache dictionaryCache) {
            try {
                this.fBreaks = (DictionaryBreakEngine.DequeI) dictionaryCache.fBreaks.clone();
                this.fPositionInCache = dictionaryCache.fPositionInCache;
                this.fStart = dictionaryCache.fStart;
                this.fLimit = dictionaryCache.fLimit;
                this.fFirstRuleStatusIndex = dictionaryCache.fFirstRuleStatusIndex;
                this.fOtherRuleStatusIndex = dictionaryCache.fOtherRuleStatusIndex;
                this.fBoundary = dictionaryCache.fBoundary;
                this.fStatusIndex = dictionaryCache.fStatusIndex;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class BreakCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CACHE_SIZE = 128;
        static final boolean RetainCachePosition = false;
        static final boolean UpdateCachePosition = true;
        int[] fBoundaries;
        int fBufIdx;
        int fEndBufIdx;
        DictionaryBreakEngine.DequeI fSideBuffer;
        int fStartBufIdx;
        short[] fStatuses;
        int fTextIdx;

        private final int modChunkSize(int i) {
            return i & 127;
        }

        BreakCache() {
            this.fBoundaries = new int[128];
            this.fStatuses = new short[128];
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
            reset();
        }

        void reset(int i, int i2) {
            this.fStartBufIdx = 0;
            this.fEndBufIdx = 0;
            this.fTextIdx = i;
            this.fBufIdx = 0;
            this.fBoundaries[0] = i;
            this.fStatuses[0] = (short) i2;
        }

        void reset() {
            reset(0, 0);
        }

        void next() {
            int i = this.fBufIdx;
            if (i == this.fEndBufIdx) {
                RuleBasedBreakIterator.this.fDone = !populateFollowing();
                RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
                RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
                return;
            }
            int iModChunkSize = modChunkSize(i + 1);
            this.fBufIdx = iModChunkSize;
            this.fTextIdx = RuleBasedBreakIterator.this.fPosition = this.fBoundaries[iModChunkSize];
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
        }

        void previous() {
            int i = this.fBufIdx;
            if (i == this.fStartBufIdx) {
                populatePreceding();
            } else {
                int iModChunkSize = modChunkSize(i - 1);
                this.fBufIdx = iModChunkSize;
                this.fTextIdx = this.fBoundaries[iModChunkSize];
            }
            RuleBasedBreakIterator.this.fDone = this.fBufIdx == i;
            RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
        }

        void following(int i) {
            if (i == this.fTextIdx || seek(i) || populateNear(i)) {
                RuleBasedBreakIterator.this.fDone = false;
                next();
            }
        }

        void preceding(int i) {
            if (i == this.fTextIdx || seek(i) || populateNear(i)) {
                if (i == this.fTextIdx) {
                    previous();
                } else {
                    current();
                }
            }
        }

        int current() {
            RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            RuleBasedBreakIterator.this.fDone = false;
            return this.fTextIdx;
        }

        boolean populateNear(int i) {
            int i2;
            int[] iArr;
            int i3;
            int i4;
            int[] iArr2 = this.fBoundaries;
            if (i < iArr2[this.fStartBufIdx] - 15 || i > iArr2[this.fEndBufIdx] + 15) {
                int beginIndex = RuleBasedBreakIterator.this.fText.getBeginIndex();
                if (i > beginIndex + 20) {
                    int iHandleSafePrevious = RuleBasedBreakIterator.this.handleSafePrevious(i);
                    if (iHandleSafePrevious > beginIndex) {
                        RuleBasedBreakIterator.this.fPosition = iHandleSafePrevious;
                        beginIndex = RuleBasedBreakIterator.this.handleNext();
                        if (beginIndex == iHandleSafePrevious + 1 || (beginIndex == iHandleSafePrevious + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(iHandleSafePrevious)) && Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next()))) {
                            beginIndex = RuleBasedBreakIterator.this.handleNext();
                        }
                    }
                    i2 = RuleBasedBreakIterator.this.fRuleStatusIndex;
                } else {
                    i2 = 0;
                }
                reset(beginIndex, i2);
            }
            int[] iArr3 = this.fBoundaries;
            if (iArr3[this.fEndBufIdx] < i) {
                do {
                    int[] iArr4 = this.fBoundaries;
                    int i5 = this.fEndBufIdx;
                    if (iArr4[i5] >= i) {
                        this.fBufIdx = i5;
                        this.fTextIdx = iArr4[i5];
                        while (this.fTextIdx > i) {
                            previous();
                        }
                        return true;
                    }
                } while (populateFollowing());
                return false;
            }
            if (iArr3[this.fStartBufIdx] > i) {
                while (true) {
                    iArr = this.fBoundaries;
                    i3 = this.fStartBufIdx;
                    if (iArr[i3] <= i) {
                        break;
                    }
                    populatePreceding();
                }
                this.fBufIdx = i3;
                this.fTextIdx = iArr[i3];
                while (true) {
                    i4 = this.fTextIdx;
                    if (i4 >= i) {
                        break;
                    }
                    next();
                }
                if (i4 > i) {
                    previous();
                }
            }
            return true;
        }

        boolean populateFollowing() {
            int iHandleNext;
            int[] iArr = this.fBoundaries;
            int i = this.fEndBufIdx;
            int i2 = iArr[i];
            short s = this.fStatuses[i];
            if (RuleBasedBreakIterator.this.fDictionaryCache.following(i2)) {
                addFollowing(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                return true;
            }
            RuleBasedBreakIterator.this.fPosition = i2;
            int iHandleNext2 = RuleBasedBreakIterator.this.handleNext();
            if (iHandleNext2 == -1) {
                return false;
            }
            int i3 = RuleBasedBreakIterator.this.fRuleStatusIndex;
            if (RuleBasedBreakIterator.this.fDictionaryCharCount > 0) {
                RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(i2, iHandleNext2, s, i3);
                if (RuleBasedBreakIterator.this.fDictionaryCache.following(i2)) {
                    addFollowing(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                    return true;
                }
            }
            addFollowing(iHandleNext2, i3, true);
            for (int i4 = 0; i4 < 6 && (iHandleNext = RuleBasedBreakIterator.this.handleNext()) != -1 && RuleBasedBreakIterator.this.fDictionaryCharCount <= 0; i4++) {
                addFollowing(iHandleNext, RuleBasedBreakIterator.this.fRuleStatusIndex, false);
            }
            return true;
        }

        boolean populatePreceding() {
            int iHandleNext;
            int i;
            int i2;
            boolean z;
            int beginIndex = RuleBasedBreakIterator.this.fText.getBeginIndex();
            int i3 = this.fBoundaries[this.fStartBufIdx];
            if (i3 == beginIndex) {
                return false;
            }
            boolean z2 = true;
            if (RuleBasedBreakIterator.this.fDictionaryCache.preceding(i3)) {
                addPreceding(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                return true;
            }
            int iHandleSafePrevious = i3;
            do {
                int i4 = iHandleSafePrevious - 30;
                iHandleSafePrevious = i4 <= beginIndex ? beginIndex : RuleBasedBreakIterator.this.handleSafePrevious(i4);
                if (iHandleSafePrevious == -1 || iHandleSafePrevious == beginIndex) {
                    iHandleNext = beginIndex;
                    i = 0;
                } else {
                    RuleBasedBreakIterator.this.fPosition = iHandleSafePrevious;
                    iHandleNext = RuleBasedBreakIterator.this.handleNext();
                    if (iHandleNext == iHandleSafePrevious + 1 || (iHandleNext == iHandleSafePrevious + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(iHandleSafePrevious)) && Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next()))) {
                        iHandleNext = RuleBasedBreakIterator.this.handleNext();
                    }
                    i = RuleBasedBreakIterator.this.fRuleStatusIndex;
                }
            } while (iHandleNext >= i3);
            this.fSideBuffer.removeAllElements();
            this.fSideBuffer.push(iHandleNext);
            this.fSideBuffer.push(i);
            while (true) {
                int i5 = RuleBasedBreakIterator.this.fPosition = iHandleNext;
                int iHandleNext2 = RuleBasedBreakIterator.this.handleNext();
                int i6 = RuleBasedBreakIterator.this.fRuleStatusIndex;
                if (iHandleNext2 == -1) {
                    break;
                }
                if (RuleBasedBreakIterator.this.fDictionaryCharCount != 0) {
                    RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(i5, iHandleNext2, i, i6);
                    z = false;
                    while (true) {
                        if (!RuleBasedBreakIterator.this.fDictionaryCache.following(i5)) {
                            break;
                        }
                        iHandleNext2 = RuleBasedBreakIterator.this.fDictionaryCache.fBoundary;
                        i6 = RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex;
                        if (iHandleNext2 >= i3) {
                            z = true;
                            break;
                        }
                        this.fSideBuffer.push(iHandleNext2);
                        this.fSideBuffer.push(i6);
                        i5 = iHandleNext2;
                        z = true;
                    }
                    i2 = i6;
                } else {
                    i2 = i6;
                    z = false;
                }
                iHandleNext = iHandleNext2;
                if (!z && iHandleNext < i3) {
                    this.fSideBuffer.push(iHandleNext);
                    this.fSideBuffer.push(i2);
                }
                if (iHandleNext >= i3) {
                    break;
                }
                i = i2;
            }
            if (this.fSideBuffer.isEmpty()) {
                z2 = false;
            } else {
                addPreceding(this.fSideBuffer.pop(), this.fSideBuffer.pop(), true);
            }
            while (!this.fSideBuffer.isEmpty()) {
                if (!addPreceding(this.fSideBuffer.pop(), this.fSideBuffer.pop(), false)) {
                    break;
                }
            }
            return z2;
        }

        void addFollowing(int i, int i2, boolean z) {
            int iModChunkSize = modChunkSize(this.fEndBufIdx + 1);
            int i3 = this.fStartBufIdx;
            if (iModChunkSize == i3) {
                this.fStartBufIdx = modChunkSize(i3 + 6);
            }
            this.fBoundaries[iModChunkSize] = i;
            this.fStatuses[iModChunkSize] = (short) i2;
            this.fEndBufIdx = iModChunkSize;
            if (z) {
                this.fBufIdx = iModChunkSize;
                this.fTextIdx = i;
            }
        }

        boolean addPreceding(int i, int i2, boolean z) {
            int iModChunkSize = modChunkSize(this.fStartBufIdx - 1);
            int i3 = this.fEndBufIdx;
            if (iModChunkSize == i3) {
                if (this.fBufIdx == i3 && !z) {
                    return false;
                }
                this.fEndBufIdx = modChunkSize(this.fEndBufIdx - 1);
            }
            this.fBoundaries[iModChunkSize] = i;
            this.fStatuses[iModChunkSize] = (short) i2;
            this.fStartBufIdx = iModChunkSize;
            if (z) {
                this.fBufIdx = iModChunkSize;
                this.fTextIdx = i;
            }
            return true;
        }

        boolean seek(int i) {
            int[] iArr = this.fBoundaries;
            int iModChunkSize = this.fStartBufIdx;
            if (i >= iArr[iModChunkSize]) {
                int i2 = this.fEndBufIdx;
                if (i <= iArr[i2]) {
                    if (i == iArr[iModChunkSize]) {
                        this.fBufIdx = iModChunkSize;
                        this.fTextIdx = iArr[iModChunkSize];
                        return true;
                    }
                    if (i == iArr[i2]) {
                        this.fBufIdx = i2;
                        this.fTextIdx = iArr[i2];
                        return true;
                    }
                    while (iModChunkSize != i2) {
                        int iModChunkSize2 = modChunkSize(((iModChunkSize + i2) + (iModChunkSize > i2 ? 128 : 0)) / 2);
                        if (this.fBoundaries[iModChunkSize2] > i) {
                            i2 = iModChunkSize2;
                        } else {
                            iModChunkSize = modChunkSize(iModChunkSize2 + 1);
                        }
                    }
                    int iModChunkSize3 = modChunkSize(i2 - 1);
                    this.fBufIdx = iModChunkSize3;
                    this.fTextIdx = this.fBoundaries[iModChunkSize3];
                    return true;
                }
            }
            return false;
        }

        BreakCache(BreakCache breakCache) {
            this.fBoundaries = new int[128];
            this.fStatuses = new short[128];
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
            this.fStartBufIdx = breakCache.fStartBufIdx;
            this.fEndBufIdx = breakCache.fEndBufIdx;
            this.fTextIdx = breakCache.fTextIdx;
            this.fBufIdx = breakCache.fBufIdx;
            this.fBoundaries = (int[]) breakCache.fBoundaries.clone();
            this.fStatuses = (short[]) breakCache.fStatuses.clone();
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
        }

        void dumpCache() {
            System.out.printf("fTextIdx:%d   fBufIdx:%d%n", Integer.valueOf(this.fTextIdx), Integer.valueOf(this.fBufIdx));
            int iModChunkSize = this.fStartBufIdx;
            while (true) {
                System.out.printf("%d  %d%n", Integer.valueOf(iModChunkSize), Integer.valueOf(this.fBoundaries[iModChunkSize]));
                if (iModChunkSize == this.fEndBufIdx) {
                    return;
                } else {
                    iModChunkSize = modChunkSize(iModChunkSize + 1);
                }
            }
        }
    }
}
