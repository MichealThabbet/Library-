package com.ibm.icu.impl;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class ICUCurrencyDisplayInfoProvider implements CurrencyData.CurrencyDisplayInfoProvider {
    private volatile ICUCurrencyDisplayInfo currencyDisplayInfoCache = null;

    @Override // com.ibm.icu.impl.CurrencyData.CurrencyDisplayInfoProvider
    public boolean hasData() {
        return true;
    }

    @Override // com.ibm.icu.impl.CurrencyData.CurrencyDisplayInfoProvider
    public CurrencyData.CurrencyDisplayInfo getInstance(ULocale uLocale, boolean z) {
        ICUResourceBundle bundleInstance;
        if (uLocale == null) {
            uLocale = ULocale.ROOT;
        }
        ICUCurrencyDisplayInfo iCUCurrencyDisplayInfo = this.currencyDisplayInfoCache;
        if (iCUCurrencyDisplayInfo != null && iCUCurrencyDisplayInfo.locale.equals(uLocale) && iCUCurrencyDisplayInfo.fallback == z) {
            return iCUCurrencyDisplayInfo;
        }
        if (z) {
            bundleInstance = ICUResourceBundle.getBundleInstance(ICUData.ICU_CURR_BASE_NAME, uLocale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
        } else {
            try {
                bundleInstance = ICUResourceBundle.getBundleInstance(ICUData.ICU_CURR_BASE_NAME, uLocale, ICUResourceBundle.OpenType.LOCALE_ONLY);
            } catch (MissingResourceException unused) {
                return null;
            }
        }
        ICUCurrencyDisplayInfo iCUCurrencyDisplayInfo2 = new ICUCurrencyDisplayInfo(uLocale, bundleInstance, z);
        this.currencyDisplayInfoCache = iCUCurrencyDisplayInfo2;
        return iCUCurrencyDisplayInfo2;
    }

    static class ICUCurrencyDisplayInfo extends CurrencyData.CurrencyDisplayInfo {
        final boolean fallback;
        final ULocale locale;
        private final ICUResourceBundle rb;
        private volatile FormattingData formattingDataCache = null;
        private volatile VariantSymbol variantSymbolCache = null;
        private volatile String[] pluralsDataCache = null;
        private volatile SoftReference<ParsingData> parsingDataCache = new SoftReference<>(null);
        private volatile Map<String, String> unitPatternsCache = null;
        private volatile CurrencyData.CurrencySpacingInfo spacingInfoCache = null;

        static class FormattingData {
            final String isoCode;
            String displayName = null;
            String symbol = null;
            CurrencyData.CurrencyFormatInfo formatInfo = null;

            FormattingData(String str) {
                this.isoCode = str;
            }
        }

        static class VariantSymbol {
            final String isoCode;
            String symbol = null;
            final String variant;

            VariantSymbol(String str, String str2) {
                this.isoCode = str;
                this.variant = str2;
            }
        }

        static class ParsingData {
            Map<String, String> symbolToIsoCode = new HashMap();
            Map<String, String> nameToIsoCode = new HashMap();

            ParsingData() {
            }
        }

        public ICUCurrencyDisplayInfo(ULocale uLocale, ICUResourceBundle iCUResourceBundle, boolean z) {
            this.locale = uLocale;
            this.fallback = z;
            this.rb = iCUResourceBundle;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public ULocale getULocale() {
            return this.rb.getULocale();
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public String getName(String str) {
            FormattingData formattingDataFetchFormattingData = fetchFormattingData(str);
            return (formattingDataFetchFormattingData.displayName == null && this.fallback) ? str : formattingDataFetchFormattingData.displayName;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public String getSymbol(String str) {
            FormattingData formattingDataFetchFormattingData = fetchFormattingData(str);
            return (formattingDataFetchFormattingData.symbol == null && this.fallback) ? str : formattingDataFetchFormattingData.symbol;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public String getNarrowSymbol(String str) {
            VariantSymbol variantSymbolFetchVariantSymbol = fetchVariantSymbol(str, "narrow");
            if (variantSymbolFetchVariantSymbol.symbol == null && this.fallback) {
                return getSymbol(str);
            }
            return variantSymbolFetchVariantSymbol.symbol;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public String getFormalSymbol(String str) {
            VariantSymbol variantSymbolFetchVariantSymbol = fetchVariantSymbol(str, "formal");
            if (variantSymbolFetchVariantSymbol.symbol == null && this.fallback) {
                return getSymbol(str);
            }
            return variantSymbolFetchVariantSymbol.symbol;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public String getVariantSymbol(String str) {
            VariantSymbol variantSymbolFetchVariantSymbol = fetchVariantSymbol(str, "variant");
            if (variantSymbolFetchVariantSymbol.symbol == null && this.fallback) {
                return getSymbol(str);
            }
            return variantSymbolFetchVariantSymbol.symbol;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public String getPluralName(String str, String str2) {
            StandardPlural standardPluralOrNullFromString = StandardPlural.orNullFromString(str2);
            String[] strArrFetchPluralsData = fetchPluralsData(str);
            String str3 = standardPluralOrNullFromString != null ? strArrFetchPluralsData[standardPluralOrNullFromString.ordinal() + 1] : null;
            if (str3 == null && this.fallback) {
                str3 = strArrFetchPluralsData[StandardPlural.OTHER.ordinal() + 1];
            }
            if (str3 == null && this.fallback) {
                str3 = fetchFormattingData(str).displayName;
            }
            return (str3 == null && this.fallback) ? str : str3;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public Map<String, String> symbolMap() {
            return fetchParsingData().symbolToIsoCode;
        }

        @Override // com.ibm.icu.text.CurrencyDisplayNames
        public Map<String, String> nameMap() {
            return fetchParsingData().nameToIsoCode;
        }

        @Override // com.ibm.icu.impl.CurrencyData.CurrencyDisplayInfo
        public Map<String, String> getUnitPatterns() {
            return fetchUnitPatterns();
        }

        @Override // com.ibm.icu.impl.CurrencyData.CurrencyDisplayInfo
        public CurrencyData.CurrencyFormatInfo getFormatInfo(String str) {
            return fetchFormattingData(str).formatInfo;
        }

        @Override // com.ibm.icu.impl.CurrencyData.CurrencyDisplayInfo
        public CurrencyData.CurrencySpacingInfo getSpacingInfo() throws MissingResourceException {
            CurrencyData.CurrencySpacingInfo currencySpacingInfoFetchSpacingInfo = fetchSpacingInfo();
            return (!(currencySpacingInfoFetchSpacingInfo.hasBeforeCurrency && currencySpacingInfoFetchSpacingInfo.hasAfterCurrency) && this.fallback) ? CurrencyData.CurrencySpacingInfo.DEFAULT : currencySpacingInfoFetchSpacingInfo;
        }

        FormattingData fetchFormattingData(String str) {
            FormattingData formattingData = this.formattingDataCache;
            if (formattingData != null && formattingData.isoCode.equals(str)) {
                return formattingData;
            }
            FormattingData formattingData2 = new FormattingData(str);
            CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCIES);
            currencySink.formattingData = formattingData2;
            this.rb.getAllItemsWithFallbackNoFail("Currencies/" + str, currencySink);
            this.formattingDataCache = formattingData2;
            return formattingData2;
        }

        VariantSymbol fetchVariantSymbol(String str, String str2) {
            VariantSymbol variantSymbol = this.variantSymbolCache;
            if (variantSymbol != null && variantSymbol.isoCode.equals(str) && variantSymbol.variant.equals(str2)) {
                return variantSymbol;
            }
            VariantSymbol variantSymbol2 = new VariantSymbol(str, str2);
            CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_VARIANT);
            currencySink.variantSymbol = variantSymbol2;
            this.rb.getAllItemsWithFallbackNoFail("Currencies%" + str2 + "/" + str, currencySink);
            this.variantSymbolCache = variantSymbol2;
            return variantSymbol2;
        }

        String[] fetchPluralsData(String str) {
            String[] strArr = this.pluralsDataCache;
            if (strArr != null && strArr[0].equals(str)) {
                return strArr;
            }
            String[] strArr2 = new String[StandardPlural.COUNT + 1];
            strArr2[0] = str;
            CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_PLURALS);
            currencySink.pluralsData = strArr2;
            this.rb.getAllItemsWithFallbackNoFail("CurrencyPlurals/" + str, currencySink);
            this.pluralsDataCache = strArr2;
            return strArr2;
        }

        ParsingData fetchParsingData() throws MissingResourceException {
            ParsingData parsingData = this.parsingDataCache.get();
            if (parsingData != null) {
                return parsingData;
            }
            ParsingData parsingData2 = new ParsingData();
            CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.TOP);
            currencySink.parsingData = parsingData2;
            this.rb.getAllItemsWithFallback("", currencySink);
            this.parsingDataCache = new SoftReference<>(parsingData2);
            return parsingData2;
        }

        Map<String, String> fetchUnitPatterns() throws MissingResourceException {
            Map<String, String> map = this.unitPatternsCache;
            if (map != null) {
                return map;
            }
            HashMap map2 = new HashMap();
            CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS);
            currencySink.unitPatterns = map2;
            this.rb.getAllItemsWithFallback("CurrencyUnitPatterns", currencySink);
            this.unitPatternsCache = map2;
            return map2;
        }

        CurrencyData.CurrencySpacingInfo fetchSpacingInfo() throws MissingResourceException {
            CurrencyData.CurrencySpacingInfo currencySpacingInfo = this.spacingInfoCache;
            if (currencySpacingInfo != null) {
                return currencySpacingInfo;
            }
            CurrencyData.CurrencySpacingInfo currencySpacingInfo2 = new CurrencyData.CurrencySpacingInfo();
            CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_SPACING);
            currencySink.spacingInfo = currencySpacingInfo2;
            this.rb.getAllItemsWithFallback("currencySpacing", currencySink);
            this.spacingInfoCache = currencySpacingInfo2;
            return currencySpacingInfo2;
        }

        private static final class CurrencySink extends UResource.Sink {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            final EntrypointTable entrypointTable;
            final boolean noRoot;
            FormattingData formattingData = null;
            String[] pluralsData = null;
            ParsingData parsingData = null;
            Map<String, String> unitPatterns = null;
            CurrencyData.CurrencySpacingInfo spacingInfo = null;
            VariantSymbol variantSymbol = null;

            enum EntrypointTable {
                TOP,
                CURRENCIES,
                CURRENCY_PLURALS,
                CURRENCY_VARIANT,
                CURRENCY_SPACING,
                CURRENCY_UNIT_PATTERNS
            }

            CurrencySink(boolean z, EntrypointTable entrypointTable) {
                this.noRoot = z;
                this.entrypointTable = entrypointTable;
            }

            @Override // com.ibm.icu.impl.UResource.Sink
            public void put(UResource.Key key, UResource.Value value, boolean z) {
                if (this.noRoot && z) {
                }
                switch (AnonymousClass1.$SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[this.entrypointTable.ordinal()]) {
                    case 1:
                        consumeTopTable(key, value);
                        break;
                    case 2:
                        consumeCurrenciesEntry(key, value);
                        break;
                    case 3:
                        consumeCurrencyPluralsEntry(key, value);
                        break;
                    case 4:
                        consumeCurrenciesVariantEntry(key, value);
                        break;
                    case 5:
                        consumeCurrencySpacingTable(key, value);
                        break;
                    case 6:
                        consumeCurrencyUnitPatternsTable(key, value);
                        break;
                }
            }

            private void consumeTopTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    if (key.contentEquals("Currencies")) {
                        consumeCurrenciesTable(key, value);
                    } else if (key.contentEquals("Currencies%variant")) {
                        consumeCurrenciesVariantTable(key, value);
                    } else if (key.contentEquals("CurrencyPlurals")) {
                        consumeCurrencyPluralsTable(key, value);
                    }
                }
            }

            void consumeCurrenciesTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    String string = key.toString();
                    if (value.getType() != 8) {
                        throw new ICUException("Unexpected data type in Currencies table for " + string);
                    }
                    UResource.Array array = value.getArray();
                    this.parsingData.symbolToIsoCode.put(string, string);
                    array.getValue(0, value);
                    this.parsingData.symbolToIsoCode.put(value.getString(), string);
                    array.getValue(1, value);
                    this.parsingData.nameToIsoCode.put(value.getString(), string);
                }
            }

            void consumeCurrenciesEntry(UResource.Key key, UResource.Value value) {
                String string = key.toString();
                if (value.getType() != 8) {
                    throw new ICUException("Unexpected data type in Currencies table for " + string);
                }
                UResource.Array array = value.getArray();
                if (this.formattingData.symbol == null) {
                    array.getValue(0, value);
                    this.formattingData.symbol = value.getString();
                }
                if (this.formattingData.displayName == null) {
                    array.getValue(1, value);
                    this.formattingData.displayName = value.getString();
                }
                if (array.getSize() <= 2 || this.formattingData.formatInfo != null) {
                    return;
                }
                array.getValue(2, value);
                UResource.Array array2 = value.getArray();
                array2.getValue(0, value);
                String string2 = value.getString();
                array2.getValue(1, value);
                String string3 = value.getString();
                array2.getValue(2, value);
                this.formattingData.formatInfo = new CurrencyData.CurrencyFormatInfo(string, string2, string3, value.getString());
            }

            void consumeCurrenciesVariantEntry(UResource.Key key, UResource.Value value) {
                if (this.variantSymbol.symbol == null) {
                    this.variantSymbol.symbol = value.getString();
                }
            }

            void consumeCurrenciesVariantTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    this.parsingData.symbolToIsoCode.put(value.getString(), key.toString());
                }
            }

            void consumeCurrencyPluralsTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    String string = key.toString();
                    UResource.Table table2 = value.getTable();
                    for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                        if (StandardPlural.orNullFromString(key.toString()) == null) {
                            throw new ICUException("Could not make StandardPlural from keyword " + ((Object) key));
                        }
                        this.parsingData.nameToIsoCode.put(value.getString(), string);
                    }
                }
            }

            void consumeCurrencyPluralsEntry(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    StandardPlural standardPluralOrNullFromString = StandardPlural.orNullFromString(key.toString());
                    if (standardPluralOrNullFromString == null) {
                        throw new ICUException("Could not make StandardPlural from keyword " + ((Object) key));
                    }
                    if (this.pluralsData[standardPluralOrNullFromString.ordinal() + 1] == null) {
                        this.pluralsData[standardPluralOrNullFromString.ordinal() + 1] = value.getString();
                    }
                }
            }

            void consumeCurrencySpacingTable(UResource.Key key, UResource.Value value) {
                CurrencyData.CurrencySpacingInfo.SpacingType spacingType;
                CurrencyData.CurrencySpacingInfo.SpacingPattern spacingPattern;
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    if (key.contentEquals("beforeCurrency")) {
                        spacingType = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                        this.spacingInfo.hasBeforeCurrency = true;
                    } else if (key.contentEquals("afterCurrency")) {
                        spacingType = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                        this.spacingInfo.hasAfterCurrency = true;
                    }
                    UResource.Table table2 = value.getTable();
                    for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                        if (key.contentEquals("currencyMatch")) {
                            spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                        } else if (key.contentEquals("surroundingMatch")) {
                            spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                        } else if (key.contentEquals("insertBetween")) {
                            spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                        }
                        this.spacingInfo.setSymbolIfNull(spacingType, spacingPattern, value.getString());
                    }
                }
            }

            void consumeCurrencyUnitPatternsTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                    String string = key.toString();
                    if (this.unitPatterns.get(string) == null) {
                        this.unitPatterns.put(string, value.getString());
                    }
                }
            }
        }
    }

    /* renamed from: com.ibm.icu.impl.ICUCurrencyDisplayInfoProvider$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable;

        static {
            int[] iArr = new int[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.values().length];
            $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable = iArr;
            try {
                iArr[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCIES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_PLURALS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_VARIANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_SPACING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
