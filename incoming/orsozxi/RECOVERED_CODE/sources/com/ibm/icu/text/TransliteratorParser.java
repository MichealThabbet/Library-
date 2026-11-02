package com.ibm.icu.text;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.RuleBasedTransliterator;
import java.text.ParsePosition;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class TransliteratorParser {
    private static final char ALT_FORWARD_RULE_OP = 8594;
    private static final char ALT_FUNCTION = 8710;
    private static final char ALT_FWDREV_RULE_OP = 8596;
    private static final char ALT_REVERSE_RULE_OP = 8592;
    private static final char ANCHOR_START = '^';
    private static final char CONTEXT_ANTE = '{';
    private static final char CONTEXT_POST = '}';
    private static final char CURSOR_OFFSET = '@';
    private static final char CURSOR_POS = '|';
    private static final char DOT = '.';
    private static final String DOT_SET = "[^[:Zp:][:Zl:]\\r\\n$]";
    private static final char END_OF_RULE = ';';
    private static final char ESCAPE = '\\';
    private static final char FORWARD_RULE_OP = '>';
    private static final char FUNCTION = '&';
    private static final char FWDREV_RULE_OP = '~';
    private static final String HALF_ENDERS = "=><←→↔;";
    private static final String ID_TOKEN = "::";
    private static final int ID_TOKEN_LEN = 2;
    private static final char KLEENE_STAR = '*';
    private static final char ONE_OR_MORE = '+';
    private static final String OPERATORS = "=><←→↔";
    private static final char QUOTE = '\'';
    private static final char REVERSE_RULE_OP = '<';
    private static final char RULE_COMMENT_CHAR = '#';
    private static final char SEGMENT_CLOSE = ')';
    private static final char SEGMENT_OPEN = '(';
    private static final char VARIABLE_DEF_OP = '=';
    private static final char ZERO_OR_ONE = '?';
    public UnicodeSet compoundFilter;
    private RuleBasedTransliterator.Data curData;
    public List<RuleBasedTransliterator.Data> dataVector;
    private int direction;
    private int dotStandIn = -1;
    public List<String> idBlockVector;
    private ParseData parseData;
    private List<StringMatcher> segmentObjects;
    private StringBuffer segmentStandins;
    private String undefinedVariableName;
    private char variableLimit;
    private Map<String, char[]> variableNames;
    private char variableNext;
    private List<Object> variablesVector;
    private static UnicodeSet ILLEGAL_TOP = new UnicodeSet("[\\)]");
    private static UnicodeSet ILLEGAL_SEG = new UnicodeSet("[\\{\\}\\|\\@]");
    private static UnicodeSet ILLEGAL_FUNC = new UnicodeSet("[\\^\\(\\.\\*\\+\\?\\{\\}\\|\\@]");

    private class ParseData implements SymbolTable {
        private ParseData() {
        }

        @Override // com.ibm.icu.text.SymbolTable
        public char[] lookup(String str) {
            return (char[]) TransliteratorParser.this.variableNames.get(str);
        }

        @Override // com.ibm.icu.text.SymbolTable
        public UnicodeMatcher lookupMatcher(int i) {
            int i2 = i - TransliteratorParser.this.curData.variablesBase;
            if (i2 < 0 || i2 >= TransliteratorParser.this.variablesVector.size()) {
                return null;
            }
            return (UnicodeMatcher) TransliteratorParser.this.variablesVector.get(i2);
        }

        @Override // com.ibm.icu.text.SymbolTable
        public String parseReference(String str, ParsePosition parsePosition, int i) {
            int index = parsePosition.getIndex();
            int i2 = index;
            while (i2 < i) {
                char cCharAt = str.charAt(i2);
                if ((i2 == index && !UCharacter.isUnicodeIdentifierStart(cCharAt)) || !UCharacter.isUnicodeIdentifierPart(cCharAt)) {
                    break;
                }
                i2++;
            }
            if (i2 == index) {
                return null;
            }
            parsePosition.setIndex(i2);
            return str.substring(index, i2);
        }

        public boolean isMatcher(int i) {
            int i2 = i - TransliteratorParser.this.curData.variablesBase;
            if (i2 < 0 || i2 >= TransliteratorParser.this.variablesVector.size()) {
                return true;
            }
            return TransliteratorParser.this.variablesVector.get(i2) instanceof UnicodeMatcher;
        }

        public boolean isReplacer(int i) {
            int i2 = i - TransliteratorParser.this.curData.variablesBase;
            if (i2 < 0 || i2 >= TransliteratorParser.this.variablesVector.size()) {
                return true;
            }
            return TransliteratorParser.this.variablesVector.get(i2) instanceof UnicodeReplacer;
        }
    }

    private static abstract class RuleBody {
        abstract String handleNextLine();

        abstract void reset();

        private RuleBody() {
        }

        String nextLine() {
            String strHandleNextLine;
            String strHandleNextLine2 = handleNextLine();
            if (strHandleNextLine2 == null || strHandleNextLine2.length() <= 0 || strHandleNextLine2.charAt(strHandleNextLine2.length() - 1) != '\\') {
                return strHandleNextLine2;
            }
            StringBuilder sb = new StringBuilder(strHandleNextLine2);
            do {
                sb.deleteCharAt(sb.length() - 1);
                strHandleNextLine = handleNextLine();
                if (strHandleNextLine != null) {
                    sb.append(strHandleNextLine);
                    if (strHandleNextLine.length() <= 0) {
                        break;
                    }
                } else {
                    break;
                }
            } while (strHandleNextLine.charAt(strHandleNextLine.length() - 1) == '\\');
            return sb.toString();
        }
    }

    private static class RuleArray extends RuleBody {
        String[] array;
        int i;

        public RuleArray(String[] strArr) {
            super();
            this.array = strArr;
            this.i = 0;
        }

        @Override // com.ibm.icu.text.TransliteratorParser.RuleBody
        public String handleNextLine() {
            int i = this.i;
            String[] strArr = this.array;
            if (i >= strArr.length) {
                return null;
            }
            this.i = i + 1;
            return strArr[i];
        }

        @Override // com.ibm.icu.text.TransliteratorParser.RuleBody
        public void reset() {
            this.i = 0;
        }
    }

    private static class RuleHalf {
        public boolean anchorEnd;
        public boolean anchorStart;
        public int ante;
        public int cursor;
        public int cursorOffset;
        private int cursorOffsetPos;
        private int nextSegmentNumber;
        public int post;
        public String text;

        private RuleHalf() {
            this.cursor = -1;
            this.ante = -1;
            this.post = -1;
            this.cursorOffset = 0;
            this.cursorOffsetPos = 0;
            this.anchorStart = false;
            this.anchorEnd = false;
            this.nextSegmentNumber = 1;
        }

        public int parse(String str, int i, int i2, TransliteratorParser transliteratorParser) {
            StringBuffer stringBuffer = new StringBuffer();
            int section = parseSection(str, i, i2, transliteratorParser, stringBuffer, TransliteratorParser.ILLEGAL_TOP, false);
            this.text = stringBuffer.toString();
            if (this.cursorOffset > 0 && this.cursor != this.cursorOffsetPos) {
                TransliteratorParser.syntaxError("Misplaced |", str, i);
            }
            return section;
        }

        /* JADX WARN: Removed duplicated region for block: B:97:0x018c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private int parseSection(java.lang.String r34, int r35, int r36, com.ibm.icu.text.TransliteratorParser r37, java.lang.StringBuffer r38, com.ibm.icu.text.UnicodeSet r39, boolean r40) {
            /*
                Method dump skipped, instructions count: 1172
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorParser.RuleHalf.parseSection(java.lang.String, int, int, com.ibm.icu.text.TransliteratorParser, java.lang.StringBuffer, com.ibm.icu.text.UnicodeSet, boolean):int");
        }

        void removeContext() {
            String str = this.text;
            int i = this.ante;
            if (i < 0) {
                i = 0;
            }
            int length = this.post;
            if (length < 0) {
                length = this.text.length();
            }
            this.text = str.substring(i, length);
            this.post = -1;
            this.ante = -1;
            this.anchorEnd = false;
            this.anchorStart = false;
        }

        public boolean isValidOutput(TransliteratorParser transliteratorParser) {
            int charCount = 0;
            while (charCount < this.text.length()) {
                int iCharAt = UTF16.charAt(this.text, charCount);
                charCount += UTF16.getCharCount(iCharAt);
                if (!transliteratorParser.parseData.isReplacer(iCharAt)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isValidInput(TransliteratorParser transliteratorParser) {
            int charCount = 0;
            while (charCount < this.text.length()) {
                int iCharAt = UTF16.charAt(this.text, charCount);
                charCount += UTF16.getCharCount(iCharAt);
                if (!transliteratorParser.parseData.isMatcher(iCharAt)) {
                    return false;
                }
            }
            return true;
        }
    }

    public void parse(String str, int i) {
        parseRules(new RuleArray(new String[]{str}), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01d8 A[LOOP:3: B:107:0x01d0->B:109:0x01d8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0227 A[Catch: IllegalArgumentException -> 0x0253, LOOP:4: B:123:0x021f->B:125:0x0227, LOOP_END, TryCatch #0 {IllegalArgumentException -> 0x0253, blocks: (B:111:0x0205, B:113:0x0209, B:120:0x0216, B:121:0x021d, B:116:0x020f, B:123:0x021f, B:125:0x0227, B:126:0x0237, B:128:0x023f, B:130:0x024d), top: B:149:0x0205 }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x028f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x018c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01ba  */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void parseRules(com.ibm.icu.text.TransliteratorParser.RuleBody r19, int r20) {
        /*
            Method dump skipped, instructions count: 687
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorParser.parseRules(com.ibm.icu.text.TransliteratorParser$RuleBody, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int parseRule(java.lang.String r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 486
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorParser.parseRule(java.lang.String, int, int):int");
    }

    private void setVariableRange(int i, int i2) {
        if (i > i2 || i < 0 || i2 > 65535) {
            throw new IllegalIcuArgumentException("Invalid variable range " + i + ", " + i2);
        }
        char c = (char) i;
        this.curData.variablesBase = c;
        if (this.dataVector.size() == 0) {
            this.variableNext = c;
            this.variableLimit = (char) (i2 + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVariableRange(int i, String str, int i2) {
        if (i < this.curData.variablesBase || i >= this.variableLimit) {
            return;
        }
        syntaxError("Variable range character in rule", str, i2);
    }

    private void pragmaMaximumBackup(int i) {
        throw new IllegalIcuArgumentException("use maximum backup pragma not implemented yet");
    }

    private void pragmaNormalizeRules(Normalizer.Mode mode) {
        throw new IllegalIcuArgumentException("use normalize rules pragma not implemented yet");
    }

    static boolean resemblesPragma(String str, int i, int i2) {
        return Utility.parsePattern(str, i, i2, "use ", null) >= 0;
    }

    private int parsePragma(String str, int i, int i2) {
        int[] iArr = new int[2];
        int i3 = i + 4;
        int pattern = Utility.parsePattern(str, i3, i2, "~variable range # #~;", iArr);
        if (pattern >= 0) {
            setVariableRange(iArr[0], iArr[1]);
            return pattern;
        }
        int pattern2 = Utility.parsePattern(str, i3, i2, "~maximum backup #~;", iArr);
        if (pattern2 >= 0) {
            pragmaMaximumBackup(iArr[0]);
            return pattern2;
        }
        int pattern3 = Utility.parsePattern(str, i3, i2, "~nfd rules~;", null);
        if (pattern3 >= 0) {
            pragmaNormalizeRules(Normalizer.NFD);
            return pattern3;
        }
        int pattern4 = Utility.parsePattern(str, i3, i2, "~nfc rules~;", null);
        if (pattern4 < 0) {
            return -1;
        }
        pragmaNormalizeRules(Normalizer.NFC);
        return pattern4;
    }

    static final void syntaxError(String str, String str2, int i) {
        throw new IllegalIcuArgumentException(str + " in \"" + Utility.escape(str2.substring(i, ruleEnd(str2, i, str2.length()))) + '\"');
    }

    static final int ruleEnd(String str, int i, int i2) {
        int iQuotedIndexOf = Utility.quotedIndexOf(str, i, i2, ";");
        return iQuotedIndexOf < 0 ? i2 : iQuotedIndexOf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final char parseSet(String str, ParsePosition parsePosition) {
        UnicodeSet unicodeSet = new UnicodeSet(str, parsePosition, this.parseData);
        if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Private use variables exhausted");
        }
        unicodeSet.compact();
        return generateStandInFor(unicodeSet);
    }

    char generateStandInFor(Object obj) {
        for (int i = 0; i < this.variablesVector.size(); i++) {
            if (this.variablesVector.get(i) == obj) {
                return (char) (this.curData.variablesBase + i);
            }
        }
        if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Variable range exhausted");
        }
        this.variablesVector.add(obj);
        char c = this.variableNext;
        this.variableNext = (char) (c + 1);
        return c;
    }

    public char getSegmentStandin(int i) {
        if (this.segmentStandins.length() < i) {
            this.segmentStandins.setLength(i);
        }
        int i2 = i - 1;
        char cCharAt = this.segmentStandins.charAt(i2);
        if (cCharAt == 0) {
            cCharAt = this.variableNext;
            if (cCharAt >= this.variableLimit) {
                throw new RuntimeException("Variable range exhausted");
            }
            this.variableNext = (char) (cCharAt + 1);
            this.variablesVector.add(null);
            this.segmentStandins.setCharAt(i2, cCharAt);
        }
        return cCharAt;
    }

    public void setSegmentObject(int i, StringMatcher stringMatcher) {
        while (this.segmentObjects.size() < i) {
            this.segmentObjects.add(null);
        }
        int segmentStandin = getSegmentStandin(i) - this.curData.variablesBase;
        int i2 = i - 1;
        if (this.segmentObjects.get(i2) != null || this.variablesVector.get(segmentStandin) != null) {
            throw new RuntimeException();
        }
        this.segmentObjects.set(i2, stringMatcher);
        this.variablesVector.set(segmentStandin, stringMatcher);
    }

    char getDotStandIn() {
        if (this.dotStandIn == -1) {
            this.dotStandIn = generateStandInFor(new UnicodeSet(DOT_SET));
        }
        return (char) this.dotStandIn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendVariableDef(String str, StringBuffer stringBuffer) {
        char[] cArr = this.variableNames.get(str);
        if (cArr == null) {
            if (this.undefinedVariableName == null) {
                this.undefinedVariableName = str;
                char c = this.variableNext;
                char c2 = this.variableLimit;
                if (c >= c2) {
                    throw new RuntimeException("Private use variables exhausted");
                }
                char c3 = (char) (c2 - 1);
                this.variableLimit = c3;
                stringBuffer.append(c3);
                return;
            }
            throw new IllegalIcuArgumentException("Undefined variable $" + str);
        }
        stringBuffer.append(cArr);
    }
}
