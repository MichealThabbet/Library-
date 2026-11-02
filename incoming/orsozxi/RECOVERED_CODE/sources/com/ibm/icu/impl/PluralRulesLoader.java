package com.ibm.icu.impl;

import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.number.range.StandardPluralRanges;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class PluralRulesLoader extends PluralRules.Factory {
    public static final PluralRulesLoader loader = new PluralRulesLoader();
    private Map<String, String> localeIdToCardinalRulesId;
    private Map<String, String> localeIdToOrdinalRulesId;
    private final Map<String, PluralRules> pluralRulesCache = new HashMap();
    private Map<String, ULocale> rulesIdToEquivalentULocale;

    @Override // com.ibm.icu.text.PluralRules.Factory
    public boolean hasOverride(ULocale uLocale) {
        return false;
    }

    private PluralRulesLoader() {
    }

    @Override // com.ibm.icu.text.PluralRules.Factory
    public ULocale[] getAvailableULocales() {
        Set<String> setKeySet = getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).keySet();
        LinkedHashSet linkedHashSet = new LinkedHashSet(setKeySet.size());
        Iterator<String> it2 = setKeySet.iterator();
        while (it2.hasNext()) {
            linkedHashSet.add(ULocale.createCanonical(it2.next()));
        }
        return (ULocale[]) linkedHashSet.toArray(new ULocale[0]);
    }

    @Override // com.ibm.icu.text.PluralRules.Factory
    public ULocale getFunctionalEquivalent(ULocale uLocale, boolean[] zArr) {
        if (zArr != null && zArr.length > 0) {
            zArr[0] = getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).containsKey(ULocale.canonicalize(uLocale.getBaseName()));
        }
        String rulesIdForLocale = getRulesIdForLocale(uLocale, PluralRules.PluralType.CARDINAL);
        if (rulesIdForLocale == null || rulesIdForLocale.trim().length() == 0) {
            return ULocale.ROOT;
        }
        ULocale uLocale2 = getRulesIdToEquivalentULocaleMap().get(rulesIdForLocale);
        return uLocale2 == null ? ULocale.ROOT : uLocale2;
    }

    private Map<String, String> getLocaleIdToRulesIdMap(PluralRules.PluralType pluralType) {
        checkBuildRulesIdMaps();
        return pluralType == PluralRules.PluralType.CARDINAL ? this.localeIdToCardinalRulesId : this.localeIdToOrdinalRulesId;
    }

    private Map<String, ULocale> getRulesIdToEquivalentULocaleMap() {
        checkBuildRulesIdMaps();
        return this.rulesIdToEquivalentULocale;
    }

    private void checkBuildRulesIdMaps() {
        int i;
        boolean z;
        Map<String, String> mapEmptyMap;
        Map<String, String> mapEmptyMap2;
        Map<String, ULocale> mapEmptyMap3;
        synchronized (this) {
            z = this.localeIdToCardinalRulesId != null;
        }
        if (z) {
            return;
        }
        try {
            UResourceBundle pluralBundle = getPluralBundle();
            UResourceBundle uResourceBundle = pluralBundle.get("locales");
            mapEmptyMap = new TreeMap<>();
            mapEmptyMap3 = new HashMap<>();
            for (int i2 = 0; i2 < uResourceBundle.getSize(); i2++) {
                UResourceBundle uResourceBundle2 = uResourceBundle.get(i2);
                String key = uResourceBundle2.getKey();
                String strIntern = uResourceBundle2.getString().intern();
                mapEmptyMap.put(key, strIntern);
                if (!mapEmptyMap3.containsKey(strIntern)) {
                    mapEmptyMap3.put(strIntern, new ULocale(key));
                }
            }
            UResourceBundle uResourceBundle3 = pluralBundle.get("locales_ordinals");
            mapEmptyMap2 = new TreeMap<>();
            for (i = 0; i < uResourceBundle3.getSize(); i++) {
                UResourceBundle uResourceBundle4 = uResourceBundle3.get(i);
                mapEmptyMap2.put(uResourceBundle4.getKey(), uResourceBundle4.getString().intern());
            }
        } catch (MissingResourceException unused) {
            mapEmptyMap = Collections.emptyMap();
            mapEmptyMap2 = Collections.emptyMap();
            mapEmptyMap3 = Collections.emptyMap();
        }
        synchronized (this) {
            if (this.localeIdToCardinalRulesId == null) {
                this.localeIdToCardinalRulesId = mapEmptyMap;
                this.localeIdToOrdinalRulesId = mapEmptyMap2;
                this.rulesIdToEquivalentULocale = mapEmptyMap3;
            }
        }
    }

    public String getRulesIdForLocale(ULocale uLocale, PluralRules.PluralType pluralType) {
        String str;
        int iLastIndexOf;
        Map<String, String> localeIdToRulesIdMap = getLocaleIdToRulesIdMap(pluralType);
        String strCanonicalize = ULocale.canonicalize(uLocale.getBaseName());
        while (true) {
            str = localeIdToRulesIdMap.get(strCanonicalize);
            if (str != null || (iLastIndexOf = strCanonicalize.lastIndexOf(BaseLocale.SEP)) == -1) {
                break;
            }
            strCanonicalize = strCanonicalize.substring(0, iLastIndexOf);
        }
        return str;
    }

    public PluralRules getOrCreateRulesForLocale(ULocale uLocale, PluralRules.PluralType pluralType) {
        boolean zContainsKey;
        PluralRules pluralRulesNewInternal;
        String rulesIdForLocale = getRulesIdForLocale(uLocale, pluralType);
        if (rulesIdForLocale == null || rulesIdForLocale.trim().length() == 0) {
            return null;
        }
        String setForLocale = StandardPluralRanges.getSetForLocale(uLocale);
        String str = rulesIdForLocale + "/" + setForLocale;
        synchronized (this.pluralRulesCache) {
            zContainsKey = this.pluralRulesCache.containsKey(str);
            pluralRulesNewInternal = zContainsKey ? this.pluralRulesCache.get(str) : null;
        }
        if (!zContainsKey) {
            try {
                UResourceBundle uResourceBundle = getPluralBundle().get("rules").get(rulesIdForLocale);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < uResourceBundle.getSize(); i++) {
                    UResourceBundle uResourceBundle2 = uResourceBundle.get(i);
                    if (i > 0) {
                        sb.append("; ");
                    }
                    sb.append(uResourceBundle2.getKey());
                    sb.append(": ");
                    sb.append(uResourceBundle2.getString());
                }
                pluralRulesNewInternal = PluralRules.newInternal(sb.toString(), StandardPluralRanges.forSet(setForLocale));
            } catch (ParseException | MissingResourceException unused) {
            }
            synchronized (this.pluralRulesCache) {
                if (this.pluralRulesCache.containsKey(str)) {
                    pluralRulesNewInternal = this.pluralRulesCache.get(str);
                } else {
                    this.pluralRulesCache.put(str, pluralRulesNewInternal);
                }
            }
        }
        return pluralRulesNewInternal;
    }

    public UResourceBundle getPluralBundle() throws MissingResourceException {
        return ICUResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "plurals", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
    }

    @Override // com.ibm.icu.text.PluralRules.Factory
    public PluralRules forLocale(ULocale uLocale, PluralRules.PluralType pluralType) {
        PluralRules orCreateRulesForLocale = getOrCreateRulesForLocale(uLocale, pluralType);
        return orCreateRulesForLocale == null ? PluralRules.DEFAULT : orCreateRulesForLocale;
    }
}
