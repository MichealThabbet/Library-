package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;

/* loaded from: classes2.dex */
class ThaiBreakEngine extends DictionaryBreakEngine {
    private static final byte THAI_LOOKAHEAD = 3;
    private static final char THAI_MAIYAMOK = 3654;
    private static final byte THAI_MIN_WORD = 2;
    private static final byte THAI_MIN_WORD_SPAN = 4;
    private static final char THAI_PAIYANNOI = 3631;
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fEndWordSet;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fThaiWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fBeginWordSet = new UnicodeSet();
    private static UnicodeSet fSuffixSet = new UnicodeSet();

    static {
        fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        fThaiWordSet.compact();
        fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        UnicodeSet unicodeSet = new UnicodeSet(fThaiWordSet);
        fEndWordSet = unicodeSet;
        unicodeSet.remove(R2.string.logo);
        fEndWordSet.remove(R2.string.material_timepicker_text_input_mode_description, R2.string.md_custom_label);
        fBeginWordSet.add(R2.string.eraser, R2.string.location);
        fBeginWordSet.add(R2.string.material_timepicker_text_input_mode_description, R2.string.md_custom_label);
        fSuffixSet.add(R2.string.location_not_found);
        fSuffixSet.add(R2.string.md_error_label);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fSuffixSet.compact();
        fThaiWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
        fSuffixSet.freeze();
    }

    public ThaiBreakEngine() throws IOException {
        setCharacters(fThaiWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }

    public boolean equals(Object obj) {
        return obj instanceof ThaiBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine, com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int i) {
        return UCharacter.getIntPropertyValue(i, 4106) == 38;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0081 A[EDGE_INSN: B:104:0x0081->B:30:0x0081 BREAK  A[LOOP:4: B:18:0x0045->B:105:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:? A[LOOP:4: B:18:0x0045->B:105:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x010e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0176 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0166 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0108 A[ADDED_TO_REGION, EDGE_INSN: B:99:0x0108->B:60:0x0108 BREAK  A[LOOP:3: B:55:0x00ec->B:59:0x00fe], REMOVE, SYNTHETIC] */
    @Override // com.ibm.icu.text.DictionaryBreakEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int divideUpDictionaryRange(java.text.CharacterIterator r17, int r18, int r19, com.ibm.icu.text.DictionaryBreakEngine.DequeI r20) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.ThaiBreakEngine.divideUpDictionaryRange(java.text.CharacterIterator, int, int, com.ibm.icu.text.DictionaryBreakEngine$DequeI):int");
    }
}
