package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.UCharacterName;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Transliterator;

/* loaded from: classes2.dex */
class NameUnicodeTransliterator extends Transliterator {
    static final char CLOSE_DELIM = '}';
    static final char OPEN_DELIM = '\\';
    static final String OPEN_PAT = "\\N~{~";
    static final char SPACE = ' ';
    static final String _ID = "Name-Any";

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory() { // from class: com.ibm.icu.text.NameUnicodeTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new NameUnicodeTransliterator(null);
            }
        });
    }

    public NameUnicodeTransliterator(UnicodeFilter unicodeFilter) {
        super(_ID, unicodeFilter);
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        int i;
        int maxCharNameLength = UCharacterName.INSTANCE.getMaxCharNameLength() + 1;
        StringBuffer stringBuffer = new StringBuffer(maxCharNameLength);
        UnicodeSet unicodeSet = new UnicodeSet();
        UCharacterName.INSTANCE.getCharNameCharacters(unicodeSet);
        int charCount = position.start;
        int i2 = position.limit;
        loop0: while (true) {
            boolean z2 = false;
            i = -1;
            while (charCount < i2) {
                int iChar32At = replaceable.char32At(charCount);
                if (z2) {
                    if (z2) {
                        if (PatternProps.isWhiteSpace(iChar32At)) {
                            if (stringBuffer.length() > 0 && stringBuffer.charAt(stringBuffer.length() - 1) != ' ') {
                                stringBuffer.append(' ');
                                if (stringBuffer.length() > maxCharNameLength) {
                                    z2 = false;
                                }
                            }
                        } else if (iChar32At == 125) {
                            int length = stringBuffer.length();
                            if (length > 0 && stringBuffer.charAt(length - 1) == ' ') {
                                stringBuffer.setLength(length - 1);
                            }
                            int charFromExtendedName = UCharacter.getCharFromExtendedName(stringBuffer.toString());
                            if (charFromExtendedName != -1) {
                                int i3 = charCount + 1;
                                String strValueOf = UTF16.valueOf(charFromExtendedName);
                                replaceable.replace(i, i3, strValueOf);
                                int length2 = (i3 - i) - strValueOf.length();
                                charCount = i3 - length2;
                                i2 -= length2;
                            }
                        } else {
                            if (unicodeSet.contains(iChar32At)) {
                                UTF16.append(stringBuffer, iChar32At);
                                if (stringBuffer.length() >= maxCharNameLength) {
                                }
                            } else {
                                charCount--;
                            }
                            z2 = false;
                        }
                    }
                } else if (iChar32At == 92) {
                    int pattern = Utility.parsePattern(OPEN_PAT, replaceable, charCount, i2);
                    if (pattern < 0 || pattern >= i2) {
                        i = charCount;
                    } else {
                        stringBuffer.setLength(0);
                        z2 = true;
                        i = charCount;
                        charCount = pattern;
                    }
                }
                charCount += UTF16.getCharCount(iChar32At);
            }
            break loop0;
        }
        position.contextLimit += i2 - position.limit;
        position.limit = i2;
        if (z && i >= 0) {
            charCount = i;
        }
        position.start = charCount;
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet filterAsUnicodeSet = getFilterAsUnicodeSet(unicodeSet);
        if (filterAsUnicodeSet.containsAll("\\N{") && filterAsUnicodeSet.contains(125)) {
            UnicodeSet unicodeSetAdd = new UnicodeSet().addAll(48, 57).addAll(65, 70).addAll(97, 122).add(60).add(62).add(40).add(41).add(45).add(32).addAll("\\N{").add(125);
            unicodeSetAdd.retainAll(filterAsUnicodeSet);
            if (unicodeSetAdd.size() > 0) {
                unicodeSet2.addAll(unicodeSetAdd);
                unicodeSet3.addAll(0, 1114111);
            }
        }
    }
}
