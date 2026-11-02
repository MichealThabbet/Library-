package com.ibm.icu.impl.number;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.number.LongNameMultiplexer;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.units.MeasureUnitImpl;
import com.ibm.icu.impl.units.SingleUnitImpl;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class LongNameHandler implements MicroPropsGenerator, ModifierStore, LongNameMultiplexer.ParentlessMicroPropsGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int ARRAY_LENGTH;
    private static final int DNAM_INDEX;
    private static final int GENDER_INDEX;
    private static final int PER_INDEX;
    private static int i;
    private String gender = "";
    private final Map<StandardPlural, SimpleModifier> modifiers;
    private final MicroPropsGenerator parent;
    private final PluralRules rules;

    private enum PlaceholderPosition {
        NONE,
        BEGINNING,
        MIDDLE,
        END
    }

    static {
        int i2 = StandardPlural.COUNT;
        int i3 = i;
        i = i3 + 1;
        DNAM_INDEX = i2 + i3;
        int i4 = StandardPlural.COUNT;
        int i5 = i;
        i = i5 + 1;
        PER_INDEX = i4 + i5;
        int i6 = StandardPlural.COUNT;
        int i7 = i;
        i = i7 + 1;
        GENDER_INDEX = i6 + i7;
        int i8 = StandardPlural.COUNT;
        int i9 = i;
        i = i9 + 1;
        ARRAY_LENGTH = i8 + i9;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getIndex(String str) {
        if (str.equals("dnam")) {
            return DNAM_INDEX;
        }
        if (str.equals("per")) {
            return PER_INDEX;
        }
        if (str.equals("gender")) {
            return GENDER_INDEX;
        }
        return StandardPlural.fromString(str).ordinal();
    }

    static String getWithPlural(String[] strArr, StandardPlural standardPlural) {
        String str = strArr[standardPlural.ordinal()];
        if (str == null) {
            str = strArr[StandardPlural.OTHER.ordinal()];
        }
        if (str != null) {
            return str;
        }
        throw new ICUException("Could not find data in 'other' plural variant");
    }

    private static class ExtractCorePatternResult {
        String coreUnit;
        char joinerChar;
        PlaceholderPosition placeholderPosition;

        private ExtractCorePatternResult() {
        }
    }

    private static ExtractCorePatternResult extractCorePattern(String str) {
        ExtractCorePatternResult extractCorePatternResult = new ExtractCorePatternResult();
        extractCorePatternResult.joinerChar = (char) 0;
        int length = str.length();
        if (str.startsWith("{0}")) {
            extractCorePatternResult.placeholderPosition = PlaceholderPosition.BEGINNING;
            if (length > 3 && Character.isSpaceChar(str.charAt(3))) {
                extractCorePatternResult.joinerChar = str.charAt(3);
                extractCorePatternResult.coreUnit = str.substring(4);
            } else {
                extractCorePatternResult.coreUnit = str.substring(3);
            }
        } else if (str.endsWith("{0}")) {
            extractCorePatternResult.placeholderPosition = PlaceholderPosition.END;
            int i2 = length - 4;
            if (Character.isSpaceChar(str.charAt(i2))) {
                extractCorePatternResult.coreUnit = str.substring(0, i2);
                extractCorePatternResult.joinerChar = str.charAt(i2);
            } else {
                extractCorePatternResult.coreUnit = str.substring(0, length - 3);
            }
        } else if (str.indexOf("{0}", 1) == -1) {
            extractCorePatternResult.placeholderPosition = PlaceholderPosition.NONE;
            extractCorePatternResult.coreUnit = str;
        } else {
            extractCorePatternResult.placeholderPosition = PlaceholderPosition.MIDDLE;
            extractCorePatternResult.coreUnit = str;
        }
        return extractCorePatternResult;
    }

    private static String getGenderForBuiltin(ULocale uLocale, MeasureUnit measureUnit) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_UNIT_BASE_NAME, uLocale);
        StringBuilder sb = new StringBuilder();
        sb.append("units/");
        sb.append(measureUnit.getType());
        sb.append("/");
        if (measureUnit.getSubtype() != null && measureUnit.getSubtype().endsWith("-person")) {
            sb.append((CharSequence) measureUnit.getSubtype(), 0, measureUnit.getSubtype().length() - 7);
        } else {
            sb.append(measureUnit.getSubtype());
        }
        sb.append("/gender");
        try {
            return iCUResourceBundle.getWithFallback(sb.toString()).getString();
        } catch (MissingResourceException unused) {
            return "";
        }
    }

    private static final class InflectedPluralSink extends UResource.Sink {
        String caseVariant;
        String gender;
        String[] outArray;

        public InflectedPluralSink(String str, String str2, String[] strArr) {
            this.gender = str;
            this.caseVariant = str2;
            this.outArray = strArr;
            for (int i = 0; i < LongNameHandler.ARRAY_LENGTH; i++) {
                strArr[i] = null;
            }
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                int index = LongNameHandler.getIndex(key.toString());
                if (this.outArray[index] == null && loadForPluralForm(value.getTable(), value)) {
                    this.outArray[index] = value.getString();
                }
            }
        }

        private boolean loadForPluralForm(UResource.Table table, UResource.Value value) {
            String str = this.gender;
            if (str != null && !str.isEmpty()) {
                if (loadForGender(table, this.gender, value)) {
                    return true;
                }
                if (this.gender != "neuter" && loadForGender(table, "neuter", value)) {
                    return true;
                }
            }
            return loadForGender(table, BaseLocale.SEP, value);
        }

        private boolean loadForGender(UResource.Table table, String str, UResource.Value value) {
            if (!table.findValue(str, value)) {
                return false;
            }
            UResource.Table table2 = value.getTable();
            String str2 = this.caseVariant;
            if (str2 != null && !str2.isEmpty()) {
                if (loadForCase(table2, this.caseVariant, value)) {
                    return true;
                }
                if (this.caseVariant != "nominative" && loadForCase(table2, "nominative", value)) {
                    return true;
                }
            }
            return loadForCase(table2, BaseLocale.SEP, value);
        }

        private boolean loadForCase(UResource.Table table, String str, UResource.Value value) {
            return table.findValue(str, value);
        }
    }

    static void getInflectedMeasureData(String str, ULocale uLocale, NumberFormatter.UnitWidth unitWidth, String str2, String str3, String[] strArr) throws MissingResourceException {
        InflectedPluralSink inflectedPluralSink = new InflectedPluralSink(str2, str3, strArr);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_UNIT_BASE_NAME, uLocale);
        StringBuilder sb = new StringBuilder();
        sb.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            sb.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            sb.append("Short");
        }
        sb.append("/");
        sb.append(str);
        try {
            iCUResourceBundle.getAllItemsWithFallback(sb.toString(), inflectedPluralSink);
            if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
                return;
            }
        } catch (MissingResourceException unused) {
        }
        sb.setLength(0);
        sb.append("unitsShort/");
        sb.append(str);
        iCUResourceBundle.getAllItemsWithFallback(sb.toString(), inflectedPluralSink);
    }

    private static final class PluralTableSink extends UResource.Sink {
        String[] outArray;

        public PluralTableSink(String[] strArr) {
            this.outArray = strArr;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                String string = key.toString();
                if (!string.equals("case")) {
                    int index = LongNameHandler.getIndex(string);
                    if (this.outArray[index] == null) {
                        this.outArray[index] = value.getString();
                    }
                }
            }
        }
    }

    static void getMeasureData(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, String str, String[] strArr) {
        PluralTableSink pluralTableSink = new PluralTableSink(strArr);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_UNIT_BASE_NAME, uLocale);
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(measureUnit.getType());
        sb.append("/");
        if (measureUnit.getSubtype() != null && measureUnit.getSubtype().endsWith("-person")) {
            sb.append((CharSequence) measureUnit.getSubtype(), 0, measureUnit.getSubtype().length() - 7);
        } else {
            sb.append(measureUnit.getSubtype());
        }
        if (unitWidth != NumberFormatter.UnitWidth.FULL_NAME) {
            try {
                strArr[GENDER_INDEX] = iCUResourceBundle.getWithFallback("units" + ((CharSequence) sb) + "/gender").getString();
            } catch (MissingResourceException unused) {
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            sb2.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            sb2.append("Short");
        }
        sb2.append((CharSequence) sb);
        if (unitWidth == NumberFormatter.UnitWidth.FULL_NAME && str != null && !str.isEmpty()) {
            try {
                iCUResourceBundle.getAllItemsWithFallback(((CharSequence) sb2) + "/case/" + str, pluralTableSink);
            } catch (MissingResourceException unused2) {
            }
        }
        try {
            iCUResourceBundle.getAllItemsWithFallback(sb2.toString(), pluralTableSink);
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("No data for unit " + measureUnit + ", width " + unitWidth, e);
        }
    }

    private static void getCurrencyLongNameData(ULocale uLocale, Currency currency, String[] strArr) {
        for (Map.Entry<String, String> entry : CurrencyData.provider.getInstance(uLocale, true).getUnitPatterns().entrySet()) {
            String key = entry.getKey();
            strArr[getIndex(key)] = entry.getValue().replace("{1}", currency.getName(uLocale, 2, key, (boolean[]) null));
        }
    }

    private static String getCompoundValue(String str, ULocale uLocale, NumberFormatter.UnitWidth unitWidth) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_UNIT_BASE_NAME, uLocale);
        StringBuilder sb = new StringBuilder();
        sb.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            sb.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            sb.append("Short");
        }
        sb.append("/compound/");
        sb.append(str);
        try {
            return iCUResourceBundle.getStringWithFallback(sb.toString());
        } catch (MissingResourceException unused) {
            if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
                return "";
            }
            sb.setLength(0);
            sb.append("unitsShort/compound/");
            sb.append(str);
            try {
                return iCUResourceBundle.getStringWithFallback(sb.toString());
            } catch (MissingResourceException unused2) {
                return "";
            }
        }
    }

    private static class DerivedComponents {
        private String value0;
        private String value1;

        DerivedComponents(ULocale uLocale, String str, String str2) {
            ICUResourceBundle iCUResourceBundle;
            this.value0 = "";
            this.value1 = "";
            try {
                ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle) ((ICUResourceBundle) ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "grammaticalFeatures")).get("grammaticalData")).get("derivations");
                try {
                    iCUResourceBundle = (ICUResourceBundle) iCUResourceBundle2.get(uLocale.getLanguage());
                } catch (MissingResourceException unused) {
                    iCUResourceBundle = (ICUResourceBundle) iCUResourceBundle2.get("root");
                }
                ICUResourceBundle iCUResourceBundle3 = (ICUResourceBundle) ((ICUResourceBundle) ((ICUResourceBundle) iCUResourceBundle.get("component")).get(str)).get(str2);
                String string = iCUResourceBundle3.getString(0);
                if (string.compareTo("compound") == 0) {
                    this.value0 = null;
                } else {
                    this.value0 = string;
                }
                String string2 = iCUResourceBundle3.getString(1);
                if (string2.compareTo("compound") == 0) {
                    this.value1 = null;
                } else {
                    this.value1 = string2;
                }
            } catch (MissingResourceException unused2) {
            }
        }

        String value0(String str) {
            String str2 = this.value0;
            return str2 != null ? str2 : str;
        }

        String value1(String str) {
            String str2 = this.value1;
            return str2 != null ? str2 : str;
        }
    }

    private static String getDeriveCompoundRule(ULocale uLocale, String str, String str2) {
        ICUResourceBundle iCUResourceBundle;
        ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle) ((ICUResourceBundle) ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "grammaticalFeatures")).get("grammaticalData")).get("derivations");
        try {
            iCUResourceBundle = (ICUResourceBundle) iCUResourceBundle2.get(uLocale.getLanguage());
        } catch (MissingResourceException unused) {
            iCUResourceBundle = (ICUResourceBundle) iCUResourceBundle2.get("root");
        }
        return ((ICUResourceBundle) ((ICUResourceBundle) iCUResourceBundle.get("compound")).get(str)).getString(str2);
    }

    private static String getDerivedGender(ULocale uLocale, String str, String[] strArr, String[] strArr2) {
        String deriveCompoundRule = getDeriveCompoundRule(uLocale, "gender", str);
        if (deriveCompoundRule.length() != 1) {
            return deriveCompoundRule;
        }
        char cCharAt = deriveCompoundRule.charAt(0);
        if (cCharAt == '0') {
            return strArr[GENDER_INDEX];
        }
        if (cCharAt != '1') {
            return deriveCompoundRule;
        }
        if (strArr2 == null) {
            return null;
        }
        return strArr2[GENDER_INDEX];
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String calculateGenderForUnit(com.ibm.icu.util.ULocale r7, com.ibm.icu.util.MeasureUnit r8) {
        /*
            com.ibm.icu.impl.units.MeasureUnitImpl r8 = r8.getCopyOfMeasureUnitImpl()
            java.util.ArrayList r0 = r8.getSingleUnits()
            com.ibm.icu.util.MeasureUnit$Complexity r1 = r8.getComplexity()
            com.ibm.icu.util.MeasureUnit$Complexity r2 = com.ibm.icu.util.MeasureUnit.Complexity.COMPOUND
            java.lang.String r3 = "gender"
            r4 = 0
            r5 = 1
            if (r1 != r2) goto L7e
            int r8 = r0.size()
            int r8 = r8 - r5
            java.lang.Object r1 = r0.get(r8)
            com.ibm.icu.impl.units.SingleUnitImpl r1 = (com.ibm.icu.impl.units.SingleUnitImpl) r1
            int r1 = r1.getDimensionality()
            if (r1 >= 0) goto L60
            java.lang.String r1 = "per"
            java.lang.String r1 = getDeriveCompoundRule(r7, r3, r1)
            int r2 = r1.length()
            if (r2 == r5) goto L32
            return r1
        L32:
            char r1 = r1.charAt(r4)
            r2 = 49
            if (r1 != r2) goto L4a
            r1 = 0
        L3b:
            java.lang.Object r2 = r0.get(r1)
            com.ibm.icu.impl.units.SingleUnitImpl r2 = (com.ibm.icu.impl.units.SingleUnitImpl) r2
            int r2 = r2.getDimensionality()
            if (r2 < 0) goto L61
            int r1 = r1 + 1
            goto L3b
        L4a:
            if (r8 < 0) goto L5b
            java.lang.Object r1 = r0.get(r8)
            com.ibm.icu.impl.units.SingleUnitImpl r1 = (com.ibm.icu.impl.units.SingleUnitImpl) r1
            int r1 = r1.getDimensionality()
            if (r1 >= 0) goto L5b
            int r8 = r8 + (-1)
            goto L4a
        L5b:
            if (r8 >= 0) goto L60
            java.lang.String r7 = ""
            return r7
        L60:
            r1 = 0
        L61:
            if (r8 <= r1) goto L7c
            java.lang.String r2 = "times"
            java.lang.String r2 = getDeriveCompoundRule(r7, r3, r2)
            int r6 = r2.length()
            if (r6 == r5) goto L71
            return r2
        L71:
            char r2 = r2.charAt(r4)
            r4 = 48
            if (r2 != r4) goto L7a
            goto L7c
        L7a:
            r4 = r8
            goto L86
        L7c:
            r4 = r1
            goto L86
        L7e:
            com.ibm.icu.util.MeasureUnit$Complexity r8 = r8.getComplexity()
            com.ibm.icu.util.MeasureUnit$Complexity r1 = com.ibm.icu.util.MeasureUnit.Complexity.MIXED
            if (r8 == r1) goto Lc7
        L86:
            java.lang.Object r8 = r0.get(r4)
            com.ibm.icu.impl.units.SingleUnitImpl r8 = (com.ibm.icu.impl.units.SingleUnitImpl) r8
            int r0 = r8.getDimensionality()
            int r0 = java.lang.Math.abs(r0)
            if (r0 == r5) goto La3
            java.lang.String r0 = "power"
            java.lang.String r0 = getDeriveCompoundRule(r7, r3, r0)
            int r1 = r0.length()
            if (r1 == r5) goto La3
            return r0
        La3:
            int r0 = r8.getDimensionality()
            int r0 = java.lang.Math.abs(r0)
            if (r0 == r5) goto Lba
            java.lang.String r0 = "prefix"
            java.lang.String r0 = getDeriveCompoundRule(r7, r3, r0)
            int r1 = r0.length()
            if (r1 == r5) goto Lba
            return r0
        Lba:
            java.lang.String r8 = r8.getSimpleUnitID()
            com.ibm.icu.util.MeasureUnit r8 = com.ibm.icu.util.MeasureUnit.forIdentifier(r8)
            java.lang.String r7 = getGenderForBuiltin(r7, r8)
            return r7
        Lc7:
            com.ibm.icu.util.ICUException r7 = new com.ibm.icu.util.ICUException
            java.lang.String r8 = "calculateGenderForUnit does not support MIXED units"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.LongNameHandler.calculateGenderForUnit(com.ibm.icu.util.ULocale, com.ibm.icu.util.MeasureUnit):java.lang.String");
    }

    private static void maybeCalculateGender(ULocale uLocale, MeasureUnit measureUnit, String[] strArr) {
        if (strArr[GENDER_INDEX] != null || getGenderForBuiltin(uLocale, MeasureUnit.METER).isEmpty()) {
            return;
        }
        strArr[GENDER_INDEX] = calculateGenderForUnit(uLocale, measureUnit);
    }

    private LongNameHandler(Map<StandardPlural, SimpleModifier> map, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        this.modifiers = map;
        this.rules = pluralRules;
        this.parent = microPropsGenerator;
    }

    public static String getUnitDisplayName(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth) {
        String[] strArr = new String[ARRAY_LENGTH];
        getMeasureData(uLocale, measureUnit, unitWidth, "", strArr);
        return strArr[DNAM_INDEX];
    }

    public static LongNameHandler forCurrencyLongNames(ULocale uLocale, Currency currency, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        String[] strArr = new String[ARRAY_LENGTH];
        getCurrencyLongNameData(uLocale, currency, strArr);
        LongNameHandler longNameHandler = new LongNameHandler(new EnumMap(StandardPlural.class), pluralRules, microPropsGenerator);
        longNameHandler.simpleFormatsToModifiers(strArr, NumberFormat.Field.CURRENCY);
        return longNameHandler;
    }

    public static LongNameHandler forMeasureUnit(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, String str, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        if (measureUnit.getType() != null) {
            String[] strArr = new String[ARRAY_LENGTH];
            getMeasureData(uLocale, measureUnit, unitWidth, str, strArr);
            maybeCalculateGender(uLocale, measureUnit, strArr);
            LongNameHandler longNameHandler = new LongNameHandler(new EnumMap(StandardPlural.class), pluralRules, microPropsGenerator);
            longNameHandler.simpleFormatsToModifiers(strArr, NumberFormat.Field.MEASURE_UNIT);
            int i2 = GENDER_INDEX;
            if (strArr[i2] != null) {
                longNameHandler.gender = strArr[i2];
            }
            return longNameHandler;
        }
        return forArbitraryUnit(uLocale, measureUnit, unitWidth, str, pluralRules, microPropsGenerator);
    }

    private static LongNameHandler forArbitraryUnit(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, String str, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        String compiledPattern;
        Iterator<SingleUnitImpl> it2 = measureUnit.getCopyOfMeasureUnitImpl().getSingleUnits().iterator();
        MeasureUnit measureUnitBuild = null;
        MeasureUnit measureUnitBuild2 = null;
        while (it2.hasNext()) {
            SingleUnitImpl next = it2.next();
            if (next.getDimensionality() <= 0) {
                next.setDimensionality(next.getDimensionality() * (-1));
                if (measureUnitBuild2 == null) {
                    measureUnitBuild2 = next.build();
                } else {
                    measureUnitBuild2 = measureUnitBuild2.product(next.build());
                }
            } else if (measureUnitBuild == null) {
                measureUnitBuild = next.build();
            } else {
                measureUnitBuild = measureUnitBuild.product(next.build());
            }
        }
        MeasureUnitImpl copyOfMeasureUnitImpl = measureUnitBuild == null ? null : measureUnitBuild.getCopyOfMeasureUnitImpl();
        MeasureUnitImpl copyOfMeasureUnitImpl2 = measureUnitBuild2 != null ? measureUnitBuild2.getCopyOfMeasureUnitImpl() : null;
        DerivedComponents derivedComponents = new DerivedComponents(uLocale, "case", "per");
        String[] strArr = new String[ARRAY_LENGTH];
        processPatternTimes(copyOfMeasureUnitImpl, uLocale, unitWidth, derivedComponents.value0(str), strArr);
        String[] strArr2 = new String[ARRAY_LENGTH];
        processPatternTimes(copyOfMeasureUnitImpl2, uLocale, unitWidth, derivedComponents.value1(str), strArr2);
        int i2 = PER_INDEX;
        if (strArr2[i2] != null) {
            compiledPattern = strArr2[i2];
        } else {
            StringBuilder sb = new StringBuilder();
            compiledPattern = SimpleFormatterImpl.formatCompiledPattern(SimpleFormatterImpl.compileToStringMinMaxArguments(getCompoundValue("per", uLocale, unitWidth), sb, 2, 2), "{0}", PatternProps.trimSpaceChar(SimpleFormatterImpl.getTextWithNoArguments(SimpleFormatterImpl.compileToStringMinMaxArguments(getWithPlural(strArr2, StandardPlural.ONE), sb, 0, 1))));
        }
        LongNameHandler longNameHandler = new LongNameHandler(new EnumMap(StandardPlural.class), pluralRules, microPropsGenerator);
        if (compiledPattern.length() == 0) {
            longNameHandler.simpleFormatsToModifiers(strArr, NumberFormat.Field.MEASURE_UNIT);
        } else {
            longNameHandler.multiSimpleFormatsToModifiers(strArr, compiledPattern, NumberFormat.Field.MEASURE_UNIT);
        }
        longNameHandler.gender = getDerivedGender(uLocale, "per", strArr, strArr2);
        return longNameHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:146:0x02fb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0259  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void processPatternTimes(com.ibm.icu.impl.units.MeasureUnitImpl r34, com.ibm.icu.util.ULocale r35, com.ibm.icu.number.NumberFormatter.UnitWidth r36, java.lang.String r37, java.lang.String[] r38) {
        /*
            Method dump skipped, instructions count: 944
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.LongNameHandler.processPatternTimes(com.ibm.icu.impl.units.MeasureUnitImpl, com.ibm.icu.util.ULocale, com.ibm.icu.number.NumberFormatter$UnitWidth, java.lang.String, java.lang.String[]):void");
    }

    private void simpleFormatsToModifiers(String[] strArr, NumberFormat.Field field) {
        StringBuilder sb = new StringBuilder();
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String strCompileToStringMinMaxArguments = SimpleFormatterImpl.compileToStringMinMaxArguments(getWithPlural(strArr, standardPlural), sb, 0, 1);
            Modifier.Parameters parameters = new Modifier.Parameters();
            parameters.obj = this;
            parameters.signum = null;
            parameters.plural = standardPlural;
            this.modifiers.put(standardPlural, new SimpleModifier(strCompileToStringMinMaxArguments, field, false, parameters));
        }
    }

    private void multiSimpleFormatsToModifiers(String[] strArr, String str, NumberFormat.Field field) {
        StringBuilder sb = new StringBuilder();
        String strCompileToStringMinMaxArguments = SimpleFormatterImpl.compileToStringMinMaxArguments(str, sb, 1, 1);
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String withPlural = getWithPlural(strArr, standardPlural);
            String strCompileToStringMinMaxArguments2 = SimpleFormatterImpl.compileToStringMinMaxArguments(withPlural.length() == 0 ? str : SimpleFormatterImpl.formatCompiledPattern(strCompileToStringMinMaxArguments, withPlural), sb, 0, 1);
            Modifier.Parameters parameters = new Modifier.Parameters();
            parameters.obj = this;
            parameters.signum = null;
            parameters.plural = standardPlural;
            this.modifiers.put(standardPlural, new SimpleModifier(strCompileToStringMinMaxArguments2, field, false, parameters));
        }
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.parent.processQuantity(decimalQuantity);
        microPropsProcessQuantity.modOuter = this.modifiers.get(RoundingUtils.getPluralSafe(microPropsProcessQuantity.rounder, this.rules, decimalQuantity));
        microPropsProcessQuantity.gender = this.gender;
        return microPropsProcessQuantity;
    }

    @Override // com.ibm.icu.impl.number.LongNameMultiplexer.ParentlessMicroPropsGenerator
    public MicroProps processQuantityWithMicros(DecimalQuantity decimalQuantity, MicroProps microProps) {
        microProps.modOuter = this.modifiers.get(RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity));
        return microProps;
    }

    @Override // com.ibm.icu.impl.number.ModifierStore
    public Modifier getModifier(Modifier.Signum signum, StandardPlural standardPlural) {
        return this.modifiers.get(standardPlural);
    }
}
