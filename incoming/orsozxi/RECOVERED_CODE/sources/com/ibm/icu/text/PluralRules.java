package com.ibm.icu.text;

import com.ibm.icu.impl.PluralRulesLoader;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.range.StandardPluralRanges;
import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.FormattedNumberRange;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class PluralRules implements Serializable {
    static final UnicodeSet ALLOWED_ID = new UnicodeSet("[a-z]").freeze();
    static final Pattern AND_SEPARATED;
    static final Pattern AT_SEPARATED;
    private static final String CATEGORY_SEPARATOR = ";  ";
    static final Pattern COMMA_SEPARATED;
    public static final PluralRules DEFAULT;
    private static final Rule DEFAULT_RULE;
    static final Pattern DOTDOT_SEPARATED;
    public static final String KEYWORD_FEW = "few";
    public static final String KEYWORD_MANY = "many";
    public static final String KEYWORD_ONE = "one";
    public static final String KEYWORD_OTHER = "other";
    public static final String KEYWORD_TWO = "two";
    public static final String KEYWORD_ZERO = "zero";
    private static final Constraint NO_CONSTRAINT;
    public static final double NO_UNIQUE_VALUE = -0.00123456777d;
    static final Pattern OR_SEPARATED;
    static final Pattern SEMI_SEPARATED;
    static final Pattern TILDE_SEPARATED;
    private static final long serialVersionUID = 1;
    private final transient Set<String> keywords;
    private final RuleList rules;
    private final transient StandardPluralRanges standardPluralRanges;

    private interface Constraint extends Serializable {
        boolean isFulfilled(IFixedDecimal iFixedDecimal);

        boolean isLimited(SampleType sampleType);
    }

    @Deprecated
    public interface IFixedDecimal {
        @Deprecated
        double getPluralOperand(Operand operand);

        @Deprecated
        boolean isHasIntegerValue();

        @Deprecated
        boolean isInfinite();

        @Deprecated
        boolean isNaN();
    }

    public enum KeywordStatus {
        INVALID,
        SUPPRESSED,
        UNIQUE,
        BOUNDED,
        UNBOUNDED
    }

    @Deprecated
    public enum Operand {
        n,
        i,
        f,
        t,
        v,
        w,
        e,
        c,
        j
    }

    public enum PluralType {
        CARDINAL,
        ORDINAL
    }

    @Deprecated
    public enum SampleType {
        INTEGER,
        DECIMAL
    }

    static {
        Constraint constraint = new Constraint() { // from class: com.ibm.icu.text.PluralRules.1
            private static final long serialVersionUID = 9163464945387899416L;

            @Override // com.ibm.icu.text.PluralRules.Constraint
            public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
                return true;
            }

            @Override // com.ibm.icu.text.PluralRules.Constraint
            public boolean isLimited(SampleType sampleType) {
                return false;
            }

            public String toString() {
                return "";
            }
        };
        NO_CONSTRAINT = constraint;
        DEFAULT_RULE = new Rule("other", constraint, null, null);
        DEFAULT = new PluralRules(new RuleList().addRule(DEFAULT_RULE), StandardPluralRanges.DEFAULT);
        AT_SEPARATED = Pattern.compile("\\s*\\Q\\E@\\s*");
        OR_SEPARATED = Pattern.compile("\\s*or\\s*");
        AND_SEPARATED = Pattern.compile("\\s*and\\s*");
        COMMA_SEPARATED = Pattern.compile("\\s*,\\s*");
        DOTDOT_SEPARATED = Pattern.compile("\\s*\\Q..\\E\\s*");
        TILDE_SEPARATED = Pattern.compile("\\s*~\\s*");
        SEMI_SEPARATED = Pattern.compile("\\s*;\\s*");
    }

    @Deprecated
    public static abstract class Factory {
        @Deprecated
        public abstract PluralRules forLocale(ULocale uLocale, PluralType pluralType);

        @Deprecated
        public abstract ULocale[] getAvailableULocales();

        @Deprecated
        public abstract ULocale getFunctionalEquivalent(ULocale uLocale, boolean[] zArr);

        @Deprecated
        public abstract boolean hasOverride(ULocale uLocale);

        @Deprecated
        protected Factory() {
        }

        @Deprecated
        public final PluralRules forLocale(ULocale uLocale) {
            return forLocale(uLocale, PluralType.CARDINAL);
        }

        @Deprecated
        public static PluralRulesLoader getDefaultFactory() {
            return PluralRulesLoader.loader;
        }
    }

    public static PluralRules parseDescription(String str) throws ParseException {
        return newInternal(str, null);
    }

    public static PluralRules createRules(String str) {
        try {
            return parseDescription(str);
        } catch (Exception unused) {
            return null;
        }
    }

    @Deprecated
    public static PluralRules newInternal(String str, StandardPluralRanges standardPluralRanges) throws ParseException {
        String strTrim = str.trim();
        return strTrim.length() == 0 ? DEFAULT : new PluralRules(parseRuleChain(strTrim), standardPluralRanges);
    }

    @Deprecated
    public static class FixedDecimal extends Number implements Comparable<FixedDecimal>, IFixedDecimal {
        static final long MAX = 1000000000000000000L;
        private static final long MAX_INTEGER_PART = 1000000000;
        private static final long serialVersionUID = -4756200506571685661L;
        private final int baseFactor;
        final long decimalDigits;
        final long decimalDigitsWithoutTrailingZeros;
        final int exponent;
        final boolean hasIntegerValue;
        final long integerValue;
        final boolean isNegative;
        final double source;
        final int visibleDecimalDigitCount;
        final int visibleDecimalDigitCountWithoutTrailingZeros;

        @Deprecated
        public double getSource() {
            return this.source;
        }

        @Deprecated
        public int getVisibleDecimalDigitCount() {
            return this.visibleDecimalDigitCount;
        }

        @Deprecated
        public int getVisibleDecimalDigitCountWithoutTrailingZeros() {
            return this.visibleDecimalDigitCountWithoutTrailingZeros;
        }

        @Deprecated
        public long getDecimalDigits() {
            return this.decimalDigits;
        }

        @Deprecated
        public long getDecimalDigitsWithoutTrailingZeros() {
            return this.decimalDigitsWithoutTrailingZeros;
        }

        @Deprecated
        public long getIntegerValue() {
            return this.integerValue;
        }

        @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
        @Deprecated
        public boolean isHasIntegerValue() {
            return this.hasIntegerValue;
        }

        @Deprecated
        public boolean isNegative() {
            return this.isNegative;
        }

        @Deprecated
        public int getBaseFactor() {
            return this.baseFactor;
        }

        @Deprecated
        public FixedDecimal(double d, int i, long j, int i2, int i3) {
            boolean z = d < 0.0d;
            this.isNegative = z;
            this.source = z ? -d : d;
            this.visibleDecimalDigitCount = i;
            this.decimalDigits = j;
            this.integerValue = d > 1.0E18d ? MAX : (long) d;
            this.exponent = i2 == 0 ? i3 : i2;
            this.hasIntegerValue = this.source == ((double) this.integerValue);
            if (j == 0) {
                this.decimalDigitsWithoutTrailingZeros = 0L;
                this.visibleDecimalDigitCountWithoutTrailingZeros = 0;
            } else {
                int i4 = i;
                while (j % 10 == 0) {
                    j /= 10;
                    i4--;
                }
                this.decimalDigitsWithoutTrailingZeros = j;
                this.visibleDecimalDigitCountWithoutTrailingZeros = i4;
            }
            this.baseFactor = (int) Math.pow(10.0d, i);
        }

        @Deprecated
        public FixedDecimal(double d, int i, long j, int i2) {
            this(d, i, j, i2, i2);
        }

        @Deprecated
        public FixedDecimal(double d, int i, long j) {
            this(d, i, j, 0);
        }

        @Deprecated
        public static FixedDecimal createWithExponent(double d, int i, int i2) {
            return new FixedDecimal(d, i, getFractionalDigits(d, i), i2);
        }

        @Deprecated
        public FixedDecimal(double d, int i) {
            this(d, i, getFractionalDigits(d, i));
        }

        private static int getFractionalDigits(double d, int i) {
            if (i == 0) {
                return 0;
            }
            if (d < 0.0d) {
                d = -d;
            }
            int iPow = (int) Math.pow(10.0d, i);
            return (int) (Math.round(d * iPow) % iPow);
        }

        @Deprecated
        public FixedDecimal(double d) {
            this(d, decimals(d));
        }

        @Deprecated
        public FixedDecimal(long j) {
            this(j, 0);
        }

        @Deprecated
        public static int decimals(double d) {
            if (Double.isInfinite(d) || Double.isNaN(d)) {
                return 0;
            }
            if (d < 0.0d) {
                d = -d;
            }
            if (d == Math.floor(d)) {
                return 0;
            }
            if (d < 1.0E9d) {
                long j = ((long) (d * 1000000.0d)) % 1000000;
                int i = 10;
                for (int i2 = 6; i2 > 0; i2--) {
                    if (j % i != 0) {
                        return i2;
                    }
                    i *= 10;
                }
                return 0;
            }
            String str = String.format(Locale.ENGLISH, "%1.15e", Double.valueOf(d));
            int iLastIndexOf = str.lastIndexOf(101);
            int i3 = iLastIndexOf + 1;
            if (str.charAt(i3) == '+') {
                i3++;
            }
            int i4 = (iLastIndexOf - 2) - Integer.parseInt(str.substring(i3));
            if (i4 < 0) {
                return 0;
            }
            for (int i5 = iLastIndexOf - 1; i4 > 0 && str.charAt(i5) == '0'; i5--) {
                i4--;
            }
            return i4;
        }

        @Deprecated
        private FixedDecimal(FixedDecimal fixedDecimal) {
            this.source = fixedDecimal.source;
            this.visibleDecimalDigitCount = fixedDecimal.visibleDecimalDigitCount;
            this.visibleDecimalDigitCountWithoutTrailingZeros = fixedDecimal.visibleDecimalDigitCountWithoutTrailingZeros;
            this.decimalDigits = fixedDecimal.decimalDigits;
            this.decimalDigitsWithoutTrailingZeros = fixedDecimal.decimalDigitsWithoutTrailingZeros;
            this.integerValue = fixedDecimal.integerValue;
            this.hasIntegerValue = fixedDecimal.hasIntegerValue;
            this.isNegative = fixedDecimal.isNegative;
            this.exponent = fixedDecimal.exponent;
            this.baseFactor = fixedDecimal.baseFactor;
        }

        @Deprecated
        public FixedDecimal(String str) {
            this(parseDecimalSampleRangeNumString(str));
        }

        @Deprecated
        private static FixedDecimal parseDecimalSampleRangeNumString(String str) throws NumberFormatException {
            if (str.contains("e") || str.contains("c")) {
                int iLastIndexOf = str.lastIndexOf(101);
                if (iLastIndexOf < 0) {
                    iLastIndexOf = str.lastIndexOf(99);
                }
                int i = Integer.parseInt(str.substring(iLastIndexOf + 1));
                String strSubstring = str.substring(0, iLastIndexOf);
                return createWithExponent(Double.parseDouble(strSubstring), getVisibleFractionCount(strSubstring), i);
            }
            return new FixedDecimal(Double.parseDouble(str), getVisibleFractionCount(str));
        }

        private static int getVisibleFractionCount(String str) {
            String strTrim = str.trim();
            int iIndexOf = strTrim.indexOf(46) + 1;
            if (iIndexOf == 0) {
                return 0;
            }
            return strTrim.length() - iIndexOf;
        }

        @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
        @Deprecated
        public double getPluralOperand(Operand operand) {
            switch (AnonymousClass2.$SwitchMap$com$ibm$icu$text$PluralRules$Operand[operand.ordinal()]) {
                case 1:
                    return this.source;
                case 2:
                    return this.integerValue;
                case 3:
                    return this.decimalDigits;
                case 4:
                    return this.decimalDigitsWithoutTrailingZeros;
                case 5:
                    return this.visibleDecimalDigitCount;
                case 6:
                    return this.visibleDecimalDigitCountWithoutTrailingZeros;
                case 7:
                    return this.exponent;
                case 8:
                    return this.exponent;
                default:
                    return this.source;
            }
        }

        @Deprecated
        public static Operand getOperand(String str) {
            return Operand.valueOf(str);
        }

        @Override // java.lang.Comparable
        @Deprecated
        public int compareTo(FixedDecimal fixedDecimal) {
            if (this.exponent != fixedDecimal.exponent) {
                return doubleValue() < fixedDecimal.doubleValue() ? -1 : 1;
            }
            long j = this.integerValue;
            long j2 = fixedDecimal.integerValue;
            if (j != j2) {
                return j < j2 ? -1 : 1;
            }
            double d = this.source;
            double d2 = fixedDecimal.source;
            if (d != d2) {
                return d < d2 ? -1 : 1;
            }
            int i = this.visibleDecimalDigitCount;
            int i2 = fixedDecimal.visibleDecimalDigitCount;
            if (i != i2) {
                return i < i2 ? -1 : 1;
            }
            long j3 = this.decimalDigits - fixedDecimal.decimalDigits;
            if (j3 != 0) {
                return j3 < 0 ? -1 : 1;
            }
            return 0;
        }

        @Deprecated
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FixedDecimal)) {
                return false;
            }
            FixedDecimal fixedDecimal = (FixedDecimal) obj;
            return this.source == fixedDecimal.source && this.visibleDecimalDigitCount == fixedDecimal.visibleDecimalDigitCount && this.decimalDigits == fixedDecimal.decimalDigits && this.exponent == fixedDecimal.exponent;
        }

        @Deprecated
        public int hashCode() {
            return (int) (this.decimalDigits + ((this.visibleDecimalDigitCount + ((int) (this.source * 37.0d))) * 37));
        }

        @Deprecated
        public String toString() {
            String str = String.format(Locale.ROOT, "%." + this.visibleDecimalDigitCount + "f", Double.valueOf(this.source));
            if (this.exponent == 0) {
                return str;
            }
            return str + "e" + this.exponent;
        }

        @Deprecated
        public boolean hasIntegerValue() {
            return this.hasIntegerValue;
        }

        @Override // java.lang.Number
        @Deprecated
        public int intValue() {
            return (int) longValue();
        }

        @Override // java.lang.Number
        @Deprecated
        public long longValue() {
            int i = this.exponent;
            if (i == 0) {
                return this.integerValue;
            }
            return (long) (Math.pow(10.0d, i) * this.integerValue);
        }

        @Override // java.lang.Number
        @Deprecated
        public float floatValue() {
            return (float) (this.source * Math.pow(10.0d, this.exponent));
        }

        @Override // java.lang.Number
        @Deprecated
        public double doubleValue() {
            return (this.isNegative ? -this.source : this.source) * Math.pow(10.0d, this.exponent);
        }

        @Deprecated
        public long getShiftedValue() {
            return (this.integerValue * this.baseFactor) + this.decimalDigits;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            throw new NotSerializableException();
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new NotSerializableException();
        }

        @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
        @Deprecated
        public boolean isNaN() {
            return Double.isNaN(this.source);
        }

        @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
        @Deprecated
        public boolean isInfinite() {
            return Double.isInfinite(this.source);
        }
    }

    @Deprecated
    public static class FixedDecimalRange {

        @Deprecated
        public final FixedDecimal end;

        @Deprecated
        public final FixedDecimal start;

        @Deprecated
        public FixedDecimalRange(FixedDecimal fixedDecimal, FixedDecimal fixedDecimal2) {
            if (fixedDecimal.visibleDecimalDigitCount != fixedDecimal2.visibleDecimalDigitCount) {
                throw new IllegalArgumentException("Ranges must have the same number of visible decimals: " + fixedDecimal + "~" + fixedDecimal2);
            }
            this.start = fixedDecimal;
            this.end = fixedDecimal2;
        }

        @Deprecated
        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(this.start);
            if (this.end == this.start) {
                str = "";
            } else {
                str = "~" + this.end;
            }
            sb.append(str);
            return sb.toString();
        }
    }

    @Deprecated
    public static class FixedDecimalSamples {

        @Deprecated
        public final boolean bounded;

        @Deprecated
        public final SampleType sampleType;

        @Deprecated
        public final Set<FixedDecimalRange> samples;

        private FixedDecimalSamples(SampleType sampleType, Set<FixedDecimalRange> set, boolean z) {
            this.sampleType = sampleType;
            this.samples = set;
            this.bounded = z;
        }

        static FixedDecimalSamples parse(String str) {
            SampleType sampleType;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (str.startsWith("integer")) {
                sampleType = SampleType.INTEGER;
            } else if (str.startsWith("decimal")) {
                sampleType = SampleType.DECIMAL;
            } else {
                throw new IllegalArgumentException("Samples must start with 'integer' or 'decimal'");
            }
            boolean z = true;
            boolean z2 = false;
            for (String str2 : PluralRules.COMMA_SEPARATED.split(str.substring(7).trim())) {
                if (str2.equals("…") || str2.equals("...")) {
                    z = false;
                    z2 = true;
                } else {
                    if (z2) {
                        throw new IllegalArgumentException("Can only have … at the end of samples: " + str2);
                    }
                    String[] strArrSplit = PluralRules.TILDE_SEPARATED.split(str2);
                    int length = strArrSplit.length;
                    if (length == 1) {
                        FixedDecimal fixedDecimal = new FixedDecimal(strArrSplit[0]);
                        checkDecimal(sampleType, fixedDecimal);
                        linkedHashSet.add(new FixedDecimalRange(fixedDecimal, fixedDecimal));
                    } else if (length == 2) {
                        FixedDecimal fixedDecimal2 = new FixedDecimal(strArrSplit[0]);
                        FixedDecimal fixedDecimal3 = new FixedDecimal(strArrSplit[1]);
                        checkDecimal(sampleType, fixedDecimal2);
                        checkDecimal(sampleType, fixedDecimal3);
                        linkedHashSet.add(new FixedDecimalRange(fixedDecimal2, fixedDecimal3));
                    } else {
                        throw new IllegalArgumentException("Ill-formed number range: " + str2);
                    }
                }
            }
            return new FixedDecimalSamples(sampleType, Collections.unmodifiableSet(linkedHashSet), z);
        }

        private static void checkDecimal(SampleType sampleType, FixedDecimal fixedDecimal) {
            if ((sampleType == SampleType.INTEGER) == (fixedDecimal.getVisibleDecimalDigitCount() == 0)) {
                return;
            }
            throw new IllegalArgumentException("Ill-formed number range: " + fixedDecimal);
        }

        @Deprecated
        public Set<Double> addSamples(Set<Double> set) {
            for (FixedDecimalRange fixedDecimalRange : this.samples) {
                long shiftedValue = fixedDecimalRange.end.getShiftedValue();
                for (long shiftedValue2 = fixedDecimalRange.start.getShiftedValue(); shiftedValue2 <= shiftedValue; shiftedValue2++) {
                    set.add(Double.valueOf(shiftedValue2 / fixedDecimalRange.start.baseFactor));
                }
            }
            return set;
        }

        @Deprecated
        public String toString() {
            StringBuilder sb = new StringBuilder("@");
            sb.append(this.sampleType.toString().toLowerCase(Locale.ENGLISH));
            boolean z = true;
            for (FixedDecimalRange fixedDecimalRange : this.samples) {
                if (z) {
                    z = false;
                } else {
                    sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
                }
                sb.append(TokenParser.SP);
                sb.append(fixedDecimalRange);
            }
            if (!this.bounded) {
                sb.append(", …");
            }
            return sb.toString();
        }

        @Deprecated
        public Set<FixedDecimalRange> getSamples() {
            return this.samples;
        }

        @Deprecated
        public void getStartEndSamples(Set<FixedDecimal> set) {
            for (FixedDecimalRange fixedDecimalRange : this.samples) {
                set.add(fixedDecimalRange.start);
                set.add(fixedDecimalRange.end);
            }
        }
    }

    static class SimpleTokenizer {
        static final UnicodeSet BREAK_AND_IGNORE = new UnicodeSet(9, 10, 12, 13, 32, 32).freeze();
        static final UnicodeSet BREAK_AND_KEEP = new UnicodeSet(33, 33, 37, 37, 44, 44, 46, 46, 61, 61).freeze();

        SimpleTokenizer() {
        }

        static String[] split(String str) {
            ArrayList arrayList = new ArrayList();
            int i = -1;
            for (int i2 = 0; i2 < str.length(); i2++) {
                char cCharAt = str.charAt(i2);
                if (BREAK_AND_IGNORE.contains(cCharAt)) {
                    if (i >= 0) {
                        arrayList.add(str.substring(i, i2));
                        i = -1;
                    }
                } else if (BREAK_AND_KEEP.contains(cCharAt)) {
                    if (i >= 0) {
                        arrayList.add(str.substring(i, i2));
                    }
                    arrayList.add(str.substring(i2, i2 + 1));
                    i = -1;
                } else if (i < 0) {
                    i = i2;
                }
            }
            if (i >= 0) {
                arrayList.add(str.substring(i));
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:144:0x021c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0179  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.ibm.icu.text.PluralRules.Constraint parseConstraint(java.lang.String r34) throws java.lang.NumberFormatException, java.text.ParseException {
        /*
            Method dump skipped, instructions count: 636
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.PluralRules.parseConstraint(java.lang.String):com.ibm.icu.text.PluralRules$Constraint");
    }

    private static ParseException unexpected(String str, String str2) {
        return new ParseException("unexpected token '" + str + "' in '" + str2 + "'", -1);
    }

    private static String nextToken(String[] strArr, int i, String str) throws ParseException {
        if (i < strArr.length) {
            return strArr[i];
        }
        throw new ParseException("missing token at end of '" + str + "'", -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Rule parseRule(String str) throws NumberFormatException, ParseException {
        FixedDecimalSamples fixedDecimalSamples;
        Constraint constraint;
        if (str.length() == 0) {
            return DEFAULT_RULE;
        }
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int iIndexOf = lowerCase.indexOf(58);
        if (iIndexOf == -1) {
            throw new ParseException("missing ':' in rule description '" + lowerCase + "'", 0);
        }
        String strTrim = lowerCase.substring(0, iIndexOf).trim();
        if (!isValidKeyword(strTrim)) {
            throw new ParseException("keyword '" + strTrim + " is not valid", 0);
        }
        String strTrim2 = lowerCase.substring(iIndexOf + 1).trim();
        String[] strArrSplit = AT_SEPARATED.split(strTrim2);
        int length = strArrSplit.length;
        FixedDecimalSamples fixedDecimalSamples2 = null;
        if (length == 1) {
            fixedDecimalSamples = null;
        } else if (length == 2) {
            fixedDecimalSamples = FixedDecimalSamples.parse(strArrSplit[1]);
            if (fixedDecimalSamples.sampleType != SampleType.DECIMAL) {
                fixedDecimalSamples2 = fixedDecimalSamples;
                fixedDecimalSamples = null;
            }
        } else if (length == 3) {
            fixedDecimalSamples2 = FixedDecimalSamples.parse(strArrSplit[1]);
            FixedDecimalSamples fixedDecimalSamples3 = FixedDecimalSamples.parse(strArrSplit[2]);
            if (fixedDecimalSamples2.sampleType != SampleType.INTEGER || fixedDecimalSamples3.sampleType != SampleType.DECIMAL) {
                throw new IllegalArgumentException("Must have @integer then @decimal in " + strTrim2);
            }
            fixedDecimalSamples = fixedDecimalSamples3;
        } else {
            throw new IllegalArgumentException("Too many samples in " + strTrim2);
        }
        boolean zEquals = strTrim.equals("other");
        if (zEquals != (strArrSplit[0].length() == 0)) {
            throw new IllegalArgumentException("The keyword 'other' must have no constraints, just samples.");
        }
        if (zEquals) {
            constraint = NO_CONSTRAINT;
        } else {
            constraint = parseConstraint(strArrSplit[0]);
        }
        return new Rule(strTrim, constraint, fixedDecimalSamples2, fixedDecimalSamples);
    }

    private static RuleList parseRuleChain(String str) throws NumberFormatException, ParseException {
        RuleList ruleList = new RuleList();
        if (str.endsWith(";")) {
            str = str.substring(0, str.length() - 1);
        }
        for (String str2 : SEMI_SEPARATED.split(str)) {
            Rule rule = parseRule(str2.trim());
            ruleList.hasExplicitBoundingInfo |= (rule.integerSamples == null && rule.decimalSamples == null) ? false : true;
            ruleList.addRule(rule);
        }
        return ruleList.finish();
    }

    private static class RangeConstraint implements Constraint, Serializable {
        private static final long serialVersionUID = 1;
        private final boolean inRange;
        private final boolean integersOnly;
        private final double lowerBound;
        private final int mod;
        private final Operand operand;
        private final long[] range_list;
        private final double upperBound;

        RangeConstraint(int i, boolean z, Operand operand, boolean z2, double d, double d2, long[] jArr) {
            this.mod = i;
            this.inRange = z;
            this.integersOnly = z2;
            this.lowerBound = d;
            this.upperBound = d2;
            this.range_list = jArr;
            this.operand = operand;
        }

        @Override // com.ibm.icu.text.PluralRules.Constraint
        public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
            double pluralOperand = iFixedDecimal.getPluralOperand(this.operand);
            if ((this.integersOnly && pluralOperand - ((long) pluralOperand) != 0.0d) || (this.operand == Operand.j && iFixedDecimal.getPluralOperand(Operand.v) != 0.0d)) {
                return !this.inRange;
            }
            int i = this.mod;
            if (i != 0) {
                pluralOperand %= i;
            }
            boolean z = pluralOperand >= this.lowerBound && pluralOperand <= this.upperBound;
            if (z && this.range_list != null) {
                z = false;
                int i2 = 0;
                while (!z) {
                    long[] jArr = this.range_list;
                    if (i2 >= jArr.length) {
                        break;
                    }
                    z = pluralOperand >= ((double) jArr[i2]) && pluralOperand <= ((double) jArr[i2 + 1]);
                    i2 += 2;
                }
            }
            return this.inRange == z;
        }

        @Override // com.ibm.icu.text.PluralRules.Constraint
        public boolean isLimited(SampleType sampleType) {
            double d = this.lowerBound;
            boolean z = (this.operand == Operand.v || this.operand == Operand.w || this.operand == Operand.f || this.operand == Operand.t) && this.inRange != ((d > this.upperBound ? 1 : (d == this.upperBound ? 0 : -1)) == 0 && (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1)) == 0);
            int i = AnonymousClass2.$SwitchMap$com$ibm$icu$text$PluralRules$SampleType[sampleType.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
                return (!z || this.operand == Operand.n || this.operand == Operand.j) && (this.integersOnly || this.lowerBound == this.upperBound) && this.mod == 0 && this.inRange;
            }
            if (z) {
                return true;
            }
            return (this.operand == Operand.n || this.operand == Operand.i || this.operand == Operand.j) && this.mod == 0 && this.inRange;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.lang.String toString() {
            /*
                r10 = this;
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                com.ibm.icu.text.PluralRules$Operand r0 = r10.operand
                r6.append(r0)
                int r0 = r10.mod
                if (r0 == 0) goto L18
                java.lang.String r0 = " % "
                r6.append(r0)
                int r0 = r10.mod
                r6.append(r0)
            L18:
                double r0 = r10.lowerBound
                double r2 = r10.upperBound
                r7 = 1
                r8 = 0
                int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r4 == 0) goto L24
                r0 = 1
                goto L25
            L24:
                r0 = 0
            L25:
                java.lang.String r1 = " = "
                java.lang.String r2 = " != "
                if (r0 != 0) goto L32
                boolean r0 = r10.inRange
                if (r0 == 0) goto L30
                goto L44
            L30:
                r1 = r2
                goto L44
            L32:
                boolean r0 = r10.integersOnly
                if (r0 == 0) goto L3b
                boolean r0 = r10.inRange
                if (r0 == 0) goto L30
                goto L44
            L3b:
                boolean r0 = r10.inRange
                if (r0 == 0) goto L42
                java.lang.String r1 = " within "
                goto L44
            L42:
                java.lang.String r1 = " not within "
            L44:
                r6.append(r1)
                long[] r0 = r10.range_list
                if (r0 == 0) goto L65
                r9 = 0
            L4c:
                long[] r0 = r10.range_list
                int r1 = r0.length
                if (r9 >= r1) goto L6e
                r1 = r0[r9]
                double r1 = (double) r1
                int r3 = r9 + 1
                r3 = r0[r3]
                double r3 = (double) r3
                if (r9 == 0) goto L5d
                r5 = 1
                goto L5e
            L5d:
                r5 = 0
            L5e:
                r0 = r6
                com.ibm.icu.text.PluralRules.access$500(r0, r1, r3, r5)
                int r9 = r9 + 2
                goto L4c
            L65:
                double r1 = r10.lowerBound
                double r3 = r10.upperBound
                r5 = 0
                r0 = r6
                com.ibm.icu.text.PluralRules.access$500(r0, r1, r3, r5)
            L6e:
                java.lang.String r0 = r6.toString()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.PluralRules.RangeConstraint.toString():java.lang.String");
        }
    }

    /* renamed from: com.ibm.icu.text.PluralRules$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$PluralRules$Operand;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$PluralRules$SampleType;

        static {
            int[] iArr = new int[SampleType.values().length];
            $SwitchMap$com$ibm$icu$text$PluralRules$SampleType = iArr;
            try {
                iArr[SampleType.INTEGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$SampleType[SampleType.DECIMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[Operand.values().length];
            $SwitchMap$com$ibm$icu$text$PluralRules$Operand = iArr2;
            try {
                iArr2[Operand.n.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.i.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.f.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.t.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.v.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.w.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.e.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[Operand.c.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addRange(StringBuilder sb, double d, double d2, boolean z) {
        if (z) {
            sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        }
        if (d == d2) {
            sb.append(format(d));
            return;
        }
        sb.append(format(d) + ".." + format(d2));
    }

    private static String format(double d) {
        long j = (long) d;
        return d == ((double) j) ? String.valueOf(j) : String.valueOf(d);
    }

    private static abstract class BinaryConstraint implements Constraint, Serializable {
        private static final long serialVersionUID = 1;
        protected final Constraint a;
        protected final Constraint b;

        protected BinaryConstraint(Constraint constraint, Constraint constraint2) {
            this.a = constraint;
            this.b = constraint2;
        }
    }

    private static class AndConstraint extends BinaryConstraint {
        private static final long serialVersionUID = 7766999779862263523L;

        AndConstraint(Constraint constraint, Constraint constraint2) {
            super(constraint, constraint2);
        }

        @Override // com.ibm.icu.text.PluralRules.Constraint
        public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
            return this.a.isFulfilled(iFixedDecimal) && this.b.isFulfilled(iFixedDecimal);
        }

        @Override // com.ibm.icu.text.PluralRules.Constraint
        public boolean isLimited(SampleType sampleType) {
            return this.a.isLimited(sampleType) || this.b.isLimited(sampleType);
        }

        public String toString() {
            return this.a.toString() + " and " + this.b.toString();
        }
    }

    private static class OrConstraint extends BinaryConstraint {
        private static final long serialVersionUID = 1405488568664762222L;

        OrConstraint(Constraint constraint, Constraint constraint2) {
            super(constraint, constraint2);
        }

        @Override // com.ibm.icu.text.PluralRules.Constraint
        public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
            return this.a.isFulfilled(iFixedDecimal) || this.b.isFulfilled(iFixedDecimal);
        }

        @Override // com.ibm.icu.text.PluralRules.Constraint
        public boolean isLimited(SampleType sampleType) {
            return this.a.isLimited(sampleType) && this.b.isLimited(sampleType);
        }

        public String toString() {
            return this.a.toString() + " or " + this.b.toString();
        }
    }

    private static class Rule implements Serializable {
        private static final long serialVersionUID = 1;
        private final Constraint constraint;
        private final FixedDecimalSamples decimalSamples;
        private final FixedDecimalSamples integerSamples;
        private final String keyword;

        public Rule(String str, Constraint constraint, FixedDecimalSamples fixedDecimalSamples, FixedDecimalSamples fixedDecimalSamples2) {
            this.keyword = str;
            this.constraint = constraint;
            this.integerSamples = fixedDecimalSamples;
            this.decimalSamples = fixedDecimalSamples2;
        }

        public Rule and(Constraint constraint) {
            return new Rule(this.keyword, new AndConstraint(this.constraint, constraint), this.integerSamples, this.decimalSamples);
        }

        public Rule or(Constraint constraint) {
            return new Rule(this.keyword, new OrConstraint(this.constraint, constraint), this.integerSamples, this.decimalSamples);
        }

        public String getKeyword() {
            return this.keyword;
        }

        public boolean appliesTo(IFixedDecimal iFixedDecimal) {
            return this.constraint.isFulfilled(iFixedDecimal);
        }

        public boolean isLimited(SampleType sampleType) {
            return this.constraint.isLimited(sampleType);
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(this.keyword);
            sb.append(": ");
            sb.append(this.constraint.toString());
            String str2 = "";
            if (this.integerSamples == null) {
                str = "";
            } else {
                str = " " + this.integerSamples.toString();
            }
            sb.append(str);
            if (this.decimalSamples != null) {
                str2 = " " + this.decimalSamples.toString();
            }
            sb.append(str2);
            return sb.toString();
        }

        public int hashCode() {
            return this.keyword.hashCode() ^ this.constraint.hashCode();
        }

        public String getConstraint() {
            return this.constraint.toString();
        }
    }

    private static class RuleList implements Serializable {
        private static final long serialVersionUID = 1;
        private boolean hasExplicitBoundingInfo;
        private final List<Rule> rules;

        private RuleList() {
            this.hasExplicitBoundingInfo = false;
            this.rules = new ArrayList();
        }

        public RuleList addRule(Rule rule) {
            String keyword = rule.getKeyword();
            Iterator<Rule> it2 = this.rules.iterator();
            while (it2.hasNext()) {
                if (keyword.equals(it2.next().getKeyword())) {
                    throw new IllegalArgumentException("Duplicate keyword: " + keyword);
                }
            }
            this.rules.add(rule);
            return this;
        }

        public RuleList finish() throws NumberFormatException, ParseException {
            Iterator<Rule> it2 = this.rules.iterator();
            Rule rule = null;
            while (it2.hasNext()) {
                Rule next = it2.next();
                if ("other".equals(next.getKeyword())) {
                    it2.remove();
                    rule = next;
                }
            }
            if (rule == null) {
                rule = PluralRules.parseRule("other:");
            }
            this.rules.add(rule);
            return this;
        }

        private Rule selectRule(IFixedDecimal iFixedDecimal) {
            for (Rule rule : this.rules) {
                if (rule.appliesTo(iFixedDecimal)) {
                    return rule;
                }
            }
            return null;
        }

        public String select(IFixedDecimal iFixedDecimal) {
            return (iFixedDecimal.isInfinite() || iFixedDecimal.isNaN()) ? "other" : selectRule(iFixedDecimal).getKeyword();
        }

        public Set<String> getKeywords() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<Rule> it2 = this.rules.iterator();
            while (it2.hasNext()) {
                linkedHashSet.add(it2.next().getKeyword());
            }
            return linkedHashSet;
        }

        public boolean isLimited(String str, SampleType sampleType) {
            if (this.hasExplicitBoundingInfo) {
                FixedDecimalSamples decimalSamples = getDecimalSamples(str, sampleType);
                if (decimalSamples == null) {
                    return true;
                }
                return decimalSamples.bounded;
            }
            return computeLimited(str, sampleType);
        }

        public boolean computeLimited(String str, SampleType sampleType) {
            boolean z = false;
            for (Rule rule : this.rules) {
                if (str.equals(rule.getKeyword())) {
                    if (!rule.isLimited(sampleType)) {
                        return false;
                    }
                    z = true;
                }
            }
            return z;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Rule rule : this.rules) {
                if (sb.length() != 0) {
                    sb.append(PluralRules.CATEGORY_SEPARATOR);
                }
                sb.append(rule);
            }
            return sb.toString();
        }

        public String getRules(String str) {
            for (Rule rule : this.rules) {
                if (rule.getKeyword().equals(str)) {
                    return rule.getConstraint();
                }
            }
            return null;
        }

        public boolean select(IFixedDecimal iFixedDecimal, String str) {
            for (Rule rule : this.rules) {
                if (rule.getKeyword().equals(str) && rule.appliesTo(iFixedDecimal)) {
                    return true;
                }
            }
            return false;
        }

        public FixedDecimalSamples getDecimalSamples(String str, SampleType sampleType) {
            for (Rule rule : this.rules) {
                if (rule.getKeyword().equals(str)) {
                    return sampleType == SampleType.INTEGER ? rule.integerSamples : rule.decimalSamples;
                }
            }
            return null;
        }
    }

    private boolean addConditional(Set<IFixedDecimal> set, Set<IFixedDecimal> set2, double d) {
        FixedDecimal fixedDecimal = new FixedDecimal(d);
        if (set.contains(fixedDecimal) || set2.contains(fixedDecimal)) {
            return false;
        }
        set2.add(fixedDecimal);
        return true;
    }

    public static PluralRules forLocale(ULocale uLocale) {
        return Factory.getDefaultFactory().forLocale(uLocale, PluralType.CARDINAL);
    }

    public static PluralRules forLocale(Locale locale) {
        return forLocale(ULocale.forLocale(locale));
    }

    public static PluralRules forLocale(ULocale uLocale, PluralType pluralType) {
        return Factory.getDefaultFactory().forLocale(uLocale, pluralType);
    }

    public static PluralRules forLocale(Locale locale, PluralType pluralType) {
        return forLocale(ULocale.forLocale(locale), pluralType);
    }

    private static boolean isValidKeyword(String str) {
        return ALLOWED_ID.containsAll(str);
    }

    private PluralRules(RuleList ruleList, StandardPluralRanges standardPluralRanges) {
        this.rules = ruleList;
        this.keywords = Collections.unmodifiableSet(ruleList.getKeywords());
        this.standardPluralRanges = standardPluralRanges;
    }

    public int hashCode() {
        return this.rules.hashCode();
    }

    public String select(double d) {
        return this.rules.select(new FixedDecimal(d));
    }

    public String select(FormattedNumber formattedNumber) {
        return this.rules.select(formattedNumber.getFixedDecimal());
    }

    public String select(FormattedNumberRange formattedNumberRange) {
        if (this.standardPluralRanges == null) {
            throw new UnsupportedOperationException("Plural ranges are unavailable on this instance");
        }
        return this.standardPluralRanges.resolve(StandardPlural.fromString(select(formattedNumberRange.getFirstFixedDecimal())), StandardPlural.fromString(select(formattedNumberRange.getSecondFixedDecimal()))).getKeyword();
    }

    @Deprecated
    public String select(double d, int i, long j) {
        return this.rules.select(new FixedDecimal(d, i, j));
    }

    @Deprecated
    public String select(IFixedDecimal iFixedDecimal) {
        return this.rules.select(iFixedDecimal);
    }

    @Deprecated
    public boolean matches(FixedDecimal fixedDecimal, String str) {
        return this.rules.select(fixedDecimal, str);
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getUniqueKeywordValue(String str) {
        Collection<Double> allKeywordValues = getAllKeywordValues(str);
        if (allKeywordValues == null || allKeywordValues.size() != 1) {
            return -0.00123456777d;
        }
        return allKeywordValues.iterator().next().doubleValue();
    }

    public Collection<Double> getAllKeywordValues(String str) {
        return getAllKeywordValues(str, SampleType.INTEGER);
    }

    @Deprecated
    public Collection<Double> getAllKeywordValues(String str, SampleType sampleType) {
        Collection<Double> samples;
        if (isLimited(str, sampleType) && (samples = getSamples(str, sampleType)) != null) {
            return Collections.unmodifiableCollection(samples);
        }
        return null;
    }

    public Collection<Double> getSamples(String str) {
        return getSamples(str, SampleType.INTEGER);
    }

    @Deprecated
    public Collection<Double> getSamples(String str, SampleType sampleType) {
        if (!this.keywords.contains(str)) {
            return null;
        }
        TreeSet treeSet = new TreeSet();
        if (this.rules.hasExplicitBoundingInfo) {
            FixedDecimalSamples decimalSamples = this.rules.getDecimalSamples(str, sampleType);
            if (decimalSamples == null) {
                return Collections.unmodifiableSet(treeSet);
            }
            return Collections.unmodifiableSet(decimalSamples.addSamples(treeSet));
        }
        int i = isLimited(str, sampleType) ? Integer.MAX_VALUE : 20;
        int i2 = AnonymousClass2.$SwitchMap$com$ibm$icu$text$PluralRules$SampleType[sampleType.ordinal()];
        int i3 = 0;
        if (i2 == 1) {
            while (i3 < 200 && addSample(str, Integer.valueOf(i3), i, treeSet)) {
                i3++;
            }
            addSample(str, 1000000, i, treeSet);
        } else if (i2 == 2) {
            while (i3 < 2000 && addSample(str, new FixedDecimal(i3 / 10.0d, 1), i, treeSet)) {
                i3++;
            }
            addSample(str, new FixedDecimal(1000000.0d, 1), i, treeSet);
        }
        if (treeSet.size() == 0) {
            return null;
        }
        return Collections.unmodifiableSet(treeSet);
    }

    private boolean addSample(String str, Number number, int i, Set<Double> set) {
        if (!(number instanceof FixedDecimal ? select((FixedDecimal) number) : select(number.doubleValue())).equals(str)) {
            return true;
        }
        set.add(Double.valueOf(number.doubleValue()));
        return i + (-1) >= 0;
    }

    @Deprecated
    public FixedDecimalSamples getDecimalSamples(String str, SampleType sampleType) {
        return this.rules.getDecimalSamples(str, sampleType);
    }

    public static ULocale[] getAvailableULocales() {
        return Factory.getDefaultFactory().getAvailableULocales();
    }

    public static ULocale getFunctionalEquivalent(ULocale uLocale, boolean[] zArr) {
        return Factory.getDefaultFactory().getFunctionalEquivalent(uLocale, zArr);
    }

    public String toString() {
        return this.rules.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof PluralRules) && equals((PluralRules) obj);
    }

    public boolean equals(PluralRules pluralRules) {
        return pluralRules != null && toString().equals(pluralRules.toString());
    }

    public KeywordStatus getKeywordStatus(String str, int i, Set<Double> set, Output<Double> output) {
        return getKeywordStatus(str, i, set, output, SampleType.INTEGER);
    }

    /* JADX WARN: Type inference failed for: r7v13, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v8, types: [T, java.lang.Object] */
    @Deprecated
    public KeywordStatus getKeywordStatus(String str, int i, Set<Double> set, Output<Double> output, SampleType sampleType) {
        if (output != null) {
            output.value = null;
        }
        if (!this.keywords.contains(str)) {
            return KeywordStatus.INVALID;
        }
        if (!isLimited(str, sampleType)) {
            return KeywordStatus.UNBOUNDED;
        }
        Collection<Double> samples = getSamples(str, sampleType);
        int size = samples.size();
        if (set == null) {
            set = Collections.emptySet();
        }
        if (size > set.size()) {
            if (size == 1) {
                if (output != null) {
                    output.value = samples.iterator().next();
                }
                return KeywordStatus.UNIQUE;
            }
            return KeywordStatus.BOUNDED;
        }
        HashSet hashSet = new HashSet(samples);
        Iterator<Double> it2 = set.iterator();
        while (it2.hasNext()) {
            hashSet.remove(Double.valueOf(it2.next().doubleValue() - i));
        }
        if (hashSet.size() == 0) {
            return KeywordStatus.SUPPRESSED;
        }
        if (output != null && hashSet.size() == 1) {
            output.value = hashSet.iterator().next();
        }
        return size == 1 ? KeywordStatus.UNIQUE : KeywordStatus.BOUNDED;
    }

    @Deprecated
    public String getRules(String str) {
        return this.rules.getRules(str);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        throw new NotSerializableException();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        throw new NotSerializableException();
    }

    private Object writeReplace() throws ObjectStreamException {
        return new PluralRulesSerialProxy(toString());
    }

    @Deprecated
    public int compareTo(PluralRules pluralRules) {
        return toString().compareTo(pluralRules.toString());
    }

    Boolean isLimited(String str) {
        return Boolean.valueOf(this.rules.isLimited(str, SampleType.INTEGER));
    }

    @Deprecated
    public boolean isLimited(String str, SampleType sampleType) {
        return this.rules.isLimited(str, sampleType);
    }

    @Deprecated
    public boolean computeLimited(String str, SampleType sampleType) {
        return this.rules.computeLimited(str, sampleType);
    }
}
