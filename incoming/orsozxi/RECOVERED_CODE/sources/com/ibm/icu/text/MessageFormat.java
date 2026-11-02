package com.ibm.icu.text;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.MessagePattern;
import com.ibm.icu.text.PluralFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.ChoiceFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes2.dex */
public class MessageFormat extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char CURLY_BRACE_LEFT = '{';
    private static final char CURLY_BRACE_RIGHT = '}';
    private static final int DATE_MODIFIER_EMPTY = 0;
    private static final int DATE_MODIFIER_FULL = 4;
    private static final int DATE_MODIFIER_LONG = 3;
    private static final int DATE_MODIFIER_MEDIUM = 2;
    private static final int DATE_MODIFIER_SHORT = 1;
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_EMPTY = 0;
    private static final int MODIFIER_INTEGER = 3;
    private static final int MODIFIER_PERCENT = 2;
    private static final char SINGLE_QUOTE = '\'';
    private static final int STATE_INITIAL = 0;
    private static final int STATE_IN_QUOTE = 2;
    private static final int STATE_MSG_ELEMENT = 3;
    private static final int STATE_SINGLE_QUOTE = 1;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_DURATION = 5;
    private static final int TYPE_NUMBER = 0;
    private static final int TYPE_ORDINAL = 4;
    private static final int TYPE_SPELLOUT = 3;
    private static final int TYPE_TIME = 2;
    static final long serialVersionUID = 7136212545847378652L;
    private transient Map<Integer, Format> cachedFormatters;
    private transient Set<Integer> customFormatArgStarts;
    private transient MessagePattern msgPattern;
    private transient PluralSelectorProvider ordinalProvider;
    private transient PluralSelectorProvider pluralProvider;
    private transient DateFormat stockDateFormatter;
    private transient NumberFormat stockNumberFormatter;
    private transient ULocale ulocale;
    private static final String[] typeList = {"number", "date", CrashHianalyticsData.TIME, "spellout", "ordinal", FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION};
    private static final String[] modifierList = {"", FirebaseAnalytics.Param.CURRENCY, "percent", "integer"};
    private static final String[] dateModifierList = {"", "short", "medium", "long", "full"};
    private static final Locale rootLocale = new Locale("");

    public MessageFormat(String str) {
        this.ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
        applyPattern(str);
    }

    public MessageFormat(String str, Locale locale) {
        this(str, ULocale.forLocale(locale));
    }

    public MessageFormat(String str, ULocale uLocale) {
        this.ulocale = uLocale;
        applyPattern(str);
    }

    public void setLocale(Locale locale) {
        setLocale(ULocale.forLocale(locale));
    }

    public void setLocale(ULocale uLocale) {
        String pattern = toPattern();
        this.ulocale = uLocale;
        this.stockDateFormatter = null;
        this.stockNumberFormatter = null;
        this.pluralProvider = null;
        this.ordinalProvider = null;
        applyPattern(pattern);
    }

    public Locale getLocale() {
        return this.ulocale.toLocale();
    }

    public ULocale getULocale() {
        return this.ulocale;
    }

    public void applyPattern(String str) {
        try {
            if (this.msgPattern == null) {
                this.msgPattern = new MessagePattern(str);
            } else {
                this.msgPattern.parse(str);
            }
            cacheExplicitFormats();
        } catch (RuntimeException e) {
            resetPattern();
            throw e;
        }
    }

    public void applyPattern(String str, MessagePattern.ApostropheMode apostropheMode) {
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern == null) {
            this.msgPattern = new MessagePattern(apostropheMode);
        } else if (apostropheMode != messagePattern.getApostropheMode()) {
            this.msgPattern.clearPatternAndSetApostropheMode(apostropheMode);
        }
        applyPattern(str);
    }

    public MessagePattern.ApostropheMode getApostropheMode() {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        return this.msgPattern.getApostropheMode();
    }

    public String toPattern() {
        String patternString;
        if (this.customFormatArgStarts != null) {
            throw new IllegalStateException("toPattern() is not supported after custom Format objects have been set via setFormat() or similar APIs");
        }
        MessagePattern messagePattern = this.msgPattern;
        return (messagePattern == null || (patternString = messagePattern.getPatternString()) == null) ? "" : patternString;
    }

    private int nextTopLevelArgStart(int i) {
        MessagePattern.Part.Type partType;
        if (i != 0) {
            i = this.msgPattern.getLimitPartIndex(i);
        }
        do {
            i++;
            partType = this.msgPattern.getPartType(i);
            if (partType == MessagePattern.Part.Type.ARG_START) {
                return i;
            }
        } while (partType != MessagePattern.Part.Type.MSG_LIMIT);
        return -1;
    }

    private boolean argNameMatches(int i, String str, int i2) {
        MessagePattern.Part part = this.msgPattern.getPart(i);
        if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
            return this.msgPattern.partSubstringMatches(part, str);
        }
        return part.getValue() == i2;
    }

    private String getArgName(int i) {
        MessagePattern.Part part = this.msgPattern.getPart(i);
        if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
            return this.msgPattern.getSubstring(part);
        }
        return Integer.toString(part.getValue());
    }

    public void setFormatsByArgumentIndex(Format[] formatArr) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                return;
            }
            int value = this.msgPattern.getPart(iNextTopLevelArgStart + 1).getValue();
            if (value < formatArr.length) {
                setCustomArgStartFormat(iNextTopLevelArgStart, formatArr[value]);
            }
        }
    }

    public void setFormatsByArgumentName(Map<String, Format> map) {
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                return;
            }
            String argName = getArgName(iNextTopLevelArgStart + 1);
            if (map.containsKey(argName)) {
                setCustomArgStartFormat(iNextTopLevelArgStart, map.get(argName));
            }
        }
    }

    public void setFormats(Format[] formatArr) {
        int iNextTopLevelArgStart = 0;
        for (int i = 0; i < formatArr.length && (iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart)) >= 0; i++) {
            setCustomArgStartFormat(iNextTopLevelArgStart, formatArr[i]);
        }
    }

    public void setFormatByArgumentIndex(int i, Format format) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                return;
            }
            if (this.msgPattern.getPart(iNextTopLevelArgStart + 1).getValue() == i) {
                setCustomArgStartFormat(iNextTopLevelArgStart, format);
            }
        }
    }

    public void setFormatByArgumentName(String str, Format format) {
        int iValidateArgumentName = MessagePattern.validateArgumentName(str);
        if (iValidateArgumentName < -1) {
            return;
        }
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                return;
            }
            if (argNameMatches(iNextTopLevelArgStart + 1, str, iValidateArgumentName)) {
                setCustomArgStartFormat(iNextTopLevelArgStart, format);
            }
        }
    }

    public void setFormat(int i, Format format) {
        int iNextTopLevelArgStart = 0;
        int i2 = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                throw new ArrayIndexOutOfBoundsException(i);
            }
            if (i2 == i) {
                setCustomArgStartFormat(iNextTopLevelArgStart, format);
                return;
            }
            i2++;
        }
    }

    public Format[] getFormatsByArgumentIndex() {
        Format format;
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        ArrayList arrayList = new ArrayList();
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart >= 0) {
                int value = this.msgPattern.getPart(iNextTopLevelArgStart + 1).getValue();
                while (true) {
                    format = null;
                    if (value < arrayList.size()) {
                        break;
                    }
                    arrayList.add(null);
                }
                Map<Integer, Format> map = this.cachedFormatters;
                if (map != null) {
                    format = map.get(Integer.valueOf(iNextTopLevelArgStart));
                }
                arrayList.set(value, format);
            } else {
                return (Format[]) arrayList.toArray(new Format[arrayList.size()]);
            }
        }
    }

    public Format[] getFormats() {
        ArrayList arrayList = new ArrayList();
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart >= 0) {
                Map<Integer, Format> map = this.cachedFormatters;
                arrayList.add(map == null ? null : map.get(Integer.valueOf(iNextTopLevelArgStart)));
            } else {
                return (Format[]) arrayList.toArray(new Format[arrayList.size()]);
            }
        }
    }

    public Set<String> getArgumentNames() {
        HashSet hashSet = new HashSet();
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                return hashSet;
            }
            hashSet.add(getArgName(iNextTopLevelArgStart + 1));
        }
    }

    public Format getFormatByArgumentName(String str) {
        int iValidateArgumentName;
        if (this.cachedFormatters == null || (iValidateArgumentName = MessagePattern.validateArgumentName(str)) < -1) {
            return null;
        }
        int iNextTopLevelArgStart = 0;
        do {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                return null;
            }
        } while (!argNameMatches(iNextTopLevelArgStart + 1, str, iValidateArgumentName));
        return this.cachedFormatters.get(Integer.valueOf(iNextTopLevelArgStart));
    }

    public final StringBuffer format(Object[] objArr, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        format(objArr, null, new AppendableWrapper(stringBuffer), fieldPosition);
        return stringBuffer;
    }

    public final StringBuffer format(Map<String, Object> map, StringBuffer stringBuffer, FieldPosition fieldPosition) throws IOException {
        format(null, map, new AppendableWrapper(stringBuffer), fieldPosition);
        return stringBuffer;
    }

    public static String format(String str, Object... objArr) {
        return new MessageFormat(str).format(objArr);
    }

    public static String format(String str, Map<String, Object> map) {
        return new MessageFormat(str).format(map);
    }

    public boolean usesNamedArguments() {
        return this.msgPattern.hasNamedArguments();
    }

    @Override // java.text.Format
    public final StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) throws IOException {
        format(obj, new AppendableWrapper(stringBuffer), fieldPosition);
        return stringBuffer;
    }

    @Override // java.text.Format
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) throws IOException {
        if (obj == null) {
            throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
        }
        StringBuilder sb = new StringBuilder();
        AppendableWrapper appendableWrapper = new AppendableWrapper(sb);
        appendableWrapper.useAttributes();
        format(obj, appendableWrapper, (FieldPosition) null);
        AttributedString attributedString = new AttributedString(sb.toString());
        for (AttributeAndPosition attributeAndPosition : appendableWrapper.attributes) {
            attributedString.addAttribute(attributeAndPosition.key, attributeAndPosition.value, attributeAndPosition.start, attributeAndPosition.limit);
        }
        return attributedString.getIterator();
    }

    public Object[] parse(String str, ParsePosition parsePosition) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use named argument.");
        }
        int i = -1;
        int iNextTopLevelArgStart = 0;
        while (true) {
            iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
            if (iNextTopLevelArgStart < 0) {
                break;
            }
            int value = this.msgPattern.getPart(iNextTopLevelArgStart + 1).getValue();
            if (value > i) {
                i = value;
            }
        }
        Object[] objArr = new Object[i + 1];
        int index = parsePosition.getIndex();
        parse(0, str, parsePosition, objArr, null);
        if (parsePosition.getIndex() == index) {
            return null;
        }
        return objArr;
    }

    public Map<String, Object> parseToMap(String str, ParsePosition parsePosition) {
        HashMap map = new HashMap();
        int index = parsePosition.getIndex();
        parse(0, str, parsePosition, null, map);
        if (parsePosition.getIndex() == index) {
            return null;
        }
        return map;
    }

    public Object[] parse(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Object[] objArr = parse(str, parsePosition);
        if (parsePosition.getIndex() != 0) {
            return objArr;
        }
        throw new ParseException("MessageFormat parse error!", parsePosition.getErrorIndex());
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void parse(int r19, java.lang.String r20, java.text.ParsePosition r21, java.lang.Object[] r22, java.util.Map<java.lang.String, java.lang.Object> r23) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.MessageFormat.parse(int, java.lang.String, java.text.ParsePosition, java.lang.Object[], java.util.Map):void");
    }

    public Map<String, Object> parseToMap(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        HashMap map = new HashMap();
        parse(0, str, parsePosition, null, map);
        if (parsePosition.getIndex() != 0) {
            return map;
        }
        throw new ParseException("MessageFormat parse error!", parsePosition.getErrorIndex());
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        if (!this.msgPattern.hasNamedArguments()) {
            return parse(str, parsePosition);
        }
        return parseToMap(str, parsePosition);
    }

    @Override // java.text.Format
    public Object clone() {
        MessageFormat messageFormat = (MessageFormat) super.clone();
        if (this.customFormatArgStarts != null) {
            messageFormat.customFormatArgStarts = new HashSet();
            Iterator<Integer> it2 = this.customFormatArgStarts.iterator();
            while (it2.hasNext()) {
                messageFormat.customFormatArgStarts.add(it2.next());
            }
        } else {
            messageFormat.customFormatArgStarts = null;
        }
        if (this.cachedFormatters != null) {
            messageFormat.cachedFormatters = new HashMap();
            for (Map.Entry<Integer, Format> entry : this.cachedFormatters.entrySet()) {
                messageFormat.cachedFormatters.put(entry.getKey(), entry.getValue());
            }
        } else {
            messageFormat.cachedFormatters = null;
        }
        MessagePattern messagePattern = this.msgPattern;
        messageFormat.msgPattern = messagePattern == null ? null : (MessagePattern) messagePattern.clone();
        DateFormat dateFormat = this.stockDateFormatter;
        messageFormat.stockDateFormatter = dateFormat == null ? null : (DateFormat) dateFormat.clone();
        NumberFormat numberFormat = this.stockNumberFormatter;
        messageFormat.stockNumberFormatter = numberFormat == null ? null : (NumberFormat) numberFormat.clone();
        messageFormat.pluralProvider = null;
        messageFormat.ordinalProvider = null;
        return messageFormat;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessageFormat messageFormat = (MessageFormat) obj;
        return Objects.equals(this.ulocale, messageFormat.ulocale) && Objects.equals(this.msgPattern, messageFormat.msgPattern) && Objects.equals(this.cachedFormatters, messageFormat.cachedFormatters) && Objects.equals(this.customFormatArgStarts, messageFormat.customFormatArgStarts);
    }

    public int hashCode() {
        return this.msgPattern.getPatternString().hashCode();
    }

    public static class Field extends Format.Field {
        public static final Field ARGUMENT = new Field("message argument field");
        private static final long serialVersionUID = 7510380454602616157L;

        protected Field(String str) {
            super(str);
        }

        @Override // java.text.AttributedCharacterIterator.Attribute
        protected Object readResolve() throws InvalidObjectException {
            if (getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of MessageFormat.Field must implement readResolve.");
            }
            if (getName().equals(ARGUMENT.getName())) {
                return ARGUMENT;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
    }

    private DateFormat getStockDateFormatter() {
        if (this.stockDateFormatter == null) {
            this.stockDateFormatter = DateFormat.getDateTimeInstance(3, 3, this.ulocale);
        }
        return this.stockDateFormatter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public NumberFormat getStockNumberFormatter() {
        if (this.stockNumberFormatter == null) {
            this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
        }
        return this.stockNumberFormatter;
    }

    private void format(int i, PluralSelectorContext pluralSelectorContext, Object[] objArr, Map<String, Object> map, AppendableWrapper appendableWrapper, FieldPosition fieldPosition) throws IOException {
        Object obj;
        int i2;
        Object obj2;
        int i3;
        FieldPosition fieldPosition2;
        String str;
        AppendableWrapper appendableWrapper2;
        Map<Integer, Format> map2;
        PluralSelectorProvider pluralSelectorProvider;
        int i4;
        Object obj3;
        Format format;
        Map<String, Object> map3 = map;
        AppendableWrapper appendableWrapper3 = appendableWrapper;
        String patternString = this.msgPattern.getPatternString();
        int limit = this.msgPattern.getPart(i).getLimit();
        int i5 = i + 1;
        FieldPosition fieldPosition3 = fieldPosition;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i5);
            MessagePattern.Part.Type type = part.getType();
            appendableWrapper3.append(patternString, limit, part.getIndex());
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                return;
            }
            limit = part.getLimit();
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                if (pluralSelectorContext.forReplaceNumber) {
                    appendableWrapper3.formatAndAppend(pluralSelectorContext.formatter, pluralSelectorContext.number, pluralSelectorContext.numberString);
                } else {
                    appendableWrapper3.formatAndAppend(getStockNumberFormatter(), pluralSelectorContext.number);
                }
            } else {
                if (type == MessagePattern.Part.Type.ARG_START) {
                    int limitPartIndex = this.msgPattern.getLimitPartIndex(i5);
                    MessagePattern.ArgType argType = part.getArgType();
                    int i6 = i5 + 1;
                    MessagePattern.Part part2 = this.msgPattern.getPart(i6);
                    boolean z = false;
                    String substring = this.msgPattern.getSubstring(part2);
                    Object obj4 = null;
                    if (objArr != null) {
                        int value = part2.getValue();
                        Integer numValueOf = appendableWrapper.attributes != null ? Integer.valueOf(value) : null;
                        if (value < 0 || value >= objArr.length) {
                            z = true;
                        } else {
                            obj4 = objArr[value];
                        }
                        obj = numValueOf;
                    } else if (map3 == null || !map3.containsKey(substring)) {
                        obj = substring;
                        z = true;
                    } else {
                        obj4 = map3.get(substring);
                        obj = substring;
                    }
                    int i7 = i6 + 1;
                    int i8 = appendableWrapper.length;
                    if (z) {
                        appendableWrapper3.append("{" + substring + "}");
                    } else if (obj4 == null) {
                        appendableWrapper3.append("null");
                    } else if (pluralSelectorContext == null || pluralSelectorContext.numberArgIndex != i7 - 2) {
                        Map<Integer, Format> map4 = this.cachedFormatters;
                        if (map4 != null && (format = map4.get(Integer.valueOf(i7 - 2))) != null) {
                            if ((format instanceof ChoiceFormat) || (format instanceof PluralFormat) || (format instanceof SelectFormat)) {
                                String str2 = format.format(obj4);
                                if (str2.indexOf(123) >= 0 || (str2.indexOf(39) >= 0 && !this.msgPattern.jdkAposMode())) {
                                    i2 = i8;
                                    obj2 = obj;
                                    new MessageFormat(str2, this.ulocale).format(0, null, objArr, map, appendableWrapper, null);
                                } else {
                                    if (appendableWrapper.attributes == null) {
                                        appendableWrapper3.append(str2);
                                    } else {
                                        appendableWrapper3.formatAndAppend(format, obj4);
                                    }
                                    i2 = i8;
                                    obj2 = obj;
                                }
                                i3 = limitPartIndex;
                                fieldPosition2 = fieldPosition3;
                                str = patternString;
                                appendableWrapper2 = appendableWrapper3;
                            } else {
                                appendableWrapper3.formatAndAppend(format, obj4);
                            }
                        } else {
                            i2 = i8;
                            obj2 = obj;
                            if (argType == MessagePattern.ArgType.NONE || ((map2 = this.cachedFormatters) != null && map2.containsKey(Integer.valueOf(i7 - 2)))) {
                                i3 = limitPartIndex;
                                fieldPosition2 = fieldPosition3;
                                str = patternString;
                                appendableWrapper2 = appendableWrapper3;
                                if (obj4 instanceof Number) {
                                    appendableWrapper2.formatAndAppend(getStockNumberFormatter(), obj4);
                                } else if (obj4 instanceof Date) {
                                    appendableWrapper2.formatAndAppend(getStockDateFormatter(), obj4);
                                } else {
                                    appendableWrapper2.append(obj4.toString());
                                }
                            } else if (argType == MessagePattern.ArgType.CHOICE) {
                                if (!(obj4 instanceof Number)) {
                                    throw new IllegalArgumentException("'" + obj4 + "' is not a Number");
                                }
                                i3 = limitPartIndex;
                                str = patternString;
                                formatComplexSubMessage(findChoiceSubMessage(this.msgPattern, i7, ((Number) obj4).doubleValue()), null, objArr, map, appendableWrapper);
                                appendableWrapper2 = appendableWrapper3;
                                i4 = i2;
                                obj3 = obj2;
                                fieldPosition2 = fieldPosition3;
                                FieldPosition fieldPositionUpdateMetaData = updateMetaData(appendableWrapper2, i4, fieldPosition2, obj3);
                                limit = this.msgPattern.getPart(i3).getLimit();
                                fieldPosition3 = fieldPositionUpdateMetaData;
                                i5 = i3;
                            } else {
                                i3 = limitPartIndex;
                                str = patternString;
                                AppendableWrapper appendableWrapper4 = appendableWrapper3;
                                FieldPosition fieldPosition4 = fieldPosition3;
                                if (argType.hasPluralStyle()) {
                                    if (!(obj4 instanceof Number)) {
                                        throw new IllegalArgumentException("'" + obj4 + "' is not a Number");
                                    }
                                    if (argType == MessagePattern.ArgType.PLURAL) {
                                        if (this.pluralProvider == null) {
                                            this.pluralProvider = new PluralSelectorProvider(this, PluralRules.PluralType.CARDINAL);
                                        }
                                        pluralSelectorProvider = this.pluralProvider;
                                    } else {
                                        if (this.ordinalProvider == null) {
                                            this.ordinalProvider = new PluralSelectorProvider(this, PluralRules.PluralType.ORDINAL);
                                        }
                                        pluralSelectorProvider = this.ordinalProvider;
                                    }
                                    Number number = (Number) obj4;
                                    PluralSelectorContext pluralSelectorContext2 = new PluralSelectorContext(i7, substring, number, this.msgPattern.getPluralOffset(i7));
                                    fieldPosition2 = fieldPosition4;
                                    formatComplexSubMessage(PluralFormat.findSubMessage(this.msgPattern, i7, pluralSelectorProvider, pluralSelectorContext2, number.doubleValue()), pluralSelectorContext2, objArr, map, appendableWrapper);
                                    appendableWrapper2 = appendableWrapper4;
                                } else {
                                    fieldPosition2 = fieldPosition4;
                                    appendableWrapper2 = appendableWrapper4;
                                    if (argType == MessagePattern.ArgType.SELECT) {
                                        formatComplexSubMessage(SelectFormat.findSubMessage(this.msgPattern, i7, obj4.toString()), null, objArr, map, appendableWrapper);
                                    } else {
                                        throw new IllegalStateException("unexpected argType " + argType);
                                    }
                                }
                            }
                        }
                        i4 = i2;
                        obj3 = obj2;
                        FieldPosition fieldPositionUpdateMetaData2 = updateMetaData(appendableWrapper2, i4, fieldPosition2, obj3);
                        limit = this.msgPattern.getPart(i3).getLimit();
                        fieldPosition3 = fieldPositionUpdateMetaData2;
                        i5 = i3;
                    } else if (pluralSelectorContext.offset == 0.0d) {
                        appendableWrapper3.formatAndAppend(pluralSelectorContext.formatter, pluralSelectorContext.number, pluralSelectorContext.numberString);
                    } else {
                        appendableWrapper3.formatAndAppend(pluralSelectorContext.formatter, obj4);
                    }
                    fieldPosition2 = fieldPosition3;
                    str = patternString;
                    appendableWrapper2 = appendableWrapper3;
                    i4 = i8;
                    obj3 = obj;
                    i3 = limitPartIndex;
                    FieldPosition fieldPositionUpdateMetaData22 = updateMetaData(appendableWrapper2, i4, fieldPosition2, obj3);
                    limit = this.msgPattern.getPart(i3).getLimit();
                    fieldPosition3 = fieldPositionUpdateMetaData22;
                    i5 = i3;
                }
                i5++;
                map3 = map;
                patternString = str;
                appendableWrapper3 = appendableWrapper2;
            }
            str = patternString;
            appendableWrapper2 = appendableWrapper3;
            i5++;
            map3 = map;
            patternString = str;
            appendableWrapper3 = appendableWrapper2;
        }
    }

    private void formatComplexSubMessage(int i, PluralSelectorContext pluralSelectorContext, Object[] objArr, Map<String, Object> map, AppendableWrapper appendableWrapper) throws IOException {
        int index;
        String string;
        if (!this.msgPattern.jdkAposMode()) {
            format(i, pluralSelectorContext, objArr, map, appendableWrapper, null);
            return;
        }
        String patternString = this.msgPattern.getPatternString();
        StringBuilder sb = null;
        int limit = this.msgPattern.getPart(i).getLimit();
        while (true) {
            i++;
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) patternString, limit, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    if (pluralSelectorContext.forReplaceNumber) {
                        sb.append(pluralSelectorContext.numberString);
                    } else {
                        sb.append(getStockNumberFormatter().format(pluralSelectorContext.number));
                    }
                }
                limit = part.getLimit();
            } else if (type == MessagePattern.Part.Type.ARG_START) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) patternString, limit, index);
                i = this.msgPattern.getLimitPartIndex(i);
                limit = this.msgPattern.getPart(i).getLimit();
                MessagePattern.appendReducedApostrophes(patternString, index, limit, sb);
            }
        }
        if (sb == null) {
            string = patternString.substring(limit, index);
        } else {
            sb.append((CharSequence) patternString, limit, index);
            string = sb.toString();
        }
        if (string.indexOf(123) >= 0) {
            MessageFormat messageFormat = new MessageFormat("", this.ulocale);
            messageFormat.applyPattern(string, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
            messageFormat.format(0, null, objArr, map, appendableWrapper, null);
            return;
        }
        appendableWrapper.append(string);
    }

    private String getLiteralStringUntilNextArgument(int i) {
        StringBuilder sb = new StringBuilder();
        String patternString = this.msgPattern.getPatternString();
        int limit = this.msgPattern.getPart(i).getLimit();
        while (true) {
            i++;
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            sb.append((CharSequence) patternString, limit, part.getIndex());
            if (type == MessagePattern.Part.Type.ARG_START || type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            limit = part.getLimit();
        }
        return sb.toString();
    }

    private FieldPosition updateMetaData(AppendableWrapper appendableWrapper, int i, FieldPosition fieldPosition, Object obj) {
        if (appendableWrapper.attributes != null && i < appendableWrapper.length) {
            appendableWrapper.attributes.add(new AttributeAndPosition(obj, i, appendableWrapper.length));
        }
        if (fieldPosition == null || !Field.ARGUMENT.equals(fieldPosition.getFieldAttribute())) {
            return fieldPosition;
        }
        fieldPosition.setBeginIndex(i);
        fieldPosition.setEndIndex(appendableWrapper.length);
        return null;
    }

    private static int findChoiceSubMessage(MessagePattern messagePattern, int i, double d) {
        int iCountParts = messagePattern.countParts();
        int i2 = i + 2;
        while (true) {
            int limitPartIndex = messagePattern.getLimitPartIndex(i2) + 1;
            if (limitPartIndex >= iCountParts) {
                break;
            }
            int i3 = limitPartIndex + 1;
            MessagePattern.Part part = messagePattern.getPart(limitPartIndex);
            if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            double numericValue = messagePattern.getNumericValue(part);
            int i4 = i3 + 1;
            if (messagePattern.getPatternString().charAt(messagePattern.getPatternIndex(i3)) == '<') {
                if (d <= numericValue) {
                    break;
                }
                i2 = i4;
            } else {
                if (d < numericValue) {
                    break;
                }
                i2 = i4;
            }
        }
        return i2;
    }

    private static double parseChoiceArgument(MessagePattern messagePattern, int i, String str, ParsePosition parsePosition) {
        int i2;
        int index = parsePosition.getIndex();
        double d = Double.NaN;
        int i3 = index;
        while (true) {
            if (messagePattern.getPartType(i) == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            double numericValue = messagePattern.getNumericValue(messagePattern.getPart(i));
            int i4 = i + 2;
            int limitPartIndex = messagePattern.getLimitPartIndex(i4);
            int iMatchStringUntilLimitPart = matchStringUntilLimitPart(messagePattern, i4, limitPartIndex, str, index);
            if (iMatchStringUntilLimitPart >= 0 && (i2 = iMatchStringUntilLimitPart + index) > i3) {
                i3 = i2;
                if (i2 == str.length()) {
                    d = numericValue;
                    break;
                }
                d = numericValue;
            }
            i = limitPartIndex + 1;
        }
        if (i3 == index) {
            parsePosition.setErrorIndex(index);
        } else {
            parsePosition.setIndex(i3);
        }
        return d;
    }

    private static int matchStringUntilLimitPart(MessagePattern messagePattern, int i, int i2, String str, int i3) {
        String patternString = messagePattern.getPatternString();
        int limit = messagePattern.getPart(i).getLimit();
        int i4 = 0;
        while (true) {
            i++;
            MessagePattern.Part part = messagePattern.getPart(i);
            if (i == i2 || part.getType() == MessagePattern.Part.Type.SKIP_SYNTAX) {
                int index = part.getIndex() - limit;
                if (index != 0 && !str.regionMatches(i3, patternString, limit, index)) {
                    return -1;
                }
                i4 += index;
                if (i == i2) {
                    return i4;
                }
                limit = part.getLimit();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findOtherSubMessage(int i) {
        int iCountParts = this.msgPattern.countParts();
        if (this.msgPattern.getPart(i).getType().hasNumericValue()) {
            i++;
        }
        do {
            int i2 = i + 1;
            MessagePattern.Part part = this.msgPattern.getPart(i);
            if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) {
                return 0;
            }
            if (this.msgPattern.partSubstringMatches(part, "other")) {
                return i2;
            }
            if (this.msgPattern.getPartType(i2).hasNumericValue()) {
                i2++;
            }
            i = this.msgPattern.getLimitPartIndex(i2) + 1;
        } while (i < iCountParts);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findFirstPluralNumberArg(int i, String str) {
        while (true) {
            i++;
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                return 0;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                return -1;
            }
            if (type == MessagePattern.Part.Type.ARG_START) {
                MessagePattern.ArgType argType = part.getArgType();
                if (str.length() != 0 && (argType == MessagePattern.ArgType.NONE || argType == MessagePattern.ArgType.SIMPLE)) {
                    if (this.msgPattern.partSubstringMatches(this.msgPattern.getPart(i + 1), str)) {
                        return i;
                    }
                }
                i = this.msgPattern.getLimitPartIndex(i);
            }
        }
    }

    private static final class PluralSelectorContext {
        String argName;
        boolean forReplaceNumber;
        Format formatter;
        Number number;
        int numberArgIndex;
        String numberString;
        double offset;
        int startIndex;

        private PluralSelectorContext(int i, String str, Number number, double d) {
            this.startIndex = i;
            this.argName = str;
            if (d == 0.0d) {
                this.number = number;
            } else {
                this.number = Double.valueOf(number.doubleValue() - d);
            }
            this.offset = d;
        }

        public String toString() {
            throw new AssertionError("PluralSelectorContext being formatted, rather than its number");
        }
    }

    private static final class PluralSelectorProvider implements PluralFormat.PluralSelector {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private MessageFormat msgFormat;
        private PluralRules rules;
        private PluralRules.PluralType type;

        public PluralSelectorProvider(MessageFormat messageFormat, PluralRules.PluralType pluralType) {
            this.msgFormat = messageFormat;
            this.type = pluralType;
        }

        @Override // com.ibm.icu.text.PluralFormat.PluralSelector
        public String select(Object obj, double d) {
            if (this.rules == null) {
                this.rules = PluralRules.forLocale(this.msgFormat.ulocale, this.type);
            }
            PluralSelectorContext pluralSelectorContext = (PluralSelectorContext) obj;
            pluralSelectorContext.numberArgIndex = this.msgFormat.findFirstPluralNumberArg(this.msgFormat.findOtherSubMessage(pluralSelectorContext.startIndex), pluralSelectorContext.argName);
            if (pluralSelectorContext.numberArgIndex > 0 && this.msgFormat.cachedFormatters != null) {
                pluralSelectorContext.formatter = (Format) this.msgFormat.cachedFormatters.get(Integer.valueOf(pluralSelectorContext.numberArgIndex));
            }
            if (pluralSelectorContext.formatter == null) {
                pluralSelectorContext.formatter = this.msgFormat.getStockNumberFormatter();
                pluralSelectorContext.forReplaceNumber = true;
            }
            pluralSelectorContext.numberString = pluralSelectorContext.formatter.format(pluralSelectorContext.number);
            if (pluralSelectorContext.formatter instanceof DecimalFormat) {
                return this.rules.select(((DecimalFormat) pluralSelectorContext.formatter).getFixedDecimal(d));
            }
            return this.rules.select(d);
        }
    }

    private void format(Object obj, AppendableWrapper appendableWrapper, FieldPosition fieldPosition) throws IOException {
        if (obj == null || (obj instanceof Map)) {
            format(null, (Map) obj, appendableWrapper, fieldPosition);
        } else {
            format((Object[]) obj, null, appendableWrapper, fieldPosition);
        }
    }

    private void format(Object[] objArr, Map<String, Object> map, AppendableWrapper appendableWrapper, FieldPosition fieldPosition) throws IOException {
        if (objArr != null && this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        format(0, null, objArr, map, appendableWrapper, fieldPosition);
    }

    private void resetPattern() {
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern != null) {
            messagePattern.clear();
        }
        Map<Integer, Format> map = this.cachedFormatters;
        if (map != null) {
            map.clear();
        }
        this.customFormatArgStarts = null;
    }

    Format dateTimeFormatForPatternOrSkeleton(String str) {
        int iSkipWhiteSpace = PatternProps.skipWhiteSpace(str, 0);
        if (str.regionMatches(iSkipWhiteSpace, "::", 0, 2)) {
            return DateFormat.getInstanceForSkeleton(str.substring(iSkipWhiteSpace + 2), this.ulocale);
        }
        return new SimpleDateFormat(str, this.ulocale);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v31, types: [java.text.Format] */
    /* JADX WARN: Type inference failed for: r7v33, types: [java.text.Format] */
    /* JADX WARN: Type inference failed for: r7v51 */
    /* JADX WARN: Type inference failed for: r7v52 */
    /* JADX WARN: Type inference failed for: r7v53 */
    private Format createAppropriateFormat(String str, String str2) {
        int iFindKeyword = findKeyword(str, typeList);
        if (iFindKeyword == 0) {
            int iFindKeyword2 = findKeyword(str2, modifierList);
            if (iFindKeyword2 == 0) {
                return NumberFormat.getInstance(this.ulocale);
            }
            if (iFindKeyword2 == 1) {
                return NumberFormat.getCurrencyInstance(this.ulocale);
            }
            if (iFindKeyword2 == 2) {
                return NumberFormat.getPercentInstance(this.ulocale);
            }
            if (iFindKeyword2 == 3) {
                return NumberFormat.getIntegerInstance(this.ulocale);
            }
            int iSkipWhiteSpace = PatternProps.skipWhiteSpace(str2, 0);
            if (str2.regionMatches(iSkipWhiteSpace, "::", 0, 2)) {
                return NumberFormatter.forSkeleton(str2.substring(iSkipWhiteSpace + 2)).locale(this.ulocale).toFormat();
            }
            return new DecimalFormat(str2, new DecimalFormatSymbols(this.ulocale));
        }
        if (iFindKeyword == 1) {
            int iFindKeyword3 = findKeyword(str2, dateModifierList);
            if (iFindKeyword3 == 0) {
                return DateFormat.getDateInstance(2, this.ulocale);
            }
            if (iFindKeyword3 == 1) {
                return DateFormat.getDateInstance(3, this.ulocale);
            }
            if (iFindKeyword3 == 2) {
                return DateFormat.getDateInstance(2, this.ulocale);
            }
            if (iFindKeyword3 == 3) {
                return DateFormat.getDateInstance(1, this.ulocale);
            }
            if (iFindKeyword3 == 4) {
                return DateFormat.getDateInstance(0, this.ulocale);
            }
            return dateTimeFormatForPatternOrSkeleton(str2);
        }
        if (iFindKeyword == 2) {
            int iFindKeyword4 = findKeyword(str2, dateModifierList);
            if (iFindKeyword4 == 0) {
                return DateFormat.getTimeInstance(2, this.ulocale);
            }
            if (iFindKeyword4 == 1) {
                return DateFormat.getTimeInstance(3, this.ulocale);
            }
            if (iFindKeyword4 == 2) {
                return DateFormat.getTimeInstance(2, this.ulocale);
            }
            if (iFindKeyword4 == 3) {
                return DateFormat.getTimeInstance(1, this.ulocale);
            }
            if (iFindKeyword4 == 4) {
                return DateFormat.getTimeInstance(0, this.ulocale);
            }
            return dateTimeFormatForPatternOrSkeleton(str2);
        }
        try {
            if (iFindKeyword == 3) {
                RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(this.ulocale, 1);
                String strTrim = str2.trim();
                if (strTrim.length() == 0) {
                    return ruleBasedNumberFormat;
                }
                ruleBasedNumberFormat.setDefaultRuleSet(strTrim);
                str = ruleBasedNumberFormat;
            } else if (iFindKeyword == 4) {
                RuleBasedNumberFormat ruleBasedNumberFormat2 = new RuleBasedNumberFormat(this.ulocale, 2);
                String strTrim2 = str2.trim();
                if (strTrim2.length() == 0) {
                    return ruleBasedNumberFormat2;
                }
                ruleBasedNumberFormat2.setDefaultRuleSet(strTrim2);
                str = ruleBasedNumberFormat2;
            } else if (iFindKeyword == 5) {
                RuleBasedNumberFormat ruleBasedNumberFormat3 = new RuleBasedNumberFormat(this.ulocale, 3);
                String strTrim3 = str2.trim();
                if (strTrim3.length() == 0) {
                    return ruleBasedNumberFormat3;
                }
                ruleBasedNumberFormat3.setDefaultRuleSet(strTrim3);
                str = ruleBasedNumberFormat3;
            } else {
                throw new IllegalArgumentException("Unknown format type \"" + ((String) str) + "\"");
            }
            return str;
        } catch (Exception unused) {
            return str;
        }
    }

    private static final int findKeyword(String str, String[] strArr) {
        String lowerCase = PatternProps.trimWhiteSpace(str).toLowerCase(rootLocale);
        for (int i = 0; i < strArr.length; i++) {
            if (lowerCase.equals(strArr[i])) {
                return i;
            }
        }
        return -1;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.ulocale.toLanguageTag());
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        objectOutputStream.writeObject(this.msgPattern.getApostropheMode());
        objectOutputStream.writeObject(this.msgPattern.getPatternString());
        Set<Integer> set = this.customFormatArgStarts;
        if (set == null || set.isEmpty()) {
            objectOutputStream.writeInt(0);
        } else {
            objectOutputStream.writeInt(this.customFormatArgStarts.size());
            int iNextTopLevelArgStart = 0;
            int i = 0;
            while (true) {
                iNextTopLevelArgStart = nextTopLevelArgStart(iNextTopLevelArgStart);
                if (iNextTopLevelArgStart < 0) {
                    break;
                }
                if (this.customFormatArgStarts.contains(Integer.valueOf(iNextTopLevelArgStart))) {
                    objectOutputStream.writeInt(i);
                    objectOutputStream.writeObject(this.cachedFormatters.get(Integer.valueOf(iNextTopLevelArgStart)));
                }
                i++;
            }
        }
        objectOutputStream.writeInt(0);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.ulocale = ULocale.forLanguageTag((String) objectInputStream.readObject());
        MessagePattern.ApostropheMode apostropheMode = (MessagePattern.ApostropheMode) objectInputStream.readObject();
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern == null || apostropheMode != messagePattern.getApostropheMode()) {
            this.msgPattern = new MessagePattern(apostropheMode);
        }
        String str = (String) objectInputStream.readObject();
        if (str != null) {
            applyPattern(str);
        }
        for (int i = objectInputStream.readInt(); i > 0; i--) {
            setFormat(objectInputStream.readInt(), (Format) objectInputStream.readObject());
        }
        for (int i2 = objectInputStream.readInt(); i2 > 0; i2--) {
            objectInputStream.readInt();
            objectInputStream.readObject();
        }
    }

    private void cacheExplicitFormats() {
        String substring;
        Map<Integer, Format> map = this.cachedFormatters;
        if (map != null) {
            map.clear();
        }
        this.customFormatArgStarts = null;
        int iCountParts = this.msgPattern.countParts() - 2;
        int i = 1;
        while (i < iCountParts) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            if (part.getType() == MessagePattern.Part.Type.ARG_START && part.getArgType() == MessagePattern.ArgType.SIMPLE) {
                int i2 = i + 2;
                MessagePattern messagePattern = this.msgPattern;
                int i3 = i2 + 1;
                String substring2 = messagePattern.getSubstring(messagePattern.getPart(i2));
                MessagePattern.Part part2 = this.msgPattern.getPart(i3);
                if (part2.getType() == MessagePattern.Part.Type.ARG_STYLE) {
                    substring = this.msgPattern.getSubstring(part2);
                    i3++;
                } else {
                    substring = "";
                }
                setArgStartFormat(i, createAppropriateFormat(substring2, substring));
                i = i3;
            }
            i++;
        }
    }

    private void setArgStartFormat(int i, Format format) {
        if (this.cachedFormatters == null) {
            this.cachedFormatters = new HashMap();
        }
        this.cachedFormatters.put(Integer.valueOf(i), format);
    }

    private void setCustomArgStartFormat(int i, Format format) {
        setArgStartFormat(i, format);
        if (this.customFormatArgStarts == null) {
            this.customFormatArgStarts = new HashSet();
        }
        this.customFormatArgStarts.add(Integer.valueOf(i));
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0047 A[PHI: r6
      0x0047: PHI (r6v4 int) = (r6v1 int), (r6v1 int), (r6v1 int), (r6v6 int) binds: [B:21:0x003b, B:24:0x0041, B:19:0x0038, B:16:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String autoQuoteApostrophe(java.lang.String r13) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            int r1 = r13.length()
            r2 = 2
            int r1 = r1 * 2
            r0.<init>(r1)
            int r1 = r13.length()
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        L14:
            r7 = 39
            r8 = 1
            if (r4 >= r1) goto L59
            char r9 = r13.charAt(r4)
            r10 = 3
            r11 = 123(0x7b, float:1.72E-43)
            if (r5 == 0) goto L49
            r12 = 125(0x7d, float:1.75E-43)
            if (r5 == r8) goto L3b
            if (r5 == r2) goto L38
            if (r5 == r10) goto L2b
            goto L53
        L2b:
            if (r9 == r11) goto L35
            if (r9 == r12) goto L30
            goto L53
        L30:
            int r6 = r6 + (-1)
            if (r6 != 0) goto L53
            goto L47
        L35:
            int r6 = r6 + 1
            goto L53
        L38:
            if (r9 == r7) goto L47
            goto L53
        L3b:
            if (r9 == r7) goto L47
            if (r9 == r11) goto L45
            if (r9 == r12) goto L45
            r0.append(r7)
            goto L47
        L45:
            r5 = 2
            goto L53
        L47:
            r5 = 0
            goto L53
        L49:
            if (r9 == r7) goto L52
            if (r9 == r11) goto L4e
            goto L53
        L4e:
            int r6 = r6 + 1
            r5 = 3
            goto L53
        L52:
            r5 = 1
        L53:
            r0.append(r9)
            int r4 = r4 + 1
            goto L14
        L59:
            if (r5 == r8) goto L5d
            if (r5 != r2) goto L60
        L5d:
            r0.append(r7)
        L60:
            java.lang.String r13 = new java.lang.String
            r13.<init>(r0)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.MessageFormat.autoQuoteApostrophe(java.lang.String):java.lang.String");
    }

    private static final class AppendableWrapper {
        private Appendable app;
        private List<AttributeAndPosition> attributes = null;
        private int length;

        public AppendableWrapper(StringBuilder sb) {
            this.app = sb;
            this.length = sb.length();
        }

        public AppendableWrapper(StringBuffer stringBuffer) {
            this.app = stringBuffer;
            this.length = stringBuffer.length();
        }

        public void useAttributes() {
            this.attributes = new ArrayList();
        }

        public void append(CharSequence charSequence) throws IOException {
            try {
                this.app.append(charSequence);
                this.length += charSequence.length();
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void append(CharSequence charSequence, int i, int i2) throws IOException {
            try {
                this.app.append(charSequence, i, i2);
                this.length += i2 - i;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void append(CharacterIterator characterIterator) {
            this.length += append(this.app, characterIterator);
        }

        public static int append(Appendable appendable, CharacterIterator characterIterator) throws IOException {
            try {
                int beginIndex = characterIterator.getBeginIndex();
                int endIndex = characterIterator.getEndIndex();
                int i = endIndex - beginIndex;
                if (beginIndex < endIndex) {
                    appendable.append(characterIterator.first());
                    while (true) {
                        beginIndex++;
                        if (beginIndex >= endIndex) {
                            break;
                        }
                        appendable.append(characterIterator.next());
                    }
                }
                return i;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void formatAndAppend(Format format, Object obj) throws IOException {
            if (this.attributes == null) {
                append(format.format(obj));
                return;
            }
            AttributedCharacterIterator toCharacterIterator = format.formatToCharacterIterator(obj);
            int i = this.length;
            append(toCharacterIterator);
            toCharacterIterator.first();
            int index = toCharacterIterator.getIndex();
            int endIndex = toCharacterIterator.getEndIndex();
            int i2 = i - index;
            while (index < endIndex) {
                Map<AttributedCharacterIterator.Attribute, Object> attributes = toCharacterIterator.getAttributes();
                int runLimit = toCharacterIterator.getRunLimit();
                if (attributes.size() != 0) {
                    for (Map.Entry<AttributedCharacterIterator.Attribute, Object> entry : attributes.entrySet()) {
                        this.attributes.add(new AttributeAndPosition(entry.getKey(), entry.getValue(), i2 + index, i2 + runLimit));
                    }
                }
                toCharacterIterator.setIndex(runLimit);
                index = runLimit;
            }
        }

        public void formatAndAppend(Format format, Object obj, String str) throws IOException {
            if (this.attributes == null && str != null) {
                append(str);
            } else {
                formatAndAppend(format, obj);
            }
        }
    }

    private static final class AttributeAndPosition {
        private AttributedCharacterIterator.Attribute key;
        private int limit;
        private int start;
        private Object value;

        public AttributeAndPosition(Object obj, int i, int i2) {
            init(Field.ARGUMENT, obj, i, i2);
        }

        public AttributeAndPosition(AttributedCharacterIterator.Attribute attribute, Object obj, int i, int i2) {
            init(attribute, obj, i, i2);
        }

        public void init(AttributedCharacterIterator.Attribute attribute, Object obj, int i, int i2) {
            this.key = attribute;
            this.value = obj;
            this.start = i;
            this.limit = i2;
        }
    }
}
