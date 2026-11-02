package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;

/* loaded from: classes2.dex */
class LaoBreakEngine extends DictionaryBreakEngine {
    private static final byte LAO_LOOKAHEAD = 3;
    private static final byte LAO_MIN_WORD = 2;
    private static final byte LAO_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte LAO_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fEndWordSet;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fLaoWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fBeginWordSet = new UnicodeSet();

    static {
        fLaoWordSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]]");
        fLaoWordSet.compact();
        fMarkSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        UnicodeSet unicodeSet = new UnicodeSet(fLaoWordSet);
        fEndWordSet = unicodeSet;
        unicodeSet.remove(R2.string.search_menu_title, R2.string.select_minutes);
        fBeginWordSet.add(R2.string.note_reminder_symbol, R2.string.redo);
        fBeginWordSet.add(R2.string.settings_dynamic_menu, R2.string.settings_dynamic_menu_summary);
        fBeginWordSet.add(R2.string.search_menu_title, R2.string.select_minutes);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fLaoWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
    }

    public LaoBreakEngine() throws IOException {
        setCharacters(fLaoWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Laoo");
    }

    public boolean equals(Object obj) {
        return obj instanceof LaoBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine, com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int i) {
        return UCharacter.getIntPropertyValue(i, 4106) == 24;
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
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.LaoBreakEngine.divideUpDictionaryRange(java.text.CharacterIterator, int, int, com.ibm.icu.text.DictionaryBreakEngine$DequeI):int");
    }
}
