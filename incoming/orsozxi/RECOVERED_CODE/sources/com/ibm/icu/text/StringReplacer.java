package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.RuleBasedTransliterator;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
class StringReplacer implements UnicodeReplacer {
    private int cursorPos;
    private final RuleBasedTransliterator.Data data;
    private boolean hasCursor;
    private boolean isComplex;
    private String output;

    public StringReplacer(String str, int i, RuleBasedTransliterator.Data data) {
        this.output = str;
        this.cursorPos = i;
        this.hasCursor = true;
        this.data = data;
        this.isComplex = true;
    }

    public StringReplacer(String str, RuleBasedTransliterator.Data data) {
        this.output = str;
        this.cursorPos = 0;
        this.hasCursor = false;
        this.data = data;
        this.isComplex = true;
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public int replace(Replaceable replaceable, int i, int i2, int[] iArr) {
        int i3;
        int i4;
        int length;
        int i5;
        if (!this.isComplex) {
            replaceable.replace(i, i2, this.output);
            length = this.output.length();
            i4 = this.cursorPos;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            this.isComplex = false;
            int length2 = replaceable.length();
            if (i > 0) {
                int charCount = UTF16.getCharCount(replaceable.char32At(i - 1));
                replaceable.copy(i - charCount, i, length2);
                i3 = charCount + length2;
            } else {
                replaceable.replace(length2, length2, "\uffff");
                i3 = length2 + 1;
            }
            int length3 = i3;
            int charCount2 = 0;
            int length4 = 0;
            int charCount3 = 0;
            while (charCount2 < this.output.length()) {
                if (charCount2 == this.cursorPos) {
                    length4 = (stringBuffer.length() + length3) - i3;
                }
                int iCharAt = UTF16.charAt(this.output, charCount2);
                charCount2 += UTF16.getCharCount(iCharAt);
                if (charCount2 == this.output.length()) {
                    charCount3 = UTF16.getCharCount(replaceable.char32At(i2));
                    replaceable.copy(i2, i2 + charCount3, length3);
                }
                UnicodeReplacer unicodeReplacerLookupReplacer = this.data.lookupReplacer(iCharAt);
                if (unicodeReplacerLookupReplacer == null) {
                    UTF16.append(stringBuffer, iCharAt);
                } else {
                    this.isComplex = true;
                    if (stringBuffer.length() > 0) {
                        replaceable.replace(length3, length3, stringBuffer.toString());
                        length3 += stringBuffer.length();
                        stringBuffer.setLength(0);
                    }
                    length3 += unicodeReplacerLookupReplacer.replace(replaceable, length3, length3, iArr);
                }
            }
            if (stringBuffer.length() > 0) {
                replaceable.replace(length3, length3, stringBuffer.toString());
                length3 += stringBuffer.length();
            }
            i4 = charCount2 == this.cursorPos ? length3 - i3 : length4;
            int i6 = length3 - i3;
            replaceable.copy(i3, length3, i);
            replaceable.replace(length2 + i6, length3 + charCount3 + i6, "");
            replaceable.replace(i + i6, i2 + i6, "");
            length = i6;
        }
        if (this.hasCursor) {
            int i7 = this.cursorPos;
            if (i7 < 0) {
                while (i7 < 0 && i > 0) {
                    i -= UTF16.getCharCount(replaceable.char32At(i - 1));
                    i7++;
                }
                i5 = i + i7;
            } else if (i7 > this.output.length()) {
                int charCount4 = i + length;
                int length5 = this.cursorPos - this.output.length();
                while (length5 > 0 && charCount4 < replaceable.length()) {
                    charCount4 += UTF16.getCharCount(replaceable.char32At(charCount4));
                    length5--;
                }
                i5 = charCount4 + length5;
            } else {
                i5 = i + i4;
            }
            iArr[0] = i5;
        }
        return length;
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public String toReplacerPattern(boolean z) {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int i2 = this.cursorPos;
        if (this.hasCursor && i2 < 0) {
            while (true) {
                i = i2 + 1;
                if (i2 >= 0) {
                    break;
                }
                Utility.appendToRule(stringBuffer, 64, true, z, stringBuffer2);
                i2 = i;
            }
            i2 = i;
        }
        for (int i3 = 0; i3 < this.output.length(); i3++) {
            if (this.hasCursor && i3 == i2) {
                Utility.appendToRule(stringBuffer, 124, true, z, stringBuffer2);
            }
            char cCharAt = this.output.charAt(i3);
            UnicodeReplacer unicodeReplacerLookupReplacer = this.data.lookupReplacer(cCharAt);
            if (unicodeReplacerLookupReplacer == null) {
                Utility.appendToRule(stringBuffer, (int) cCharAt, false, z, stringBuffer2);
            } else {
                StringBuffer stringBuffer3 = new StringBuffer(" ");
                stringBuffer3.append(unicodeReplacerLookupReplacer.toReplacerPattern(z));
                stringBuffer3.append(TokenParser.SP);
                Utility.appendToRule(stringBuffer, stringBuffer3.toString(), true, z, stringBuffer2);
            }
        }
        if (this.hasCursor && i2 > this.output.length()) {
            int length = i2 - this.output.length();
            while (true) {
                int i4 = length - 1;
                if (length <= 0) {
                    break;
                }
                Utility.appendToRule(stringBuffer, 64, true, z, stringBuffer2);
                length = i4;
            }
            Utility.appendToRule(stringBuffer, 124, true, z, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, -1, true, z, stringBuffer2);
        return stringBuffer.toString();
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
        int charCount = 0;
        while (charCount < this.output.length()) {
            int iCharAt = UTF16.charAt(this.output, charCount);
            UnicodeReplacer unicodeReplacerLookupReplacer = this.data.lookupReplacer(iCharAt);
            if (unicodeReplacerLookupReplacer == null) {
                unicodeSet.add(iCharAt);
            } else {
                unicodeReplacerLookupReplacer.addReplacementSetTo(unicodeSet);
            }
            charCount += UTF16.getCharCount(iCharAt);
        }
    }
}
