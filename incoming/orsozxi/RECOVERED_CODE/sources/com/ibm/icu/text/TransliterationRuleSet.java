package com.ibm.icu.text;

import com.ibm.icu.text.Transliterator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
class TransliterationRuleSet {
    private int[] index;
    private TransliterationRule[] rules;
    private List<TransliterationRule> ruleVector = new ArrayList();
    private int maxContextLength = 0;

    public int getMaximumContextLength() {
        return this.maxContextLength;
    }

    public void addRule(TransliterationRule transliterationRule) {
        this.ruleVector.add(transliterationRule);
        int anteContextLength = transliterationRule.getAnteContextLength();
        if (anteContextLength > this.maxContextLength) {
            this.maxContextLength = anteContextLength;
        }
        this.rules = null;
    }

    public void freeze() {
        int i;
        int size = this.ruleVector.size();
        this.index = new int[257];
        ArrayList arrayList = new ArrayList(size * 2);
        int[] iArr = new int[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = this.ruleVector.get(i3).getIndexValue();
        }
        for (int i4 = 0; i4 < 256; i4++) {
            this.index[i4] = arrayList.size();
            for (int i5 = 0; i5 < size; i5++) {
                if (iArr[i5] >= 0) {
                    if (iArr[i5] == i4) {
                        arrayList.add(this.ruleVector.get(i5));
                    }
                } else {
                    TransliterationRule transliterationRule = this.ruleVector.get(i5);
                    if (transliterationRule.matchesIndexValue(i4)) {
                        arrayList.add(transliterationRule);
                    }
                }
            }
        }
        this.index[256] = arrayList.size();
        TransliterationRule[] transliterationRuleArr = new TransliterationRule[arrayList.size()];
        this.rules = transliterationRuleArr;
        arrayList.toArray(transliterationRuleArr);
        StringBuilder sb = null;
        while (i2 < 256) {
            int i6 = this.index[i2];
            while (true) {
                i = i2 + 1;
                if (i6 < this.index[i] - 1) {
                    TransliterationRule transliterationRule2 = this.rules[i6];
                    i6++;
                    for (int i7 = i6; i7 < this.index[i]; i7++) {
                        TransliterationRule transliterationRule3 = this.rules[i7];
                        if (transliterationRule2.masks(transliterationRule3)) {
                            if (sb == null) {
                                sb = new StringBuilder();
                            } else {
                                sb.append("\n");
                            }
                            sb.append("Rule " + transliterationRule2 + " masks " + transliterationRule3);
                        }
                    }
                }
            }
            i2 = i;
        }
        if (sb != null) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean transliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        int iChar32At = replaceable.char32At(position.start) & 255;
        for (int i = this.index[iChar32At]; i < this.index[iChar32At + 1]; i++) {
            int iMatchAndReplace = this.rules[i].matchAndReplace(replaceable, position, z);
            if (iMatchAndReplace == 1) {
                return false;
            }
            if (iMatchAndReplace == 2) {
                return true;
            }
        }
        position.start += UTF16.getCharCount(replaceable.char32At(position.start));
        return true;
    }

    String toRules(boolean z) {
        int size = this.ruleVector.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append('\n');
            }
            sb.append(this.ruleVector.get(i).toRule(z));
        }
        return sb.toString();
    }

    void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = new UnicodeSet(unicodeSet);
        UnicodeSet unicodeSet5 = new UnicodeSet();
        int size = this.ruleVector.size();
        for (int i = 0; i < size; i++) {
            this.ruleVector.get(i).addSourceTargetSet(unicodeSet4, unicodeSet2, unicodeSet3, unicodeSet5.clear());
            unicodeSet4.addAll(unicodeSet5);
        }
    }
}
