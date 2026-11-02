package com.ibm.icu.impl;

import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class ValidIdentifiers {

    public enum Datasubtype {
        deprecated,
        private_use,
        regular,
        special,
        unknown,
        macroregion,
        reserved
    }

    public enum Datatype {
        currency,
        language,
        region,
        script,
        subdivision,
        unit,
        variant,
        u,
        t,
        x,
        illegal
    }

    public static class ValiditySet {
        public final Set<String> regularData;
        public final Map<String, Set<String>> subdivisionData;

        public ValiditySet(Set<String> set, boolean z) {
            if (z) {
                HashMap map = new HashMap();
                for (String str : set) {
                    int iIndexOf = str.indexOf(45);
                    int i = iIndexOf + 1;
                    if (iIndexOf < 0) {
                        iIndexOf = str.charAt(0) < 'A' ? 3 : 2;
                        i = iIndexOf;
                    }
                    String strSubstring = str.substring(0, iIndexOf);
                    String strSubstring2 = str.substring(i);
                    Set hashSet = (Set) map.get(strSubstring);
                    if (hashSet == null) {
                        hashSet = new HashSet();
                        map.put(strSubstring, hashSet);
                    }
                    hashSet.add(strSubstring2);
                }
                this.regularData = null;
                HashMap map2 = new HashMap();
                for (Map.Entry entry : map.entrySet()) {
                    Set set2 = (Set) entry.getValue();
                    map2.put(entry.getKey(), set2.size() == 1 ? Collections.singleton(set2.iterator().next()) : Collections.unmodifiableSet(set2));
                }
                this.subdivisionData = Collections.unmodifiableMap(map2);
                return;
            }
            this.regularData = Collections.unmodifiableSet(set);
            this.subdivisionData = null;
        }

        public boolean contains(String str) {
            Set<String> set = this.regularData;
            if (set != null) {
                return set.contains(str);
            }
            int iIndexOf = str.indexOf(45);
            return contains(str.substring(0, iIndexOf), str.substring(iIndexOf + 1));
        }

        public boolean contains(String str, String str2) {
            Set<String> set = this.subdivisionData.get(str);
            return set != null && set.contains(str2);
        }

        public String toString() {
            Set<String> set = this.regularData;
            if (set != null) {
                return set.toString();
            }
            return this.subdivisionData.toString();
        }
    }

    private static class ValidityData {
        static final Map<Datatype, Map<Datasubtype, ValiditySet>> data;

        private ValidityData() {
        }

        static {
            EnumMap enumMap = new EnumMap(Datatype.class);
            UResourceBundleIterator iterator = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("idValidity").getIterator();
            while (iterator.hasNext()) {
                UResourceBundle next = iterator.next();
                Datatype datatypeValueOf = Datatype.valueOf(next.getKey());
                EnumMap enumMap2 = new EnumMap(Datasubtype.class);
                UResourceBundleIterator iterator2 = next.getIterator();
                while (iterator2.hasNext()) {
                    UResourceBundle next2 = iterator2.next();
                    Datasubtype datasubtypeValueOf = Datasubtype.valueOf(next2.getKey());
                    HashSet hashSet = new HashSet();
                    boolean z = false;
                    if (next2.getType() == 0) {
                        addRange(next2.getString(), hashSet);
                    } else {
                        for (String str : next2.getStringArray()) {
                            addRange(str, hashSet);
                        }
                    }
                    if (datatypeValueOf == Datatype.subdivision) {
                        z = true;
                    }
                    enumMap2.put((EnumMap) datasubtypeValueOf, (Datasubtype) new ValiditySet(hashSet, z));
                }
                enumMap.put((EnumMap) datatypeValueOf, (Datatype) Collections.unmodifiableMap(enumMap2));
            }
            data = Collections.unmodifiableMap(enumMap);
        }

        private static void addRange(String str, Set<String> set) {
            String lowerString = AsciiUtil.toLowerString(str);
            int iIndexOf = lowerString.indexOf(126);
            if (iIndexOf < 0) {
                set.add(lowerString);
            } else {
                StringRange.expand(lowerString.substring(0, iIndexOf), lowerString.substring(iIndexOf + 1), false, set);
            }
        }
    }

    public static Map<Datatype, Map<Datasubtype, ValiditySet>> getData() {
        return ValidityData.data;
    }

    public static Datasubtype isValid(Datatype datatype, Set<Datasubtype> set, String str) {
        Map<Datasubtype, ValiditySet> map = ValidityData.data.get(datatype);
        if (map == null) {
            return null;
        }
        for (Datasubtype datasubtype : set) {
            ValiditySet validitySet = map.get(datasubtype);
            if (validitySet != null && validitySet.contains(AsciiUtil.toLowerString(str))) {
                return datasubtype;
            }
        }
        return null;
    }

    public static Datasubtype isValid(Datatype datatype, Set<Datasubtype> set, String str, String str2) {
        Map<Datasubtype, ValiditySet> map = ValidityData.data.get(datatype);
        if (map == null) {
            return null;
        }
        String lowerString = AsciiUtil.toLowerString(str);
        String lowerString2 = AsciiUtil.toLowerString(str2);
        for (Datasubtype datasubtype : set) {
            ValiditySet validitySet = map.get(datasubtype);
            if (validitySet != null && validitySet.contains(lowerString, lowerString2)) {
                return datasubtype;
            }
        }
        return null;
    }
}
