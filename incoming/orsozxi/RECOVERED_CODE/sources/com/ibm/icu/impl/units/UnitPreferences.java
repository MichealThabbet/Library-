package com.ibm.icu.impl.units;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.util.UResourceBundle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class UnitPreferences {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private HashMap<String, HashMap<String, UnitPreference[]>> mapToUnitPreferences;

    public UnitPreferences() throws MissingResourceException {
        this.mapToUnitPreferences = new HashMap<>();
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "units");
        UnitPreferencesSink unitPreferencesSink = new UnitPreferencesSink();
        iCUResourceBundle.getAllItemsWithFallback(UnitsData.Constants.UNIT_PREFERENCE_TABLE_NAME, unitPreferencesSink);
        this.mapToUnitPreferences = unitPreferencesSink.getMapToUnitPreferences();
    }

    public static String formMapKey(String str, String str2) {
        return str + "++" + str2;
    }

    private static String[] getAllUsages(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        int length = str.length();
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            if (str.charAt(length) == '-') {
                arrayList.add(str.substring(0, length));
            }
        }
        if (!str.equals("default")) {
            arrayList.add("default");
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public UnitPreference[] getPreferencesFor(String str, String str2, String str3) {
        UnitPreference[] unitPreferences = null;
        for (String str4 : getAllUsages(str2)) {
            unitPreferences = getUnitPreferences(str, str4, str3);
            if (unitPreferences != null) {
                break;
            }
        }
        return unitPreferences;
    }

    private UnitPreference[] getUnitPreferences(String str, String str2, String str3) {
        String strFormMapKey = formMapKey(str, str2);
        if (!this.mapToUnitPreferences.containsKey(strFormMapKey)) {
            return null;
        }
        HashMap<String, UnitPreference[]> map = this.mapToUnitPreferences.get(strFormMapKey);
        if (map.containsKey(str3)) {
            return map.get(str3);
        }
        return map.get(UnitsData.Constants.DEFAULT_REGION);
    }

    public static class UnitPreference {
        private final BigDecimal geq;
        private final String skeleton;
        private final String unit;

        public UnitPreference(String str, String str2, String str3) {
            this.unit = str;
            this.geq = new BigDecimal(str2);
            this.skeleton = str3;
        }

        public String getUnit() {
            return this.unit;
        }

        public BigDecimal getGeq() {
            return this.geq;
        }

        public String getSkeleton() {
            return this.skeleton;
        }
    }

    public static class UnitPreferencesSink extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private HashMap<String, HashMap<String, UnitPreference[]>> mapToUnitPreferences = new HashMap<>();

        public HashMap<String, HashMap<String, UnitPreference[]>> getMapToUnitPreferences() {
            return this.mapToUnitPreferences;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Key key2 = key;
            UResource.Value value2 = value;
            UResource.Table table = value.getTable();
            int i = 0;
            while (table.getKeyAndValue(i, key2, value2)) {
                String string = key.toString();
                UResource.Table table2 = value.getTable();
                int i2 = 0;
                while (table2.getKeyAndValue(i2, key2, value2)) {
                    String string2 = key.toString();
                    UResource.Table table3 = value.getTable();
                    int i3 = 0;
                    while (table3.getKeyAndValue(i3, key2, value2)) {
                        String string3 = key.toString();
                        UResource.Array array = value.getArray();
                        ArrayList arrayList = new ArrayList();
                        int i4 = 0;
                        while (array.getValue(i4, value2)) {
                            UResource.Table table4 = value.getTable();
                            String string4 = null;
                            UResource.Table table5 = table;
                            String string5 = "1";
                            UResource.Table table6 = table2;
                            String string6 = "";
                            UResource.Table table7 = table3;
                            int i5 = 0;
                            while (table4.getKeyAndValue(i5, key2, value2)) {
                                String string7 = key.toString();
                                if ("unit".equals(string7)) {
                                    string4 = value.getString();
                                } else if ("geq".equals(string7)) {
                                    string5 = value.getString();
                                } else if ("skeleton".equals(string7)) {
                                    string6 = value.getString();
                                }
                                i5++;
                                key2 = key;
                                value2 = value;
                            }
                            arrayList.add(new UnitPreference(string4, string5, string6));
                            i4++;
                            key2 = key;
                            value2 = value;
                            table = table5;
                            table2 = table6;
                            table3 = table7;
                        }
                        insertUnitPreferences(string, string2, string3, (UnitPreference[]) arrayList.toArray(new UnitPreference[0]));
                        i3++;
                        key2 = key;
                        value2 = value;
                        table = table;
                        table2 = table2;
                        table3 = table3;
                    }
                    i2++;
                    key2 = key;
                    value2 = value;
                    table = table;
                }
                i++;
                key2 = key;
                value2 = value;
                table = table;
            }
        }

        private void insertUnitPreferences(String str, String str2, String str3, UnitPreference[] unitPreferenceArr) {
            HashMap<String, UnitPreference[]> map;
            String strFormMapKey = UnitPreferences.formMapKey(str, str2);
            if (this.mapToUnitPreferences.containsKey(strFormMapKey)) {
                map = this.mapToUnitPreferences.get(strFormMapKey);
            } else {
                HashMap<String, UnitPreference[]> map2 = new HashMap<>();
                this.mapToUnitPreferences.put(strFormMapKey, map2);
                map = map2;
            }
            map.put(str3, unitPreferenceArr);
        }
    }
}
