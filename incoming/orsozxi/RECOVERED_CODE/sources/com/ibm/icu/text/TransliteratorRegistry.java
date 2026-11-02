package com.ibm.icu.text;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.LocaleUtility;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.util.CaseInsensitiveString;
import com.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* loaded from: classes2.dex */
class TransliteratorRegistry {
    private static final String ANY = "Any";
    private static final boolean DEBUG = false;
    private static final char LOCALE_SEP = '_';
    private static final String NO_VARIANT = "";
    private Map<CaseInsensitiveString, Object[]> registry = Collections.synchronizedMap(new HashMap());
    private Map<CaseInsensitiveString, Map<CaseInsensitiveString, List<CaseInsensitiveString>>> specDAG = Collections.synchronizedMap(new HashMap());
    private List<CaseInsensitiveString> availableIDs = new ArrayList();

    static class Spec {
        private boolean isNextLocale;
        private boolean isSpecLocale;
        private String nextSpec;
        private ICUResourceBundle res;
        private String scriptName;
        private String spec = null;
        private String top;

        public Spec(String str) {
            this.top = str;
            this.scriptName = null;
            try {
                int codeFromName = UScript.getCodeFromName(str);
                int[] code = UScript.getCode(this.top);
                if (code != null) {
                    String name = UScript.getName(code[0]);
                    this.scriptName = name;
                    if (name.equalsIgnoreCase(this.top)) {
                        this.scriptName = null;
                    }
                }
                this.isSpecLocale = false;
                this.res = null;
                if (codeFromName == -1) {
                    ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_TRANSLIT_BASE_NAME, LocaleUtility.getLocaleFromName(this.top));
                    this.res = iCUResourceBundle;
                    if (iCUResourceBundle != null && LocaleUtility.isFallbackOf(iCUResourceBundle.getULocale().toString(), this.top)) {
                        this.isSpecLocale = true;
                    }
                }
            } catch (MissingResourceException unused) {
                this.scriptName = null;
            }
            reset();
        }

        public boolean hasFallback() {
            return this.nextSpec != null;
        }

        public void reset() {
            if (Utility.sameObjects(this.spec, this.top)) {
                return;
            }
            this.spec = this.top;
            this.isSpecLocale = this.res != null;
            setupNext();
        }

        private void setupNext() {
            this.isNextLocale = false;
            if (this.isSpecLocale) {
                String str = this.spec;
                this.nextSpec = str;
                int iLastIndexOf = str.lastIndexOf(95);
                if (iLastIndexOf > 0) {
                    this.nextSpec = this.spec.substring(0, iLastIndexOf);
                    this.isNextLocale = true;
                    return;
                } else {
                    this.nextSpec = this.scriptName;
                    return;
                }
            }
            if (!Utility.sameObjects(this.nextSpec, this.scriptName)) {
                this.nextSpec = this.scriptName;
            } else {
                this.nextSpec = null;
            }
        }

        public String next() {
            this.spec = this.nextSpec;
            this.isSpecLocale = this.isNextLocale;
            setupNext();
            return this.spec;
        }

        public String get() {
            return this.spec;
        }

        public boolean isLocale() {
            return this.isSpecLocale;
        }

        public ResourceBundle getBundle() {
            ICUResourceBundle iCUResourceBundle = this.res;
            if (iCUResourceBundle == null || !iCUResourceBundle.getULocale().toString().equals(this.spec)) {
                return null;
            }
            return this.res;
        }

        public String getTop() {
            return this.top;
        }
    }

    static class ResourceEntry {
        public int direction;
        public String resource;

        public ResourceEntry(String str, int i) {
            this.resource = str;
            this.direction = i;
        }
    }

    static class LocaleEntry {
        public int direction;
        public String rule;

        public LocaleEntry(String str, int i) {
            this.rule = str;
            this.direction = i;
        }
    }

    static class AliasEntry {
        public String alias;

        public AliasEntry(String str) {
            this.alias = str;
        }
    }

    static class CompoundRBTEntry {
        private String ID;
        private UnicodeSet compoundFilter;
        private List<RuleBasedTransliterator.Data> dataVector;
        private List<String> idBlockVector;

        public CompoundRBTEntry(String str, List<String> list, List<RuleBasedTransliterator.Data> list2, UnicodeSet unicodeSet) {
            this.ID = str;
            this.idBlockVector = list;
            this.dataVector = list2;
            this.compoundFilter = unicodeSet;
        }

        public Transliterator getInstance() {
            ArrayList arrayList = new ArrayList();
            int iMax = Math.max(this.idBlockVector.size(), this.dataVector.size());
            int i = 1;
            for (int i2 = 0; i2 < iMax; i2++) {
                if (i2 < this.idBlockVector.size()) {
                    String str = this.idBlockVector.get(i2);
                    if (str.length() > 0) {
                        arrayList.add(Transliterator.getInstance(str));
                    }
                }
                if (i2 < this.dataVector.size()) {
                    arrayList.add(new RuleBasedTransliterator("%Pass" + i, this.dataVector.get(i2), null));
                    i++;
                }
            }
            CompoundTransliterator compoundTransliterator = new CompoundTransliterator(arrayList, i - 1);
            compoundTransliterator.setID(this.ID);
            UnicodeSet unicodeSet = this.compoundFilter;
            if (unicodeSet != null) {
                compoundTransliterator.setFilter(unicodeSet);
            }
            return compoundTransliterator;
        }
    }

    public Transliterator get(String str, StringBuffer stringBuffer) {
        Object[] objArrFind = find(str);
        if (objArrFind == null) {
            return null;
        }
        return instantiateEntry(str, objArrFind, stringBuffer);
    }

    public void put(String str, Class<? extends Transliterator> cls, boolean z) {
        registerEntry(str, cls, z);
    }

    public void put(String str, Transliterator.Factory factory, boolean z) {
        registerEntry(str, factory, z);
    }

    public void put(String str, String str2, int i, boolean z) {
        registerEntry(str, new ResourceEntry(str2, i), z);
    }

    public void put(String str, String str2, boolean z) {
        registerEntry(str, new AliasEntry(str2), z);
    }

    public void put(String str, Transliterator transliterator, boolean z) {
        registerEntry(str, transliterator, z);
    }

    public void remove(String str) {
        String[] strArrIDtoSTV = TransliteratorIDParser.IDtoSTV(str);
        String strSTVtoID = TransliteratorIDParser.STVtoID(strArrIDtoSTV[0], strArrIDtoSTV[1], strArrIDtoSTV[2]);
        this.registry.remove(new CaseInsensitiveString(strSTVtoID));
        removeSTV(strArrIDtoSTV[0], strArrIDtoSTV[1], strArrIDtoSTV[2]);
        this.availableIDs.remove(new CaseInsensitiveString(strSTVtoID));
    }

    private static class IDEnumeration implements Enumeration<String> {
        Enumeration<CaseInsensitiveString> en;

        public IDEnumeration(Enumeration<CaseInsensitiveString> enumeration) {
            this.en = enumeration;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            Enumeration<CaseInsensitiveString> enumeration = this.en;
            return enumeration != null && enumeration.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public String nextElement() {
            return this.en.nextElement().getString();
        }
    }

    public Enumeration<String> getAvailableIDs() {
        return new IDEnumeration(Collections.enumeration(this.availableIDs));
    }

    public Enumeration<String> getAvailableSources() {
        return new IDEnumeration(Collections.enumeration(this.specDAG.keySet()));
    }

    public Enumeration<String> getAvailableTargets(String str) {
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(new CaseInsensitiveString(str));
        if (map == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(map.keySet()));
    }

    public Enumeration<String> getAvailableVariants(String str, String str2) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(str);
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(str2);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(caseInsensitiveString);
        if (map == null) {
            return new IDEnumeration(null);
        }
        List<CaseInsensitiveString> list = map.get(caseInsensitiveString2);
        if (list == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(list));
    }

    private void registerEntry(String str, String str2, String str3, Object obj, boolean z) {
        registerEntry(TransliteratorIDParser.STVtoID(str, str2, str3), str.length() == 0 ? ANY : str, str2, str3, obj, z);
    }

    private void registerEntry(String str, Object obj, boolean z) {
        String[] strArrIDtoSTV = TransliteratorIDParser.IDtoSTV(str);
        registerEntry(TransliteratorIDParser.STVtoID(strArrIDtoSTV[0], strArrIDtoSTV[1], strArrIDtoSTV[2]), strArrIDtoSTV[0], strArrIDtoSTV[1], strArrIDtoSTV[2], obj, z);
    }

    private void registerEntry(String str, String str2, String str3, String str4, Object obj, boolean z) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(str);
        this.registry.put(caseInsensitiveString, obj instanceof Object[] ? (Object[]) obj : new Object[]{obj});
        if (z) {
            registerSTV(str2, str3, str4);
            if (this.availableIDs.contains(caseInsensitiveString)) {
                return;
            }
            this.availableIDs.add(caseInsensitiveString);
            return;
        }
        removeSTV(str2, str3, str4);
        this.availableIDs.remove(caseInsensitiveString);
    }

    private void registerSTV(String str, String str2, String str3) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(str);
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(str2);
        CaseInsensitiveString caseInsensitiveString3 = new CaseInsensitiveString(str3);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> mapSynchronizedMap = this.specDAG.get(caseInsensitiveString);
        if (mapSynchronizedMap == null) {
            mapSynchronizedMap = Collections.synchronizedMap(new HashMap());
            this.specDAG.put(caseInsensitiveString, mapSynchronizedMap);
        }
        List<CaseInsensitiveString> arrayList = mapSynchronizedMap.get(caseInsensitiveString2);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            mapSynchronizedMap.put(caseInsensitiveString2, arrayList);
        }
        if (arrayList.contains(caseInsensitiveString3)) {
            return;
        }
        if (str3.length() > 0) {
            arrayList.add(caseInsensitiveString3);
        } else {
            arrayList.add(0, caseInsensitiveString3);
        }
    }

    private void removeSTV(String str, String str2, String str3) {
        List<CaseInsensitiveString> list;
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(str);
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(str2);
        CaseInsensitiveString caseInsensitiveString3 = new CaseInsensitiveString(str3);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(caseInsensitiveString);
        if (map == null || (list = map.get(caseInsensitiveString2)) == null) {
            return;
        }
        list.remove(caseInsensitiveString3);
        if (list.size() == 0) {
            map.remove(caseInsensitiveString2);
            if (map.size() == 0) {
                this.specDAG.remove(caseInsensitiveString);
            }
        }
    }

    private Object[] findInDynamicStore(Spec spec, Spec spec2, String str) {
        return this.registry.get(new CaseInsensitiveString(TransliteratorIDParser.STVtoID(spec.get(), spec2.get(), str)));
    }

    private Object[] findInStaticStore(Spec spec, Spec spec2, String str) {
        Object[] objArrFindInBundle;
        if (spec.isLocale()) {
            objArrFindInBundle = findInBundle(spec, spec2, str, 0);
        } else {
            objArrFindInBundle = spec2.isLocale() ? findInBundle(spec2, spec, str, 1) : null;
        }
        if (objArrFindInBundle != null) {
            registerEntry(spec.getTop(), spec2.getTop(), str, objArrFindInBundle, false);
        }
        return objArrFindInBundle;
    }

    private Object[] findInBundle(Spec spec, Spec spec2, String str, int i) {
        String[] stringArray;
        int i2;
        ResourceBundle bundle = spec.getBundle();
        if (bundle == null) {
            return null;
        }
        int i3 = 0;
        while (i3 < 2) {
            StringBuilder sb = new StringBuilder();
            if (i3 == 0) {
                sb.append(i == 0 ? "TransliterateTo" : "TransliterateFrom");
            } else {
                sb.append("Transliterate");
            }
            sb.append(spec2.get().toUpperCase(Locale.ENGLISH));
            try {
                stringArray = bundle.getStringArray(sb.toString());
                if (str.length() != 0) {
                    i2 = 0;
                    while (i2 < stringArray.length && !stringArray[i2].equalsIgnoreCase(str)) {
                        i2 += 2;
                    }
                } else {
                    i2 = 0;
                }
            } catch (MissingResourceException unused) {
            }
            if (i2 < stringArray.length) {
                return new Object[]{new LocaleEntry(stringArray[i2 + 1], i3 == 0 ? 0 : i)};
            }
            continue;
            i3++;
        }
        return null;
    }

    private Object[] find(String str) {
        String[] strArrIDtoSTV = TransliteratorIDParser.IDtoSTV(str);
        return find(strArrIDtoSTV[0], strArrIDtoSTV[1], strArrIDtoSTV[2]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003b, code lost:
    
        if (r2.hasFallback() != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003d, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object[] find(java.lang.String r2, java.lang.String r3, java.lang.String r4) {
        /*
            r1 = this;
            com.ibm.icu.text.TransliteratorRegistry$Spec r0 = new com.ibm.icu.text.TransliteratorRegistry$Spec
            r0.<init>(r2)
            com.ibm.icu.text.TransliteratorRegistry$Spec r2 = new com.ibm.icu.text.TransliteratorRegistry$Spec
            r2.<init>(r3)
            int r3 = r4.length()
            if (r3 == 0) goto L1e
            java.lang.Object[] r3 = r1.findInDynamicStore(r0, r2, r4)
            if (r3 == 0) goto L17
            return r3
        L17:
            java.lang.Object[] r3 = r1.findInStaticStore(r0, r2, r4)
            if (r3 == 0) goto L1e
            return r3
        L1e:
            r0.reset()
        L21:
            java.lang.String r3 = ""
            java.lang.Object[] r4 = r1.findInDynamicStore(r0, r2, r3)
            if (r4 == 0) goto L2a
            return r4
        L2a:
            java.lang.Object[] r3 = r1.findInStaticStore(r0, r2, r3)
            if (r3 == 0) goto L31
            return r3
        L31:
            boolean r3 = r0.hasFallback()
            if (r3 != 0) goto L43
            boolean r3 = r2.hasFallback()
            if (r3 != 0) goto L3f
            r2 = 0
            return r2
        L3f:
            r2.next()
            goto L1e
        L43:
            r0.next()
            goto L21
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorRegistry.find(java.lang.String, java.lang.String, java.lang.String):java.lang.Object[]");
    }

    private Transliterator instantiateEntry(String str, Object[] objArr, StringBuffer stringBuffer) {
        while (true) {
            Object obj = objArr[0];
            if (obj instanceof RuleBasedTransliterator.Data) {
                return new RuleBasedTransliterator(str, (RuleBasedTransliterator.Data) obj, null);
            }
            if (obj instanceof Class) {
                try {
                    return (Transliterator) ((Class) obj).newInstance();
                } catch (IllegalAccessException | InstantiationException unused) {
                    return null;
                }
            }
            if (obj instanceof AliasEntry) {
                stringBuffer.append(((AliasEntry) obj).alias);
                return null;
            }
            if (obj instanceof Transliterator.Factory) {
                return ((Transliterator.Factory) obj).getInstance(str);
            }
            if (obj instanceof CompoundRBTEntry) {
                return ((CompoundRBTEntry) obj).getInstance();
            }
            if (obj instanceof AnyTransliterator) {
                return ((AnyTransliterator) obj).safeClone();
            }
            if (obj instanceof RuleBasedTransliterator) {
                return ((RuleBasedTransliterator) obj).safeClone();
            }
            if (obj instanceof CompoundTransliterator) {
                return ((CompoundTransliterator) obj).safeClone();
            }
            if (obj instanceof Transliterator) {
                return (Transliterator) obj;
            }
            TransliteratorParser transliteratorParser = new TransliteratorParser();
            try {
                ResourceEntry resourceEntry = (ResourceEntry) obj;
                transliteratorParser.parse(resourceEntry.resource, resourceEntry.direction);
            } catch (ClassCastException unused2) {
                LocaleEntry localeEntry = (LocaleEntry) obj;
                transliteratorParser.parse(localeEntry.rule, localeEntry.direction);
            }
            if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 0) {
                objArr[0] = new AliasEntry("Any-Null");
            } else if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 1) {
                objArr[0] = transliteratorParser.dataVector.get(0);
            } else if (transliteratorParser.idBlockVector.size() == 1 && transliteratorParser.dataVector.size() == 0) {
                if (transliteratorParser.compoundFilter != null) {
                    objArr[0] = new AliasEntry(transliteratorParser.compoundFilter.toPattern(false) + ";" + transliteratorParser.idBlockVector.get(0));
                } else {
                    objArr[0] = new AliasEntry(transliteratorParser.idBlockVector.get(0));
                }
            } else {
                objArr[0] = new CompoundRBTEntry(str, transliteratorParser.idBlockVector, transliteratorParser.dataVector, transliteratorParser.compoundFilter);
            }
        }
    }
}
