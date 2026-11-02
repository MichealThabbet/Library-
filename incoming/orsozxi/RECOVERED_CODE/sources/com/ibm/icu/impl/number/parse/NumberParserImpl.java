package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class NumberParserImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int parseFlags;
    private final List<NumberParseMatcher> matchers = new ArrayList();
    private boolean frozen = false;

    public static NumberParserImpl createSimpleParser(ULocale uLocale, String str, int i) {
        NumberParserImpl numberParserImpl = new NumberParserImpl(i);
        Currency currency = Currency.getInstance("USD");
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(uLocale);
        IgnorablesMatcher ignorablesMatcher = IgnorablesMatcher.getInstance(i);
        AffixTokenMatcherFactory affixTokenMatcherFactory = new AffixTokenMatcherFactory();
        affixTokenMatcherFactory.currency = currency;
        affixTokenMatcherFactory.symbols = decimalFormatSymbols;
        affixTokenMatcherFactory.ignorables = ignorablesMatcher;
        affixTokenMatcherFactory.locale = uLocale;
        affixTokenMatcherFactory.parseFlags = i;
        PatternStringParser.ParsedPatternInfo toPatternInfo = PatternStringParser.parseToPatternInfo(str);
        AffixMatcher.createMatchers(toPatternInfo, numberParserImpl, affixTokenMatcherFactory, ignorablesMatcher, i);
        Grouper grouperWithLocaleData = Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO).withLocaleData(uLocale, toPatternInfo);
        numberParserImpl.addMatcher(ignorablesMatcher);
        numberParserImpl.addMatcher(DecimalMatcher.getInstance(decimalFormatSymbols, grouperWithLocaleData, i));
        numberParserImpl.addMatcher(MinusSignMatcher.getInstance(decimalFormatSymbols, false));
        numberParserImpl.addMatcher(PlusSignMatcher.getInstance(decimalFormatSymbols, false));
        numberParserImpl.addMatcher(PercentMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(PermilleMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(NanMatcher.getInstance(decimalFormatSymbols, i));
        numberParserImpl.addMatcher(InfinityMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(PaddingMatcher.getInstance("@"));
        numberParserImpl.addMatcher(ScientificMatcher.getInstance(decimalFormatSymbols, grouperWithLocaleData));
        numberParserImpl.addMatcher(CombinedCurrencyMatcher.getInstance(currency, decimalFormatSymbols, i));
        numberParserImpl.addMatcher(new RequireNumberValidator());
        numberParserImpl.freeze();
        return numberParserImpl;
    }

    public static Number parseStatic(String str, ParsePosition parsePosition, DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols) {
        NumberParserImpl numberParserImplCreateParserFromProperties = createParserFromProperties(decimalFormatProperties, decimalFormatSymbols, false);
        ParsedNumber parsedNumber = new ParsedNumber();
        numberParserImplCreateParserFromProperties.parse(str, true, parsedNumber);
        if (parsedNumber.success()) {
            parsePosition.setIndex(parsedNumber.charEnd);
            return parsedNumber.getNumber();
        }
        parsePosition.setErrorIndex(parsedNumber.charEnd);
        return null;
    }

    public static CurrencyAmount parseStaticCurrency(String str, ParsePosition parsePosition, DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols) {
        NumberParserImpl numberParserImplCreateParserFromProperties = createParserFromProperties(decimalFormatProperties, decimalFormatSymbols, true);
        ParsedNumber parsedNumber = new ParsedNumber();
        numberParserImplCreateParserFromProperties.parse(str, true, parsedNumber);
        if (parsedNumber.success()) {
            parsePosition.setIndex(parsedNumber.charEnd);
            return new CurrencyAmount(parsedNumber.getNumber(), Currency.getInstance(parsedNumber.currencyCode));
        }
        parsePosition.setErrorIndex(parsedNumber.charEnd);
        return null;
    }

    public static NumberParserImpl createDefaultParserForLocale(ULocale uLocale) {
        return createParserFromProperties(PatternStringParser.parseToProperties("0"), DecimalFormatSymbols.getInstance(uLocale), false);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0156  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.ibm.icu.impl.number.parse.NumberParserImpl createParserFromProperties(com.ibm.icu.impl.number.DecimalFormatProperties r11, com.ibm.icu.text.DecimalFormatSymbols r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.parse.NumberParserImpl.createParserFromProperties(com.ibm.icu.impl.number.DecimalFormatProperties, com.ibm.icu.text.DecimalFormatSymbols, boolean):com.ibm.icu.impl.number.parse.NumberParserImpl");
    }

    public NumberParserImpl(int i) {
        this.parseFlags = i;
    }

    public void addMatcher(NumberParseMatcher numberParseMatcher) {
        this.matchers.add(numberParseMatcher);
    }

    public void addMatchers(Collection<? extends NumberParseMatcher> collection) {
        this.matchers.addAll(collection);
    }

    public void freeze() {
        this.frozen = true;
    }

    public int getParseFlags() {
        return this.parseFlags;
    }

    public void parse(String str, boolean z, ParsedNumber parsedNumber) {
        parse(str, 0, z, parsedNumber);
    }

    public void parse(String str, int i, boolean z, ParsedNumber parsedNumber) {
        StringSegment stringSegment = new StringSegment(str, (this.parseFlags & 1) != 0);
        stringSegment.adjustOffset(i);
        if (z) {
            parseGreedy(stringSegment, parsedNumber);
        } else if ((this.parseFlags & 16384) != 0) {
            parseLongestRecursive(stringSegment, parsedNumber, 1);
        } else {
            parseLongestRecursive(stringSegment, parsedNumber, -100);
        }
        Iterator<NumberParseMatcher> it2 = this.matchers.iterator();
        while (it2.hasNext()) {
            it2.next().postProcess(parsedNumber);
        }
        parsedNumber.postProcess();
    }

    private void parseGreedy(StringSegment stringSegment, ParsedNumber parsedNumber) {
        while (true) {
            for (int i = 0; i < this.matchers.size() && stringSegment.length() != 0; i++) {
                NumberParseMatcher numberParseMatcher = this.matchers.get(i);
                if (numberParseMatcher.smokeTest(stringSegment)) {
                    int offset = stringSegment.getOffset();
                    numberParseMatcher.match(stringSegment, parsedNumber);
                    if (stringSegment.getOffset() != offset) {
                        break;
                    }
                }
            }
            return;
        }
    }

    private void parseLongestRecursive(StringSegment stringSegment, ParsedNumber parsedNumber, int i) {
        if (stringSegment.length() == 0 || i == 0) {
            return;
        }
        ParsedNumber parsedNumber2 = new ParsedNumber();
        parsedNumber2.copyFrom(parsedNumber);
        ParsedNumber parsedNumber3 = new ParsedNumber();
        int offset = stringSegment.getOffset();
        for (int i2 = 0; i2 < this.matchers.size(); i2++) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(i2);
            if (numberParseMatcher.smokeTest(stringSegment)) {
                int iCharCount = 0;
                while (iCharCount < stringSegment.length()) {
                    iCharCount += Character.charCount(stringSegment.codePointAt(iCharCount));
                    parsedNumber3.copyFrom(parsedNumber2);
                    stringSegment.setLength(iCharCount);
                    boolean zMatch = numberParseMatcher.match(stringSegment, parsedNumber3);
                    stringSegment.resetLength();
                    if (stringSegment.getOffset() - offset == iCharCount) {
                        parseLongestRecursive(stringSegment, parsedNumber3, i + 1);
                        if (parsedNumber3.isBetterThan(parsedNumber)) {
                            parsedNumber.copyFrom(parsedNumber3);
                        }
                    }
                    stringSegment.setOffset(offset);
                    if (!zMatch) {
                        break;
                    }
                }
            }
        }
    }

    public String toString() {
        return "<NumberParserImpl matchers=" + this.matchers.toString() + ">";
    }
}
