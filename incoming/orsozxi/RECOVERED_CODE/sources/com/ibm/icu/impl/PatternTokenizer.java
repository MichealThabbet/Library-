package com.ibm.icu.impl;

import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class PatternTokenizer {
    private static final int AFTER_QUOTE = -1;
    public static final char BACK_SLASH = '\\';
    public static final int BROKEN_ESCAPE = 4;
    public static final int BROKEN_QUOTE = 3;
    public static final int DONE = 0;
    private static final int HEX = 4;
    private static int IN_QUOTE = -2;
    public static final int LITERAL = 2;
    private static final int NONE = 0;
    private static final int NORMAL_QUOTE = 2;
    private static int NO_QUOTE = -1;
    public static final char SINGLE_QUOTE = '\'';
    private static final int SLASH_START = 3;
    private static final int START_QUOTE = 1;
    public static final int SYNTAX = 1;
    public static final int UNKNOWN = 5;
    private int limit;
    private String pattern;
    private int start;
    private UnicodeSet ignorableCharacters = new UnicodeSet();
    private UnicodeSet syntaxCharacters = new UnicodeSet();
    private UnicodeSet extraQuotingCharacters = new UnicodeSet();
    private UnicodeSet escapeCharacters = new UnicodeSet();
    private boolean usingSlash = false;
    private boolean usingQuote = false;
    private transient UnicodeSet needingQuoteCharacters = null;

    public UnicodeSet getIgnorableCharacters() {
        return (UnicodeSet) this.ignorableCharacters.clone();
    }

    public PatternTokenizer setIgnorableCharacters(UnicodeSet unicodeSet) {
        this.ignorableCharacters = (UnicodeSet) unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public UnicodeSet getSyntaxCharacters() {
        return (UnicodeSet) this.syntaxCharacters.clone();
    }

    public UnicodeSet getExtraQuotingCharacters() {
        return (UnicodeSet) this.extraQuotingCharacters.clone();
    }

    public PatternTokenizer setSyntaxCharacters(UnicodeSet unicodeSet) {
        this.syntaxCharacters = (UnicodeSet) unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public PatternTokenizer setExtraQuotingCharacters(UnicodeSet unicodeSet) {
        this.extraQuotingCharacters = (UnicodeSet) unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public UnicodeSet getEscapeCharacters() {
        return (UnicodeSet) this.escapeCharacters.clone();
    }

    public PatternTokenizer setEscapeCharacters(UnicodeSet unicodeSet) {
        this.escapeCharacters = (UnicodeSet) unicodeSet.clone();
        return this;
    }

    public boolean isUsingQuote() {
        return this.usingQuote;
    }

    public PatternTokenizer setUsingQuote(boolean z) {
        this.usingQuote = z;
        this.needingQuoteCharacters = null;
        return this;
    }

    public boolean isUsingSlash() {
        return this.usingSlash;
    }

    public PatternTokenizer setUsingSlash(boolean z) {
        this.usingSlash = z;
        this.needingQuoteCharacters = null;
        return this;
    }

    public int getLimit() {
        return this.limit;
    }

    public PatternTokenizer setLimit(int i) {
        this.limit = i;
        return this;
    }

    public int getStart() {
        return this.start;
    }

    public PatternTokenizer setStart(int i) {
        this.start = i;
        return this;
    }

    public PatternTokenizer setPattern(CharSequence charSequence) {
        return setPattern(charSequence.toString());
    }

    public PatternTokenizer setPattern(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Inconsistent arguments");
        }
        this.start = 0;
        this.limit = str.length();
        this.pattern = str;
        return this;
    }

    public String quoteLiteral(CharSequence charSequence) {
        return quoteLiteral(charSequence.toString());
    }

    public String quoteLiteral(String str) {
        if (this.needingQuoteCharacters == null) {
            UnicodeSet unicodeSetAddAll = new UnicodeSet().addAll(this.syntaxCharacters).addAll(this.ignorableCharacters).addAll(this.extraQuotingCharacters);
            this.needingQuoteCharacters = unicodeSetAddAll;
            if (this.usingSlash) {
                unicodeSetAddAll.add(92);
            }
            if (this.usingQuote) {
                this.needingQuoteCharacters.add(39);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = NO_QUOTE;
        int charCount = 0;
        while (charCount < str.length()) {
            int iCharAt = UTF16.charAt(str, charCount);
            if (this.escapeCharacters.contains(iCharAt)) {
                if (i == IN_QUOTE) {
                    stringBuffer.append(SINGLE_QUOTE);
                    i = NO_QUOTE;
                }
                appendEscaped(stringBuffer, iCharAt);
            } else if (this.needingQuoteCharacters.contains(iCharAt)) {
                if (i == IN_QUOTE) {
                    UTF16.append(stringBuffer, iCharAt);
                    if (this.usingQuote && iCharAt == 39) {
                        stringBuffer.append(SINGLE_QUOTE);
                    }
                } else if (this.usingSlash) {
                    stringBuffer.append('\\');
                    UTF16.append(stringBuffer, iCharAt);
                } else if (!this.usingQuote) {
                    appendEscaped(stringBuffer, iCharAt);
                } else if (iCharAt == 39) {
                    stringBuffer.append(SINGLE_QUOTE);
                    stringBuffer.append(SINGLE_QUOTE);
                } else {
                    stringBuffer.append(SINGLE_QUOTE);
                    UTF16.append(stringBuffer, iCharAt);
                    i = IN_QUOTE;
                }
            } else {
                if (i == IN_QUOTE) {
                    stringBuffer.append(SINGLE_QUOTE);
                    i = NO_QUOTE;
                }
                UTF16.append(stringBuffer, iCharAt);
            }
            charCount += UTF16.getCharCount(iCharAt);
        }
        if (i == IN_QUOTE) {
            stringBuffer.append(SINGLE_QUOTE);
        }
        return stringBuffer.toString();
    }

    private void appendEscaped(StringBuffer stringBuffer, int i) {
        if (i <= 65535) {
            stringBuffer.append("\\u");
            stringBuffer.append(Utility.hex(i, 4));
        } else {
            stringBuffer.append("\\U");
            stringBuffer.append(Utility.hex(i, 8));
        }
    }

    public String normalize() {
        int i = this.start;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        while (true) {
            stringBuffer2.setLength(0);
            int next = next(stringBuffer2);
            if (next == 0) {
                this.start = i;
                return stringBuffer.toString();
            }
            if (next != 1) {
                stringBuffer.append(quoteLiteral(stringBuffer2));
            } else {
                stringBuffer.append(stringBuffer2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00bb A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int next(java.lang.StringBuffer r17) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.PatternTokenizer.next(java.lang.StringBuffer):int");
    }
}
