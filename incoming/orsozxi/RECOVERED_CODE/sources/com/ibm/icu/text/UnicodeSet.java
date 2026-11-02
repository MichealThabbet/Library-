package com.ibm.icu.text;

import androidx.core.view.InputDeviceCompat;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.BMPSet;
import com.ibm.icu.impl.CharacterPropertiesImpl;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.RuleCharacterIterator;
import com.ibm.icu.impl.SortedSetRelation;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.impl.UPropertyAliases;
import com.ibm.icu.impl.UnicodeSetStringSpan;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.CharSequences;
import com.ibm.icu.lang.CharacterProperties;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.OutputInt;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public class UnicodeSet extends UnicodeFilter implements Iterable<String>, Comparable<UnicodeSet>, Freezable<UnicodeSet> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ADD_CASE_MAPPINGS = 4;
    private static final String ANY_ID = "ANY";
    private static final String ASCII_ID = "ASCII";
    private static final String ASSIGNED = "Assigned";
    public static final int CASE = 2;
    public static final int CASE_INSENSITIVE = 2;
    private static final int HIGH = 1114112;
    public static final int IGNORE_SPACE = 1;
    private static final int INITIAL_CAPACITY = 25;
    private static final int LAST0_START = 0;
    private static final int LAST1_RANGE = 1;
    private static final int LAST2_SET = 2;
    private static final int LOW = 0;
    private static final int MAX_DEPTH = 100;
    private static final int MAX_LENGTH = 1114113;
    public static final int MAX_VALUE = 1114111;
    public static final int MIN_VALUE = 0;
    private static final int MODE0_NONE = 0;
    private static final int MODE1_INBRACKET = 1;
    private static final int MODE2_OUTBRACKET = 2;
    private static final int SETMODE0_NONE = 0;
    private static final int SETMODE1_UNICODESET = 1;
    private static final int SETMODE2_PROPERTYPAT = 2;
    private static final int SETMODE3_PREPARSED = 3;
    private volatile BMPSet bmpSet;
    private int[] buffer;
    private int len;
    private int[] list;
    private String pat;
    private int[] rangeList;
    private volatile UnicodeSetStringSpan stringSpan;
    SortedSet<String> strings;
    private static final SortedSet<String> EMPTY_STRINGS = Collections.unmodifiableSortedSet(new TreeSet());
    public static final UnicodeSet EMPTY = new UnicodeSet().freeze();
    public static final UnicodeSet ALL_CODE_POINTS = new UnicodeSet(0, 1114111).freeze();
    private static XSymbolTable XSYMBOL_TABLE = null;
    private static final VersionInfo NO_VERSION = VersionInfo.getInstance(0, 0, 0, 0);

    public enum ComparisonStyle {
        SHORTER_FIRST,
        LEXICOGRAPHIC,
        LONGER_FIRST
    }

    private interface Filter {
        boolean contains(int i);
    }

    public enum SpanCondition {
        NOT_CONTAINED,
        CONTAINED,
        SIMPLE,
        CONDITION_COUNT
    }

    public static abstract class XSymbolTable implements SymbolTable {
        public boolean applyPropertyAlias(String str, String str2, UnicodeSet unicodeSet) {
            return false;
        }

        @Override // com.ibm.icu.text.SymbolTable
        public char[] lookup(String str) {
            return null;
        }

        @Override // com.ibm.icu.text.SymbolTable
        public UnicodeMatcher lookupMatcher(int i) {
            return null;
        }

        @Override // com.ibm.icu.text.SymbolTable
        public String parseReference(String str, ParsePosition parsePosition, int i) {
            return null;
        }
    }

    private static final int max(int i, int i2) {
        return i > i2 ? i : i2;
    }

    private int nextCapacity(int i) {
        if (i < 25) {
            return i + 25;
        }
        if (i <= 2500) {
            return i * 5;
        }
        int i2 = i * 2;
        if (i2 > 1114113) {
            return 1114113;
        }
        return i2;
    }

    public UnicodeSet() {
        this.strings = EMPTY_STRINGS;
        this.pat = null;
        int[] iArr = new int[25];
        this.list = iArr;
        iArr[0] = 1114112;
        this.len = 1;
    }

    public UnicodeSet(UnicodeSet unicodeSet) {
        this.strings = EMPTY_STRINGS;
        this.pat = null;
        set(unicodeSet);
    }

    public UnicodeSet(int i, int i2) {
        this();
        add(i, i2);
    }

    public UnicodeSet(int... iArr) {
        this.strings = EMPTY_STRINGS;
        this.pat = null;
        if ((iArr.length & 1) != 0) {
            throw new IllegalArgumentException("Must have even number of integers");
        }
        int[] iArr2 = new int[iArr.length + 1];
        this.list = iArr2;
        this.len = iArr2.length;
        int i = -1;
        int i2 = 0;
        while (i2 < iArr.length) {
            int i3 = iArr[i2];
            if (i >= i3) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            int[] iArr3 = this.list;
            int i4 = i2 + 1;
            iArr3[i2] = i3;
            int i5 = iArr[i4] + 1;
            if (i3 >= i5) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            iArr3[i4] = i5;
            i = i5;
            i2 = i4 + 1;
        }
        this.list[i2] = 1114112;
    }

    public UnicodeSet(String str) throws IOException {
        this();
        applyPattern(str, null, null, 1);
    }

    public UnicodeSet(String str, boolean z) throws IOException {
        this();
        applyPattern(str, null, null, z ? 1 : 0);
    }

    public UnicodeSet(String str, int i) throws IOException {
        this();
        applyPattern(str, null, null, i);
    }

    public UnicodeSet(String str, ParsePosition parsePosition, SymbolTable symbolTable) throws IOException {
        this();
        applyPattern(str, parsePosition, symbolTable, 1);
    }

    public UnicodeSet(String str, ParsePosition parsePosition, SymbolTable symbolTable, int i) throws IOException {
        this();
        applyPattern(str, parsePosition, symbolTable, i);
    }

    public Object clone() {
        return isFrozen() ? this : new UnicodeSet(this);
    }

    public UnicodeSet set(int i, int i2) {
        checkFrozen();
        clear();
        complement(i, i2);
        return this;
    }

    public UnicodeSet set(UnicodeSet unicodeSet) {
        checkFrozen();
        this.list = Arrays.copyOf(unicodeSet.list, unicodeSet.len);
        this.len = unicodeSet.len;
        this.pat = unicodeSet.pat;
        if (unicodeSet.hasStrings()) {
            this.strings = new TreeSet((SortedSet) unicodeSet.strings);
        } else {
            this.strings = EMPTY_STRINGS;
        }
        return this;
    }

    public final UnicodeSet applyPattern(String str) {
        checkFrozen();
        return applyPattern(str, null, null, 1);
    }

    public UnicodeSet applyPattern(String str, boolean z) {
        checkFrozen();
        return applyPattern(str, null, null, z ? 1 : 0);
    }

    public UnicodeSet applyPattern(String str, int i) {
        checkFrozen();
        return applyPattern(str, null, null, i);
    }

    public static boolean resemblesPattern(String str, int i) {
        return (i + 1 < str.length() && str.charAt(i) == '[') || resemblesPropertyPattern(str, i);
    }

    private static void appendCodePoint(Appendable appendable, int i) throws IOException {
        try {
            if (i <= 65535) {
                appendable.append((char) i);
            } else {
                appendable.append(UTF16.getLeadSurrogate(i)).append(UTF16.getTrailSurrogate(i));
            }
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    private static void append(Appendable appendable, CharSequence charSequence) throws IOException {
        try {
            appendable.append(charSequence);
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    private static <T extends Appendable> T _appendToPat(T t, String str, boolean z) throws IOException {
        int iCharCount = 0;
        while (iCharCount < str.length()) {
            int iCodePointAt = str.codePointAt(iCharCount);
            _appendToPat(t, iCodePointAt, z);
            iCharCount += Character.charCount(iCodePointAt);
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0038 A[Catch: IOException -> 0x000f, TryCatch #0 {IOException -> 0x000f, blocks: (B:3:0x0002, B:5:0x0008, B:22:0x002b, B:23:0x002e, B:25:0x0034, B:27:0x003b, B:26:0x0038), top: B:31:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static <T extends java.lang.Appendable> T _appendToPat(T r1, int r2, boolean r3) throws java.io.IOException {
        /*
            if (r3 == 0) goto L11
            boolean r3 = com.ibm.icu.impl.Utility.isUnprintable(r2)     // Catch: java.io.IOException -> Lf
            if (r3 == 0) goto L11
            boolean r3 = com.ibm.icu.impl.Utility.escapeUnprintable(r1, r2)     // Catch: java.io.IOException -> Lf
            if (r3 == 0) goto L11
            return r1
        Lf:
            r1 = move-exception
            goto L3f
        L11:
            r3 = 36
            r0 = 92
            if (r2 == r3) goto L38
            r3 = 38
            if (r2 == r3) goto L38
            r3 = 45
            if (r2 == r3) goto L38
            r3 = 58
            if (r2 == r3) goto L38
            r3 = 123(0x7b, float:1.72E-43)
            if (r2 == r3) goto L38
            r3 = 125(0x7d, float:1.75E-43)
            if (r2 == r3) goto L38
            switch(r2) {
                case 91: goto L38;
                case 92: goto L38;
                case 93: goto L38;
                case 94: goto L38;
                default: goto L2e;
            }     // Catch: java.io.IOException -> Lf
        L2e:
            boolean r3 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r2)     // Catch: java.io.IOException -> Lf
            if (r3 == 0) goto L3b
            r1.append(r0)     // Catch: java.io.IOException -> Lf
            goto L3b
        L38:
            r1.append(r0)     // Catch: java.io.IOException -> Lf
        L3b:
            appendCodePoint(r1, r2)     // Catch: java.io.IOException -> Lf
            return r1
        L3f:
            com.ibm.icu.util.ICUUncheckedIOException r2 = new com.ibm.icu.util.ICUUncheckedIOException
            r2.<init>(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet._appendToPat(java.lang.Appendable, int, boolean):java.lang.Appendable");
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public String toPattern(boolean z) {
        String str = this.pat;
        return (str == null || z) ? ((StringBuilder) _toPattern(new StringBuilder(), z)).toString() : str;
    }

    private <T extends Appendable> T _toPattern(T t, boolean z) throws IOException {
        boolean z2;
        String str = this.pat;
        if (str == null) {
            return (T) appendNewPattern(t, z, true);
        }
        try {
            if (!z) {
                t.append(str);
                return t;
            }
            int iCharCount = 0;
            loop0: while (true) {
                z2 = false;
                while (iCharCount < this.pat.length()) {
                    int iCodePointAt = this.pat.codePointAt(iCharCount);
                    iCharCount += Character.charCount(iCodePointAt);
                    if (Utility.isUnprintable(iCodePointAt)) {
                        Utility.escapeUnprintable(t, iCodePointAt);
                    } else if (z2 || iCodePointAt != 92) {
                        if (z2) {
                            t.append('\\');
                        }
                        appendCodePoint(t, iCodePointAt);
                    } else {
                        z2 = true;
                    }
                }
                break loop0;
            }
            if (z2) {
                t.append('\\');
            }
            return t;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    public StringBuffer _generatePattern(StringBuffer stringBuffer, boolean z) {
        return _generatePattern(stringBuffer, z, true);
    }

    public StringBuffer _generatePattern(StringBuffer stringBuffer, boolean z, boolean z2) {
        return (StringBuffer) appendNewPattern(stringBuffer, z, z2);
    }

    private <T extends Appendable> T appendNewPattern(T t, boolean z, boolean z2) throws IOException {
        try {
            t.append('[');
            int rangeCount = getRangeCount();
            if (rangeCount > 1 && getRangeStart(0) == 0 && getRangeEnd(rangeCount - 1) == 1114111) {
                t.append('^');
                for (int i = 1; i < rangeCount; i++) {
                    int rangeEnd = getRangeEnd(i - 1) + 1;
                    int rangeStart = getRangeStart(i) - 1;
                    _appendToPat(t, rangeEnd, z);
                    if (rangeEnd != rangeStart) {
                        if (rangeEnd + 1 != rangeStart) {
                            t.append(Soundex.SILENT_MARKER);
                        }
                        _appendToPat(t, rangeStart, z);
                    }
                }
            } else {
                for (int i2 = 0; i2 < rangeCount; i2++) {
                    int rangeStart2 = getRangeStart(i2);
                    int rangeEnd2 = getRangeEnd(i2);
                    _appendToPat(t, rangeStart2, z);
                    if (rangeStart2 != rangeEnd2) {
                        if (rangeStart2 + 1 != rangeEnd2) {
                            t.append(Soundex.SILENT_MARKER);
                        }
                        _appendToPat(t, rangeEnd2, z);
                    }
                }
            }
            if (z2 && hasStrings()) {
                for (String str : this.strings) {
                    t.append('{');
                    _appendToPat(t, str, z);
                    t.append('}');
                }
            }
            t.append(']');
            return t;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    boolean hasStrings() {
        return !this.strings.isEmpty();
    }

    public int size() {
        int rangeCount = getRangeCount();
        int rangeEnd = 0;
        for (int i = 0; i < rangeCount; i++) {
            rangeEnd += (getRangeEnd(i) - getRangeStart(i)) + 1;
        }
        return rangeEnd + this.strings.size();
    }

    public boolean isEmpty() {
        return this.len == 1 && !hasStrings();
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public boolean matchesIndexValue(int i) {
        for (int i2 = 0; i2 < getRangeCount(); i2++) {
            int rangeStart = getRangeStart(i2);
            int rangeEnd = getRangeEnd(i2);
            if ((rangeStart & InputDeviceCompat.SOURCE_ANY) == (rangeEnd & InputDeviceCompat.SOURCE_ANY)) {
                if ((rangeStart & 255) <= i && i <= (rangeEnd & 255)) {
                    return true;
                }
            } else if ((rangeStart & 255) <= i || i <= (rangeEnd & 255)) {
                return true;
            }
        }
        if (hasStrings()) {
            for (String str : this.strings) {
                if (!str.isEmpty() && (UTF16.charAt(str, 0) & 255) == i) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.ibm.icu.text.UnicodeFilter, com.ibm.icu.text.UnicodeMatcher
    public int matches(Replaceable replaceable, int[] iArr, int i, boolean z) {
        if (iArr[0] == i) {
            if (contains(65535)) {
                return z ? 1 : 2;
            }
            return 0;
        }
        if (hasStrings()) {
            boolean z2 = iArr[0] < i;
            char cCharAt = replaceable.charAt(iArr[0]);
            int i2 = 0;
            for (String str : this.strings) {
                if (!str.isEmpty()) {
                    char cCharAt2 = str.charAt(z2 ? 0 : str.length() - 1);
                    if (z2 && cCharAt2 > cCharAt) {
                        break;
                    }
                    if (cCharAt2 == cCharAt) {
                        int iMatchRest = matchRest(replaceable, iArr[0], i, str);
                        if (z) {
                            if (iMatchRest == (z2 ? i - iArr[0] : iArr[0] - i)) {
                                return 1;
                            }
                        }
                        if (iMatchRest == str.length()) {
                            if (iMatchRest > i2) {
                                i2 = iMatchRest;
                            }
                            if (z2 && iMatchRest < i2) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
            if (i2 != 0) {
                int i3 = iArr[0];
                if (!z2) {
                    i2 = -i2;
                }
                iArr[0] = i3 + i2;
                return 2;
            }
        }
        return super.matches(replaceable, iArr, i, z);
    }

    private static int matchRest(Replaceable replaceable, int i, int i2, String str) {
        int length = str.length();
        int i3 = 1;
        if (i < i2) {
            int i4 = i2 - i;
            if (i4 <= length) {
                length = i4;
            }
            while (i3 < length) {
                if (replaceable.charAt(i + i3) != str.charAt(i3)) {
                    return 0;
                }
                i3++;
            }
            return length;
        }
        int i5 = i - i2;
        if (i5 > length) {
            i5 = length;
        }
        int i6 = length - 1;
        while (i3 < i5) {
            if (replaceable.charAt(i - i3) != str.charAt(i6 - i3)) {
                return 0;
            }
            i3++;
        }
        return i5;
    }

    @Deprecated
    public int matchesAt(CharSequence charSequence, int i) {
        int charCount = -1;
        if (hasStrings()) {
            char cCharAt = charSequence.charAt(i);
            String next = null;
            Iterator<String> it2 = this.strings.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    while (true) {
                        int iMatchesAt = matchesAt(charSequence, i, next);
                        if (charCount > iMatchesAt) {
                            break;
                        }
                        if (!it2.hasNext()) {
                            charCount = iMatchesAt;
                            break;
                        }
                        next = it2.next();
                        charCount = iMatchesAt;
                    }
                } else {
                    next = it2.next();
                    char cCharAt2 = next.charAt(0);
                    if (cCharAt2 >= cCharAt && cCharAt2 > cCharAt) {
                        break;
                    }
                }
            }
        }
        if (charCount < 2) {
            int iCharAt = UTF16.charAt(charSequence, i);
            if (contains(iCharAt)) {
                charCount = UTF16.getCharCount(iCharAt);
            }
        }
        return i + charCount;
    }

    private static int matchesAt(CharSequence charSequence, int i, CharSequence charSequence2) {
        int length = charSequence2.length();
        if (charSequence.length() + i > length) {
            return -1;
        }
        int i2 = 0;
        while (i2 < length) {
            if (charSequence2.charAt(i2) != charSequence.charAt(i)) {
                return -1;
            }
            i2++;
            i++;
        }
        return i2;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        unicodeSet.addAll(this);
    }

    public int indexOf(int i) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.list;
            int i4 = i2 + 1;
            int i5 = iArr[i2];
            if (i < i5) {
                return -1;
            }
            int i6 = i4 + 1;
            int i7 = iArr[i4];
            if (i < i7) {
                return (i3 + i) - i5;
            }
            i3 += i7 - i5;
            i2 = i6;
        }
    }

    public int charAt(int i) {
        if (i < 0) {
            return -1;
        }
        int i2 = this.len & (-2);
        int i3 = 0;
        while (i3 < i2) {
            int[] iArr = this.list;
            int i4 = i3 + 1;
            int i5 = iArr[i3];
            int i6 = i4 + 1;
            int i7 = iArr[i4] - i5;
            if (i < i7) {
                return i5 + i;
            }
            i -= i7;
            i3 = i6;
        }
        return -1;
    }

    public UnicodeSet add(int i, int i2) {
        checkFrozen();
        return add_unchecked(i, i2);
    }

    public UnicodeSet addAll(int i, int i2) {
        checkFrozen();
        return add_unchecked(i, i2);
    }

    private UnicodeSet add_unchecked(int i, int i2) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (i2 < 0 || i2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i2, 6));
        }
        if (i < i2) {
            int i3 = i2 + 1;
            int i4 = this.len;
            if ((i4 & 1) != 0) {
                int i5 = i4 == 1 ? -2 : this.list[i4 - 2];
                if (i5 <= i) {
                    checkFrozen();
                    if (i5 == i) {
                        int[] iArr = this.list;
                        int i6 = this.len;
                        iArr[i6 - 2] = i3;
                        if (i3 == 1114112) {
                            this.len = i6 - 1;
                        }
                    } else {
                        int[] iArr2 = this.list;
                        int i7 = this.len;
                        iArr2[i7 - 1] = i;
                        if (i3 < 1114112) {
                            ensureCapacity(i7 + 2);
                            int[] iArr3 = this.list;
                            int i8 = this.len;
                            int i9 = i8 + 1;
                            this.len = i9;
                            iArr3[i8] = i3;
                            this.len = i9 + 1;
                            iArr3[i9] = 1114112;
                        } else {
                            ensureCapacity(i7 + 1);
                            int[] iArr4 = this.list;
                            int i10 = this.len;
                            this.len = i10 + 1;
                            iArr4[i10] = 1114112;
                        }
                    }
                    this.pat = null;
                    return this;
                }
            }
            add(range(i, i2), 2, 0);
        } else if (i == i2) {
            add(i);
        }
        return this;
    }

    public final UnicodeSet add(int i) {
        checkFrozen();
        return add_unchecked(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final com.ibm.icu.text.UnicodeSet add_unchecked(int r6) {
        /*
            r5 = this;
            if (r6 < 0) goto L99
            r0 = 1114111(0x10ffff, float:1.561202E-39)
            if (r6 > r0) goto L99
            int r1 = r5.findCodePoint(r6)
            r2 = r1 & 1
            if (r2 == 0) goto L10
            return r5
        L10:
            int[] r2 = r5.list
            r3 = r2[r1]
            int r3 = r3 + (-1)
            if (r6 != r3) goto L4a
            r2[r1] = r6
            if (r6 != r0) goto L2f
            int r0 = r5.len
            int r0 = r0 + 1
            r5.ensureCapacity(r0)
            int[] r0 = r5.list
            int r2 = r5.len
            int r3 = r2 + 1
            r5.len = r3
            r3 = 1114112(0x110000, float:1.561203E-39)
            r0[r2] = r3
        L2f:
            if (r1 <= 0) goto L95
            int[] r0 = r5.list
            int r2 = r1 + (-1)
            r3 = r0[r2]
            if (r6 != r3) goto L95
            int r6 = r1 + 1
            int r3 = r5.len
            int r3 = r3 - r1
            int r3 = r3 + (-1)
            java.lang.System.arraycopy(r0, r6, r0, r2, r3)
            int r6 = r5.len
            int r6 = r6 + (-2)
            r5.len = r6
            goto L95
        L4a:
            if (r1 <= 0) goto L59
            int r0 = r1 + (-1)
            r3 = r2[r0]
            if (r6 != r3) goto L59
            r6 = r2[r0]
            int r6 = r6 + 1
            r2[r0] = r6
            goto L95
        L59:
            int r0 = r5.len
            int r2 = r0 + 2
            int[] r3 = r5.list
            int r4 = r3.length
            if (r2 <= r4) goto L7f
            int r0 = r0 + 2
            int r0 = r5.nextCapacity(r0)
            int[] r0 = new int[r0]
            if (r1 == 0) goto L72
            int[] r2 = r5.list
            r3 = 0
            java.lang.System.arraycopy(r2, r3, r0, r3, r1)
        L72:
            int[] r2 = r5.list
            int r3 = r1 + 2
            int r4 = r5.len
            int r4 = r4 - r1
            java.lang.System.arraycopy(r2, r1, r0, r3, r4)
            r5.list = r0
            goto L85
        L7f:
            int r2 = r1 + 2
            int r0 = r0 - r1
            java.lang.System.arraycopy(r3, r1, r3, r2, r0)
        L85:
            int[] r0 = r5.list
            r0[r1] = r6
            int r1 = r1 + 1
            int r6 = r6 + 1
            r0[r1] = r6
            int r6 = r5.len
            int r6 = r6 + 2
            r5.len = r6
        L95:
            r6 = 0
            r5.pat = r6
            return r5
        L99:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid code point U+"
            r1.append(r2)
            long r2 = (long) r6
            r6 = 6
            java.lang.String r6 = com.ibm.icu.impl.Utility.hex(r2, r6)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.<init>(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.add_unchecked(int):com.ibm.icu.text.UnicodeSet");
    }

    public final UnicodeSet add(CharSequence charSequence) {
        checkFrozen();
        int singleCP = getSingleCP(charSequence);
        if (singleCP < 0) {
            String string = charSequence.toString();
            if (!this.strings.contains(string)) {
                addString(string);
                this.pat = null;
            }
        } else {
            add_unchecked(singleCP, singleCP);
        }
        return this;
    }

    private void addString(CharSequence charSequence) {
        if (this.strings == EMPTY_STRINGS) {
            this.strings = new TreeSet();
        }
        this.strings.add(charSequence.toString());
    }

    private static int getSingleCP(CharSequence charSequence) {
        int iCodePointAt;
        if (charSequence.length() == 1) {
            return charSequence.charAt(0);
        }
        if (charSequence.length() != 2 || (iCodePointAt = Character.codePointAt(charSequence, 0)) <= 65535) {
            return -1;
        }
        return iCodePointAt;
    }

    public final UnicodeSet addAll(CharSequence charSequence) {
        checkFrozen();
        int charCount = 0;
        while (charCount < charSequence.length()) {
            int iCharAt = UTF16.charAt(charSequence, charCount);
            add_unchecked(iCharAt, iCharAt);
            charCount += UTF16.getCharCount(iCharAt);
        }
        return this;
    }

    public final UnicodeSet retainAll(CharSequence charSequence) {
        return retainAll(fromAll(charSequence));
    }

    public final UnicodeSet complementAll(CharSequence charSequence) {
        return complementAll(fromAll(charSequence));
    }

    public final UnicodeSet removeAll(CharSequence charSequence) {
        return removeAll(fromAll(charSequence));
    }

    public final UnicodeSet removeAllStrings() {
        checkFrozen();
        if (hasStrings()) {
            this.strings.clear();
            this.pat = null;
        }
        return this;
    }

    public static UnicodeSet from(CharSequence charSequence) {
        return new UnicodeSet().add(charSequence);
    }

    public static UnicodeSet fromAll(CharSequence charSequence) {
        return new UnicodeSet().addAll(charSequence);
    }

    public UnicodeSet retain(int i, int i2) {
        checkFrozen();
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (i2 < 0 || i2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i2, 6));
        }
        if (i <= i2) {
            retain(range(i, i2), 2, 0);
        } else {
            clear();
        }
        return this;
    }

    public final UnicodeSet retain(int i) {
        return retain(i, i);
    }

    public final UnicodeSet retain(CharSequence charSequence) {
        int singleCP = getSingleCP(charSequence);
        if (singleCP < 0) {
            checkFrozen();
            String string = charSequence.toString();
            boolean zContains = this.strings.contains(string);
            if (zContains && getRangeCount() == 0 && size() == 1) {
                return this;
            }
            clear();
            if (zContains) {
                addString(string);
            }
            this.pat = null;
        } else {
            retain(singleCP, singleCP);
        }
        return this;
    }

    public UnicodeSet remove(int i, int i2) {
        checkFrozen();
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (i2 >= 0 && i2 <= 1114111) {
            if (i <= i2) {
                retain(range(i, i2), 2, 2);
            }
            return this;
        }
        throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i2, 6));
    }

    public final UnicodeSet remove(int i) {
        return remove(i, i);
    }

    public final UnicodeSet remove(CharSequence charSequence) {
        int singleCP = getSingleCP(charSequence);
        if (singleCP < 0) {
            checkFrozen();
            String string = charSequence.toString();
            if (this.strings.contains(string)) {
                this.strings.remove(string);
                this.pat = null;
            }
        } else {
            remove(singleCP, singleCP);
        }
        return this;
    }

    public UnicodeSet complement(int i, int i2) {
        checkFrozen();
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (i2 < 0 || i2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i2, 6));
        }
        if (i <= i2) {
            xor(range(i, i2), 2, 0);
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet complement(int i) {
        return complement(i, i);
    }

    public UnicodeSet complement() {
        checkFrozen();
        int[] iArr = this.list;
        if (iArr[0] == 0) {
            System.arraycopy(iArr, 1, iArr, 0, this.len - 1);
            this.len--;
        } else {
            ensureCapacity(this.len + 1);
            int[] iArr2 = this.list;
            System.arraycopy(iArr2, 0, iArr2, 1, this.len);
            this.list[0] = 0;
            this.len++;
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet complement(CharSequence charSequence) {
        checkFrozen();
        int singleCP = getSingleCP(charSequence);
        if (singleCP < 0) {
            String string = charSequence.toString();
            if (this.strings.contains(string)) {
                this.strings.remove(string);
            } else {
                addString(string);
            }
            this.pat = null;
        } else {
            complement(singleCP, singleCP);
        }
        return this;
    }

    @Override // com.ibm.icu.text.UnicodeFilter
    public boolean contains(int i) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (this.bmpSet != null) {
            return this.bmpSet.contains(i);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.contains(i);
        }
        return (findCodePoint(i) & 1) != 0;
    }

    private final int findCodePoint(int i) {
        int[] iArr = this.list;
        int i2 = 0;
        if (i < iArr[0]) {
            return 0;
        }
        int i3 = this.len;
        if (i3 >= 2 && i >= iArr[i3 - 2]) {
            return i3 - 1;
        }
        int i4 = this.len - 1;
        while (true) {
            int i5 = (i2 + i4) >>> 1;
            if (i5 == i2) {
                return i4;
            }
            if (i < this.list[i5]) {
                i4 = i5;
            } else {
                i2 = i5;
            }
        }
    }

    public boolean contains(int i, int i2) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (i2 < 0 || i2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i2, 6));
        }
        int iFindCodePoint = findCodePoint(i);
        return (iFindCodePoint & 1) != 0 && i2 < this.list[iFindCodePoint];
    }

    public final boolean contains(CharSequence charSequence) {
        int singleCP = getSingleCP(charSequence);
        if (singleCP < 0) {
            return this.strings.contains(charSequence.toString());
        }
        return contains(singleCP);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0035, code lost:
    
        if (r13.strings.containsAll(r14.strings) != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0038, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0046 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0043 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean containsAll(com.ibm.icu.text.UnicodeSet r14) {
        /*
            r13 = this;
            int[] r0 = r14.list
            int r1 = r13.len
            r2 = 1
            int r1 = r1 - r2
            int r3 = r14.len
            int r3 = r3 - r2
            r4 = 0
            r5 = 1
            r6 = 0
            r7 = 1
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
        L12:
            if (r5 == 0) goto L29
            if (r6 < r1) goto L1c
            if (r7 == 0) goto L1b
            if (r8 < r3) goto L1b
            goto L2d
        L1b:
            return r4
        L1c:
            int[] r5 = r13.list
            int r9 = r6 + 1
            r6 = r5[r6]
            int r11 = r9 + 1
            r5 = r5[r9]
            r9 = r6
            r6 = r11
            r11 = r5
        L29:
            if (r7 == 0) goto L41
            if (r8 < r3) goto L39
        L2d:
            java.util.SortedSet<java.lang.String> r0 = r13.strings
            java.util.SortedSet<java.lang.String> r14 = r14.strings
            boolean r14 = r0.containsAll(r14)
            if (r14 != 0) goto L38
            return r4
        L38:
            return r2
        L39:
            int r5 = r8 + 1
            r10 = r0[r8]
            int r8 = r5 + 1
            r12 = r0[r5]
        L41:
            if (r10 < r11) goto L46
            r5 = 1
            r7 = 0
            goto L12
        L46:
            if (r10 < r9) goto L4d
            if (r12 > r11) goto L4d
            r5 = 0
            r7 = 1
            goto L12
        L4d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.containsAll(com.ibm.icu.text.UnicodeSet):boolean");
    }

    public boolean containsAll(String str) {
        int charCount = 0;
        while (charCount < str.length()) {
            int iCharAt = UTF16.charAt(str, charCount);
            if (contains(iCharAt)) {
                charCount += UTF16.getCharCount(iCharAt);
            } else {
                if (hasStrings()) {
                    return containsAll(str, 0);
                }
                return false;
            }
        }
        return true;
    }

    private boolean containsAll(String str, int i) {
        if (i >= str.length()) {
            return true;
        }
        int iCharAt = UTF16.charAt(str, i);
        if (contains(iCharAt) && containsAll(str, UTF16.getCharCount(iCharAt) + i)) {
            return true;
        }
        for (String str2 : this.strings) {
            if (!str2.isEmpty() && str.startsWith(str2, i) && containsAll(str, str2.length() + i)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public String getRegexEquivalent() throws IOException {
        if (!hasStrings()) {
            return toString();
        }
        StringBuilder sb = new StringBuilder("(?:");
        appendNewPattern(sb, true, false);
        for (String str : this.strings) {
            sb.append('|');
            _appendToPat(sb, str, true);
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean containsNone(int i, int i2) {
        int[] iArr;
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (i2 < 0 || i2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i2, 6));
        }
        int i3 = -1;
        do {
            iArr = this.list;
            i3++;
        } while (i >= iArr[i3]);
        return (i3 & 1) == 0 && i2 < iArr[i3];
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0031, code lost:
    
        if (com.ibm.icu.impl.SortedSetRelation.hasRelation(r13.strings, 5, r14.strings) != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0034, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean containsNone(com.ibm.icu.text.UnicodeSet r14) {
        /*
            r13 = this;
            int[] r0 = r14.list
            int r1 = r13.len
            r2 = 1
            int r1 = r1 - r2
            int r3 = r14.len
            int r3 = r3 - r2
            r4 = 0
            r5 = 1
            r6 = 0
            r7 = 1
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
        L12:
            if (r5 == 0) goto L24
            if (r6 < r1) goto L17
            goto L28
        L17:
            int[] r5 = r13.list
            int r9 = r6 + 1
            r6 = r5[r6]
            int r11 = r9 + 1
            r5 = r5[r9]
            r9 = r6
            r6 = r11
            r11 = r5
        L24:
            if (r7 == 0) goto L3d
            if (r8 < r3) goto L35
        L28:
            java.util.SortedSet<java.lang.String> r0 = r13.strings
            r1 = 5
            java.util.SortedSet<java.lang.String> r14 = r14.strings
            boolean r14 = com.ibm.icu.impl.SortedSetRelation.hasRelation(r0, r1, r14)
            if (r14 != 0) goto L34
            return r4
        L34:
            return r2
        L35:
            int r5 = r8 + 1
            r10 = r0[r8]
            int r8 = r5 + 1
            r12 = r0[r5]
        L3d:
            if (r10 < r11) goto L42
            r5 = 1
            r7 = 0
            goto L12
        L42:
            if (r9 < r12) goto L47
            r5 = 0
            r7 = 1
            goto L12
        L47:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.containsNone(com.ibm.icu.text.UnicodeSet):boolean");
    }

    public boolean containsNone(CharSequence charSequence) {
        return span(charSequence, SpanCondition.NOT_CONTAINED) == charSequence.length();
    }

    public final boolean containsSome(int i, int i2) {
        return !containsNone(i, i2);
    }

    public final boolean containsSome(UnicodeSet unicodeSet) {
        return !containsNone(unicodeSet);
    }

    public final boolean containsSome(CharSequence charSequence) {
        return !containsNone(charSequence);
    }

    public UnicodeSet addAll(UnicodeSet unicodeSet) {
        checkFrozen();
        add(unicodeSet.list, unicodeSet.len, 0);
        if (unicodeSet.hasStrings()) {
            SortedSet<String> sortedSet = this.strings;
            if (sortedSet == EMPTY_STRINGS) {
                this.strings = new TreeSet((SortedSet) unicodeSet.strings);
            } else {
                sortedSet.addAll(unicodeSet.strings);
            }
        }
        return this;
    }

    public UnicodeSet retainAll(UnicodeSet unicodeSet) {
        checkFrozen();
        retain(unicodeSet.list, unicodeSet.len, 0);
        if (hasStrings()) {
            if (!unicodeSet.hasStrings()) {
                this.strings.clear();
            } else {
                this.strings.retainAll(unicodeSet.strings);
            }
        }
        return this;
    }

    public UnicodeSet removeAll(UnicodeSet unicodeSet) {
        checkFrozen();
        retain(unicodeSet.list, unicodeSet.len, 2);
        if (hasStrings() && unicodeSet.hasStrings()) {
            this.strings.removeAll(unicodeSet.strings);
        }
        return this;
    }

    public UnicodeSet complementAll(UnicodeSet unicodeSet) {
        checkFrozen();
        xor(unicodeSet.list, unicodeSet.len, 0);
        if (unicodeSet.hasStrings()) {
            SortedSet<String> sortedSet = this.strings;
            if (sortedSet == EMPTY_STRINGS) {
                this.strings = new TreeSet((SortedSet) unicodeSet.strings);
            } else {
                SortedSetRelation.doOperation(sortedSet, 5, unicodeSet.strings);
            }
        }
        return this;
    }

    public UnicodeSet clear() {
        checkFrozen();
        this.list[0] = 1114112;
        this.len = 1;
        this.pat = null;
        if (hasStrings()) {
            this.strings.clear();
        }
        return this;
    }

    public int getRangeCount() {
        return this.len / 2;
    }

    public int getRangeStart(int i) {
        return this.list[i * 2];
    }

    public int getRangeEnd(int i) {
        return this.list[(i * 2) + 1] - 1;
    }

    public UnicodeSet compact() {
        checkFrozen();
        int i = this.len;
        int i2 = i + 7;
        int[] iArr = this.list;
        if (i2 < iArr.length) {
            this.list = Arrays.copyOf(iArr, i);
        }
        this.rangeList = null;
        this.buffer = null;
        SortedSet<String> sortedSet = this.strings;
        if (sortedSet != EMPTY_STRINGS && sortedSet.isEmpty()) {
            this.strings = EMPTY_STRINGS;
        }
        return this;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        try {
            UnicodeSet unicodeSet = (UnicodeSet) obj;
            if (this.len != unicodeSet.len) {
                return false;
            }
            for (int i = 0; i < this.len; i++) {
                if (this.list[i] != unicodeSet.list[i]) {
                    return false;
                }
            }
            return this.strings.equals(unicodeSet.strings);
        } catch (Exception unused) {
            return false;
        }
    }

    public int hashCode() {
        int i = this.len;
        for (int i2 = 0; i2 < this.len; i2++) {
            i = (i * 1000003) + this.list[i2];
        }
        return i;
    }

    public String toString() {
        return toPattern(true);
    }

    @Deprecated
    public UnicodeSet applyPattern(String str, ParsePosition parsePosition, SymbolTable symbolTable, int i) throws IOException {
        boolean z = parsePosition == null;
        if (z) {
            parsePosition = new ParsePosition(0);
        }
        StringBuilder sb = new StringBuilder();
        RuleCharacterIterator ruleCharacterIterator = new RuleCharacterIterator(str, symbolTable, parsePosition);
        applyPattern(ruleCharacterIterator, symbolTable, sb, i, 0);
        if (ruleCharacterIterator.inVariable()) {
            syntaxError(ruleCharacterIterator, "Extra chars in variable value");
        }
        this.pat = sb.toString();
        if (z) {
            int index = parsePosition.getIndex();
            if ((i & 1) != 0) {
                index = PatternProps.skipWhiteSpace(str, index);
            }
            if (index != str.length()) {
                throw new IllegalArgumentException("Parse of \"" + str + "\" failed at " + index);
            }
        }
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:174:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0167 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x00d4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void applyPattern(com.ibm.icu.impl.RuleCharacterIterator r29, com.ibm.icu.text.SymbolTable r30, java.lang.Appendable r31, int r32, int r33) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 914
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.applyPattern(com.ibm.icu.impl.RuleCharacterIterator, com.ibm.icu.text.SymbolTable, java.lang.Appendable, int, int):void");
    }

    private static void syntaxError(RuleCharacterIterator ruleCharacterIterator, String str) {
        throw new IllegalArgumentException("Error: " + str + " at \"" + Utility.escape(ruleCharacterIterator.toString()) + '\"');
    }

    public <T extends Collection<String>> T addAllTo(T t) {
        return (T) addAllTo(this, t);
    }

    public String[] addAllTo(String[] strArr) {
        return (String[]) addAllTo(this, strArr);
    }

    public static String[] toArray(UnicodeSet unicodeSet) {
        return (String[]) addAllTo(unicodeSet, new String[unicodeSet.size()]);
    }

    public UnicodeSet add(Iterable<?> iterable) {
        return addAll(iterable);
    }

    public UnicodeSet addAll(Iterable<?> iterable) {
        checkFrozen();
        Iterator<?> it2 = iterable.iterator();
        while (it2.hasNext()) {
            add(it2.next().toString());
        }
        return this;
    }

    private void ensureCapacity(int i) {
        if (i > 1114113) {
            i = 1114113;
        }
        if (i <= this.list.length) {
            return;
        }
        int[] iArr = new int[nextCapacity(i)];
        System.arraycopy(this.list, 0, iArr, 0, this.len);
        this.list = iArr;
    }

    private void ensureBufferCapacity(int i) {
        if (i > 1114113) {
            i = 1114113;
        }
        int[] iArr = this.buffer;
        if (iArr == null || i > iArr.length) {
            this.buffer = new int[nextCapacity(i)];
        }
    }

    private int[] range(int i, int i2) {
        int[] iArr = this.rangeList;
        if (iArr == null) {
            this.rangeList = new int[]{i, i2 + 1, 1114112};
        } else {
            iArr[0] = i;
            iArr[1] = i2 + 1;
        }
        return this.rangeList;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0023 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.text.UnicodeSet xor(int[] r7, int r8, int r9) {
        /*
            r6 = this;
            int r0 = r6.len
            int r0 = r0 + r8
            r6.ensureBufferCapacity(r0)
            int[] r8 = r6.list
            r0 = 0
            r8 = r8[r0]
            r1 = 1
            if (r9 == r1) goto L15
            r2 = 2
            if (r9 != r2) goto L12
            goto L15
        L12:
            r9 = r7[r0]
            goto L1b
        L15:
            r9 = r7[r0]
            if (r9 != 0) goto L1e
            r9 = r7[r1]
        L1b:
            r0 = r9
            r9 = 0
            goto L20
        L1e:
            r9 = 0
            r1 = 0
        L20:
            r2 = 1
        L21:
            if (r8 >= r0) goto L32
            int[] r3 = r6.buffer
            int r4 = r9 + 1
            r3[r9] = r8
            int[] r8 = r6.list
            int r9 = r2 + 1
            r8 = r8[r2]
            r2 = r9
        L30:
            r9 = r4
            goto L21
        L32:
            if (r0 >= r8) goto L41
            int[] r3 = r6.buffer
            int r4 = r9 + 1
            r3[r9] = r0
            int r0 = r1 + 1
            r9 = r7[r1]
            r1 = r0
            r0 = r9
            goto L30
        L41:
            r0 = 1114112(0x110000, float:1.561203E-39)
            if (r8 == r0) goto L54
            int[] r8 = r6.list
            int r0 = r2 + 1
            r8 = r8[r2]
            int r2 = r1 + 1
            r1 = r7[r1]
            r5 = r2
            r2 = r0
            r0 = r1
            r1 = r5
            goto L21
        L54:
            int[] r7 = r6.buffer
            int r8 = r9 + 1
            r7[r9] = r0
            r6.len = r8
            int[] r8 = r6.list
            r6.list = r7
            r6.buffer = r8
            r7 = 0
            r6.pat = r7
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.xor(int[], int, int):com.ibm.icu.text.UnicodeSet");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.text.UnicodeSet add(int[] r10, int r11, int r12) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.add(int[], int, int):com.ibm.icu.text.UnicodeSet");
    }

    private UnicodeSet retain(int[] iArr, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        ensureBufferCapacity(this.len + i);
        int i18 = 0;
        int i19 = this.list[0];
        int i20 = iArr[0];
        int i21 = 1;
        int i22 = 1;
        while (true) {
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            continue;
                        } else if (i19 < i20) {
                            i11 = i18 + 1;
                            this.buffer[i18] = i19;
                            i12 = i21 + 1;
                            i19 = this.list[i21];
                            i2 ^= 1;
                            i21 = i12;
                        } else if (i20 < i19) {
                            i11 = i18 + 1;
                            this.buffer[i18] = i20;
                            i13 = i22 + 1;
                            i20 = iArr[i22];
                            i2 ^= 2;
                            i22 = i13;
                        } else {
                            if (i19 == 1114112) {
                                break;
                            }
                            i3 = i18 + 1;
                            this.buffer[i18] = i19;
                            i4 = i21 + 1;
                            i19 = this.list[i21];
                            i5 = i2 ^ 1;
                            i6 = i22 + 1;
                            i7 = iArr[i22];
                            i2 = i5 ^ 2;
                            i22 = i6;
                            i20 = i7;
                            i21 = i4;
                            i18 = i3;
                        }
                    } else if (i20 < i19) {
                        i8 = i22 + 1;
                        i9 = iArr[i22];
                        i2 ^= 2;
                        int i23 = i9;
                        i22 = i8;
                        i20 = i23;
                    } else if (i19 < i20) {
                        i11 = i18 + 1;
                        this.buffer[i18] = i19;
                        i12 = i21 + 1;
                        i19 = this.list[i21];
                        i2 ^= 1;
                        i21 = i12;
                    } else {
                        if (i19 == 1114112) {
                            break;
                        }
                        i14 = i21 + 1;
                        i19 = this.list[i21];
                        i15 = i2 ^ 1;
                        i16 = i22 + 1;
                        i17 = iArr[i22];
                        i2 = i15 ^ 2;
                        int i24 = i16;
                        i21 = i14;
                        i20 = i17;
                        i22 = i24;
                    }
                    i18 = i11;
                } else if (i19 < i20) {
                    i10 = i21 + 1;
                    i19 = this.list[i21];
                    i2 ^= 1;
                    i21 = i10;
                } else if (i20 < i19) {
                    i11 = i18 + 1;
                    this.buffer[i18] = i20;
                    i13 = i22 + 1;
                    i20 = iArr[i22];
                    i2 ^= 2;
                    i22 = i13;
                    i18 = i11;
                } else {
                    if (i19 == 1114112) {
                        break;
                    }
                    i14 = i21 + 1;
                    i19 = this.list[i21];
                    i15 = i2 ^ 1;
                    i16 = i22 + 1;
                    i17 = iArr[i22];
                    i2 = i15 ^ 2;
                    int i242 = i16;
                    i21 = i14;
                    i20 = i17;
                    i22 = i242;
                }
            } else if (i19 < i20) {
                i10 = i21 + 1;
                i19 = this.list[i21];
                i2 ^= 1;
                i21 = i10;
            } else if (i20 < i19) {
                i8 = i22 + 1;
                i9 = iArr[i22];
                i2 ^= 2;
                int i232 = i9;
                i22 = i8;
                i20 = i232;
            } else {
                if (i19 == 1114112) {
                    break;
                }
                i3 = i18 + 1;
                this.buffer[i18] = i19;
                i4 = i21 + 1;
                i19 = this.list[i21];
                i5 = i2 ^ 1;
                i6 = i22 + 1;
                i7 = iArr[i22];
                i2 = i5 ^ 2;
                i22 = i6;
                i20 = i7;
                i21 = i4;
                i18 = i3;
            }
        }
        int[] iArr2 = this.buffer;
        iArr2[i18] = 1114112;
        this.len = i18 + 1;
        int[] iArr3 = this.list;
        this.list = iArr2;
        this.buffer = iArr3;
        this.pat = null;
        return this;
    }

    private static final class NumericValueFilter implements Filter {
        double value;

        NumericValueFilter(double d) {
            this.value = d;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int i) {
            return UCharacter.getUnicodeNumericValue(i) == this.value;
        }
    }

    private static final class GeneralCategoryMaskFilter implements Filter {
        int mask;

        GeneralCategoryMaskFilter(int i) {
            this.mask = i;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int i) {
            return ((1 << UCharacter.getType(i)) & this.mask) != 0;
        }
    }

    private static final class IntPropertyFilter implements Filter {
        int prop;
        int value;

        IntPropertyFilter(int i, int i2) {
            this.prop = i;
            this.value = i2;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int i) {
            return UCharacter.getIntPropertyValue(i, this.prop) == this.value;
        }
    }

    private static final class ScriptExtensionsFilter implements Filter {
        int script;

        ScriptExtensionsFilter(int i) {
            this.script = i;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int i) {
            return UScript.hasScript(i, this.script);
        }
    }

    private static final class VersionFilter implements Filter {
        VersionInfo version;

        VersionFilter(VersionInfo versionInfo) {
            this.version = versionInfo;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int i) {
            VersionInfo age = UCharacter.getAge(i);
            return !Utility.sameObjects(age, UnicodeSet.NO_VERSION) && age.compareTo(this.version) <= 0;
        }
    }

    private void applyFilter(Filter filter, UnicodeSet unicodeSet) {
        clear();
        int rangeCount = unicodeSet.getRangeCount();
        int i = -1;
        for (int i2 = 0; i2 < rangeCount; i2++) {
            int rangeEnd = unicodeSet.getRangeEnd(i2);
            for (int rangeStart = unicodeSet.getRangeStart(i2); rangeStart <= rangeEnd; rangeStart++) {
                if (filter.contains(rangeStart)) {
                    if (i < 0) {
                        i = rangeStart;
                    }
                } else if (i >= 0) {
                    add_unchecked(i, rangeStart - 1);
                    i = -1;
                }
            }
        }
        if (i >= 0) {
            add_unchecked(i, 1114111);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0038 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String mungeCharName(java.lang.String r6) {
        /*
            java.lang.String r6 = com.ibm.icu.impl.PatternProps.trimWhiteSpace(r6)
            r0 = 0
            r1 = 0
            r2 = 0
        L7:
            int r3 = r6.length()
            if (r2 >= r3) goto L3b
            char r3 = r6.charAt(r2)
            boolean r4 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r3)
            r5 = 32
            if (r4 == 0) goto L33
            if (r1 != 0) goto L24
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6, r0, r2)
            goto L31
        L24:
            int r3 = r1.length()
            int r3 = r3 + (-1)
            char r3 = r1.charAt(r3)
            if (r3 != r5) goto L31
            goto L38
        L31:
            r3 = 32
        L33:
            if (r1 == 0) goto L38
            r1.append(r3)
        L38:
            int r2 = r2 + 1
            goto L7
        L3b:
            if (r1 != 0) goto L3e
            goto L42
        L3e:
            java.lang.String r6 = r1.toString()
        L42:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeSet.mungeCharName(java.lang.String):java.lang.String");
    }

    public UnicodeSet applyIntPropertyValue(int i, int i2) {
        if (i == 8192) {
            applyFilter(new GeneralCategoryMaskFilter(i2), CharacterPropertiesImpl.getInclusionsForProperty(i));
        } else if (i == 28672) {
            applyFilter(new ScriptExtensionsFilter(i2), CharacterPropertiesImpl.getInclusionsForProperty(i));
        } else if (i < 0 || i >= 65) {
            if (4096 <= i && i < 4121) {
                applyFilter(new IntPropertyFilter(i, i2), CharacterPropertiesImpl.getInclusionsForProperty(i));
            } else {
                throw new IllegalArgumentException("unsupported property " + i);
            }
        } else if (i2 == 0 || i2 == 1) {
            set(CharacterProperties.getBinaryPropertySet(i));
            if (i2 == 0) {
                complement();
            }
        } else {
            clear();
        }
        return this;
    }

    public UnicodeSet applyPropertyAlias(String str, String str2) {
        return applyPropertyAlias(str, str2, null);
    }

    public UnicodeSet applyPropertyAlias(String str, String str2, SymbolTable symbolTable) {
        checkFrozen();
        if (symbolTable != null && (symbolTable instanceof XSymbolTable) && ((XSymbolTable) symbolTable).applyPropertyAlias(str, str2, this)) {
            return this;
        }
        XSymbolTable xSymbolTable = XSYMBOL_TABLE;
        if (xSymbolTable != null && xSymbolTable.applyPropertyAlias(str, str2, this)) {
            return this;
        }
        int i = 4106;
        int propertyValueEnum = 1;
        boolean z = false;
        if (str2.length() > 0) {
            int propertyEnum = UCharacter.getPropertyEnum(str);
            if (propertyEnum == 4101) {
                propertyEnum = 8192;
            }
            if ((propertyEnum >= 0 && propertyEnum < 65) || ((propertyEnum >= 4096 && propertyEnum < 4121) || (propertyEnum >= 8192 && propertyEnum < 8193))) {
                try {
                    propertyValueEnum = UCharacter.getPropertyValueEnum(propertyEnum, str2);
                } catch (IllegalArgumentException e) {
                    if ((propertyEnum != 4098 && propertyEnum != 4112 && propertyEnum != 4113) || (propertyValueEnum = Integer.parseInt(PatternProps.trimWhiteSpace(str2))) < 0 || propertyValueEnum > 255) {
                        throw e;
                    }
                }
            } else {
                if (propertyEnum == 12288) {
                    applyFilter(new NumericValueFilter(Double.parseDouble(PatternProps.trimWhiteSpace(str2))), CharacterPropertiesImpl.getInclusionsForProperty(propertyEnum));
                    return this;
                }
                if (propertyEnum == 16384) {
                    applyFilter(new VersionFilter(VersionInfo.getInstance(mungeCharName(str2))), CharacterPropertiesImpl.getInclusionsForProperty(propertyEnum));
                    return this;
                }
                if (propertyEnum == 16389) {
                    int charFromExtendedName = UCharacter.getCharFromExtendedName(mungeCharName(str2));
                    if (charFromExtendedName == -1) {
                        throw new IllegalArgumentException("Invalid character name");
                    }
                    clear();
                    add_unchecked(charFromExtendedName);
                    return this;
                }
                if (propertyEnum == 16395) {
                    throw new IllegalArgumentException("Unicode_1_Name (na1) not supported");
                }
                if (propertyEnum == 28672) {
                    propertyValueEnum = UCharacter.getPropertyValueEnum(4106, str2);
                } else {
                    throw new IllegalArgumentException("Unsupported property");
                }
            }
            i = propertyEnum;
        } else {
            UPropertyAliases uPropertyAliases = UPropertyAliases.INSTANCE;
            int propertyValueEnum2 = uPropertyAliases.getPropertyValueEnum(8192, str);
            if (propertyValueEnum2 == -1) {
                int propertyValueEnum3 = uPropertyAliases.getPropertyValueEnum(4106, str);
                if (propertyValueEnum3 == -1) {
                    int propertyEnum2 = uPropertyAliases.getPropertyEnum(str);
                    i = propertyEnum2 == -1 ? -1 : propertyEnum2;
                    if (i < 0 || i >= 65) {
                        if (i == -1) {
                            if (UPropertyAliases.compare(ANY_ID, str) == 0) {
                                set(0, 1114111);
                                return this;
                            }
                            if (UPropertyAliases.compare("ASCII", str) == 0) {
                                set(0, 127);
                                return this;
                            }
                            if (UPropertyAliases.compare(ASSIGNED, str) != 0) {
                                throw new IllegalArgumentException("Invalid property alias: " + str + ContainerUtils.KEY_VALUE_DELIMITER + str2);
                            }
                            i = 8192;
                            z = true;
                        } else {
                            throw new IllegalArgumentException("Missing property value");
                        }
                    }
                } else {
                    propertyValueEnum = propertyValueEnum3;
                }
            } else {
                propertyValueEnum = propertyValueEnum2;
                i = 8192;
            }
        }
        applyIntPropertyValue(i, propertyValueEnum);
        if (z) {
            complement();
        }
        return this;
    }

    private static boolean resemblesPropertyPattern(String str, int i) {
        if (i + 5 > str.length()) {
            return false;
        }
        return str.regionMatches(i, "[:", 0, 2) || str.regionMatches(true, i, "\\p", 0, 2) || str.regionMatches(i, "\\N", 0, 2);
    }

    private static boolean resemblesPropertyPattern(RuleCharacterIterator ruleCharacterIterator, int i) {
        int i2 = i & (-3);
        Object pos = ruleCharacterIterator.getPos(null);
        int next = ruleCharacterIterator.next(i2);
        boolean z = false;
        if (next == 91 || next == 92) {
            int next2 = ruleCharacterIterator.next(i2 & (-5));
            if (next != 91 ? next2 == 78 || next2 == 112 || next2 == 80 : next2 == 58) {
                z = true;
            }
        }
        ruleCharacterIterator.setPos(pos);
        return z;
    }

    private UnicodeSet applyPropertyPattern(String str, ParsePosition parsePosition, SymbolTable symbolTable) {
        boolean z;
        boolean z2;
        int iSkipWhiteSpace;
        String strSubstring;
        String strSubstring2;
        int index = parsePosition.getIndex();
        if (index + 5 > str.length()) {
            return null;
        }
        boolean z3 = false;
        if (str.regionMatches(index, "[:", 0, 2)) {
            iSkipWhiteSpace = PatternProps.skipWhiteSpace(str, index + 2);
            if (iSkipWhiteSpace >= str.length() || str.charAt(iSkipWhiteSpace) != '^') {
                z2 = false;
                z = false;
            } else {
                iSkipWhiteSpace++;
                z2 = false;
                z = true;
            }
            z3 = true;
        } else {
            if (!str.regionMatches(true, index, "\\p", 0, 2) && !str.regionMatches(index, "\\N", 0, 2)) {
                return null;
            }
            char cCharAt = str.charAt(index + 1);
            boolean z4 = cCharAt == 'P';
            boolean z5 = cCharAt == 'N';
            int iSkipWhiteSpace2 = PatternProps.skipWhiteSpace(str, index + 2);
            if (iSkipWhiteSpace2 != str.length()) {
                int i = iSkipWhiteSpace2 + 1;
                if (str.charAt(iSkipWhiteSpace2) == '{') {
                    z = z4;
                    z2 = z5;
                    iSkipWhiteSpace = i;
                }
            }
            return null;
        }
        int iIndexOf = str.indexOf(z3 ? ":]" : "}", iSkipWhiteSpace);
        if (iIndexOf < 0) {
            return null;
        }
        int iIndexOf2 = str.indexOf(61, iSkipWhiteSpace);
        if (iIndexOf2 >= 0 && iIndexOf2 < iIndexOf && !z2) {
            strSubstring = str.substring(iSkipWhiteSpace, iIndexOf2);
            strSubstring2 = str.substring(iIndexOf2 + 1, iIndexOf);
        } else {
            strSubstring = str.substring(iSkipWhiteSpace, iIndexOf);
            if (z2) {
                strSubstring = "na";
                strSubstring2 = strSubstring;
            } else {
                strSubstring2 = "";
            }
        }
        applyPropertyAlias(strSubstring, strSubstring2, symbolTable);
        if (z) {
            complement();
        }
        parsePosition.setIndex(iIndexOf + (z3 ? 2 : 1));
        return this;
    }

    private void applyPropertyPattern(RuleCharacterIterator ruleCharacterIterator, Appendable appendable, SymbolTable symbolTable) throws IOException {
        String strLookahead = ruleCharacterIterator.lookahead();
        ParsePosition parsePosition = new ParsePosition(0);
        applyPropertyPattern(strLookahead, parsePosition, symbolTable);
        if (parsePosition.getIndex() == 0) {
            syntaxError(ruleCharacterIterator, "Invalid property pattern");
        }
        ruleCharacterIterator.jumpahead(parsePosition.getIndex());
        append(appendable, strLookahead.substring(0, parsePosition.getIndex()));
    }

    private static final void addCaseMapping(UnicodeSet unicodeSet, int i, StringBuilder sb) {
        if (i >= 0) {
            if (i > 31) {
                unicodeSet.add(i);
            } else {
                unicodeSet.add(sb.toString());
                sb.setLength(0);
            }
        }
    }

    public UnicodeSet closeOver(int i) {
        checkFrozen();
        if ((i & 6) != 0) {
            UCaseProps uCaseProps = UCaseProps.INSTANCE;
            UnicodeSet unicodeSet = new UnicodeSet(this);
            ULocale uLocale = ULocale.ROOT;
            int i2 = i & 2;
            if (i2 != 0 && unicodeSet.hasStrings()) {
                unicodeSet.strings.clear();
            }
            int rangeCount = getRangeCount();
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < rangeCount; i3++) {
                int rangeStart = getRangeStart(i3);
                int rangeEnd = getRangeEnd(i3);
                if (i2 != 0) {
                    while (rangeStart <= rangeEnd) {
                        uCaseProps.addCaseClosure(rangeStart, unicodeSet);
                        rangeStart++;
                    }
                } else {
                    while (rangeStart <= rangeEnd) {
                        addCaseMapping(unicodeSet, uCaseProps.toFullLower(rangeStart, null, sb, 1), sb);
                        addCaseMapping(unicodeSet, uCaseProps.toFullTitle(rangeStart, null, sb, 1), sb);
                        addCaseMapping(unicodeSet, uCaseProps.toFullUpper(rangeStart, null, sb, 1), sb);
                        addCaseMapping(unicodeSet, uCaseProps.toFullFolding(rangeStart, sb, 0), sb);
                        rangeStart++;
                    }
                }
            }
            if (hasStrings()) {
                if (i2 != 0) {
                    Iterator<String> it2 = this.strings.iterator();
                    while (it2.hasNext()) {
                        String strFoldCase = UCharacter.foldCase(it2.next(), 0);
                        if (!uCaseProps.addStringCaseClosure(strFoldCase, unicodeSet)) {
                            unicodeSet.add(strFoldCase);
                        }
                    }
                } else {
                    BreakIterator wordInstance = BreakIterator.getWordInstance(uLocale);
                    for (String str : this.strings) {
                        unicodeSet.add(UCharacter.toLowerCase(uLocale, str));
                        unicodeSet.add(UCharacter.toTitleCase(uLocale, str, wordInstance));
                        unicodeSet.add(UCharacter.toUpperCase(uLocale, str));
                        unicodeSet.add(UCharacter.foldCase(str, 0));
                    }
                }
            }
            set(unicodeSet);
        }
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return (this.bmpSet == null && this.stringSpan == null) ? false : true;
    }

    @Override // com.ibm.icu.util.Freezable
    public UnicodeSet freeze() {
        if (!isFrozen()) {
            compact();
            if (hasStrings()) {
                this.stringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), 127);
            }
            if (this.stringSpan == null || !this.stringSpan.needsStringSpanUTF16()) {
                this.bmpSet = new BMPSet(this.list, this.len);
            }
        }
        return this;
    }

    public int span(CharSequence charSequence, SpanCondition spanCondition) {
        return span(charSequence, 0, spanCondition);
    }

    public int span(CharSequence charSequence, int i, SpanCondition spanCondition) {
        int length = charSequence.length();
        if (i < 0) {
            i = 0;
        } else if (i >= length) {
            return length;
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(charSequence, i, spanCondition, null);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.span(charSequence, i, spanCondition);
        }
        if (hasStrings()) {
            UnicodeSetStringSpan unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34);
            if (unicodeSetStringSpan.needsStringSpanUTF16()) {
                return unicodeSetStringSpan.span(charSequence, i, spanCondition);
            }
        }
        return spanCodePointsAndCount(charSequence, i, spanCondition, null);
    }

    @Deprecated
    public int spanAndCount(CharSequence charSequence, int i, SpanCondition spanCondition, OutputInt outputInt) {
        if (outputInt == null) {
            throw new IllegalArgumentException("outCount must not be null");
        }
        int length = charSequence.length();
        if (i < 0) {
            i = 0;
        } else if (i >= length) {
            return length;
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanAndCount(charSequence, i, spanCondition, outputInt);
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(charSequence, i, spanCondition, outputInt);
        }
        if (hasStrings()) {
            return new UnicodeSetStringSpan(this, new ArrayList(this.strings), (spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34) | 64).spanAndCount(charSequence, i, spanCondition, outputInt);
        }
        return spanCodePointsAndCount(charSequence, i, spanCondition, outputInt);
    }

    private int spanCodePointsAndCount(CharSequence charSequence, int i, SpanCondition spanCondition, OutputInt outputInt) {
        int i2 = 0;
        boolean z = spanCondition != SpanCondition.NOT_CONTAINED;
        int length = charSequence.length();
        do {
            int iCodePointAt = Character.codePointAt(charSequence, i);
            if (z != contains(iCodePointAt)) {
                break;
            }
            i2++;
            i += Character.charCount(iCodePointAt);
        } while (i < length);
        if (outputInt != null) {
            outputInt.value = i2;
        }
        return i;
    }

    public int spanBack(CharSequence charSequence, SpanCondition spanCondition) {
        return spanBack(charSequence, charSequence.length(), spanCondition);
    }

    public int spanBack(CharSequence charSequence, int i, SpanCondition spanCondition) {
        if (i <= 0) {
            return 0;
        }
        if (i > charSequence.length()) {
            i = charSequence.length();
        }
        if (this.bmpSet != null) {
            return this.bmpSet.spanBack(charSequence, i, spanCondition);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanBack(charSequence, i, spanCondition);
        }
        if (hasStrings()) {
            UnicodeSetStringSpan unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), spanCondition == SpanCondition.NOT_CONTAINED ? 17 : 18);
            if (unicodeSetStringSpan.needsStringSpanUTF16()) {
                return unicodeSetStringSpan.spanBack(charSequence, i, spanCondition);
            }
        }
        boolean z = spanCondition != SpanCondition.NOT_CONTAINED;
        do {
            int iCodePointBefore = Character.codePointBefore(charSequence, i);
            if (z != contains(iCodePointBefore)) {
                break;
            }
            i -= Character.charCount(iCodePointBefore);
        } while (i > 0);
        return i;
    }

    @Override // com.ibm.icu.util.Freezable
    public UnicodeSet cloneAsThawed() {
        return new UnicodeSet(this);
    }

    private void checkFrozen() {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }

    public static class EntryRange {
        public int codepoint;
        public int codepointEnd;

        EntryRange() {
        }

        public String toString() {
            StringBuilder sb;
            StringBuilder sb2 = new StringBuilder();
            int i = this.codepoint;
            if (i == this.codepointEnd) {
                sb = (StringBuilder) UnicodeSet._appendToPat(sb2, i, false);
            } else {
                StringBuilder sb3 = (StringBuilder) UnicodeSet._appendToPat(sb2, i, false);
                sb3.append(Soundex.SILENT_MARKER);
                sb = (StringBuilder) UnicodeSet._appendToPat(sb3, this.codepointEnd, false);
            }
            return sb.toString();
        }
    }

    public Iterable<EntryRange> ranges() {
        return new EntryRangeIterable();
    }

    private class EntryRangeIterable implements Iterable<EntryRange> {
        private EntryRangeIterable() {
        }

        @Override // java.lang.Iterable
        public Iterator<EntryRange> iterator() {
            return new EntryRangeIterator();
        }
    }

    private class EntryRangeIterator implements Iterator<EntryRange> {
        int pos;
        EntryRange result;

        private EntryRangeIterator() {
            this.result = new EntryRange();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.pos < UnicodeSet.this.len - 1;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public EntryRange next() {
            if (this.pos < UnicodeSet.this.len - 1) {
                EntryRange entryRange = this.result;
                int[] iArr = UnicodeSet.this.list;
                int i = this.pos;
                this.pos = i + 1;
                entryRange.codepoint = iArr[i];
                EntryRange entryRange2 = this.result;
                int[] iArr2 = UnicodeSet.this.list;
                this.pos = this.pos + 1;
                entryRange2.codepointEnd = iArr2[r2] - 1;
                return this.result;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.lang.Iterable
    public Iterator<String> iterator() {
        return new UnicodeSetIterator2(this);
    }

    private static class UnicodeSetIterator2 implements Iterator<String> {
        private char[] buffer;
        private int current;
        private int item;
        private int len;
        private int limit;
        private int[] sourceList;
        private SortedSet<String> sourceStrings;
        private Iterator<String> stringIterator;

        UnicodeSetIterator2(UnicodeSet unicodeSet) {
            int i = unicodeSet.len - 1;
            this.len = i;
            if (i > 0) {
                this.sourceStrings = unicodeSet.strings;
                int[] iArr = unicodeSet.list;
                this.sourceList = iArr;
                int i2 = this.item;
                int i3 = i2 + 1;
                this.item = i3;
                this.current = iArr[i2];
                this.item = i3 + 1;
                this.limit = iArr[i3];
                return;
            }
            this.stringIterator = unicodeSet.strings.iterator();
            this.sourceList = null;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.sourceList != null || this.stringIterator.hasNext();
        }

        @Override // java.util.Iterator
        public String next() {
            int[] iArr = this.sourceList;
            if (iArr == null) {
                return this.stringIterator.next();
            }
            int i = this.current;
            int i2 = i + 1;
            this.current = i2;
            if (i2 >= this.limit) {
                int i3 = this.item;
                if (i3 >= this.len) {
                    this.stringIterator = this.sourceStrings.iterator();
                    this.sourceList = null;
                } else {
                    int i4 = i3 + 1;
                    this.item = i4;
                    this.current = iArr[i3];
                    this.item = i4 + 1;
                    this.limit = iArr[i4];
                }
            }
            if (i <= 65535) {
                return String.valueOf((char) i);
            }
            if (this.buffer == null) {
                this.buffer = new char[2];
            }
            int i5 = i - 65536;
            char[] cArr = this.buffer;
            cArr[0] = (char) ((i5 >>> 10) + 55296);
            cArr[1] = (char) ((i5 & 1023) + UTF16.TRAIL_SURROGATE_MIN_VALUE);
            return String.valueOf(cArr);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public <T extends CharSequence> boolean containsAll(Iterable<T> iterable) {
        Iterator<T> it2 = iterable.iterator();
        while (it2.hasNext()) {
            if (!contains(it2.next())) {
                return false;
            }
        }
        return true;
    }

    public <T extends CharSequence> boolean containsNone(Iterable<T> iterable) {
        Iterator<T> it2 = iterable.iterator();
        while (it2.hasNext()) {
            if (contains(it2.next())) {
                return false;
            }
        }
        return true;
    }

    public final <T extends CharSequence> boolean containsSome(Iterable<T> iterable) {
        return !containsNone(iterable);
    }

    public <T extends CharSequence> UnicodeSet addAll(T... tArr) {
        checkFrozen();
        for (T t : tArr) {
            add(t);
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet removeAll(Iterable<T> iterable) {
        checkFrozen();
        Iterator<T> it2 = iterable.iterator();
        while (it2.hasNext()) {
            remove(it2.next());
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet retainAll(Iterable<T> iterable) {
        checkFrozen();
        UnicodeSet unicodeSet = new UnicodeSet();
        unicodeSet.addAll((Iterable<?>) iterable);
        retainAll(unicodeSet);
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(UnicodeSet unicodeSet) {
        return compareTo(unicodeSet, ComparisonStyle.SHORTER_FIRST);
    }

    public int compareTo(UnicodeSet unicodeSet, ComparisonStyle comparisonStyle) {
        int iCompare;
        int size;
        if (comparisonStyle != ComparisonStyle.LEXICOGRAPHIC && (size = size() - unicodeSet.size()) != 0) {
            return (size < 0) == (comparisonStyle == ComparisonStyle.SHORTER_FIRST) ? -1 : 1;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.list;
            int i2 = iArr[i];
            int[] iArr2 = unicodeSet.list;
            int i3 = i2 - iArr2[i];
            if (i3 != 0) {
                if (iArr[i] == 1114112) {
                    if (hasStrings()) {
                        return compare(this.strings.first(), unicodeSet.list[i]);
                    }
                    return 1;
                }
                if (iArr2[i] != 1114112) {
                    return (i & 1) == 0 ? i3 : -i3;
                }
                if (unicodeSet.hasStrings() && (iCompare = compare(unicodeSet.strings.first(), this.list[i])) <= 0) {
                    return iCompare < 0 ? 1 : 0;
                }
                return -1;
            }
            if (iArr[i] == 1114112) {
                return compare(this.strings, unicodeSet.strings);
            }
            i++;
        }
    }

    public int compareTo(Iterable<String> iterable) {
        return compare(this, iterable);
    }

    public static int compare(CharSequence charSequence, int i) {
        return CharSequences.compare(charSequence, i);
    }

    public static int compare(int i, CharSequence charSequence) {
        return -CharSequences.compare(charSequence, i);
    }

    public static <T extends Comparable<T>> int compare(Iterable<T> iterable, Iterable<T> iterable2) {
        return compare(iterable.iterator(), iterable2.iterator());
    }

    @Deprecated
    public static <T extends Comparable<T>> int compare(Iterator<T> it2, Iterator<T> it3) {
        while (it2.hasNext()) {
            if (!it3.hasNext()) {
                return 1;
            }
            int iCompareTo = it2.next().compareTo(it3.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return it3.hasNext() ? -1 : 0;
    }

    public static <T extends Comparable<T>> int compare(Collection<T> collection, Collection<T> collection2, ComparisonStyle comparisonStyle) {
        int size;
        if (comparisonStyle == ComparisonStyle.LEXICOGRAPHIC || (size = collection.size() - collection2.size()) == 0) {
            return compare(collection, collection2);
        }
        return (size < 0) == (comparisonStyle == ComparisonStyle.SHORTER_FIRST) ? -1 : 1;
    }

    public static <T, U extends Collection<T>> U addAllTo(Iterable<T> iterable, U u) {
        Iterator<T> it2 = iterable.iterator();
        while (it2.hasNext()) {
            u.add(it2.next());
        }
        return u;
    }

    public static <T> T[] addAllTo(Iterable<T> iterable, T[] tArr) {
        Iterator<T> it2 = iterable.iterator();
        int i = 0;
        while (it2.hasNext()) {
            tArr[i] = it2.next();
            i++;
        }
        return tArr;
    }

    public Collection<String> strings() {
        if (hasStrings()) {
            return Collections.unmodifiableSortedSet(this.strings);
        }
        return EMPTY_STRINGS;
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence charSequence) {
        return CharSequences.getSingleCodePoint(charSequence);
    }

    @Deprecated
    public UnicodeSet addBridges(UnicodeSet unicodeSet) {
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(new UnicodeSet(this).complement());
        while (unicodeSetIterator.nextRange()) {
            if (unicodeSetIterator.codepoint != 0 && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING && unicodeSetIterator.codepointEnd != 1114111 && unicodeSet.contains(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd)) {
                add(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd);
            }
        }
        return this;
    }

    @Deprecated
    public int findIn(CharSequence charSequence, int i, boolean z) {
        while (i < charSequence.length()) {
            int iCharAt = UTF16.charAt(charSequence, i);
            if (contains(iCharAt) != z) {
                break;
            }
            i += UTF16.getCharCount(iCharAt);
        }
        return i;
    }

    @Deprecated
    public int findLastIn(CharSequence charSequence, int i, boolean z) {
        int charCount = i - 1;
        while (charCount >= 0) {
            int iCharAt = UTF16.charAt(charSequence, charCount);
            if (contains(iCharAt) != z) {
                break;
            }
            charCount -= UTF16.getCharCount(iCharAt);
        }
        if (charCount < 0) {
            return -1;
        }
        return charCount;
    }

    @Deprecated
    public String stripFrom(CharSequence charSequence, boolean z) {
        StringBuilder sb = new StringBuilder();
        int iFindIn = 0;
        while (iFindIn < charSequence.length()) {
            int iFindIn2 = findIn(charSequence, iFindIn, !z);
            sb.append(charSequence.subSequence(iFindIn, iFindIn2));
            iFindIn = findIn(charSequence, iFindIn2, z);
        }
        return sb.toString();
    }

    @Deprecated
    public static XSymbolTable getDefaultXSymbolTable() {
        return XSYMBOL_TABLE;
    }

    @Deprecated
    public static void setDefaultXSymbolTable(XSymbolTable xSymbolTable) {
        CharacterPropertiesImpl.clear();
        XSYMBOL_TABLE = xSymbolTable;
    }
}
