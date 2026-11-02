package com.ibm.icu.impl.number.range;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceTypeMismatchException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class StandardPluralRanges {
    public static final StandardPluralRanges DEFAULT = new StandardPluralRanges();
    private static volatile Map<String, String> languageToSet;
    StandardPlural[] flatTriples;
    int numTriples = 0;

    private static final class PluralRangeSetsDataSink extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        Map<String, String> output;

        PluralRangeSetsDataSink(Map<String, String> map) {
            this.output = map;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                this.output.put(key.toString(), value.toString());
            }
        }
    }

    private static Map<String, String> getLanguageToSet() throws MissingResourceException {
        Map<String, String> mapUnmodifiableMap = languageToSet;
        if (mapUnmodifiableMap == null) {
            HashMap map = new HashMap();
            ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "pluralRanges")).getAllItemsWithFallback("locales", new PluralRangeSetsDataSink(map));
            mapUnmodifiableMap = Collections.unmodifiableMap(map);
        }
        if (languageToSet == null) {
            languageToSet = mapUnmodifiableMap;
        }
        return languageToSet;
    }

    private static final class PluralRangesDataSink extends UResource.Sink {
        StandardPluralRanges output;

        PluralRangesDataSink(StandardPluralRanges standardPluralRanges) {
            this.output = standardPluralRanges;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Array array = value.getArray();
            this.output.setCapacity(array.getSize());
            for (int i = 0; array.getValue(i, value); i++) {
                UResource.Array array2 = value.getArray();
                if (array2.getSize() != 3) {
                    throw new UResourceTypeMismatchException("Expected 3 elements in pluralRanges.txt array");
                }
                array2.getValue(0, value);
                StandardPlural standardPluralFromString = StandardPlural.fromString(value.getString());
                array2.getValue(1, value);
                StandardPlural standardPluralFromString2 = StandardPlural.fromString(value.getString());
                array2.getValue(2, value);
                this.output.addPluralRange(standardPluralFromString, standardPluralFromString2, StandardPlural.fromString(value.getString()));
            }
        }
    }

    private static void getPluralRangesData(String str, StandardPluralRanges standardPluralRanges) throws MissingResourceException {
        StringBuilder sb = new StringBuilder();
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "pluralRanges");
        sb.setLength(0);
        sb.append("rules/");
        sb.append(str);
        iCUResourceBundle.getAllItemsWithFallback(sb.toString(), new PluralRangesDataSink(standardPluralRanges));
    }

    public static StandardPluralRanges forLocale(ULocale uLocale) {
        return forSet(getSetForLocale(uLocale));
    }

    public static StandardPluralRanges forSet(String str) throws MissingResourceException {
        StandardPluralRanges standardPluralRanges = new StandardPluralRanges();
        if (str == null) {
            return DEFAULT;
        }
        getPluralRangesData(str, standardPluralRanges);
        return standardPluralRanges;
    }

    public static String getSetForLocale(ULocale uLocale) {
        return getLanguageToSet().get(uLocale.getLanguage());
    }

    private StandardPluralRanges() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addPluralRange(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
        StandardPlural[] standardPluralArr = this.flatTriples;
        int i = this.numTriples;
        standardPluralArr[i * 3] = standardPlural;
        standardPluralArr[(i * 3) + 1] = standardPlural2;
        standardPluralArr[(i * 3) + 2] = standardPlural3;
        this.numTriples = i + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCapacity(int i) {
        this.flatTriples = new StandardPlural[i * 3];
    }

    public StandardPlural resolve(StandardPlural standardPlural, StandardPlural standardPlural2) {
        for (int i = 0; i < this.numTriples; i++) {
            StandardPlural[] standardPluralArr = this.flatTriples;
            int i2 = i * 3;
            if (standardPlural == standardPluralArr[i2] && standardPlural2 == standardPluralArr[i2 + 1]) {
                return standardPluralArr[i2 + 2];
            }
        }
        return StandardPlural.OTHER;
    }
}
