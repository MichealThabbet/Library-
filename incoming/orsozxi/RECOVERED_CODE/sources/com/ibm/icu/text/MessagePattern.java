package com.ibm.icu.text;

import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class MessagePattern implements Cloneable, Freezable<MessagePattern> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ARG_NAME_NOT_NUMBER = -1;
    public static final int ARG_NAME_NOT_VALID = -2;
    private static final int MAX_PREFIX_LENGTH = 24;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8d;
    private ApostropheMode aposMode;
    private volatile boolean frozen;
    private boolean hasArgNames;
    private boolean hasArgNumbers;
    private String msg;
    private boolean needsAutoQuoting;
    private ArrayList<Double> numericValues;
    private ArrayList<Part> parts;
    private static final ApostropheMode defaultAposMode = ApostropheMode.valueOf(ICUConfig.get("com.ibm.icu.text.MessagePattern.ApostropheMode", "DOUBLE_OPTIONAL"));
    private static final ArgType[] argTypes = ArgType.values();

    public enum ApostropheMode {
        DOUBLE_OPTIONAL,
        DOUBLE_REQUIRED
    }

    private static boolean isArgTypeChar(int i) {
        return (97 <= i && i <= 122) || (65 <= i && i <= 90);
    }

    private void postParse() {
    }

    public MessagePattern() {
        this.parts = new ArrayList<>();
        this.aposMode = defaultAposMode;
    }

    public MessagePattern(ApostropheMode apostropheMode) {
        this.parts = new ArrayList<>();
        this.aposMode = apostropheMode;
    }

    public MessagePattern(String str) {
        this.parts = new ArrayList<>();
        this.aposMode = defaultAposMode;
        parse(str);
    }

    public MessagePattern parse(String str) {
        preParse(str);
        parseMessage(0, 0, 0, ArgType.NONE);
        postParse();
        return this;
    }

    public MessagePattern parseChoiceStyle(String str) {
        preParse(str);
        parseChoiceStyle(0, 0);
        postParse();
        return this;
    }

    public MessagePattern parsePluralStyle(String str) {
        preParse(str);
        parsePluralOrSelectStyle(ArgType.PLURAL, 0, 0);
        postParse();
        return this;
    }

    public MessagePattern parseSelectStyle(String str) {
        preParse(str);
        parsePluralOrSelectStyle(ArgType.SELECT, 0, 0);
        postParse();
        return this;
    }

    public void clear() {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to clear() a frozen MessagePattern instance.");
        }
        this.msg = null;
        this.hasArgNumbers = false;
        this.hasArgNames = false;
        this.needsAutoQuoting = false;
        this.parts.clear();
        ArrayList<Double> arrayList = this.numericValues;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void clearPatternAndSetApostropheMode(ApostropheMode apostropheMode) {
        clear();
        this.aposMode = apostropheMode;
    }

    public boolean equals(Object obj) {
        String str;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessagePattern messagePattern = (MessagePattern) obj;
        return this.aposMode.equals(messagePattern.aposMode) && ((str = this.msg) != null ? str.equals(messagePattern.msg) : messagePattern.msg == null) && this.parts.equals(messagePattern.parts);
    }

    public int hashCode() {
        int iHashCode = this.aposMode.hashCode() * 37;
        String str = this.msg;
        return ((iHashCode + (str != null ? str.hashCode() : 0)) * 37) + this.parts.hashCode();
    }

    public ApostropheMode getApostropheMode() {
        return this.aposMode;
    }

    boolean jdkAposMode() {
        return this.aposMode == ApostropheMode.DOUBLE_REQUIRED;
    }

    public String getPatternString() {
        return this.msg;
    }

    public boolean hasNamedArguments() {
        return this.hasArgNames;
    }

    public boolean hasNumberedArguments() {
        return this.hasArgNumbers;
    }

    public String toString() {
        return this.msg;
    }

    public static int validateArgumentName(String str) {
        if (PatternProps.isIdentifier(str)) {
            return parseArgNumber(str, 0, str.length());
        }
        return -2;
    }

    public String autoQuoteApostropheDeep() {
        if (!this.needsAutoQuoting) {
            return this.msg;
        }
        StringBuilder sb = null;
        int iCountParts = countParts();
        while (iCountParts > 0) {
            iCountParts--;
            Part part = getPart(iCountParts);
            if (part.getType() == Part.Type.INSERT_CHAR) {
                if (sb == null) {
                    sb = new StringBuilder(this.msg.length() + 10);
                    sb.append(this.msg);
                }
                sb.insert(part.index, (char) part.value);
            }
        }
        if (sb == null) {
            return this.msg;
        }
        return sb.toString();
    }

    public int countParts() {
        return this.parts.size();
    }

    public Part getPart(int i) {
        return this.parts.get(i);
    }

    public Part.Type getPartType(int i) {
        return this.parts.get(i).type;
    }

    public int getPatternIndex(int i) {
        return this.parts.get(i).index;
    }

    public String getSubstring(Part part) {
        int i = part.index;
        return this.msg.substring(i, part.length + i);
    }

    public boolean partSubstringMatches(Part part, String str) {
        return part.length == str.length() && this.msg.regionMatches(part.index, str, 0, part.length);
    }

    public double getNumericValue(Part part) {
        Part.Type type = part.type;
        if (type != Part.Type.ARG_INT) {
            if (type == Part.Type.ARG_DOUBLE) {
                return this.numericValues.get(part.value).doubleValue();
            }
            return -1.23456789E8d;
        }
        return part.value;
    }

    public double getPluralOffset(int i) {
        Part part = this.parts.get(i);
        if (part.type.hasNumericValue()) {
            return getNumericValue(part);
        }
        return 0.0d;
    }

    public int getLimitPartIndex(int i) {
        int i2 = this.parts.get(i).limitPartIndex;
        return i2 < i ? i : i2;
    }

    public static final class Part {
        private static final int MAX_LENGTH = 65535;
        private static final int MAX_VALUE = 32767;
        private final int index;
        private final char length;
        private int limitPartIndex;
        private final Type type;
        private short value;

        private Part(Type type, int i, int i2, int i3) {
            this.type = type;
            this.index = i;
            this.length = (char) i2;
            this.value = (short) i3;
        }

        public Type getType() {
            return this.type;
        }

        public int getIndex() {
            return this.index;
        }

        public int getLength() {
            return this.length;
        }

        public int getLimit() {
            return this.index + this.length;
        }

        public int getValue() {
            return this.value;
        }

        public ArgType getArgType() {
            Type type = getType();
            if (type == Type.ARG_START || type == Type.ARG_LIMIT) {
                return MessagePattern.argTypes[this.value];
            }
            return ArgType.NONE;
        }

        public enum Type {
            MSG_START,
            MSG_LIMIT,
            SKIP_SYNTAX,
            INSERT_CHAR,
            REPLACE_NUMBER,
            ARG_START,
            ARG_LIMIT,
            ARG_NUMBER,
            ARG_NAME,
            ARG_TYPE,
            ARG_STYLE,
            ARG_SELECTOR,
            ARG_INT,
            ARG_DOUBLE;

            public boolean hasNumericValue() {
                return this == ARG_INT || this == ARG_DOUBLE;
            }
        }

        public String toString() {
            return this.type.name() + "(" + ((this.type == Type.ARG_START || this.type == Type.ARG_LIMIT) ? getArgType().name() : Integer.toString(this.value)) + ")@" + this.index;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Part part = (Part) obj;
            return this.type.equals(part.type) && this.index == part.index && this.length == part.length && this.value == part.value && this.limitPartIndex == part.limitPartIndex;
        }

        public int hashCode() {
            return (((((this.type.hashCode() * 37) + this.index) * 37) + this.length) * 37) + this.value;
        }
    }

    public enum ArgType {
        NONE,
        SIMPLE,
        CHOICE,
        PLURAL,
        SELECT,
        SELECTORDINAL;

        public boolean hasPluralStyle() {
            return this == PLURAL || this == SELECTORDINAL;
        }
    }

    public Object clone() {
        return isFrozen() ? this : cloneAsThawed();
    }

    @Override // com.ibm.icu.util.Freezable
    public MessagePattern cloneAsThawed() {
        try {
            MessagePattern messagePattern = (MessagePattern) super.clone();
            messagePattern.parts = (ArrayList) this.parts.clone();
            ArrayList<Double> arrayList = this.numericValues;
            if (arrayList != null) {
                messagePattern.numericValues = (ArrayList) arrayList.clone();
            }
            messagePattern.frozen = false;
            return messagePattern;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    @Override // com.ibm.icu.util.Freezable
    public MessagePattern freeze() {
        this.frozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.frozen;
    }

    private void preParse(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to parse(" + prefix(str) + ") on frozen MessagePattern instance.");
        }
        this.msg = str;
        this.hasArgNumbers = false;
        this.hasArgNames = false;
        this.needsAutoQuoting = false;
        this.parts.clear();
        ArrayList<Double> arrayList = this.numericValues;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    private int parseMessage(int i, int i2, int i3, ArgType argType) {
        int iIndexOf;
        if (i3 > 32767) {
            throw new IndexOutOfBoundsException();
        }
        int size = this.parts.size();
        addPart(Part.Type.MSG_START, i, i2, i3);
        int length = i + i2;
        while (length < this.msg.length()) {
            int i4 = length + 1;
            char cCharAt = this.msg.charAt(length);
            if (cCharAt == '\'') {
                if (i4 == this.msg.length()) {
                    addPart(Part.Type.INSERT_CHAR, i4, 0, 39);
                    this.needsAutoQuoting = true;
                } else {
                    char cCharAt2 = this.msg.charAt(i4);
                    if (cCharAt2 == '\'') {
                        addPart(Part.Type.SKIP_SYNTAX, i4, 1, 0);
                        length = i4 + 1;
                    } else if (this.aposMode == ApostropheMode.DOUBLE_REQUIRED || cCharAt2 == '{' || cCharAt2 == '}' || ((argType == ArgType.CHOICE && cCharAt2 == '|') || (argType.hasPluralStyle() && cCharAt2 == '#'))) {
                        addPart(Part.Type.SKIP_SYNTAX, i4 - 1, 1, 0);
                        while (true) {
                            iIndexOf = this.msg.indexOf(39, i4 + 1);
                            if (iIndexOf >= 0) {
                                i4 = iIndexOf + 1;
                                if (i4 >= this.msg.length() || this.msg.charAt(i4) != '\'') {
                                    break;
                                }
                                addPart(Part.Type.SKIP_SYNTAX, i4, 1, 0);
                            } else {
                                length = this.msg.length();
                                addPart(Part.Type.INSERT_CHAR, length, 0, 39);
                                this.needsAutoQuoting = true;
                                break;
                            }
                        }
                        addPart(Part.Type.SKIP_SYNTAX, iIndexOf, 1, 0);
                    } else {
                        addPart(Part.Type.INSERT_CHAR, i4, 0, 39);
                        this.needsAutoQuoting = true;
                    }
                }
                length = i4;
            } else {
                if (argType.hasPluralStyle() && cCharAt == '#') {
                    addPart(Part.Type.REPLACE_NUMBER, i4 - 1, 1, 0);
                } else if (cCharAt == '{') {
                    length = parseArg(i4 - 1, 1, i3);
                } else if ((i3 > 0 && cCharAt == '}') || (argType == ArgType.CHOICE && cCharAt == '|')) {
                    int i5 = (argType == ArgType.CHOICE && cCharAt == '}') ? 0 : 1;
                    int i6 = i4 - 1;
                    addLimitPart(size, Part.Type.MSG_LIMIT, i6, i5, i3);
                    return argType == ArgType.CHOICE ? i6 : i4;
                }
                length = i4;
            }
        }
        if (i3 > 0 && !inTopLevelChoiceMessage(i3, argType)) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
        }
        addLimitPart(size, Part.Type.MSG_LIMIT, length, 0, i3);
        return length;
    }

    private int parseArg(int i, int i2, int i3) {
        char cCharAt;
        int size = this.parts.size();
        ArgType argType = ArgType.NONE;
        addPart(Part.Type.ARG_START, i, i2, argType.ordinal());
        int iSkipWhiteSpace = skipWhiteSpace(i + i2);
        if (iSkipWhiteSpace == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
        }
        int iSkipIdentifier = skipIdentifier(iSkipWhiteSpace);
        int argNumber = parseArgNumber(iSkipWhiteSpace, iSkipIdentifier);
        if (argNumber >= 0) {
            int i4 = iSkipIdentifier - iSkipWhiteSpace;
            if (i4 > 65535 || argNumber > 32767) {
                throw new IndexOutOfBoundsException("Argument number too large: " + prefix(iSkipWhiteSpace));
            }
            this.hasArgNumbers = true;
            addPart(Part.Type.ARG_NUMBER, iSkipWhiteSpace, i4, argNumber);
        } else {
            if (argNumber != -1) {
                throw new IllegalArgumentException("Bad argument syntax: " + prefix(iSkipWhiteSpace));
            }
            int i5 = iSkipIdentifier - iSkipWhiteSpace;
            if (i5 > 65535) {
                throw new IndexOutOfBoundsException("Argument name too long: " + prefix(iSkipWhiteSpace));
            }
            this.hasArgNames = true;
            addPart(Part.Type.ARG_NAME, iSkipWhiteSpace, i5, 0);
        }
        int iSkipWhiteSpace2 = skipWhiteSpace(iSkipIdentifier);
        if (iSkipWhiteSpace2 == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
        }
        char cCharAt2 = this.msg.charAt(iSkipWhiteSpace2);
        if (cCharAt2 != '}') {
            if (cCharAt2 != ',') {
                throw new IllegalArgumentException("Bad argument syntax: " + prefix(iSkipWhiteSpace));
            }
            int iSkipWhiteSpace3 = skipWhiteSpace(iSkipWhiteSpace2 + 1);
            int i6 = iSkipWhiteSpace3;
            while (i6 < this.msg.length() && isArgTypeChar(this.msg.charAt(i6))) {
                i6++;
            }
            int i7 = i6 - iSkipWhiteSpace3;
            int iSkipWhiteSpace4 = skipWhiteSpace(i6);
            if (iSkipWhiteSpace4 == this.msg.length()) {
                throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
            }
            if (i7 == 0 || !((cCharAt = this.msg.charAt(iSkipWhiteSpace4)) == ',' || cCharAt == '}')) {
                throw new IllegalArgumentException("Bad argument syntax: " + prefix(iSkipWhiteSpace));
            }
            if (i7 > 65535) {
                throw new IndexOutOfBoundsException("Argument type name too long: " + prefix(iSkipWhiteSpace));
            }
            argType = ArgType.SIMPLE;
            if (i7 == 6) {
                if (isChoice(iSkipWhiteSpace3)) {
                    argType = ArgType.CHOICE;
                } else if (isPlural(iSkipWhiteSpace3)) {
                    argType = ArgType.PLURAL;
                } else if (isSelect(iSkipWhiteSpace3)) {
                    argType = ArgType.SELECT;
                }
            } else if (i7 == 13 && isSelect(iSkipWhiteSpace3) && isOrdinal(iSkipWhiteSpace3 + 6)) {
                argType = ArgType.SELECTORDINAL;
            }
            this.parts.get(size).value = (short) argType.ordinal();
            if (argType == ArgType.SIMPLE) {
                addPart(Part.Type.ARG_TYPE, iSkipWhiteSpace3, i7, 0);
            }
            if (cCharAt == '}') {
                if (argType != ArgType.SIMPLE) {
                    throw new IllegalArgumentException("No style field for complex argument: " + prefix(iSkipWhiteSpace));
                }
                iSkipWhiteSpace2 = iSkipWhiteSpace4;
            } else {
                int i8 = iSkipWhiteSpace4 + 1;
                if (argType == ArgType.SIMPLE) {
                    iSkipWhiteSpace2 = parseSimpleStyle(i8);
                } else if (argType == ArgType.CHOICE) {
                    iSkipWhiteSpace2 = parseChoiceStyle(i8, i3);
                } else {
                    iSkipWhiteSpace2 = parsePluralOrSelectStyle(argType, i8, i3);
                }
            }
        }
        addLimitPart(size, Part.Type.ARG_LIMIT, iSkipWhiteSpace2, 1, argType.ordinal());
        return iSkipWhiteSpace2 + 1;
    }

    private int parseSimpleStyle(int i) {
        int i2 = i;
        int i3 = 0;
        while (i2 < this.msg.length()) {
            int i4 = i2 + 1;
            char cCharAt = this.msg.charAt(i2);
            if (cCharAt == '\'') {
                int iIndexOf = this.msg.indexOf(39, i4);
                if (iIndexOf < 0) {
                    throw new IllegalArgumentException("Quoted literal argument style text reaches to the end of the message: " + prefix(i));
                }
                i2 = iIndexOf + 1;
            } else {
                if (cCharAt == '{') {
                    i3++;
                } else if (cCharAt == '}') {
                    if (i3 <= 0) {
                        int i5 = i4 - 1;
                        int i6 = i5 - i;
                        if (i6 > 65535) {
                            throw new IndexOutOfBoundsException("Argument style text too long: " + prefix(i));
                        }
                        addPart(Part.Type.ARG_STYLE, i, i6, 0);
                        return i5;
                    }
                    i3--;
                }
                i2 = i4;
            }
        }
        throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
    }

    private int parseChoiceStyle(int i, int i2) {
        int iSkipWhiteSpace = skipWhiteSpace(i);
        if (iSkipWhiteSpace == this.msg.length() || this.msg.charAt(iSkipWhiteSpace) == '}') {
            throw new IllegalArgumentException("Missing choice argument pattern in " + prefix());
        }
        while (true) {
            int iSkipDouble = skipDouble(iSkipWhiteSpace);
            int i3 = iSkipDouble - iSkipWhiteSpace;
            if (i3 == 0) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + prefix(i));
            }
            if (i3 > 65535) {
                throw new IndexOutOfBoundsException("Choice number too long: " + prefix(iSkipWhiteSpace));
            }
            parseDouble(iSkipWhiteSpace, iSkipDouble, true);
            int iSkipWhiteSpace2 = skipWhiteSpace(iSkipDouble);
            if (iSkipWhiteSpace2 == this.msg.length()) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + prefix(i));
            }
            char cCharAt = this.msg.charAt(iSkipWhiteSpace2);
            if (cCharAt != '#' && cCharAt != '<' && cCharAt != 8804) {
                throw new IllegalArgumentException("Expected choice separator (#<≤) instead of '" + cCharAt + "' in choice pattern " + prefix(i));
            }
            addPart(Part.Type.ARG_SELECTOR, iSkipWhiteSpace2, 1, 0);
            int message = parseMessage(iSkipWhiteSpace2 + 1, 0, i2 + 1, ArgType.CHOICE);
            if (message == this.msg.length()) {
                return message;
            }
            if (this.msg.charAt(message) == '}') {
                if (inMessageFormatPattern(i2)) {
                    return message;
                }
                throw new IllegalArgumentException("Bad choice pattern syntax: " + prefix(i));
            }
            iSkipWhiteSpace = skipWhiteSpace(message + 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x01c4, code lost:
    
        if (r5 == inMessageFormatPattern(r15)) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01c6, code lost:
    
        if (r3 == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01c8, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01f5, code lost:
    
        throw new java.lang.IllegalArgumentException("Missing 'other' keyword in " + r13.toString().toLowerCase(java.util.Locale.ENGLISH) + " pattern in " + prefix());
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x021e, code lost:
    
        throw new java.lang.IllegalArgumentException("Bad " + r13.toString().toLowerCase(java.util.Locale.ENGLISH) + " pattern syntax: " + prefix(r14));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int parsePluralOrSelectStyle(com.ibm.icu.text.MessagePattern.ArgType r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 543
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.MessagePattern.parsePluralOrSelectStyle(com.ibm.icu.text.MessagePattern$ArgType, int, int):int");
    }

    private static int parseArgNumber(CharSequence charSequence, int i, int i2) {
        int i3;
        if (i >= i2) {
            return -2;
        }
        int i4 = i + 1;
        char cCharAt = charSequence.charAt(i);
        boolean z = false;
        if (cCharAt == '0') {
            if (i4 == i2) {
                return 0;
            }
            i3 = 0;
            z = true;
        } else {
            if ('1' > cCharAt || cCharAt > '9') {
                return -1;
            }
            i3 = cCharAt - '0';
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            char cCharAt2 = charSequence.charAt(i4);
            if ('0' > cCharAt2 || cCharAt2 > '9') {
                return -1;
            }
            if (i3 >= 214748364) {
                z = true;
            }
            i3 = (i3 * 10) + (cCharAt2 - '0');
            i4 = i5;
        }
        if (z) {
            return -2;
        }
        return i3;
    }

    private int parseArgNumber(int i, int i2) {
        return parseArgNumber(this.msg, i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005c A[LOOP:0: B:22:0x005c->B:35:0x007a, LOOP_START, PHI: r0 r1 r3
      0x005c: PHI (r0v7 char) = (r0v6 char), (r0v11 char) binds: [B:12:0x002d, B:35:0x007a] A[DONT_GENERATE, DONT_INLINE]
      0x005c: PHI (r1v6 int) = (r1v5 int), (r1v7 int) binds: [B:12:0x002d, B:35:0x007a] A[DONT_GENERATE, DONT_INLINE]
      0x005c: PHI (r3v1 int) = (r3v0 int), (r3v3 int) binds: [B:12:0x002d, B:35:0x007a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void parseDouble(int r6, int r7, boolean r8) {
        /*
            r5 = this;
            java.lang.String r0 = r5.msg
            int r1 = r6 + 1
            char r0 = r0.charAt(r6)
            r2 = 45
            r3 = 0
            if (r0 != r2) goto L1b
            if (r1 == r7) goto L3f
            java.lang.String r0 = r5.msg
            int r2 = r1 + 1
            char r0 = r0.charAt(r1)
            r1 = 1
            r1 = r2
            r2 = 1
            goto L2b
        L1b:
            r2 = 43
            if (r0 != r2) goto L2a
            if (r1 == r7) goto L3f
            java.lang.String r0 = r5.msg
            int r2 = r1 + 1
            char r0 = r0.charAt(r1)
            r1 = r2
        L2a:
            r2 = 0
        L2b:
            r4 = 8734(0x221e, float:1.2239E-41)
            if (r0 != r4) goto L5c
            if (r8 == 0) goto L3f
            if (r1 != r7) goto L3f
            if (r2 == 0) goto L38
            r0 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            goto L3a
        L38:
            r0 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
        L3a:
            int r7 = r7 - r6
            r5.addArgDoublePart(r0, r6, r7)
            return
        L3f:
            java.lang.NumberFormatException r8 = new java.lang.NumberFormatException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Bad syntax for numeric value: "
            r0.append(r1)
            java.lang.String r1 = r5.msg
            java.lang.String r6 = r1.substring(r6, r7)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            r8.<init>(r6)
            throw r8
        L5c:
            r8 = 48
            if (r8 > r0) goto L85
            r8 = 57
            if (r0 > r8) goto L85
            int r3 = r3 * 10
            int r0 = r0 + (-48)
            int r3 = r3 + r0
            int r8 = r2 + 32767
            if (r3 <= r8) goto L6e
            goto L85
        L6e:
            if (r1 != r7) goto L7a
            com.ibm.icu.text.MessagePattern$Part$Type r8 = com.ibm.icu.text.MessagePattern.Part.Type.ARG_INT
            int r7 = r7 - r6
            if (r2 == 0) goto L76
            int r3 = -r3
        L76:
            r5.addPart(r8, r6, r7, r3)
            return
        L7a:
            java.lang.String r8 = r5.msg
            int r0 = r1 + 1
            char r8 = r8.charAt(r1)
            r1 = r0
            r0 = r8
            goto L5c
        L85:
            java.lang.String r8 = r5.msg
            java.lang.String r8 = r8.substring(r6, r7)
            double r0 = java.lang.Double.parseDouble(r8)
            int r7 = r7 - r6
            r5.addArgDoublePart(r0, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.MessagePattern.parseDouble(int, int, boolean):void");
    }

    static void appendReducedApostrophes(String str, int i, int i2, StringBuilder sb) {
        loop0: while (true) {
            int i3 = -1;
            while (true) {
                int iIndexOf = str.indexOf(39, i);
                if (iIndexOf < 0 || iIndexOf >= i2) {
                    break loop0;
                }
                if (iIndexOf == i3) {
                    break;
                }
                sb.append((CharSequence) str, i, iIndexOf);
                i = iIndexOf + 1;
                i3 = i;
            }
            sb.append(PatternTokenizer.SINGLE_QUOTE);
            i++;
        }
        sb.append((CharSequence) str, i, i2);
    }

    private int skipWhiteSpace(int i) {
        return PatternProps.skipWhiteSpace(this.msg, i);
    }

    private int skipIdentifier(int i) {
        return PatternProps.skipIdentifier(this.msg, i);
    }

    private int skipDouble(int i) {
        char cCharAt;
        while (i < this.msg.length() && (((cCharAt = this.msg.charAt(i)) >= '0' || "+-.".indexOf(cCharAt) >= 0) && (cCharAt <= '9' || cCharAt == 'e' || cCharAt == 'E' || cCharAt == 8734))) {
            i++;
        }
        return i;
    }

    private boolean isChoice(int i) {
        char cCharAt;
        int i2 = i + 1;
        char cCharAt2 = this.msg.charAt(i);
        if (cCharAt2 == 'c' || cCharAt2 == 'C') {
            int i3 = i2 + 1;
            char cCharAt3 = this.msg.charAt(i2);
            if (cCharAt3 == 'h' || cCharAt3 == 'H') {
                int i4 = i3 + 1;
                char cCharAt4 = this.msg.charAt(i3);
                if (cCharAt4 == 'o' || cCharAt4 == 'O') {
                    int i5 = i4 + 1;
                    char cCharAt5 = this.msg.charAt(i4);
                    if (cCharAt5 == 'i' || cCharAt5 == 'I') {
                        int i6 = i5 + 1;
                        char cCharAt6 = this.msg.charAt(i5);
                        if ((cCharAt6 == 'c' || cCharAt6 == 'C') && ((cCharAt = this.msg.charAt(i6)) == 'e' || cCharAt == 'E')) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isPlural(int i) {
        char cCharAt;
        int i2 = i + 1;
        char cCharAt2 = this.msg.charAt(i);
        if (cCharAt2 == 'p' || cCharAt2 == 'P') {
            int i3 = i2 + 1;
            char cCharAt3 = this.msg.charAt(i2);
            if (cCharAt3 == 'l' || cCharAt3 == 'L') {
                int i4 = i3 + 1;
                char cCharAt4 = this.msg.charAt(i3);
                if (cCharAt4 == 'u' || cCharAt4 == 'U') {
                    int i5 = i4 + 1;
                    char cCharAt5 = this.msg.charAt(i4);
                    if (cCharAt5 == 'r' || cCharAt5 == 'R') {
                        int i6 = i5 + 1;
                        char cCharAt6 = this.msg.charAt(i5);
                        if ((cCharAt6 == 'a' || cCharAt6 == 'A') && ((cCharAt = this.msg.charAt(i6)) == 'l' || cCharAt == 'L')) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isSelect(int i) {
        char cCharAt;
        int i2 = i + 1;
        char cCharAt2 = this.msg.charAt(i);
        if (cCharAt2 == 's' || cCharAt2 == 'S') {
            int i3 = i2 + 1;
            char cCharAt3 = this.msg.charAt(i2);
            if (cCharAt3 == 'e' || cCharAt3 == 'E') {
                int i4 = i3 + 1;
                char cCharAt4 = this.msg.charAt(i3);
                if (cCharAt4 == 'l' || cCharAt4 == 'L') {
                    int i5 = i4 + 1;
                    char cCharAt5 = this.msg.charAt(i4);
                    if (cCharAt5 == 'e' || cCharAt5 == 'E') {
                        int i6 = i5 + 1;
                        char cCharAt6 = this.msg.charAt(i5);
                        if ((cCharAt6 == 'c' || cCharAt6 == 'C') && ((cCharAt = this.msg.charAt(i6)) == 't' || cCharAt == 'T')) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isOrdinal(int i) {
        char cCharAt;
        int i2 = i + 1;
        char cCharAt2 = this.msg.charAt(i);
        if (cCharAt2 == 'o' || cCharAt2 == 'O') {
            int i3 = i2 + 1;
            char cCharAt3 = this.msg.charAt(i2);
            if (cCharAt3 == 'r' || cCharAt3 == 'R') {
                int i4 = i3 + 1;
                char cCharAt4 = this.msg.charAt(i3);
                if (cCharAt4 == 'd' || cCharAt4 == 'D') {
                    int i5 = i4 + 1;
                    char cCharAt5 = this.msg.charAt(i4);
                    if (cCharAt5 == 'i' || cCharAt5 == 'I') {
                        int i6 = i5 + 1;
                        char cCharAt6 = this.msg.charAt(i5);
                        if (cCharAt6 == 'n' || cCharAt6 == 'N') {
                            int i7 = i6 + 1;
                            char cCharAt7 = this.msg.charAt(i6);
                            if ((cCharAt7 == 'a' || cCharAt7 == 'A') && ((cCharAt = this.msg.charAt(i7)) == 'l' || cCharAt == 'L')) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean inMessageFormatPattern(int i) {
        return i > 0 || this.parts.get(0).type == Part.Type.MSG_START;
    }

    private boolean inTopLevelChoiceMessage(int i, ArgType argType) {
        return i == 1 && argType == ArgType.CHOICE && this.parts.get(0).type != Part.Type.MSG_START;
    }

    private void addPart(Part.Type type, int i, int i2, int i3) {
        this.parts.add(new Part(type, i, i2, i3));
    }

    private void addLimitPart(int i, Part.Type type, int i2, int i3, int i4) {
        this.parts.get(i).limitPartIndex = this.parts.size();
        addPart(type, i2, i3, i4);
    }

    private void addArgDoublePart(double d, int i, int i2) {
        int size;
        ArrayList<Double> arrayList = this.numericValues;
        if (arrayList == null) {
            this.numericValues = new ArrayList<>();
            size = 0;
        } else {
            size = arrayList.size();
            if (size > 32767) {
                throw new IndexOutOfBoundsException("Too many numeric values");
            }
        }
        this.numericValues.add(Double.valueOf(d));
        addPart(Part.Type.ARG_DOUBLE, i, i2, size);
    }

    private static String prefix(String str, int i) {
        StringBuilder sb = new StringBuilder(44);
        if (i == 0) {
            sb.append("\"");
        } else {
            sb.append("[at pattern index ");
            sb.append(i);
            sb.append("] \"");
        }
        if (str.length() - i <= 24) {
            if (i != 0) {
                str = str.substring(i);
            }
            sb.append(str);
        } else {
            int i2 = (i + 24) - 4;
            if (Character.isHighSurrogate(str.charAt(i2 - 1))) {
                i2--;
            }
            sb.append((CharSequence) str, i, i2);
            sb.append(" ...");
        }
        sb.append("\"");
        return sb.toString();
    }

    private static String prefix(String str) {
        return prefix(str, 0);
    }

    private String prefix(int i) {
        return prefix(this.msg, i);
    }

    private String prefix() {
        return prefix(this.msg, 0);
    }
}
