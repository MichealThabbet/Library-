package com.ibm.icu.number;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.NoUnit;
import com.ibm.icu.util.StringTrieBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
class NumberSkeletonImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final char ALT_WILDCARD_CHAR = '+';
    static final char WILDCARD_CHAR = '*';
    static final StemEnum[] STEM_ENUM_VALUES = StemEnum.values();
    static final String SERIALIZED_STEM_TRIE = buildStemTrie();
    private static final CacheBase<String, UnlocalizedNumberFormatter, Void> cache = new SoftCache<String, UnlocalizedNumberFormatter, Void>() { // from class: com.ibm.icu.number.NumberSkeletonImpl.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public UnlocalizedNumberFormatter createInstance(String str, Void r2) {
            return NumberSkeletonImpl.create(str);
        }
    };

    enum ParseState {
        STATE_NULL,
        STATE_SCIENTIFIC,
        STATE_FRACTION_PRECISION,
        STATE_PRECISION,
        STATE_INCREMENT_PRECISION,
        STATE_MEASURE_UNIT,
        STATE_PER_MEASURE_UNIT,
        STATE_IDENTIFIER_UNIT,
        STATE_UNIT_USAGE,
        STATE_CURRENCY_UNIT,
        STATE_INTEGER_WIDTH,
        STATE_NUMBERING_SYSTEM,
        STATE_SCALE
    }

    enum StemEnum {
        STEM_COMPACT_SHORT,
        STEM_COMPACT_LONG,
        STEM_SCIENTIFIC,
        STEM_ENGINEERING,
        STEM_NOTATION_SIMPLE,
        STEM_BASE_UNIT,
        STEM_PERCENT,
        STEM_PERMILLE,
        STEM_PERCENT_100,
        STEM_PRECISION_INTEGER,
        STEM_PRECISION_UNLIMITED,
        STEM_PRECISION_CURRENCY_STANDARD,
        STEM_PRECISION_CURRENCY_CASH,
        STEM_ROUNDING_MODE_CEILING,
        STEM_ROUNDING_MODE_FLOOR,
        STEM_ROUNDING_MODE_DOWN,
        STEM_ROUNDING_MODE_UP,
        STEM_ROUNDING_MODE_HALF_EVEN,
        STEM_ROUNDING_MODE_HALF_DOWN,
        STEM_ROUNDING_MODE_HALF_UP,
        STEM_ROUNDING_MODE_UNNECESSARY,
        STEM_GROUP_OFF,
        STEM_GROUP_MIN2,
        STEM_GROUP_AUTO,
        STEM_GROUP_ON_ALIGNED,
        STEM_GROUP_THOUSANDS,
        STEM_LATIN,
        STEM_UNIT_WIDTH_NARROW,
        STEM_UNIT_WIDTH_SHORT,
        STEM_UNIT_WIDTH_FULL_NAME,
        STEM_UNIT_WIDTH_ISO_CODE,
        STEM_UNIT_WIDTH_FORMAL,
        STEM_UNIT_WIDTH_VARIANT,
        STEM_UNIT_WIDTH_HIDDEN,
        STEM_SIGN_AUTO,
        STEM_SIGN_ALWAYS,
        STEM_SIGN_NEVER,
        STEM_SIGN_ACCOUNTING,
        STEM_SIGN_ACCOUNTING_ALWAYS,
        STEM_SIGN_EXCEPT_ZERO,
        STEM_SIGN_ACCOUNTING_EXCEPT_ZERO,
        STEM_SIGN_NEGATIVE,
        STEM_SIGN_ACCOUNTING_NEGATIVE,
        STEM_DECIMAL_AUTO,
        STEM_DECIMAL_ALWAYS,
        STEM_PRECISION_INCREMENT,
        STEM_MEASURE_UNIT,
        STEM_PER_MEASURE_UNIT,
        STEM_UNIT,
        STEM_UNIT_USAGE,
        STEM_CURRENCY,
        STEM_INTEGER_WIDTH,
        STEM_NUMBERING_SYSTEM,
        STEM_SCALE
    }

    static boolean isWildcardChar(char c) {
        return c == '*' || c == '+';
    }

    NumberSkeletonImpl() {
    }

    static String buildStemTrie() {
        CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
        charsTrieBuilder.add("compact-short", StemEnum.STEM_COMPACT_SHORT.ordinal());
        charsTrieBuilder.add("compact-long", StemEnum.STEM_COMPACT_LONG.ordinal());
        charsTrieBuilder.add("scientific", StemEnum.STEM_SCIENTIFIC.ordinal());
        charsTrieBuilder.add("engineering", StemEnum.STEM_ENGINEERING.ordinal());
        charsTrieBuilder.add("notation-simple", StemEnum.STEM_NOTATION_SIMPLE.ordinal());
        charsTrieBuilder.add("base-unit", StemEnum.STEM_BASE_UNIT.ordinal());
        charsTrieBuilder.add("percent", StemEnum.STEM_PERCENT.ordinal());
        charsTrieBuilder.add("permille", StemEnum.STEM_PERMILLE.ordinal());
        charsTrieBuilder.add("precision-integer", StemEnum.STEM_PRECISION_INTEGER.ordinal());
        charsTrieBuilder.add("precision-unlimited", StemEnum.STEM_PRECISION_UNLIMITED.ordinal());
        charsTrieBuilder.add("precision-currency-standard", StemEnum.STEM_PRECISION_CURRENCY_STANDARD.ordinal());
        charsTrieBuilder.add("precision-currency-cash", StemEnum.STEM_PRECISION_CURRENCY_CASH.ordinal());
        charsTrieBuilder.add("rounding-mode-ceiling", StemEnum.STEM_ROUNDING_MODE_CEILING.ordinal());
        charsTrieBuilder.add("rounding-mode-floor", StemEnum.STEM_ROUNDING_MODE_FLOOR.ordinal());
        charsTrieBuilder.add("rounding-mode-down", StemEnum.STEM_ROUNDING_MODE_DOWN.ordinal());
        charsTrieBuilder.add("rounding-mode-up", StemEnum.STEM_ROUNDING_MODE_UP.ordinal());
        charsTrieBuilder.add("rounding-mode-half-even", StemEnum.STEM_ROUNDING_MODE_HALF_EVEN.ordinal());
        charsTrieBuilder.add("rounding-mode-half-down", StemEnum.STEM_ROUNDING_MODE_HALF_DOWN.ordinal());
        charsTrieBuilder.add("rounding-mode-half-up", StemEnum.STEM_ROUNDING_MODE_HALF_UP.ordinal());
        charsTrieBuilder.add("rounding-mode-unnecessary", StemEnum.STEM_ROUNDING_MODE_UNNECESSARY.ordinal());
        charsTrieBuilder.add("group-off", StemEnum.STEM_GROUP_OFF.ordinal());
        charsTrieBuilder.add("group-min2", StemEnum.STEM_GROUP_MIN2.ordinal());
        charsTrieBuilder.add("group-auto", StemEnum.STEM_GROUP_AUTO.ordinal());
        charsTrieBuilder.add("group-on-aligned", StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
        charsTrieBuilder.add("group-thousands", StemEnum.STEM_GROUP_THOUSANDS.ordinal());
        charsTrieBuilder.add("latin", StemEnum.STEM_LATIN.ordinal());
        charsTrieBuilder.add("unit-width-narrow", StemEnum.STEM_UNIT_WIDTH_NARROW.ordinal());
        charsTrieBuilder.add("unit-width-short", StemEnum.STEM_UNIT_WIDTH_SHORT.ordinal());
        charsTrieBuilder.add("unit-width-full-name", StemEnum.STEM_UNIT_WIDTH_FULL_NAME.ordinal());
        charsTrieBuilder.add("unit-width-iso-code", StemEnum.STEM_UNIT_WIDTH_ISO_CODE.ordinal());
        charsTrieBuilder.add("unit-width-formal", StemEnum.STEM_UNIT_WIDTH_FORMAL.ordinal());
        charsTrieBuilder.add("unit-width-variant", StemEnum.STEM_UNIT_WIDTH_VARIANT.ordinal());
        charsTrieBuilder.add("unit-width-hidden", StemEnum.STEM_UNIT_WIDTH_HIDDEN.ordinal());
        charsTrieBuilder.add("sign-auto", StemEnum.STEM_SIGN_AUTO.ordinal());
        charsTrieBuilder.add("sign-always", StemEnum.STEM_SIGN_ALWAYS.ordinal());
        charsTrieBuilder.add("sign-never", StemEnum.STEM_SIGN_NEVER.ordinal());
        charsTrieBuilder.add("sign-accounting", StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
        charsTrieBuilder.add("sign-accounting-always", StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
        charsTrieBuilder.add("sign-except-zero", StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("sign-accounting-except-zero", StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("sign-negative", StemEnum.STEM_SIGN_NEGATIVE.ordinal());
        charsTrieBuilder.add("sign-accounting-negative", StemEnum.STEM_SIGN_ACCOUNTING_NEGATIVE.ordinal());
        charsTrieBuilder.add("decimal-auto", StemEnum.STEM_DECIMAL_AUTO.ordinal());
        charsTrieBuilder.add("decimal-always", StemEnum.STEM_DECIMAL_ALWAYS.ordinal());
        charsTrieBuilder.add("precision-increment", StemEnum.STEM_PRECISION_INCREMENT.ordinal());
        charsTrieBuilder.add("measure-unit", StemEnum.STEM_MEASURE_UNIT.ordinal());
        charsTrieBuilder.add("per-measure-unit", StemEnum.STEM_PER_MEASURE_UNIT.ordinal());
        charsTrieBuilder.add("unit", StemEnum.STEM_UNIT.ordinal());
        charsTrieBuilder.add("usage", StemEnum.STEM_UNIT_USAGE.ordinal());
        charsTrieBuilder.add(FirebaseAnalytics.Param.CURRENCY, StemEnum.STEM_CURRENCY.ordinal());
        charsTrieBuilder.add("integer-width", StemEnum.STEM_INTEGER_WIDTH.ordinal());
        charsTrieBuilder.add("numbering-system", StemEnum.STEM_NUMBERING_SYSTEM.ordinal());
        charsTrieBuilder.add("scale", StemEnum.STEM_SCALE.ordinal());
        charsTrieBuilder.add("K", StemEnum.STEM_COMPACT_SHORT.ordinal());
        charsTrieBuilder.add("KK", StemEnum.STEM_COMPACT_LONG.ordinal());
        charsTrieBuilder.add("%", StemEnum.STEM_PERCENT.ordinal());
        charsTrieBuilder.add("%x100", StemEnum.STEM_PERCENT_100.ordinal());
        charsTrieBuilder.add(",_", StemEnum.STEM_GROUP_OFF.ordinal());
        charsTrieBuilder.add(",?", StemEnum.STEM_GROUP_MIN2.ordinal());
        charsTrieBuilder.add(",!", StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
        charsTrieBuilder.add("+!", StemEnum.STEM_SIGN_ALWAYS.ordinal());
        charsTrieBuilder.add("+_", StemEnum.STEM_SIGN_NEVER.ordinal());
        charsTrieBuilder.add("()", StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
        charsTrieBuilder.add("()!", StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
        charsTrieBuilder.add("+?", StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("()?", StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("+-", StemEnum.STEM_SIGN_NEGATIVE.ordinal());
        charsTrieBuilder.add("()-", StemEnum.STEM_SIGN_ACCOUNTING_NEGATIVE.ordinal());
        return charsTrieBuilder.buildCharSequence(StringTrieBuilder.Option.FAST).toString();
    }

    static final class StemToObject {
        StemToObject() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Notation notation(StemEnum stemEnum) {
            int i = AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
            if (i == 1) {
                return Notation.compactShort();
            }
            if (i == 2) {
                return Notation.compactLong();
            }
            if (i == 3) {
                return Notation.scientific();
            }
            if (i == 4) {
                return Notation.engineering();
            }
            if (i == 5) {
                return Notation.simple();
            }
            throw new AssertionError();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static MeasureUnit unit(StemEnum stemEnum) {
            int i = AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
            if (i == 6) {
                return NoUnit.BASE;
            }
            if (i == 7) {
                return NoUnit.PERCENT;
            }
            if (i == 8) {
                return NoUnit.PERMILLE;
            }
            throw new AssertionError();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Precision precision(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_PRECISION_INTEGER:
                    return Precision.integer();
                case STEM_PRECISION_UNLIMITED:
                    return Precision.unlimited();
                case STEM_PRECISION_CURRENCY_STANDARD:
                    return Precision.currency(Currency.CurrencyUsage.STANDARD);
                case STEM_PRECISION_CURRENCY_CASH:
                    return Precision.currency(Currency.CurrencyUsage.CASH);
                default:
                    throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static RoundingMode roundingMode(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_ROUNDING_MODE_CEILING:
                    return RoundingMode.CEILING;
                case STEM_ROUNDING_MODE_FLOOR:
                    return RoundingMode.FLOOR;
                case STEM_ROUNDING_MODE_DOWN:
                    return RoundingMode.DOWN;
                case STEM_ROUNDING_MODE_UP:
                    return RoundingMode.UP;
                case STEM_ROUNDING_MODE_HALF_EVEN:
                    return RoundingMode.HALF_EVEN;
                case STEM_ROUNDING_MODE_HALF_DOWN:
                    return RoundingMode.HALF_DOWN;
                case STEM_ROUNDING_MODE_HALF_UP:
                    return RoundingMode.HALF_UP;
                case STEM_ROUNDING_MODE_UNNECESSARY:
                    return RoundingMode.UNNECESSARY;
                default:
                    throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static NumberFormatter.GroupingStrategy groupingStrategy(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_GROUP_OFF:
                    return NumberFormatter.GroupingStrategy.OFF;
                case STEM_GROUP_MIN2:
                    return NumberFormatter.GroupingStrategy.MIN2;
                case STEM_GROUP_AUTO:
                    return NumberFormatter.GroupingStrategy.AUTO;
                case STEM_GROUP_ON_ALIGNED:
                    return NumberFormatter.GroupingStrategy.ON_ALIGNED;
                case STEM_GROUP_THOUSANDS:
                    return NumberFormatter.GroupingStrategy.THOUSANDS;
                default:
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static NumberFormatter.UnitWidth unitWidth(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_UNIT_WIDTH_NARROW:
                    return NumberFormatter.UnitWidth.NARROW;
                case STEM_UNIT_WIDTH_SHORT:
                    return NumberFormatter.UnitWidth.SHORT;
                case STEM_UNIT_WIDTH_FULL_NAME:
                    return NumberFormatter.UnitWidth.FULL_NAME;
                case STEM_UNIT_WIDTH_ISO_CODE:
                    return NumberFormatter.UnitWidth.ISO_CODE;
                case STEM_UNIT_WIDTH_FORMAL:
                    return NumberFormatter.UnitWidth.FORMAL;
                case STEM_UNIT_WIDTH_VARIANT:
                    return NumberFormatter.UnitWidth.VARIANT;
                case STEM_UNIT_WIDTH_HIDDEN:
                    return NumberFormatter.UnitWidth.HIDDEN;
                default:
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static NumberFormatter.SignDisplay signDisplay(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_SIGN_AUTO:
                    return NumberFormatter.SignDisplay.AUTO;
                case STEM_SIGN_ALWAYS:
                    return NumberFormatter.SignDisplay.ALWAYS;
                case STEM_SIGN_NEVER:
                    return NumberFormatter.SignDisplay.NEVER;
                case STEM_SIGN_ACCOUNTING:
                    return NumberFormatter.SignDisplay.ACCOUNTING;
                case STEM_SIGN_ACCOUNTING_ALWAYS:
                    return NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS;
                case STEM_SIGN_EXCEPT_ZERO:
                    return NumberFormatter.SignDisplay.EXCEPT_ZERO;
                case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO:
                    return NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
                case STEM_SIGN_NEGATIVE:
                    return NumberFormatter.SignDisplay.NEGATIVE;
                case STEM_SIGN_ACCOUNTING_NEGATIVE:
                    return NumberFormatter.SignDisplay.ACCOUNTING_NEGATIVE;
                default:
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay(StemEnum stemEnum) {
            int i = AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
            if (i == 42) {
                return NumberFormatter.DecimalSeparatorDisplay.AUTO;
            }
            if (i != 43) {
                return null;
            }
            return NumberFormatter.DecimalSeparatorDisplay.ALWAYS;
        }
    }

    static final class EnumToStemString {
        EnumToStemString() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void roundingMode(RoundingMode roundingMode, StringBuilder sb) {
            switch (AnonymousClass2.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
                case 1:
                    sb.append("rounding-mode-ceiling");
                    return;
                case 2:
                    sb.append("rounding-mode-floor");
                    return;
                case 3:
                    sb.append("rounding-mode-down");
                    return;
                case 4:
                    sb.append("rounding-mode-up");
                    return;
                case 5:
                    sb.append("rounding-mode-half-even");
                    return;
                case 6:
                    sb.append("rounding-mode-half-down");
                    return;
                case 7:
                    sb.append("rounding-mode-half-up");
                    return;
                case 8:
                    sb.append("rounding-mode-unnecessary");
                    return;
                default:
                    throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void groupingStrategy(NumberFormatter.GroupingStrategy groupingStrategy, StringBuilder sb) {
            int i = AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[groupingStrategy.ordinal()];
            if (i == 1) {
                sb.append("group-off");
                return;
            }
            if (i == 2) {
                sb.append("group-min2");
                return;
            }
            if (i == 3) {
                sb.append("group-auto");
            } else if (i == 4) {
                sb.append("group-on-aligned");
            } else {
                if (i == 5) {
                    sb.append("group-thousands");
                    return;
                }
                throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void unitWidth(NumberFormatter.UnitWidth unitWidth, StringBuilder sb) {
            switch (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[unitWidth.ordinal()]) {
                case 1:
                    sb.append("unit-width-narrow");
                    return;
                case 2:
                    sb.append("unit-width-short");
                    return;
                case 3:
                    sb.append("unit-width-full-name");
                    return;
                case 4:
                    sb.append("unit-width-iso-code");
                    return;
                case 5:
                    sb.append("unit-width-formal");
                    return;
                case 6:
                    sb.append("unit-width-variant");
                    return;
                case 7:
                    sb.append("unit-width-hidden");
                    return;
                default:
                    throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void signDisplay(NumberFormatter.SignDisplay signDisplay, StringBuilder sb) {
            switch (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[signDisplay.ordinal()]) {
                case 1:
                    sb.append("sign-auto");
                    return;
                case 2:
                    sb.append("sign-always");
                    return;
                case 3:
                    sb.append("sign-never");
                    return;
                case 4:
                    sb.append("sign-accounting");
                    return;
                case 5:
                    sb.append("sign-accounting-always");
                    return;
                case 6:
                    sb.append("sign-except-zero");
                    return;
                case 7:
                    sb.append("sign-accounting-except-zero");
                    return;
                case 8:
                    sb.append("sign-negative");
                    return;
                case 9:
                    sb.append("sign-accounting-negative");
                    return;
                default:
                    throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void decimalSeparatorDisplay(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay, StringBuilder sb) {
            int i = AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberFormatter$DecimalSeparatorDisplay[decimalSeparatorDisplay.ordinal()];
            if (i == 1) {
                sb.append("decimal-auto");
            } else {
                if (i == 2) {
                    sb.append("decimal-always");
                    return;
                }
                throw new AssertionError();
            }
        }
    }

    public static UnlocalizedNumberFormatter getOrCreate(String str) {
        return cache.getInstance(str, null);
    }

    public static UnlocalizedNumberFormatter create(String str) {
        return NumberFormatter.with().macros(parseSkeleton(str));
    }

    public static String generate(MacroProps macroProps) {
        StringBuilder sb = new StringBuilder();
        generateSkeleton(macroProps, sb);
        return sb.toString();
    }

    private static MacroProps parseSkeleton(String str) {
        MacroProps macroProps = new MacroProps();
        StringSegment stringSegment = new StringSegment(str + " ", false);
        CharsTrie charsTrie = new CharsTrie(SERIALIZED_STEM_TRIE, 0);
        ParseState option = ParseState.STATE_NULL;
        int iCharCount = 0;
        while (iCharCount < stringSegment.length()) {
            int iCodePointAt = stringSegment.codePointAt(iCharCount);
            boolean zIsWhiteSpace = PatternProps.isWhiteSpace(iCodePointAt);
            boolean z = iCodePointAt == 47;
            if (!zIsWhiteSpace && !z) {
                iCharCount += Character.charCount(iCodePointAt);
                if (option == ParseState.STATE_NULL) {
                    charsTrie.nextForCodePoint(iCodePointAt);
                }
            } else {
                if (iCharCount != 0) {
                    stringSegment.setLength(iCharCount);
                    if (option == ParseState.STATE_NULL) {
                        option = parseStem(stringSegment, charsTrie, macroProps);
                        charsTrie.reset();
                    } else {
                        option = parseOption(option, stringSegment, macroProps);
                    }
                    stringSegment.resetLength();
                    stringSegment.adjustOffset(iCharCount);
                    iCharCount = 0;
                } else if (option != ParseState.STATE_NULL) {
                    stringSegment.setLength(Character.charCount(iCodePointAt));
                    throw new SkeletonSyntaxException("Unexpected separator character", stringSegment);
                }
                if (z && option == ParseState.STATE_NULL) {
                    stringSegment.setLength(Character.charCount(iCodePointAt));
                    throw new SkeletonSyntaxException("Unexpected option separator", stringSegment);
                }
                if (zIsWhiteSpace && option != ParseState.STATE_NULL) {
                    switch (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[option.ordinal()]) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                            stringSegment.setLength(Character.charCount(iCodePointAt));
                            throw new SkeletonSyntaxException("Stem requires an option", stringSegment);
                        default:
                            option = ParseState.STATE_NULL;
                            break;
                    }
                }
                stringSegment.adjustOffset(Character.charCount(iCodePointAt));
            }
        }
        return macroProps;
    }

    /* renamed from: com.ibm.icu.number.NumberSkeletonImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$DecimalSeparatorDisplay;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState;
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[ParseState.values().length];
            $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState = iArr;
            try {
                iArr[ParseState.STATE_INCREMENT_PRECISION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_MEASURE_UNIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_PER_MEASURE_UNIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_IDENTIFIER_UNIT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_UNIT_USAGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_CURRENCY_UNIT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_INTEGER_WIDTH.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_NUMBERING_SYSTEM.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_SCALE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_SCIENTIFIC.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_FRACTION_PRECISION.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[ParseState.STATE_PRECISION.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            int[] iArr2 = new int[NumberFormatter.DecimalSeparatorDisplay.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$DecimalSeparatorDisplay = iArr2;
            try {
                iArr2[NumberFormatter.DecimalSeparatorDisplay.AUTO.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$DecimalSeparatorDisplay[NumberFormatter.DecimalSeparatorDisplay.ALWAYS.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr3 = new int[NumberFormatter.SignDisplay.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay = iArr3;
            try {
                iArr3[NumberFormatter.SignDisplay.AUTO.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ALWAYS.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.NEVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.EXCEPT_ZERO.ordinal()] = 6;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO.ordinal()] = 7;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.NEGATIVE.ordinal()] = 8;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING_NEGATIVE.ordinal()] = 9;
            } catch (NoSuchFieldError unused23) {
            }
            int[] iArr4 = new int[NumberFormatter.UnitWidth.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth = iArr4;
            try {
                iArr4[NumberFormatter.UnitWidth.NARROW.ordinal()] = 1;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.FULL_NAME.ordinal()] = 3;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.ISO_CODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.FORMAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.VARIANT.ordinal()] = 6;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.HIDDEN.ordinal()] = 7;
            } catch (NoSuchFieldError unused30) {
            }
            int[] iArr5 = new int[NumberFormatter.GroupingStrategy.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy = iArr5;
            try {
                iArr5[NumberFormatter.GroupingStrategy.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.MIN2.ordinal()] = 2;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.AUTO.ordinal()] = 3;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.ON_ALIGNED.ordinal()] = 4;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.THOUSANDS.ordinal()] = 5;
            } catch (NoSuchFieldError unused35) {
            }
            int[] iArr6 = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr6;
            try {
                iArr6[RoundingMode.CEILING.ordinal()] = 1;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 5;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 8;
            } catch (NoSuchFieldError unused43) {
            }
            int[] iArr7 = new int[StemEnum.values().length];
            $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum = iArr7;
            try {
                iArr7[StemEnum.STEM_COMPACT_SHORT.ordinal()] = 1;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_COMPACT_LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SCIENTIFIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ENGINEERING.ordinal()] = 4;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_NOTATION_SIMPLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_BASE_UNIT.ordinal()] = 6;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PERCENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PERMILLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PRECISION_INTEGER.ordinal()] = 9;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PRECISION_UNLIMITED.ordinal()] = 10;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PRECISION_CURRENCY_STANDARD.ordinal()] = 11;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PRECISION_CURRENCY_CASH.ordinal()] = 12;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_CEILING.ordinal()] = 13;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_FLOOR.ordinal()] = 14;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_DOWN.ordinal()] = 15;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_UP.ordinal()] = 16;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_HALF_EVEN.ordinal()] = 17;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_HALF_DOWN.ordinal()] = 18;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_HALF_UP.ordinal()] = 19;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_ROUNDING_MODE_UNNECESSARY.ordinal()] = 20;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_GROUP_OFF.ordinal()] = 21;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_GROUP_MIN2.ordinal()] = 22;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_GROUP_AUTO.ordinal()] = 23;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_GROUP_ON_ALIGNED.ordinal()] = 24;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_GROUP_THOUSANDS.ordinal()] = 25;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_NARROW.ordinal()] = 26;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_SHORT.ordinal()] = 27;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_FULL_NAME.ordinal()] = 28;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_ISO_CODE.ordinal()] = 29;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_FORMAL.ordinal()] = 30;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_VARIANT.ordinal()] = 31;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_WIDTH_HIDDEN.ordinal()] = 32;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_AUTO.ordinal()] = 33;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_ALWAYS.ordinal()] = 34;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_NEVER.ordinal()] = 35;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_ACCOUNTING.ordinal()] = 36;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal()] = 37;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal()] = 38;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal()] = 39;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_NEGATIVE.ordinal()] = 40;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SIGN_ACCOUNTING_NEGATIVE.ordinal()] = 41;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_DECIMAL_AUTO.ordinal()] = 42;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_DECIMAL_ALWAYS.ordinal()] = 43;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PERCENT_100.ordinal()] = 44;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_LATIN.ordinal()] = 45;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PRECISION_INCREMENT.ordinal()] = 46;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_MEASURE_UNIT.ordinal()] = 47;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_PER_MEASURE_UNIT.ordinal()] = 48;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT.ordinal()] = 49;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_UNIT_USAGE.ordinal()] = 50;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_CURRENCY.ordinal()] = 51;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_INTEGER_WIDTH.ordinal()] = 52;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_NUMBERING_SYSTEM.ordinal()] = 53;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[StemEnum.STEM_SCALE.ordinal()] = 54;
            } catch (NoSuchFieldError unused97) {
            }
        }
    }

    private static ParseState parseStem(StringSegment stringSegment, CharsTrie charsTrie, MacroProps macroProps) {
        char cCharAt = stringSegment.charAt(0);
        if (cCharAt == '.') {
            checkNull(macroProps.precision, stringSegment);
            BlueprintHelpers.parseFractionStem(stringSegment, macroProps);
            return ParseState.STATE_FRACTION_PRECISION;
        }
        if (cCharAt == '0') {
            checkNull(macroProps.integerWidth, stringSegment);
            BlueprintHelpers.parseIntegerStem(stringSegment, macroProps);
            return ParseState.STATE_NULL;
        }
        if (cCharAt == '@') {
            checkNull(macroProps.precision, stringSegment);
            BlueprintHelpers.parseDigitsStem(stringSegment, macroProps);
            return ParseState.STATE_PRECISION;
        }
        if (cCharAt == 'E') {
            checkNull(macroProps.notation, stringSegment);
            BlueprintHelpers.parseScientificStem(stringSegment, macroProps);
            return ParseState.STATE_NULL;
        }
        BytesTrie.Result resultCurrent = charsTrie.current();
        if (resultCurrent != BytesTrie.Result.INTERMEDIATE_VALUE && resultCurrent != BytesTrie.Result.FINAL_VALUE) {
            throw new SkeletonSyntaxException("Unknown stem", stringSegment);
        }
        StemEnum stemEnum = STEM_ENUM_VALUES[charsTrie.getValue()];
        switch (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                checkNull(macroProps.notation, stringSegment);
                macroProps.notation = StemToObject.notation(stemEnum);
                int i = AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
                if (i == 3 || i == 4) {
                    return ParseState.STATE_SCIENTIFIC;
                }
                return ParseState.STATE_NULL;
            case 6:
            case 7:
            case 8:
                checkNull(macroProps.unit, stringSegment);
                macroProps.unit = StemToObject.unit(stemEnum);
                return ParseState.STATE_NULL;
            case 9:
            case 10:
            case 11:
            case 12:
                checkNull(macroProps.precision, stringSegment);
                macroProps.precision = StemToObject.precision(stemEnum);
                if (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()] == 9) {
                    return ParseState.STATE_FRACTION_PRECISION;
                }
                return ParseState.STATE_PRECISION;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                checkNull(macroProps.roundingMode, stringSegment);
                macroProps.roundingMode = StemToObject.roundingMode(stemEnum);
                return ParseState.STATE_NULL;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                checkNull(macroProps.grouping, stringSegment);
                macroProps.grouping = StemToObject.groupingStrategy(stemEnum);
                return ParseState.STATE_NULL;
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                checkNull(macroProps.unitWidth, stringSegment);
                macroProps.unitWidth = StemToObject.unitWidth(stemEnum);
                return ParseState.STATE_NULL;
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
                checkNull(macroProps.sign, stringSegment);
                macroProps.sign = StemToObject.signDisplay(stemEnum);
                return ParseState.STATE_NULL;
            case 42:
            case 43:
                checkNull(macroProps.decimal, stringSegment);
                macroProps.decimal = StemToObject.decimalSeparatorDisplay(stemEnum);
                return ParseState.STATE_NULL;
            case 44:
                checkNull(macroProps.scale, stringSegment);
                checkNull(macroProps.unit, stringSegment);
                macroProps.scale = Scale.powerOfTen(2);
                macroProps.unit = NoUnit.PERCENT;
                return ParseState.STATE_NULL;
            case 45:
                checkNull(macroProps.symbols, stringSegment);
                macroProps.symbols = NumberingSystem.LATIN;
                return ParseState.STATE_NULL;
            case 46:
                checkNull(macroProps.precision, stringSegment);
                return ParseState.STATE_INCREMENT_PRECISION;
            case 47:
                checkNull(macroProps.unit, stringSegment);
                return ParseState.STATE_MEASURE_UNIT;
            case 48:
                if (macroProps.unit instanceof Currency) {
                    throw new SkeletonSyntaxException("Duplicated setting", stringSegment);
                }
                checkNull(macroProps.perUnit, stringSegment);
                return ParseState.STATE_PER_MEASURE_UNIT;
            case 49:
                checkNull(macroProps.unit, stringSegment);
                checkNull(macroProps.perUnit, stringSegment);
                return ParseState.STATE_IDENTIFIER_UNIT;
            case 50:
                checkNull(macroProps.usage, stringSegment);
                return ParseState.STATE_UNIT_USAGE;
            case 51:
                checkNull(macroProps.unit, stringSegment);
                checkNull(macroProps.perUnit, stringSegment);
                return ParseState.STATE_CURRENCY_UNIT;
            case 52:
                checkNull(macroProps.integerWidth, stringSegment);
                return ParseState.STATE_INTEGER_WIDTH;
            case 53:
                checkNull(macroProps.symbols, stringSegment);
                return ParseState.STATE_NUMBERING_SYSTEM;
            case 54:
                checkNull(macroProps.scale, stringSegment);
                return ParseState.STATE_SCALE;
            default:
                throw new AssertionError();
        }
    }

    private static ParseState parseOption(ParseState parseState, StringSegment stringSegment, MacroProps macroProps) {
        switch (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[parseState.ordinal()]) {
            case 1:
                BlueprintHelpers.parseIncrementOption(stringSegment, macroProps);
                return ParseState.STATE_PRECISION;
            case 2:
                BlueprintHelpers.parseMeasureUnitOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 3:
                BlueprintHelpers.parseMeasurePerUnitOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 4:
                BlueprintHelpers.parseIdentifierUnitOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 5:
                BlueprintHelpers.parseUnitUsageOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 6:
                BlueprintHelpers.parseCurrencyOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 7:
                BlueprintHelpers.parseIntegerWidthOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 8:
                BlueprintHelpers.parseNumberingSystemOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            case 9:
                BlueprintHelpers.parseScaleOption(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            default:
                if (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[parseState.ordinal()] == 10) {
                    if (BlueprintHelpers.parseExponentWidthOption(stringSegment, macroProps)) {
                        return ParseState.STATE_SCIENTIFIC;
                    }
                    if (BlueprintHelpers.parseExponentSignOption(stringSegment, macroProps)) {
                        return ParseState.STATE_SCIENTIFIC;
                    }
                }
                if (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[parseState.ordinal()] == 11) {
                    if (BlueprintHelpers.parseFracSigOption(stringSegment, macroProps)) {
                        return ParseState.STATE_PRECISION;
                    }
                    parseState = ParseState.STATE_PRECISION;
                }
                if (AnonymousClass2.$SwitchMap$com$ibm$icu$number$NumberSkeletonImpl$ParseState[parseState.ordinal()] == 12 && BlueprintHelpers.parseTrailingZeroOption(stringSegment, macroProps)) {
                    return ParseState.STATE_NULL;
                }
                throw new SkeletonSyntaxException("Invalid option", stringSegment);
        }
    }

    private static void generateSkeleton(MacroProps macroProps, StringBuilder sb) {
        if (macroProps.notation != null && GeneratorHelpers.notation(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.unit != null && GeneratorHelpers.unit(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.usage != null && GeneratorHelpers.usage(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.precision != null && GeneratorHelpers.precision(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.roundingMode != null && GeneratorHelpers.roundingMode(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.grouping != null && GeneratorHelpers.grouping(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.integerWidth != null && GeneratorHelpers.integerWidth(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.symbols != null && GeneratorHelpers.symbols(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.unitWidth != null && GeneratorHelpers.unitWidth(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.sign != null && GeneratorHelpers.sign(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.decimal != null && GeneratorHelpers.decimal(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.scale != null && GeneratorHelpers.scale(macroProps, sb)) {
            sb.append(TokenParser.SP);
        }
        if (macroProps.padder != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom padder");
        }
        if (macroProps.unitDisplayCase != null && !macroProps.unitDisplayCase.isEmpty()) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom unit display case");
        }
        if (macroProps.affixProvider != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom affix provider");
        }
        if (macroProps.rules != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom plural rules");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
    }

    static final class BlueprintHelpers {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        BlueprintHelpers() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean parseExponentWidthOption(StringSegment stringSegment, MacroProps macroProps) {
            if (!NumberSkeletonImpl.isWildcardChar(stringSegment.charAt(0))) {
                return false;
            }
            int i = 1;
            int i2 = 0;
            while (i < stringSegment.length() && stringSegment.charAt(i) == 'e') {
                i2++;
                i++;
            }
            if (i < stringSegment.length()) {
                return false;
            }
            macroProps.notation = ((ScientificNotation) macroProps.notation).withMinExponentDigits(i2);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateExponentWidthOption(int i, StringBuilder sb) {
            sb.append(NumberSkeletonImpl.WILDCARD_CHAR);
            NumberSkeletonImpl.appendMultiple(sb, 101, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean parseExponentSignOption(StringSegment stringSegment, MacroProps macroProps) {
            NumberFormatter.SignDisplay signDisplay;
            CharsTrie charsTrie = new CharsTrie(NumberSkeletonImpl.SERIALIZED_STEM_TRIE, 0);
            BytesTrie.Result next = charsTrie.next(stringSegment, 0, stringSegment.length());
            if ((next != BytesTrie.Result.INTERMEDIATE_VALUE && next != BytesTrie.Result.FINAL_VALUE) || (signDisplay = StemToObject.signDisplay(NumberSkeletonImpl.STEM_ENUM_VALUES[charsTrie.getValue()])) == null) {
                return false;
            }
            macroProps.notation = ((ScientificNotation) macroProps.notation).withExponentSignDisplay(signDisplay);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseCurrencyOption(StringSegment stringSegment, MacroProps macroProps) {
            try {
                macroProps.unit = Currency.getInstance(stringSegment.subSequence(0, stringSegment.length()).toString());
            } catch (IllegalArgumentException e) {
                throw new SkeletonSyntaxException("Invalid currency", stringSegment, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateCurrencyOption(Currency currency, StringBuilder sb) {
            sb.append(currency.getCurrencyCode());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseMeasureUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            int i = 0;
            while (i < stringSegment.length() && stringSegment.charAt(i) != '-') {
                i++;
            }
            if (i == stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid measure unit option", stringSegment);
            }
            String string = stringSegment.subSequence(0, i).toString();
            String string2 = stringSegment.subSequence(i + 1, stringSegment.length()).toString();
            for (MeasureUnit measureUnit : MeasureUnit.getAvailable(string)) {
                if (string2.equals(measureUnit.getSubtype())) {
                    macroProps.unit = measureUnit;
                    return;
                }
            }
            throw new SkeletonSyntaxException("Unknown measure unit", stringSegment);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseMeasurePerUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            MeasureUnit measureUnit = macroProps.unit;
            parseMeasureUnitOption(stringSegment, macroProps);
            macroProps.perUnit = macroProps.unit;
            macroProps.unit = measureUnit;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseIdentifierUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            try {
                macroProps.unit = MeasureUnit.forIdentifier(stringSegment.asString());
            } catch (IllegalArgumentException unused) {
                throw new SkeletonSyntaxException("Invalid unit stem", stringSegment);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseUnitUsageOption(StringSegment stringSegment, MacroProps macroProps) {
            macroProps.usage = stringSegment.asString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseFractionStem(StringSegment stringSegment, MacroProps macroProps) {
            int i;
            int i2 = 0;
            int i3 = 1;
            while (i3 < stringSegment.length() && stringSegment.charAt(i3) == '0') {
                i2++;
                i3++;
            }
            if (i3 >= stringSegment.length()) {
                i = i2;
            } else if (NumberSkeletonImpl.isWildcardChar(stringSegment.charAt(i3))) {
                i3++;
                i = -1;
            } else {
                i = i2;
                while (i3 < stringSegment.length() && stringSegment.charAt(i3) == '#') {
                    i++;
                    i3++;
                }
            }
            if (i3 < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid fraction stem", stringSegment);
            }
            if (i != -1) {
                macroProps.precision = Precision.minMaxFraction(i2, i);
            } else if (i2 == 0) {
                macroProps.precision = Precision.unlimited();
            } else {
                macroProps.precision = Precision.minFraction(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateFractionStem(int i, int i2, StringBuilder sb) {
            if (i == 0 && i2 == 0) {
                sb.append("precision-integer");
                return;
            }
            sb.append('.');
            NumberSkeletonImpl.appendMultiple(sb, 48, i);
            if (i2 != -1) {
                NumberSkeletonImpl.appendMultiple(sb, 35, i2 - i);
            } else {
                sb.append(NumberSkeletonImpl.WILDCARD_CHAR);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseDigitsStem(StringSegment stringSegment, MacroProps macroProps) {
            int i;
            int i2 = 0;
            int i3 = 0;
            while (i2 < stringSegment.length() && stringSegment.charAt(i2) == '@') {
                i3++;
                i2++;
            }
            if (i2 >= stringSegment.length()) {
                i = i3;
            } else if (NumberSkeletonImpl.isWildcardChar(stringSegment.charAt(i2))) {
                i2++;
                i = -1;
            } else {
                i = i3;
                while (i2 < stringSegment.length() && stringSegment.charAt(i2) == '#') {
                    i++;
                    i2++;
                }
            }
            if (i2 < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid significant digits stem", stringSegment);
            }
            if (i == -1) {
                macroProps.precision = Precision.minSignificantDigits(i3);
            } else {
                macroProps.precision = Precision.minMaxSignificantDigits(i3, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateDigitsStem(int i, int i2, StringBuilder sb) {
            NumberSkeletonImpl.appendMultiple(sb, 64, i);
            if (i2 != -1) {
                NumberSkeletonImpl.appendMultiple(sb, 35, i2 - i);
            } else {
                sb.append(NumberSkeletonImpl.WILDCARD_CHAR);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:36:? A[PHI: r0 r4
          PHI (r0v5 int) = (r0v4 int), (r0v9 int) binds: [B:11:0x0023, B:21:0x0047] A[DONT_GENERATE, DONT_INLINE]
          PHI (r4v1 com.ibm.icu.number.NumberFormatter$SignDisplay) = (r4v0 com.ibm.icu.number.NumberFormatter$SignDisplay), (r4v6 com.ibm.icu.number.NumberFormatter$SignDisplay) binds: [B:11:0x0023, B:21:0x0047] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static void parseScientificStem(com.ibm.icu.impl.StringSegment r7, com.ibm.icu.impl.number.MacroProps r8) {
            /*
                int r0 = r7.length()
                r1 = 1
                if (r0 == r1) goto L72
                char r0 = r7.charAt(r1)
                r2 = 69
                r3 = 0
                if (r0 != r2) goto L19
                r0 = 2
                int r2 = r7.length()
                if (r2 == r0) goto L72
                r2 = 1
                goto L1b
            L19:
                r0 = 1
                r2 = 0
            L1b:
                com.ibm.icu.number.NumberFormatter$SignDisplay r4 = com.ibm.icu.number.NumberFormatter.SignDisplay.AUTO
                char r5 = r7.charAt(r0)
                r6 = 43
                if (r5 != r6) goto L49
                int r0 = r0 + 1
                int r4 = r7.length()
                if (r4 == r0) goto L72
                char r4 = r7.charAt(r0)
                r5 = 33
                if (r4 != r5) goto L38
                com.ibm.icu.number.NumberFormatter$SignDisplay r4 = com.ibm.icu.number.NumberFormatter.SignDisplay.ALWAYS
                goto L42
            L38:
                char r4 = r7.charAt(r0)
                r5 = 63
                if (r4 != r5) goto L72
                com.ibm.icu.number.NumberFormatter$SignDisplay r4 = com.ibm.icu.number.NumberFormatter.SignDisplay.EXCEPT_ZERO
            L42:
                int r0 = r0 + r1
                int r1 = r7.length()
                if (r1 == r0) goto L72
            L49:
                int r1 = r7.length()
                if (r0 >= r1) goto L5c
                char r1 = r7.charAt(r0)
                r5 = 48
                if (r1 != r5) goto L72
                int r3 = r3 + 1
                int r0 = r0 + 1
                goto L49
            L5c:
                if (r2 == 0) goto L63
                com.ibm.icu.number.ScientificNotation r7 = com.ibm.icu.number.Notation.engineering()
                goto L67
            L63:
                com.ibm.icu.number.ScientificNotation r7 = com.ibm.icu.number.Notation.scientific()
            L67:
                com.ibm.icu.number.ScientificNotation r7 = r7.withExponentSignDisplay(r4)
                com.ibm.icu.number.ScientificNotation r7 = r7.withMinExponentDigits(r3)
                r8.notation = r7
                return
            L72:
                com.ibm.icu.number.SkeletonSyntaxException r8 = new com.ibm.icu.number.SkeletonSyntaxException
                java.lang.String r0 = "Invalid scientific stem"
                r8.<init>(r0, r7)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.number.NumberSkeletonImpl.BlueprintHelpers.parseScientificStem(com.ibm.icu.impl.StringSegment, com.ibm.icu.impl.number.MacroProps):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseIntegerStem(StringSegment stringSegment, MacroProps macroProps) {
            int i = 1;
            while (true) {
                if (i >= stringSegment.length()) {
                    break;
                }
                if (stringSegment.charAt(i) != '0') {
                    i--;
                    break;
                }
                i++;
            }
            if (i < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid integer stem", stringSegment);
            }
            macroProps.integerWidth = IntegerWidth.zeroFillTo(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0070  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static boolean parseFracSigOption(com.ibm.icu.impl.StringSegment r6, com.ibm.icu.impl.number.MacroProps r7) {
            /*
                r0 = 0
                char r1 = r6.charAt(r0)
                r2 = 64
                if (r1 == r2) goto La
                return r0
            La:
                r1 = 0
            Lb:
                int r3 = r6.length()
                if (r0 >= r3) goto L1c
                char r3 = r6.charAt(r0)
                if (r3 != r2) goto L1c
                int r1 = r1 + 1
                int r0 = r0 + 1
                goto Lb
            L1c:
                int r2 = r6.length()
                r3 = -1
                if (r0 >= r2) goto L45
                char r2 = r6.charAt(r0)
                boolean r2 = com.ibm.icu.number.NumberSkeletonImpl.isWildcardChar(r2)
                if (r2 == 0) goto L31
                int r0 = r0 + 1
                r2 = -1
                goto L46
            L31:
                r2 = r1
            L32:
                int r4 = r6.length()
                if (r0 >= r4) goto L46
                char r4 = r6.charAt(r0)
                r5 = 35
                if (r4 != r5) goto L46
                int r2 = r2 + 1
                int r0 = r0 + 1
                goto L32
            L45:
                r2 = r1
            L46:
                int r4 = r6.length()
                r5 = 1
                if (r0 >= r4) goto L80
                if (r2 == r3) goto L78
                int r3 = r6.codePointAt(r0)
                r4 = 114(0x72, float:1.6E-43)
                if (r3 != r4) goto L5c
                com.ibm.icu.number.NumberFormatter$RoundingPriority r3 = com.ibm.icu.number.NumberFormatter.RoundingPriority.RELAXED
            L59:
                int r0 = r0 + 1
                goto L69
            L5c:
                int r3 = r6.codePointAt(r0)
                r4 = 115(0x73, float:1.61E-43)
                if (r3 != r4) goto L67
                com.ibm.icu.number.NumberFormatter$RoundingPriority r3 = com.ibm.icu.number.NumberFormatter.RoundingPriority.STRICT
                goto L59
            L67:
                com.ibm.icu.number.NumberFormatter$RoundingPriority r3 = com.ibm.icu.number.NumberFormatter.RoundingPriority.RELAXED
            L69:
                int r4 = r6.length()
                if (r0 < r4) goto L70
                goto L8b
            L70:
                com.ibm.icu.number.SkeletonSyntaxException r7 = new com.ibm.icu.number.SkeletonSyntaxException
                java.lang.String r0 = "Invalid digits option for fraction rounder"
                r7.<init>(r0, r6)
                throw r7
            L78:
                com.ibm.icu.number.SkeletonSyntaxException r7 = new com.ibm.icu.number.SkeletonSyntaxException
                java.lang.String r0 = "Invalid digits option: Wildcard character not allowed with the priority annotation"
                r7.<init>(r0, r6)
                throw r7
            L80:
                if (r2 != r3) goto L87
                com.ibm.icu.number.NumberFormatter$RoundingPriority r3 = com.ibm.icu.number.NumberFormatter.RoundingPriority.RELAXED
                r2 = r1
                r1 = 1
                goto L8b
            L87:
                if (r1 != r5) goto L96
                com.ibm.icu.number.NumberFormatter$RoundingPriority r3 = com.ibm.icu.number.NumberFormatter.RoundingPriority.STRICT
            L8b:
                com.ibm.icu.number.Precision r6 = r7.precision
                com.ibm.icu.number.FractionPrecision r6 = (com.ibm.icu.number.FractionPrecision) r6
                com.ibm.icu.number.Precision r6 = r6.withSignificantDigits(r1, r2, r3)
                r7.precision = r6
                return r5
            L96:
                com.ibm.icu.number.SkeletonSyntaxException r7 = new com.ibm.icu.number.SkeletonSyntaxException
                java.lang.String r0 = "Invalid digits option: Priority annotation required"
                r7.<init>(r0, r6)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.number.NumberSkeletonImpl.BlueprintHelpers.parseFracSigOption(com.ibm.icu.impl.StringSegment, com.ibm.icu.impl.number.MacroProps):boolean");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean parseTrailingZeroOption(StringSegment stringSegment, MacroProps macroProps) {
            if (!stringSegment.contentEquals("w")) {
                return false;
            }
            macroProps.precision = macroProps.precision.trailingZeroDisplay(NumberFormatter.TrailingZeroDisplay.HIDE_IF_WHOLE);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseIncrementOption(StringSegment stringSegment, MacroProps macroProps) {
            try {
                macroProps.precision = Precision.increment(new BigDecimal(stringSegment.subSequence(0, stringSegment.length()).toString()));
            } catch (NumberFormatException e) {
                throw new SkeletonSyntaxException("Invalid rounding increment", stringSegment, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateIncrementOption(BigDecimal bigDecimal, StringBuilder sb) {
            sb.append(bigDecimal.toPlainString());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseIntegerWidthOption(StringSegment stringSegment, MacroProps macroProps) {
            int i;
            int i2;
            int i3 = 0;
            if (NumberSkeletonImpl.isWildcardChar(stringSegment.charAt(0))) {
                i = 1;
                i2 = -1;
            } else {
                i = 0;
                i2 = 0;
            }
            while (i < stringSegment.length() && i2 != -1 && stringSegment.charAt(i) == '#') {
                i2++;
                i++;
            }
            if (i < stringSegment.length()) {
                while (i < stringSegment.length() && stringSegment.charAt(i) == '0') {
                    i3++;
                    i++;
                }
            }
            if (i2 != -1) {
                i2 += i3;
            }
            if (i < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid integer width stem", stringSegment);
            }
            if (i2 == -1) {
                macroProps.integerWidth = IntegerWidth.zeroFillTo(i3);
            } else {
                macroProps.integerWidth = IntegerWidth.zeroFillTo(i3).truncateAt(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateIntegerWidthOption(int i, int i2, StringBuilder sb) {
            if (i2 != -1) {
                NumberSkeletonImpl.appendMultiple(sb, 35, i2 - i);
            } else {
                sb.append(NumberSkeletonImpl.WILDCARD_CHAR);
            }
            NumberSkeletonImpl.appendMultiple(sb, 48, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseNumberingSystemOption(StringSegment stringSegment, MacroProps macroProps) {
            NumberingSystem instanceByName = NumberingSystem.getInstanceByName(stringSegment.subSequence(0, stringSegment.length()).toString());
            if (instanceByName == null) {
                throw new SkeletonSyntaxException("Unknown numbering system", stringSegment);
            }
            macroProps.symbols = instanceByName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateNumberingSystemOption(NumberingSystem numberingSystem, StringBuilder sb) {
            sb.append(numberingSystem.getName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void parseScaleOption(StringSegment stringSegment, MacroProps macroProps) {
            try {
                macroProps.scale = Scale.byBigDecimal(new BigDecimal(stringSegment.subSequence(0, stringSegment.length()).toString()));
            } catch (NumberFormatException e) {
                throw new SkeletonSyntaxException("Invalid scale", stringSegment, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void generateScaleOption(Scale scale, StringBuilder sb) {
            BigDecimal bigDecimal = scale.arbitrary;
            if (bigDecimal == null) {
                bigDecimal = BigDecimal.ONE;
            }
            sb.append(bigDecimal.scaleByPowerOfTen(scale.magnitude).toPlainString());
        }
    }

    static final class GeneratorHelpers {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        GeneratorHelpers() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean notation(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.notation instanceof CompactNotation) {
                if (macroProps.notation == Notation.compactLong()) {
                    sb.append("compact-long");
                    return true;
                }
                if (macroProps.notation == Notation.compactShort()) {
                    sb.append("compact-short");
                    return true;
                }
                throw new UnsupportedOperationException("Cannot generate number skeleton with custom compact data");
            }
            if (!(macroProps.notation instanceof ScientificNotation)) {
                return false;
            }
            ScientificNotation scientificNotation = (ScientificNotation) macroProps.notation;
            if (scientificNotation.engineeringInterval == 3) {
                sb.append("engineering");
            } else {
                sb.append("scientific");
            }
            if (scientificNotation.minExponentDigits > 1) {
                sb.append(IOUtils.DIR_SEPARATOR_UNIX);
                BlueprintHelpers.generateExponentWidthOption(scientificNotation.minExponentDigits, sb);
            }
            if (scientificNotation.exponentSignDisplay != NumberFormatter.SignDisplay.AUTO) {
                sb.append(IOUtils.DIR_SEPARATOR_UNIX);
                EnumToStemString.signDisplay(scientificNotation.exponentSignDisplay, sb);
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean unit(MacroProps macroProps, StringBuilder sb) {
            MeasureUnit measureUnitProduct = macroProps.unit;
            if (macroProps.perUnit != null) {
                if ((macroProps.unit instanceof Currency) || (macroProps.perUnit instanceof Currency)) {
                    throw new UnsupportedOperationException("Cannot generate number skeleton with currency unit and per-unit");
                }
                measureUnitProduct = measureUnitProduct.product(macroProps.perUnit.reciprocal());
            }
            if (measureUnitProduct instanceof Currency) {
                sb.append("currency/");
                BlueprintHelpers.generateCurrencyOption((Currency) measureUnitProduct, sb);
                return true;
            }
            if (measureUnitProduct.equals(MeasureUnit.PERCENT)) {
                sb.append("percent");
                return true;
            }
            if (measureUnitProduct.equals(MeasureUnit.PERMILLE)) {
                sb.append("permille");
                return true;
            }
            sb.append("unit/");
            sb.append(measureUnitProduct.getIdentifier());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean usage(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.usage == null || macroProps.usage.length() <= 0) {
                return false;
            }
            sb.append("usage/");
            sb.append(macroProps.usage);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean precision(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.precision instanceof Precision.InfiniteRounderImpl) {
                sb.append("precision-unlimited");
            } else if (macroProps.precision instanceof Precision.FractionRounderImpl) {
                Precision.FractionRounderImpl fractionRounderImpl = (Precision.FractionRounderImpl) macroProps.precision;
                BlueprintHelpers.generateFractionStem(fractionRounderImpl.minFrac, fractionRounderImpl.maxFrac, sb);
            } else if (macroProps.precision instanceof Precision.SignificantRounderImpl) {
                Precision.SignificantRounderImpl significantRounderImpl = (Precision.SignificantRounderImpl) macroProps.precision;
                BlueprintHelpers.generateDigitsStem(significantRounderImpl.minSig, significantRounderImpl.maxSig, sb);
            } else if (macroProps.precision instanceof Precision.FracSigRounderImpl) {
                Precision.FracSigRounderImpl fracSigRounderImpl = (Precision.FracSigRounderImpl) macroProps.precision;
                BlueprintHelpers.generateFractionStem(fracSigRounderImpl.minFrac, fracSigRounderImpl.maxFrac, sb);
                sb.append(IOUtils.DIR_SEPARATOR_UNIX);
                BlueprintHelpers.generateDigitsStem(fracSigRounderImpl.minSig, fracSigRounderImpl.maxSig, sb);
                if (fracSigRounderImpl.priority == NumberFormatter.RoundingPriority.RELAXED) {
                    sb.append('r');
                } else {
                    sb.append('s');
                }
            } else if (macroProps.precision instanceof Precision.IncrementRounderImpl) {
                Precision.IncrementRounderImpl incrementRounderImpl = (Precision.IncrementRounderImpl) macroProps.precision;
                sb.append("precision-increment/");
                BlueprintHelpers.generateIncrementOption(incrementRounderImpl.increment, sb);
            } else if (((Precision.CurrencyRounderImpl) macroProps.precision).usage == Currency.CurrencyUsage.STANDARD) {
                sb.append("precision-currency-standard");
            } else {
                sb.append("precision-currency-cash");
            }
            if (macroProps.precision.trailingZeroDisplay != NumberFormatter.TrailingZeroDisplay.HIDE_IF_WHOLE) {
                return true;
            }
            sb.append("/w");
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean roundingMode(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.roundingMode == RoundingUtils.DEFAULT_ROUNDING_MODE) {
                return false;
            }
            EnumToStemString.roundingMode(macroProps.roundingMode, sb);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean grouping(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.grouping instanceof NumberFormatter.GroupingStrategy) {
                if (macroProps.grouping == NumberFormatter.GroupingStrategy.AUTO) {
                    return false;
                }
                EnumToStemString.groupingStrategy((NumberFormatter.GroupingStrategy) macroProps.grouping, sb);
                return true;
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom Grouper");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean integerWidth(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.integerWidth.equals(IntegerWidth.DEFAULT)) {
                return false;
            }
            sb.append("integer-width/");
            BlueprintHelpers.generateIntegerWidthOption(macroProps.integerWidth.minInt, macroProps.integerWidth.maxInt, sb);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean symbols(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.symbols instanceof NumberingSystem) {
                NumberingSystem numberingSystem = (NumberingSystem) macroProps.symbols;
                if (numberingSystem.getName().equals("latn")) {
                    sb.append("latin");
                    return true;
                }
                sb.append("numbering-system/");
                BlueprintHelpers.generateNumberingSystemOption(numberingSystem, sb);
                return true;
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom DecimalFormatSymbols");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean unitWidth(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.unitWidth == NumberFormatter.UnitWidth.SHORT) {
                return false;
            }
            EnumToStemString.unitWidth(macroProps.unitWidth, sb);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean sign(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.sign == NumberFormatter.SignDisplay.AUTO) {
                return false;
            }
            EnumToStemString.signDisplay(macroProps.sign, sb);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean decimal(MacroProps macroProps, StringBuilder sb) {
            if (macroProps.decimal == NumberFormatter.DecimalSeparatorDisplay.AUTO) {
                return false;
            }
            EnumToStemString.decimalSeparatorDisplay(macroProps.decimal, sb);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean scale(MacroProps macroProps, StringBuilder sb) {
            if (!macroProps.scale.isValid()) {
                return false;
            }
            sb.append("scale/");
            BlueprintHelpers.generateScaleOption(macroProps.scale, sb);
            return true;
        }
    }

    private static void checkNull(Object obj, CharSequence charSequence) {
        if (obj != null) {
            throw new SkeletonSyntaxException("Duplicated setting", charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendMultiple(StringBuilder sb, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            sb.appendCodePoint(i);
        }
    }
}
