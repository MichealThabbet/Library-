package com.ibm.icu.text;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.text.MessagePattern;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public class PluralFormat extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 1;
    private transient MessagePattern msgPattern;
    private NumberFormat numberFormat;
    private transient double offset;
    private Map<String, String> parsedValues;
    private String pattern;
    private PluralRules pluralRules;
    private transient PluralSelectorAdapter pluralRulesWrapper;
    private ULocale ulocale;

    interface PluralSelector {
        String select(Object obj, double d);
    }

    public PluralFormat() {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(ULocale uLocale) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public PluralFormat(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public PluralFormat(PluralRules pluralRules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(ULocale uLocale, PluralRules pluralRules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public PluralFormat(Locale locale, PluralRules pluralRules) {
        this(ULocale.forLocale(locale), pluralRules);
    }

    public PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, pluralType, uLocale, null);
    }

    public PluralFormat(Locale locale, PluralRules.PluralType pluralType) {
        this(ULocale.forLocale(locale), pluralType);
    }

    public PluralFormat(String str) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        applyPattern(str);
    }

    public PluralFormat(ULocale uLocale, String str) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
        applyPattern(str);
    }

    public PluralFormat(PluralRules pluralRules, String str) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        applyPattern(str);
    }

    public PluralFormat(ULocale uLocale, PluralRules pluralRules, String str) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale, null);
        applyPattern(str);
    }

    public PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType, String str) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, pluralType, uLocale, null);
        applyPattern(str);
    }

    PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType, String str, NumberFormat numberFormat) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, pluralType, uLocale, numberFormat);
        applyPattern(str);
    }

    private void init(PluralRules pluralRules, PluralRules.PluralType pluralType, ULocale uLocale, NumberFormat numberFormat) {
        this.ulocale = uLocale;
        if (pluralRules == null) {
            pluralRules = PluralRules.forLocale(uLocale, pluralType);
        }
        this.pluralRules = pluralRules;
        resetPattern();
        if (numberFormat == null) {
            numberFormat = NumberFormat.getInstance(this.ulocale);
        }
        this.numberFormat = numberFormat;
    }

    private void resetPattern() {
        this.pattern = null;
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern != null) {
            messagePattern.clear();
        }
        this.offset = 0.0d;
    }

    public void applyPattern(String str) {
        this.pattern = str;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parsePluralStyle(str);
            this.offset = this.msgPattern.getPluralOffset(0);
        } catch (RuntimeException e) {
            resetPattern();
            throw e;
        }
    }

    public String toPattern() {
        return this.pattern;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static int findSubMessage(com.ibm.icu.text.MessagePattern r15, int r16, com.ibm.icu.text.PluralFormat.PluralSelector r17, java.lang.Object r18, double r19) {
        /*
            r0 = r15
            int r1 = r15.countParts()
            com.ibm.icu.text.MessagePattern$Part r2 = r15.getPart(r16)
            com.ibm.icu.text.MessagePattern$Part$Type r3 = r2.getType()
            boolean r3 = r3.hasNumericValue()
            if (r3 == 0) goto L1a
            double r2 = r15.getNumericValue(r2)
            int r4 = r16 + 1
            goto L1e
        L1a:
            r2 = 0
            r4 = r16
        L1e:
            r5 = 0
            r6 = 0
            r7 = 0
        L21:
            int r8 = r4 + 1
            com.ibm.icu.text.MessagePattern$Part r4 = r15.getPart(r4)
            com.ibm.icu.text.MessagePattern$Part$Type r9 = r4.getType()
            com.ibm.icu.text.MessagePattern$Part$Type r10 = com.ibm.icu.text.MessagePattern.Part.Type.ARG_LIMIT
            if (r9 != r10) goto L31
            goto La0
        L31:
            com.ibm.icu.text.MessagePattern$Part$Type r9 = r15.getPartType(r8)
            boolean r9 = r9.hasNumericValue()
            r10 = 1
            if (r9 == 0) goto L51
            int r4 = r8 + 1
            com.ibm.icu.text.MessagePattern$Part r8 = r15.getPart(r8)
            double r8 = r15.getNumericValue(r8)
            int r11 = (r19 > r8 ? 1 : (r19 == r8 ? 0 : -1))
            if (r11 != 0) goto L4b
            return r4
        L4b:
            r13 = r17
            r14 = r18
            r8 = r4
            goto L99
        L51:
            if (r6 != 0) goto L95
            java.lang.String r9 = "other"
            boolean r11 = r15.partSubstringMatches(r4, r9)
            if (r11 == 0) goto L70
            if (r7 != 0) goto L95
            if (r5 == 0) goto L6a
            boolean r4 = r5.equals(r9)
            if (r4 == 0) goto L6a
            r13 = r17
            r14 = r18
            goto L92
        L6a:
            r13 = r17
            r14 = r18
            r7 = r8
            goto L99
        L70:
            if (r5 != 0) goto L86
            double r11 = r19 - r2
            r13 = r17
            r14 = r18
            java.lang.String r5 = r13.select(r14, r11)
            if (r7 == 0) goto L8a
            boolean r9 = r5.equals(r9)
            if (r9 == 0) goto L8a
            r6 = 1
            goto L8a
        L86:
            r13 = r17
            r14 = r18
        L8a:
            if (r6 != 0) goto L99
            boolean r4 = r15.partSubstringMatches(r4, r5)
            if (r4 == 0) goto L99
        L92:
            r7 = r8
            r6 = 1
            goto L99
        L95:
            r13 = r17
            r14 = r18
        L99:
            int r4 = r15.getLimitPartIndex(r8)
            int r4 = r4 + r10
            if (r4 < r1) goto L21
        La0:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.PluralFormat.findSubMessage(com.ibm.icu.text.MessagePattern, int, com.ibm.icu.text.PluralFormat$PluralSelector, java.lang.Object, double):int");
    }

    private final class PluralSelectorAdapter implements PluralSelector {
        private PluralSelectorAdapter() {
        }

        @Override // com.ibm.icu.text.PluralFormat.PluralSelector
        public String select(Object obj, double d) {
            return PluralFormat.this.pluralRules.select((PluralRules.IFixedDecimal) obj);
        }
    }

    public final String format(double d) {
        return format(Double.valueOf(d), d);
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(obj instanceof Number)) {
            throw new IllegalArgumentException("'" + obj + "' is not a Number");
        }
        Number number = (Number) obj;
        stringBuffer.append(format(number, number.doubleValue()));
        return stringBuffer;
    }

    private String format(Number number, double d) {
        String str;
        String string;
        PluralRules.IFixedDecimal fixedDecimal;
        int index;
        FormattedNumber formattedNumber;
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern == null || messagePattern.countParts() == 0) {
            return this.numberFormat.format(number);
        }
        double d2 = this.offset;
        double d3 = d - d2;
        NumberFormat numberFormat = this.numberFormat;
        if (numberFormat instanceof DecimalFormat) {
            LocalizedNumberFormatter numberFormatter = ((DecimalFormat) numberFormat).toNumberFormatter();
            if (this.offset == 0.0d) {
                formattedNumber = numberFormatter.format(number);
            } else {
                formattedNumber = numberFormatter.format(d3);
            }
            string = formattedNumber.toString();
            fixedDecimal = formattedNumber.getFixedDecimal();
        } else {
            if (d2 == 0.0d) {
                str = numberFormat.format(number);
            } else {
                str = numberFormat.format(d3);
            }
            string = str;
            fixedDecimal = new PluralRules.FixedDecimal(d3);
        }
        int iFindSubMessage = findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, fixedDecimal, d);
        StringBuilder sb = null;
        int limit = this.msgPattern.getPart(iFindSubMessage).getLimit();
        while (true) {
            iFindSubMessage++;
            MessagePattern.Part part = this.msgPattern.getPart(iFindSubMessage);
            MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || (type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode())) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) this.pattern, limit, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    sb.append(string);
                }
                limit = part.getLimit();
            } else if (type == MessagePattern.Part.Type.ARG_START) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) this.pattern, limit, index);
                iFindSubMessage = this.msgPattern.getLimitPartIndex(iFindSubMessage);
                limit = this.msgPattern.getPart(iFindSubMessage).getLimit();
                MessagePattern.appendReducedApostrophes(this.pattern, index, limit, sb);
            }
        }
        if (sb == null) {
            return this.pattern.substring(limit, index);
        }
        sb.append((CharSequence) this.pattern, limit, index);
        return sb.toString();
    }

    public Number parse(String str, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    String parseType(String str, RbnfLenientScanner rbnfLenientScanner, FieldPosition fieldPosition) {
        int iIndexOf;
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern == null || messagePattern.countParts() == 0) {
            fieldPosition.setBeginIndex(-1);
            fieldPosition.setEndIndex(-1);
            return null;
        }
        int iCountParts = this.msgPattern.countParts();
        int beginIndex = fieldPosition.getBeginIndex();
        char c = 0;
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        int i = 0;
        String strSubstring = null;
        int i2 = -1;
        String str2 = null;
        while (i < iCountParts) {
            int i3 = i + 1;
            if (this.msgPattern.getPart(i).getType() != MessagePattern.Part.Type.ARG_SELECTOR) {
                i = i3;
            } else {
                int i4 = i3 + 1;
                MessagePattern.Part part = this.msgPattern.getPart(i3);
                if (part.getType() != MessagePattern.Part.Type.MSG_START) {
                    i = i4;
                } else {
                    int i5 = i4 + 1;
                    MessagePattern.Part part2 = this.msgPattern.getPart(i4);
                    if (part2.getType() != MessagePattern.Part.Type.MSG_LIMIT) {
                        i = i5;
                    } else {
                        String strSubstring2 = this.pattern.substring(part.getLimit(), part2.getIndex());
                        if (rbnfLenientScanner != null) {
                            iIndexOf = str.indexOf(strSubstring2, beginIndex);
                            if (iIndexOf < 0) {
                                iIndexOf = rbnfLenientScanner.findText(str, strSubstring2, beginIndex)[c];
                            }
                        } else {
                            iIndexOf = str.indexOf(strSubstring2, beginIndex);
                        }
                        if (iIndexOf >= 0 && iIndexOf >= i2 && (str2 == null || strSubstring2.length() > str2.length())) {
                            str2 = strSubstring2;
                            i2 = iIndexOf;
                            strSubstring = this.pattern.substring(part.getLimit(), part2.getIndex());
                        }
                        i = i5;
                        c = 0;
                    }
                }
            }
        }
        if (strSubstring != null) {
            fieldPosition.setBeginIndex(i2);
            fieldPosition.setEndIndex(i2 + str2.length());
            return strSubstring;
        }
        fieldPosition.setBeginIndex(-1);
        fieldPosition.setEndIndex(-1);
        return null;
    }

    @Deprecated
    public void setLocale(ULocale uLocale) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PluralFormat pluralFormat = (PluralFormat) obj;
        return Objects.equals(this.ulocale, pluralFormat.ulocale) && Objects.equals(this.pluralRules, pluralFormat.pluralRules) && Objects.equals(this.msgPattern, pluralFormat.msgPattern) && Objects.equals(this.numberFormat, pluralFormat.numberFormat);
    }

    public boolean equals(PluralFormat pluralFormat) {
        return equals((Object) pluralFormat);
    }

    public int hashCode() {
        return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("locale=" + this.ulocale);
        sb.append(", rules='" + this.pluralRules + "'");
        sb.append(", pattern='" + this.pattern + "'");
        sb.append(", format='" + this.numberFormat + "'");
        return sb.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.parsedValues = null;
        String str = this.pattern;
        if (str != null) {
            applyPattern(str);
        }
    }
}
