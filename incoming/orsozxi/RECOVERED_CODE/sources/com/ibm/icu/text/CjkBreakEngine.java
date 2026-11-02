package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.text.DictionaryBreakEngine;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.text.CharacterIterator;

/* loaded from: classes2.dex */
class CjkBreakEngine extends DictionaryBreakEngine {
    private static final int kMaxKatakanaGroupLength = 20;
    private static final int kMaxKatakanaLength = 8;
    private static final int kint32max = Integer.MAX_VALUE;
    private static final int maxSnlp = 255;
    private DictionaryMatcher fDictionary;
    private static final UnicodeSet fHangulWordSet = new UnicodeSet();
    private static final UnicodeSet fHanWordSet = new UnicodeSet();
    private static final UnicodeSet fKatakanaWordSet = new UnicodeSet();
    private static final UnicodeSet fHiraganaWordSet = new UnicodeSet();

    private static boolean isKatakana(int i) {
        return (i >= 12449 && i <= 12542 && i != 12539) || (i >= 65382 && i <= 65439);
    }

    static {
        fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
        fHanWordSet.applyPattern("[:Han:]");
        fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
        fHiraganaWordSet.applyPattern("[:Hiragana:]");
        fHangulWordSet.freeze();
        fHanWordSet.freeze();
        fKatakanaWordSet.freeze();
        fHiraganaWordSet.freeze();
    }

    public CjkBreakEngine(boolean z) throws IOException {
        this.fDictionary = null;
        this.fDictionary = DictionaryData.loadDictionaryFor("Hira");
        if (z) {
            setCharacters(fHangulWordSet);
            return;
        }
        UnicodeSet unicodeSet = new UnicodeSet();
        unicodeSet.addAll(fHanWordSet);
        unicodeSet.addAll(fKatakanaWordSet);
        unicodeSet.addAll(fHiraganaWordSet);
        unicodeSet.add(65392);
        unicodeSet.add(12540);
        setCharacters(unicodeSet);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CjkBreakEngine) {
            return this.fSet.equals(((CjkBreakEngine) obj).fSet);
        }
        return false;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    private static int getKatakanaCost(int i) {
        int[] iArr = {8192, R2.attr.spRocEndSpinnerItemStyle, 408, 240, 204, 252, 300, R2.attr.dialogLayout, R2.attr.flow_lastHorizontalBias};
        if (i > 8) {
            return 8192;
        }
        return iArr[i];
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine
    public int divideUpDictionaryRange(CharacterIterator characterIterator, int i, int i2, DictionaryBreakEngine.DequeI dequeI) {
        java.text.StringCharacterIterator stringCharacterIterator;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int katakanaCost;
        if (i >= i2) {
            return 0;
        }
        characterIterator.setIndex(i);
        int[] iArr5 = new int[(i2 - i) + 1];
        StringBuffer stringBuffer = new StringBuffer("");
        characterIterator.setIndex(i);
        while (characterIterator.getIndex() < i2) {
            stringBuffer.append(characterIterator.current());
            characterIterator.next();
        }
        String string = stringBuffer.toString();
        if (Normalizer.quickCheck(string, Normalizer.NFKC) == Normalizer.YES || Normalizer.isNormalized(string, Normalizer.NFKC, 0)) {
            stringCharacterIterator = new java.text.StringCharacterIterator(string);
            iArr5[0] = 0;
            int iCharCount = 0;
            i3 = 0;
            while (iCharCount < string.length()) {
                iCharCount += Character.charCount(string.codePointAt(iCharCount));
                i3++;
                iArr5[i3] = iCharCount;
            }
        } else {
            String strNormalize = Normalizer.normalize(string, Normalizer.NFKC);
            stringCharacterIterator = new java.text.StringCharacterIterator(strNormalize);
            iArr5 = new int[strNormalize.length() + 1];
            Normalizer normalizer = new Normalizer(string, Normalizer.NFKC, 0);
            iArr5[0] = 0;
            int index = 0;
            i3 = 0;
            while (index < normalizer.endIndex()) {
                normalizer.next();
                i3++;
                index = normalizer.getIndex();
                iArr5[i3] = index;
            }
        }
        java.text.StringCharacterIterator stringCharacterIterator2 = stringCharacterIterator;
        int i10 = i3;
        int i11 = i10 + 1;
        int[] iArr6 = new int[i11];
        iArr6[0] = 0;
        int i12 = 1;
        while (true) {
            i4 = Integer.MAX_VALUE;
            if (i12 > i10) {
                break;
            }
            iArr6[i12] = Integer.MAX_VALUE;
            i12++;
        }
        int[] iArr7 = new int[i11];
        for (int i13 = 0; i13 <= i10; i13++) {
            iArr7[i13] = -1;
        }
        int[] iArr8 = new int[i10];
        int[] iArr9 = new int[i10];
        stringCharacterIterator2.setIndex(0);
        int i14 = 0;
        boolean z = false;
        while (i14 < i10) {
            int index2 = stringCharacterIterator2.getIndex();
            if (iArr6[i14] == i4) {
                i8 = index2;
                i9 = i14;
                iArr = iArr9;
                iArr2 = iArr8;
                iArr3 = iArr7;
                iArr4 = iArr6;
            } else {
                int i15 = i14 + 20 < i10 ? 20 : i10 - i14;
                int[] iArr10 = new int[1];
                i8 = index2;
                i9 = i14;
                iArr = iArr9;
                int i16 = i15;
                iArr2 = iArr8;
                iArr3 = iArr7;
                int i17 = i15;
                iArr4 = iArr6;
                this.fDictionary.matches(stringCharacterIterator2, i16, iArr, iArr10, i17, iArr2);
                int i18 = iArr10[0];
                stringCharacterIterator2.setIndex(i8);
                if ((i18 == 0 || iArr[0] != 1) && CharacterIteration.current32(stringCharacterIterator2) != Integer.MAX_VALUE && !fHangulWordSet.contains(CharacterIteration.current32(stringCharacterIterator2))) {
                    iArr2[i18] = 255;
                    iArr[i18] = 1;
                    i18++;
                }
                for (int i19 = 0; i19 < i18; i19++) {
                    int i20 = iArr4[i9] + iArr2[i19];
                    if (i20 < iArr4[iArr[i19] + i9]) {
                        iArr4[iArr[i19] + i9] = i20;
                        iArr3[iArr[i19] + i9] = i9;
                    }
                }
                boolean zIsKatakana = isKatakana(CharacterIteration.current32(stringCharacterIterator2));
                if (!z && zIsKatakana) {
                    int i21 = i9 + 1;
                    CharacterIteration.next32(stringCharacterIterator2);
                    while (i21 < i10 && i21 - i9 < 20 && isKatakana(CharacterIteration.current32(stringCharacterIterator2))) {
                        CharacterIteration.next32(stringCharacterIterator2);
                        i21++;
                    }
                    int i22 = i21 - i9;
                    if (i22 < 20 && (katakanaCost = iArr4[i9] + getKatakanaCost(i22)) < iArr4[i21]) {
                        iArr4[i21] = katakanaCost;
                        iArr3[i21] = i9;
                    }
                }
                z = zIsKatakana;
            }
            i14 = i9 + 1;
            stringCharacterIterator2.setIndex(i8);
            CharacterIteration.next32(stringCharacterIterator2);
            iArr6 = iArr4;
            iArr9 = iArr;
            iArr8 = iArr2;
            iArr7 = iArr3;
            i4 = Integer.MAX_VALUE;
        }
        int[] iArr11 = iArr7;
        int[] iArr12 = new int[i11];
        if (iArr6[i10] == Integer.MAX_VALUE) {
            iArr12[0] = i10;
            i5 = 1;
        } else {
            i5 = 0;
            while (i10 > 0) {
                iArr12[i5] = i10;
                i5++;
                i10 = iArr11[i10];
            }
            Assert.assrt(iArr11[iArr12[i5 + (-1)]] == 0);
        }
        if (dequeI.size() == 0 || dequeI.peek() < i) {
            i6 = 0;
            iArr12[i5] = 0;
            i5++;
            i7 = 1;
        } else {
            i7 = 1;
            i6 = 0;
        }
        for (int i23 = i5 - i7; i23 >= 0; i23--) {
            int i24 = iArr5[iArr12[i23]] + i;
            if (!dequeI.contains(i24) && i24 != i) {
                dequeI.push(iArr5[iArr12[i23]] + i);
                i6++;
            }
        }
        if (!dequeI.isEmpty() && dequeI.peek() == i2) {
            dequeI.pop();
            i6--;
        }
        if (!dequeI.isEmpty()) {
            characterIterator.setIndex(dequeI.peek());
        }
        return i6;
    }
}
