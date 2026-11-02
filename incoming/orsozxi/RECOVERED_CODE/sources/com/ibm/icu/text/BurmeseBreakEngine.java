package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;

/* loaded from: classes2.dex */
class BurmeseBreakEngine extends DictionaryBreakEngine {
    private static final byte BURMESE_LOOKAHEAD = 3;
    private static final byte BURMESE_MIN_WORD = 2;
    private static final byte BURMESE_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte BURMESE_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fEndWordSet;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fBurmeseWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fBeginWordSet = new UnicodeSet();

    static {
        fBurmeseWordSet.applyPattern("[[:Mymr:]&[:LineBreak=SA:]]");
        fBurmeseWordSet.compact();
        fMarkSet.applyPattern("[[:Mymr:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fBurmeseWordSet);
        fBeginWordSet.add(4096, R2.style.Base_V28_Theme_AppCompat);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fBurmeseWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
    }

    public BurmeseBreakEngine() throws IOException {
        setCharacters(fBurmeseWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Mymr");
    }

    public boolean equals(Object obj) {
        return obj instanceof BurmeseBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine, com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int i) {
        return UCharacter.getIntPropertyValue(i, 4106) == 28;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0083 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0093 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x011e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x010c A[ADDED_TO_REGION, EDGE_INSN: B:81:0x010c->B:62:0x010c BREAK  A[LOOP:3: B:57:0x00f0->B:61:0x0102], REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0085 A[EDGE_INSN: B:85:0x0085->B:32:0x0085 BREAK  A[LOOP:4: B:19:0x0046->B:87:?], SYNTHETIC] */
    @Override // com.ibm.icu.text.DictionaryBreakEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int divideUpDictionaryRange(java.text.CharacterIterator r17, int r18, int r19, com.ibm.icu.text.DictionaryBreakEngine.DequeI r20) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.BurmeseBreakEngine.divideUpDictionaryRange(java.text.CharacterIterator, int, int, com.ibm.icu.text.DictionaryBreakEngine$DequeI):int");
    }
}
