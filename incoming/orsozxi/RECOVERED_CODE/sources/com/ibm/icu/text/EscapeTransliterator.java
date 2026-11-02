package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Transliterator;
import java.io.IOException;

/* loaded from: classes2.dex */
class EscapeTransliterator extends Transliterator {
    private boolean grokSupplementals;
    private int minDigits;
    private String prefix;
    private int radix;
    private String suffix;
    private EscapeTransliterator supplementalHandler;

    static void register() {
        Transliterator.registerFactory("Any-Hex/Unicode", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/Unicode", "U+", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Java", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.2
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/Java", "\\u", "", 16, 4, false, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/C", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.3
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/C", "\\u", "", 16, 4, true, new EscapeTransliterator("", "\\U", "", 16, 8, true, null));
            }
        });
        Transliterator.registerFactory("Any-Hex/XML", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.4
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/XML", "&#x", ";", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/XML10", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.5
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/XML10", "&#", ";", 10, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Perl", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.6
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/Perl", "\\x{", "}", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Plain", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.7
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex/Plain", "", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.8
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new EscapeTransliterator("Any-Hex", "\\u", "", 16, 4, false, null);
            }
        });
    }

    EscapeTransliterator(String str, String str2, String str3, int i, int i2, boolean z, EscapeTransliterator escapeTransliterator) {
        super(str, null);
        this.prefix = str2;
        this.suffix = str3;
        this.radix = i;
        this.minDigits = i2;
        this.grokSupplementals = z;
        this.supplementalHandler = escapeTransliterator;
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) throws IOException {
        int length = position.start;
        int length2 = position.limit;
        StringBuilder sb = new StringBuilder(this.prefix);
        int length3 = this.prefix.length();
        boolean z2 = false;
        while (length < length2) {
            int iChar32At = this.grokSupplementals ? replaceable.char32At(length) : replaceable.charAt(length);
            int charCount = this.grokSupplementals ? UTF16.getCharCount(iChar32At) : 1;
            if (((-65536) & iChar32At) != 0 && this.supplementalHandler != null) {
                sb.setLength(0);
                sb.append(this.supplementalHandler.prefix);
                EscapeTransliterator escapeTransliterator = this.supplementalHandler;
                Utility.appendNumber(sb, iChar32At, escapeTransliterator.radix, escapeTransliterator.minDigits);
                sb.append(this.supplementalHandler.suffix);
                z2 = true;
            } else {
                if (z2) {
                    sb.setLength(0);
                    sb.append(this.prefix);
                    z2 = false;
                } else {
                    sb.setLength(length3);
                }
                Utility.appendNumber(sb, iChar32At, this.radix, this.minDigits);
                sb.append(this.suffix);
            }
            replaceable.replace(length, length + charCount, sb.toString());
            length += sb.length();
            length2 += sb.length() - charCount;
        }
        position.contextLimit += length2 - position.limit;
        position.limit = length2;
        position.start = length;
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) throws IOException {
        unicodeSet2.addAll(getFilterAsUnicodeSet(unicodeSet));
        for (EscapeTransliterator escapeTransliterator = this; escapeTransliterator != null; escapeTransliterator = escapeTransliterator.supplementalHandler) {
            if (unicodeSet.size() != 0) {
                unicodeSet3.addAll(escapeTransliterator.prefix);
                unicodeSet3.addAll(escapeTransliterator.suffix);
                StringBuilder sb = new StringBuilder();
                int i = 0;
                while (true) {
                    int i2 = escapeTransliterator.radix;
                    if (i >= i2) {
                        break;
                    }
                    Utility.appendNumber(sb, i, i2, escapeTransliterator.minDigits);
                    i++;
                }
                unicodeSet3.addAll(sb.toString());
            }
        }
    }
}
