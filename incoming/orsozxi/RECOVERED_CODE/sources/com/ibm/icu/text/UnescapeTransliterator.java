package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
class UnescapeTransliterator extends Transliterator {
    private static final char END = 65535;
    private char[] spec;

    static void register() {
        Transliterator.registerFactory("Hex-Any/Unicode", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any/Unicode", new char[]{2, 0, 16, 4, 6, 'U', '+', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/Java", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.2
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any/Java", new char[]{2, 0, 16, 4, 4, '\\', 'u', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/C", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.3
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any/C", new char[]{2, 0, 16, 4, 4, '\\', 'u', 2, 0, 16, '\b', '\b', '\\', 'U', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.4
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any/XML", new char[]{3, 1, 16, 1, 6, Typography.amp, '#', ULocale.PRIVATE_USE_EXTENSION, ';', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML10", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.5
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any/XML10", new char[]{2, 1, '\n', 1, 7, Typography.amp, '#', ';', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/Perl", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.6
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any/Perl", new char[]{3, 1, 16, 1, 6, '\\', ULocale.PRIVATE_USE_EXTENSION, '{', '}', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.7
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new UnescapeTransliterator("Hex-Any", new char[]{2, 0, 16, 4, 6, 'U', '+', 2, 0, 16, 4, 4, '\\', 'u', 2, 0, 16, '\b', '\b', '\\', 'U', 3, 1, 16, 1, 6, Typography.amp, '#', ULocale.PRIVATE_USE_EXTENSION, ';', 2, 1, '\n', 1, 7, Typography.amp, '#', ';', 3, 1, 16, 1, 6, '\\', ULocale.PRIVATE_USE_EXTENSION, '{', '}', 65535});
            }
        });
    }

    UnescapeTransliterator(String str, char[] cArr) {
        super(str, null);
        this.spec = cArr;
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        boolean z2;
        int charCount = position.start;
        int length = position.limit;
        loop0: while (charCount < length) {
            int i = 0;
            while (true) {
                char[] cArr = this.spec;
                if (cArr[i] == 65535) {
                    break;
                }
                int i2 = i + 1;
                char c = cArr[i];
                int i3 = i2 + 1;
                char c2 = cArr[i2];
                int i4 = i3 + 1;
                char c3 = cArr[i3];
                int i5 = i4 + 1;
                char c4 = cArr[i4];
                int i6 = i5 + 1;
                char c5 = cArr[i5];
                int charCount2 = charCount;
                int i7 = 0;
                while (true) {
                    if (i7 >= c) {
                        z2 = true;
                        break;
                    }
                    if (charCount2 < length || i7 <= 0) {
                        int i8 = charCount2 + 1;
                        if (replaceable.charAt(charCount2) != this.spec[i6 + i7]) {
                            charCount2 = i8;
                            break;
                        } else {
                            i7++;
                            charCount2 = i8;
                        }
                    } else if (z) {
                        break loop0;
                    }
                }
                z2 = false;
                if (z2) {
                    int i9 = 0;
                    int i10 = 0;
                    while (true) {
                        if (charCount2 >= length) {
                            if (charCount2 > charCount && z) {
                                break loop0;
                            } else {
                                break;
                            }
                        }
                        int iChar32At = replaceable.char32At(charCount2);
                        int iDigit = UCharacter.digit(iChar32At, c3);
                        if (iDigit < 0) {
                            break;
                        }
                        charCount2 += UTF16.getCharCount(iChar32At);
                        i9 = (i9 * c3) + iDigit;
                        i10++;
                        if (i10 == c5) {
                            break;
                        }
                    }
                    boolean z3 = i10 >= c4;
                    if (z3) {
                        int i11 = 0;
                        while (i11 < c2) {
                            if (charCount2 < length) {
                                int i12 = charCount2 + 1;
                                if (replaceable.charAt(charCount2) != this.spec[i6 + c + i11]) {
                                    charCount2 = i12;
                                } else {
                                    i11++;
                                    charCount2 = i12;
                                }
                            } else if (charCount2 > charCount && z) {
                                break loop0;
                            }
                            z3 = false;
                            break;
                        }
                        if (z3) {
                            String strValueOf = UTF16.valueOf(i9);
                            replaceable.replace(charCount, charCount2, strValueOf);
                            length -= (charCount2 - charCount) - strValueOf.length();
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                i = c + c2 + i6;
            }
            if (charCount < length) {
                charCount += UTF16.getCharCount(replaceable.char32At(charCount));
            }
        }
        position.contextLimit += length - position.limit;
        position.limit = length;
        position.start = charCount;
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) throws IOException {
        UnicodeSet filterAsUnicodeSet = getFilterAsUnicodeSet(unicodeSet);
        UnicodeSet unicodeSet4 = new UnicodeSet();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            char[] cArr = this.spec;
            if (cArr[i] == 65535) {
                break;
            }
            int i2 = cArr[i] + i + cArr[i + 1] + 5;
            char c = cArr[i + 2];
            for (int i3 = 0; i3 < c; i3++) {
                Utility.appendNumber(sb, i3, c, 0);
            }
            for (int i4 = i + 5; i4 < i2; i4++) {
                unicodeSet4.add(this.spec[i4]);
            }
            i = i2;
        }
        unicodeSet4.addAll(sb.toString());
        unicodeSet4.retainAll(filterAsUnicodeSet);
        if (unicodeSet4.size() > 0) {
            unicodeSet2.addAll(unicodeSet4);
            unicodeSet3.addAll(0, 1114111);
        }
    }
}
