package com.ibm.icu.impl.coll;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class CollationRuleParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String BEFORE = "[before";
    private static final int OFFSET_SHIFT = 8;
    static final char POS_BASE = 10240;
    static final char POS_LEAD = 65534;
    private static final int STARRED_FLAG = 16;
    private static final int STRENGTH_MASK = 15;
    private static final int UCOL_DEFAULT = -1;
    private static final int UCOL_OFF = 0;
    private static final int UCOL_ON = 1;
    private static final int U_PARSE_CONTEXT_LEN = 16;
    private final CollationData baseData;
    private Importer importer;
    private int ruleIndex;
    private String rules;
    private CollationSettings settings;
    private Sink sink;
    static final Position[] POSITION_VALUES = Position.values();
    private static final String[] positions = {"first tertiary ignorable", "last tertiary ignorable", "first secondary ignorable", "last secondary ignorable", "first primary ignorable", "last primary ignorable", "first variable", "last variable", "first regular", "last regular", "first implicit", "last implicit", "first trailing", "last trailing"};
    private static final String[] gSpecialReorderCodes = {"space", "punct", "symbol", FirebaseAnalytics.Param.CURRENCY, "digit"};
    private final StringBuilder rawBuilder = new StringBuilder();
    private Normalizer2 nfd = Normalizer2.getNFDInstance();
    private Normalizer2 nfc = Normalizer2.getNFCInstance();

    interface Importer {
        String getRules(String str, String str2);
    }

    enum Position {
        FIRST_TERTIARY_IGNORABLE,
        LAST_TERTIARY_IGNORABLE,
        FIRST_SECONDARY_IGNORABLE,
        LAST_SECONDARY_IGNORABLE,
        FIRST_PRIMARY_IGNORABLE,
        LAST_PRIMARY_IGNORABLE,
        FIRST_VARIABLE,
        LAST_VARIABLE,
        FIRST_REGULAR,
        LAST_REGULAR,
        FIRST_IMPLICIT,
        LAST_IMPLICIT,
        FIRST_TRAILING,
        LAST_TRAILING
    }

    private static final boolean isSurrogate(int i) {
        return (i & (-2048)) == 55296;
    }

    private static boolean isSyntaxChar(int i) {
        return 33 <= i && i <= 126 && (i <= 47 || ((58 <= i && i <= 64) || ((91 <= i && i <= 96) || 123 <= i)));
    }

    static abstract class Sink {
        abstract void addRelation(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3);

        abstract void addReset(int i, CharSequence charSequence);

        void optimize(UnicodeSet unicodeSet) {
        }

        void suppressContractions(UnicodeSet unicodeSet) {
        }

        Sink() {
        }
    }

    CollationRuleParser(CollationData collationData) {
        this.baseData = collationData;
    }

    void setSink(Sink sink) {
        this.sink = sink;
    }

    void setImporter(Importer importer) {
        this.importer = importer;
    }

    void parse(String str, CollationSettings collationSettings) throws ParseException {
        this.settings = collationSettings;
        parse(str);
    }

    private void parse(String str) throws ParseException {
        this.rules = str;
        this.ruleIndex = 0;
        while (this.ruleIndex < this.rules.length()) {
            char cCharAt = this.rules.charAt(this.ruleIndex);
            if (PatternProps.isWhiteSpace(cCharAt)) {
                this.ruleIndex++;
            } else if (cCharAt == '!') {
                this.ruleIndex++;
            } else if (cCharAt == '#') {
                this.ruleIndex = skipComment(this.ruleIndex + 1);
            } else if (cCharAt == '&') {
                parseRuleChain();
            } else if (cCharAt == '@') {
                this.settings.setFlag(2048, true);
                this.ruleIndex++;
            } else if (cCharAt == '[') {
                parseSetting();
            } else {
                setParseError("expected a reset or setting or comment");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002c, code lost:
    
        if (r2 == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x002e, code lost:
    
        setParseError("reset not followed by a relation");
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0033, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void parseRuleChain() throws java.text.ParseException {
        /*
            r6 = this;
            int r0 = r6.parseResetAndPosition()
            r1 = 1
            r2 = 1
        L6:
            int r3 = r6.parseRelationOperator()
            if (r3 >= 0) goto L34
            int r3 = r6.ruleIndex
            java.lang.String r4 = r6.rules
            int r4 = r4.length()
            if (r3 >= r4) goto L2c
            java.lang.String r3 = r6.rules
            int r4 = r6.ruleIndex
            char r3 = r3.charAt(r4)
            r4 = 35
            if (r3 != r4) goto L2c
            int r3 = r6.ruleIndex
            int r3 = r3 + r1
            int r3 = r6.skipComment(r3)
            r6.ruleIndex = r3
            goto L6
        L2c:
            if (r2 == 0) goto L33
            java.lang.String r0 = "reset not followed by a relation"
            r6.setParseError(r0)
        L33:
            return
        L34:
            r4 = r3 & 15
            r5 = 15
            if (r0 >= r5) goto L4c
            if (r2 == 0) goto L44
            if (r4 == r0) goto L4c
            java.lang.String r0 = "reset-before strength differs from its first relation"
            r6.setParseError(r0)
            return
        L44:
            if (r4 >= r0) goto L4c
            java.lang.String r0 = "reset-before strength followed by a stronger relation"
            r6.setParseError(r0)
            return
        L4c:
            int r2 = r6.ruleIndex
            int r5 = r3 >> 8
            int r2 = r2 + r5
            r3 = r3 & 16
            if (r3 != 0) goto L59
            r6.parseRelationStrings(r4, r2)
            goto L5c
        L59:
            r6.parseStarredCharacters(r4, r2)
        L5c:
            r2 = 0
            goto L6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationRuleParser.parseRuleChain():void");
    }

    private int parseResetAndPosition() throws ParseException {
        int i;
        int tailoringString;
        int i2;
        int iSkipWhiteSpace;
        int iSkipWhiteSpace2;
        char cCharAt;
        int iSkipWhiteSpace3 = skipWhiteSpace(this.ruleIndex + 1);
        if (!this.rules.regionMatches(iSkipWhiteSpace3, BEFORE, 0, 7) || (i2 = iSkipWhiteSpace3 + 7) >= this.rules.length() || !PatternProps.isWhiteSpace(this.rules.charAt(i2)) || (iSkipWhiteSpace2 = (iSkipWhiteSpace = skipWhiteSpace(i2 + 1)) + 1) >= this.rules.length() || '1' > (cCharAt = this.rules.charAt(iSkipWhiteSpace)) || cCharAt > '3' || this.rules.charAt(iSkipWhiteSpace2) != ']') {
            i = 15;
        } else {
            i = (cCharAt - '1') + 0;
            iSkipWhiteSpace3 = skipWhiteSpace(iSkipWhiteSpace + 2);
        }
        if (iSkipWhiteSpace3 >= this.rules.length()) {
            setParseError("reset without position");
            return -1;
        }
        if (this.rules.charAt(iSkipWhiteSpace3) == '[') {
            tailoringString = parseSpecialPosition(iSkipWhiteSpace3, this.rawBuilder);
        } else {
            tailoringString = parseTailoringString(iSkipWhiteSpace3, this.rawBuilder);
        }
        try {
            this.sink.addReset(i, this.rawBuilder);
            this.ruleIndex = tailoringString;
            return i;
        } catch (Exception e) {
            setParseError("adding reset failed", e);
            return -1;
        }
    }

    private int parseRelationOperator() {
        int iSkipWhiteSpace = skipWhiteSpace(this.ruleIndex);
        this.ruleIndex = iSkipWhiteSpace;
        if (iSkipWhiteSpace >= this.rules.length()) {
            return -1;
        }
        int i = this.ruleIndex;
        int i2 = i + 1;
        char cCharAt = this.rules.charAt(i);
        int i3 = 2;
        if (cCharAt != ',') {
            switch (cCharAt) {
                case ';':
                    i3 = 1;
                    break;
                case '<':
                    if (i2 >= this.rules.length() || this.rules.charAt(i2) != '<') {
                        i3 = 0;
                    } else {
                        i2++;
                        if (i2 >= this.rules.length() || this.rules.charAt(i2) != '<') {
                            i3 = 1;
                        } else {
                            i2++;
                            if (i2 < this.rules.length() && this.rules.charAt(i2) == '<') {
                                i2++;
                                i3 = 3;
                            }
                        }
                    }
                    if (i2 < this.rules.length() && this.rules.charAt(i2) == '*') {
                        i2++;
                        i3 |= 16;
                        break;
                    }
                    break;
                case '=':
                    i3 = 15;
                    if (i2 < this.rules.length() && this.rules.charAt(i2) == '*') {
                        i2++;
                        i3 = 31;
                        break;
                    }
                    break;
                default:
                    return -1;
            }
        }
        return ((i2 - this.ruleIndex) << 8) | i3;
    }

    private void parseRelationStrings(int i, int i2) throws ParseException {
        String str;
        int tailoringString = parseTailoringString(i2, this.rawBuilder);
        char cCharAt = tailoringString < this.rules.length() ? this.rules.charAt(tailoringString) : (char) 0;
        CharSequence charSequence = "";
        if (cCharAt == '|') {
            String string = this.rawBuilder.toString();
            tailoringString = parseTailoringString(tailoringString + 1, this.rawBuilder);
            str = string;
            cCharAt = tailoringString < this.rules.length() ? this.rules.charAt(tailoringString) : (char) 0;
        } else {
            str = "";
        }
        CharSequence charSequence2 = charSequence;
        if (cCharAt == '/') {
            StringBuilder sb = new StringBuilder();
            tailoringString = parseTailoringString(tailoringString + 1, sb);
            charSequence2 = sb;
        }
        if (str.length() != 0) {
            int iCodePointAt = str.codePointAt(0);
            int iCodePointAt2 = this.rawBuilder.codePointAt(0);
            if (!this.nfc.hasBoundaryBefore(iCodePointAt) || !this.nfc.hasBoundaryBefore(iCodePointAt2)) {
                setParseError("in 'prefix|str', prefix and str must each start with an NFC boundary");
                return;
            }
        }
        try {
            this.sink.addRelation(i, str, this.rawBuilder, charSequence2);
            this.ruleIndex = tailoringString;
        } catch (Exception e) {
            setParseError("adding relation failed", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d6, code lost:
    
        r8.ruleIndex = skipWhiteSpace(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00dc, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void parseStarredCharacters(int r9, int r10) throws java.text.ParseException {
        /*
            r8 = this;
            int r10 = r8.skipWhiteSpace(r10)
            java.lang.StringBuilder r0 = r8.rawBuilder
            int r10 = r8.parseString(r10, r0)
            java.lang.StringBuilder r0 = r8.rawBuilder
            int r0 = r0.length()
            if (r0 != 0) goto L18
            java.lang.String r9 = "missing starred-relation string"
            r8.setParseError(r9)
            return
        L18:
            r0 = 0
            r1 = -1
            r2 = 0
        L1b:
            r3 = -1
        L1c:
            java.lang.StringBuilder r4 = r8.rawBuilder
            int r4 = r4.length()
            java.lang.String r5 = "adding relation failed"
            java.lang.String r6 = ""
            if (r2 >= r4) goto L51
            java.lang.StringBuilder r3 = r8.rawBuilder
            int r3 = r3.codePointAt(r2)
            com.ibm.icu.text.Normalizer2 r4 = r8.nfd
            boolean r4 = r4.isInert(r3)
            if (r4 != 0) goto L3d
            java.lang.String r9 = "starred-relation string is not all NFD-inert"
            r8.setParseError(r9)
            return
        L3d:
            com.ibm.icu.impl.coll.CollationRuleParser$Sink r4 = r8.sink     // Catch: java.lang.Exception -> L4c
            java.lang.String r7 = com.ibm.icu.text.UTF16.valueOf(r3)     // Catch: java.lang.Exception -> L4c
            r4.addRelation(r9, r6, r7, r6)     // Catch: java.lang.Exception -> L4c
            int r4 = java.lang.Character.charCount(r3)
            int r2 = r2 + r4
            goto L1c
        L4c:
            r9 = move-exception
            r8.setParseError(r5, r9)
            return
        L51:
            java.lang.String r2 = r8.rules
            int r2 = r2.length()
            if (r10 >= r2) goto Ld6
            java.lang.String r2 = r8.rules
            char r2 = r2.charAt(r10)
            r4 = 45
            if (r2 == r4) goto L64
            goto Ld6
        L64:
            if (r3 >= 0) goto L6c
            java.lang.String r9 = "range without start in starred-relation string"
            r8.setParseError(r9)
            return
        L6c:
            int r10 = r10 + 1
            java.lang.StringBuilder r2 = r8.rawBuilder
            int r10 = r8.parseString(r10, r2)
            java.lang.StringBuilder r2 = r8.rawBuilder
            int r2 = r2.length()
            if (r2 != 0) goto L82
            java.lang.String r9 = "range without end in starred-relation string"
            r8.setParseError(r9)
            return
        L82:
            java.lang.StringBuilder r2 = r8.rawBuilder
            int r2 = r2.codePointAt(r0)
            if (r2 >= r3) goto L90
            java.lang.String r9 = "range start greater than end in starred-relation string"
            r8.setParseError(r9)
            return
        L90:
            int r3 = r3 + 1
            if (r3 > r2) goto Ld0
            com.ibm.icu.text.Normalizer2 r4 = r8.nfd
            boolean r4 = r4.isInert(r3)
            if (r4 != 0) goto La3
            java.lang.String r9 = "starred-relation string range is not all NFD-inert"
            r8.setParseError(r9)
            return
        La3:
            boolean r4 = isSurrogate(r3)
            if (r4 == 0) goto Lb0
            java.lang.String r9 = "starred-relation string range contains a surrogate"
            r8.setParseError(r9)
            return
        Lb0:
            r4 = 65533(0xfffd, float:9.1831E-41)
            if (r4 > r3) goto Lc1
            r4 = 65535(0xffff, float:9.1834E-41)
            if (r3 > r4) goto Lc1
            java.lang.String r9 = "starred-relation string range contains U+FFFD, U+FFFE or U+FFFF"
            r8.setParseError(r9)
            return
        Lc1:
            com.ibm.icu.impl.coll.CollationRuleParser$Sink r4 = r8.sink     // Catch: java.lang.Exception -> Lcb
            java.lang.String r7 = com.ibm.icu.text.UTF16.valueOf(r3)     // Catch: java.lang.Exception -> Lcb
            r4.addRelation(r9, r6, r7, r6)     // Catch: java.lang.Exception -> Lcb
            goto L90
        Lcb:
            r9 = move-exception
            r8.setParseError(r5, r9)
            return
        Ld0:
            int r2 = java.lang.Character.charCount(r2)
            goto L1b
        Ld6:
            int r9 = r8.skipWhiteSpace(r10)
            r8.ruleIndex = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationRuleParser.parseStarredCharacters(int, int):void");
    }

    private int parseTailoringString(int i, StringBuilder sb) throws ParseException {
        int string = parseString(skipWhiteSpace(i), sb);
        if (sb.length() == 0) {
            setParseError("missing relation string");
        }
        return skipWhiteSpace(string);
    }

    private int parseString(int i, StringBuilder sb) throws ParseException {
        int iCharCount = 0;
        sb.setLength(0);
        while (i < this.rules.length()) {
            int iCharCount2 = i + 1;
            char cCharAt = this.rules.charAt(i);
            if (isSyntaxChar(cCharAt)) {
                if (cCharAt != '\'') {
                    if (cCharAt != '\\') {
                        i = iCharCount2 - 1;
                        break;
                    }
                    if (iCharCount2 == this.rules.length()) {
                        setParseError("backslash escape at the end of the rule string");
                        return iCharCount2;
                    }
                    int iCodePointAt = this.rules.codePointAt(iCharCount2);
                    sb.appendCodePoint(iCodePointAt);
                    iCharCount2 += Character.charCount(iCodePointAt);
                    i = iCharCount2;
                } else if (iCharCount2 < this.rules.length() && this.rules.charAt(iCharCount2) == '\'') {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    i = iCharCount2 + 1;
                } else {
                    while (iCharCount2 != this.rules.length()) {
                        int i2 = iCharCount2 + 1;
                        char cCharAt2 = this.rules.charAt(iCharCount2);
                        if (cCharAt2 == '\'') {
                            if (i2 >= this.rules.length() || this.rules.charAt(i2) != '\'') {
                                i = i2;
                            } else {
                                i2++;
                            }
                        }
                        iCharCount2 = i2;
                        sb.append(cCharAt2);
                    }
                    setParseError("quoted literal text missing terminating apostrophe");
                    return iCharCount2;
                }
            } else {
                if (PatternProps.isWhiteSpace(cCharAt)) {
                    i = iCharCount2 - 1;
                    break;
                }
                sb.append(cCharAt);
                i = iCharCount2;
            }
        }
        while (iCharCount < sb.length()) {
            int iCodePointAt2 = sb.codePointAt(iCharCount);
            if (isSurrogate(iCodePointAt2)) {
                setParseError("string contains an unpaired surrogate");
                return i;
            }
            if (65533 <= iCodePointAt2 && iCodePointAt2 <= 65535) {
                setParseError("string contains U+FFFD, U+FFFE or U+FFFF");
                return i;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return i;
    }

    private int parseSpecialPosition(int i, StringBuilder sb) throws ParseException {
        int words = readWords(i + 1, this.rawBuilder);
        if (words > i && this.rules.charAt(words) == ']' && this.rawBuilder.length() != 0) {
            int i2 = words + 1;
            String string = this.rawBuilder.toString();
            int i3 = 0;
            sb.setLength(0);
            while (true) {
                String[] strArr = positions;
                if (i3 < strArr.length) {
                    if (string.equals(strArr[i3])) {
                        sb.append(POS_LEAD);
                        sb.append((char) (i3 + 10240));
                        return i2;
                    }
                    i3++;
                } else {
                    if (string.equals("top")) {
                        sb.append(POS_LEAD);
                        sb.append((char) (Position.LAST_REGULAR.ordinal() + 10240));
                        return i2;
                    }
                    if (string.equals("variable top")) {
                        sb.append(POS_LEAD);
                        sb.append((char) (Position.LAST_VARIABLE.ordinal() + 10240));
                        return i2;
                    }
                }
            }
        }
        setParseError("not a valid special reset position");
        return i;
    }

    private void parseSetting() throws ParseException {
        String strSubstring;
        char c;
        int i = 1;
        int i2 = this.ruleIndex + 1;
        int words = readWords(i2, this.rawBuilder);
        if (words <= i2 || this.rawBuilder.length() == 0) {
            setParseError("expected a setting/option at '['");
        }
        String string = this.rawBuilder.toString();
        if (this.rules.charAt(words) == ']') {
            int i3 = words + 1;
            if (string.startsWith("reorder") && (string.length() == 7 || string.charAt(7) == ' ')) {
                parseReordering(string);
                this.ruleIndex = i3;
                return;
            }
            if (string.equals("backwards 2")) {
                this.settings.setFlag(2048, true);
                this.ruleIndex = i3;
                return;
            }
            int iLastIndexOf = string.lastIndexOf(32);
            int i4 = 0;
            if (iLastIndexOf >= 0) {
                strSubstring = string.substring(iLastIndexOf + 1);
                string = string.substring(0, iLastIndexOf);
            } else {
                strSubstring = "";
            }
            if (string.equals("strength") && strSubstring.length() == 1) {
                char cCharAt = strSubstring.charAt(0);
                int i5 = ('1' > cCharAt || cCharAt > '4') ? cCharAt == 'I' ? 15 : -1 : (cCharAt - '1') + 0;
                if (i5 != -1) {
                    this.settings.setStrength(i5);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("alternate")) {
                if (strSubstring.equals("non-ignorable")) {
                    c = 0;
                } else {
                    c = strSubstring.equals("shifted") ? (char) 1 : (char) 65535;
                }
                if (c != 65535) {
                    this.settings.setAlternateHandlingShifted(c > 0);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("maxVariable")) {
                if (strSubstring.equals("space")) {
                    i = 0;
                } else if (!strSubstring.equals("punct")) {
                    if (strSubstring.equals("symbol")) {
                        i = 2;
                    } else {
                        i = strSubstring.equals(FirebaseAnalytics.Param.CURRENCY) ? 3 : -1;
                    }
                }
                if (i != -1) {
                    this.settings.setMaxVariable(i, 0);
                    this.settings.variableTop = this.baseData.getLastPrimaryForGroup(i + 4096);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("caseFirst")) {
                if (!strSubstring.equals("off")) {
                    if (strSubstring.equals("lower")) {
                        i4 = 512;
                    } else {
                        i4 = strSubstring.equals("upper") ? 768 : -1;
                    }
                }
                if (i4 != -1) {
                    this.settings.setCaseFirst(i4);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("caseLevel")) {
                int onOffValue = getOnOffValue(strSubstring);
                if (onOffValue != -1) {
                    this.settings.setFlag(1024, onOffValue > 0);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("normalization")) {
                int onOffValue2 = getOnOffValue(strSubstring);
                if (onOffValue2 != -1) {
                    this.settings.setFlag(1, onOffValue2 > 0);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("numericOrdering")) {
                int onOffValue3 = getOnOffValue(strSubstring);
                if (onOffValue3 != -1) {
                    this.settings.setFlag(2, onOffValue3 > 0);
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("hiraganaQ")) {
                int onOffValue4 = getOnOffValue(strSubstring);
                if (onOffValue4 != -1) {
                    if (onOffValue4 == 1) {
                        setParseError("[hiraganaQ on] is not supported");
                    }
                    this.ruleIndex = i3;
                    return;
                }
            } else if (string.equals("import")) {
                try {
                    ULocale uLocaleBuild = new ULocale.Builder().setLanguageTag(strSubstring).build();
                    String baseName = uLocaleBuild.getBaseName();
                    String keywordValue = uLocaleBuild.getKeywordValue("collation");
                    Importer importer = this.importer;
                    if (importer == null) {
                        setParseError("[import langTag] is not supported");
                        return;
                    }
                    if (keywordValue == null) {
                        keywordValue = CookieSpecs.STANDARD;
                    }
                    try {
                        String rules = importer.getRules(baseName, keywordValue);
                        String str = this.rules;
                        int i6 = this.ruleIndex;
                        try {
                            parse(rules);
                        } catch (Exception e) {
                            this.ruleIndex = i6;
                            setParseError("parsing imported rules failed", e);
                        }
                        this.rules = str;
                        this.ruleIndex = i3;
                        return;
                    } catch (Exception e2) {
                        setParseError("[import langTag] failed", e2);
                        return;
                    }
                } catch (Exception e3) {
                    setParseError("expected language tag in [import langTag]", e3);
                    return;
                }
            }
        } else if (this.rules.charAt(words) == '[') {
            UnicodeSet unicodeSet = new UnicodeSet();
            int unicodeSet2 = parseUnicodeSet(words, unicodeSet);
            if (string.equals("optimize")) {
                try {
                    this.sink.optimize(unicodeSet);
                } catch (Exception e4) {
                    setParseError("[optimize set] failed", e4);
                }
                this.ruleIndex = unicodeSet2;
                return;
            }
            if (string.equals("suppressContractions")) {
                try {
                    this.sink.suppressContractions(unicodeSet);
                } catch (Exception e5) {
                    setParseError("[suppressContractions set] failed", e5);
                }
                this.ruleIndex = unicodeSet2;
                return;
            }
        }
        setParseError("not a valid setting/option");
    }

    private void parseReordering(CharSequence charSequence) throws ParseException {
        int i;
        if (7 == charSequence.length()) {
            this.settings.resetReordering();
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 7; i2 < charSequence.length(); i2 = i) {
            int i3 = i2 + 1;
            i = i3;
            while (i < charSequence.length() && charSequence.charAt(i) != ' ') {
                i++;
            }
            int reorderCode = getReorderCode(charSequence.subSequence(i3, i).toString());
            if (reorderCode < 0) {
                setParseError("unknown script or reorder code");
                return;
            }
            arrayList.add(Integer.valueOf(reorderCode));
        }
        if (arrayList.isEmpty()) {
            this.settings.resetReordering();
            return;
        }
        int[] iArr = new int[arrayList.size()];
        int i4 = 0;
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            iArr[i4] = ((Integer) it2.next()).intValue();
            i4++;
        }
        this.settings.setReordering(this.baseData, iArr);
    }

    public static int getReorderCode(String str) {
        int i = 0;
        while (true) {
            String[] strArr = gSpecialReorderCodes;
            if (i < strArr.length) {
                if (str.equalsIgnoreCase(strArr[i])) {
                    return i + 4096;
                }
                i++;
            } else {
                try {
                    int propertyValueEnum = UCharacter.getPropertyValueEnum(4106, str);
                    if (propertyValueEnum >= 0) {
                        return propertyValueEnum;
                    }
                } catch (IllegalIcuArgumentException unused) {
                }
                return str.equalsIgnoreCase("others") ? 103 : -1;
            }
        }
    }

    private static int getOnOffValue(String str) {
        if (str.equals("on")) {
            return 1;
        }
        return str.equals("off") ? 0 : -1;
    }

    private int parseUnicodeSet(int i, UnicodeSet unicodeSet) throws ParseException {
        int i2 = 0;
        int i3 = i;
        while (i3 != this.rules.length()) {
            int i4 = i3 + 1;
            char cCharAt = this.rules.charAt(i3);
            if (cCharAt == '[') {
                i2++;
            } else if (cCharAt == ']' && i2 - 1 == 0) {
                try {
                    unicodeSet.applyPattern(this.rules.substring(i, i4));
                } catch (Exception e) {
                    setParseError("not a valid UnicodeSet pattern: " + e.getMessage());
                }
                int iSkipWhiteSpace = skipWhiteSpace(i4);
                if (iSkipWhiteSpace != this.rules.length() && this.rules.charAt(iSkipWhiteSpace) == ']') {
                    return iSkipWhiteSpace + 1;
                }
                setParseError("missing option-terminating ']' after UnicodeSet pattern");
                return iSkipWhiteSpace;
            }
            i3 = i4;
        }
        setParseError("unbalanced UnicodeSet pattern brackets");
        return i3;
    }

    private int readWords(int i, StringBuilder sb) {
        sb.setLength(0);
        int iSkipWhiteSpace = skipWhiteSpace(i);
        while (iSkipWhiteSpace < this.rules.length()) {
            char cCharAt = this.rules.charAt(iSkipWhiteSpace);
            if (isSyntaxChar(cCharAt) && cCharAt != '-' && cCharAt != '_') {
                if (sb.length() == 0) {
                    return iSkipWhiteSpace;
                }
                int length = sb.length() - 1;
                if (sb.charAt(length) == ' ') {
                    sb.setLength(length);
                }
                return iSkipWhiteSpace;
            }
            if (PatternProps.isWhiteSpace(cCharAt)) {
                sb.append(TokenParser.SP);
                iSkipWhiteSpace = skipWhiteSpace(iSkipWhiteSpace + 1);
            } else {
                sb.append(cCharAt);
                iSkipWhiteSpace++;
            }
        }
        return 0;
    }

    private int skipComment(int i) {
        while (i < this.rules.length()) {
            int i2 = i + 1;
            char cCharAt = this.rules.charAt(i);
            if (cCharAt == '\n' || cCharAt == '\f' || cCharAt == '\r' || cCharAt == 133 || cCharAt == 8232 || cCharAt == 8233) {
                return i2;
            }
            i = i2;
        }
        return i;
    }

    private void setParseError(String str) throws ParseException {
        throw makeParseException(str);
    }

    private void setParseError(String str, Exception exc) throws ParseException {
        ParseException parseExceptionMakeParseException = makeParseException(str + ": " + exc.getMessage());
        parseExceptionMakeParseException.initCause(exc);
        throw parseExceptionMakeParseException;
    }

    private ParseException makeParseException(String str) {
        return new ParseException(appendErrorContext(str), this.ruleIndex);
    }

    private String appendErrorContext(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(" at index ");
        sb.append(this.ruleIndex);
        sb.append(" near \"");
        int i = 15;
        int i2 = this.ruleIndex - 15;
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 > 0 && Character.isLowSurrogate(this.rules.charAt(i2))) {
            i2++;
        }
        sb.append((CharSequence) this.rules, i2, this.ruleIndex);
        sb.append('!');
        int length = this.rules.length() - this.ruleIndex;
        if (length < 16) {
            i = length;
        } else if (Character.isHighSurrogate(this.rules.charAt((r2 + 15) - 1))) {
            i = 14;
        }
        String str2 = this.rules;
        int i3 = this.ruleIndex;
        sb.append((CharSequence) str2, i3, i + i3);
        sb.append('\"');
        return sb.toString();
    }

    private int skipWhiteSpace(int i) {
        while (i < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(i))) {
            i++;
        }
        return i;
    }
}
